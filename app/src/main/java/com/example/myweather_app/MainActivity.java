package com.example.myweather_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,ForecastAdapter.ForecastAdapterOnClickHandler {

    private final String TAG = MainActivity.class.getSimpleName();







    private RecyclerView rv_value;

private ForecastAdapter mForecastAdapter;
private ProgressBar progressBar;
    public static final String[] MAIN_FORECAST_PROJECTION = {
            WeatherContact.WeatherEntry.COLUMN_DATE,
            WeatherContact.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContact.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContact.WeatherEntry.COLUMN_WEATHER_ID,
    };
    public static final int INDEX_WEATHER_DATE = 0;
    public static final int INDEX_WEATHER_MAX_TEMP = 1;
    public static final int INDEX_WEATHER_MIN_TEMP = 2;
    public static final int INDEX_WEATHER_CONDITION_ID = 3;
    private static final int ID_FORECAST_LOADER = 44;
    private int mPosition=RecyclerView.NO_POSITION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar ().setElevation (0f);

        rv_value=findViewById(R.id.rv_value);


LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
rv_value.setLayoutManager(layoutManager);
rv_value.setHasFixedSize(true);
        progressBar=findViewById(R.id.scroll);
mForecastAdapter=new ForecastAdapter( this, this);
rv_value.setAdapter(mForecastAdapter);


showLoading ();
getSupportLoaderManager ().initLoader ( ID_FORECAST_LOADER,null,this );
SunshineSyncUtils.initialize ( this );
    }


    private void openPreferredLocationInMap() {
        double[] coords = SunshinePreferences.getLocationCoordinates(this);
        String posLat = Double.toString(coords[0]);
        String posLong = Double.toString(coords[1]);
        Uri geoLocation = Uri.parse("geo:" + posLat + "," + posLong);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(TAG, "Couldn't call " + geoLocation.toString() + ", no receiving apps installed!");
        }
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id){
            case ID_FORECAST_LOADER:
                Uri  forecastQueryUri=WeatherContact.WeatherEntry.CONTENT_URI;
                String sortOrder=WeatherContact.WeatherEntry.COLUMN_DATE+" ASC";
                String selection=WeatherContact.WeatherEntry.getSqlSelectForTodayOnwards ();
                return new CursorLoader ( this,forecastQueryUri,MAIN_FORECAST_PROJECTION,selection,null,sortOrder );
            default:
                throw new RuntimeException ( "loader Not Implemented "+id );

        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        Log.i ( "new aatif2","we are fukuju" );

mForecastAdapter.swapCursor ( data );

if(mPosition==RecyclerView.NO_POSITION)mPosition=0;

rv_value.smoothScrollToPosition ( mPosition );
Log.i(String.valueOf (  data.getCount()),"countAtif");
if( data.getCount ()!=0){
    showWeatherDataView ();

}


   }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
mForecastAdapter.swapCursor ( null );
    }

    @Override
    public void onClick(long WeatherForDay) {
        Intent intent=new Intent(MainActivity.this,Detail_Activity.class);
        Uri uriForDateClicked=WeatherContact.WeatherEntry.buildWeatherUriWithDate ( WeatherForDay);
        Log.i ("naffu","andgery");
        intent.setData(uriForDateClicked);


        startActivity(intent);

    }
    public void showWeatherDataView(){
        Log.i ( TAG,"atif reyaz kahn namn" );
        rv_value.setVisibility(View.VISIBLE);
        progressBar.setVisibility ( View.INVISIBLE );

    }

private  void showLoading(){
        rv_value.setVisibility ( View.INVISIBLE );
        progressBar.setVisibility ( View.VISIBLE );


}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull MenuItem item) {
        int id = item.getItemId();


         if(id==R.id.setting){
            Log.i("atif is great","got fucked from manay");
            Intent intent=new Intent ( MainActivity.this,settingActivity.class );
            startActivity ( intent );
        }

        return super.onOptionsItemSelected(item);
    }




}
