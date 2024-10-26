package com.globalhackathon.nextlyf.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class CustomRecyclerView extends RecyclerView {

    private ViewPager2 viewPager2;

    public CustomRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // Set the ViewPager2 instance
    public void setViewPager2(ViewPager2 viewPager2) {
        this.viewPager2 = viewPager2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Check if the event is in the bounds of the RecyclerView (Carousel)
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Disable ViewPager2 swipe when interacting with RecyclerView (carousel)
                if (viewPager2 != null && isTouchInBounds(event)) {
                    viewPager2.setUserInputEnabled(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Re-enable ViewPager2 swipe after the touch interaction ends
                if (viewPager2 != null) {
                    viewPager2.setUserInputEnabled(true);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    // Helper method to check if touch is within the bounds of the RecyclerView
    private boolean isTouchInBounds(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        // Get RecyclerView bounds
        int[] location = new int[2];
        this.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + this.getWidth();
        int bottom = top + this.getHeight();

        // Check if the touch event is within the bounds of the RecyclerView
        return x > left && x < right && y > top && y < bottom;
    }
}


