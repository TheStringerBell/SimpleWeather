package mcanddev.minimalisticweather.UI;

import android.util.Log;
import android.view.View;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.Datum;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.GetWeather;
import mcanddev.minimalisticweather.RetModel.Interface.RetrofitInterface;
import mcanddev.minimalisticweather.RetModel.RetrofitClient;
import mcanddev.minimalisticweather.RetModel.WeatherClient;


public class MainPresenter implements MainViewInterface {

    Calendar calendar = Calendar.getInstance();
    int hour;

    private MainViewInterface mvi;


    public MainPresenter(MainViewInterface mvi){
        this.mvi = mvi;

    }

    @Override
    public void getWeatherObject(GetWeather getWeather) {

    }

    @Override
    public void fillListView(MainList mainList) {

    }

    public void getPredictionList(String name){
        getPrediction(name).subscribeWith(getPredictionObserver());
    }

    public Observable<MainList> getPrediction(String name){

        return RetrofitClient.getRetrofitAutoComplete().create(RetrofitInterface.class)
                .getNames("autocomplete/"+"json?input=" +name+ "&key=" + ApiKeys.getApiKey + "&types=(cities)")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<MainList> getPredictionObserver(){

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


    public void getLocationString(String name){
        getAttributes(name).subscribeWith(getLocationObserver());

    }




    public Observable<GetLocation> getAttributes(String name){


        return RetrofitClient.getRetrofitAutoComplete().create(RetrofitInterface.class)

                .getLocation("textsearch/"+"json?input=" +name.replace(" ", "%")+ "&key=" + ApiKeys.getApiKey)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<GetLocation> getLocationObserver(){

        return new DisposableObserver<GetLocation>() {
            @Override
            public void onNext(GetLocation getLocation) {
                String lat = getLocation.getResults().get(0).getGeometry().getLocation().getLat().toString();
                String lon = getLocation.getResults().get(0).getGeometry().getLocation().getLng().toString();

                getWeatherString(lat, lon);



            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onComplete() {


            }
        };
    }

    public void getWeatherString(String lat, String lon){
        getWeatherObservable(lat, lon).subscribeWith(getWeather());

    }


    public Observable<GetWeather> getWeatherObservable(String lat, String lon){


        return WeatherClient.getRetrofitWeather().create(RetrofitInterface.class)

                .getWeather("forecast/" +ApiKeys.getWeatherApiKey + "/" +lat +  "," + lon + "?units=auto")

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<GetWeather> getWeather(){
        return new DisposableObserver<GetWeather>() {
            @Override
            public void onNext(GetWeather getWeather) {
                mvi.getWeatherObject(getWeather);

                List<Datum> data = getWeather.getHourly().getData();
                double temp;
                for (int i = 0; i< data.size(); i+=2){
                    temp = data.get(i).getTemperature();
                    calendar.setTimeInMillis(data.get(i).getTime()*1000L);

                    Log.d("TIME "," " + calendar.get(Calendar.HOUR_OF_DAY) + " : 00" + " temp: " + temp );
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.d("ERROR", " ");
                e.printStackTrace();

            }

            @Override
            public void onComplete() {

            }
        };
    }


}
