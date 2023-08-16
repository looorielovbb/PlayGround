package com.youth.banner.config;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class IndicatorConfig {

    private int indicatorSize;
    private int currentPosition;
    private int gravity = Direction.CENTER;
    private int indicatorSpace = BannerConfig.INDICATOR_SPACE;
    private int normalWidth = BannerConfig.INDICATOR_NORMAL_WIDTH;
    private int selectedWidth = BannerConfig.INDICATOR_SELECTED_WIDTH;
    @ColorInt
    private int normalColor = BannerConfig.INDICATOR_NORMAL_COLOR;
    @ColorInt
    private int selectedColor = BannerConfig.INDICATOR_SELECTED_COLOR;

    private int radius = BannerConfig.INDICATOR_RADIUS;
    private int height = BannerConfig.INDICATOR_HEIGHT;

    private Margins margins;

    //是将指示器添加到banner上
    private boolean attachToBanner = true;

    @IntDef({Direction.LEFT, Direction.CENTER, Direction.RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
        int LEFT = 0;
        int CENTER = 1;
        int RIGHT = 2;
    }

    public static class Margins {
        public final int leftMargin;
        public final int topMargin;
        public final int rightMargin;
        public final int bottomMargin;

        public Margins() {
            this(BannerConfig.INDICATOR_MARGIN);
        }

        public Margins(int marginSize) {
            this(marginSize, marginSize, marginSize, marginSize);
        }

        public Margins(int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
            this.leftMargin = leftMargin;
            this.topMargin = topMargin;
            this.rightMargin = rightMargin;
            this.bottomMargin = bottomMargin;
        }
    }

    public Margins getMargins() {
        if (margins == null) {
            setMargins(new Margins());
        }
        return margins;
    }

    public void setMargins(Margins margins) {
        this.margins = margins;
    }

    public int getIndicatorSize() {
        return indicatorSize;
    }

    public void setIndicatorSize(int indicatorSize) {
        this.indicatorSize = indicatorSize;
    }

    public int getNormalColor() {
        return normalColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public int getIndicatorSpace() {
        return indicatorSpace;
    }

    public void setIndicatorSpace(int indicatorSpace) {
        this.indicatorSpace = indicatorSpace;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getNormalWidth() {
        return normalWidth;
    }

    public void setNormalWidth(int normalWidth) {
        this.normalWidth = normalWidth;
    }

    public int getSelectedWidth() {
        return selectedWidth;
    }

    public void setSelectedWidth(int selectedWidth) {
        this.selectedWidth = selectedWidth;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(@Direction int gravity) {
        this.gravity = gravity;
    }

    public boolean isAttachToBanner() {
        return attachToBanner;
    }

    public void setAttachToBanner(boolean attachToBanner) {
        this.attachToBanner = attachToBanner;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
