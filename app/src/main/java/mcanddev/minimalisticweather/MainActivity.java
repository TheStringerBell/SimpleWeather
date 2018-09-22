package mcanddev.minimalisticweather;



import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.evernote.android.job.JobManager;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.POJO.OpenWeather.GetOpenWeather;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.Datum;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.GetWeather;
import mcanddev.minimalisticweather.UI.MainPresenter;
import mcanddev.minimalisticweather.UI.MainViewInterface;
import mcanddev.minimalisticweather.UI.Notification.SetupNotification;
import mcanddev.minimalisticweather.service.CreateJob;
import mcanddev.minimalisticweather.service.JobCreator;
import mcanddev.minimalisticweather.service.StartJob;



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
    String lat;
    String lon;
    String units;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        JobManager.create(this).addJobCreator(new JobCreator());
        setupMVP();
        createJob();


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
        lat = sp.getString("Lat", "o");
        lon = sp.getString("Lon", "o");
        units = sp.getString("Units", "metric");

        if (!sp.getString("Lat", "o").equals("o")) {

            getOnlyWeather();

        }


    }

    public void getNames(String s){
        mainPresenter.getPredictionList(s);
    }


    public void setListViewClicable(){
        listView.setOnItemClickListener((adapterView, view, i, l) ->
            mainPresenter.combined(arrayAdapter.getItem(i))
        );
    }

    @Override
    public void getWeatherObject(GetOpenWeather getWeather) {
        if (getWeather != null) {
            Log.d("JOB SUCCESS", " ");

            new SetupNotification(this, getPackageName(), getWeather, units).setupNotifyLayout();

        }
    }

    public void createJob(){
        if (!lat.equals("o")){
            CreateJob.scheduleJob();
        }

    }


    public void getOnlyWeather(){
        mainPresenter.onlyWeather(lat, lon, units );
    }




}
