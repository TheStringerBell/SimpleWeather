package mcanddev.minimalisticweather.RetModel.Interface;


import io.reactivex.Observable;
import mcanddev.minimalisticweather.POJO.MainList;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitInterface {

    @GET
    Observable<MainList> getNames(@Url String url);


}
