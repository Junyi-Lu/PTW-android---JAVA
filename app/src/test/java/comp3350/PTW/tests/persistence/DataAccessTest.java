package comp3350.PTW.tests.persistence;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

import comp3350.PTW.tests.DataAccessStub;
import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.objects.PrioritizedShow;
import comp3350.ptw.objects.RatedShow;
import comp3350.ptw.objects.Show;
import comp3350.ptw.persistence.DataAccess;

public class DataAccessTest extends TestCase {

    private DataAccess dataAccess;

    public DataAccessTest(String arg0) {
        super(arg0);
    }

    public static void dataAccessTest(DataAccess dataAccess) {
        DataAccessTest dataAccessTest = new DataAccessTest("");
        dataAccessTest.dataAccess = dataAccess;
        dataAccessTest.testGettingAllShows();
        dataAccessTest.testGettingShowSubSet();
        dataAccessTest.testAddingShows();
        dataAccessTest.testRemovingShows();

    }

    public void setUp() {
        System.out.println("\nStarting Persistence test DataAccess (using stub)");

        dataAccess = new DataAccessStub("stub");
        dataAccess.open("stub");
    }


    public void tearDown() {
        System.out.println("\nFinished Persistence test DataAccess (using stub)");
    }

    @Test
    public void testGettingAllShows() {

        try {
            dataAccess.getAllShows(null);
        } catch (Exception e) {
            assertTrue("should not throw exception when passed null parameter", true);
        }

        ArrayList<Show> showList = null;
        dataAccess.getAllShows(showList);
        assertNull(showList);


        showList = new ArrayList<>();
        dataAccess.getAllShows(showList);
        assertEquals(26, showList.size());

        for (int i = 0; i < showList.size(); i++) {
            assertTrue(showList.get(i) instanceof Show);
        }

        assertEquals("The Shawshank Redemption", showList.get(0).getTitle());
        assertEquals("Herbie Fully Loaded", showList.get(25).getTitle());


        dataAccess.getAllShows(showList);
        assertEquals(26, showList.size());


    }

    @Test
    public void testAddingShows() {

        Show show = new Show(12, "adding testShow", "testGenre", "testActors", "testDirectors",  "testSummary", 0, 0, "test");
        try {
            dataAccess.addShow(show, null);

            dataAccess.addShow(null, ShowSubSet.AllSHOWS);
            dataAccess.addShow(null, ShowSubSet.PTWSHOWS);
            dataAccess.addShow(null, ShowSubSet.WATCHINGSHOWS);
            dataAccess.addShow(null, ShowSubSet.WATCHEDSHOWS);
            assertTrue("Does not throw exception when passed null value", true);
        } catch (Exception e) {
            assertTrue("Should not throw exception", true);
        }

        ArrayList<Show> showList = new ArrayList<>();
        dataAccess.getAllShows(showList);
        int sizePre = showList.size();

        dataAccess.addShow(show, ShowSubSet.AllSHOWS);
        ArrayList<Show> showListPost = new ArrayList<>();
        dataAccess.getAllShows(showListPost);
        assertEquals(sizePre, showList.size());
        assertTrue(showList.containsAll(showListPost));
        assertTrue(showListPost.containsAll(showList));

        testShowAddingHelper(show, ShowSubSet.PTWSHOWS);

        showList.clear();
        dataAccess.getShowSubset(showList, ShowSubSet.PTWSHOWS);
        sizePre = showList.size();
        showList.clear();

        dataAccess.addShow(show, ShowSubSet.PTWSHOWS);
        dataAccess.getShowSubset(showList, ShowSubSet.PTWSHOWS);
        assertEquals(sizePre, showList.size());
        assertTrue("show should be in list after it was added", showList.contains(show));

        testShowAddingHelper(show, ShowSubSet.WATCHINGSHOWS);

        testShowAddingHelper(show, ShowSubSet.WATCHEDSHOWS);

        Show newShow = new Show(22, "newTestShow", "testGenere", "testActors", "testDirectors",  "testSummary", 0, 0, "test2");

        dataAccess.addShow(newShow, ShowSubSet.AllSHOWS);
        testShowAddingHelper(newShow, ShowSubSet.PTWSHOWS);
        testShowAddingHelper(newShow, ShowSubSet.WATCHINGSHOWS);
        testShowAddingHelper(newShow, ShowSubSet.WATCHEDSHOWS);

    }

