package com.globalhackathon.nextlyf.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;
import android.graphics.Color;

public class AudioWaveView extends View {

    private Paint[] paints;
    private float[] waveHeights;
    private float[] targetWaveHeights; // Target heights for smoother animation
    private int waveCount = 15;
    private float waveWidth;
    private float waveSpace;
    private float maxHeight;
    private float speedMultiplier = 1.0f; // Control speed of wave animation
    private boolean isAnimating = false;
    private boolean isSpeaking = false; // Speaking flag to determine wave behavior
    private Random random = new Random();
    private int idleColor = Color.GRAY; // Color when idle
    private int speakingColor = Color.CYAN; // Color when speaking
    private float animationStep = 0.05f; // Step to control smoothness

    private float heightMultiplier = 1.0f; // New: Multiplier for wave height adjustment


    public AudioWaveView(Context context) {
        super(context);
        init();
    }

    public AudioWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AudioWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        waveCount = 15; // Number of waves
        waveHeights = new float[waveCount];
        targetWaveHeights = new float[waveCount]; // New target heights
        paints = new Paint[waveCount];
        waveWidth = 10f; // Wave bar width
        waveSpace = 20f; // Space between wave bars
        maxHeight = 120f; // Max wave height

        // Initialize paints for each wave
        for (int i = 0; i < waveCount; i++) {
            paints[i] = new Paint();
            paints[i].setStyle(Paint.Style.FILL);
            paints[i].setStrokeCap(Paint.Cap.ROUND);
            paints[i].setColor(idleColor); // Initially set to idle color
        }
        generateRandomTargetHeights(); // Generate initial random target heights
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the wave bars
        for (int i = 0; i < waveCount; i++) {
            float centerX = i * (waveWidth + waveSpace) + waveWidth / 2;
            float centerY = getHeight() / 2;
            float halfHeight = waveHeights[i] / 2;

            // Draw the wave bars
            canvas.drawRoundRect(centerX - waveWidth / 2, centerY - halfHeight,
                    centerX + waveWidth / 2, centerY + halfHeight,
                    waveWidth / 2, waveWidth / 2, paints[i]);
        }

        if (isAnimating) {
            animateWave(); // Trigger animation if isAnimating flag is true
        }
    }

    // Method to generate random target heights for the wave bars
    private void generateRandomTargetHeights() {
        float amplitude = isSpeaking ? maxHeight : maxHeight / 4; // Higher waves when speaking
        int center = waveCount / 2; // The center of the wave array

        for (int i = 0; i < waveCount; i++) {
            float distanceFromCenter = Math.abs(center - i); // Distance from the center
            float initialScaleFactor = 3.0f;

            if(distanceFromCenter > 3) {
                initialScaleFactor = 1.5f; // Scale down the outer waves
            }

            float scaleFactor = initialScaleFactor - (distanceFromCenter / (float) center); // Scale based on distance
            targetWaveHeights[i] = (float) (Math.random() * amplitude * (0.5f + 0.5f * scaleFactor));
        }
    }

    // Update the current heights to move towards the target heights
    private void updateWaveHeights() {
        for (int i = 0; i < waveCount; i++) {
            // Smoothly transition to the target heights
            waveHeights[i] += (targetWaveHeights[i] - waveHeights[i]) * animationStep;
        }
    }

    public void startAnimation() {
        isAnimating = true;
        invalidate();
    }

    public void stopAnimation() {
        isAnimating = false;
        invalidate();
    }

    // This method animates the wave, updating its heights and invalidating the view
    private void animateWave() {
        updateWaveHeights(); // Smoothly update wave heights towards target
        invalidate(); // Redraw the view

        // Change target heights after some delay
        postDelayed(() -> {
            generateRandomTargetHeights(); // Update target heights periodically
            animateWave(); // Continue animation
        }, (long) (200 / speedMultiplier)); // Increase delay for slower movement
    }

    // Adjusts the speed of the wave animation
    public void setSpeedMultiplier(float multiplier) {
        this.speedMultiplier = multiplier;
    }

    // Changes the wave's behavior based on speaking state
    public void setSpeaking(boolean speaking) {
        this.isSpeaking = speaking;
        if (speaking) {
            // Set to multi-color when speaking
            for (int i = 0; i < waveCount; i++) {
                paints[i].setColor(randomColor());
            }
        } else {
            // Set to a single color (idleColor) when not speaking
            for (int i = 0; i < waveCount; i++) {
                paints[i].setColor(idleColor);
            }
        }
        invalidate();
    }

    // Generates random colors for the wave when speaking
    private int randomColor() {
        return 0xff000000 | random.nextInt(0xffffff); // RGB colors
    }

    public void setWaveHeightMultiplier(float multiplier) {
        this.heightMultiplier = multiplier;
        for (int i = 0; i < waveCount; i++) {
            waveHeights[i] = targetWaveHeights[i] * heightMultiplier; // Adjust wave height dynamically
        }
        invalidate(); // Redraw the view
    }
}

