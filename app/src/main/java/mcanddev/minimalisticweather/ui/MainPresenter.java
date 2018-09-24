package mcanddev.minimalisticweather.ui;



import android.content.res.Resources;
import android.graphics.Color;

import io.reactivex.disposables.CompositeDisposable;
import mcanddev.minimalisticweather.network.OpenWeatherClient;
import mcanddev.minimalisticweather.network.RetrofitClient;

public class MainPresenter implements MainViewInterface.presenter {


    private MainViewInterface.view mvi;
    private RetrofitClient retrofitClient;
    private OpenWeatherClient openWeatherClient;
    private CompositeDisposable cDisposable;
    private static final String ON_ERROR = "Something went wrong";
    private int active;
    private int deactive;


    public MainPresenter(MainViewInterface.view mvi){
        this.mvi = mvi;
        retrofitClient = new RetrofitClient();
        openWeatherClient = new OpenWeatherClient();
        cDisposable = new CompositeDisposable();
        active = Color.parseColor("#FF4081");
        deactive = Color.parseColor("#f9f9f9");
    }

    @Override
    public void getAutocompleteResults(String s) {
        cDisposable.add(retrofitClient.getPrediction(s)
                   .subscribe(mainList ->
                              mvi.fillListView(mainList),
                              throwable -> mvi.showToast(ON_ERROR)));
    }

    @Override
    public void getWeatherData(String s, String units) {
        cDisposable.add(retrofitClient.getAttributes(s)
                .flatMap(getLocation -> {
                    String lat = getLocation.getResults().get(0).getGeometry().getLocation().getLat().toString();
                    String lon = getLocation.getResults().get(0).getGeometry().getLocation().getLng().toString();
                    mvi.setSharedPref(lat, lon);

                    return openWeatherClient.getOpenWeatherObservable(lat, lon, units);
                })
                .subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather),
                        throwable -> mvi.showToast(ON_ERROR), this::dispose));
    }


    @Override
    public void getOnlyWeather(String s, String l, String units) {
        cDisposable.add(openWeatherClient.getOpenWeatherObservable(s, l, units)
                .subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather),
                        throwable -> mvi.showToast(ON_ERROR), this::dispose));

    }

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

    private void dispose(){
        cDisposable.clear();
    }





}
