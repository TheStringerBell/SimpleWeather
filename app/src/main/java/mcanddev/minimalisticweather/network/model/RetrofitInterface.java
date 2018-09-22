package mcanddev.minimalisticweather.network.model;


import io.reactivex.Observable;
import mcanddev.minimalisticweather.pojo.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.pojo.MainList;
import mcanddev.minimalisticweather.pojo.OpenWeather.GetOpenWeather;
import mcanddev.minimalisticweather.pojo.WeatherPOJO.GetWeather;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface RetrofitInterface {

    @GET
    Observable<MainList> getNames(@Url String url);

    @GET
    Observable<GetLocation> getLocation(@Url String url);


    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Accept: application/json"
    })
    @GET
    Observable<GetWeather> getWeather(@Url String url);

    @GET
    Observable<GetOpenWeather> getOpenWeather(@Url String url);




}
