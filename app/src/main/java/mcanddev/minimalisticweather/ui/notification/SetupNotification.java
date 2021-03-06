package mcanddev.minimalisticweather.ui.notification;


import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.List;

import mcanddev.minimalisticweather.pojo.openweather.GetOpenWeather;
import mcanddev.minimalisticweather.R;
import mcanddev.minimalisticweather.utils.GetWeatherIcon;

public class SetupNotification {

    private Context context;
    private String PACKAGE_NAME;
    private String units;
    private GetOpenWeather data;
    private GetWeatherIcon getWeatherIcon = new GetWeatherIcon();
    private List<mcanddev.minimalisticweather.pojo.openweather.List> list;

    public SetupNotification(Context context, String PACKAGE_NAME, GetOpenWeather data, String units){
        this.context = context;
        this.PACKAGE_NAME = PACKAGE_NAME;
        this.data = data;
        this.units = units;
        list = data.getList();
    }

    public void setupNotifyLayout(){

        RemoteViews remoteViews = new RemoteViews(PACKAGE_NAME, R.layout.custom_notify_layout);

        remoteViews.setImageViewResource(R.id.image, getIcon(list.get(0).getWeather().get(0).getIcon()));
        remoteViews.setTextViewText(R.id.time, getTime(0));
        remoteViews.setTextViewText(R.id.temp, getTemp(list.get(0).getMain().getTemp()));

        remoteViews.setImageViewResource(R.id.image3, getIcon(list.get(1).getWeather().get(0).getIcon()));
        remoteViews.setTextViewText(R.id.time3, getTime(1));
        remoteViews.setTextViewText(R.id.temp3, getTemp(list.get(1).getMain().getTemp()));

        remoteViews.setImageViewResource(R.id.image4, getIcon(list.get(2).getWeather().get(0).getIcon()));
        remoteViews.setTextViewText(R.id.time4, getTime(2));
        remoteViews.setTextViewText(R.id.temp4, getTemp(list.get(2).getMain().getTemp()));

        remoteViews.setImageViewResource(R.id.image5, getIcon(list.get(3).getWeather().get(0).getIcon()));
        remoteViews.setTextViewText(R.id.time5, getTime(3));
        remoteViews.setTextViewText(R.id.temp5, getTemp(list.get(3).getMain().getTemp()));

        remoteViews.setImageViewResource(R.id.image6, getIcon(list.get(4).getWeather().get(0).getIcon()));
        remoteViews.setTextViewText(R.id.time6, getTime(4));
        remoteViews.setTextViewText(R.id.temp6, getTemp(list.get(4).getMain().getTemp()));


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "SIMPLE_WEATHER")

                .setContent(remoteViews)
                .setSmallIcon(R.mipmap.cloudy)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        managerCompat.notify(1, mBuilder.build());
    }

    private String getTime(int i){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(list.get(i).getDt() * 1000L);

        if (units.equals("metric")){
            if (calendar.get(Calendar.HOUR_OF_DAY) < 10){
                return "0" + calendar.get(Calendar.HOUR_OF_DAY) + " : 00";
            }
            return calendar.get(Calendar.HOUR_OF_DAY) + " : 00";
        }

        if (calendar.get(Calendar.AM_PM) == 1){
            if (calendar.get(Calendar.HOUR) == 0){
                return  "12" +  " AM";
            }
                return  calendar.get(Calendar.HOUR) +  " PM";
            }
        if (calendar.get(Calendar.HOUR) == 0){
            return  "12" +  " PM";
        }
            return  calendar.get(Calendar.HOUR) +  " AM";
    }
    private int getIcon(String s){
        return getWeatherIcon.getIcon(s);
    }

    private String getTemp(Double d){
        if (units.equals("metric")){
            return String.valueOf( d.intValue()) + "°C";
        }
        return String.valueOf( d.intValue()) + "°F";

    }
}
