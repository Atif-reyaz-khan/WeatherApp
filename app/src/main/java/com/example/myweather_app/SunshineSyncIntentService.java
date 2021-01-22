package com.example.myweather_app;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class SunshineSyncIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SunshineSyncIntentService(String name) {
        super ( "SunshineSyncIntentService" );
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
     SunshineSyncTask.syncWeather (this);
    }
}
