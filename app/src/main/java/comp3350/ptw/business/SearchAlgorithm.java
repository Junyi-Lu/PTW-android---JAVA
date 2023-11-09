package comp3350.ptw.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp3350.ptw.objects.Show;

/**
 * SearchAlgorithm - class that performs the search logic
 */

public abstract class SearchAlgorithm {

    //returns the list after search is performed
    public static List<Show> searchMovies(List<Show> movies, String phrase)
    {
        List<Show> result = new ArrayList<Show>();
        Map<Integer, Show> dict = new HashMap<Integer, Show>();

        if(movies==null)
            return result;
        if(phrase==null || phrase.length()==0)
            return movies;

        //only keep strings containing the search phrase, and match with levenshtein score
        for(Show movie : movies)
            if(movie.getTitle().toUpperCase().contains(phrase.toUpperCase()))
                dict.put(computeLevenshteinDistance(phrase.toUpperCase(), movie.getTitle().toUpperCase()), movie);

        //sorting by levenshtein score
        while(!dict.isEmpty())
        {
            Map.Entry<Integer, Show> lowest = null;

            for(Map.Entry<Integer, Show> m : dict.entrySet())
                if(lowest==null || lowest.getKey().intValue() > m.getKey().intValue())
                    lowest = m;

            result.add(lowest.getValue());
            dict.remove(lowest.getKey());
        }

        return result;
    }

    //function from https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
    //calculates the "difference" between two strings
    public static int computeLevenshteinDistance(String lhs, String rhs) {
        if(lhs == null)
            lhs = "";
        if(rhs == null)
            rhs = "";

        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

        for (int i = 0; i <= lhs.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= rhs.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= lhs.length(); i++)
            for (int j = 1; j <= rhs.length(); j++)
                distance[i][j] = Math.min(
                        distance[i - 1][j] + 1,
                        Math.min(distance[i][j - 1] + 1,
                                distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1)));

        return distance[lhs.length()][rhs.length()];
    }
}