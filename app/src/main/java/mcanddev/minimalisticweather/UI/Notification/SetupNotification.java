package mcanddev.minimalisticweather.UI.Notification;


import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.List;

import mcanddev.minimalisticweather.MainActivity;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.Datum;
import mcanddev.minimalisticweather.R;
import mcanddev.minimalisticweather.utils.GetWeatherIcon;

public class SetupNotification {

    private Context context;
    private String PACKAGE_NAME;
    private List<Datum> data;


    private int time;



    public SetupNotification(Context context, String PACKAGE_NAME, List<Datum> data){
        this.context = context;
        this.PACKAGE_NAME = PACKAGE_NAME;
        this.data = data;
    }

    public void setupNotifyLayout(){
        Log.d("WEATHER2", " " +data.size());
        RemoteViews remoteViews = new RemoteViews(PACKAGE_NAME, R.layout.custom_notify_layout);


        remoteViews.setImageViewResource(R.id.image, getIcon(data.get(2).getIcon()));
        remoteViews.setTextViewText(R.id.time, getTime(2));
        remoteViews.setTextViewText(R.id.temp, getTemp(data.get(2).getTemperature()));

        remoteViews.setImageViewResource(R.id.image3, getIcon(data.get(5).getIcon()));
        remoteViews.setTextViewText(R.id.time3, getTime(5));
        remoteViews.setTextViewText(R.id.temp3, getTemp(data.get(5).getTemperature()));

        remoteViews.setImageViewResource(R.id.image4, getIcon(data.get(8).getIcon()));
        remoteViews.setTextViewText(R.id.time4, getTime(8));
        remoteViews.setTextViewText(R.id.temp4, getTemp(data.get(8).getTemperature()));

        remoteViews.setImageViewResource(R.id.image5, getIcon(data.get(11).getIcon()));
        remoteViews.setTextViewText(R.id.time5, getTime(11));
        remoteViews.setTextViewText(R.id.temp5, getTemp(data.get(11).getTemperature()));

        remoteViews.setImageViewResource(R.id.image6, getIcon(data.get(14).getIcon()));
        remoteViews.setTextViewText(R.id.time6, getTime(14));
        remoteViews.setTextViewText(R.id.temp6, getTemp(data.get(14).getTemperature()));




        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "SIMPLE_WEATHER")
                .setContent(remoteViews)
                .setSmallIcon(R.mipmap.cloudy)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1, mBuilder.build());

    }

    private String getTime(int i){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(data.get(i).getTime() * 1000L);
        if (calendar.get(Calendar.HOUR_OF_DAY) < 10){
            return "0" + calendar.get(Calendar.HOUR_OF_DAY) + " : 00";
        }else {
            return calendar.get(Calendar.HOUR_OF_DAY) + " : 00";
        }


    }
    private int getIcon(String s){
        return new GetWeatherIcon(s).getIcon();
    }

    private String getTemp(Double d){
        return String.valueOf( d.intValue()) + "°C";
    }
}
