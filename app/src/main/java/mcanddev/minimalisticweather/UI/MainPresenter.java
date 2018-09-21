package mcanddev.minimalisticweather.UI;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.POJO.OpenWeather.GetOpenWeather;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.Datum;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.GetWeather;
import mcanddev.minimalisticweather.R;
import mcanddev.minimalisticweather.RetModel.Interface.RetrofitInterface;
import mcanddev.minimalisticweather.RetModel.OpenWeatherClient;
import mcanddev.minimalisticweather.RetModel.RetrofitClient;
import mcanddev.minimalisticweather.RetModel.WeatherClient;


public class MainPresenter implements MainViewInterface {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private MainViewInterface mvi;
    private Context context;


    public MainPresenter(MainViewInterface mvi, Context context){
        this.mvi = mvi;
        this.context = context;
    }

    @Override
    public void getWeatherObject(GetOpenWeather getWeather) {
    }

    @Override
    public void fillListView(MainList mainList) {
    }

    public void getPredictionList(String name){
        getPrediction(name).subscribeWith(getPredictionObserver());
    }


    private Observable<MainList> getPrediction(String name){

        return RetrofitClient.getRetrofitAutoComplete().create(RetrofitInterface.class)
                .getNames("autocomplete/"+"json?input=" +name+ "&key=" + ApiKeys.getApiKey + "&types=(cities)")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<MainList> getPredictionObserver(){

        return new DisposableObserver<MainList>() {
            @Override
            public void onNext(MainList mainList) {
                mvi.fillListView(mainList);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        };
    }


    private Observable<GetLocation> getAttributes(String name){

        return RetrofitClient.getRetrofitAutoComplete().create(RetrofitInterface.class)
                .getLocation("textsearch/"+"json?input=" +name.replace(" ", "%")+ "&key=" + ApiKeys.getApiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




//    private Observable<GetWeather> getWeatherObservable(String lat, String lon){
//
//        return WeatherClient.getRetrofitWeather().create(RetrofitInterface.class)
//                .getWeather("forecast/" +ApiKeys.getWeatherApiKey + "/" +lat +  "," + lon + "?units=auto")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    private Observable<GetOpenWeather> getOpenWeatherObservable(String lat, String lon, String units){

        return OpenWeatherClient.getOpenWeatherClient().create(RetrofitInterface.class)
                .getOpenWeather("forecast?lat=" + lat + "&lon=" + lon + "&units=" + units+ "&appid=" + ApiKeys.getOpenWeatherApiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }





//    public void combined(String s){
//        getOpenWeatherObservable("49.0511221", "20.295414", "metric").subscribeWith(getOpenWeatherDisposableObserver());
//        getAttributes(s).flatMap(getLocation -> {
//            String lat = getLocation.getResults().get(0).getGeometry().getLocation().getLat().toString();
//            String lon = getLocation.getResults().get(0).getGeometry().getLocation().getLng().toString();
//            sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
//            editor = sp.edit();
//            editor.putString("Lat", lat);
//            editor.putString("Lon", lon);
//            editor.apply();
//            return getWeatherObservable(lat, lon);
//        }
//        ).subscribe(getWeather -> mvi.getWeatherObject(getWeather));
//    }

    public void onlyWeather(String s, String l, String units){
        getOpenWeatherObservable(s, l, units).subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather));

    }

    public void combined(String s){

        getAttributes(s).flatMap(getLocation -> {
                    String lat = getLocation.getResults().get(0).getGeometry().getLocation().getLat().toString();
                    String lon = getLocation.getResults().get(0).getGeometry().getLocation().getLng().toString();
                    sp = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
                    editor = sp.edit();
                    editor.putString("Lat", lat);
                    editor.putString("Lon", lon);
                    editor.apply();
                    return getOpenWeatherObservable("49.0511221", "20.295414", "metric");
                }
        ).subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather));
    }




}
