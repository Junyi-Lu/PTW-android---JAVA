package comp3350.PTW.tests.business;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.PTW.tests.DataAccessStub;
import comp3350.ptw.business.DbReturnStrings;
import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.business.UIShowAccess;
import comp3350.ptw.objects.PrioritizedShow;
import comp3350.ptw.objects.RatedShow;
import comp3350.ptw.objects.Show;

/**
 * TestShowAccess
 * Group7
 * PTW
 * <p>
 * This class tests UIShowAccess class
 */

public class UIShowAccessTest extends TestCase {
    private UIShowAccess UIShowAccess;

    public void setUp() {
        System.out.println("\nStarting Business test UIShowAccess (using stub)");
        UIShowAccess = new UIShowAccess(new DataAccessStub("stub"));

    }

    public void tearDown() {
        System.out.println("\nFinished Business test UIShowAccess (using stub)");
    }

    @Test
    public void testGetAll() {
        List<Show> showList = new ArrayList<>();
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.AllSHOWS));//since the allshows only display the information,not supposed to modify
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.PTWSHOWS));
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.WATCHEDSHOWS));
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.WATCHINGSHOWS));

        List<Show> anotherList = new ArrayList<>();
        Show show = new Show(-2, "getall test", "", "", "", "", 0, 0, "test");
        anotherList.add(show);
        anotherList.add(show);
        anotherList.add(show);
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.AllSHOWS));
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.PTWSHOWS));
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.WATCHEDSHOWS));
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.WATCHINGSHOWS));

        anotherList.remove(show);
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.AllSHOWS));
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.PTWSHOWS));
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.WATCHEDSHOWS));
        assertEquals("successful", UIShowAccess.getAll(showList, ShowSubSet.WATCHINGSHOWS));
    }

    @Test
    public void testAdd() {
        Show showOne = new Show(-12, "adding testShow", "testGenre", "testActor", "testDirector", "testSummary", 0, 0, "test");
        assertEquals("Illegal ShowSubSet for this operation.", UIShowAccess.add(showOne, ShowSubSet.AllSHOWS));
        assertEquals(DbReturnStrings.ADD_SUCCESS.toString(), UIShowAccess.add(showOne, ShowSubSet.PTWSHOWS));
        assertEquals(DbReturnStrings.ADD_SUCCESS.toString(), UIShowAccess.add(showOne, ShowSubSet.WATCHEDSHOWS));
        assertEquals(DbReturnStrings.ADD_SUCCESS.toString(), UIShowAccess.add(showOne, ShowSubSet.WATCHINGSHOWS));
        assertEquals(DbReturnStrings.IN_LIST.toString(), UIShowAccess.add(showOne, ShowSubSet.PTWSHOWS));


        Show showSec = new Show(-5, "a", "   ", " ", "", "", -5, -10, "test");
        assertEquals("Illegal ShowSubSet for this operation.", UIShowAccess.add(showSec, ShowSubSet.AllSHOWS));
        assertEquals(DbReturnStrings.ADD_SUCCESS.toString(), UIShowAccess.add(showSec, ShowSubSet.WATCHINGSHOWS));
        assertEquals(DbReturnStrings.IN_LIST.toString(), UIShowAccess.add(showOne, ShowSubSet.PTWSHOWS));
        assertEquals(DbReturnStrings.IN_LIST.toString(), UIShowAccess.add(showOne, ShowSubSet.WATCHEDSHOWS));
        assertEquals(DbReturnStrings.IN_LIST.toString(), UIShowAccess.add(showOne, ShowSubSet.WATCHINGSHOWS));

        Show showThird = new Show(-15, "a", "   ", " ", "", "", -5, -10, "test");
        assertEquals("Illegal ShowSubSet for this operation.", UIShowAccess.add(showThird, ShowSubSet.AllSHOWS));
        assertEquals(DbReturnStrings.ADD_SUCCESS.toString(), UIShowAccess.add(showThird, ShowSubSet.WATCHEDSHOWS));
        assertEquals(DbReturnStrings.ADD_SUCCESS.toString(), UIShowAccess.add(showThird, ShowSubSet.PTWSHOWS));
        assertEquals(DbReturnStrings.ADD_SUCCESS.toString(), UIShowAccess.add(showThird, ShowSubSet.WATCHINGSHOWS));
        assertEquals(DbReturnStrings.IN_LIST.toString(), UIShowAccess.add(showThird, ShowSubSet.WATCHEDSHOWS));


    }

    @Test
    public void testUpdatePriority() {
        UIShowAccess = new UIShowAccess(new DataAccessStub("stub"));

        Show showOne = new Show(-21, "showOne", "testGenre", "testActor", "testDirector", "testSummary", 0, 0, "test");
        Show showTwo = new Show(-22, "ShowTwo", "testGenre", "testActor", "testDirector", "testSummary", 0, 0, "test");
        Show showThree = new Show(-23, "ShowThree", "testGenre", "testActor", "testDirector", "testSummary", 0, 0, "test");
        Show showFour = new Show(-24, "ShowFour", "testGenre", "testActor", "testDirector", "testSummary", 0, 0, "test");
        Show showFive = new Show(-25, "ShowFive", "testGenre", "testActor", "testDirector", "testSummary", 0, 0, "test");

        List<Show> ptwList = new ArrayList<>();
        UIShowAccess.getAll(ptwList, ShowSubSet.PTWSHOWS);

        UIShowAccess.add(showOne, ShowSubSet.PTWSHOWS);
        UIShowAccess.add(showTwo, ShowSubSet.PTWSHOWS);
        UIShowAccess.add(showThree, ShowSubSet.PTWSHOWS);
        UIShowAccess.add(showFour, ShowSubSet.PTWSHOWS);
        UIShowAccess.add(showFive, ShowSubSet.PTWSHOWS);

        ptwList = new ArrayList<>();
        UIShowAccess.getAll(ptwList, ShowSubSet.PTWSHOWS);
        PrioritizedShow showOneP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showOne));
        PrioritizedShow showTwoP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showTwo));
        PrioritizedShow showThreeP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showThree));
        PrioritizedShow showFourP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFour));
        PrioritizedShow showFiveP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFive));

        assertEquals(1, showOneP.getPriority());
        assertEquals(2, showTwoP.getPriority());
        assertEquals(3, showThreeP.getPriority());
        assertEquals(4, showFourP.getPriority());
        assertEquals(5, showFiveP.getPriority());

        showOneP.setPriority(5);

        UIShowAccess.updatePriority(showOneP);

        UIShowAccess.getAll(ptwList, ShowSubSet.PTWSHOWS);
        showOneP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showOne));
        showTwoP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showTwo));
        showThreeP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showThree));
        showFourP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFour));
        showFiveP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFive));

        assertEquals(1, showTwoP.getPriority());
        assertEquals(2, showThreeP.getPriority());
        assertEquals(3, showFourP.getPriority());
        assertEquals(4, showFiveP.getPriority());
        assertEquals(5, showOneP.getPriority());

        showFourP.setPriority(1);

        UIShowAccess.updatePriority(showFourP);

        UIShowAccess.getAll(ptwList, ShowSubSet.PTWSHOWS);
        showOneP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showOne));
        showTwoP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showTwo));
        showThreeP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showThree));
        showFourP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFour));
        showFiveP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFive));

        assertEquals(1, showFourP.getPriority());
        assertEquals(2, showTwoP.getPriority());
        assertEquals(3, showThreeP.getPriority());
        assertEquals(4, showFiveP.getPriority());
        assertEquals(5, showOneP.getPriority());


    }

    @Test
    public void testUpdateRating() {
        Show ratedShow = new Show(-20, "showOne", "testGenre", "testActor", "testDirector", "testSummary", 0, 0, "test");
        UIShowAccess.add(ratedShow, ShowSubSet.WATCHEDSHOWS);
        List<Show> watchedlist = new ArrayList<>();
        UIShowAccess.getAll(watchedlist, ShowSubSet.WATCHEDSHOWS);
        if (watchedlist.get(0) instanceof RatedShow) {
            assertEquals(0, ((RatedShow) watchedlist.get(0)).getRating(), 0.0);
            ((RatedShow) watchedlist.get(0)).setRating(5);
            UIShowAccess.updateRating(watchedlist.get(0));
            assertEquals(5, ((RatedShow) watchedlist.get(0)).getRating(), 0.0);
            UIShowAccess.updateRating(ratedShow);
        }
    }

    @Test
    public void testRemove() {

        List<Show> allShows = new ArrayList<>();
        UIShowAccess.getAll(allShows, ShowSubSet.AllSHOWS);


        Show showOne = new Show(-12, "adding testShow", "testGenre", "testActor", "testDirector", "testSummary", 0, 0, "test");
        Show showSec = new Show(-8, "w", "   ", " ", " ", " ", 3, 4, "test");
        assertEquals("Illegal ShowSubSet for this operation.", UIShowAccess.remove(showOne, ShowSubSet.AllSHOWS));
        UIShowAccess.add(showOne, ShowSubSet.PTWSHOWS);
        UIShowAccess.add(showSec, ShowSubSet.PTWSHOWS);
        List<Show> ptwList = new ArrayList<>();
        UIShowAccess.getAll(ptwList, ShowSubSet.PTWSHOWS);
        for (int i = 0; i < ptwList.size(); i++) {
            if (ptwList.get(i).getTitle().equals(showOne.getTitle())) {
                assertEquals("Show removed from list.", UIShowAccess.remove(ptwList.get(i), ShowSubSet.PTWSHOWS));
            }
        }
        assertEquals("Show not in current list.", UIShowAccess.remove(showOne, ShowSubSet.WATCHINGSHOWS));
        assertEquals("Show not in current list.", UIShowAccess.remove(showOne, ShowSubSet.WATCHEDSHOWS));

    }

    @Test
    public void testShowInSubSet() {
        Show showOne = new Show(12, "adding testShow", "testGenre", "", "testActor", "testSummary", 0, 0, "test");
        UIShowAccess.add(showOne, ShowSubSet.PTWSHOWS);
        UIShowAccess.add(showOne, ShowSubSet.WATCHINGSHOWS);
        UIShowAccess.add(showOne, ShowSubSet.WATCHEDSHOWS);
        assertFalse(UIShowAccess.showInSubSet(showOne, ShowSubSet.AllSHOWS));//only use to display
        assertTrue(UIShowAccess.showInSubSet(showOne, ShowSubSet.PTWSHOWS));
        assertTrue(UIShowAccess.showInSubSet(showOne, ShowSubSet.WATCHINGSHOWS));
        assertTrue(UIShowAccess.showInSubSet(showOne, ShowSubSet.WATCHEDSHOWS));
    }
}

