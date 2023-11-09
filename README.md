# COMP3350_Group7_PTW

# Plan To Watch App

Group 7
> Jacob Broggy
> 
> Colin Lee
> 
> Junyi Lu
> 
> Mike Kolisnyk
> 
> Owen Hnylycia

# Iteration 3

REPO URL: https://github.com/kolisnykm/COMP3350_Group7_PTW/

 # Description & User Stories

For our 3rd and final iteration we have tried to incorporate most of our remaining big user stories, as well as prepare as many tests as possible. Some big new features include a star rating system for any shows you have marked as 'Watched' (completed), as well as a algorithm that processes some data to recommend more shows to you and last but not least, a details page to view detailed info about the movies. We did our best to manage our time and complete all the tasks that we had originally set out to do, using the remaining time to ensure all of our tests were well written and diverse. Below is a list of our Big user stories and dev tasks that follow.

*note: All User stories and dev tasks are also included within the log.txt file (you will need to scroll past the iteration 1 & iteration 2 logs in order to find the iteration 3 log + stories/dev tasks)*

Big user story 5: View Show Information (6 days)

	Task 1: Create details page (4 hours)
		Create page itself (2 hours)
		Design UI (2 hours)

	Task 2: Insert data from database into details page (5 hours)
		Create synopsis section (1 hour)
		Create list of actors (2 hours)
		Extra details (2 hours)

	Task 3: Associate detail pages with individual shows (2 hours)

	Task 4: Create clickable interface button to view details page of each show (1 hour)

	Task 5: Create dropdown buttons for adding a show to any of the user’s lists via the details page (3 hours)

Big user story 6: Be Recommended Content (4 days)

	Task 1: Create ‘Suggested’ tab for recommended shows (30 minutes)

	Task 2: Create activity classes for adding shows to the suggested list (4 hours)

	Task 3: Create algorithm for deciding which shows to recommend, based on the shows that the user has already watched (aka shows in the ‘watched’ list) (3 hours)

	Task 4: connect the algorithm to the suggested tab and recommended activity (2 hours)

Big user story 7: Rate Shows (4 days)

	Task 1: Design the UI for the star system (3 hours)
	
	Task 2: Make the UI interactive/selectable by clicking on the stars (4 hours)

	Task 3: have the stars be associated with each show under the ‘Watched’ list (3 hours)

	Task 4: save the star ratings to the database (3 hours)

Write in-depth Unit tests, Acceptance tests and Integration tests (4 days)

	Unit tests: (8 hours)
	
	Acceptance tests: (6 hours)
	
	Integration tests: (5 hours)

# Running the Project

It is essential for building + running our app that you have the same Gradle + SDK + JDK versions.
- JDK Version: jdk-15
- SDK Version: Android 6.0 (API 23)
- Gradle Version: 7.1-rc-2
     *(Android Gradle Plugin Version: 4.2.1)*
     
# Feature Overview

As mentioned in the iteration 3 description, our main features for this iteration are the Show details page, the star rating system and the recommending algorithm. Here is an overview of all features, particularly the newly added ones:

- Add shows via the 'quick-add' button, which will immediately add the show to the next list. For example, if a show is added from the 'Browse' tab, it will go straight into the user's 'Plan to Watch' tab. Shows that are quick-added from the Plan to Watch will go to the 'Watching', and shows quick-added from Watching will go into 'Watched' (completed)
- If you want to select which list to put a show into, this can be done under the details page of the show. Once the 'details' button is pressed for a show, you can select the blue '+' (add) icon, which will allow you to add the show to any of the lists from the selection
- The details button can be pressed to view the details page of each show, where the synopsis, list of actors and more can be viewed
- The new 'Suggested' tab allows for recommended shows to be displayed to you. Of course if you have not completed any shows (if your 'Watched' list is empty) then the suggestions list will also be empty. But based off of the shows that you have completed, the Suggested tab will display shows that are similar
- Shows can be conviently viewed by navigating between the tabs located at the top of the app. Shows in a list can be scrolled through, or the search function can be used to filter through the list being displayed in that particular tab
- Shows that have been completed can be rated based on a 1-5 star system. This can be done by navigating to the 'Watched' tab and selecting a rating for the shows

# Project Structure (Packages and Source code files)
Business
> UIShowAccess (Interacts with Data Access to add, remove and return show data)
> 
> SearchAlgorithm (implements the search functionality)
> 
>DbReturnStrings
>
>ShowSubSet
>
>RecommendingAlgorithm

Objects
>Show
>
>PrioritizedShow
>
>RatedShow

Persistence
>DataAccess (interface)
>
>DataAccessObject (implements DataAccess interface, serving as a connection to the hsqldb)

Presentation
>ContentActivity
>
>ErrorMessages
>
>HomeActivity
>
>ListItemAdapter
>
>ListUpdate
>
>PTWActivity
>
>PTWListItemAdapter
>
>SearchActivity
>
>SearchAdapter
>
>WatchedActivity
>
>WatchedListItemAdapter
>
>WatchingActivity
>
>WatchingListItemAdapter
>
>RecommendActivity
>
>RecommendListItemAdapter

-------------------
# Iteration 2 (Old)
> Everything below here is from Iteration 2
-------------------

Big user story 3: Remember Shows I Plan to Watch (4 days)
- View list of Ongoing Shows/movies
- View list of Completed shows/movies
- View Plan to Watch list
- Edit Lists

