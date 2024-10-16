package com.globalhackathon.nextlyf.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.globalhackathon.nextlyf.databinding.UserIntroFinalBinding;
import com.globalhackathon.nextlyf.listeners.UserIntroFinalListener;
import com.globalhackathon.nextlyf.model.UserSignUpData;

public class UserIntroFinalFragment extends Fragment {


    UserIntroFinalBinding userIntroFinalBinding;

    UserSignUpData userSignUpData;

    public UserIntroFinalFragment(UserSignUpData userSignUpData) {
        this.userSignUpData = userSignUpData;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userIntroFinalBinding = UserIntroFinalBinding.inflate(inflater);

        return userIntroFinalBinding.getRoot();



    }
}