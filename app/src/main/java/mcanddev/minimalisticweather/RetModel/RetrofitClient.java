package mcanddev.minimalisticweather.RetModel;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.POJO.Description;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.RetModel.Interface.RetrofitInterface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;


    public static Retrofit getRetrofitAutoComplete(){

        if(retrofit == null){

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/maps/api/place/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                    .client(okHttpClient)
                    .build();

        }

        return retrofit;
    }







}
