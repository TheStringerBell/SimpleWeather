package mcanddev.minimalisticweather.service;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;


public class CreateJob extends Job {
    public static final String TAG = "weatcher_job";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {


        return Result.SUCCESS;
    }

    public static void scheduleJob(){
        new JobRequest.Builder(TAG)
                .setPeriodic(TimeUnit.SECONDS.toMillis(10))
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .build()

                .schedule();
    }
}
