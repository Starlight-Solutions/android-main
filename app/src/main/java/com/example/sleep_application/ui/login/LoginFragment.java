package com.example.sleep_application.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.sleep_application.database.LocalSqlDbService;
import com.example.sleep_application.database.entity.UserEntity;
import com.example.sleep_application.databinding.FragmentLoginBinding;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    LocalSqlDbService dbService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLogin;
        loginViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.loginButton.setOnClickListener(this::onClickLogin);
        binding.newAccButton.setOnClickListener(this::onClockNewAcc);

        dbService = Room.databaseBuilder(requireActivity().getApplicationContext(), LocalSqlDbService.class, "appUserDb")
                .allowMainThreadQueries().build();

        return root;
    }

    public void onClickLogin(View view) {
        // TODO: login to switch current user
    }


    public void onClockNewAcc(View view) {
        String username = binding.inputUsername.getText().toString();
        String email = binding.inputEmail.getText().toString();

        // user name must not be empty and email must be in format
        if (username.matches("\\w+") && email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {

            // email must not exist in database
            if (!dbService.userDao().findPrimaryKeyExists(email)){
                // create new acc
                UserEntity userEntity = new UserEntity(username, email);
                dbService.userDao().insertAll(userEntity);
                Snackbar.make(view, new StringBuffer("User created."), Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(view, new StringBuffer("Email already Exist. Please Login."), Snackbar.LENGTH_SHORT).show();
            }

            Log.d("LoginFragment", dbService.userDao().getAll().toString());


            // TODO: turn current user to this new added one

        } else {
            Snackbar.make(view, new StringBuffer("Invalid Email."), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}