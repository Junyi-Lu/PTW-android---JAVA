package comp3350.ptw.presentation;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import comp3350.ptw.R;
import comp3350.ptw.business.SearchAlgorithm;
import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.business.UIShowAccess;

/**
 * This class hosts the search/browse lists and enables searching of the whole library of shows
 */
public class SearchActivity extends Activity {
    private List allShows = new ArrayList<>();
    private List displayList = new ArrayList<>();
    private UIShowAccess UIShowAccess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        UIShowAccess = new UIShowAccess();

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        UIShowAccess.getAll(allShows, ShowSubSet.AllSHOWS);
        displayList = SearchAlgorithm.searchMovies(allShows, "");

        SearchAdapter adapter = new SearchAdapter(SearchActivity.this, R.layout.movie_item, allShows, this);
        ListView listView = findViewById(R.id.search_list_view);


        ListUpdate.setSearchActivity(this);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            displayList = SearchAlgorithm.searchMovies(allShows, query);
            adapter = new SearchAdapter(SearchActivity.this, R.layout.movie_item, displayList, this);

        }

        listView.setAdapter(adapter);
    }

    //temporarily updates the list according to the searchPhrase
    public void searchList(String searchPhrase) {
        displayList = SearchAlgorithm.searchMovies(allShows, searchPhrase);
        SearchAdapter adapter = new SearchAdapter(SearchActivity.this, R.layout.movie_item, displayList, this);
        ListView listView = (ListView) findViewById(R.id.search_list_view);
        listView.setAdapter(adapter);
    }

}
