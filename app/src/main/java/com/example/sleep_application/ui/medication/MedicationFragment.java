package com.example.sleep_application.ui.medication;

import androidx.lifecycle.ViewModelProvider;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.sleep_application.R;
import com.example.sleep_application.database.LocalSqlDbService;
import com.example.sleep_application.databinding.FragmentLoginBinding;
import com.example.sleep_application.databinding.FragmentMedicationBinding;
import com.example.sleep_application.ui.login.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.Instant;
import java.util.Calendar;

public class MedicationFragment extends Fragment {

    private @NonNull FragmentMedicationBinding binding;

    private static final int[] BREATH_IMG = {R.drawable.img1, R.drawable.img1, R.drawable.img1};
    private static final int[] STRETCH_IMG = {R.drawable.img2, R.drawable.img2, R.drawable.img2};
    private static final int[] AEROBIC_IMG = {R.drawable.img3, R.drawable.img3, R.drawable.img3};
    private static final int SLIDE_DURATION = 3000; // milliseconds

    private int med_seconds = 0;
    private boolean running;
    private boolean wasRunning;
    private long stopTime = Instant.now().getEpochSecond();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MedicationViewModel medicationViewModel =
                new ViewModelProvider(this).get(MedicationViewModel.class);

        binding = FragmentMedicationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMedTimer;
        medicationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // setup dropdown menu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.array_medication, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.medicationSpinner.setAdapter((adapter));

        binding.medicationSlideshow.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right));
        binding.medicationSlideshow.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left));

        binding.medicationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switchImgSet(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection case (optional)
            }
        });
        binding.medStartBtn.setOnClickListener(this::onClickStart);


        startSlideShow(); // scroll through medication guide img

        return root;
    }

    private void startSlideShow() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                binding.medicationSlideshow.showNext();
                handler.postDelayed(this, SLIDE_DURATION);
            }
        };
        handler.postDelayed(runnable, SLIDE_DURATION);
    }

    private void onClickStart(View view) {
        // play m06
    }

    private void switchImgSet(Integer position){
        binding.medicationSlideshow.removeAllViews();
        int[] IMG_SET = {};

        switch (position) {
            case 0:
                IMG_SET = BREATH_IMG;
                break;
            case 1:
                IMG_SET = STRETCH_IMG;
                break;
            case 2:
                IMG_SET = AEROBIC_IMG;
                break;
        }

        for (int imageId : IMG_SET) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(imageId);
            binding.medicationSlideshow.addView(imageView);
        }
    }


}