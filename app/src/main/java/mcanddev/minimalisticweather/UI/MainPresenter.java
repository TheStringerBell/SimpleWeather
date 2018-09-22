package mcanddev.minimalisticweather.UI;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.evernote.android.job.JobManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.POJO.OpenWeather.GetOpenWeather;
import mcanddev.minimalisticweather.RetModel.Interface.RetrofitInterface;
import mcanddev.minimalisticweather.RetModel.OpenWeatherClient;
import mcanddev.minimalisticweather.RetModel.RetrofitClient;
import mcanddev.minimalisticweather.service.CreateJob;
import mcanddev.minimalisticweather.service.JobCreator;


public class MainPresenter implements MainViewInterface {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private MainViewInterface mvi;
    private Context context;



    public MainPresenter(MainViewInterface mvi, Context context){
        this.mvi = mvi;
        this.context = context;
        sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }

    @Override
    public void getWeatherObject(GetOpenWeather getWeather) {
    }

    @Override
    public void fillListView(MainList mainList) {
    }

    public void getPredictionList(String name){
        getPrediction(name)
                .subscribe(mainList ->
                            mvi.fillListView(mainList), throwable -> Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                );

    }


    private Observable<MainList> getPrediction(String name){

        return RetrofitClient.getRetrofitAutoComplete().create(RetrofitInterface.class)
                .getNames("autocomplete/"+"json?input=" +name.replace(" ", "%20")+ "&key=" + ApiKeys.getApiKey + "&types=(cities)")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




    private Observable<GetLocation> getAttributes(String name){

        return RetrofitClient.getRetrofitAutoComplete().create(RetrofitInterface.class)
                .getLocation("textsearch/"+"json?input=" +name.replace(" ", "%20")+ "&key=" + ApiKeys.getApiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




    private Observable<GetOpenWeather> getOpenWeatherObservable(String lat, String lon, String units){

        return OpenWeatherClient.getOpenWeatherClient().create(RetrofitInterface.class)
                .getOpenWeather("forecast?lat=" + lat + "&lon=" + lon + "&units=" + units+ "&appid=" + ApiKeys.getOpenWeatherApiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public void onlyWeather(String s, String l, String units){
        getOpenWeatherObservable(s, l, units).subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather));

    }

    public void combined(String s){

        getAttributes(s).flatMap(getLocation -> {
                    String lat = getLocation.getResults().get(0).getGeometry().getLocation().getLat().toString();
                    String lon = getLocation.getResults().get(0).getGeometry().getLocation().getLng().toString();


                    editor = sp.edit();
                    editor.putString("Lat", lat);
                    editor.putString("Lon", lon);
                    editor.apply();
                    return getOpenWeatherObservable(lat, lon, "metric");
                }
        )

                .subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather), throwable -> Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show());
    }

    public void executeJob(){
        JobManager.create(context).addJobCreator(new JobCreator());
        CreateJob.scheduleJob();
    }




}
