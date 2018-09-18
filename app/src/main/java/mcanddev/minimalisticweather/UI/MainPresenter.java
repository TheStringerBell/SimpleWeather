package mcanddev.minimalisticweather.UI;

import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.RetModel.Interface.RetrofitInterface;
import mcanddev.minimalisticweather.RetModel.RetrofitClient;



public class MainPresenter implements MainViewInterface {

    private MainViewInterface mvi;


    public MainPresenter(MainViewInterface mvi){
        this.mvi = mvi;

    }

    @Override
    public void getLocationName(GetLocation getLocation) {

    }

    @Override
    public void fillListView(MainList mainList) {

    }

    public void getPredictionList(String name){
        getPrediction(name).subscribeWith(getPredictionObserver());
    }

    public Observable<MainList> getPrediction(String name){

        return RetrofitClient.getRetrofitAutoComplete().create(RetrofitInterface.class)
                .getNames("json?input=" +name+ "&key=" + ApiKeys.getApiKey + "&types=(cities)")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<MainList> getPredictionObserver(){

        return new DisposableObserver<MainList>() {
            @Override
            public void onNext(MainList mainList) {
                mvi.fillListView(mainList);
                dispose();

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


        return RetrofitClient.getRetrofitPlacesName().create(RetrofitInterface.class)

                .getLocation("json?input=" +name.replace(" ", "%")+ "&key=" + ApiKeys.getApiKey)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<GetLocation> getLocationObserver(){

        return new DisposableObserver<GetLocation>() {
            @Override
            public void onNext(GetLocation getLocation) {

                Log.d("STATUS", " " + getLocation.getStatus());
                Log.d("BODY", " " + getLocation.getResults().size());

                mvi.getLocationName(getLocation);


            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onComplete() {


            }
        };
    }
}