    private void testShowAddingHelper(Show show, ShowSubSet showSubSet) {
        ArrayList<Show> showList = new ArrayList<>();

        if(showSubSet == ShowSubSet.PTWSHOWS){
            show = new PrioritizedShow(show, 1);
        }else if(showSubSet == ShowSubSet.WATCHEDSHOWS){
            show = new RatedShow(show, 1);
        }
        dataAccess.getShowSubset(showList, showSubSet);

        ArrayList<Show> showListPost = new ArrayList<>();
        dataAccess.addShow(show, showSubSet);
        dataAccess.getShowSubset(showListPost, showSubSet);
        assertEquals(showList.size() + 1, showListPost.size());

        assertTrue(showListPost.containsAll(showList));
        assertFalse(showList.containsAll(showListPost));

    }

    @Test
    public void testRemovingShows() {

        Show show = new Show(1, "newTestShow", "testGenere", "testActors", "testDirectors",  "testSummary", 0, 0, "test2");

        try {
            dataAccess.removeShow(null, null);

            dataAccess.removeShow(null, ShowSubSet.AllSHOWS);
            dataAccess.removeShow(null, ShowSubSet.PTWSHOWS);
            dataAccess.removeShow(null, ShowSubSet.WATCHINGSHOWS);
            dataAccess.removeShow(null, ShowSubSet.WATCHEDSHOWS);

            dataAccess.removeShow(show, null);
        } catch (Exception e) {
            assertTrue("should not throw exception when passed null parameter", true);
        }

        runAllRemovalTests(ShowSubSet.PTWSHOWS);
        runAllRemovalTests(ShowSubSet.WATCHINGSHOWS);
        runAllRemovalTests(ShowSubSet.WATCHEDSHOWS);

    }

    private void runAllRemovalTests(ShowSubSet showSubSet) {
        testRemovalHelper(showSubSet);
        testRemovalSingleElement(showSubSet);
        testRemovalSingleGivenElement(showSubSet);
        testRemovalMultiElement(showSubSet);
        testRemovalEnds(showSubSet);
        testRemovalMiddle(showSubSet);
    }


    private void testRemovalHelper(ShowSubSet showSubSet) {
        Show show = new Show(11, "newTestShow", "testGenere", "testActors", "testDirectors", "testSummary", 0, 0, "test2");
        if(showSubSet == ShowSubSet.PTWSHOWS){
            show = new PrioritizedShow(show, 1);
        }else if(showSubSet == ShowSubSet.WATCHEDSHOWS){
            show = new RatedShow(show, 1);
        }
        ArrayList<Show> showList = new ArrayList<>();
        dataAccess.getShowSubset(showList, showSubSet);

        dataAccess.removeShow(show, showSubSet);

        ArrayList<Show> testList = new ArrayList<>();
        dataAccess.getShowSubset(testList, showSubSet);

        assertEquals(showList, testList);
    }

    private void testRemovalSingleElement(ShowSubSet showSubSet) {
        Show show = new Show(21, "newTestShow", "testGenere", "testActors", "testDirectors",  "testSummary", 0, 0, "test2");
        if(showSubSet == ShowSubSet.PTWSHOWS){
            show = new PrioritizedShow(show, 1);
        }else if(showSubSet == ShowSubSet.WATCHEDSHOWS){
            show = new RatedShow(show, 1);
        }
        dataAccess.addShow(show, showSubSet);

        testRemovalHelper(showSubSet);
    }

    private void testRemovalSingleGivenElement(ShowSubSet showSubSet) {
        Show show = new Show(22, "newTestShow", "testGenere", "testActors", "testDirectors",  "testSummary", 0, 0, "test2");
        if(showSubSet == ShowSubSet.PTWSHOWS){
            show = new PrioritizedShow(show, 1);
        }else if(showSubSet == ShowSubSet.WATCHEDSHOWS){
            show = new RatedShow(show, 1);
        }
        dataAccess.addShow(show, showSubSet);

        testRemovalGivenElementHelper(showSubSet, show);
    }

