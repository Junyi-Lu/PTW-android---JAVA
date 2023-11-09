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
 * This class hosts the watched list and is placed on the tab host
 */
public class WatchedActivity extends Activity {
    private List<Show> watchedList = new ArrayList<>();
    private UIShowAccess UIShowAccess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watched_list);
        UIShowAccess = new UIShowAccess();

        ListUpdate.setWatchedActivity(this);

        refreshList();
    }


    public void refreshList() {
        UIShowAccess.getAll(watchedList, ShowSubSet.WATCHEDSHOWS);

        WatchedListItemAdapter adapter = new WatchedListItemAdapter(WatchedActivity.this, R.layout.movie_item, watchedList, UIShowAccess);
        ListView listView = findViewById(R.id.watched_list_view);

        listView.setAdapter(adapter);
    }

    //temporarily updates the list according to the searchPhrase
    public void searchList(String searchPhrase) {
        List<Show> searchedWatchedList = SearchAlgorithm.searchMovies(watchedList, searchPhrase);
        WatchedListItemAdapter adapter = new WatchedListItemAdapter(WatchedActivity.this, R.layout.movie_item, searchedWatchedList, UIShowAccess);
        ListView listView = (ListView) findViewById(R.id.watched_list_view);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
