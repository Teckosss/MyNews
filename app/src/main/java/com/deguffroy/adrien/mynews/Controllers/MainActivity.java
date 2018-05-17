package com.deguffroy.adrien.mynews.Controllers;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.deguffroy.adrien.mynews.Controllers.Fragments.BusinessFragment;
import com.deguffroy.adrien.mynews.Controllers.Fragments.MostPopularFragment;
import com.deguffroy.adrien.mynews.Controllers.Fragments.TopStoriesFragment;
import com.deguffroy.adrien.mynews.R;
import com.deguffroy.adrien.mynews.Views.PageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FOR DESIGN
    @BindView(R.id.activity_main_toolbar) Toolbar toolbar;
    @BindView(R.id.activity_main_drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_nav_view) NavigationView navigationView;
    @BindView(R.id.activity_main_viewpager) ViewPager pager;
    @BindView(R.id.activity_main_tabs) TabLayout tabs;

    //FOR FRAGMENTS
    private Fragment fragmentTopStories;
    private Fragment fragmentMostPopular;
    private Fragment fragmentBusiness;

    //Identity each fragment with a number
    public static final int FRAGMENT_TOP_STORIES = 0;
    public static final int  FRAGMENT_MOST_POPULAR = 1;
    public static final int  FRAGMENT_BUSINESS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        this.showFragmentAtLaunch();

        this.configureViewPagerAndTabs();
    }

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_top_stories :
                this.showFragment(FRAGMENT_TOP_STORIES);
                break;
            case R.id.activity_main_drawer_most_popular:
                this.showFragment(FRAGMENT_MOST_POPULAR);
                break;
            case R.id.activity_main_drawer_business:
                this.showFragment(FRAGMENT_BUSINESS);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ---------------------
    // FRAGMENTS
    // ---------------------

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_TOP_STORIES:
                this.showTopStoriesFragment();
                pager.setCurrentItem(FRAGMENT_TOP_STORIES);
                break;
            case  FRAGMENT_MOST_POPULAR:
                this.showMostPopularFragment();
                pager.setCurrentItem(FRAGMENT_MOST_POPULAR);
                break;
            case FRAGMENT_BUSINESS:
                this.showBusinessFragment();
                pager.setCurrentItem(FRAGMENT_BUSINESS);
                break;
        }
    }

    // Create each fragment page and show it

    private void showTopStoriesFragment(){
        if (this.fragmentTopStories == null) this.fragmentTopStories = TopStoriesFragment.newInstance();
        this.startTransactionFragment(this.fragmentTopStories);
    }

    private void showMostPopularFragment(){
        if (this.fragmentMostPopular== null) this.fragmentMostPopular = MostPopularFragment.newInstance();
        this.startTransactionFragment(this.fragmentMostPopular);
    }

    private void showBusinessFragment(){
        if (this.fragmentBusiness == null) this.fragmentBusiness = BusinessFragment.newInstance();
        this.startTransactionFragment(this.fragmentBusiness);
    }

    // ---

    // 3 - Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }

    private void showFragmentAtLaunch(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null) {
            this.showFragment(FRAGMENT_TOP_STORIES);
            // Mark as selected the menu item corresponding to NewsFragment
            this.navigationView.getMenu().getItem(FRAGMENT_TOP_STORIES).setChecked(true);
        }
    }


    // ---------------------
    // CONFIGURATION
    // ---------------------

    // Configure Toolbar
    private void configureToolBar(){
        setSupportActionBar(toolbar);
    }

    // Configure Drawer Layout
    private void configureDrawerLayout(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView(){
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void configureViewPagerAndTabs(){
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(),getApplicationContext()));
        // Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }
}
