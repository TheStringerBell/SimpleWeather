package mcanddev.minimalisticweather;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import mcanddev.minimalisticweather.POJO.MainList;
import mcanddev.minimalisticweather.RetModel.RetrofitClient;
import mcanddev.minimalisticweather.UI.MainPresenter;
import mcanddev.minimalisticweather.UI.MainViewInterface;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setList();
        setupMVP();
        getNames();


        new RetrofitClient().getPredictionList();

    }

    public void setList(){



}

    @Override
    public void fillListView(MainList mainList) {
        if (mainList != null){
            for (int i = 0; i < mainList.getPredictions().size(); i++){
                arrayList.add(mainList.getPredictions().get(i).getDescription());
            }
            arrayAdapter = new ArrayAdapter<>(this, R.layout.row, arrayList);
            listView.setAdapter(arrayAdapter);

        }

    }

    public void setupMVP(){
        mainPresenter = new MainPresenter(this);
    }

    public void getNames(){
        mainPresenter.getPredictionList("Poprad");
    }
}
