package mcanddev.minimalisticweather;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mcanddev.minimalisticweather.API.ApiKeys;
import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.POJO.WeatherPOJO.GetWeather;
import mcanddev.minimalisticweather.RetModel.Interface.RetrofitInterface;
import mcanddev.minimalisticweather.RetModel.RetrofitClient;

import mcanddev.minimalisticweather.UI.MainPresenter;
import mcanddev.minimalisticweather.UI.MainViewInterface;
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
    String s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(1537354800*1000L);
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        Log.d("TIME", " " + hour +" : " + "00");


        setupMVP();
//        Ok();
//        mainPresenter.getLocationString("Poprad");





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
        mainPresenter = new MainPresenter(this);

    }

    public void getNames(String s){
        mainPresenter.getPredictionList(s);
    }


    public void setListViewClicable(){
        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            mainPresenter.getLocationString(arrayAdapter.getItem(i));


        });
    }

    @Override
    public void getWeatherObject(GetWeather getWeather) {

    }
}
