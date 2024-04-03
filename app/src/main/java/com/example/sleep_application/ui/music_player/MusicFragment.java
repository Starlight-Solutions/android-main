package com.example.sleep_application.ui.music_player;

import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sleep_application.MainActivity;
import com.example.sleep_application.R;
import com.example.sleep_application.databinding.FragmentMusicBinding;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {

    private FragmentMusicBinding binding;
    private MediaPlayer mediaPlayer;
    private BackgroundMusicService backgroundMusicService;
    private boolean isBound = false;

    // value for tracking the current track in track list
    private Integer currentMusicNumber = 0;
    final private int minMusicNumber = 0;
    final private int maxMusicNumber = 6;
    List<Integer> musicList = new ArrayList<>();
    private boolean isPlaying = false;

    //setText needs a variable to work
    private String currentTrack = "Current Track: ";

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackgroundMusicService.BackgroundMusicBinder binder = (BackgroundMusicService.BackgroundMusicBinder) service;
            backgroundMusicService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            backgroundMusicService = null;
            isBound = false;
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MusicViewModel loginViewModel = new ViewModelProvider(this).get(MusicViewModel.class);

        binding = FragmentMusicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textMusic;
        loginViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        getActivity().bindService(new Intent(getActivity(), BackgroundMusicService.class), serviceConnection, Context.BIND_AUTO_CREATE);


        musicListSetup();

        updateButtons();

        binding.playBtn.setOnClickListener(this::onClickPlay);
        binding.stopBtn.setOnClickListener(this::onClickStop);
        binding.pauseBtn.setOnClickListener(this::onClickPause);
        binding.previousBtn.setOnClickListener(this::onClickPrevious);
        binding.nextBtn.setOnClickListener(this::onClickNext);

        return root;
    }

    public void onClickPlay(View view) {
        if ( backgroundMusicService != null ) {
            backgroundMusicService.playSong();
            isPlaying = true;
            updateButtons();
            binding.textMusic.setText(currentTrack.concat(currentMusicNumber.toString()));
        }
    }

    public void onClickPause(View view) {
        if ( mediaPlayer.isPlaying() ) {
            mediaPlayer.pause();
            isPlaying = false;
            updateButtons();
            binding.textMusic.setText("Paused");
        }
    }

    public void onClickStop(View view) {
        if ( backgroundMusicService != null ) {
            backgroundMusicService.pauseSong();
            isPlaying = false;
            updateButtons();
            binding.textMusic.setText("Stopped");
        }
    }


    public void onClickPrevious(View view) {
        mediaPlayer.stop();
        if (currentMusicNumber > minMusicNumber) {  // cant go below min amount of track
            currentMusicNumber--;
        }
        binding.textMusic.setText(currentMusicNumber.toString());
        mediaPlayer = MediaPlayer.create(getActivity(), musicList.get(currentMusicNumber));
    }

    public void onClickNext(View view) {
        mediaPlayer.stop();
        if (currentMusicNumber < maxMusicNumber) { // cant go beyond max amount of track
            currentMusicNumber++;
        }
        binding.textMusic.setText(currentMusicNumber.toString());
        mediaPlayer = MediaPlayer.create(getActivity(), musicList.get(currentMusicNumber));
    }

    private void musicListSetup() {
        // add music to track list to choose from
        musicList.add(R.raw.m00);
        musicList.add(R.raw.m01);
        musicList.add(R.raw.m02);
        musicList.add(R.raw.m03);
        musicList.add(R.raw.m04);
        musicList.add(R.raw.m05);
        musicList.add(R.raw.m06);

        // Initialize MediaPlayer with an audio file
        mediaPlayer = MediaPlayer.create(getActivity(), musicList.get(currentMusicNumber));
    }

    private void updateButtons() {
        binding.playBtn.setEnabled(!isPlaying);
        binding.stopBtn.setEnabled(isPlaying);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBound) {
            getActivity().unbindService(serviceConnection);
        }
    }
}