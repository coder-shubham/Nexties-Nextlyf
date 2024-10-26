package com.globalhackathon.nextlyf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.globalhackathon.nextlyf.adapter.UserIntroAdapter;
import com.globalhackathon.nextlyf.databinding.ActivityUserIntroBinding;
import com.globalhackathon.nextlyf.model.UserSignUpData;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class UserIntroActivity extends AppCompatActivity {

    ActivityUserIntroBinding userIntroBinding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userIntroBinding = ActivityUserIntroBinding.inflate(getLayoutInflater());
        setContentView(userIntroBinding.getRoot());

        String email = getIntent().getStringExtra("email");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        userIntroBinding.finalStepProgressBar.setVisibility(View.GONE);

        ViewPager2 viewPager = userIntroBinding.viewPager;
        LinearProgressIndicator userIntroProgressBar = userIntroBinding.userIntroProgressBar;

        UserIntroAdapter userIntroAdapter = new UserIntroAdapter(this);
        viewPager.setAdapter(userIntroAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                int totalSteps = viewPager.getAdapter().getItemCount();
                int progress = ((position + 1) * 100) / totalSteps;
                userIntroProgressBar.setProgress(progress);

                // Change button text on the last page
                if (position == totalSteps - 1) {
                    userIntroBinding.nextButton.setText("Let's Get Started");
                } else {
                    userIntroBinding.nextButton.setText("Next");
                }

            }
        });


        // Next button logic
        userIntroBinding.nextButton.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            int totalSteps = viewPager.getAdapter().getItemCount();
            if (currentItem < totalSteps - 1) {
                viewPager.setCurrentItem(currentItem + 1); // Move to the next step
            } else {

                userIntroBinding.nextButton.setVisibility(View.GONE);
                userIntroBinding.finalStepProgressBar.setVisibility(View.VISIBLE);

                UserSignUpData userSignUpData = userIntroAdapter.getUserSignUpData();
                userSignUpData.setEmail(email);

                db.collection("users").document(currentUser.getUid()).set(userIntroAdapter.getUserSignUpData()).addOnSuccessListener(aVoid -> {
                    Log.d("UserIntroData", "User data saved successfully");

                    Gson gson = new Gson();
                    String userSignUpDataJson = gson.toJson(userIntroAdapter.getUserSignUpData());

                    // Navigate to the next activity (e.g., Home screen)
                    //Pass information to the next activity
                    Intent intent = new Intent(UserIntroActivity.this, HomeActivity.class);
                    intent.putExtra("userSignUpData", userSignUpDataJson);
                    intent.putExtra("isFromSignUp", true);
                    startActivity(intent);
                    finish();


                }).addOnFailureListener(e -> {
                    userIntroBinding.nextButton.setVisibility(View.VISIBLE);
                    userIntroBinding.finalStepProgressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error saving user data", Toast.LENGTH_SHORT).show();
                    Log.d("UserIntroData", "Error saving user data");
                });

                // Navigate to the next activity (e.g., Login screen)
//                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
            }
        });


    }
}