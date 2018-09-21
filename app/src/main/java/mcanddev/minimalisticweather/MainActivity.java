package mcanddev.minimalisticweather;


import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.Datum;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.GetWeather;
import mcanddev.minimalisticweather.RetModel.Interface.RetrofitInterface;
import mcanddev.minimalisticweather.RetModel.RetrofitClient;

import mcanddev.minimalisticweather.UI.MainPresenter;
import mcanddev.minimalisticweather.UI.MainViewInterface;
import mcanddev.minimalisticweather.UI.Notification.SetupNotification;
import mcanddev.minimalisticweather.service.StartJob;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity  implements MainViewInterface{

    @BindView(R.id.search)
    EditText search;

    @BindView(R.id.button)
    AppCompatButton button;

    @BindView(R.id.listView)
    ListView listView;

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList = new ArrayList<>();

    MainPresenter mainPresenter;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupMVP();

//        setupNotifyLayout();

        button.setOnClickListener(view ->{
            if (!search.getText().toString().isEmpty()){
                getNames(search.getText().toString().replace(" ", "%"));
            }
        } );



    }



    @Override
    public void fillListView(MainList mainList) {
        if (mainList != null){
            if (!arrayList.isEmpty()){arrayList.clear();}
            for (int i = 0; i < mainList.getPredictions().size(); i++){
                arrayList.add(mainList.getPredictions().get(i).getDescription());
            }

            arrayAdapter = new ArrayAdapter<>(this, R.layout.row, arrayList);
            listView.setAdapter(arrayAdapter);
            setListViewClicable();


        }

    }

    public void setupMVP(){
        mainPresenter = new MainPresenter(this, getApplicationContext());
        sp = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);

        if (!sp.getString("Lat", "n").equals("n")) {
            mainPresenter.onlyWeather(sp.getString("Lat", "n"), sp.getString("Lon", "n"));
        }


    }

    public void getNames(String s){
        mainPresenter.getPredictionList(s);
    }


    public void setListViewClicable(){
        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            mainPresenter.combined(arrayAdapter.getItem(i));



        });
    }

    @Override
    public void getWeatherObject(GetWeather getWeather) {
        if (getWeather != null) {
            List<Datum> data = getWeather.getHourly().getData();
            new SetupNotification(this, getPackageName(), data, getWeather.getFlags().getUnits()).setupNotifyLayout();

        }

    }

    public void createJob(Context context){
        new StartJob(context).createJob();
    }









}
