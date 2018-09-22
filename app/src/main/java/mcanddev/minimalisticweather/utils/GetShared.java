package mcanddev.minimalisticweather.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class GetShared {
    private Context context;
    private SharedPreferences sp;

    public GetShared(Context context){
        this.context = context;
        sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);

    }

    public String getLat(){
        return sp.getString("Lat", "o");
    }

    public String getLon(){
        return sp.getString("Lon", "o");
    }
    public String getUnits(){
        return sp.getString("Units", "metric");
    }
}
