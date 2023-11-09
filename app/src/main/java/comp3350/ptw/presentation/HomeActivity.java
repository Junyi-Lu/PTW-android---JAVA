package comp3350.ptw.presentation;


import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.ptw.R;
import comp3350.ptw.application.Main;


/**
 * This is the main/home activity of the PTW app,
 * This activity hosts the tabs for the lists of shows
 */
public class HomeActivity extends AppCompatActivity {
    private static final String PTW_STRING = "Plan To Watch";
    private static final String WATCHING_STRING = "Watching";
    private static final String WATCHED_STRING = "Watched";
    private static final String BROWSE_STRING = "Browse";
    private static final String RECOMMENDED_STRING = "Suggested";
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        copyDatabaseToDevice();
        Main.startUp();

        setContentView(R.layout.activity_home);

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);

        TabSpec ptwSpec = tabHost.newTabSpec(PTW_STRING); //create new tab
        ptwSpec.setIndicator(PTW_STRING); //set the name of the tab/ what the tab is represented by
        Intent ptwIntent = new Intent(this, PTWActivity.class); //make the List the intent
        ptwSpec.setContent(ptwIntent);  //set the list as the view content

        TabSpec recommendSpec = tabHost.newTabSpec(RECOMMENDED_STRING);
        recommendSpec.setIndicator(RECOMMENDED_STRING);
        Intent recommendIntent = new Intent(this, RecommendActivity.class);
        recommendSpec.setContent(recommendIntent);

        TabSpec watchingSpec = tabHost.newTabSpec(WATCHING_STRING);
        watchingSpec.setIndicator(WATCHING_STRING);
        Intent watchingIntent = new Intent(this, WatchingActivity.class);
        watchingSpec.setContent(watchingIntent);

        TabSpec watchedSpec = tabHost.newTabSpec(WATCHED_STRING);
        watchedSpec.setIndicator(WATCHED_STRING);
        Intent watchedIntent = new Intent(this, WatchedActivity.class);
        watchedSpec.setContent(watchedIntent);

        TabSpec browseSpec = tabHost.newTabSpec(BROWSE_STRING);
        browseSpec.setIndicator(BROWSE_STRING);
        Intent browseIntent = new Intent(this, SearchActivity.class);
        browseSpec.setContent(browseIntent);


        tabHost.addTab(browseSpec);//put tabs on tab host
        tabHost.addTab(recommendSpec);
        tabHost.addTab(ptwSpec);
        tabHost.addTab(watchingSpec);
        tabHost.addTab(watchedSpec);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchbar_menu, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();

        //sets search bar to the width of the screen
        searchView.setMaxWidth(Integer.MAX_VALUE);

        //sets searchview listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(ListUpdate.getSearchActivity() != null)
                    ListUpdate.getSearchActivity().searchList(newText);
                if(ListUpdate.getRecommendActivity() != null)
                    ListUpdate.getRecommendActivity().searchList(newText);
                if(ListUpdate.getPtwActivity() != null)
                    ListUpdate.getPtwActivity().searchList(newText);
                if(ListUpdate.getWatchingActivity() != null)
                    ListUpdate.getWatchingActivity().searchList(newText);
                if(ListUpdate.getWatchedActivity() != null)
                    ListUpdate.getWatchedActivity().searchList(newText);
                if(ListUpdate.getRecommendActivity() != null)
                    ListUpdate.getRecommendActivity().searchList(newText);
                return false;
            }
        });

        //clear searchview when switching tabs
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
                searchView.setQuery("", false);
                searchView.clearFocus();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Main.shutDown();
    }

    ///MUST BE IN MAIN ACTIVITY
    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPath(dataDirectory.toString() + "/" + Main.dbName);

        } catch (IOException ioe) {
            ErrorMessages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    //MUST BE IN MAIN ACTIVITY
    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];
            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

}
