package comp3350.PTW.tests.business;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import comp3350.ptw.business.SearchAlgorithm;
import comp3350.ptw.objects.Show;

/*
    SearchTest class contains the required unit tests for the domain-specific SearchAlgorithm
    SearchAlgorithm is part of the business package and implements the search functionality,
    which is tested here.
 */
public class SearchTest extends TestCase{
    private List<Show> movieList;
    private List<Show> sortedList;

    @Test
    public void testEmptyList()
    {
        movieList = new ArrayList<Show>();

        //test with empty search string
        sortedList = SearchAlgorithm.searchMovies((movieList), "");
        assertEquals(movieList, sortedList);

        //test with non-empty search string
        sortedList = SearchAlgorithm.searchMovies((movieList), "not empty");
        assertEquals(movieList, sortedList);
    }

    @Test
    public void testNull()
    {
        movieList = new ArrayList<Show>();
        movieList.add(new Show(1,"The Shawshank Redemption", "Drama","testActors","testDirector","testSummary", 1994, 142, "shawshank_redemption"));
        movieList.add(new Show(2,"The Godfather", "Drama","testActors", "testDirector","testSummary", 1972, 175, "godfather"));
        movieList.add(new Show(3,"The Godfather: Part II", "Drama","testActors", "testDirector","testSummary",1974, 202, "godfather2"));

        //returns empty list if list is null
        assertEquals(0, SearchAlgorithm.searchMovies(null, "").size());

        //null phrase is treated as an empty string
        assertEquals(SearchAlgorithm.searchMovies(movieList, null), SearchAlgorithm.searchMovies(movieList, ""));

        //returns empty list if both list and phrase are null
        assertEquals(0, SearchAlgorithm.searchMovies(null, null).size());
    }

    @Test
    public void testSearchResults()
    {
        movieList = new ArrayList<Show>();

        Show pulpfiction = new Show(4,"Pulp Fiction","Drama","testActors", "testDirector","testSummary", 1994, 154, "pulp_fiction");
        Show godfather = new Show(5,"The Godfather", "Drama","testDirector","testActors", "testSummary", 1972, 175, "godfather");
        Show godfather2 = new Show(6,"The Godfather: Part II", "Drama","testActors","testDirector","testSummary", 1974, 202,"godfather2");
        Show shawshank = new Show(7,"The Shawshank Redemption", "Drama","testActors", "testDirector", "testSummary", 1994, 142, "shawshank_redemption");

        movieList.add(pulpfiction);
        movieList.add(godfather);
        movieList.add(godfather2);
        movieList.add(shawshank);

        //searching for "The Shawshank Redemption" should return a list containing only that movie
        assertEquals(1, SearchAlgorithm.searchMovies(movieList, "The Shawshank Redemption").size() ); //list should be of size 1
        assertEquals(shawshank, SearchAlgorithm.searchMovies(movieList, "The Shawshank Redemption").get(0));

        //searching for "godfather" should return a list with only The Godfather and The Godfather: Part II
        assertEquals(2, SearchAlgorithm.searchMovies(movieList, "Godfather").size() );

        //"The Godfather" should come before "The Godfather : Part II"
        assertEquals(godfather, SearchAlgorithm.searchMovies(movieList, "Godfather").get(0));
        assertEquals(godfather2, SearchAlgorithm.searchMovies(movieList, "Godfather").get(1));

        //testing uppercase and lowercase
        assertEquals(SearchAlgorithm.searchMovies(movieList, "godfather"), SearchAlgorithm.searchMovies(movieList, "GODFATHER"));
        assertEquals(godfather, SearchAlgorithm.searchMovies(movieList, "GODFATHER").get(0));
        assertEquals(godfather2, SearchAlgorithm.searchMovies(movieList, "GoDfAtHeR").get(1));

        //searching an empty string should return the original list
        assertEquals(movieList, SearchAlgorithm.searchMovies(movieList, ""));

        //searching a string that no movie title contains should return an empty list
        assertEquals(0, SearchAlgorithm.searchMovies(movieList, "No movie has this in its title").size());
    }

    @Test
    public void testLevenshtein()
    {
        //test empty
        assertEquals(0, SearchAlgorithm.computeLevenshteinDistance("", ""));
        assertEquals(0, SearchAlgorithm.computeLevenshteinDistance(null, null));

        //test small differences
        assertEquals(1, SearchAlgorithm.computeLevenshteinDistance("", "a"));
        assertEquals(1, SearchAlgorithm.computeLevenshteinDistance("a", "aa"));
        assertEquals(1, SearchAlgorithm.computeLevenshteinDistance("a", "b"));
        assertEquals(1, SearchAlgorithm.computeLevenshteinDistance("a", "A"));

        //test larger differences
        assertEquals(5, SearchAlgorithm.computeLevenshteinDistance("abcde", "fghij"));
        assertEquals(4, SearchAlgorithm.computeLevenshteinDistance("abcde", "b"));
        assertEquals(5, SearchAlgorithm.computeLevenshteinDistance("ABCDE", "abcde"));
    }
}