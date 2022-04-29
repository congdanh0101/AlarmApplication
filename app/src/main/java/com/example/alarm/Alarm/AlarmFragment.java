package com.example.alarm.Alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarm.Adapter.AlarmAdapter;
import com.example.alarm.R;
import com.example.alarm.ViewModel.AlarmListVM;
import com.example.alarm.databinding.FragmentAlarmBinding;
import com.example.alarm.model.Alarm;
import com.example.alarm.util.OnToggleAlarmListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AlarmFragment extends Fragment implements OnToggleAlarmListener {

    public static final String LOG_TAG = AlarmFragment.class.getSimpleName();
    private AlarmAdapter adapter;
    private AlarmListVM listVM;
    private RecyclerView recyclerView;
    private FloatingActionButton btn_addAlarm;
    private FragmentAlarmBinding binding;

    public AlarmFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AlarmAdapter(this);
        listVM = new ViewModelProvider(this).get(AlarmListVM.class);
        listVM.getListLiveData().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                if (alarms != null) adapter.setDataAlarm(alarms);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAlarmBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = binding.fragmentListalarmsRecylerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btn_addAlarm = binding.fragmentListalarmsAddAlarm;
        btn_addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_alarmFragment_to_createAlarmFragment);
            }
        });
        return view;
    }

    @Override
    public void onToggle(Alarm alarm) {
        if(alarm.isStarted()){
            alarm.cancelAlarm(getContext());
            listVM.updateAlarm(alarm);
        }else {
            alarm.schedule(getContext());
            listVM.updateAlarm(alarm);
        }
    }

    @Override
    public void onDelete(Alarm alarm) {
        if(alarm.isStarted())
            alarm.cancelAlarm(getContext());
        listVM.deleteAlarm(alarm.getId());
    }

    @Override
    public void onItemClick(Alarm alarm, View view) {
        if(alarm.isStarted())
            alarm.cancelAlarm(getContext());
        Bundle bundle = new Bundle();
        bundle.putSerializable(getContext().getString(R.string.arg_alarm_obj),alarm);
        Navigation.findNavController(view).navigate(R.id.action_alarmFragment_to_createAlarmFragment);
    }
}