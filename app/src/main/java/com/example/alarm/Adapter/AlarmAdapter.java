package com.example.alarm.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarm.databinding.ItemAlarmBinding;
import com.example.alarm.model.Alarm;
import com.example.alarm.util.OnToggleAlarmListener;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmVH> {

    private List<Alarm> alarmList;
    private OnToggleAlarmListener listener;
    private ItemAlarmBinding binding;

    public AlarmAdapter(OnToggleAlarmListener listener) {
        this.alarmList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlarmVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemAlarmBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AlarmVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmVH holder, int position) {
        Alarm alarm = alarmList.get(position);
        holder.bind(alarm,listener);
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public void setDataAlarm(List<Alarm> list){
        alarmList = list;
        notifyDataSetChanged();
    }
}
