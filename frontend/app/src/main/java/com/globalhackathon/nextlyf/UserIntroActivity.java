package com.globalhackathon.nextlyf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.globalhackathon.nextlyf.adapter.UserIntroAdapter;
import com.globalhackathon.nextlyf.databinding.ActivityUserIntroBinding;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class UserIntroActivity extends AppCompatActivity {

    ActivityUserIntroBinding userIntroBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userIntroBinding = ActivityUserIntroBinding.inflate(getLayoutInflater());
        setContentView(userIntroBinding.getRoot());

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
                // Navigate to the next activity (e.g., Login screen)
//                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
                Log.d("UserIntroData", userIntroAdapter.getUserSignUpData().getProfession());
                Toast.makeText(this, "Navigating to the next activity", Toast.LENGTH_SHORT).show();
            }
        });


    }
}