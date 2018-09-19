package mcanddev.minimalisticweather.UI;


import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.GetWeather;

public interface MainViewInterface {

    void fillListView(MainList mainList);
    void getWeatherObject(GetWeather getWeather);
//    void getText(String s);
}
