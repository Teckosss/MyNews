package com.deguffroy.adrien.mynews.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.deguffroy.adrien.mynews.Models.NotificationsPreferences;
import com.deguffroy.adrien.mynews.R;
import com.deguffroy.adrien.mynews.Utils.Notifications.NotificationHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsActivity extends AppCompatActivity {

    @BindView(R.id.simple_toolbar) Toolbar mToolbar;
    @BindView(R.id.activity_notifications_switch) Switch mSwitch;
    @BindView(R.id.checkbox_container) LinearLayout mCheckboxContainer;
    @BindView(R.id.search_editText) EditText mSearchQuery;

    public static final String PREFS = "PREFS";
    public static final String NOTIFICATIONS_STATE = "NOTIFICATIONS_STATE";
    public static final String PREF_KEY_QUERY_TERM = "PREF_KEY_QUERY_TERM";
    public static final String PREF_KEY_CATEGORY_LIST = "PREF_KEY_CATEGORY_LIST";

    public static final String NOTIFICATIONS_HOUR = "14";
    public static final String NOTIFICATIONS_MIN = "30";


    private SharedPreferences mPreferences;
    private NotificationsPreferences mNotificationsPreferences;
    private String mQueryTerm;
    private List<String> mCategoryList;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.retrievePreferences();
        this.configureSwitchChangeListener();
    }

    @Override
    protected void onPause(){
        super.onPause();
        saveNotificationsPreferences(mSearchQuery.getText().toString(), getSelectedCheckboxes(), mSwitch.isChecked());
        if (mSwitch.isChecked()){
            toggleNotifications(true);
            Log.e("Notifications", "onPause: True" );
        }else{
            toggleNotifications(false);
            Log.e("Notifications", "onPause: False" );
        }
        Toast.makeText(NotificationsActivity.this, "Notifications preferences saved", Toast.LENGTH_SHORT).show();
    }

    // -------------
    // CONFIGURATION
    // -------------

    private void configureToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void retrievePreferences(){
        mPreferences = getBaseContext().getSharedPreferences(PREFS,MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<NotificationsPreferences>(){}.getType();
        String jsonState = mPreferences.getString(NOTIFICATIONS_STATE,"");
        mNotificationsPreferences = gson.fromJson(jsonState,type);
        if (mNotificationsPreferences == null){ // Normally use for first time launch
            mQueryTerm = mPreferences.getString(PREF_KEY_QUERY_TERM, NotificationsPreferences.DEFAULT_QUERY_TERM);
            String retrieve = mPreferences.getString(PREF_KEY_CATEGORY_LIST, NotificationsPreferences.DEFAULT_CATEGORY_LIST);
            mCategoryList = new ArrayList<>();
            mCategoryList.add(retrieve);
            saveNotificationsPreferences(mQueryTerm, mCategoryList, false);
        }else{
            mQueryTerm = mNotificationsPreferences.getQueryTerm();
            List<String> list = mNotificationsPreferences.getCategoryList();
            //Log.e("TAG", "retrievePreferences: LIST : " + list );
            if (!(mQueryTerm.equals("")) && (!((list.isEmpty())))){
                mSearchQuery.setText(mQueryTerm);
                for (int x = 0; x < list.size(); x++){
                    for (int i = 0; i < mCheckboxContainer.getChildCount(); i++) {
                        View view = mCheckboxContainer.getChildAt(i);
                        if (view instanceof ViewGroup) {
                            ViewGroup viewGroup = ((ViewGroup)view);
                            for (int y = 0; y < viewGroup.getChildCount(); y++){
                                View viewChild = viewGroup.getChildAt(y);
                                if (viewChild instanceof CheckBox){
                                    CheckBox checkBox = ((CheckBox) viewChild);
                                    if (checkBox.getTag().toString().equals(list.get(x)) ){
                                        checkBox.setChecked(true);
                                    }
                                }
                            }
                        }
                    }
                }
                mSwitch.setChecked(mNotificationsPreferences.isEnabled());
            }
        }
    }

    private void saveNotificationsPreferences(String queryTerm, List<String> categoryList, boolean isEnabled){
        Gson gson = new Gson();
        NotificationsPreferences notifPrefs = new NotificationsPreferences(queryTerm,categoryList, isEnabled);
        String jsonNotifPrefs = gson.toJson(notifPrefs);
        mPreferences.edit().putString(NOTIFICATIONS_STATE,jsonNotifPrefs).apply();
    }

    private List<String> getSelectedCheckboxes(){
        List<String> selectedCheckboxes = new ArrayList<>();
        for (int i = 0; i < mCheckboxContainer.getChildCount(); i++) {
            View view = mCheckboxContainer.getChildAt(i);
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = ((ViewGroup)view);
                for (int y = 0; y < viewGroup.getChildCount(); y++){
                    View viewChild = viewGroup.getChildAt(y);
                    if (viewChild instanceof CheckBox){
                        CheckBox checkBox = ((CheckBox) viewChild);
                        if (checkBox.isChecked()){
                            selectedCheckboxes.add(checkBox.getTag().toString());
                        }
                    }
                }
            }
        }
        return selectedCheckboxes;
    }

    private void configureSwitchChangeListener(){
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!(mSearchQuery.getText().toString().equals(""))){
                        List<String> selectedCheckboxes = getSelectedCheckboxes();
                        if (selectedCheckboxes.isEmpty()){
                            Toast.makeText(NotificationsActivity.this, "You have to select at least one category", Toast.LENGTH_SHORT).show();
                            buttonView.setChecked(false);
                        }
                        /*else{
                            Toast.makeText(NotificationsActivity.this, "Notifications preferences saved", Toast.LENGTH_SHORT).show();
                            saveNotificationsPreferences(mSearchQuery.getText().toString(),selectedCheckboxes, true);
                            toggleNotifications(true);
                        }*/
                    }else{
                        Toast.makeText(NotificationsActivity.this, "Query term can't be empty to enable notifications", Toast.LENGTH_SHORT).show();
                        buttonView.setChecked(false);
                    }
                }else{
                    toggleNotifications(false);
                }
            }
        });
    }

    private void toggleNotifications(boolean enableNotifications){
        if (enableNotifications){
            NotificationHelper.scheduleRepeatingRTCNotification(getBaseContext(), NOTIFICATIONS_HOUR, NOTIFICATIONS_MIN);
            NotificationHelper.enableBootReceiver(getBaseContext());
        }else{
            NotificationHelper.cancelAlarmRTC();
            NotificationHelper.disableBootReceiver(getBaseContext());
        }
    }
}
