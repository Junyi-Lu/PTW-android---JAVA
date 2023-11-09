package comp3350.PTW.tests.objects;

import org.junit.Test;

import comp3350.ptw.objects.RatedShow;
import comp3350.ptw.objects.Show;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RatedShowTest {

    @Test
    public void testRetention(){
        int id = 0;
        String title = "title";
        String genre = "genre";
        String actors = "actors";
        String director = "director";
        String summary = "summary";
        int year = 1;
        int duration = 2;
        String imageid = "imageId";
        float rating = 1;


        RatedShow rShow = new RatedShow(id, title, genre, actors, director, summary, year, duration, imageid, rating);


        assertEquals(id, rShow.getShowID());
        assertEquals(title, rShow.getTitle());
        assertEquals(genre, rShow.getGenre());
        assertEquals(actors, rShow.getActors());
        assertEquals(summary, rShow.getSummary());
        assertEquals(year, rShow.getReleaseYear());
        assertEquals(duration, rShow.getDuration());
        assertEquals(imageid, rShow.getImageID());
        assertEquals(rating, rShow.getRating(), .0);

        Show show = new Show(id, title, genre, actors, director, summary, year, duration, imageid);
        rShow = null;
        assertNull(rShow);

        rShow = new RatedShow(show, rating);

        assertEquals(id, rShow.getShowID());
        assertEquals(title, rShow.getTitle());
        assertEquals(genre, rShow.getGenre());
        assertEquals(actors, rShow.getActors());
        assertEquals(summary, rShow.getSummary());
        assertEquals(year, rShow.getReleaseYear());
        assertEquals(duration, rShow.getDuration());
        assertEquals(imageid, rShow.getImageID());
        assertEquals(rating, rShow.getRating(), .0);

        try{
            rShow = new RatedShow(id, "", genre, actors, director, summary, year, duration, imageid, rating);
            fail("fails invalid title test");
        }catch (IllegalArgumentException exception){
            assertTrue("passed invalid title tests", true);
        }
    }

    @Test
    public void testRating(){
        int id = 0;
        String title = "title";
        String genre = "genre";
        String actors = "actors";
        String director = "director";
        String summary = "summary";
        int year = 1;
        int duration = 2;
        String imageid = "imageId";
        int rating = 1;


        RatedShow show = new RatedShow(id, title, genre, actors, director, summary, year, duration, imageid, rating);

        show.setRating((float) 2.0);
        assertEquals((float) 2.0, show.getRating(), .0);
    }

}
