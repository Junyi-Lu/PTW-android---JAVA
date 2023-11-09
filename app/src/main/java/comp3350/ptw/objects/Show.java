package comp3350.ptw.objects;

import java.io.Serializable;

/**
 * Show Object Class. A 'Show' Object contains the information about a movie
 * This object contains information pertaining to the fields listed below
 */
public class Show implements Serializable {

    //Show Object variables
    private int showID;
    private String title;
    private String genre;
    private String actors;
    private String summary;
    private int releaseYear;
    private int duration;
    private String imageID;
    private String director;


    //Show constructor which expects some additional details to be passed as parameters
    public Show(int id, String title, String genre, String actors, String director, String summary, int releaseYear, int duration, String imageID) {
        this.showID = id;
        this.title = title;
        this.genre = genre;
        this.actors = actors;
        this.director = director;
        this.summary = summary;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.imageID = imageID;

        if (getTitle() != null && title.trim().length() <= 0)//validation check
            throw new IllegalArgumentException("Title cannot be empty");
    }


    //Getters and Setters for Show
    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getDuration() {
        return duration;
    }

    public String getImageID() {
        return imageID;
    }

    public int getShowID() {
        return this.showID;
    }


    public String getActors() {
        return this.actors;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getDirector() {
        return director;
    }

    //toString for Show
    public String toString() {
        return this.title + " Category: " + this.genre + " Released: " + this.releaseYear + " Duration: " + this.duration + " minutes";
    }

    //equals comparison method for two Show objects
    public boolean equals(Object object) {
        Show show;
        boolean result = false;

        if (object instanceof Show) {
            show = (Show) object;
            if (show.showID == this.showID) {
                result = true;
            }
        }
        return result;
    }


}
