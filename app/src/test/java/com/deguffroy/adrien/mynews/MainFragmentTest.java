package com.deguffroy.adrien.mynews;

import com.deguffroy.adrien.mynews.Controllers.MainActivity;
import com.deguffroy.adrien.mynews.Controllers.SearchArticlesActivity;
import com.deguffroy.adrien.mynews.Views.PageAdapter;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;

import com.deguffroy.adrien.mynews.Controllers.Fragments.MainFragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Adrien Deguffroy on 10/06/2018.
 */

@RunWith(RobolectricTestRunner.class)
public class MainFragmentTest {

    private MainActivity mActivity;
    private ViewPager mViewPager;
    private PageAdapter mPageAdapter;

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
