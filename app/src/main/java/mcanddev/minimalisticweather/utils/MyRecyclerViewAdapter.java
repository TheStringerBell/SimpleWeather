package mcanddev.minimalisticweather.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import mcanddev.minimalisticweather.R;
import mcanddev.minimalisticweather.pojo.Places;
import mcanddev.minimalisticweather.ui.MainViewInterface;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> myList;
    private MainViewInterface.recycleView presenter;

    public MyRecyclerViewAdapter(ArrayList<String> myList, MainViewInterface.recycleView presenter){
        this.myList = myList;
        this.presenter = presenter;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.place.setText(myList.get(position));
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_row, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView place;
        private ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            place = itemView.findViewById(R.id.recycleTextView);
        }

        @Override
        public void onClick(View view) {
            presenter.getPlace(getLayoutPosition());
        }
    }

}

