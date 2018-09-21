package mcanddev.minimalisticweather.UI;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.Datum;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.GetWeather;
import mcanddev.minimalisticweather.R;
import mcanddev.minimalisticweather.RetModel.Interface.RetrofitInterface;
import mcanddev.minimalisticweather.RetModel.RetrofitClient;
import mcanddev.minimalisticweather.RetModel.WeatherClient;


public class MainPresenter implements MainViewInterface {



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




    private Observable<GetWeather> getWeatherObservable(String lat, String lon){


        return WeatherClient.getRetrofitWeather().create(RetrofitInterface.class)

                .getWeather("forecast/" +ApiKeys.getWeatherApiKey + "/" +lat +  "," + lon + "?units=auto")

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public void combined(String s){
        getAttributes(s).flatMap(getLocation -> {
            String lat = getLocation.getResults().get(0).getGeometry().getLocation().getLat().toString();
            String lon = getLocation.getResults().get(0).getGeometry().getLocation().getLng().toString();
            return getWeatherObservable(lat, lon);
        }
        ).subscribe(getWeather -> mvi.getWeatherObject(getWeather));
    }




}
