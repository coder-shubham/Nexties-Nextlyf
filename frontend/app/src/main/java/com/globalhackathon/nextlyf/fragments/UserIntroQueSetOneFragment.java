package com.globalhackathon.nextlyf.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.globalhackathon.nextlyf.R;
import com.globalhackathon.nextlyf.databinding.UserIntroQueSetOneBinding;
import com.globalhackathon.nextlyf.model.UserSignUpData;
import com.google.android.material.textfield.TextInputLayout;

public class UserIntroQueSetOneFragment extends Fragment {

    UserIntroQueSetOneBinding userIntroQueSetOneBinding;

    UserSignUpData userSignUpData;

    RadioButton[] radioButtons;

    public UserIntroQueSetOneFragment(UserSignUpData userSignUpData) {
        this.userSignUpData = userSignUpData;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userIntroQueSetOneBinding = UserIntroQueSetOneBinding.inflate(inflater);

        RadioButton radioTech = userIntroQueSetOneBinding.userIndustryTech;
        RadioButton radioBusiness = userIntroQueSetOneBinding.userIndustryBusiness;
        RadioButton radioArts = userIntroQueSetOneBinding.userIndustryArts;
        RadioButton radioMedia = userIntroQueSetOneBinding.userIndustryMedia;
        RadioButton radioLegal = userIntroQueSetOneBinding.userIndustryLegal;
        RadioButton radioEducation = userIntroQueSetOneBinding.userIndustryEducation;
        RadioButton radioScience = userIntroQueSetOneBinding.userIndustryScience;
        RadioButton radioRetail = userIntroQueSetOneBinding.userIndustryRetail;
        RadioButton radioHospitality = userIntroQueSetOneBinding.userIndustryHospitality;
        RadioButton radioHealth = userIntroQueSetOneBinding.userIndustryHealthcare;
        RadioButton radioOther = userIntroQueSetOneBinding.userIndustryOthers;
        radioButtons = new RadioButton[]{radioTech, radioBusiness, radioArts, radioMedia, radioLegal, radioEducation, radioScience, radioRetail, radioHospitality, radioHealth, radioOther};
        for (RadioButton radioButton : radioButtons) {
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (RadioButton button : radioButtons) {
                        if (button.getId() == radioButton.getId()) {
                            button.setChecked(true);
                            button.setElevation(0);
                            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.baseline_check_24);
//                            button.setCompoundDrawables(drawable, null, null, null);
                            button.setButtonDrawable(drawable);
                            userSignUpData.setProfession(button.getText().toString());
//                            button.setCompoundDrawables(drawable, null, null, null);
                        } else {
                            button.setButtonDrawable(null);
                            button.setChecked(false);
                            button.setElevation(4 * getResources().getDisplayMetrics().density);
                        }
                        if(radioButton.getId() == R.id.userIndustryOthers) {
                            TextInputLayout otherIndustryLayout = userIntroQueSetOneBinding.userIndustryOthersLayout;
                            otherIndustryLayout.setVisibility(View.VISIBLE);
                        }else{
                            TextInputLayout otherIndustryLayout = userIntroQueSetOneBinding.userIndustryOthersLayout;
                            otherIndustryLayout.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }


        userIntroQueSetOneBinding.userIndustryOthersText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userSignUpData.setProfession(editable.toString());
            }
        });


        return userIntroQueSetOneBinding.getRoot();
    }
}
