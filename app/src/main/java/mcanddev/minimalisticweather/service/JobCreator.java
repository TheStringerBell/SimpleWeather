package mcanddev.minimalisticweather.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;



public class JobCreator implements com.evernote.android.job.JobCreator{

    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag){
            case CreateJob.TAG:
                return new CreateJob();
            default: return null;
        }

    }
}
