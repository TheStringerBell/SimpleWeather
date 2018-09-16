package mcanddev.minimalisticweather.UI;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.RetModel.Interface.RetrofitInterface;
import mcanddev.minimalisticweather.RetModel.RetrofitClient;

public class LocationPresenter implements LocationViewPresenter{

    private LocationViewPresenter lvp;


    public LocationPresenter(LocationViewPresenter lvp){
        this.lvp = lvp;
    }

    public void getLocationString(String name){
        getAttributes(name).subscribeWith(getLocationObserver());

    }


    @Override
    public void finishUrl(GetLocation getLocation) {

    }

    public Observable<GetLocation> getAttributes(String name){


        return RetrofitClient.getRetrofitPlacesName().create(RetrofitInterface.class)
                .getLocation("json?query=" +name.replace(" ", "%")+ "&key=" + ApiKeys.getApiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<GetLocation> getLocationObserver(){

        return new DisposableObserver<GetLocation>() {
            @Override
            public void onNext(GetLocation getLocation) {

                lvp.finishUrl(getLocation);

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
