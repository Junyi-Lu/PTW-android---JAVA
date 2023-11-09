package comp3350.PTW.tests.business;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.ptw.business.RecommendingAlgorithm;
import comp3350.ptw.objects.PrioritizedShow;
import comp3350.ptw.objects.RatedShow;
import comp3350.ptw.objects.Show;

/*
    RecommendTest class contains the required unit tests for the domain-specific RecommendingAlgorithm
    This algorithm is part of the business package and implements the recommending functionality,
    which is tested here.
 */
public class RecommendTest extends TestCase{
    private List<Show> movieList;
    private List<String> tagList;
    private List<Show> resultList;

    @Test
    public void testEmptyList()
    {
        movieList = new ArrayList<Show>();
        tagList = new ArrayList<String>();

        //test with empty string list of tags and empty movie list
        resultList = RecommendingAlgorithm.recommendMovies(movieList, tagList);
        assertEquals(movieList, resultList);

        //test with non-empty tag list and empty movie list
        tagList.add("Action");
        resultList = RecommendingAlgorithm.recommendMovies((movieList), tagList);
        assertEquals(movieList, resultList);
    }

    @Test
    public void testNullList(){
        try{
            RecommendingAlgorithm.getData(null);
            RecommendingAlgorithm.recommendMovies(null, null);
        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void testGetData(){
        Show show1 = new PrioritizedShow(0, "title", "genre1", "actors", "director", "summary", 0, 0, "imageId", 1);
        Show show2 = new Show(1, "title", "genre2", "actors", "director", "summary", 0, 0, "imageId");
        Show show3 = new RatedShow(2, "title", "genre3", "actors", "director", "summary", 0, 0, "imageId", 1);
        Show show4 = new RatedShow(2, "title", "genre3", "actors", "director", "summary", 0, 0, "imageId", 1);
        Show show5 = new RatedShow(2, "title", "genre1", "actors", "director", "summary", 0, 0, "imageId", 1);

        ArrayList<Show> list = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();

        assertEquals(new ArrayList<String>(),RecommendingAlgorithm.getData(list));

        list.add(show1);
        tags.add("genre1");
        assertEquals(tags, RecommendingAlgorithm.getData(list));

        list.add(show5);
        assertEquals(tags, RecommendingAlgorithm.getData(list));

        list.add(show2);
        tags.add("genre2");
        assertEquals(tags, RecommendingAlgorithm.getData(list));

        list.add(show4);
        list.add(show3);
        tags.add("genre3");
        assertEquals(tags, RecommendingAlgorithm.getData(list));

    }

    @Test
    public void testRecommended(){
        Show show1 = new PrioritizedShow(0, "title", "genre1", "actors", "director", "summary", 0, 0, "imageId", 1);
        Show show2 = new Show(1, "title", "genre2", "actors", "director", "summary", 0, 0, "imageId");
        Show show3 = new RatedShow(2, "title", "genre3", "actors", "director", "summary", 0, 0, "imageId", 1);
        Show show4 = new RatedShow(3, "title", "genre3", "actors", "director", "summary", 0, 0, "imageId", 1);
        Show show5 = new RatedShow(4, "title", "genre1", "actors", "director", "summary", 0, 0, "imageId", 1);

        ArrayList<String> tagList = new ArrayList<>();
        ArrayList<Show> list = new ArrayList<>();
        list.add(show1);

        assertEquals(new ArrayList<>(), RecommendingAlgorithm.recommendMovies(list, tagList));

        tagList.add("genre1");
        assertEquals(list, RecommendingAlgorithm.recommendMovies(list,tagList));

        tagList.add("genre2");
        assertEquals(list, RecommendingAlgorithm.recommendMovies(list,tagList));

        list.add(show5);
        assertEquals(list, RecommendingAlgorithm.recommendMovies(list,tagList));

        list.add(show2);
        assertEquals(list, RecommendingAlgorithm.recommendMovies(list,tagList));

        list.add(show3);
        list.add(show4);
        ArrayList testList = new ArrayList();
        testList.add(show1);
        testList.add(show5);
        testList.add(show2);
        assertEquals(testList, RecommendingAlgorithm.recommendMovies(list, tagList));
    }


}