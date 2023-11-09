package comp3350.PTW.tests.objects;

import org.junit.Test;

import comp3350.ptw.objects.Show;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class ShowTest {

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


        Show show = new Show(id, title, genre, actors, director, summary, year, duration, imageid);

        assertEquals(id, show.getShowID());
        assertEquals(title, show.getTitle());
        assertEquals(genre, show.getGenre());
        assertEquals(actors, show.getActors());
        assertEquals(summary, show.getSummary());
        assertEquals(year, show.getReleaseYear());
        assertEquals(duration, show.getDuration());
        assertEquals(imageid, show.getImageID());


        try{
            show = new Show(id, "", genre, actors, director, summary, year, duration, imageid);
            fail("fails invalid title test");
        }catch (IllegalArgumentException exception){
            assertTrue("passed invalid title tests", true);
        }

    }

    @Test
    public void testToString(){
        Show show = new Show(0, "Title", "genre","actors", "director","summary", 0, 1, "");

        String toString = "Title Category: genre Released: 0 Duration: 1 minutes";
        assertEquals(toString, show.toString());
    }

    @Test
    public void multipleWatchablesTest(){
        System.out.println("\nStarting multipleWatchablesTest");


        Show show2 = new Show(1,"The Dark Knight","Action","","","", 2008, 152, "" );
        Show show3 = new Show(2,"The Dark Knight","Action","","", "",2008, 152, "");
        Show show4 = new Show(1, "Title", "genre","","","", 0, 0 , "");

        assertFalse(show2.equals(show3));


        assertTrue(show2.equals(show4));
        assertTrue(show2.equals(show2));

        try{
            show2.equals(null);

            Object object = new Object();
            show2.equals(object);
        } catch (Exception e){
            fail("should not throw exception when passed non show object");
        }

        System.out.println("\nFinished multipleWatchablesTest");

    }


}