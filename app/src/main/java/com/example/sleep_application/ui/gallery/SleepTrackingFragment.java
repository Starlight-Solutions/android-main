package com.example.sleep_application.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sleep_application.databinding.FragmentSleepTrackingBinding;

public class SleepTrackingFragment extends Fragment {

    private FragmentSleepTrackingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SleepTrackingViewModel sleepTrackingViewModel =
                new ViewModelProvider(this).get(SleepTrackingViewModel.class);

        binding = FragmentSleepTrackingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.timeView;
        sleepTrackingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}