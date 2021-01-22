package com.example.myweather_app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import java.net.URL;

public class SunshineSyncTask {
    public static void syncWeather(Context context){
        try{
            URL weatherRequestUrl=NetworkUtils.getUrl (context );
            String jsonWeatherResponse=NetworkUtils.getResponseFromHttpUrl ( weatherRequestUrl );
            ContentValues[] weatherValues=OpenWeatherJsonUtils.getWeatherContentValuesFromJson ( context,jsonWeatherResponse );
            if(weatherValues!=null && weatherValues.length!=0){
                ContentResolver sunshineContentResolver=context.getContentResolver ();
                sunshineContentResolver.delete ( WeatherContact.WeatherEntry.CONTENT_URI,null,null );
                sunshineContentResolver.bulkInsert ( WeatherContact.WeatherEntry.CONTENT_URI,weatherValues );
            }
        }
        catch (Exception e){
            e.printStackTrace ();

        }
    }
}
