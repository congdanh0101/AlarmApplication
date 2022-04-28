package com.example.alarm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarm.R;
import com.example.alarm.model.City;

import java.util.ArrayList;

public class WorldClockAdapter extends RecyclerView.Adapter<WorldClockAdapter.ExampleViewHolder> {
    private ArrayList<City> list;
    private onItemClickListener mListener;

    //This interface would be implemented in main activity to get position of the item clicked.
    //The main activity is subscribed to the listeners via this interface.
    public interface onItemClickListener{
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public WorldClockAdapter(ArrayList<City> l) {
        this.list = l;
    }

    //We cant use a non static class inside a static class so we pass the ExampleViewHolder a onclick listener object.
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView cityName;
        public TextView time;
        public ExampleViewHolder(View itemView, onItemClickListener listener) {
            super(itemView);
            cityName = itemView.findViewById(R.id.subCityName);
            time = itemView.findViewById(R.id.subTime);
        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribed_objects,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    //Sets the values of one Example_object held by holder.
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        City current = list.get(position);
        holder.cityName.setText(current.getName());
        holder.time.setText(current.getCurrentTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void changeList(ArrayList<City> l){
        list = l;
    }
}
