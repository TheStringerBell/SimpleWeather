package mcanddev.minimalisticweather.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import mcanddev.minimalisticweather.MainActivity;
import mcanddev.minimalisticweather.UI.MainPresenter;
import mcanddev.minimalisticweather.UI.MainViewInterface;
import mcanddev.minimalisticweather.UI.Notification.SetupNotification;


public class CreateJob extends Job{
    public static final String TAG = "weather_job";




    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {









        return Result.SUCCESS;
    }

    public static void scheduleJob(){
        new JobRequest.Builder(CreateJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .setUpdateCurrent(true)
                .build()

                .schedule();
    }


}