Task 2: Create separate lists for Plan To Watch, Watching and Completed (in addition to 
the Browse tab) (2 hours)

Task 3: Attach lists to each tab respectively (tab system implemented in user story 4) (3 
hours)

Task 4: Allow shows to be moved from one list to another via the add button (1 hour)

Task 5: Delete button to remove shows from a list (1 hour)

Task 6: Add check to ensure shows that are in the lists cannot be added twice, as well 
as shows that have been deleted may be re-added (2 hours)

Big user story 4: Keep Track of Shows (3days) 
- New Search within lists
- New Navigate between lists

Task 1: Finish Tab system implementation (5 hours)

Task 2: Set the search function up to work for any given list (4 hours)

Task 3: With the lists added by the previous user story, connect them to the search 
function (1 hour)

Task 4: Update the GUI to include the magnifying glass search button for each list (2 
hours)

Add Database Implementation (4 days)

Task 1: Create Persistence class + functions for accessing hsql database (3 hours)

Task 2: Follow the steps to setup the hsqldb scripts appropriately (2 hours)
	
Task 3: Transfer all data to database via the .script file (2 hours)
	
Task 4: Make necessary changes to fully connect the database + data access and 
ensure all other classes work with the database implementation (6 hours)
- Connect database + data access (2.5 hours)
- Ensure media is working correctly (using assets folder to host our images, with their data being kept in the database) (1 hour)
- Bug testing and necessary fixes across other files as needed (2.5 hours)


# Running the Project

It is essential for building + running our app that you have the same Gradle + SDK + JDK versions.
- JDK Version: jdk-15
- SDK Version: Android 6.0 (API 23)
- Gradle Version: 7.1-rc-2
     *(Android Gradle Plugin Version: 4.2.1)*
     
# Feature Overview
----
For iteration 2, our main focus was the implementation of the database and refactoring of our code. In addition to the PTW list, we have now included a Watching list and a Completed list. Also, we have implemented a tab feature as well as search bars within each tab to help users navigate their personal lists of plan-to-watch shows, ongoing and completed shows. There are some GUI improvements as well. Due to time restraints for iteration 2, further functionality and data processing will be handled during the 3rd iteration.

From the browse show tab you are able to scroll or search for a show in the list, and press the add button (represented by three bars and a +) to add that show to your Plan To Watch. Additionally, your Plan To Watch list functions the same way, and a show can be 'added' to your 'Watching' list from there. Finally, from your Watching List, any shows there can also be added to your Completed list. Another new feature is the delete button, which allows you to delete a show from any of your lists. The show will still be viewable from Browse and can be re-added. All three personal lists + the browse list are all viewable via navigating the tabs at the top of the screen, and the search feature is accessable by pressing the magnifying glass in the corner. Finally, All show data, including which list they are in, is stored using hsqldb, as required by the iteration. 

Our test suite is included under AllTests.java, and will run our JUnit test classes (and their tests).

Our log is kept and updated using Google docs. For this submission it has been converted into a log.txt file which is located in this folder. We update our log whenever we spend time working on this project or when there is a meeting.

# Project Structure (Packages and Source code files)
Business
> ShowAccess (Interacts with Data Access to add, remove and return show data)
> 
> SearchAlgorithm (implements the search functionality)

Objects
>Show

Persistence
>DataAccess (interface)
>
>DataAccessObject (implements DataAccess interface, serving as a connection to the hsqldb)

Presentation
>ErrorMessages
>
>HomeActivity
>
>ListItemAdapter
>
>ListUpdate
>
>PTWActivity
>
>PTWListItemAdapter
>
>SearchActivity
>
>SearchAdapter
>
>WatchedActivity
>
>WatchedListItemAdapter
>
>WatchingActivity
>
>WatchingListItemAdapter
>



----
# Iteration 1 (Old)
> Everything below here is from Iteration 1
----
At the moment, when you open the app you will first see the home section. This is currently a browsable, scrollable and searchable list of movies (in later iterations tv shows will be included as well). The movies contain some details about themselves such as genre and duration. As far as the search feature goes, pressing the magnifying glass in the top corner will bring up the keyboard menu, and you will be able to search for any show by title, so long as it is in the list. If you want to search for a show, you can just scroll through the list and then type in one of the names of the shows that you see there, and the search will filter by that title.

The add functionality for iteration 1 allows you to press the 'add' button under any movie and have it added to your list. Since the tab functionality has yet to be fully implemented, the only way to view your list of 'planned to watch' shows is to add another show to the list. Once a show is added to your PTW (Plan To Watch), then the app will automatically display your PTW list. We are aware that the add button does not dissapear for a show that has already been added, but this will be changed in a future iteration. To return to the browse section, the back button must be pressed.



# Project Structure (Packages and Source code files)
Business
> PTWListAccess (access to the default list)
> 
> ToWatchListAccess (access to the plane to watch list)
> 
> SearchAlgorithm (implements the search functionality on the home screen)

Objects
>Watchable 

Persistence
>DataAccess (contains stub database access in the form of private ArrayLists)

Presentation
>PTWListActivity (creates list view of the default show list)
>
>PTWListItemAdapter (creates item view for shows in the default list)
>
>ToWatchListActivity (creates list view of the planned to watch show list)
>
>ToWatchListItemAdapter (creates item view for shows in the to watch list)
