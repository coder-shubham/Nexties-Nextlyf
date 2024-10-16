package com.globalhackathon.nextlyf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.globalhackathon.nextlyf.api.APIManager;
import com.globalhackathon.nextlyf.databinding.ActivitySignUpBinding;
import com.globalhackathon.nextlyf.fragments.OTPFragment;
import com.globalhackathon.nextlyf.listeners.OTPResponseListener;
import com.globalhackathon.nextlyf.listeners.OTPVerificationListener;
import com.globalhackathon.nextlyf.model.OTPResponse;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity implements OTPVerificationListener {


    ActivitySignUpBinding signUpBinding;

    FragmentManager fragmentManager;
    OTPFragment otpFragment;
    APIManager apiManager;
//    private TextInputEditText dobEditText;
//    private Calendar calendar;
    private RadioButton[] radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        apiManager = new APIManager();
        fragmentManager = getSupportFragmentManager();


        signUpBinding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signUpBinding.registerEmailText.getText().toString();
                String password = signUpBinding.registerPassText.getText().toString();

                if (email.isEmpty()) {
                    signUpBinding.registerEmailField.setError("Email is required");
                } else if (password.isEmpty()) {
                    signUpBinding.registerPasswordField.setError("Password is required");
                } else {
                    signUpBinding.registerProgressBar.setVisibility(View.VISIBLE);
                    signUpBinding.registerProgressBar.setIndeterminate(true);
                    signUpBinding.registerEmailField.setError(null);
                    signUpBinding.registerPasswordField.setError(null);
                    signUpBinding.registerButton.setEnabled(false);

                    //Call OTP Send API

                    apiManager.sendOTPToUserEmail(email, new OTPResponseListener() {
                        @Override
                        public void onResponse(OTPResponse otpResponse) {

                            //start Fragment to enter OTP

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            otpFragment = new OTPFragment();
                            Bundle args = new Bundle();
                            args.putString("email", email);
                            args.putString("password", password);
//                            args.putString("otp", otpResponse.getOtp());
                            otpFragment.setArguments(args);
                            fragmentTransaction.add(signUpBinding.otpFrameLayout.getId(), otpFragment);
                            signUpBinding.registerLayout.setVisibility(View.GONE);
                            fragmentTransaction.commit();


                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }
            }
        });



    }

    @Override
    public void onOTPVerificationSuccess() {
        signUpBinding.registerProgressBar.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().remove(otpFragment).commit();
        Intent intent = new Intent(SignUpActivity.this, UserIntroActivity.class);
        startActivity(intent);
        finish();
    }
}