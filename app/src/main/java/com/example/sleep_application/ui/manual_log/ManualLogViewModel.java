package com.example.sleep_application.ui.manual_log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManualLogViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ManualLogViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is manual log fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}