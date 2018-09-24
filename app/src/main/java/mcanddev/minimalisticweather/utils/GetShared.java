package mcanddev.minimalisticweather.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class GetShared {
    private Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public GetShared(Context context){
        this.context = context;
        sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public String getLat(){
        return sp.getString("Lat", "o");
    }
    public String getPlace(){
        return sp.getString("Place", "City");
    }

    public String getLon(){
        return sp.getString("Lon", "o");
    }
    public String getUnits(){
        return sp.getString("Units", "metric");
    }

    public void setLat(String value){
            editor.putString("Lat", value);
            apply();
    }

    public void setLon(String value){
        editor.putString("Lon", value);
        apply();
    }

    public void setUnits(String value){
        editor.putString("Units", value);
        apply();
    }
    public void setPlace(String value){
        editor.putString("Place", value);
        apply();
    }

    private void apply(){
        editor.apply();
    }
}
