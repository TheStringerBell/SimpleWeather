package mcanddev.minimalisticweather.RetModel.Interface;


import io.reactivex.Observable;
import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitInterface {

    @GET
    Observable<MainList> getNames(@Url String url);

    @GET
    Observable<GetLocation> getLocation(@Url String url);

    @GET
    Call<GetLocation> getResponse(@Url String url);





}
