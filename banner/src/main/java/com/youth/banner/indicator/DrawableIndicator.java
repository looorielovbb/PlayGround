package com.youth.banner.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.youth.banner.R;


/**
 * Drawable指示器
 */
@SuppressWarnings("unused")
public class DrawableIndicator extends BaseIndicator {
    private final Bitmap normalBitmap;
    private final Bitmap selectedBitmap;

    /**
     * 实例化Drawable指示器 ，也可以通过自定义属性设置
     */
    public DrawableIndicator(Context context, @DrawableRes int normalResId, @DrawableRes int selectedResId) {
        super(context);
        normalBitmap = BitmapFactory.decodeResource(getResources(), normalResId);
        selectedBitmap = BitmapFactory.decodeResource(getResources(), selectedResId);
    }

    public DrawableIndicator(Context context) {
        this(context, null);
    }

    public DrawableIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DrawableIndicator);
        BitmapDrawable normal = (BitmapDrawable) array.getDrawable(R.styleable.DrawableIndicator_normal_drawable);
        BitmapDrawable selected = (BitmapDrawable) array.getDrawable(R.styleable.DrawableIndicator_selected_drawable);
        normalBitmap = normal != null ? normal.getBitmap() : null;
        selectedBitmap = selected != null ? selected.getBitmap() : null;
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = config.getIndicatorSize();
        if (count <= 1) {
            return;
        }
        setMeasuredDimension(selectedBitmap.getWidth() * (count - 1) + selectedBitmap.getWidth() + config.getIndicatorSpace() * (count - 1), Math.max(normalBitmap.getHeight(), selectedBitmap.getHeight()));
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        int count = config.getIndicatorSize();
        if (count <= 1 || normalBitmap == null || selectedBitmap == null) {
            return;
        }

        float left = 0;
        for (int i = 0; i < count; i++) {
            canvas.drawBitmap(config.getCurrentPosition() == i ? selectedBitmap : normalBitmap, left, 0, mPaint);
            left += normalBitmap.getWidth() + config.getIndicatorSpace();
        }
    }
}
