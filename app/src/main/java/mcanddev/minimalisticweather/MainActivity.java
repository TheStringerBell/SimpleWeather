package mcanddev.minimalisticweather;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.RetModel.RetrofitClient;
import mcanddev.minimalisticweather.UI.LocationPresenter;
import mcanddev.minimalisticweather.UI.LocationViewPresenter;
import mcanddev.minimalisticweather.UI.MainPresenter;
import mcanddev.minimalisticweather.UI.MainViewInterface;


public class MainActivity extends AppCompatActivity  implements MainViewInterface, LocationViewPresenter{

    @BindView(R.id.search)
    EditText search;

    @BindView(R.id.button)
    AppCompatButton button;

    @BindView(R.id.listView)
    ListView listView;

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList = new ArrayList<>();

    MainPresenter mainPresenter;
    LocationPresenter locationPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setList();
        setupMVP();



        button.setOnClickListener(view ->{
            if (!search.getText().toString().isEmpty()){
                getNames(search.getText().toString().replace(" ", "%"));
            }
        } );



    }

    @Override
    public void finishUrl(GetLocation getLocation) {
        if (getLocation != null){

            String lat = getLocation.getResults().get(0).getGeometry().getLocation().getLat().toString();
            String lng = getLocation.getResults().get(0).getGeometry().getLocation().getLng().toString();
            Toast.makeText(this, lat+" "+lng, Toast.LENGTH_LONG).show();
        }


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
        locationPresenter = new LocationPresenter(this);
    }

    public void getNames(String s){
        mainPresenter.getPredictionList(s);
    }
    public void getLocation(String s){
        locationPresenter.getLocationString(s);
    }

    public void setListViewClicable(){
        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            getLocation(arrayAdapter.getItem(i));

        });
    }
}
