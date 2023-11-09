package comp3350.ptw.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.ptw.application.DataServices;
import comp3350.ptw.objects.PrioritizedShow;
import comp3350.ptw.objects.RatedShow;
import comp3350.ptw.objects.Show;
import comp3350.ptw.persistence.DataAccess;

import static comp3350.ptw.business.ShowSubSet.PTWSHOWS;
import static comp3350.ptw.business.ShowSubSet.WATCHEDSHOWS;
import static comp3350.ptw.business.ShowSubSet.WATCHINGSHOWS;

/**
 * UIShowAccess integrates the interface, and can access show in the four lists by enumerating showSubSet
 */
public class UIShowAccess {
    private DataAccess dataAccess;
    private static final String ACCESS_VIOLATION = "Illegal ShowSubSet for this operation.";
    private static final String ALREADY_ADDED = DbReturnStrings.IN_LIST.toString();
    private static final String NOT_IN_LIST = DbReturnStrings.NOT_IN_LIST.toString();


    public UIShowAccess() {
        dataAccess = DataServices.getDataAccess();
    }

    public UIShowAccess(DataAccess alternateDataAccess) {
        this.dataAccess = alternateDataAccess;
    }

    public DataAccess getDataAccess() {
        return dataAccess;
    }

    public String getAll(List<Show> allShowsList, ShowSubSet showSubSet) {
        String returnMessage = null;
        if (allShowsList != null) {
            allShowsList.clear();
            switch (showSubSet) {
                case AllSHOWS: //we only display the all shows
                    returnMessage = dataAccess.getAllShows(allShowsList);
                    break;
                default:
                    returnMessage = dataAccess.getShowSubset(allShowsList, showSubSet);
                    break;
            }
        }
        return returnMessage;
    }

    public String add(Show newShow, ShowSubSet showSubSet) {
        String returnMessage = null;
        if (newShow != null && showSubSet != null) {
            switch (showSubSet) {
                case AllSHOWS: { //we only display the all shows
                    returnMessage = ACCESS_VIOLATION;
                    break;
                }
                case PTWSHOWS: {
                    if (showInSubSet(newShow, PTWSHOWS) ) {
                        returnMessage = ALREADY_ADDED;
                    } else {
                        List<Show> workingList = new ArrayList<>();
                        dataAccess.getShowSubset(workingList, PTWSHOWS);
                        PrioritizedShow prioritizedShow = new PrioritizedShow(newShow, (workingList.size() + 1));
                        returnMessage = dataAccess.addShow(prioritizedShow, PTWSHOWS);
                    }
                    break;
                }
                case WATCHINGSHOWS: {
                    if (showInSubSet(newShow, WATCHINGSHOWS) ) {
                        returnMessage = ALREADY_ADDED;
                    } else {
                        returnMessage = dataAccess.addShow(newShow, showSubSet);
                    }
                    break;
                }
                case WATCHEDSHOWS: {
                    if (showInSubSet(newShow, WATCHEDSHOWS)) {
                        returnMessage = ALREADY_ADDED;
                    } else {
                        RatedShow ratedShow = new RatedShow(newShow, 0);
                        returnMessage = dataAccess.addShow(ratedShow, WATCHEDSHOWS);
                    }
                    break;
                }
            }
        }

        return returnMessage;
    }

    public String remove(Show toRemove, ShowSubSet showSubSet) {
        String returnMessage = null;
        if (toRemove != null && showSubSet != null) {
            if (showSubSet == ShowSubSet.AllSHOWS) { //we only display the all shows
                returnMessage = ACCESS_VIOLATION;
            } else if (showSubSet == PTWSHOWS) {
                List<Show> workingList = new ArrayList<>();
                dataAccess.getShowSubset(workingList, PTWSHOWS);
                ((PrioritizedShow) toRemove).setPriority(workingList.size());
                updatePriority(toRemove);
                returnMessage = dataAccess.removeShow(toRemove, PTWSHOWS);

            } else {
                if (showInSubSet(toRemove, showSubSet)) {
                    returnMessage = dataAccess.removeShow(toRemove, showSubSet);
                } else {
                    returnMessage = NOT_IN_LIST;
                }
            }
        }
        return returnMessage;
    }


    public String updatePriority(Show toUpdate) {
        String resultMessage = null;

        if (toUpdate != null && toUpdate.getClass() == PrioritizedShow.class && showInSubSet(toUpdate, PTWSHOWS)) {
            List<Show> oldPriorities = new ArrayList<>();
            dataAccess.getShowSubset(oldPriorities, PTWSHOWS);
            int newSpot = ((PrioritizedShow) toUpdate).getPriority();
            Show oldShow = oldPriorities.get(oldPriorities.indexOf(toUpdate));
            int oldSpot = ((PrioritizedShow) oldShow).getPriority();

            int i = 0;
            while (i < oldPriorities.size()) {
                Show currentShow = oldPriorities.get(i);
                if (oldPriorities.get(i) != toUpdate) {
                    if (oldSpot > newSpot && currentShow.getClass() == PrioritizedShow.class) {
                        if (((PrioritizedShow) currentShow).getPriority() >= newSpot && ((PrioritizedShow) currentShow).getPriority() < oldSpot) {
                            int oldPriority = ((PrioritizedShow) currentShow).getPriority();
                            ((PrioritizedShow) currentShow).setPriority((oldPriority + 1));
                        }
                    } else if (oldSpot < newSpot && currentShow.getClass() == PrioritizedShow.class) {
                        if (((PrioritizedShow) currentShow).getPriority() <= newSpot && ((PrioritizedShow) currentShow).getPriority() > oldSpot) {
                            int oldPriority = ((PrioritizedShow) currentShow).getPriority();
                            ((PrioritizedShow) currentShow).setPriority((oldPriority - 1));
                        }
                    }
                    dataAccess.updatePriority(currentShow);
                }
                i++;
            }

            resultMessage = dataAccess.updatePriority(toUpdate);

        }

        return resultMessage;
    }

    public String updateRating(Show toUpdate) {
        String resultMessage = null;
        if (toUpdate != null && toUpdate.getClass() == RatedShow.class && showInSubSet(toUpdate, WATCHEDSHOWS)) {
            resultMessage = dataAccess.updateRating(toUpdate);
        }
        return resultMessage;

    }

    public boolean showInSubSet(Show show, ShowSubSet showSubSet) {
        boolean inSubSet = false;
        if (show != null) {
            inSubSet = dataAccess.showInSubSet(show.getShowID(), showSubSet);
        }
        return inSubSet;
    }


}
