package mcanddev.minimalisticweather.ui;


import io.reactivex.disposables.CompositeDisposable;
import mcanddev.minimalisticweather.pojo.MainList;
import mcanddev.minimalisticweather.pojo.openweather.GetOpenWeather;

public interface MainViewInterface {

    interface view{
        void fillListView(MainList mainList);
        void getWeatherObject(GetOpenWeather getWeather);
        void showToast(String s);
        void setSharedPref(String lat, String lon);
        void setUnits(String units);
        void setButtonColor(int color, int color2);

    }
    interface presenter{
        void getAutocompleteResults(String s);
        void getWeatherData(String s, String units);
        void getButtonState(Boolean b);
        void getOnlyWeather(String s, String l, String units);
    }
    interface recycleView{
        void getPlace(int i);
    }

}
