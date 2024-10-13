package com.globalhackathon.nextlyf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.globalhackathon.nextlyf.adapter.OnboardingAdapter;
import com.globalhackathon.nextlyf.model.OnboardingItem;

import java.util.Arrays;
import java.util.List;

public class OnboardingScreen extends AppCompatActivity {

    private ViewPager2 viewPager;
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout dotsLayout;
    private LinearLayout paginationIndicator;
    private Button btnSkip, btnNext;
    private List<OnboardingItem> onboardingItems;

    private ImageView[] indicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        viewPager = findViewById(R.id.viewPager);
        dotsLayout = findViewById(R.id.dotsLayout);
        btnSkip = findViewById(R.id.btnSkip);
        btnNext = findViewById(R.id.btnNext);

        // Initialize onboarding slides
        onboardingItems = Arrays.asList(
                new OnboardingItem(R.drawable.onboarding_connect_new, "Text and Image Here 1"),
                new OnboardingItem(R.drawable.onboarding_connect_new, "Text and Image Here 2"),
                new OnboardingItem(R.drawable.nextlyf_mainlogo, "Text and Image Here 3"),
                new OnboardingItem(R.drawable.nextlyf_mainlogo, "Text and Image Here 4")
        );

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
        viewPager.setAdapter(onboardingAdapter);

        setupDots(onboardingItems.size());

        // Setup indicators (dots)
//        setupIndicators(onboardingItems.size());
//        setCurrentIndicator(0);


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDots(position);
//                setCurrentIndicator(position);

            }
        });


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() < onboardingItems.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    goToLogin();
                }
            }
        });
    }

    // Setup dots for indicating progress
    private void setupDots(int count) {
        ImageView[] dots = new ImageView[count];
        for (int i = 0; i < count; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.dot_inactive);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            dots[i].setLayoutParams(params);
            dotsLayout.addView(dots[i]);
        }
        dots[0].setImageResource(R.drawable.dot_active);
    }

    // Update dots based on slide position
    private void updateDots(int position) {
        int childCount = dotsLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) dotsLayout.getChildAt(i);
            if (i == position) {
                imageView.setImageResource(R.drawable.dot_active);
            } else {
                imageView.setImageResource(R.drawable.dot_inactive);
            }
        }
    }

    private void setupIndicators(int count) {
        indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
               ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < count; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setLayoutParams(layoutParams);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.indicator_inactive));
            paginationIndicator.addView(indicators[i]);
        }
    }

    private void setCurrentIndicator(int index) {
        for (int i = 0; i < indicators.length; i++) {
            if (i == index) {
                indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.indicator_active));
            } else {
                indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.indicator_inactive));
            }
        }
    }

    private void goToLogin() {
//        Toast.makeText(this, "Go to login", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(OnboardingScreen.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}