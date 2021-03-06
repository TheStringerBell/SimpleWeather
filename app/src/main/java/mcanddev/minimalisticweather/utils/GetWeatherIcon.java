package mcanddev.minimalisticweather.utils;


import mcanddev.minimalisticweather.R;

public class GetWeatherIcon {

    private int icon;

    public GetWeatherIcon(){
    }

    public int getIcon(String iconName){

        switch (iconName){
            case "01d": icon = R.mipmap.sunny; break;
            case "01n": icon = R.mipmap.night_clear; break;

            case "02d": icon = R.mipmap.partlycloudy; break;
            case "02n": icon = R.mipmap.night_cloudy; break;

            case "03n": icon = R.mipmap.cloudy; break;
            case "03d": icon = R.mipmap.cloudy; break;

            case "04n": icon = R.mipmap.cloudy; break;
            case "04d": icon = R.mipmap.cloudy; break;

            case "09d": icon = R.mipmap.rain; break;
            case "09n": icon = R.mipmap.rain; break;

            case "10d": icon = R.mipmap.rain; break;
            case "10n": icon = R.mipmap.rain; break;

            case "11d": icon = R.mipmap.thunderstorm; break;
            case "11n": icon = R.mipmap.thunderstorm; break;

            case "13d": icon = R.mipmap.snow; break;
            case "13n": icon = R.mipmap.snow; break;

            case "50d": icon = R.mipmap.fog; break;
        }
        return icon;
    }

}
