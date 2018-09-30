package mcanddev.minimalisticweather.ui;


import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import mcanddev.minimalisticweather.network.OpenWeatherClient;
import mcanddev.minimalisticweather.network.RetrofitClient;
import mcanddev.minimalisticweather.pojo.MainList;

public class MainPresenter implements MainViewInterface.presenter {


    private MainViewInterface.view mvi;
    private RetrofitClient retrofitClient;
    private OpenWeatherClient openWeatherClient;
    private CompositeDisposable cDisposable;
    private static final String ON_ERROR = "Something went wrong";
    private int active;
    private int deactive;
    private ArrayList<String> arrayList;


    public MainPresenter(MainViewInterface.view mvi){
        this.mvi = mvi;
        retrofitClient = new RetrofitClient();
        openWeatherClient = new OpenWeatherClient();
        cDisposable = new CompositeDisposable();
        active = Color.parseColor("#FF4081");
        deactive = Color.parseColor("#f9f9f9");
        arrayList = new ArrayList<>();
    }

    // get city names from Googles Autocomplete API
    @Override
    public void getAutocompleteResults(String s) {
        cDisposable.add(retrofitClient.getPrediction(s.replace(" ", "%20"))
                   .subscribe(mainList ->
                              mvi.fillListView(getArrayList(mainList)),
                              throwable -> mvi.showToast(ON_ERROR)));
    }

    // get latitude and longitude from Googles Places API and object with weather forecast from Openweather API
    @Override
    public void getWeatherData(String s, String units) {
        cDisposable.add(retrofitClient.getAttributes(s.replace(" ", "%20"))
                .flatMap(getLocation -> {
                    String lat = getLocation.getResults().get(0).getGeometry().getLocation().getLat().toString();
                    String lon = getLocation.getResults().get(0).getGeometry().getLocation().getLng().toString();
                    mvi.setSharedPref(lat, lon);
                    return openWeatherClient.getOpenWeatherObservable(lat, lon, units);
                })
                .subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather),
                        throwable -> mvi.showToast(ON_ERROR), this::dispose));
    }

    // get only weather forecast
    @Override
    public void getOnlyWeather(String s, String l, String units) {
        cDisposable.add(openWeatherClient.getOpenWeatherObservable(s, l, units)
                .subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather),
                        throwable -> mvi.showToast(ON_ERROR), this::dispose));
    }

    // change units in sharedpref and set button colors
    @Override
    public void getButtonState(Boolean b) {
        if (b){
            mvi.setButtonColor(active, deactive);
            mvi.setUnits("metric");
        }else {
            mvi.setButtonColor(deactive, active);
            mvi.setUnits("imperial");
        }
    }

    @Override
    public void checkLatitudeAtStartUp(String s, String l, String units) {
        if (!s.equals("o")){
            getOnlyWeather(s, l, units);
        }
    }

    private void dispose(){
        cDisposable.clear();
    }

    private ArrayList<String> getArrayList(MainList mainList){
        if (mainList != null){
            if (!arrayList.isEmpty()){arrayList.clear();}
            for (int i = 0; i < mainList.getPredictions().size(); i++){
                arrayList.add(mainList.getPredictions().get(i).getDescription());
            }
        }
        return arrayList;

    }





}
