package comp3350.ptw.persistence;

import java.util.List;

import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.objects.Show;

/**
 * DataAccess interface for the persistence layer
 */
public interface DataAccess {
    void open(String dbName);

    void close();

    String getAllShows(List<Show> showList);

    String getShowSubset(List<Show> showList, ShowSubSet showSubSet);

    String addShow (Show newShow, ShowSubSet showSubSet);

    String removeShow (Show toRemove, ShowSubSet showSubSet);

    String updatePriority(Show toUpdate);

    String updateRating(Show toUpdate);

    boolean showInSubSet(int showId, ShowSubSet showSubSet);


}
