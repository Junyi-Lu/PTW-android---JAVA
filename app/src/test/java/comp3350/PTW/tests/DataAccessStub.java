package comp3350.PTW.tests;

import java.util.ArrayList;
import java.util.List;

import comp3350.ptw.business.DbReturnStrings;
import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.objects.PrioritizedShow;
import comp3350.ptw.objects.RatedShow;
import comp3350.ptw.objects.Show;
import comp3350.ptw.persistence.DataAccess;

public class DataAccessStub implements DataAccess {
    private String dbName;
    private static String dbType = "stub";
    private static final String SQLERROR = "*** SQL Error: ";
    private String resultMessage;

    private static List<Show> allShows = new ArrayList<>();
    private  List<Show> ptwShows;
    private  List<Show> watchingShows;
    private  List<Show> watchedShows;

    public DataAccessStub(String dbName) {
        this.dbName = dbName;
        ptwShows = new ArrayList<>();
        watchedShows = new ArrayList<>();
        watchingShows = new ArrayList<>();
    }


    public void open(String dbName) {
        this.dbName = dbName;

        if (allShows.isEmpty()) {
            allShows.add(new Show(0, "The Shawshank Redemption", "Drama", "Morgan Freeman, Tim Robbins, Bob Gunton", "Frank Darabont", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", 1994, 142, "shawshank_redemption"));
            allShows.add(new Show(1, "The Godfather", "Drama", "Marlon Brando, Al Pacino, James Caan", "Francis Ford Coppola", "The aging patriarch of an organized crime family transfers control of his clandestine empire to his reluctant son.", 1972, 175, "godfather"));
            allShows.add(new Show(2, "The Godfather: Part II", "Drama", "Al Pacino, Robert De Niro, Robert Duvall", "Francis Ford Coppola", "The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.", 1974, 202, "godfather2"));
            allShows.add(new Show(3, "The Dark Knight", "Action", "Christian Bale, Heath Ledger, Aaron Eckhart", "Christopher Nolan", "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.", 2008, 152, "the_dark_knight"));
            allShows.add(new Show(4, "12 Angry Men", "Drama", "Henry Fonda, Lee J. Cobb, Martin Balsam", "Sidney Lumet", "A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.", 1957, 96, "twelve_angry_men"));
            allShows.add(new Show(5, "Schindler's List", "Drama", "Liam Neeson, Ralph Fiennes, Ben Kingsley", "Steven Spielberg", "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.", 1993, 195, "schindlers_list"));
            allShows.add(new Show(6, "The Lord of the Rings: The Return of the King", "Action", "Elijah Wood, Viggo Mortensen, Ian McKeller", "Peter Jackson", "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.", 2003, 201, "lotr_rotk"));
            allShows.add(new Show(7, "Pulp Fiction", "Drama", "John Travolta, Uma Thurman, Samuel Jackson", "Quentin Tarantino", "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", 1994, 154, "pulp_fiction"));
            allShows.add(new Show(8, "Inception", "Action", "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page", "Christopher Nolan", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.", 2010, 148, "inception"));
            allShows.add(new Show(9, "The Matrix", "Action", "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss", "Lana Wachowski", "When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth--the life he knows is the elaborate deception of an evil cyber-intelligence.", 1999, 136, "the_matrix"));
            allShows.add(new Show(10, "Gladiator", "Action", "Russell Crowe, Joaquin Phoenix, Connie Neilson", "Ridley Scott", "A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.", 2000, 155, "gladiator"));
            allShows.add(new Show(11, "Back to the Future", "Comedy", "Michael J. Fox, Christopher Lloyd, Lea Thompson", "Robert Zemeckis", "Marty McFly, a 17-year-old high school student, is accidentally sent thirty years into the past in a time-traveling DeLorean invented by his close friend, the eccentric scientist Doc Brown.", 1985, 116, "back_to_the_future"));
            allShows.add(new Show(12, "The Truman Show", "Comedy", "Jim Carrey, Ed Harris, Laura Linney", "Peter Weir", "An insurance salesman discovers his whole life is actually a reality TV show.", 1998, 103, "the_truman_show"));
            allShows.add(new Show(13, "The Big Lebowski", "Comedy", "Jeff Bridges, John Goodman, Julianne Moore", "Joel Coen", "Jeff 'The Dude' Lebowski, mistaken for a millionaire of the same name, seeks restitution for his ruined rug and enlists his bowling buddies to help get it.", 1998, 117, "the_big_lebowski"));
            allShows.add(new Show(14, "Deadpool", "Comedy", "Ryan Reynolds, Morena Baccarin, T.J. Miller", "Tim Miller", "A wisecracking mercenary gets experimented on and becomes immortal but ugly, and sets out to track down the man who ruined his looks.", 2016, 108, "deadpool"));
            allShows.add(new Show(15, "Fight Club", "Drama", "Brad Pitt, Edward Norton, Meat Loaf", "David Fincher", "An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.", 1999, 139, "fight_club"));
            allShows.add(new Show(16, "Goodfellas", "Crime", "Robert De Niro, Ray Liotta, Joe Pesci", "Martin Scorsese", "The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito in the Italian-American crime syndicate.", 1990, 146, "goodfellas"));
            allShows.add(new Show(17, "Seven Samurai", "Action", "Toshiro Mifune, Takashi Shimura, Keiko Tsushima", "Akira Kurosawa", "A poor village under attack by bandits recruits seven unemployed samurai to help them defend themselves.", 1954, 207, "seven_samurai"));
            allShows.add(new Show(18, "Seven", "Crime", "Morgan Freeman, Brad Pitt, Kevin Spacey", "David Fincher", "Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his motives.", 1995, 127, "seven"));
            allShows.add(new Show(19, "Memento", "Mystery", "Guy Pearce, Carrie-Anne Moss, Joe Pantoliano", "Christopher Nolan", "A man with short-term memory loss attempts to track down his wife''s murderer.", 2000, 113, "memento"));
            allShows.add(new Show(20, "Fargo", "Crime", "William H. Macy, Frances McDormand, Steve Buscemi", "Noah Hawley", "Jerry Lundegaard's inept crime falls apart due to his and his henchmen's bungling and the persistent police work of the quite pregnant Marge Gunderson.", 1996, 98, "fargo"));
            allShows.add(new Show(21, "The Love Bug", "Comedy", "Dean Jones, Michele Lee, David Tomlinson", "Robert Stevenson", "A race car driver becomes a champion with a Volkswagen Beetle with a mind of its own.", 1968, 108, "the_love_bug"));
            allShows.add(new Show(22, "Herbie Rides Again", "Comedy", "Helen Hayes, Ken Berry, Stefanie Powers", "Robert Stevenson", "The living Volkswagen Beetle helps an old lady protect her home from a corrupt developer.", 1974, 88, "herbie_rides_again"));
            allShows.add(new Show(23, "Herbie Goes to Monte Carlo", "Action", "Dean Jones, Don Knotts, Julie Sommars", "Vincent McEveety", "Herbie, the Volkswagen Beetle with a mind of its own, is racing in the Monte Carlo Rally. Unbeknownst to Herbie's driver, thieves have hidden a cache of stolen diamonds in Herbie's gas tank, and are now trying to get them back.", 1977, 105, "herbie_monte_carlo"));
            allShows.add(new Show(24, "Herbie Goes Bananas", "Adventure", "Charles Martin Smith, Stephen W. Burns, Cloris Leachman", "Vincent McEveety", "The adorable little VW helps its owners break up a counterfeiting ring in Mexico.", 1980, 100, "herbie_bananas"));
            allShows.add(new Show(25, "Herbie Fully Loaded", "Adventure", "Lindsay Lohan, Michael Keaton, Cheryl Hines", "Angela Robinson", "Maggie Peyton, the new owner of Herbie, Number 53, the free-wheelin' Volkswagen bug with a mind of its own, puts the car through its paces on the road to becoming a NASCAR competitor.", 2005, 101, "herbie_fully_loaded"));
        }

    }

    public void close() {
        System.out.println("Closed " + dbType + " database " + dbName);
    }


    public String getAllShows(List<Show> showList) {
        resultMessage = null;
        if (showList !=null) {

            resultMessage = resultsToShowList(this.allShows, showList);
        }
        return resultMessage;
    }


    //retrieves a list of shows, based on showSubSet
    public String getShowSubset(List<Show> showList, ShowSubSet showSubSet) {
        resultMessage = null;
        if (showList !=null) {

            if (showSubSet == ShowSubSet.PTWSHOWS){
                resultMessage = getPTWShows(showList);
            }
            else if (showSubSet==ShowSubSet.WATCHEDSHOWS){
                resultMessage = getWatchedShows(showList);
            }
            else if (showSubSet == ShowSubSet.WATCHINGSHOWS){
                resultMessage = getWatchingShows(showList);
            }
        }
        return resultMessage;
    }

    @Override
    public String addShow(Show newShow, ShowSubSet showSubSet) {
        resultMessage = null;
        if (newShow !=null) {
            if (showSubSet == ShowSubSet.PTWSHOWS){
                resultMessage = addPTW(newShow);
            }
            else if (showSubSet==ShowSubSet.WATCHEDSHOWS){
                resultMessage = addWatched(newShow);
            }
            else if (showSubSet == ShowSubSet.WATCHINGSHOWS){
                resultMessage = addWatching(newShow);
            }
        }
        return resultMessage;
    }

    @Override
    public String removeShow(Show toRemove, ShowSubSet showSubSet) {
        resultMessage = null;

        if (toRemove !=null && showInSubSet(toRemove.getShowID(),showSubSet)) {
            try {
                List<Show> activeList = subSetToList(showSubSet);
                activeList.remove(toRemove);
                resultMessage = DbReturnStrings.REMOVE_SUCCESS.toString();
            } catch (Exception e) {
                resultMessage = processSQLError(e);
            }
        }else if(!showInSubSet(toRemove.getShowID(), showSubSet)){
            resultMessage = DbReturnStrings.NOT_IN_LIST.toString();
        }

        return resultMessage;
    }

    @Override
    public String updatePriority(Show toUpdate) {
        resultMessage = null;

        if (toUpdate !=null && toUpdate.getClass()== PrioritizedShow.class){

            try {
                this.ptwShows.remove(toUpdate);
                this.ptwShows.add(toUpdate);
                resultMessage = DbReturnStrings.UPDATE_SUCCESS.toString();
            } catch (Exception e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    @Override
    public String updateRating(Show toUpdate) {
        resultMessage = null;
        if (toUpdate !=null && toUpdate.getClass()== RatedShow.class){
            try {
                this.watchedShows.remove(toUpdate);
                this.watchedShows.add(toUpdate);
            } catch (Exception e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    @Override
    public boolean showInSubSet(int showId, ShowSubSet showSubSet) {
        boolean inSubSet = false;

        List<Show> activeList = new ArrayList<>();
        activeList = subSetToList(showSubSet);
        int i = 0;
        while (i < activeList.size() && !inSubSet){
            if (activeList.get(i).getShowID() == showId){
                inSubSet = true;
            }
            i++;
        }
        return inSubSet;
    }

    private List<Show> subSetToList(ShowSubSet showSubSet) {
        List<Show> activeList = new ArrayList<>();
        switch (showSubSet) {
            case PTWSHOWS:
                activeList = this.ptwShows;
                break;
            case WATCHINGSHOWS:
                activeList = this.watchingShows;
                break;
            case WATCHEDSHOWS:
                activeList = this.watchedShows;
                break;
        }
        return activeList;
    }

    private String getPTWShows(List<Show> showList) {
        resultMessage = null;
        if (showList !=null) {

            resultMessage = resultsToPrioritizedShowList(this.ptwShows, showList);
        }
        return resultMessage;
    }

    private String getWatchingShows(List<Show> showList) {
        resultMessage = null;
        if (showList !=null) {

            resultMessage = resultsToShowList(this.watchingShows, showList);
        }
        return resultMessage;
    }
    private String getWatchedShows(List<Show> showList) {
        resultMessage = null;
        if (showList !=null) {
            resultMessage = resultsToRatedShowList(this.watchedShows, showList);
        }
        return resultMessage;
    }

    private String addPTW(Show newShow) {
        resultMessage = null;
        if (newShow != null && newShow.getClass() == PrioritizedShow.class && !ptwShows.contains(newShow)) {
            try {
                this.ptwShows.add(newShow);
                resultMessage = DbReturnStrings.ADD_SUCCESS.toString();
            } catch (Exception e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    private String addWatching(Show newShow) {
        resultMessage = null;
        if (newShow != null && !watchingShows.contains(newShow)) {
            try {
                this.watchingShows.add(newShow);
                resultMessage = DbReturnStrings.ADD_SUCCESS.toString();
            } catch (Exception e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    private String addWatched(Show newShow) {
        resultMessage = null;
        if (newShow != null && !watchedShows.contains(newShow)) {
            try {
                this.watchedShows.add(newShow);
                resultMessage = DbReturnStrings.ADD_SUCCESS.toString();
            } catch (Exception e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    private String resultsToShowList(List<Show> results, List<Show> showList){
        resultMessage = null;
        if (showList != null) {
            showList.clear();
            try {
                int i = 0;
                while (i < results.size()) {
                    int showID = results.get(i).getShowID();
                    String title = results.get(i).getTitle();
                    String genre = results.get(i).getGenre();
                    String actors = results.get(i).getActors();
                    String director = results.get(i).getDirector();
                    String summary = results.get(i).getSummary();
                    int releaseYear = results.get(i).getReleaseYear();
                    int duration = results.get(i).getDuration();
                    String imgName = results.get(i).getImageID();
                    Show newShow = new Show(showID, title, genre, actors, director, summary, releaseYear, duration, imgName);
                    showList.add(newShow);
                    i++;
                }
                resultMessage = "successful";
            } catch (Exception e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    private String resultsToPrioritizedShowList(List<Show> results, List<Show> showList){
        resultMessage = null;
        if (showList != null) {
            showList.clear();
            try {
                int i = 0;
                while (i < results.size()) {
                    int showID = results.get(i).getShowID();
                    String title = results.get(i).getTitle();
                    String genre = results.get(i).getGenre();
                    String actors = results.get(i).getActors();
                    String summary = results.get(i).getSummary();
                    String director = results.get(i).getDirector();
                    int releaseYear = results.get(i).getReleaseYear();
                    int duration = results.get(i).getDuration();
                    String imgName = results.get(i).getImageID();
                    int priority = ((PrioritizedShow)results.get(i)).getPriority();
                    Show newShow = new PrioritizedShow(showID, title, genre, actors,director, summary, releaseYear, duration, imgName, priority);
                    showList.add(newShow);
                    i++;
                }
                resultMessage = "successful";
            }catch (Exception e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }


    private String resultsToRatedShowList(List<Show> results, List<Show> showList){
        resultMessage = null;
        if (showList != null) {
            showList.clear();
            try {
                int i=0;
                while (i < results.size()) {
                    int showID = results.get(i).getShowID();
                    String title = results.get(i).getTitle();
                    String genre = results.get(i).getGenre();
                    String actors = results.get(i).getActors();
                    String director = results.get(i).getDirector();
                    String summary = results.get(i).getSummary();
                    int releaseYear = results.get(i).getReleaseYear();
                    int duration = results.get(i).getDuration();
                    String imgName = results.get(i).getImageID();
                    float rating = ((RatedShow)results.get(i)).getRating();
                    Show newShow = new RatedShow(showID, title, genre, actors,director, summary, releaseYear, duration, imgName, rating);
                    showList.add(newShow);
                    i++;
                }
                resultMessage = "successful";
            } catch (Exception e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }


    public String processSQLError(Exception e) {
        String result = "*** SQL Error: " + e.getMessage();

        e.printStackTrace();

        return result;
    }

}
