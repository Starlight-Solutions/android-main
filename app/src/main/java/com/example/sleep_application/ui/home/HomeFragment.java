package com.example.sleep_application.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.sleep_application.database.LocalSqlDbService;
import com.example.sleep_application.database.entity.SleepEntity;
import com.example.sleep_application.databinding.FragmentHomeBinding;
import com.example.sleep_application.ui.home.sleeprecyclerview.SleepEntityAdapter;

import java.util.ArrayList;
import java.util.Comparator;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.sleepLogRecycler;

        LocalSqlDbService dbService = Room.databaseBuilder(requireActivity().getApplicationContext(), LocalSqlDbService.class, "appDb")
                .allowMainThreadQueries().build();

        ArrayList<SleepEntity> sleepData = new ArrayList<>(dbService.sleepDao().getAll());

        sleepData.sort(Comparator.comparing(SleepEntity::getDate).thenComparing(SleepEntity::getFinishTime).reversed());


        SleepEntityAdapter sleepEntityAdapter = new SleepEntityAdapter(sleepData, this.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(sleepEntityAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}