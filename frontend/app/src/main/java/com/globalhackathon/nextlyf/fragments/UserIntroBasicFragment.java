package com.globalhackathon.nextlyf.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.globalhackathon.nextlyf.databinding.UserIntroBasicBinding;
import com.globalhackathon.nextlyf.model.UserSignUpData;

public class UserIntroBasicFragment extends Fragment {

    UserIntroBasicBinding userIntroBasicBinding;

    UserSignUpData userSignUpData;

    public UserIntroBasicFragment(UserSignUpData userSignUpData) {
        this.userSignUpData = userSignUpData;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userIntroBasicBinding = UserIntroBasicBinding.inflate(inflater);

        userIntroBasicBinding.userIntroName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userIntroBasicBinding.userIntroName.setText(editable.toString());
            }
        });

        userIntroBasicBinding.userIntroDob.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userIntroBasicBinding.userIntroDob.setText(editable.toString());
            }
        });

        userIntroBasicBinding.userIntroGender.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userIntroBasicBinding.userIntroGender.setText(editable.toString());
            }
        });

        userIntroBasicBinding.userIntroPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userIntroBasicBinding.userIntroPhone.setText(editable.toString());
            }
        });

        userIntroBasicBinding.userIntroLanguage.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userIntroBasicBinding.userIntroLanguage.setText(editable.toString());
            }
        });

        userIntroBasicBinding.userIntroOriginCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userIntroBasicBinding.userIntroOriginCountry.setText(editable.toString());
            }
        });

        userIntroBasicBinding.userIntroOriginCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userIntroBasicBinding.userIntroOriginCity.setText(editable.toString());
            }
        });

        userIntroBasicBinding.userIntroReferralCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userIntroBasicBinding.userIntroReferralCode.setText(editable.toString());
            }
        });


        return userIntroBasicBinding.getRoot();
    }
}
