package mcanddev.minimalisticweather;



import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evernote.android.job.JobManager;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mcanddev.minimalisticweather.pojo.MainList;
import mcanddev.minimalisticweather.pojo.openweather.GetOpenWeather;
import mcanddev.minimalisticweather.service.MyJobCreator;
import mcanddev.minimalisticweather.ui.MainPresenter;
import mcanddev.minimalisticweather.ui.MainViewInterface;
import mcanddev.minimalisticweather.ui.notification.SetupNotification;
import mcanddev.minimalisticweather.utils.GetShared;
import mcanddev.minimalisticweather.utils.MyRecyclerViewAdapter;


public class MainActivity extends AppCompatActivity  implements MainViewInterface.view, MainViewInterface.recycleView{

    @BindView(R.id.search)
    EditText search;


    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.currentPlace)
    TextView currentPlace;

    @BindView(R.id.cels)
    TextView celsiusButton;

    @BindView(R.id.fer)
    TextView ferButton;

    ArrayList<String> arrayList = new ArrayList<>();
    MainPresenter mainPresenter;
    String lat;
    String lon;
    String units;
    String place;
    GetShared getShared;
    RecyclerView.Adapter adapter;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        JobManager.create(this).addJobCreator(new MyJobCreator());
        setupMVP();
    }


    @Override
    public void fillListView(ArrayList<String> arrayLists) {
        if (arrayLists != null){
            arrayList = arrayLists;
            adapter = new MyRecyclerViewAdapter(arrayList, this);
            recyclerView.setAdapter(adapter);
        }
    }

    public void setupMVP(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyRecyclerViewAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
        getShared = new GetShared(this);
        mainPresenter = new MainPresenter(this);
        lat = getShared.getLat();
        lon = getShared.getLon();
        units = getShared.getUnits();
        place = getShared.getPlace();
        currentPlace.setText(place);
        mainPresenter.checkLatitudeAtStartUp(lat, lon, units );  // check if there is saved city in sharedpref

    }

    @Override
    public void getPlace(int i) {
        mainPresenter.getWeatherData(arrayList.get(i), units);
        currentPlace.setText(arrayList.get(i));
        getShared.setPlace(arrayList.get(i));

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
    }


    @Override
    public void setButtonColor(int color, int color2) {
        celsiusButton.setTextColor(color);
        ferButton.setTextColor(color2);
    }

    @Override
    public void setUnits(String s) {
        getShared.setUnits(s);
        units = s;
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null){
            view = new View(this);
        }
        try {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }catch (NullPointerException n){
            n.printStackTrace();
        }
    }
    @OnClick(R.id.button)
        public void submit(View view){
            if (!search.getText().toString().isEmpty()){
            mainPresenter.getAutocompleteResults((search.getText().toString()));
            hideKeyboard();
            }
    }
    @OnClick(R.id.cels)
    public void celsius(View view){
        mainPresenter.getButtonState(true);
    }
    @OnClick(R.id.fer)
    public void fer(View view){
        mainPresenter.getButtonState(false);
    }
}

