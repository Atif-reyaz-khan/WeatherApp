package com.example.myweather_app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

 public class SunshineSyncUtils {
    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private static boolean sInitialized ;
    private static final String SUNSHINE_SYNC_TAG = "sunshine-sync";
static void scheduleFirebaseJobDispatcherSync(Context context){
    GooglePlayDriver driver=new GooglePlayDriver ( context );
    FirebaseJobDispatcher dispatcher=new FirebaseJobDispatcher ( driver );
    Job syncSunshineJob=dispatcher.newJobBuilder ()
            .setService( SunshineFirebaseJobService.class)
            .setTag(SUNSHINE_SYNC_TAG)
            .setConstraints ( Constraint.ON_ANY_NETWORK )
            .setLifetime ( Lifetime.FOREVER )
            .setRecurring ( true )
            .setTrigger( Trigger.executionWindow (
                    SYNC_INTERVAL_SECONDS,SYNC_INTERVAL_SECONDS+SYNC_FLEXTIME_SECONDS))
            .setReplaceCurrent ( true )
            .build ();

dispatcher.schedule ( syncSunshineJob );
}

    public static void initialize(final Context context){
        if(sInitialized){
            return ;
        }
        sInitialized=true;
        scheduleFirebaseJobDispatcherSync ( context );
        new AsyncTask<Void,Void,Void> (){
            @Override
            protected Void doInBackground(Void... voids) {
                Uri forcastQueryUri=WeatherContact.WeatherEntry.CONTENT_URI;
                String[] projectionColumns={WeatherContact.WeatherEntry._ID};
                String selectionStatement=WeatherContact.WeatherEntry
                        .getSqlSelectForTodayOnwards ();
                Cursor cursor=context.getContentResolver ().query (
                        forcastQueryUri,projectionColumns,selectionStatement,null,null );
                if (null == cursor || cursor.getCount() == 0) {
                    startImmediateSync(context);
                }

                /* Make sure to close the Cursor to avoid memory leaks! */
                cursor.close();
                return null;

        }


    }.execute();
}
    public static void startImmediateSync(final Context context)
    {
        Intent intentToSyncImmediately=new Intent ( context,SunshineSyncIntentService.class );
        context.startService ( intentToSyncImmediately );
    }
}