package comp3350.ptw.presentation;

//import android.support.v7.view.menu.ShowableListMenu;

import comp3350.ptw.business.ShowSubSet;

/**
 *  This class is an interface so that whenever a list is updated the activity that hosts that list can be told to refresh
 */
public class ListUpdate {
    private static PTWActivity ptwActivity;
    private static WatchingActivity watchingActivity;
    private static WatchedActivity watchedActivity;
    private static SearchActivity searchActivity;
    private static RecommendActivity recommendActivity;

    public static void setPtwActivity(PTWActivity ptwActivity) {
        ListUpdate.ptwActivity = ptwActivity;
    }

    public static void setRecommendActivity(RecommendActivity recommendActivity) {
        ListUpdate.recommendActivity = recommendActivity;
    }

    public static void setWatchedActivity(WatchedActivity watchedActivity) {
        ListUpdate.watchedActivity = watchedActivity;
    }

    public static void setWatchingActivity(WatchingActivity watchingActivity) {
        ListUpdate.watchingActivity = watchingActivity;
    }

    public static void setSearchActivity(SearchActivity searchActivity){
        ListUpdate.searchActivity = searchActivity;
    }

    public static PTWActivity getPtwActivity(){
        return ptwActivity;
    }

    public static RecommendActivity getRecommendActivity(){
        return recommendActivity;
    }

    public static WatchingActivity getWatchingActivity(){
        return watchingActivity;
    }

    public static WatchedActivity getWatchedActivity(){
        return watchedActivity;
    }

    public static SearchActivity getSearchActivity(){
        return searchActivity;
    }

    public static void refreshList(ShowSubSet  subSet){
        switch (subSet){
            case AllSHOWS: break;
            case PTWSHOWS: refreshPTWList();
                    break;
            case WATCHINGSHOWS: refreshWatchingList();
                    break;
            case WATCHEDSHOWS: refreshWatchedList();
                    break;
        }
    }

    public static void refreshAll(){
        refreshRecommendList();
        refreshPTWList();
        refreshWatchingList();
        refreshWatchedList();
    }

    public static void refreshPTWList(){
        if(ptwActivity != null){
            ptwActivity.refreshList();
        }
    }

    public static void refreshRecommendList(){
        if(recommendActivity != null){
            recommendActivity.refreshList();
        }
    }

    public static void refreshWatchingList(){
        if(watchingActivity != null){
            watchingActivity.refreshList();
        }
    }

    public static void refreshWatchedList(){
        if(watchedActivity != null){
            watchedActivity.refreshList();
        }
        refreshRecommendList();
    }

}
