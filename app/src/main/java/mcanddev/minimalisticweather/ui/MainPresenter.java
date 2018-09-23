package mcanddev.minimalisticweather.ui;



import io.reactivex.disposables.CompositeDisposable;
import mcanddev.minimalisticweather.network.OpenWeatherClient;
import mcanddev.minimalisticweather.network.RetrofitClient;

public class MainPresenter implements MainViewInterface.presenter {


    private MainViewInterface.view mvi;
    private RetrofitClient retrofitClient;
    private OpenWeatherClient openWeatherClient;
    private CompositeDisposable cDisposable;
    private static final String ON_ERROR = "Something went wrong";


    public MainPresenter(MainViewInterface.view mvi){
        this.mvi = mvi;
        retrofitClient = new RetrofitClient();
        openWeatherClient = new OpenWeatherClient();
        cDisposable = new CompositeDisposable();
    }

    @Override
    public void getAutocompleteResults(String s) {
        cDisposable.add(retrofitClient.getPrediction(s)
                   .subscribe(mainList ->
                              mvi.fillListView(mainList),
                              throwable -> mvi.showToast(ON_ERROR)));
    }

    @Override
    public void getWeatherData(String s) {
        cDisposable.add(retrofitClient.getAttributes(s)
                .flatMap(getLocation -> {
                    String lat = getLocation.getResults().get(0).getGeometry().getLocation().getLat().toString();
                    String lon = getLocation.getResults().get(0).getGeometry().getLocation().getLng().toString();
                    mvi.setSharedPref(lat, lon);

                    return openWeatherClient.getOpenWeatherObservable(lat, lon, "metric");
                })
                .subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather),
                        throwable -> mvi.showToast(ON_ERROR), this::dispose));
    }


    @Override
    public void getOnlyWeather(String s, String l, String units) {
        cDisposable.add(openWeatherClient.getOpenWeatherObservable(s, l, units)
                .subscribe(getOpenWeather -> mvi.getWeatherObject(getOpenWeather),
                        throwable -> mvi.showToast(ON_ERROR)));

    }

    private void dispose(){
        cDisposable.dispose();
    }





}
