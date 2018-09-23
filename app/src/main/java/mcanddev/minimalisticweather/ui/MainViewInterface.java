package mcanddev.minimalisticweather.ui;


import io.reactivex.disposables.CompositeDisposable;
import mcanddev.minimalisticweather.pojo.MainList;
import mcanddev.minimalisticweather.pojo.openweather.GetOpenWeather;

public interface MainViewInterface {

    interface view{
        void fillListView(MainList mainList);
        void getWeatherObject(GetOpenWeather getWeather);
        void showToast(String s);

    }
    interface presenter{
        void getAutocompleteResults(String s);
        void getWeatherData(String s);
        void getOnlyWeather(String s, String l, String units);
    }


//    void getText(String s);
}
