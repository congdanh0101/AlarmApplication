package com.example.alarm.Alarm;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.alarm.R;
import com.example.alarm.ViewModel.CreateAlarmVM;
import com.example.alarm.databinding.FragmentCreateAlarmBinding;
import com.example.alarm.model.Alarm;
import com.example.alarm.util.DayUtil;
import com.example.alarm.util.TimePickerUtil;

import java.util.Random;


public class CreateAlarmFragment extends Fragment {

    private static final int REQUEST_CODE = 1;
    private FragmentCreateAlarmBinding binding;
    private boolean isVibrate = false;
    private String tone;
    private Alarm alarm;
    private Ringtone ringtone;
    private CreateAlarmVM createAlarmVM;
    private ImageView iv_callback;

    public CreateAlarmFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            alarm = (Alarm) getArguments().getSerializable(getString(R.string.arg_alarm_obj));
        }
        createAlarmVM = new ViewModelProvider(this).get(CreateAlarmVM.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateAlarmBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        tone = RingtoneManager.getActualDefaultRingtoneUri(this.getContext(), RingtoneManager.TYPE_ALARM).toString();
        ringtone = RingtoneManager.getRingtone(getContext(), Uri.parse(tone));
        binding.fragmentCreatealarmSetToneName.setText(ringtone.getTitle(getContext()));

        iv_callback = binding.ivCallback;
        iv_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_createAlarmFragment_to_alarmFragment);
            }
        });

        if (alarm != null) updateAlarmInfo(alarm);

        binding.fragmentCreatealarmRecurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    binding.fragmentCreatealarmRecurringOptions.setVisibility(View.VISIBLE);
                else
                    binding.fragmentCreatealarmRecurringOptions.setVisibility(View.GONE);
            }
        });

        binding.fragmentCreatealarmScheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alarm != null) updateAlarm();
                else scheduleAlarm();

                Navigation.findNavController(view).navigate(R.id.action_createAlarmFragment_to_alarmFragment);
            }
        });

        binding.fragmentCreatealarmCardSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Sound");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) Uri.parse(tone));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        binding.fragmentCreatealarmVibrateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) isVibrate = true;
                else isVibrate = false;
            }
        });

        binding.fragmentCreatealarmTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                binding.fragmentCreatealarmScheduleAlarmHeading.setText(
                        DayUtil.getDay(TimePickerUtil.getTimePickerHour(timePicker),
                                TimePickerUtil.getTimePickerMinute(timePicker))
                );
            }
        });

        return view;
    }

    private void scheduleAlarm() {
        String alarmTitle = getString(R.string.alarm_title);
        int id = new Random().nextInt(Integer.MAX_VALUE);
        if (!binding.fragmentCreatealarmTitle.getText().toString().trim().isEmpty())
            alarmTitle = binding.fragmentCreatealarmScheduleAlarmHeading.getText().toString().trim();

        Alarm alarm = new Alarm(
                id,
                TimePickerUtil.getTimePickerHour(binding.fragmentCreatealarmTimePicker),
                TimePickerUtil.getTimePickerMinute(binding.fragmentCreatealarmTimePicker),
                alarmTitle,
                true,
                binding.fragmentCreatealarmRecurring.isChecked(),
                binding.fragmentCreatealarmCheckMon.isChecked(),
                binding.fragmentCreatealarmCheckTue.isChecked(),
                binding.fragmentCreatealarmCheckWed.isChecked(),
                binding.fragmentCreatealarmCheckThu.isChecked(),
                binding.fragmentCreatealarmCheckFri.isChecked(),
                binding.fragmentCreatealarmCheckSat.isChecked(),
                binding.fragmentCreatealarmCheckSun.isChecked(),
                tone,
                isVibrate
        );

        createAlarmVM.insertAlarm(alarm);
        alarm.schedule(getContext());
    }

    private void updateAlarm() {
        String alarmTitle = getString(R.string.alarm_title);
        int id = new Random().nextInt(Integer.MAX_VALUE);
        if (!binding.fragmentCreatealarmTitle.getText().toString().trim().isEmpty())
            alarmTitle = binding.fragmentCreatealarmScheduleAlarmHeading.getText().toString().trim();

        Alarm alarm = new Alarm(
                id,
                TimePickerUtil.getTimePickerHour(binding.fragmentCreatealarmTimePicker),
                TimePickerUtil.getTimePickerMinute(binding.fragmentCreatealarmTimePicker),
                alarmTitle,
                true,
                binding.fragmentCreatealarmRecurring.isChecked(),
                binding.fragmentCreatealarmCheckMon.isChecked(),
                binding.fragmentCreatealarmCheckTue.isChecked(),
                binding.fragmentCreatealarmCheckWed.isChecked(),
                binding.fragmentCreatealarmCheckThu.isChecked(),
                binding.fragmentCreatealarmCheckFri.isChecked(),
                binding.fragmentCreatealarmCheckSat.isChecked(),
                binding.fragmentCreatealarmCheckSun.isChecked(),
                tone,
                isVibrate
        );

        createAlarmVM.updateAlarm(alarm);
        alarm.schedule(getContext());
    }

    private void updateAlarmInfo(Alarm alarm) {
        binding.fragmentCreatealarmTitle.setText(alarm.getTitle());
        binding.fragmentCreatealarmTimePicker.setHour(alarm.getHour());
        binding.fragmentCreatealarmTimePicker.setMinute(alarm.getMinute());

        if (alarm.isRecurring()) {
            binding.fragmentCreatealarmRecurring.setChecked(true);
            binding.fragmentCreatealarmRecurringOptions.setVisibility(View.VISIBLE);

            if (alarm.isMonday()) binding.fragmentCreatealarmCheckMon.setChecked(true);
            else binding.fragmentCreatealarmCheckMon.setChecked(false);

            if (alarm.isTuesday()) binding.fragmentCreatealarmCheckTue.setChecked(true);
            else binding.fragmentCreatealarmCheckTue.setChecked(false);

            if (alarm.isWednesday()) binding.fragmentCreatealarmCheckWed.setChecked(true);
            else binding.fragmentCreatealarmCheckWed.setChecked(false);

            if (alarm.isThursday()) binding.fragmentCreatealarmCheckThu.setChecked(true);
            else binding.fragmentCreatealarmCheckThu.setChecked(false);

            if (alarm.isFriday()) binding.fragmentCreatealarmCheckFri.setChecked(true);
            else binding.fragmentCreatealarmCheckFri.setChecked(false);

            if (alarm.isSaturday()) binding.fragmentCreatealarmCheckSat.setChecked(true);
            else binding.fragmentCreatealarmCheckSat.setChecked(false);

            if (alarm.isSunday()) binding.fragmentCreatealarmCheckSun.setChecked(true);
            else binding.fragmentCreatealarmCheckSun.setChecked(false);


            tone = alarm.getTone();
            ringtone = RingtoneManager.getRingtone(getContext(), Uri.parse(tone));
            binding.fragmentCreatealarmSetToneName.setText(ringtone.getTitle(getContext()));

            if (alarm.isVibrate()) binding.fragmentCreatealarmVibrateSwitch.setChecked(true);
            else binding.fragmentCreatealarmVibrateSwitch.setChecked(false);
        } else
            binding.fragmentCreatealarmRecurring.setChecked(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            ringtone = RingtoneManager.getRingtone(getContext(), uri);
            String title = ringtone.getTitle(getContext());
            if (uri != null) {
                tone = uri.toString();
                if (title != null && !title.isEmpty())
                    binding.fragmentCreatealarmSetToneName.setText(title);
            } else {
                binding.fragmentCreatealarmSetToneName.setText("");
            }

        }
    }
}