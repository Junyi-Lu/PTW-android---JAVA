package comp3350.ptw.presentation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import comp3350.ptw.R;
import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.business.UIShowAccess;
import comp3350.ptw.objects.Show;

/**
 * this class is an adapter for the objects that go in the watching list, it extends ListItemAdapter
 */
public class WatchingListItemAdapter extends ListItemAdapter {
    private UIShowAccess UIShowAccess;

    public WatchingListItemAdapter(Context context, int textViewResourceId, List<Show> objects, UIShowAccess UIShowAccess) {
        super(context, textViewResourceId, objects);
        this.UIShowAccess = UIShowAccess;
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        Show movie = super.getMovie();
        final ShowSubSet[] subSet = {null};


        getViewHolder().showAdd.setBackgroundResource(R.drawable.ic_playlist_add_black_24dp);
        getViewHolder().ratingBar.setVisibility(View.GONE);

        super.addViewHolderDisplayInfo();

        getViewHolder().showAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewHolder().showAdd.setBackgroundResource(R.drawable.ic_playlist_add_check_black_24dp);
                UIShowAccess.add(movie, ShowSubSet.WATCHEDSHOWS);
                UIShowAccess.remove(movie, ShowSubSet.WATCHINGSHOWS);
                ListUpdate.refreshWatchedList();
                ListUpdate.refreshWatchingList();
                ListUpdate.refreshRecommendList();
            }
        });

        getViewHolder().showDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIShowAccess.remove(movie, ShowSubSet.WATCHINGSHOWS);
                ListUpdate.refreshWatchingList();
            }
        });

        getViewHolder().showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsClick(movie);
            }
        });


        return view;
    }
}
