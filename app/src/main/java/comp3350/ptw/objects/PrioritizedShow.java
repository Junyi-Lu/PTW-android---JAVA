package comp3350.ptw.objects;

import java.io.Serializable;

/**
 * Subclass of Show object that allows for a priority to be tracked - used in the Plan to Watch list
 * for tracking the priority of Shows that a user plans to watch
 */
public class PrioritizedShow extends Show implements Serializable {
    private int priority;

    public PrioritizedShow(int id, String title, String genre, String actors, String director, String summary, int releaseYear, int duration, String imageID, int priority) {
        super(id, title, genre, actors, director, summary, releaseYear, duration, imageID);
        this.priority = priority;
    }

    public PrioritizedShow(Show base, int priority) {
        super(base.getShowID(), base.getTitle(), base.getGenre(), base.getActors(), base.getDirector(), base.getSummary(), base.getReleaseYear(), base.getDuration(), base.getImageID());
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int newPriority) {
        this.priority = newPriority;
    }

    public String toString(){
        return super.toString() +" Priority: "+ this.priority;
    }
}