    private void testRemovalMultiElement(ShowSubSet showSubSet) {
        addShowsHelper(showSubSet);

        testRemovalHelper(showSubSet);
    }

    private void testRemovalEnds(ShowSubSet showSubSet) {
        addShowsHelper(showSubSet);

        ArrayList<Show> showList = new ArrayList<>();
        dataAccess.getShowSubset(showList, showSubSet);
        int end = showList.size() - 1;

        testRemovalOfElementMultiElement(showSubSet, showList.get(end));
        testRemovalOfElementMultiElement(showSubSet, showList.get(0));
    }

    private void testRemovalMiddle(ShowSubSet showSubSet) {
        addShowsHelper(showSubSet);

        ArrayList<Show> showList = new ArrayList<>();
        dataAccess.getShowSubset(showList, showSubSet);

        testRemovalOfElementMultiElement(showSubSet, showList.get(1));
    }


    private void testRemovalGivenElementHelper(ShowSubSet showSubSet, Show show) {

        ArrayList<Show> showList = new ArrayList<>();
        dataAccess.getShowSubset(showList, showSubSet);

        assertTrue(showList.contains(show));

        dataAccess.removeShow(show, showSubSet);

        ArrayList<Show> testList = new ArrayList<>();
        dataAccess.getShowSubset(testList, showSubSet);

        assertFalse(testList.contains(show));

        assertTrue(showList.containsAll(testList));
        assertFalse(testList.containsAll(showList));
        assertEquals(showList.size(), testList.size() + 1);
    }

    private void testRemovalOfElementMultiElement(ShowSubSet showSubSet, Show show) {
        addShowsHelper(showSubSet);

        testRemovalGivenElementHelper(showSubSet, show);
    }

    private void addShowsHelper(ShowSubSet showSubSet) {
        for (int i = 1; i < 4; i++) {
            Show show = new Show(i + 40, "newTestShow", "testGenere", "testActors", "testDirectors", "testSummary", 0, 0, "test2");
            if(showSubSet == ShowSubSet.PTWSHOWS){
                show = new PrioritizedShow(show, i);
            }else if(showSubSet == ShowSubSet.WATCHEDSHOWS){
                show = new RatedShow(show, 1);
            }
            dataAccess.addShow(show, showSubSet);
        }
    }

    @Test
    public void testGettingShowSubSet() {

        ArrayList<Show> showList = new ArrayList<>();
        try {
            dataAccess.getShowSubset(null, null);

            dataAccess.getShowSubset(null, ShowSubSet.AllSHOWS);
            dataAccess.getShowSubset(null, ShowSubSet.PTWSHOWS);
            dataAccess.getShowSubset(null, ShowSubSet.WATCHINGSHOWS);
            dataAccess.getShowSubset(null, ShowSubSet.WATCHEDSHOWS);

            dataAccess.getShowSubset(showList, null);

            dataAccess.getShowSubset(showList, ShowSubSet.AllSHOWS);
        } catch (Exception e) {
            assertTrue("should not throw exception when passed null parameter", true);
        }

        testGetShowHelper(ShowSubSet.PTWSHOWS, 0);

        testGetShowHelper(ShowSubSet.WATCHINGSHOWS, 0);

        testGetShowHelper(ShowSubSet.WATCHEDSHOWS, 0);

        ArrayList<Show> ptwList = new ArrayList<>();
        ArrayList<Show> watchingList = new ArrayList<>();
        ArrayList<Show> doneList = new ArrayList<>();

        dataAccess.getShowSubset(ptwList, ShowSubSet.PTWSHOWS);
        dataAccess.getShowSubset(watchingList, ShowSubSet.WATCHINGSHOWS);
        dataAccess.getShowSubset(doneList, ShowSubSet.WATCHEDSHOWS);

        assertNotSame(ptwList, watchingList);
        assertNotSame(watchingList, doneList);
        assertNotSame(ptwList, doneList);
    }

    private void testGetShowHelper(ShowSubSet showSubSet, int size) {
        ArrayList<Show> testList = new ArrayList<>();
        ArrayList<Show> showList = new ArrayList<>();

        dataAccess.getShowSubset(showList, showSubSet);

        assertNotSame(showList, testList);
    }



}
