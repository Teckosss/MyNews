package com.deguffroy.adrien.mynews;

import com.deguffroy.adrien.mynews.Controllers.Fragments.MainFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

/**
 * Created by Adrien Deguffroy on 10/06/2018.
 */

@RunWith(RobolectricTestRunner.class)
public class MainFragmentTest {

    @Test
    public void testFragment() throws Exception {
        MainFragment mainFragment = MainFragment.newInstance(0);
        startFragment(mainFragment);
        assertNotNull(mainFragment);
    }
}
