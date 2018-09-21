package mcanddev.minimalisticweather.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.*;



public class StartJob {
    private Context context;

    public StartJob(Context context){
        this.context = context;

    }


    public void createJob(){
        JobManager.create(context).addJobCreator(tag ->  null);
    }
    }


