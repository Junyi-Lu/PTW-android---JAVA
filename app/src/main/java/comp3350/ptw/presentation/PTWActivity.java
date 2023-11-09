package comp3350.ptw.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import comp3350.ptw.R;
import comp3350.ptw.business.SearchAlgorithm;
import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.business.UIShowAccess;
import comp3350.ptw.objects.Show;

/**
 * This class hosts the plan to watch list and is placed on the tab host
 */
public class PTWActivity extends Activity {
    private static List<Show> ptwList = new ArrayList<>();
    private static UIShowAccess UIShowAccess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ptw_list);
        UIShowAccess = new UIShowAccess();

        ListUpdate.setPtwActivity(this);


        refreshList();
    }

    public void refreshList() {
        UIShowAccess.getAll(ptwList, ShowSubSet.PTWSHOWS);

        PTWListItemAdapter adapter = new PTWListItemAdapter(PTWActivity.this, R.layout.movie_item, ptwList, UIShowAccess);
        ListView listView = findViewById(R.id.ptw_list_view);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    //temporarily updates the list according to the searchPhrase
    public void searchList(String searchPhrase) {
        List<Show> searchedPtwList = SearchAlgorithm.searchMovies(ptwList, searchPhrase);
        PTWListItemAdapter adapter = new PTWListItemAdapter(PTWActivity.this, R.layout.movie_item, searchedPtwList, UIShowAccess);
        ListView listView = (ListView) findViewById(R.id.ptw_list_view);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
