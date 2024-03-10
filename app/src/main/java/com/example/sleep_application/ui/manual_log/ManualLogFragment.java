package com.example.sleep_application.ui.manual_log;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sleep_application.MainActivity;
import com.example.sleep_application.R;
import com.example.sleep_application.ui.manual_log.ManualLogViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class ManualLogFragment extends Fragment {

    int hour, minute;
    private com.example.sleep_application.databinding.FragmentManualLogBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ManualLogViewModel manualLogView =
                new ViewModelProvider(this).get(ManualLogViewModel.class);

        binding = com.example.sleep_application.databinding.FragmentManualLogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button startButton = root.findViewById(R.id.manual_log_start);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                popTimePicker(view, binding.manualLogStartTime);
            }
        });

        Button endButton = root.findViewById(R.id.manual_log_end);
        endButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                popTimePicker(view, binding.manualLogEndTime);
            }
        });

        Button saveButton = root.findViewById((R.id.manual_log_save));
        saveButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(view);
            }
        }));

        final TextView textView = binding.textManualLog;

        manualLogView.getText().observe(getViewLifecycleOwner(), textView::setText);


        return root;
    }

    public void popTimePicker(View view, TextView time) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                time.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.BUTTON_NEGATIVE;
        // The themeResId is the ID that gives us the "spinner style" choice of time
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), 16973939, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void saveData(View view){
        Snackbar.make(view, new StringBuffer("Sleep Saved"), Snackbar.LENGTH_SHORT).show();
    }


}
