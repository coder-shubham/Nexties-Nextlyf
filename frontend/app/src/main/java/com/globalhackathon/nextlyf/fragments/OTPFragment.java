package com.globalhackathon.nextlyf.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.globalhackathon.nextlyf.databinding.OtpFragmentLayoutBinding;
import com.globalhackathon.nextlyf.listeners.OTPVerificationListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class OTPFragment extends Fragment {

    OtpFragmentLayoutBinding otpFragmentLayoutBinding;

    OTPVerificationListener otpVerificationListener;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    String sentOtp = "";
    String email = "";
    String password = "";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof OTPVerificationListener) {
            otpVerificationListener = (OTPVerificationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OTPVerificationListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        otpFragmentLayoutBinding = OtpFragmentLayoutBinding.inflate(inflater);

        otpFragmentLayoutBinding.otpProgressBar.setIndeterminate(false);
        otpFragmentLayoutBinding.otpProgressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();

        sentOtp = "11111";
        if(getArguments() != null) {
            sentOtp = getArguments().getString("otp");
            email = getArguments().getString("email");
            password = getArguments().getString("password");
        }

        otpFragmentLayoutBinding.otpSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = otpFragmentLayoutBinding.otpText.getText().toString();
                if(otp.equals(sentOtp) && !email.isEmpty() && !password.isEmpty()) {
                    // OTP is correct
                    otpFragmentLayoutBinding.otpSubmitButton.setEnabled(false);
                    otpFragmentLayoutBinding.otpProgressBar.setVisibility(View.VISIBLE);
                    otpFragmentLayoutBinding.otpProgressBar.setIndeterminate(true);

                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    currentUser = mAuth.getCurrentUser();
                                    Toast.makeText(getContext(), "User created successfully", Toast.LENGTH_SHORT).show();
                                    otpVerificationListener.onOTPVerificationSuccess();
//                                    database.getReference("users").child(currentUser.getUid()).child("email").setValue(email);
//                                    database.getReference("users").child(currentUser.getUid()).child("password").setValue(password);
//                                    database.getReference("users").child(currentUser.getUid()).child("uid").setValue(currentUser.getUid());
//                                    database.getReference("users").child(currentUser.getUid()).child("name").setValue("");
//                                    database.getReference("users").child(currentUser.getUid()).child("phone").setValue("");
//                                    database.getReference("users").child(currentUser.getUid()).child("dob").setValue("");
                                }
                            }
                        });

                } else {
                    otpFragmentLayoutBinding.otpText.setError("OTP is incorrect");
                    // OTP is incorrect
                }
            }
        });


        return otpFragmentLayoutBinding.getRoot();
    }


}
