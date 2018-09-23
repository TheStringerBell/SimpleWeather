package mcanddev.minimalisticweather;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import mcanddev.minimalisticweather.pojo.MainList;
import mcanddev.minimalisticweather.pojo.Places;
import mcanddev.minimalisticweather.pojo.openweather.GetOpenWeather;
import mcanddev.minimalisticweather.ui.MainPresenter;
import mcanddev.minimalisticweather.ui.MainViewInterface;
import mcanddev.minimalisticweather.ui.notification.SetupNotification;
import mcanddev.minimalisticweather.utils.GetShared;
import mcanddev.minimalisticweather.utils.MyRecyclerViewAdapter;


public class MainActivity extends AppCompatActivity  implements MainViewInterface.view, MainViewInterface.recycleView{

    @BindView(R.id.search)
    EditText search;

    @BindView(R.id.button)
    AppCompatButton button;

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    ArrayList<String> arrayList = new ArrayList<>();
    MainPresenter mainPresenter;
    String lat;
    String lon;
    String units;
    GetShared getShared;
    boolean restart = false;
    RecyclerView.Adapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
//        LeakCanary.install(getApplication());
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
            adapter = new MyRecyclerViewAdapter(arrayList, this);
            recyclerView.setAdapter(adapter);

        }

    }

    public void setupMVP(){

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getShared = new GetShared(this);
        mainPresenter = new MainPresenter(this);
        lat = getShared.getLat();
        lon = getShared.getLon();
        units = getShared.getUnits();
        if (!lat.equals("o") && !restart) {
            mainPresenter.getOnlyWeather(lat, lon, units );
        }
    }

    @Override
    public void getPlace(int i) {
        mainPresenter.getWeatherData(arrayList.get(i));

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getWeatherObject(GetOpenWeather getWeather) {
        if (getWeather != null) {
            new SetupNotification(this, getPackageName(), getWeather, units).setupNotifyLayout();
        }
    }

    @Override
    public void setSharedPref(String lat, String lon) {
            getShared.setLat(lat);
            getShared.setLon(lon);
//            getShared.setLat(units);

    }

    @Override
    protected void onPause() {
        restart = true;
        super.onPause();
    }


}
