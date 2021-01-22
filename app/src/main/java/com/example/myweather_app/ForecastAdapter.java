package com.example.myweather_app;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForestViewHolder> {

    private final Context mContext;

    private final ForecastAdapterOnClickHandler mClickHandler;

public interface ForecastAdapterOnClickHandler {
    void onClick(long WeatherForDay);
}

    private Cursor mCursor;
public ForecastAdapter(Context Context, ForecastAdapterOnClickHandler clickHandler){
    mContext = Context;
    mClickHandler=clickHandler;
}

    public  class ForestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView t1;
        public ForestViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.tv_weather_data);

            itemView.setOnClickListener(this);
        }
        public void onClick(View V){
                int adapterPosition=getAdapterPosition ();
            mCursor.moveToPosition ( adapterPosition );
            long dateInMillis=mCursor.getLong ( MainActivity.INDEX_WEATHER_DATE );
            mClickHandler.onClick(dateInMillis);
        }
    }
@NonNull
    @Override

    public ForestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.sub_main,parent,false);

        return new ForestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForestViewHolder holder, int position) {
mCursor.moveToPosition ( position );
long dataInMillis=mCursor.getLong ( MainActivity.INDEX_WEATHER_DATE );
String dateString=SunshineDateUtils.getFriendlyDateString ( mContext,dataInMillis,false );
int weather=mCursor.getInt(MainActivity.INDEX_WEATHER_CONDITION_ID);
        String description = SunshineWeatherUtils.getStringForWeatherCondition(mContext, weather);
        /* Read high temperature from the cursor (in degrees celsius) */
        double highInCelsius = mCursor.getDouble(MainActivity.INDEX_WEATHER_MAX_TEMP);
        /* Read low temperature from the cursor (in degrees celsius) */
        double lowInCelsius = mCursor.getDouble(MainActivity.INDEX_WEATHER_MIN_TEMP);

        String highAndLowTemperature =
                SunshineWeatherUtils.formatHighLows(mContext, highInCelsius, lowInCelsius);

        String weatherSummary = dateString + " - " + description + " - " + highAndLowTemperature;
        holder.t1.setText ( weatherSummary );

    }

    @Override
    public int getItemCount() {

    if(null==mCursor){
        Log.i("atif is good","atiifueee");
        return 0;}
        return mCursor.getCount ();
    }

void swapCursor(Cursor newCursor){
    mCursor=newCursor;
    notifyDataSetChanged ();
}

}
