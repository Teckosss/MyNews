package com.deguffroy.adrien.mynews.Utils;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.deguffroy.adrien.mynews.Controllers.MainActivity;

import java.io.File;
import java.io.IOException;

/**
 * Created by Adrien Deguffroy on 14/06/2018.
 */

public class LogWriteToFile extends Application{
    public static final String LOG_WRITE_TO_FILE_TAG = "LogWriteToFile";
    /**
     * Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.
     */
    public void onCreate() {
        super.onCreate();

        if ( isExternalStorageWritable() ) {

            Log.e(LOG_WRITE_TO_FILE_TAG, "IsWritable : True" );
            File appDirectory = new File( Environment.getExternalStorageDirectory() + "/MyNews" );
            File logDirectory = new File( appDirectory + "/log" );
            File logFile = new File( logDirectory, "logcat" + System.currentTimeMillis() + ".txt" );

            // create app folder
            if ( !appDirectory.exists() ) {
                appDirectory.mkdir();
            }

            // create log folder
            if ( !logDirectory.exists() ) {
                logDirectory.mkdir();
            }

            // clear the previous logcat and then write the new one to the file
            try {
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
