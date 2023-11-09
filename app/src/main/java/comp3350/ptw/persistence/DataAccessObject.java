package comp3350.ptw.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import comp3350.ptw.business.DbReturnStrings;
import comp3350.ptw.business.ShowSubSet;
import comp3350.ptw.objects.PrioritizedShow;
import comp3350.ptw.objects.RatedShow;
import comp3350.ptw.objects.Show;

/**
 * DataAccessObject - Class that retrieves shows and lists from the database
 */

public class DataAccessObject implements DataAccess {
    private Statement sqlStatement;
    private Connection dbConnection;
    private ResultSet resultSet;

    private String dbName;
    private String dbType;


    private String cmdString;

    private String resultMessage;


    public DataAccessObject(String dbName) {
        this.dbName = dbName;
    }


    public void open(String dbName) {
        String url;

        try {
            dbType = "HSQL";
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbName;
            dbConnection = DriverManager.getConnection(url, "WATCHER", "");
            sqlStatement = dbConnection.createStatement();
        } catch (Exception e) {
            processSQLError(e);
        }

        System.out.println("Opened " + dbType + " database " + dbName);

    }

    public void close() {
        try {
            cmdString = "shutdown compact";
            resultSet = sqlStatement.executeQuery(cmdString);
            dbConnection.close();
        } catch (SQLException e) {
            processSQLError(e);
        }

        System.out.println("Closed " + dbType + " database " + dbName);

    }

    public String getAllShows(List<Show> showList) {
        resultMessage = null;
        if (showList != null) {
            try {
                cmdString = "SELECT * FROM ALLSHOWS";
                resultSet = sqlStatement.executeQuery(cmdString);
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }

            resultMessage = resultsToShowList(resultSet, showList);
        }
        return resultMessage;

    }


    //retrieves a list of shows, based on showSubSet
    public String getShowSubset(List<Show> showList, ShowSubSet showSubSet) {
        resultMessage = null;
        if (showList != null) {

            if (showSubSet == ShowSubSet.PTWSHOWS) {
                resultMessage = getPTWShows(showList);
            } else if (showSubSet == ShowSubSet.WATCHEDSHOWS) {
                resultMessage = getWatchedShows(showList);
            } else if (showSubSet == ShowSubSet.WATCHINGSHOWS) {
                resultMessage = getWatchingShows(showList);
            }
        }
        return resultMessage;
    }

    @Override
    public String addShow(Show newShow, ShowSubSet showSubSet) {
        resultMessage = null;
        if (newShow != null) {
            if (showSubSet == ShowSubSet.PTWSHOWS) {
                resultMessage = addPTW(newShow);
            } else if (showSubSet == ShowSubSet.WATCHEDSHOWS) {
                resultMessage = addWatched(newShow);
            } else if (showSubSet == ShowSubSet.WATCHINGSHOWS) {
                resultMessage = addWatching(newShow);
            }
        }
        return resultMessage;
    }

    @Override
    public String removeShow(Show toRemove, ShowSubSet showSubSet) {
        resultMessage = null;

        if (toRemove != null && showInSubSet(toRemove.getShowID(), showSubSet)) {
            try {
                cmdString = "DELETE FROM " + showSubSet.toString() + " WHERE SHOWID=" + toRemove.getShowID();
                this.resultSet = sqlStatement.executeQuery(cmdString);
                resultMessage = "successful";
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }
        }

        return resultMessage;
    }

