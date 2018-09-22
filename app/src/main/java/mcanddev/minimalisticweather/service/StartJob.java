package mcanddev.minimalisticweather.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.*;

import java.util.concurrent.TimeUnit;


public class StartJob {
//    private Context context;
//    private String lat;
//    private String lon;
//    private String units;
//
//    public StartJob(Context context, String lat, String lon, String units){
//        this.context = context;
//        this.lat = lat;
//        this.units = units;
//        this.lon = lon;
//
//    }


    public static void scheduleJob(){
        new JobRequest.Builder(CreateJob.TAG)
                .setPeriodic(TimeUnit.HOURS.toMillis(4))
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .build()

                .schedule();
    }
    }


