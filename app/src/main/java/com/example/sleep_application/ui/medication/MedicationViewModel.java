package com.example.sleep_application.ui.medication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MedicationViewModel extends ViewModel {
    private final MutableLiveData<String> timerText;

    public MedicationViewModel() {
        timerText = new MutableLiveData<>();
        timerText.setValue("TIMER");
    }

    public LiveData<String> getText() {
        return timerText;
    }
}