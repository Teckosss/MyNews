package com.deguffroy.adrien.mynews;

import com.deguffroy.adrien.mynews.Controllers.MainActivity;
import com.deguffroy.adrien.mynews.Views.PageAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.deguffroy.adrien.mynews.Controllers.Fragments.MainFragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
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
        mFragment = MainFragment.newInstance(0);
        startFragment(mFragment);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(mActivity);
    }

    @Test
    public void setAndGetCurrentPage() throws Exception {

    }

    private void startFragment( Fragment fragment ) {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null );
        fragmentTransaction.commit();
    }
}
