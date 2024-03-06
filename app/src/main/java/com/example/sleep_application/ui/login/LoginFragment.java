package com.example.sleep_application.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sleep_application.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LoginViewModel loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLogin;
        loginViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        binding.loginButton.setOnClickListener(this::onClickLogin);


        return root;
    }

    public void onClickLogin(View view) {
        String username = binding.inputUsername.getText().toString();
        if (username.equals("admin")) { // should check DB for account existence
            // correct password, proceed to the next screen
            binding.textLogin.setText("Login Successful");
        } else {
            binding.textLogin.setText("Username does not exist.");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}