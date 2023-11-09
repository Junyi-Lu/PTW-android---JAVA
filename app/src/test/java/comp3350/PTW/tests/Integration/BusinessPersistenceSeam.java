package comp3350.PTW.tests.Integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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
import comp3350.ptw.persistence.DataAccess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BusinessPersistenceSeam {
    private static DataAccess dataAccess;
    private static UIShowAccess showAccess;

    private Boolean preInvariantSetUp = false;
    private ArrayList<Show> allShows = new ArrayList<>();
    private ArrayList<Show> ptwShows = new ArrayList<>();
    private ArrayList<Show> watchingShows = new ArrayList<>();
    private ArrayList<Show> watchedShows = new ArrayList<>();

    @BeforeClass
    public static void beforeClass(){
        System.out.println("Open");
        //dataAccess = DataServices.createDataAccess("testDB");//this is probably not the right constructor for a test DB

        //dataAccess = new DataAccessObject(Main.dbName);
        //dataAccess.open(Main.getDBPath());
        dataAccess = new DataAccessStub("testDB");
        dataAccess.open("testDB");
        showAccess = new UIShowAccess(dataAccess);
    }

    @AfterClass
    public static void afterClass(){
        dataAccess.close();
        System.out.println("close");
    }


    @Test
    public void testAdd(){
        Show show = new Show(0, "title", "genre", "actors", "director", "summary", 0, 0, "imageId");
        String message;
        try{
            ListInvariantPre();
            message = showAccess.add(null, null);
            assertEquals(null, message);
            ListInvariantPost(null);

            ListInvariantPre();
            message = showAccess.add(null, ShowSubSet.AllSHOWS);
            assertEquals(null, message);
            ListInvariantPost(null);

            ListInvariantPre();
            message = showAccess.add(null, ShowSubSet.PTWSHOWS);
            assertEquals(null, message);
            ListInvariantPost(null);

            ListInvariantPre();
            message = showAccess.add(null, ShowSubSet.WATCHINGSHOWS);
            assertEquals(null, message);
            ListInvariantPost(null);

            ListInvariantPre();
            message = showAccess.add(null, ShowSubSet.WATCHEDSHOWS);
            assertEquals(null, message);
            ListInvariantPost(null);

            ListInvariantPre();
            message = showAccess.add(show, null);
            assertEquals(null, message);
            ListInvariantPost(null);
        }catch (Exception e){

            fail("should not throw exception when passed a null parameter");
        }

        ListInvariantPre();
        message = showAccess.add(show, ShowSubSet.AllSHOWS);
        assertEquals("Illegal ShowSubSet for this operation." , message);
        ListInvariantPost(null);

        testSuccessfulAdd(ShowSubSet.PTWSHOWS, show);
        Show watchingShow = new Show(1, "title", "genre", "actors", "director", "summary", 0, 0, "imageId");
        testSuccessfulAdd(ShowSubSet.WATCHINGSHOWS, watchingShow);
        Show watchedShow = new Show(2, "title", "genre", "actors", "director", "summary", 0, 0, "imageId");
        testSuccessfulAdd(ShowSubSet.WATCHEDSHOWS, watchedShow);

        testDuplicateAdd(ShowSubSet.PTWSHOWS, show);
        testDuplicateAdd(ShowSubSet.WATCHINGSHOWS, watchingShow);
        testDuplicateAdd(ShowSubSet.WATCHEDSHOWS, watchedShow);

        testMultiAdd(ShowSubSet.PTWSHOWS);
        testMultiAdd(ShowSubSet.WATCHINGSHOWS);
        testMultiAdd(ShowSubSet.WATCHEDSHOWS);

    }

    @Test
    public void testRemove(){
        Show show = new Show(-1, "title", "genre", "actors", "director", "summary", 0, 0, "imageId");
        String message;
        try{
            ListInvariantPre();
            message = showAccess.remove(null, null);
            assertEquals(null, message);
            ListInvariantPost(null);

            ListInvariantPre();
            message = showAccess.remove(null, ShowSubSet.AllSHOWS);
            assertEquals(null, message);
            ListInvariantPost(null);

            ListInvariantPre();
            message = showAccess.remove(null, ShowSubSet.PTWSHOWS);
            assertEquals(null, message);
            ListInvariantPost(null);

            ListInvariantPre();
            message = showAccess.remove(null, ShowSubSet.WATCHINGSHOWS);
            assertEquals(null, message);
            ListInvariantPost(null);

            ListInvariantPre();
            message = showAccess.remove(null, ShowSubSet.WATCHEDSHOWS);
            assertEquals(null, message);
            ListInvariantPost(null);

            ListInvariantPre();
            message = showAccess.remove(show, null);
            assertEquals(null, message);
            ListInvariantPost(null);

        }catch (Exception e){

            fail("should not throw exception when passed a null parameter");
        }

        ListInvariantPre();
        message = showAccess.remove(show, ShowSubSet.AllSHOWS);
        assertEquals("Illegal ShowSubSet for this operation." , message);
        ListInvariantPost(null);

        testRemoveOutShow(ShowSubSet.PTWSHOWS);
        testRemoveOutShow(ShowSubSet.WATCHINGSHOWS);
        testRemoveOutShow(ShowSubSet.WATCHEDSHOWS);

        testRemoveShow(ShowSubSet.PTWSHOWS);
        testRemoveShow(ShowSubSet.WATCHINGSHOWS);
        testRemoveShow(ShowSubSet.WATCHEDSHOWS);
    }


    @Test
    public void testGetAll(){
        ArrayList<Show> testShows = new ArrayList<>();

        dataAccess.getAllShows(allShows);

        showAccess.getAll(testShows, ShowSubSet.AllSHOWS);
        assertEquals(allShows, testShows);

        dataAccess.getShowSubset(ptwShows, ShowSubSet.PTWSHOWS);
        showAccess.getAll(testShows, ShowSubSet.PTWSHOWS);
        assertEquals(ptwShows, testShows);

        dataAccess.getShowSubset(watchingShows, ShowSubSet.WATCHINGSHOWS);
        showAccess.getAll(testShows, ShowSubSet.WATCHINGSHOWS);
        assertEquals(watchingShows, testShows);

        dataAccess.getShowSubset(watchedShows, ShowSubSet.WATCHEDSHOWS);
        showAccess.getAll(testShows, ShowSubSet.WATCHEDSHOWS);
        assertEquals(watchedShows, testShows);
    }


    @Test
    public void testPriority(){
        try {
            showAccess.updatePriority(null);
        }catch (Exception e) {
            fail("Should not throw exception when passed null parameter");
        }

        Show showOne = new Show(-21, "showOne", "testGenre","testActor","testDirector","testSummary",  0, 0, "test");
        Show showTwo = new Show(-22, "ShowTwo", "testGenre","testActor","testDirector","testSummary",  0, 0, "test");
        Show showThree = new Show(-23, "ShowThree", "testGenre","testActor","testDirector","testSummary",  0, 0, "test");
        Show showFour = new Show(-24, "ShowFour", "testGenre","testActor","testDirector","testSummary",  0, 0, "test");
        Show showFive = new Show(-25, "ShowFive", "testGenre","testActor","testDirector","testSummary",  0, 0, "test");

        List<Show> ptwList = new ArrayList<>();
        dataAccess.getShowSubset(ptwList,ShowSubSet.PTWSHOWS);
        System.out.println("PTWList Size before add: "+ ptwList.size());
        int sizePre = ptwList.size();

        showAccess.add(showOne,ShowSubSet.PTWSHOWS);
        showAccess.add(showTwo,ShowSubSet.PTWSHOWS);
        showAccess.add(showThree,ShowSubSet.PTWSHOWS);
        showAccess.add(showFour,ShowSubSet.PTWSHOWS);
        showAccess.add(showFive,ShowSubSet.PTWSHOWS);

        ptwList = new ArrayList<>();
        dataAccess.getShowSubset(ptwList,ShowSubSet.PTWSHOWS);
        System.out.println("PTW List after adding 5 shows:");
        for (int i=0; i<ptwList.size();i++){
            System.out.println(ptwList.get(i).toString());
        }
        PrioritizedShow showOneP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showOne));
        PrioritizedShow showTwoP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showTwo));
        PrioritizedShow showThreeP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showThree));
        PrioritizedShow showFourP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFour));
        PrioritizedShow showFiveP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFive));

        assertEquals(1 + sizePre,showOneP.getPriority());
        assertEquals(2 + sizePre,showTwoP.getPriority());
        assertEquals(3 + sizePre,showThreeP.getPriority());
        assertEquals(4 +sizePre,showFourP.getPriority());
        assertEquals(5 +sizePre,showFiveP.getPriority());

        showOneP.setPriority(5 + sizePre);

        showAccess.updatePriority(showOneP);

        dataAccess.getShowSubset(ptwList,ShowSubSet.PTWSHOWS);
        showOneP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showOne));
        showTwoP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showTwo));
        showThreeP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showThree));
        showFourP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFour));
        showFiveP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFive));

        assertEquals(1 + sizePre,showTwoP.getPriority());
        assertEquals(2 + sizePre,showThreeP.getPriority());
        assertEquals(3 + sizePre,showFourP.getPriority());
        assertEquals(4 + sizePre,showFiveP.getPriority());
        assertEquals(5 + sizePre,showOneP.getPriority());

        showFourP.setPriority(1 + sizePre);

        showAccess.updatePriority(showFourP);

        dataAccess.getShowSubset(ptwList,ShowSubSet.PTWSHOWS);
        showOneP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showOne));
        showTwoP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showTwo));
        showThreeP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showThree));
        showFourP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFour));
        showFiveP = (PrioritizedShow) ptwList.get(ptwList.indexOf(showFive));

        assertEquals(1 + sizePre,showFourP.getPriority());
        assertEquals(2 + sizePre,showTwoP.getPriority());
        assertEquals(3 + sizePre,showThreeP.getPriority());
        assertEquals(4 + sizePre,showFiveP.getPriority());
        assertEquals(5 + sizePre,showOneP.getPriority());


    }


    @Test
    public void testRating(){
           try {
            showAccess.updateRating(null);
        } catch (Exception e) {
            fail("Should not throw exception when passed null parameter");
        }
        Show showOne = new Show(-21, "showOne", "testGenre", "testActor", "testDirector", "testSummary", 0, 0, "test");
        showAccess.add(showOne, ShowSubSet.WATCHEDSHOWS);
        List<Show> testList = new ArrayList<>();
        showAccess.getAll(testList, ShowSubSet.WATCHEDSHOWS);
        if (testList.get(0) instanceof RatedShow) {
            assertEquals(0, ((RatedShow) testList.get(0)).getRating(), 0.0);
            ((RatedShow) testList.get(0)).setRating(5);
            showAccess.updateRating(testList.get(0));
            assertEquals(5, ((RatedShow) testList.get(0)).getRating(), 0.0);
        }
    }

    @Test
    public void testShowInSubset(){
        Show show1 = new PrioritizedShow(0, "title", "genre", "actors", "director", "summary", 0, 0, "imageId", 1);
        Show show2 = new Show(1, "title", "genre", "actors", "director", "summary", 0, 0, "imageId");
        Show show3 = new RatedShow(2, "title", "genre", "actors", "director", "summary", 0, 0, "imageId", 1);
        showAccess.add(show1, ShowSubSet.PTWSHOWS);
        showAccess.add(show3, ShowSubSet.WATCHEDSHOWS);
        showAccess.add(show2, ShowSubSet.WATCHINGSHOWS);

        assertTrue(showAccess.showInSubSet(show1, ShowSubSet.PTWSHOWS));
        assertTrue(showAccess.showInSubSet(show3, ShowSubSet.WATCHEDSHOWS));
        assertTrue(showAccess.showInSubSet(show2, ShowSubSet.WATCHINGSHOWS));

        assertFalse(showAccess.showInSubSet(show2, ShowSubSet.PTWSHOWS));
        assertFalse(showAccess.showInSubSet(show3, ShowSubSet.WATCHINGSHOWS));
        assertFalse(showAccess.showInSubSet(show1, ShowSubSet.WATCHEDSHOWS));

        showAccess.add(show1, ShowSubSet.PTWSHOWS);
        showAccess.add(show3, ShowSubSet.WATCHEDSHOWS);
        showAccess.add(show2, ShowSubSet.WATCHINGSHOWS);

        showAccess.remove(show1, ShowSubSet.PTWSHOWS);
        showAccess.remove(show3, ShowSubSet.WATCHEDSHOWS);
        showAccess.remove(show2, ShowSubSet.WATCHINGSHOWS);

        //check the duplicated data for showInSubSet
        assertFalse(showAccess.showInSubSet(show1, ShowSubSet.PTWSHOWS));
        assertFalse(showAccess.showInSubSet(show2, ShowSubSet.WATCHINGSHOWS));
        assertFalse(showAccess.showInSubSet(show3, ShowSubSet.WATCHEDSHOWS));
    }


    private void ListInvariantPre(){
        dataAccess.getShowSubset(allShows, ShowSubSet.AllSHOWS);
        dataAccess.getShowSubset(ptwShows, ShowSubSet.PTWSHOWS);
        dataAccess.getShowSubset(watchingShows, ShowSubSet.WATCHINGSHOWS);
        dataAccess.getShowSubset(watchedShows, ShowSubSet.WATCHEDSHOWS);

        preInvariantSetUp = true;
    }

    //pass the subset that has changed, if none have changed pass null
    private void ListInvariantPost(ShowSubSet subSet){
        if(preInvariantSetUp){
            if(subSet != ShowSubSet.AllSHOWS){
                testInvariant(allShows, ShowSubSet.AllSHOWS);
            }

            if(subSet != ShowSubSet.PTWSHOWS){
                testInvariant(ptwShows, ShowSubSet.PTWSHOWS);
            }

            if(subSet != ShowSubSet.WATCHINGSHOWS){
                testInvariant(watchingShows, ShowSubSet.WATCHINGSHOWS);
            }

            if(subSet != ShowSubSet.WATCHEDSHOWS){
                testInvariant(watchedShows, ShowSubSet.WATCHEDSHOWS);
            }

        }else{
            fail("must call DBInvariantPre before DBInvariantPost");
        }

        preInvariantSetUp = false;
    }

    private void testInvariant(ArrayList<Show> preList, ShowSubSet subSet){
        ArrayList<Show> postList = new ArrayList<>();
        dataAccess.getShowSubset(postList, subSet);

        assertEquals(preList, postList);
        assertTrue(preList.containsAll(postList));
        assertTrue(postList.containsAll(preList));

        for(int i = 0; i< preList.size(); i++){
            assertEquals(preList.get(i), postList.get(i));
        }
        preList.clear();
    }

    private void testAddedShow(ArrayList<Show> preList, ShowSubSet subSet, Show newShow){

        ArrayList<Show> postList = new ArrayList<>();
        dataAccess.getShowSubset(postList, subSet);

        assertTrue(postList.containsAll(preList));
        assertFalse(preList.containsAll(postList));

        assertTrue(postList.contains(newShow));
    }

    private void testSuccessfulAdd(ShowSubSet subSet, Show show){
        ArrayList<Show> preList= new ArrayList<>();
        showAccess.getAll(preList, subSet);

        ListInvariantPre();
        assertFalse(preList.contains(show));
        String message = showAccess.add(show, subSet);
        assertEquals("Show successfully added to list.", message);
        ListInvariantPost(subSet);
        testAddedShow(preList, subSet, show);
    }

    private void testDuplicateAdd(ShowSubSet subSet, Show show){
        ArrayList<Show> preList= new ArrayList<>();
        showAccess.getAll(preList, subSet);
        ListInvariantPre();
        assertTrue(preList.contains(show));
        String message = showAccess.add(show, subSet);
        assertEquals(DbReturnStrings.IN_LIST.toString(), message);
        ListInvariantPost(null);
    }

    private void testMultiAdd(ShowSubSet subSet){
        ArrayList<Show> preList= new ArrayList<>();
        showAccess.getAll(preList, subSet);
        ArrayList<Show> showList = new ArrayList<>();

        for(int i = 0; i< 5; i++){
            Show show = new  Show(100 + i, "title", "genre", "actors", "director", "summary", 0, 0, "imageId");
            showList.add(show);
        }

        for(int i = 0; i < showList.size(); i++){
            ListInvariantPre();

            assertFalse(preList.contains(showList.get(i)));

            String message = showAccess.add(showList.get(i), subSet);
            assertEquals("Show successfully added to list.", message);

            ListInvariantPost(subSet);

            testAddedShow(preList, subSet, showList.get(i));
        }

    }

    private void testRemoveShow(ShowSubSet subSet){
        ArrayList<Show> preList= new ArrayList<>();

        Show show = new Show(0, "title", "genre", "actors", "director", "summary", 0, 0, "imageId");
        if(subSet == ShowSubSet.PTWSHOWS){
            show = new PrioritizedShow(show, 1);
        }else if(subSet == ShowSubSet.WATCHEDSHOWS){
            show = new RatedShow(show, 1);
        }

        showAccess.add(show, subSet);
        showAccess.getAll(preList, subSet);
        assertTrue(showAccess.showInSubSet(show, subSet));

        ListInvariantPre();
        String message = showAccess.remove(show, subSet);

        assertEquals(DbReturnStrings.REMOVE_SUCCESS.toString(), message);
        ListInvariantPost(subSet);
        testRemovedShow(preList, subSet, show);
    }

    private void testRemovedShow(ArrayList<Show> preList, ShowSubSet subSet, Show show){
        ArrayList<Show> postList = new ArrayList<>();
        dataAccess.getShowSubset(postList, subSet);

        assertFalse(postList.containsAll(preList));
        assertTrue(preList.containsAll(postList));

        assertFalse(postList.contains(show));
    }

    private void testRemoveOutShow(ShowSubSet subSet){
        Show show = new Show(-1, "title", "genre", "actors", "director", "summary", 0, 0, "imageId");
        if(subSet == ShowSubSet.PTWSHOWS){
            show = new PrioritizedShow(show, 1);
        }else if(subSet == ShowSubSet.WATCHEDSHOWS){
            show = new RatedShow(show, 1);
        }
        ListInvariantPre();
        String message = showAccess.remove(show, subSet);
        assertEquals(DbReturnStrings.NOT_IN_LIST.toString(), message);
        ListInvariantPost(null);
    }
}
