package mcanddev.minimalisticweather.service;


import android.support.annotation.NonNull;
import android.util.Log;
import com.evernote.android.job.Job;

public class MySyncJob extends Job {
    public static final String TAG = "weather";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        Log.d("JOB", "15 minutes");



        return Result.SUCCESS;
    }


}
