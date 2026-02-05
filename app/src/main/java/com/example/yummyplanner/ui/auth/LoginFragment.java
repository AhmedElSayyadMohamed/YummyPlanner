package com.example.yummyplanner.ui.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yummyplanner.R;
import com.example.yummyplanner.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

   FragmentLoginBinding loginBinding ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginBinding = FragmentLoginBinding.inflate(inflater,container,false);

        loginBinding.createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_signup);
            }
        });
        return loginBinding.getRoot();
    }

}