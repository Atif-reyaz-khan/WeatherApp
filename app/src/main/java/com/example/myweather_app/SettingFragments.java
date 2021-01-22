package com.example.myweather_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class SettingFragments extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private  void setPreferenceSummary(Preference preference,Object value){
        Log.i("fucked","REARLLY");
        String stringValue=value.toString ();String key=preference.getKey ();
        if(preference instanceof ListPreference){
            ListPreference listPreference=(ListPreference) preference;
            int prefIndex=listPreference.findIndexOfValue ( stringValue);
if(prefIndex>=0){
    preference.setSummary(listPreference.getEntries ()[prefIndex]);
}
    }
        else{
preference.setSummary(stringValue);
        }
    }
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource ( R.xml.pref_xml );
SharedPreferences sharedPreferences=getPreferenceScreen ().getSharedPreferences ();
        PreferenceScreen preferenceScreen=getPreferenceScreen ();
        int count=preferenceScreen.getPreferenceCount ();
        Log.i(String.valueOf ( count ),"atifreyazkhan@gaail");
        for(int i=0;i<count;i++){
            Preference p=preferenceScreen.getPreference ( i );
            if( !(p instanceof CheckBoxPreference)){
                String value=sharedPreferences.getString ( p.getKey (),"" );
                setPreferenceSummary ( p,value );
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop ();
    getPreferenceScreen ().getSharedPreferences ().unregisterOnSharedPreferenceChangeListener ( this );
    }

    @Override
    public void onStart() {
        super.onStart ();
        getPreferenceScreen ().getSharedPreferences ().registerOnSharedPreferenceChangeListener ( this );
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
Preference preference=findPreference ( key );
if(null!=preference){
    if(!(preference instanceof CheckBoxPreference)){
        setPreferenceSummary ( preference,sharedPreferences.getString ( key," " ) );
    }

}
    }
}
