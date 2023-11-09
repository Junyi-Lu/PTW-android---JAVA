package comp3350.PTW.tests.acceptance;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.*;
import org.junit.runner.RunWith;
import androidx.test.runner.AndroidJUnit4;

import comp3350.ptw.R;
import comp3350.ptw.presentation.HomeActivity;
import androidx.appcompat.widget.SearchView;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StartUpAcceptanceTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void testHomeScreen() {
        //check tabs
        onView(withText("BROWSE")).check(matches(isDisplayed()));
        onView(withText("PLAN TO WATCH")).check(matches(isDisplayed()));
        onView(withText("WATCHING")).check(matches(isDisplayed()));
        onView(withText("WATCHED")).check(matches(isDisplayed()));

        //check that the proper view is displayed
        onView(withId(R.id.search_list_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recommend_list_view)).check(doesNotExist());
        onView(withId(R.id.ptw_list_view)).check(doesNotExist());
        onView(withId(R.id.watching_list_view)).check(doesNotExist());
        onView(withId(R.id.watched_list_view)).check(doesNotExist());

        onView(withId(R.id.search)).check(matches(isDisplayed()));
    }

    @Test
    public void testSearch(){
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
        onView(withId(R.id.search_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather (1972)")))));
        onView(withId(R.id.search_list_view)).check(matches(hasDescendant(withText(containsString("The Godfather: Part II (1974)")))));

        //check for movies without "god" in the title
        onView(withId(R.id.search_list_view)).check(matches(not(hasDescendant(withText(containsString("The Shawshank Redemption"))))));
        onView(withId(R.id.search_list_view)).check(matches(not(hasDescendant(withText(containsString("Fargo"))))));
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
