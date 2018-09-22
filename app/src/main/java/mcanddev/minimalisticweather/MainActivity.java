package mcanddev.minimalisticweather;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.POJO.OpenWeather.GetOpenWeather;
import mcanddev.minimalisticweather.UI.MainPresenter;
import mcanddev.minimalisticweather.UI.MainViewInterface;
import mcanddev.minimalisticweather.UI.Notification.SetupNotification;
import mcanddev.minimalisticweather.utils.GetShared;


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
    String lat;
    String lon;
    String units;
    GetShared getShared;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupMVP();



        button.setOnClickListener(view ->{
            if (!search.getText().toString().isEmpty()){
                mainPresenter.getPredictionList((search.getText().toString().replace(" ", "%20")));
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
        getShared = new GetShared(this);
        mainPresenter = new MainPresenter(this, getApplicationContext());

        lat = getShared.getLat();
        lon = getShared.getLon();
        units = getShared.getUnits();

        if (!lat.equals("o")) {
            getOnlyWeather();
        }
    }


    public void setListViewClicable(){
        listView.setOnItemClickListener((adapterView, view, i, l) ->
            mainPresenter.combined(arrayAdapter.getItem(i))
        );
    }

    @Override
    public void getWeatherObject(GetOpenWeather getWeather) {
        if (getWeather != null) {

            new SetupNotification(this, getPackageName(), getWeather, units).setupNotifyLayout();

        }
    }




    public void getOnlyWeather(){
        mainPresenter.onlyWeather(lat, lon, units );
    }




}
