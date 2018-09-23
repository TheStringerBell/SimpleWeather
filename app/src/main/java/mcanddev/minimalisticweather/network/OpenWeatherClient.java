package mcanddev.minimalisticweather.network;



import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.network.model.RetrofitInterface;
import mcanddev.minimalisticweather.pojo.openweather.GetOpenWeather;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class OpenWeatherClient {

    private Retrofit retrofit;

    public OpenWeatherClient (){
        if(retrofit == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

    }

    public Retrofit getOpenWeatherClient(){
        return retrofit;
    }

    public Observable<GetOpenWeather> getOpenWeatherObservable(String lat, String lon, String units){
        return retrofit.create(RetrofitInterface.class)
                .getOpenWeather("forecast?lat=" + lat + "&lon=" + lon + "&units=" + units+ "&appid=" + ApiKeys.getOpenWeatherApiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
