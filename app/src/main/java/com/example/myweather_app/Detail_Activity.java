package com.example.myweather_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Detail_Activity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor> {
private TextView weather_date,weather_description,weather_low_temp,weather_high_temp,weather_humidity,weather_pressure,weather_wind;
private static final String FORECAST_SHARE_HASHTAG="#sunshineApp";
    public static final String[] WEATHER_DETAIL_PROJECTION = {
            WeatherContact.WeatherEntry.COLUMN_DATE,
            WeatherContact.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContact.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContact.WeatherEntry.COLUMN_HUMIDITY,
            WeatherContact.WeatherEntry.COLUMN_PRESSURE,
        WeatherContact.WeatherEntry.COLUMN_WIND_SPEED,
            WeatherContact.WeatherEntry.COLUMN_DEGREES,
        WeatherContact.WeatherEntry.COLUMN_WEATHER_ID
    };
    public static final int INDEX_WEATHER_DATE = 0;
    public static final int INDEX_WEATHER_MAX_TEMP = 1;
    public static final int INDEX_WEATHER_MIN_TEMP = 2;
    public static final int INDEX_WEATHER_HUMIDITY = 3;
    public static final int INDEX_WEATHER_PRESSURE = 4;
    public static final int INDEX_WEATHER_WIND_SPEED = 5;
    public static final int INDEX_WEATHER_DEGREES = 6;
    public static final int INDEX_WEATHER_CONDITION_ID = 7;
    private static final int ID_DETAIL_LOADER = 353;
    private String mForecastSummary;
private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_);

        weather_date=findViewById(R.id.weather_date);

        weather_description=findViewById(R.id.weather_description);

        weather_low_temp=findViewById(R.id.weather_low_temp);

        weather_high_temp=findViewById(R.id.weather_high_temp);

        weather_humidity=findViewById(R.id.weather_humidity);

        weather_pressure=findViewById(R.id.weather_pressure);
        weather_wind=findViewById ( R.id.weather_wind );



        uri=getIntent().getData();
        if(uri==null) throw new NullPointerException ( "URI For DetailActivity cannot be null" );

getSupportLoaderManager ().initLoader(ID_DETAIL_LOADER,null,this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_act,menu);
        MenuItem menuItem=menu.findItem ( R.id.share);
        Intent shareIntent=ShareCompat.IntentBuilder.from(this).setType("text/plain")
                .setText(mForecastSummary+FORECAST_SHARE_HASHTAG).getIntent();
        menuItem.setIntent ( shareIntent );
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId ();



        if(id==R.id.setting2){
            Intent intent =new Intent ( this,settingActivity.class );
            startActivity(intent);
        return true;
        }
        return super.onOptionsItemSelected ( item );
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id){
            case ID_DETAIL_LOADER:
                return new CursorLoader ( this,uri,WEATHER_DETAIL_PROJECTION,null,null,null );
            default:
                throw new RuntimeException ( "LoaderNot Implemented0 "+id );
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        boolean cursorHasValidData=false;
        if(data!=null && data.moveToFirst ()){
            cursorHasValidData=true;
        }
        if(!cursorHasValidData){
            return;
        }
        long localDateMidnightGmt=data.getLong ( INDEX_WEATHER_DATE );
        String dateText=SunshineDateUtils.getFriendlyDateString ( this,localDateMidnightGmt,true );
weather_date.setText ( dateText );
int weatherId=data.getInt(INDEX_WEATHER_CONDITION_ID);
String description=SunshineWeatherUtils.getStringForWeatherCondition (this, weatherId );
    weather_description.setText ( description );
    double highInCelsius=data.getDouble ( INDEX_WEATHER_MAX_TEMP );
    String highString=SunshineWeatherUtils.formatTemperature ( this,highInCelsius );
    weather_high_temp.setText ( highString );
    double lowInCelsius=data.getDouble ( INDEX_WEATHER_MIN_TEMP );
    String lowString=SunshineWeatherUtils.formatTemperature ( this,lowInCelsius );
    weather_low_temp.setText ( lowString );
    float humidity=data.getFloat ( INDEX_WEATHER_HUMIDITY );
    String humidityString=getString ( R.string.format_humidity,humidity );
    weather_humidity.setText ( humidityString );
    float windSpeed=data.getFloat(INDEX_WEATHER_WIND_SPEED);
    float windDirection=data.getFloat (  INDEX_WEATHER_DEGREES);
    String windString=SunshineWeatherUtils.getFormattedWind ( this,windSpeed,windDirection );
    weather_wind.setText ( windString );
    float pressure=data.getFloat(INDEX_WEATHER_PRESSURE);
    String pressureString=getString ( R.string.format_pressure,pressure );
    weather_pressure.setText ( pressureString );
    mForecastSummary=String.format ( "%s - %s - %s%s",dateText,description,highString,lowString );
    }



    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
