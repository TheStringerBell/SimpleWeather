package mcanddev.minimalisticweather;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import mcanddev.minimalisticweather.pojo.MainList;
import mcanddev.minimalisticweather.pojo.openweather.GetOpenWeather;
import mcanddev.minimalisticweather.ui.MainPresenter;
import mcanddev.minimalisticweather.ui.MainViewInterface;
import mcanddev.minimalisticweather.ui.notification.SetupNotification;
import mcanddev.minimalisticweather.utils.GetShared;


public class MainActivity extends AppCompatActivity  implements MainViewInterface.view{

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
    boolean restart = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(getApplication());
        ButterKnife.bind(this);
        setupMVP();

        button.setOnClickListener(view ->{
            if (!search.getText().toString().isEmpty()){
                mainPresenter.getAutocompleteResults((search.getText().toString().replace(" ", "%20")));
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
        if (!lat.equals("o") && !restart) {
            getOnlyWeather();
        }


    }


    public void setListViewClicable(){
        listView.setOnItemClickListener((adapterView, view, i, l) ->
            mainPresenter.getWeatherData(arrayAdapter.getItem(i))
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


    @Override
    protected void onPause() {
        restart = true;
        super.onPause();
    }


}
