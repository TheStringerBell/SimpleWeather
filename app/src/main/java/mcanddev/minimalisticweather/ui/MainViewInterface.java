package mcanddev.minimalisticweather.ui;


import mcanddev.minimalisticweather.pojo.MainList;
import mcanddev.minimalisticweather.pojo.openweather.GetOpenWeather;

public interface MainViewInterface {

    void fillListView(MainList mainList);
    void getWeatherObject(GetOpenWeather getWeather);
//    void getText(String s);
}
