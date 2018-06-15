package com.deguffroy.adrien.mynews;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.deguffroy.adrien.mynews.Controllers.MainActivity;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.AnyOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Adrien Deguffroy on 13/06/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){
        activityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void isUiDisplayedTest() throws Exception {
        onView(withId(R.id.activity_main_drawer_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_main_toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_main_tabs)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_main_viewpager)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment_main_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void navDrawerOpenTest() throws Exception {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withId(R.id.activity_main_nav_view)).check(matches(isDisplayed()));
    }

    @Test
    public void navDrawerCloseTest() throws Exception {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.activity_main_nav_view)).check(matches(not(isDisplayed())));
    }

    @Test
    public void navDrawerClickItemTest() throws Exception {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText(R.string.navigation_drawer_search)).perform(click());
        onView(withId(R.id.search_button)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void toolbarSearchClickTest() throws Exception {
        onView(withId(R.id.menu_activity_main_search)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.menu_activity_main_search)).perform(click());
        onView(withId(R.id.search_button)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void toolbarMenuVerticalClickTest() throws Exception {
        onView(withId(R.id.menu_activity_main_settings)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.menu_activity_main_settings)).perform(click());
        onView(withText(R.string.navigation_drawer_notifications)).inRoot(isPlatformPopup()).check(matches(isCompletelyDisplayed()));
        onView(withText(R.string.navigation_drawer_notifications)).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.activity_notifications_switch)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void swipeRightChangeFragmentTest() throws Exception {
        onView(withId(R.id.activity_main_viewpager)).perform(swipeLeft());
        onView(withId(R.id.activity_main_viewpager)).check(matches(inPage(1)));
    }

    @Test
    public void clickOnViewPagerTab() throws Exception {
        onView(allOf(isDescendantOfA(withId(R.id.activity_main_tabs)),withText(R.string.navigation_drawer_business))).check(matches(isCompletelyDisplayed()));
        onView(allOf(isDescendantOfA(withId(R.id.activity_main_tabs)),withText(R.string.navigation_drawer_business))).perform(click());
        onView(withId(R.id.activity_main_viewpager)).check(matches(inPage(2)));
    }

    @Test
    public void swipeFromLastToFirstFragment() throws Exception {
        onView(allOf(isDescendantOfA(withId(R.id.activity_main_tabs)),withText(R.string.navigation_drawer_business))).perform(click());
        onView(withId(R.id.activity_main_viewpager)).check(matches(inPage(2)));
        for (int i = 0; i < 2 ; i++){
            onView(withId(R.id.activity_main_viewpager)).perform(swipeRight());
        }
        onView(withId(R.id.activity_main_viewpager)).check(matches(inPage(0)));
    }

    @Test
    public void clickRecyclerView() {
        // Click on the RecyclerView item at position 2
        onView(allOf(withId(R.id.fragment_main_recycler_view),isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @NonNull
    public static Matcher<View> inPage(final int page) {

        return new BoundedMatcher<View, ViewPager>(ViewPager.class) {

            @Override
            public void describeTo(final Description description) {
                description.appendText("in page: " + page);
            }

            @Override
            public boolean matchesSafely(final ViewPager viewPager) {
                return viewPager.getCurrentItem() == page;
            }
        };
    }
}
