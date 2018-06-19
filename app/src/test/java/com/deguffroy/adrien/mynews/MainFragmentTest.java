package com.deguffroy.adrien.mynews;

import com.deguffroy.adrien.mynews.Controllers.MainActivity;
import com.deguffroy.adrien.mynews.Controllers.NotificationsActivity;
import com.deguffroy.adrien.mynews.Controllers.SearchArticlesActivity;
import com.deguffroy.adrien.mynews.Controllers.SearchResultActivity;
import com.deguffroy.adrien.mynews.Models.NYTimesResultAPI;
import com.deguffroy.adrien.mynews.Utils.NYTimesStreams;
import com.deguffroy.adrien.mynews.Views.PageAdapter;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ListView;

import com.deguffroy.adrien.mynews.Controllers.Fragments.MainFragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

/**
 * Created by Adrien Deguffroy on 10/06/2018.
 */

@RunWith(RobolectricTestRunner.class)
public class MainFragmentTest {

    private MainActivity mActivity;
    private MainFragment mFragment;
    private ViewPager mViewPager;
    private PageAdapter mPageAdapter;
    private String[] mStrings;

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.setupActivity(MainActivity.class);
        mViewPager = mActivity.findViewById(R.id.activity_main_viewpager);
        mPageAdapter = (PageAdapter)mViewPager.getAdapter();
    }

    @Test
    public void checkIfMainFragmentIsVisible() throws Exception {
        assertEquals(0, mViewPager.getCurrentItem());
        assertEquals(MainActivity.FRAGMENT_TOP_STORIES, getIdentifier());
    }

    @Test
    public void clickSearch_shouldChangeActivity() throws Exception {
        mActivity.findViewById(R.id.menu_activity_main_search).performClick();
        Intent intent = Shadows.shadowOf(mActivity).peekNextStartedActivity();
        assertEquals(SearchArticlesActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void checkIfNavigationDrawerContainsItem() throws Exception {
        NavigationView nvMenu = mActivity.findViewById(R.id.activity_main_nav_view);
        assertEquals("Top Stories", nvMenu.getMenu().findItem(R.id.activity_main_drawer_top_stories).getTitle().toString() );
        assertEquals("Notifications", nvMenu.getMenu().findItem(R.id.activity_main_drawer_notifs).getTitle().toString() );
    }

    private int getIdentifier(){
        return mPageAdapter.getItem(mViewPager.getCurrentItem()).getArguments().getInt(MainFragment.IDENTIFIER, 0);
    }
}
