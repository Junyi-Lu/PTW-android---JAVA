package comp3350.ptw.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import comp3350.ptw.R;
import comp3350.ptw.business.RecommendingAlgorithm;
import comp3350.ptw.business.SearchAlgorithm;
import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.business.UIShowAccess;
import comp3350.ptw.objects.Show;

import static comp3350.ptw.business.ShowSubSet.WATCHEDSHOWS;
import static comp3350.ptw.business.ShowSubSet.WATCHINGSHOWS;

/**
 * Recommend Class. This class serves to implement the GUI interface for recommended shows
 */
public class RecommendActivity extends Activity {
    private static List<Show> allList = new ArrayList<>();
    private static List<Show> recommendList = new ArrayList<>();
    private static List<Show> completedList = new ArrayList<>();
    private static UIShowAccess showAccess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showAccess = new UIShowAccess();
        setContentView(R.layout.recommend_list);

        refreshList();
    }

    public void refreshList(){
        showAccess.getAll(allList, ShowSubSet.AllSHOWS);
        for(Show movie : allList) {
            if (!showAccess.showInSubSet(movie, WATCHEDSHOWS) && !showAccess.showInSubSet(movie, WATCHINGSHOWS))
                recommendList.add(movie);
        }

        showAccess.getAll(completedList, ShowSubSet.WATCHEDSHOWS);//recommend shows based on user's completed shows
        List<String> results = RecommendingAlgorithm.getData(completedList);

        recommendList = RecommendingAlgorithm.recommendMovies(recommendList, results);

        RecommendListItemAdapter adapter = new RecommendListItemAdapter(RecommendActivity.this, R.layout.movie_item, recommendList, showAccess);
        ListView listView = findViewById(R.id.recommend_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Show show = (Show) listView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("movie", show);
                intent.setClass(RecommendActivity.this, ContentActivity.class);
                startActivity(intent);
            }
        });

        ListUpdate.setRecommendActivity(this);


        listView.setAdapter(adapter);
    }

    //temporarily updates the list according to the searchPhrase
    public void searchList(String searchPhrase) {
        List<Show> searchedRecommendList = SearchAlgorithm.searchMovies(recommendList, searchPhrase);
        RecommendListItemAdapter adapter = new RecommendListItemAdapter(RecommendActivity.this, R.layout.movie_item, searchedRecommendList, showAccess);
        ListView listView = (ListView) findViewById(R.id.recommend_list_view);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
