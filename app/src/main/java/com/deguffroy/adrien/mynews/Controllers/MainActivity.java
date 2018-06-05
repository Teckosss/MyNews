package com.deguffroy.adrien.mynews.Controllers;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.deguffroy.adrien.mynews.Controllers.Fragments.MainFragment;
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

    //Identity each fragment with a number
    public static final int FRAGMENT_TOP_STORIES = 0;
    public static final int  FRAGMENT_MOST_POPULAR = 1;
    public static final int  FRAGMENT_BUSINESS = 2;

    //Identity each activity with a number
    public static final int ACTIVITY_SEARCH = 0;
    public static final int ACTIVITY_NOTIFICATIONS = 1;

    //FOR DATA
    private PageAdapter viewPagerAdapter;

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        this.configureViewPagerAndTabs();

        this.showFragment(FRAGMENT_TOP_STORIES);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.activity_main_toolbar_menu, menu);
        return true;
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
            case R.id.activity_main_drawer_search:
                this.showActivity(ACTIVITY_SEARCH);
                break;
            case R.id.activity_main_drawer_settings:
                this.showActivity(ACTIVITY_NOTIFICATIONS);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_settings:
                this.showActivity(ACTIVITY_NOTIFICATIONS);
                return true;
            case R.id.menu_activity_main_search:
                this.showActivity(ACTIVITY_SEARCH);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ---------------------
    // ACTIVITY
    // ---------------------

    private void showActivity(int activityIdentifier){
        switch (activityIdentifier){
            case ACTIVITY_SEARCH:
                launchActivity(SearchArticlesActivity.class);
                break;
            case ACTIVITY_NOTIFICATIONS:
                launchActivity(NotificationsActivity.class);
                break;
        }
    }

    private void launchActivity(Class mClass){
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }

    // ---------------------
    // FRAGMENTS
    // ---------------------

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_TOP_STORIES:
                pager.setCurrentItem(FRAGMENT_TOP_STORIES);
                navigationView.setCheckedItem(R.id.activity_main_drawer_top_stories);
                break;
            case FRAGMENT_MOST_POPULAR:
                pager.setCurrentItem(FRAGMENT_MOST_POPULAR);
                break;
            case FRAGMENT_BUSINESS:
                pager.setCurrentItem(FRAGMENT_BUSINESS);
                break;
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
        this.viewPagerAdapter = new PageAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.namesPagesViewPager));
        pager.setAdapter(viewPagerAdapter);
        // Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case FRAGMENT_TOP_STORIES:
                        navigationView.setCheckedItem(R.id.activity_main_drawer_top_stories);
                        break;
                    case FRAGMENT_MOST_POPULAR:
                        navigationView.setCheckedItem(R.id.activity_main_drawer_most_popular);
                        break;
                    case FRAGMENT_BUSINESS:
                        navigationView.setCheckedItem(R.id.activity_main_drawer_business);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}
