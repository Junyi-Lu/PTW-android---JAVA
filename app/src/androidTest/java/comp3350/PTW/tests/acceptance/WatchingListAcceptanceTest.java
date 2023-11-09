package comp3350.PTW.tests.acceptance;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.ptw.R;
import comp3350.ptw.presentation.HomeActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WatchingListAcceptanceTest {
    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void testAddAndDelete() {
        DataInteraction movieEntry;

        //add The Shawshank Redemption to PTW
        movieEntry = onData(anything()).inAdapterView(withId(R.id.search_list_view)).atPosition(0);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Shawshank Redemption"))));
        movieEntry.onChildView((withId(R.id.movie_add))).perform(click());

        //add The Godfather to PTW
        movieEntry = onData(anything()).inAdapterView(withId(R.id.search_list_view)).atPosition(1);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather"))));
        movieEntry.onChildView((withId(R.id.movie_add))).perform(click());

        //add The Godfather: Part 2 to PTW
        movieEntry = onData(anything()).inAdapterView(withId(R.id.search_list_view)).atPosition(2);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather: Part II"))));
        movieEntry.onChildView((withId(R.id.movie_add))).perform(click());

        //Display PTW list
        onView(allOf(childAtPosition(allOf(withId(android.R.id.tabs), childAtPosition(withClassName(is("android.widget.LinearLayout")), 0)), 2), isDisplayed())).perform(click());

        //add The Shawshank Redemption to Watching
        movieEntry = onData(anything()).inAdapterView(withId(R.id.ptw_list_view)).atPosition(0);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Shawshank Redemption"))));
        movieEntry.onChildView((withId(R.id.movie_add))).perform(click());

        //add The Godfather to Watching
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather"))));
        movieEntry.onChildView((withId(R.id.movie_add))).perform(click());

        //add The Godfather: Part 2 to Watching
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather: Part II"))));
        movieEntry.onChildView((withId(R.id.movie_add))).perform(click());


        //Display Watching list
        onView(allOf(childAtPosition(allOf(withId(android.R.id.tabs), childAtPosition(withClassName(is("android.widget.LinearLayout")), 0)), 3), isDisplayed())).perform(click());

        //check for the added movies
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Shawshank Redemption")))));
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather")))));
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather: Part II")))));


        //TEST DELETION
        //delete The Shawshank Redemption
        movieEntry = onData(anything()).inAdapterView(withId(R.id.watching_list_view)).atPosition(0);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Shawshank Redemption (1994)"))));
        movieEntry.onChildView((withId(R.id.movie_delete))).perform(click());

        //check if The Shawshank Redemption was deleted
        onView(withId(R.id.watching_list_view)).check(matches(not(hasDescendant(withText(containsString("The Shawshank Redemption (1994)"))))));

        //check if The Godfather and The Godfather: Part II are still in the list
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather (1972)")))));
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather: Part II (1974)")))));


        //delete The Godfather
        movieEntry = onData(anything()).inAdapterView(withId(R.id.watching_list_view)).atPosition(0);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather (1972)"))));
        movieEntry.onChildView((withId(R.id.movie_delete))).perform(click());

        //check if The Godfather was deleted
        onView(withId(R.id.watching_list_view)).check(matches(not(hasDescendant(withText(containsString("The Godfather (1972)"))))));

        //check if The Godfather: Part II is still in the list
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather: Part II (1974)")))));

        //delete The Godfather: Part 2
        movieEntry = onData(anything()).inAdapterView(withId(R.id.watching_list_view)).atPosition(0);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather: Part II (1974)"))));
        movieEntry.onChildView((withId(R.id.movie_delete))).perform(click());

        //check if The Godfather: Part II was deleted
        onView(withId(R.id.watching_list_view)).check(matches(not(hasDescendant(withText(containsString("The Godfather: Part II (1974)"))))));
    }

    @Test
    public void testAddFromDetails(){
        DataInteraction movieEntry;

        //add The Shawshank Redemption
        movieEntry = onData(anything()).inAdapterView(withId(R.id.search_list_view)).atPosition(0);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Shawshank Redemption"))));
        movieEntry.onChildView((withId(R.id.movie_details))).perform(click());

        onView(allOf(withId(R.id.fab_expand_menu_button),
                childAtPosition(
                        allOf(withId(R.id.multiple_actions),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        3)),
                        3))).perform(click());

        onView(withId(R.id.green_background)).perform(swipeUp());
        onView(
                allOf(withId(R.id.action_b),
                        childAtPosition(
                                allOf(withId(R.id.multiple_actions),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1))).perform(click());

        Espresso.pressBack();

        //add The Godfather
        movieEntry = onData(anything()).inAdapterView(withId(R.id.search_list_view)).atPosition(1);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather"))));
        movieEntry.onChildView((withId(R.id.movie_details))).perform(click());

        onView(allOf(withId(R.id.fab_expand_menu_button),
                childAtPosition(
                        allOf(withId(R.id.multiple_actions),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        3)),
                        3))).perform(click());

        onView(withId(R.id.green_background)).perform(swipeUp());
        onView(
                allOf(withId(R.id.action_b),
                        childAtPosition(
                                allOf(withId(R.id.multiple_actions),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1))).perform(click());

        Espresso.pressBack();

        //add The Godfather: Part 2
        movieEntry = onData(anything()).inAdapterView(withId(R.id.search_list_view)).atPosition(2);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather: Part II"))));
        movieEntry.onChildView((withId(R.id.movie_details))).perform(click());

        onView(allOf(withId(R.id.fab_expand_menu_button),
                childAtPosition(
                        allOf(withId(R.id.multiple_actions),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        3)),
                        3))).perform(click());

        onView(withId(R.id.green_background)).perform(swipeUp());
        onView(
                allOf(withId(R.id.action_b),
                        childAtPosition(
                                allOf(withId(R.id.multiple_actions),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1))).perform(click());

        Espresso.pressBack();

        //Display PTW list
        onView(allOf(childAtPosition(allOf(withId(android.R.id.tabs), childAtPosition(withClassName(is("android.widget.LinearLayout")), 0)), 3), isDisplayed())).perform(click());


        //check for the added movies
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Shawshank Redemption")))));
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather")))));
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather: Part II")))));


        //TEST DELETION
        //delete The Shawshank Redemption
        movieEntry = onData(anything()).inAdapterView(withId(R.id.watching_list_view)).atPosition(0);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Shawshank Redemption (1994)"))));
        movieEntry.onChildView((withId(R.id.movie_delete))).perform(click());

        //check if The Shawshank Redemption was deleted
        onView(withId(R.id.watching_list_view)).check(matches(not(hasDescendant(withText(containsString("The Shawshank Redemption (1994)"))))));

        //check if The Godfather and The Godfather: Part II are still in the list
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather (1972)")))));
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather: Part II (1974)")))));


        //delete The Godfather
        movieEntry = onData(anything()).inAdapterView(withId(R.id.watching_list_view)).atPosition(0);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather (1972)"))));
        movieEntry.onChildView((withId(R.id.movie_delete))).perform(click());

        //check if The Godfather was deleted
        onView(withId(R.id.watching_list_view)).check(matches(not(hasDescendant(withText(containsString("The Godfather (1972)"))))));

        //check if The Godfather: Part II is still in the list
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather: Part II (1974)")))));

        //delete The Godfather: Part 2
        movieEntry = onData(anything()).inAdapterView(withId(R.id.watching_list_view)).atPosition(0);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather: Part II (1974)"))));
        movieEntry.onChildView((withId(R.id.movie_delete))).perform(click());

        //check if The Godfather: Part II was deleted
        onView(withId(R.id.watching_list_view)).check(matches(not(hasDescendant(withText(containsString("The Godfather: Part II (1974)"))))));
    }

    @Test
    public void testSearch() {
        DataInteraction movieEntry;

        //add The Shawshank Redemption
        movieEntry = onData(anything()).inAdapterView(withId(R.id.search_list_view)).atPosition(0);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Shawshank Redemption"))));
        movieEntry.onChildView((withId(R.id.movie_details))).perform(click());

        onView(allOf(withId(R.id.fab_expand_menu_button),
                childAtPosition(
                        allOf(withId(R.id.multiple_actions),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        3)),
                        3))).perform(click());

        onView(withId(R.id.green_background)).perform(swipeUp());
        onView(
                allOf(withId(R.id.action_b),
                        childAtPosition(
                                allOf(withId(R.id.multiple_actions),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1))).perform(click());

        Espresso.pressBack();

        //add The Godfather
        movieEntry = onData(anything()).inAdapterView(withId(R.id.search_list_view)).atPosition(1);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather"))));
        movieEntry.onChildView((withId(R.id.movie_details))).perform(click());

        onView(allOf(withId(R.id.fab_expand_menu_button),
                childAtPosition(
                        allOf(withId(R.id.multiple_actions),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        3)),
                        3))).perform(click());

        onView(withId(R.id.green_background)).perform(swipeUp());
        onView(
                allOf(withId(R.id.action_b),
                        childAtPosition(
                                allOf(withId(R.id.multiple_actions),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1))).perform(click());

        Espresso.pressBack();

        //add The Godfather: Part 2
        movieEntry = onData(anything()).inAdapterView(withId(R.id.search_list_view)).atPosition(2);
        movieEntry.onChildView((withId(R.id.movie_name))).check(matches(withText(containsString("The Godfather: Part II"))));
        movieEntry.onChildView((withId(R.id.movie_details))).perform(click());

        onView(allOf(withId(R.id.fab_expand_menu_button),
                childAtPosition(
                        allOf(withId(R.id.multiple_actions),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        3)),
                        3))).perform(click());

        onView(withId(R.id.green_background)).perform(swipeUp());
        onView(
                allOf(withId(R.id.action_b),
                        childAtPosition(
                                allOf(withId(R.id.multiple_actions),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1))).perform(click());

        Espresso.pressBack();

        //Display Watching list
        onView(allOf(childAtPosition(allOf(withId(android.R.id.tabs), childAtPosition(withClassName(is("android.widget.LinearLayout")), 0)), 3), isDisplayed())).perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.search), withContentDescription("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(R.id.search_plate),
                                        childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("god"), closeSoftKeyboard());

        //check for The Godfather and The Godfather: Part 2
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather (1972)")))));
        onView(withId(R.id.watching_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather: Part II (1974)")))));

        //check for movies without "god" in the title
        onView(withId(R.id.watching_list_view)).check(matches(not(hasDescendant(withText(containsString("The Shawshank Redemption"))))));
        onView(withId(R.id.watching_list_view)).check(matches(not(hasDescendant(withText(containsString("Fargo"))))));

        searchAutoComplete.perform(replaceText(""), closeSoftKeyboard());

        //delete The Shawshank Redemption
        movieEntry = onData(anything()).inAdapterView(withId(R.id.watching_list_view)).atPosition(0);
        movieEntry.onChildView((withId(R.id.movie_delete))).perform(click());

        //delete The Godfather
        movieEntry.onChildView((withId(R.id.movie_delete))).perform(click());

        //delete The Godfather: Part 2
        movieEntry.onChildView((withId(R.id.movie_delete))).perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
