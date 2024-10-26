package com.globalhackathon.nextlyf.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.LoyaltyPointsPopupBinding;

public class LoyaltyPointsPopUpFragment extends DialogFragment {

    LoyaltyPointsPopupBinding loyaltyPointsPopupBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        loyaltyPointsPopupBinding = LoyaltyPointsPopupBinding.inflate(getLayoutInflater());
        return loyaltyPointsPopupBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.popup);

        loyaltyPointsPopupBinding.getRoot().startAnimation(animation);

        loyaltyPointsPopupBinding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//
//        View view = getLayoutInflater().inflate(R.layout.loyalty_points_popup, null, false);
//
//
//
//
//
//
//
//    }
}
