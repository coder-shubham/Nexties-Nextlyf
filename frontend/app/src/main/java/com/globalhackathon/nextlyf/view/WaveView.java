package com.globalhackathon.nextlyf.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class WaveView extends View {

    private final Path wavePath = new Path();
    private final Paint wavePaint = new Paint();
    private final float waveHeight = 100f;
    private final float waveSpeed = 10f;
    private float waveOffset = 0f;

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        wavePaint.setColor(Color.BLUE); // Customize the wave color
        wavePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Calculate the wave path
        wavePath.reset();
        wavePath.moveTo(0f, getHeight());
        for (int x = 0; x < getWidth(); x++) {
            float y = waveHeight * (float) Math.sin(x / getWidth() * 2 * Math.PI + waveOffset);
            wavePath.lineTo(x, getHeight() - y);
        }
        wavePath.lineTo(getWidth(), getHeight());
        wavePath.lineTo(0f, getHeight());

        // Draw the wave
        canvas.drawPath(wavePath, wavePaint);

        // Update the wave offset for animation
        waveOffset += waveSpeed;
        invalidate();
    }
}
