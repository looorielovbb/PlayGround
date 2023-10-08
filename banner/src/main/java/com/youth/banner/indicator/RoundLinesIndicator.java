package com.youth.banner.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RoundLinesIndicator extends BaseIndicator {
    protected RectF rectF;
    public RoundLinesIndicator(Context context) {
        this(context, null);
    }

    public RoundLinesIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundLinesIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        mPaint.setStyle(Paint.Style.FILL);
    }
    void init(){
        mPaint.setColor(config.getNormalColor());
        rectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = config.getIndicatorSize();
        if (count <= 1) return;
        setMeasuredDimension(config.getSelectedWidth() * count, config.getHeight());
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        int count = config.getIndicatorSize();
        if (count <= 1) return;
        rectF.set(0, 0, getWidth(), config.getHeight());
        canvas.drawRoundRect(rectF, config.getRadius(), config.getRadius(), mPaint);
        int left = config.getCurrentPosition() * config.getSelectedWidth();
        mPaint.setColor(config.getSelectedColor());
        rectF.set(left, 0, left + config.getSelectedWidth(), config.getHeight());
        canvas.drawRoundRect(rectF, config.getRadius(), config.getRadius(), mPaint);
    }
}
