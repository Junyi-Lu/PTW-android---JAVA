package comp3350.PTW.tests.objects;

import org.junit.Test;

import comp3350.ptw.objects.PrioritizedShow;
import comp3350.ptw.objects.Show;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class PrioritizedShowTest {

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
        int priority = 1;


        PrioritizedShow pShow = new PrioritizedShow(id, title, genre, actors, director, summary, year, duration, imageid, priority);


        assertEquals(id, pShow.getShowID());
        assertEquals(title, pShow.getTitle());
        assertEquals(genre, pShow.getGenre());
        assertEquals(actors, pShow.getActors());
        assertEquals(summary, pShow.getSummary());
        assertEquals(year, pShow.getReleaseYear());
        assertEquals(duration, pShow.getDuration());
        assertEquals(imageid, pShow.getImageID());
        assertEquals(priority, pShow.getPriority());


        Show show = new Show(id, title, genre, actors, director, summary, year, duration, imageid);
        pShow = null;
        assertNull(pShow);

        pShow = new PrioritizedShow(show, priority);

        assertEquals(id, pShow.getShowID());
        assertEquals(title, pShow.getTitle());
        assertEquals(genre, pShow.getGenre());
        assertEquals(actors, pShow.getActors());
        assertEquals(summary, pShow.getSummary());
        assertEquals(year, pShow.getReleaseYear());
        assertEquals(duration, pShow.getDuration());
        assertEquals(imageid, pShow.getImageID());
        assertEquals(priority, pShow.getPriority());


        try{
            show = new PrioritizedShow(id, "", genre, actors, director, summary, year, duration, imageid, priority);
            fail("fails invalid title test");
        }catch (IllegalArgumentException exception){
            assertTrue("passed invalid title tests", true);
        }
    }

    @Test
    public void testPriority(){
        int id = 0;
        String title = "title";
        String genre = "genre";
        String actors = "actors";
        String director = "director";
        String summary = "summary";
        int year = 1;
        int duration = 2;
        String imageid = "imageId";
        int priority = 1;


        PrioritizedShow show = new PrioritizedShow(id, title, genre, actors, director, summary, year, duration, imageid, priority);

        show.setPriority(2);
        assertEquals(2, show.getPriority());
    }

    @Test
    public void testToString(){
        PrioritizedShow show = new PrioritizedShow(0, "Title", "genre","actors", "director","summary", 0, 1, "", 1);

        String toString = "Title Category: genre Released: 0 Duration: 1 minutes Priority: 1";
        assertEquals(toString, show.toString());
    }

}
