package com.deguffroy.adrien.mynews.Utils.Notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.deguffroy.adrien.mynews.Controllers.SearchResultActivity;
import com.deguffroy.adrien.mynews.Models.NYTimesResultAPI;
import com.deguffroy.adrien.mynews.Models.NotificationsPreferences;
import com.deguffroy.adrien.mynews.R;
import com.deguffroy.adrien.mynews.Utils.NYTimesStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.content.Context.MODE_PRIVATE;
import static com.deguffroy.adrien.mynews.Controllers.NotificationsActivity.NOTIFICATIONS_STATE;
import static com.deguffroy.adrien.mynews.Controllers.NotificationsActivity.PREFS;
import static com.deguffroy.adrien.mynews.Controllers.SearchArticlesActivity.BEGIN_DATE;
import static com.deguffroy.adrien.mynews.Controllers.SearchArticlesActivity.FILTER_QUERY;
import static com.deguffroy.adrien.mynews.Controllers.SearchArticlesActivity.QUERY;

/**
 * Created by Adrien Deguffroy on 08/06/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public static final String NOTIFICATIONS_DATE = "NOTIFICATIONS_DATE";

    private Disposable disposable;
    private SharedPreferences mPreferences;
    private NotificationsPreferences mNotificationsPreferences;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ALARM_RECEIVER", "onReceive: Waiting for action" );

        this.mContext = context;
        this.retrieveSharedPreferences();
    }

    private void retrieveSharedPreferences(){
        mPreferences = mContext.getSharedPreferences(PREFS,MODE_PRIVATE);
        String date = mPreferences.getString(NOTIFICATIONS_DATE,"");
        Log.e("ALARM_RECEIVER", "Date in SharedPreferences: " + date);
        if (!(date.equals(todayDate()))){
            Gson gson = new Gson();
            Type type = new TypeToken<NotificationsPreferences>(){}.getType();
            String jsonState = mPreferences.getString(NOTIFICATIONS_STATE,"");
            mNotificationsPreferences = gson.fromJson(jsonState,type);
            if (mNotificationsPreferences != null){
                String queryTerm = mNotificationsPreferences.getQueryTerm();
                List<String> categoryList = mNotificationsPreferences.getCategoryList();
                mPreferences.edit().putString(NOTIFICATIONS_DATE,todayDate()).apply();
                this.executeHttpRequestWithRetrofit(queryTerm, categoryList);
            }
        }else{
            Log.e("ALARM_RECEIVER", "retrieveSharedPreferences: Notification already sent today" );
        }

    }

    private void executeHttpRequestWithRetrofit(String queryTerm, List<String> categoryList){
        this.disposable = NYTimesStreams.streamFetchSearchResultFilterDate(queryTerm, categoryList, todayDate(),null).subscribeWith(new DisposableObserver<NYTimesResultAPI>() {
            @Override
            public void onNext(NYTimesResultAPI nyTimesResultAPI) {
                if (!(nyTimesResultAPI.getResponse().getDocs().isEmpty())){
                    Log.e("ALARM_RECEIVER", "onNext: Result found !" );
                    sendNotifications(queryTerm, categoryList);
                }else{
                    Log.e("ALARM_RECEIVER", "onNext: No result found !" );
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("ALARM_RECEIVER", "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void sendNotifications(String queryTerm, List<String> categoryList){
        Log.e("ALARM_RECEIVER", "Sending notification !" );
        //Intent to invoke app when click on notification.
        //In this sample, we want to start/launch this sample app when user clicks on notification
        Intent intentToRepeat = new Intent(mContext, SearchResultActivity.class);
        intentToRepeat.putExtra(QUERY,queryTerm);
        intentToRepeat.putExtra(BEGIN_DATE,todayDate());
        intentToRepeat.putStringArrayListExtra(FILTER_QUERY,(ArrayList<String>)categoryList);
        //set flag to restart/relaunch the app
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Pending intent to handle launch of Activity in intent above
        PendingIntent pendingIntent =
                PendingIntent.getActivity(mContext, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build notification
        Notification repeatedNotification = buildLocalNotification(mContext, pendingIntent).build();

        //Send local notification
        NotificationHelper.getNotificationManager(mContext).notify(NotificationHelper.ALARM_TYPE_RTC, repeatedNotification);
    }

    public NotificationCompat.Builder buildLocalNotification(Context context, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_openclassrooms)
                        .setContentTitle("MyNews")
                        .setContentText("Articles that may interest you!")
                        .setAutoCancel(true);

        return builder;
    }

    private String todayDate(){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(Calendar.getInstance().getTime());
    }
}