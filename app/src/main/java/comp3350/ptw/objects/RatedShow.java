package comp3350.ptw.objects;

import java.io.Serializable;

/**
 * Subclass of Show object that allows for a rating to be tracked - used in the Watched list
 * for tracking the rating of Shows that a user has watched
 */
public class RatedShow extends Show implements Serializable {
    float rating;

    public RatedShow(int id, String title, String genre, String actors, String director, String summary, int releaseYear, int duration, String imageID, float rating) {
        super(id, title, genre, actors, director, summary, releaseYear, duration, imageID);
        this.rating = rating;
    }

    public RatedShow(Show base, float rating) {
        super(base.getShowID(), base.getTitle(), base.getGenre(), base.getActors(), base.getDirector(), base.getSummary(), base.getReleaseYear(), base.getDuration(), base.getImageID());
        this.rating = rating;
    }

    public float getRating() {
        return this.rating;
    }

    public void setRating(float newRating) {
        this.rating = newRating;
    }
}