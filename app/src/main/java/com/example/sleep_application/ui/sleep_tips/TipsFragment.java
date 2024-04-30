package com.example.sleep_application.ui.sleep_tips;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sleep_application.R;
import com.example.sleep_application.database.LocalSqlDbService;
import com.example.sleep_application.database.entity.SleepEntity;
import com.example.sleep_application.databinding.FragmentMusicBinding;
import com.example.sleep_application.databinding.FragmentTipsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class TipsFragment extends Fragment {

    private FragmentTipsBinding binding;

    private TipsViewModel mViewModel;

    LocalSqlDbService dbService;

    ArrayList<SleepEntity> sleepData = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = FragmentTipsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbService = Room.databaseBuilder(requireActivity().getApplicationContext(), LocalSqlDbService.class, "appDb")
                .allowMainThreadQueries().build();


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String email = sharedPref.getString("login_email", "No email");
        sleepData = new ArrayList<>(dbService.sleepDao().getAllByUser(email));
        Log.d("TipsFrag", Integer.toString(sleepData.size()));



        binding.tipsButton.setOnClickListener(this::onClickTips);
        binding.tempButton.setOnClickListener(this::onClickTemp);
        binding.tempButton2.setOnClickListener(this::onClickTemp2);




        return root;
    }

    public void onClickTips(View view) {
        int sleep_size = sleepData.size();
        long seven = sleepData.stream().filter(entity -> 25200 > entity.getDuration()).count();
        long nine = sleepData.stream().filter(entity -> 32400 < entity.getDuration()).count();
        long normal = sleepData.stream().filter(entity -> 25200 > entity.getDuration() && 32400 < entity.getDuration()).count();
        long total_sleep_duration = 0;
        for (SleepEntity entity : sleepData) { total_sleep_duration += entity.getDuration(); }
        double avg = (double) total_sleep_duration / (double) sleep_size / 3600;
        Snackbar.make(view, new StringBuffer("total:" + sleep_size + "  >7:" + seven + " <9:" + nine + " normal:" + normal + "avg(hrs):" + avg), Snackbar.LENGTH_SHORT).show();
        // OO% of your sleeps have desirable length.
        // OO% of your sleeps are less than 7 hours.
        // OO% of your sleeps are more than 9 hours.
        // OO% of your sleeps are outside your regular sleep pattern. (outliers, 3hrs+- outside avg)

        // if sleep_size <= 0, not enough record
        // if <7 >40% => sleep more, try relaxation
        // if >9 >40% => sleep less, (tips to wake up)
        // if both or outliers >40% => need regular sleep
        // else good sleep
    }

    public void onClickTemp(View view) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String email = sharedPref.getString("login_email", "No email");

        SleepEntity sleepEntity = new SleepEntity(email, LocalDate.now(), LocalTime.now(), 32760);
        // 7hrs = 25200 secs
        // 9hrs = 32400 secs
        // total <7hrs, >9hrs, avg

        Log.d("SleepEntity", sleepEntity.toString());
        dbService.sleepDao().insertAll(sleepEntity);
    }

    public void onClickTemp2(View view) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String email = sharedPref.getString("login_email", "No email");

        SleepEntity sleepEntity = new SleepEntity(email, LocalDate.now(), LocalTime.now(), 24000);
        // 7hrs = 25200 secs
        // 9hrs = 32400 secs
        // total <7hrs, >9hrs, avg

        Log.d("SleepEntity", sleepEntity.toString());
        dbService.sleepDao().insertAll(sleepEntity);
    }

}