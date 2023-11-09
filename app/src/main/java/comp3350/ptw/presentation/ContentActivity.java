package comp3350.ptw.presentation;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import comp3350.ptw.R;
import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.business.UIShowAccess;
import comp3350.ptw.objects.PrioritizedShow;
import comp3350.ptw.objects.Show;

/**
 * Creates all content for the GUI
 */
public class ContentActivity extends Activity {
    AssetManager assetManager;
    private UIShowAccess UIShowAccess;
    private LinearLayout actorLayout;
    private Show show;
    private LayoutInflater inflater;
    private PrioritizedShow prioritizedShow;
    private FloatingActionsMenu floatingActionsMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        assetManager = getAssets();
        UIShowAccess = new UIShowAccess();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_page);
        inflater = LayoutInflater.from(this);
        ImageView image = (ImageView) findViewById(R.id.movieIcon);
        TextView title = (TextView) findViewById(R.id.movieTitle);
        TextView duration = (TextView) findViewById(R.id.movieDuration);
        TextView year = (TextView) findViewById(R.id.movieYear);
        TextView genre = (TextView) findViewById(R.id.movieGenre);
        TextView director = (TextView) findViewById(R.id.movieDirector);
        TextView summary = (TextView) findViewById(R.id.movieSummary);
        actorLayout = (LinearLayout) findViewById(R.id.actor_gallery);
        show = (Show) getIntent().getSerializableExtra("movie");
        image.setImageBitmap(getBitmapFromAsset(show.getImageID()));
        title.setText(show.getTitle());
        duration.setText("Duration \n" + show.getDuration());
        director.setText("Director \n" + show.getDirector());
        year.setText("Movie\n" + show.getReleaseYear());
        genre.setText("Genre \n" + show.getGenre());
        summary.setText("Summary:" + show.getSummary());
        setFBA();
        setActor();


    }

    public Bitmap getBitmapFromAsset(String strName) {
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("images/" + strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    public void setActor() {
        List<String> actorList = new ArrayList<>();
        RelativeLayout myLayout;
        String[] tokens = show.getActors().trim().split(",");
        inflater = getLayoutInflater();
        TextView actor;
        for (int i = 0; i < tokens.length; i++) {
            actorList.add(tokens[i]);
        }
        for (int i = 0; i < actorList.size(); i++) {
            myLayout = (RelativeLayout) inflater.inflate(R.layout.gallery_actor, null, false);
            actor = (TextView) myLayout.getChildAt(0);
            actor.setText(actorList.get(i));
            actorLayout.addView(myLayout);
        }
    }

    public void setFBA() {
        floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        floatingActionsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContentActivity.this, UIShowAccess.add(show, ShowSubSet.PTWSHOWS), Toast.LENGTH_SHORT).show();
                ListUpdate.refreshPTWList();
                UIShowAccess.remove(show, ShowSubSet.WATCHINGSHOWS);
                UIShowAccess.remove(show, ShowSubSet.WATCHEDSHOWS);
                ListUpdate.refreshAll();
            }
        });

        FloatingActionButton actionB = (FloatingActionButton) findViewById(R.id.action_b);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContentActivity.this, UIShowAccess.add(show, ShowSubSet.WATCHINGSHOWS), Toast.LENGTH_SHORT).show();
                List<Show> ptwList = new ArrayList<>();
                removePTWList(show);
                UIShowAccess.remove(show, ShowSubSet.WATCHEDSHOWS);
                ListUpdate.refreshAll();
            }
        });

        FloatingActionButton actionC = (FloatingActionButton) findViewById(R.id.action_c);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContentActivity.this, UIShowAccess.add(show, ShowSubSet.WATCHEDSHOWS), Toast.LENGTH_SHORT).show();
                UIShowAccess.remove(show, ShowSubSet.WATCHINGSHOWS);
                removePTWList(show);
                ListUpdate.refreshAll();
            }
        });

    }

    private void removePTWList(Show show){
        ArrayList<Show> ptwList = new ArrayList<>();
        if (UIShowAccess.showInSubSet(show, ShowSubSet.PTWSHOWS)) {
            UIShowAccess.getAll(ptwList, ShowSubSet.PTWSHOWS);
            for (int i = 0; i < ptwList.size(); i++) {
                if (ptwList.get(i).getTitle().equals(show.getTitle())) {
                    prioritizedShow = (PrioritizedShow) ptwList.get(i);
                }
            }
            UIShowAccess.remove(prioritizedShow, ShowSubSet.PTWSHOWS);
        }
    }

}
