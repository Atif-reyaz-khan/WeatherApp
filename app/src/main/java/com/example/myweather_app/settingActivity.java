package com.example.myweather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.Objects;

public class settingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("atif","fucked");
        super.onCreate ( savedInstanceState );
        Log.i("atif","lucked");

        this.setContentView ( R.layout.activity_setting );
        Log.i("naffess","is good");
        this.getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId ();
        if(id==android.R.id.home){

            Log.i( "nafeesa", "Lucked " );
            onBackPressed ();
        }
        return super.onOptionsItemSelected ( item );
    }
}
