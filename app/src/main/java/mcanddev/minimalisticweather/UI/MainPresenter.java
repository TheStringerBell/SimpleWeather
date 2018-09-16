package mcanddev.minimalisticweather.UI;

import android.view.View;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.RetModel.Interface.RetrofitInterface;
import mcanddev.minimalisticweather.RetModel.RetrofitClient;



public class MainPresenter implements MainViewInterface {

    private MainViewInterface mvi;


    public MainPresenter(MainViewInterface mvi){
        this.mvi = mvi;

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
}
