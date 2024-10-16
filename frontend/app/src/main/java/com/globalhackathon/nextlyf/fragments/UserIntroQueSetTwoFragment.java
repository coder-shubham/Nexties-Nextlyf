package com.globalhackathon.nextlyf.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.globalhackathon.nextlyf.databinding.UserIntroQueSetTwoBinding;
import com.globalhackathon.nextlyf.model.UserSignUpData;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class UserIntroQueSetTwoFragment extends Fragment {


    UserIntroQueSetTwoBinding userIntroQueSetTwoBinding;

    UserSignUpData userSignUpData;

    private List<String> selectedChipTexts = new ArrayList<>();

    public UserIntroQueSetTwoFragment(UserSignUpData userSignUpData) {
        this.userSignUpData = userSignUpData;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userIntroQueSetTwoBinding = UserIntroQueSetTwoBinding.inflate(inflater);

        ChipGroup chipGroup = userIntroQueSetTwoBinding.chipGroup;

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);

            // Set a listener to detect when a chip's checked state changes
            chip.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    // Chip is selected, add its text to the list
                    selectedChipTexts.add(chip.getText().toString());
                } else {
                    // Chip is unselected, remove its text from the list
                    selectedChipTexts.remove(chip.getText().toString());
                }
                // You can now use the selectedChipTexts list as needed
                Log.d("SelectedChips", selectedChipTexts.toString());
                userSignUpData.setInterests(selectedChipTexts);
            });
        }

        return userIntroQueSetTwoBinding.getRoot();
    }
}