package mcanddev.minimalisticweather.ui;


import android.content.Context;
import android.content.SharedPreferences;

import android.widget.Toast;

import com.evernote.android.job.JobManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.network.WeatherClient;
import mcanddev.minimalisticweather.pojo.places.GetLocation;
import mcanddev.minimalisticweather.pojo.MainList;
import mcanddev.minimalisticweather.pojo.openweather.GetOpenWeather;
import mcanddev.minimalisticweather.network.model.RetrofitInterface;
import mcanddev.minimalisticweather.network.OpenWeatherClient;
import mcanddev.minimalisticweather.network.RetrofitClient;
import mcanddev.minimalisticweather.service.CreateJob;
import mcanddev.minimalisticweather.service.JobCreator;


public class MainPresenter implements MainViewInterface.presenter {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private MainViewInterface.view mvi;
    private Context context;
    private RetrofitClient retrofitClient;
    private OpenWeatherClient openWeatherClient;
    private CompositeDisposable cDisposable;


    public MainPresenter(MainViewInterface.view mvi, Context context){
        this.mvi = mvi;
        this.context = context;
        sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        retrofitClient = new RetrofitClient();
        openWeatherClient = new OpenWeatherClient();
        cDisposable = new CompositeDisposable();
    }

    @Override
    public void getAutocompleteResults(String s) {
        cDisposable.add(retrofitClient.getPrediction(s)
                .subscribe(mainList ->
                        mvi.fillListView(mainList), throwable -> Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                ));
    }

    @Override
    public void getWeatherData(String s) {
        combined(s);

    }


    public void onlyWeather(String s, String l, String units){
        cDisposable.add(openWeatherClient.getOpenWeatherObservable(s, l, units).subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather),
                        throwable -> Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()));

    }

    private void combined(String s){
        cDisposable.add(retrofitClient.getAttributes(s).flatMap(getLocation -> {
                    String lat = getLocation.getResults().get(0).getGeometry().getLocation().getLat().toString();
                    String lon = getLocation.getResults().get(0).getGeometry().getLocation().getLng().toString();
                    editor = sp.edit();
                    editor.putString("Lat", lat);
                    editor.putString("Lon", lon);
                    editor.apply();
                    return openWeatherClient.getOpenWeatherObservable(lat, lon, "metric");
                }
        )

                .subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather),
                           throwable -> Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()));


    }

    public void executeJob(){
        JobManager.create(context).addJobCreator(new JobCreator());
        CreateJob.scheduleJob();
    }

    public void dispose(){
        cDisposable.dispose();
    }




}
