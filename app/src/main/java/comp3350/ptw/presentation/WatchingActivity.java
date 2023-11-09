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
 * This class hosts the watching list and is placed on the tab host
 */
public class WatchingActivity extends Activity {
    private List<Show> watchingList = new ArrayList<>();
    private UIShowAccess UIShowAccess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watching_list);
        UIShowAccess = new UIShowAccess();

        ListUpdate.setWatchingActivity(this);
        refreshList();
    }

    public void refreshList() {
        UIShowAccess.getAll(watchingList, ShowSubSet.WATCHINGSHOWS);


        WatchingListItemAdapter adapter = new WatchingListItemAdapter(WatchingActivity.this, R.layout.movie_item, watchingList, UIShowAccess);
        ListView listView = findViewById(R.id.watching_list_view);

        listView.setAdapter(adapter);
    }

    //temporarily updates the list according to the searchPhrase
    public void searchList(String searchPhrase) {
        List<Show> searchedWatchingList = SearchAlgorithm.searchMovies(watchingList, searchPhrase);
        WatchingListItemAdapter adapter = new WatchingListItemAdapter(WatchingActivity.this, R.layout.movie_item, searchedWatchingList, UIShowAccess);
        ListView listView = (ListView) findViewById(R.id.watching_list_view);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
