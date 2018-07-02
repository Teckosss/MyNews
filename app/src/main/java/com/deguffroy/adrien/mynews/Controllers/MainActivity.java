package com.deguffroy.adrien.mynews.Controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.deguffroy.adrien.mynews.Controllers.Fragments.MainFragment;
import com.deguffroy.adrien.mynews.R;
import com.deguffroy.adrien.mynews.Views.PageAdapter;

import java.io.File;
import java.io.IOException;

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
    public static final int ACTIVITY_HELP = 2;
    public static final int ACTIVITY_ABOUT = 3;

    public static final String LOG_WRITE_TO_FILE_TAG = "LogWriteToFile";
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;

    //FOR DATA
    private PageAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //this.checkPermissions(); // USE FOR DEBUG

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        this.configureViewPagerAndTabs();

        this.showFragment(FRAGMENT_TOP_STORIES);
    }

    private void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        }else{
            this.writeLogToFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.writeLogToFile();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                }
            }
        }
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
            case R.id.activity_main_drawer_notifs:
                this.showActivity(ACTIVITY_NOTIFICATIONS);
                break;
            case R.id.activity_main_drawer_help:
                this.showActivity(ACTIVITY_HELP);
                break;
            case R.id.activity_main_drawer_about:
                this.showActivity(ACTIVITY_ABOUT);
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
            case R.id.menu_activity_main_notifs:
                this.showActivity(ACTIVITY_NOTIFICATIONS);
                return true;
            case R.id.menu_activity_main_help:
                this.showActivity(ACTIVITY_HELP);
                return true;
            case R.id.menu_activity_main_about:
                this.showActivity(ACTIVITY_ABOUT);
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
            case ACTIVITY_HELP:
                launchActivity(HelpActivity.class);
                break;
            case ACTIVITY_ABOUT:
                launchActivity(AboutActivity.class);
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

    private void writeLogToFile(){
        if ( isExternalStorageWritable() ) {

            Log.e(LOG_WRITE_TO_FILE_TAG, "IsWritable : True" );
            File appDirectory = new File( Environment.getExternalStorageDirectory() + "/MyNews" );
            File logDirectory = new File( appDirectory + "/log" );
            File logFile = new File( logDirectory, "logcat" + System.currentTimeMillis() + ".txt" );

            // create app folder
            if ( !appDirectory.exists() ) {
                Log.e(LOG_WRITE_TO_FILE_TAG, "Creating folder" );
                appDirectory.mkdir();
            }

            // create log folder
            if ( !logDirectory.exists() ) {
                Log.e(LOG_WRITE_TO_FILE_TAG, "Creating log file" );
                logDirectory.mkdir();
            }

            // clear the previous logcat and then write the new one to the file
            try {
                Log.e(LOG_WRITE_TO_FILE_TAG, "Writing" );
                Process process = Runtime.getRuntime().exec("logcat -c");
                process = Runtime.getRuntime().exec("logcat -f " + logFile);
            } catch ( IOException e ) {
                e.printStackTrace();
            }

        } else if ( isExternalStorageReadable() ) {
            Log.e(LOG_WRITE_TO_FILE_TAG, "IsOnlyReadable : True" );

        } else {
            Log.e(LOG_WRITE_TO_FILE_TAG, "IsNotAccessible : True" );
            // not accessible
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if ( Environment.MEDIA_MOUNTED.equals( state ) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals( state ) ) {
            return true;
        }
        return false;
    }
}