    @Override
    public String updatePriority(Show toUpdate) {
        resultMessage = null;

        if (toUpdate != null && toUpdate.getClass() == PrioritizedShow.class) {
            int newPriority = ((PrioritizedShow) toUpdate).getPriority();
            int showID = toUpdate.getShowID();
            try {
                cmdString = "UPDATE PTWSHOWS SET PRIORITY=" + newPriority + " WHERE SHOWID=" + showID;
                resultSet = sqlStatement.executeQuery(cmdString);
                resultMessage = DbReturnStrings.UPDATE_SUCCESS.toString();
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    @Override
    public String updateRating(Show toUpdate) {
        resultMessage = null;

        if (toUpdate != null && toUpdate.getClass() == RatedShow.class) {
            float newRating = ((RatedShow) toUpdate).getRating();
            int showID = toUpdate.getShowID();
            try {
                cmdString = "UPDATE WATCHEDSHOWS SET RATING=" + newRating + " WHERE SHOWID=" + showID;
                this.resultSet = sqlStatement.executeQuery(cmdString);
                resultMessage = DbReturnStrings.UPDATE_SUCCESS.toString();
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    @Override
    public boolean showInSubSet(int showId, ShowSubSet table) {
        boolean inTable = false;
        try {
            cmdString = "SELECT SHOWID FROM " + table.toString() + " WHERE SHOWID=" + showId;
            this.resultSet = sqlStatement.executeQuery(cmdString);
            inTable = this.resultSet.next();
        } catch (SQLException e) {

        }

        return inTable;
    }

    private String getPTWShows(List<Show> showList) {
        resultMessage = null;
        if (showList != null) {
            try {
                cmdString = "SELECT ALLSHOWS.*, PTWSHOWS.PRIORITY FROM ALLSHOWS JOIN PTWSHOWS ON ALLSHOWS.SHOWID=PTWSHOWS.SHOWID ORDER BY PTWSHOWS.PRIORITY";
                this.resultSet = sqlStatement.executeQuery(cmdString);
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }
            resultMessage = resultsToPrioritizedShowList(this.resultSet, showList);
        }
        return resultMessage;
    }

    private String getWatchingShows(List<Show> showList) {
        resultMessage = null;
        if (showList != null) {
            try {
                cmdString = "SELECT * FROM ALLSHOWS WHERE SHOWID IN (SELECT SHOWID FROM WATCHINGSHOWS)";
                this.resultSet = sqlStatement.executeQuery(cmdString);
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }
            resultMessage = resultsToShowList(this.resultSet, showList);
        }
        return resultMessage;
    }

    private String getWatchedShows(List<Show> showList) {
        resultMessage = null;
        if (showList != null) {
            try {
                cmdString = "SELECT ALLSHOWS.*, WATCHEDSHOWS.RATING FROM AllSHOWS JOIN WATCHEDSHOWS ON ALLSHOWS.SHOWID=WATCHEDSHOWS.SHOWID ORDER BY WATCHEDSHOWS.RATING";
                this.resultSet = sqlStatement.executeQuery(cmdString);
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }
            resultMessage = resultsToRatedShowList(this.resultSet, showList);
        }
        return resultMessage;
    }

    private String addPTW(Show newShow) {
        resultMessage = null;
        if (newShow != null && newShow.getClass() == PrioritizedShow.class) {
            try {
                cmdString = "INSERT INTO PTWSHOWS VALUES(" + newShow.getShowID() + "," + ((PrioritizedShow) newShow).getPriority() + ")";
                this.resultSet = sqlStatement.executeQuery(cmdString);
                resultMessage = DbReturnStrings.ADD_SUCCESS.toString();
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    private String addWatching(Show newShow) {
        resultMessage = null;
        if (newShow != null) {
            try {
                cmdString = "INSERT INTO WATCHINGSHOWS VALUES(" + newShow.getShowID() + ")";
                this.resultSet = sqlStatement.executeQuery(cmdString);
                resultMessage = DbReturnStrings.ADD_SUCCESS.toString();
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    private String addWatched(Show newShow) {
        resultMessage = null;
        if (newShow != null) {
            try {
                cmdString = "INSERT INTO WATCHEDSHOWS VALUES(" + newShow.getShowID() + ", " + 0 + ")";
                this.resultSet = sqlStatement.executeQuery(cmdString);
                resultMessage = DbReturnStrings.ADD_SUCCESS.toString();
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    private String resultsToShowList(ResultSet results, List<Show> showList) {
        resultMessage = null;
        if (showList != null) {
            try {
                while (results.next()) {
                    int showID = results.getInt("SHOWID");
                    String title = results.getString("TITLE");
                    String genre = results.getString("GENRE");
                    String actors = results.getString("ACTORS");
                    String director = results.getString("DIRECTOR");
                    String summary = results.getString("SUMMARY");
                    int releaseYear = results.getInt("RELEASED");
                    int duration = results.getInt("DURATION");
                    String imgName = results.getString("IMAGE");
                    Show newShow = new Show(showID, title, genre, actors, director, summary, releaseYear, duration, imgName);
                    showList.add(newShow);
                }
                results.close();
                resultMessage = "successful";
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }

    private String resultsToPrioritizedShowList(ResultSet results, List<Show> showList) {
        resultMessage = null;
        if (showList != null) {
            try {
                while (results.next()) {
                    int showID = results.getInt("SHOWID");
                    String title = results.getString("TITLE");
                    String genre = results.getString("GENRE");
                    String actors = results.getString("ACTORS");
                    String director = results.getString("DIRECTOR");
                    String summary = results.getString("SUMMARY");
                    int releaseYear = results.getInt("RELEASED");
                    int duration = results.getInt("DURATION");
                    String imgName = results.getString("IMAGE");
                    int priority = results.getInt("PRIORITY");
                    Show newShow = new PrioritizedShow(showID, title, genre, actors, director, summary, releaseYear, duration, imgName, priority);
                    showList.add(newShow);
                }
                results.close();
                resultMessage = "successful";
            } catch (SQLException e) {
                resultMessage = processSQLError(e);
            }
        }
        return resultMessage;
    }


    private String resultsToRatedShowList(ResultSet results, List<Show> showList) {
        resultMessage = null;
        if (showList != null) {
            try {
                while (results.next()) {
                    int showID = results.getInt("SHOWID");
                    String title = results.getString("TITLE");
                    String genre = results.getString("GENRE");
                    String actors = results.getString("ACTORS");
                    String director = results.getString("DIRECTOR");
                    String summary = results.getString("SUMMARY");
                    int releaseYear = results.getInt("RELEASED");
                    int duration = results.getInt("DURATION");
                    String imgName = results.getString("IMAGE");
                    float rating = results.getFloat("RATING");
                    Show newShow = new RatedShow(showID, title, genre, actors, director, summary, releaseYear, duration, imgName, rating);
                    showList.add(newShow);
                }
                results.close();
                resultMessage = "successful";
            } catch (SQLException e) {
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
