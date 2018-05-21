package com.deguffroy.adrien.mynews.Views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.deguffroy.adrien.mynews.Controllers.Fragments.BusinessFragment;
import com.deguffroy.adrien.mynews.Controllers.Fragments.MostPopularFragment;
import com.deguffroy.adrien.mynews.Controllers.Fragments.TopStoriesFragment;
import com.deguffroy.adrien.mynews.Controllers.MainActivity;
import com.deguffroy.adrien.mynews.R;

/**
 * Created by Adrien Deguffroy on 17/05/2018.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 3;
    private String[] names;

    //Default Constructor
    public PageAdapter(FragmentManager mgr, String[] names) {
        super(mgr);
        this.names = names;
    }

    @Override
    public int getCount() {
        return(PAGE_COUNT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case MainActivity.FRAGMENT_TOP_STORIES: //Page number 1
                return TopStoriesFragment.newInstance();
            case MainActivity.FRAGMENT_MOST_POPULAR: //Page number 2
                return MostPopularFragment.newInstance();
            case MainActivity.FRAGMENT_BUSINESS: //Page number 3
                return BusinessFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return names[position];
            case 1: //Page number 2
                return names[position];
            case 2: //Page number 3
                return names[position];
            default:
                return null;
        }
    }
}

