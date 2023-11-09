package comp3350.ptw.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.ptw.objects.Show;

/**
 * Recommending Algorithm - Class that performs the recommendation logic.
 */

public abstract class RecommendingAlgorithm {

    //returns the list of shows to be recommended
    public static List<Show> recommendMovies(List<Show> movies, List<String> tags)
    {
        List<Show> result = new ArrayList<Show>();

        //if no movies completed
        if(movies==null)
            return result;

        for(Show movie : movies) {
            for(String genre : tags){
                if (movie.getGenre().equals(genre))
                    result.add(movie);//add movie
            }
        }

        return result;
    }

    //fetches data about the shows that the user has watched
    public static List<String> getData(List<Show> movies){
        List<String> result = new ArrayList<String>();

        //if no movies completed
        if(movies==null)
            return result;

        boolean inList;

        //
        for(Show movie: movies){
            inList = false;
            String genre = movie.getGenre();
            for(String compare: result){
                if(compare.equals(genre)){
                    inList = true;
                }
            }
            if(!inList)
                result.add(genre);
        }

        return result;
    }

}