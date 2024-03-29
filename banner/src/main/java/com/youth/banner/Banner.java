package com.youth.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.Indicator;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.transformer.MZScaleInTransformer;
import com.youth.banner.transformer.ScaleInTransformer;
import com.youth.banner.util.BannerLifecycleObserver;
import com.youth.banner.util.BannerLifecycleObserverAdapter;
import com.youth.banner.util.BannerUtils;
import com.youth.banner.util.ScrollSpeedManger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;


/**
 * @noinspection unused
 */
public class Banner extends FrameLayout implements BannerLifecycleObserver {
    public static final int INVALID_VALUE = -1;
    private ViewPager2 mViewPager2;
    private AutoLoopTask mLoopTask;
    private OnPageChangeListener mOnPageChangeListener;
    private BannerAdapter<?, ? extends RecyclerView.ViewHolder> mAdapter;
    private Indicator mIndicator;
    private CompositePageTransformer mCompositePageTransformer;
    private BannerOnPageChangeCallback mPageChangeCallback;

    // 是否允许无限轮播（即首尾直接切换）
    private boolean mIsInfiniteLoop = BannerConfig.IS_INFINITE_LOOP;
    // 是否自动轮播
    private boolean mIsAutoLoop = BannerConfig.IS_AUTO_LOOP;
    // 轮播切换间隔时间
    private long mLoopTime = BannerConfig.LOOP_TIME;
    // 轮播切换时间
    private int mScrollTime = BannerConfig.SCROLL_TIME;
    // 轮播开始位置
    private int mStartPosition = 0;
    // banner圆角半径，默认没有圆角
    private float mBannerRadius = 0;
    // banner圆角方向，如果一个都不设置，默认四个角全部圆角
    private boolean mRoundTopLeft, mRoundTopRight, mRoundBottomLeft, mRoundBottomRight;

    // 指示器相关配置
    private int normalWidth = BannerConfig.INDICATOR_NORMAL_WIDTH;
    private int selectedWidth = BannerConfig.INDICATOR_SELECTED_WIDTH;
    private int normalColor = BannerConfig.INDICATOR_NORMAL_COLOR;
    private int selectedColor = BannerConfig.INDICATOR_SELECTED_COLOR;
    private int indicatorGravity = IndicatorConfig.Direction.CENTER;
    private int indicatorSpace;
    private int indicatorMargin;
    private int indicatorMarginLeft;
    private int indicatorMarginTop;
    private int indicatorMarginRight;
    private int indicatorMarginBottom;
    private int indicatorHeight = BannerConfig.INDICATOR_HEIGHT;
    private int indicatorRadius = BannerConfig.INDICATOR_RADIUS;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private int mOrientation = HORIZONTAL;

    // 滑动距离范围
    private int mTouchSlop;
    // 记录触摸的位置（主要用于解决事件冲突问题）
    private float mStartX, mStartY;
    // 是否要拦截事件
    private boolean isIntercept = true;

    //绘制圆角视图
    private Paint mRoundPaint;
    private Paint mImagePaint;

    private void initTypedArray(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Banner);
            mBannerRadius = array.getDimensionPixelSize(R.styleable.Banner_banner_radius, 0);
            mLoopTime = array.getInt(R.styleable.Banner_banner_loop_time, BannerConfig.LOOP_TIME);
            mIsAutoLoop = array.getBoolean(R.styleable.Banner_banner_auto_loop, BannerConfig.IS_AUTO_LOOP);
            mIsInfiniteLoop = array.getBoolean(R.styleable.Banner_banner_infinite_loop, BannerConfig.IS_INFINITE_LOOP);
            normalWidth = array.getDimensionPixelSize(R.styleable.Banner_banner_indicator_normal_width, BannerConfig.INDICATOR_NORMAL_WIDTH);
            selectedWidth = array.getDimensionPixelSize(R.styleable.Banner_banner_indicator_selected_width, BannerConfig.INDICATOR_SELECTED_WIDTH);
            normalColor = array.getColor(R.styleable.Banner_banner_indicator_normal_color, BannerConfig.INDICATOR_NORMAL_COLOR);
            selectedColor = array.getColor(R.styleable.Banner_banner_indicator_selected_color, BannerConfig.INDICATOR_SELECTED_COLOR);
            indicatorGravity = array.getInt(R.styleable.Banner_banner_indicator_gravity, IndicatorConfig.Direction.CENTER);
            indicatorSpace = array.getDimensionPixelSize(R.styleable.Banner_banner_indicator_space, 0);
            indicatorMargin = array.getDimensionPixelSize(R.styleable.Banner_banner_indicator_margin, 0);
            indicatorMarginLeft = array.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginLeft, 0);
            indicatorMarginTop = array.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginTop, 0);
            indicatorMarginRight = array.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginRight, 0);
            indicatorMarginBottom = array.getDimensionPixelSize(R.styleable.Banner_banner_indicator_marginBottom, 0);
            indicatorHeight = array.getDimensionPixelSize(R.styleable.Banner_banner_indicator_height, BannerConfig.INDICATOR_HEIGHT);
            indicatorRadius = array.getDimensionPixelSize(R.styleable.Banner_banner_indicator_radius, BannerConfig.INDICATOR_RADIUS);
            mOrientation = array.getInt(R.styleable.Banner_banner_orientation, HORIZONTAL);
            mRoundTopLeft = array.getBoolean(R.styleable.Banner_banner_round_top_left, false);
            mRoundTopRight = array.getBoolean(R.styleable.Banner_banner_round_top_right, false);
            mRoundBottomLeft = array.getBoolean(R.styleable.Banner_banner_round_bottom_left, false);
            mRoundBottomRight = array.getBoolean(R.styleable.Banner_banner_round_bottom_right, false);
            array.recycle();
        }
        setOrientation(mOrientation);
    }

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initTypedArray(context, attrs);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() / 2;
        mCompositePageTransformer = new CompositePageTransformer();
        mPageChangeCallback = new BannerOnPageChangeCallback();
        mLoopTask = new AutoLoopTask(this);
        mViewPager2 = new ViewPager2(context);
        mViewPager2.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mViewPager2.setOffscreenPageLimit(2);
        mViewPager2.registerOnPageChangeCallback(mPageChangeCallback);
        mViewPager2.setPageTransformer(mCompositePageTransformer);
        ScrollSpeedManger.reflectLayoutManager(this);
        addView(mViewPager2);

        mRoundPaint = new Paint();
        mRoundPaint.setColor(Color.WHITE);
        mRoundPaint.setAntiAlias(true);
        mRoundPaint.setStyle(Paint.Style.FILL);
        mRoundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mImagePaint = new Paint();
        mImagePaint.setXfermode(null);
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        if (mBannerRadius > 0) {
            canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mImagePaint);
            super.dispatchDraw(canvas);
            //绘制外圆环边框圆环
            //默认四个角都设置
            if (!mRoundTopRight && !mRoundTopLeft && !mRoundBottomRight && !mRoundBottomLeft) {
                drawTopLeft(canvas);
                drawTopRight(canvas);
                drawBottomLeft(canvas);
                drawBottomRight(canvas);
                canvas.restore();
                return;
            }
            if (mRoundTopLeft) {
                drawTopLeft(canvas);
            }
            if (mRoundTopRight) {
                drawTopRight(canvas);
            }
            if (mRoundBottomLeft) {
                drawBottomLeft(canvas);
            }
            if (mRoundBottomRight) {
                drawBottomRight(canvas);
            }
            canvas.restore();
        } else {
            super.dispatchDraw(canvas);
        }
    }


    private void initIndicatorAttr() {
        if (indicatorMargin != 0) {
            setIndicatorMargins(new IndicatorConfig.Margins(indicatorMargin));
        } else if (indicatorMarginLeft != 0
                || indicatorMarginTop != 0
                || indicatorMarginRight != 0
                || indicatorMarginBottom != 0) {
            setIndicatorMargins(new IndicatorConfig.Margins(
                    indicatorMarginLeft,
                    indicatorMarginTop,
                    indicatorMarginRight,
                    indicatorMarginBottom));
        }
        if (indicatorSpace > 0) {
            setIndicatorSpace(indicatorSpace);
        }
        if (indicatorGravity != IndicatorConfig.Direction.CENTER) {
            setIndicatorGravity(indicatorGravity);
        }
        if (normalWidth > 0) {
            setIndicatorNormalWidth(normalWidth);
        }
        if (selectedWidth > 0) {
            setIndicatorSelectedWidth(selectedWidth);
        }

        if (indicatorHeight > 0) {
            setIndicatorHeight(indicatorHeight);
        }
        if (indicatorRadius > 0) {
            setIndicatorRadius(indicatorRadius);
        }
        setIndicatorNormalColor(normalColor);
        setIndicatorSelectedColor(selectedColor);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!getViewPager2().isUserInputEnabled()) {
            return super.dispatchTouchEvent(ev);
        }
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_OUTSIDE) {
            start();
        } else if (action == MotionEvent.ACTION_DOWN) {
            stop();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!getViewPager2().isUserInputEnabled() || !isIntercept) {
            return super.onInterceptTouchEvent(event);
        }
        // 记录viewpager2是否被拖动
        boolean mIsViewPager2Drag;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();
                float distanceX = Math.abs(endX - mStartX);
                float distanceY = Math.abs(endY - mStartY);
                if (getViewPager2().getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    mIsViewPager2Drag = distanceX > mTouchSlop && distanceX > distanceY;
                } else {
                    mIsViewPager2Drag = distanceY > mTouchSlop && distanceY > distanceX;
                }
                getParent().requestDisallowInterceptTouchEvent(mIsViewPager2Drag);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    private void setRecyclerViewPadding(int leftItemPadding, int rightItemPadding) {
        RecyclerView recyclerView = (RecyclerView) getViewPager2().getChildAt(0);
        if (getViewPager2().getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
            recyclerView.setPadding(mViewPager2.getPaddingLeft(), leftItemPadding + 1, mViewPager2.getPaddingRight(), rightItemPadding + 1);
        } else {
            recyclerView.setPadding(leftItemPadding, mViewPager2.getPaddingTop(), rightItemPadding, mViewPager2.getPaddingBottom());
        }
        recyclerView.setClipToPadding(false);
    }

    private void drawTopLeft(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, mBannerRadius);
        path.lineTo(0, 0);
        path.lineTo(mBannerRadius, 0);
        path.arcTo(new RectF(0, 0, mBannerRadius * 2, mBannerRadius * 2), -90, -90);
        path.close();
        canvas.drawPath(path, mRoundPaint);
    }

    private void drawTopRight(Canvas canvas) {
        int width = getWidth();
        Path path = new Path();
        path.moveTo(width - mBannerRadius, 0);
        path.lineTo(width, 0);
        path.lineTo(width, mBannerRadius);
        path.arcTo(new RectF(width - 2 * mBannerRadius, 0, width, mBannerRadius * 2), 0, -90);
        path.close();
        canvas.drawPath(path, mRoundPaint);
    }

    private void drawBottomLeft(Canvas canvas) {
        int height = getHeight();
        Path path = new Path();
        path.moveTo(0, height - mBannerRadius);
        path.lineTo(0, height);
        path.lineTo(mBannerRadius, height);
        path.arcTo(new RectF(0, height - 2 * mBannerRadius, mBannerRadius * 2, height), 90, 90);
        path.close();
        canvas.drawPath(path, mRoundPaint);
    }

    private void drawBottomRight(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        Path path = new Path();
        path.moveTo(width - mBannerRadius, height);
        path.lineTo(width, height);
        path.lineTo(width, height - mBannerRadius);
        path.arcTo(new RectF(width - 2 * mBannerRadius, height - 2 * mBannerRadius, width, height), 0, 90);
        path.close();
        canvas.drawPath(path, mRoundPaint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    class BannerOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
        private int mTempPosition = INVALID_VALUE;
        private boolean isScrolled;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int realPosition = BannerUtils.getRealPosition(isInfiniteLoop(), position, getRealCount());
            if (mOnPageChangeListener != null && realPosition == getCurrentItem() - 1) {
                mOnPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }
            if (getIndicator() != null && realPosition == getCurrentItem() - 1) {
                getIndicator().onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (isScrolled) {
                mTempPosition = position;
                int realPosition = BannerUtils.getRealPosition(isInfiniteLoop(), position, getRealCount());
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(realPosition);
                }
                if (getIndicator() != null) {
                    getIndicator().onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //手势滑动中,代码执行滑动中
            if (state == ViewPager2.SCROLL_STATE_DRAGGING || state == ViewPager2.SCROLL_STATE_SETTLING) {
                isScrolled = true;
            } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                //滑动闲置或滑动结束
                isScrolled = false;
                if (mTempPosition != INVALID_VALUE && mIsInfiniteLoop) {
                    if (mTempPosition == 0) {
                        setCurrentItem(getRealCount(), false);
                    } else if (mTempPosition == getItemCount() - 1) {
                        setCurrentItem(1, false);
                    }
                }
            }
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrollStateChanged(state);
            }
            if (getIndicator() != null) {
                getIndicator().onPageScrollStateChanged(state);
            }
        }
    }

    public BannerAdapter<?, ? extends RecyclerView.ViewHolder> getAdapter() {
        return mAdapter;
    }

    private final RecyclerView.AdapterDataObserver mAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (getItemCount() <= 1) {
                stop();
            } else {
                start();
            }
            setIndicatorPageChange();
        }
    };

    private void initIndicator() {
        if (getIndicator() == null || getAdapter() == null) {
            return;
        }
        if (getIndicator().getIndicatorConfig().isAttachToBanner()) {
            removeIndicator();
            addView(getIndicator().getIndicatorView());
        }
        initIndicatorAttr();
        setIndicatorPageChange();
    }

    private void setInfiniteLoop() {
        // 当不支持无限循环时，要关闭自动轮播
        if (!isInfiniteLoop()) {
            isAutoLoop(false);
        }
        setStartPosition(isInfiniteLoop() ? mStartPosition : 0);
    }

    private void setRecyclerViewPadding(int itemPadding) {
        setRecyclerViewPadding(itemPadding, itemPadding);
    }

    /**
     * 设置banner的适配器
     */
    public void setAdapter(@NonNull BannerAdapter<?, ? extends RecyclerView.ViewHolder> adapter) {
        this.mAdapter = adapter;
        if (!isInfiniteLoop()) {
            getAdapter().setIncreaseCount(0);
        }
        getAdapter().registerAdapterDataObserver(mAdapterDataObserver);
        mViewPager2.setAdapter(adapter);
        setCurrentItem(mStartPosition, false);
        initIndicator();
    }


    /**
     * **********************************************************************
     * ------------------------ 对外公开API ---------------------------------*
     * **********************************************************************
     */

    public int getCurrentItem() {
        return getViewPager2().getCurrentItem();
    }

    public int getItemCount() {
        if (getAdapter() != null) {
            return getAdapter().getItemCount();
        }
        return 0;

    }

    public int getScrollTime() {
        return mScrollTime;
    }

    public boolean isInfiniteLoop() {
        return mIsInfiniteLoop;
    }

    public boolean getIntercept() {
        return isIntercept;
    }

    public ViewPager2 getViewPager2() {
        return mViewPager2;
    }

    public Indicator getIndicator() {
        return mIndicator;
    }

    public IndicatorConfig getIndicatorConfig() {
        if (getIndicator() != null) {
            return getIndicator().getIndicatorConfig();
        }
        return null;
    }

    /**
     * 返回banner真实总数
     */
    public int getRealCount() {
        if (getAdapter() != null) {
            return getAdapter().getBannerCount();
        }
        return 0;
    }

    /**
     * 是否要拦截事件
     */
    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }

    /**
     * 禁止手动滑动
     * @param enabled true 允许，false 禁止
     */
    public void setUserInputEnabled(boolean enabled) {
        getViewPager2().setUserInputEnabled(enabled);
    }

    /**
     * 跳转到指定位置（最好在设置了数据后在调用，不然没有意义）
     */
    public void setCurrentItem(int position) {
        setCurrentItem(position, true);
    }

    /**
     * 跳转到指定位置（最好在设置了数据后在调用，不然没有意义）
     */
    public void setCurrentItem(int position, boolean smoothScroll) {
        getViewPager2().setCurrentItem(position, smoothScroll);
    }

    public void setIndicatorPageChange() {
        if (getIndicator() != null) {
            int realPosition = BannerUtils.getRealPosition(isInfiniteLoop(), getCurrentItem(), getRealCount());
            getIndicator().onPageChanged(getRealCount(), realPosition);
        }
    }

    public void removeIndicator() {
        if (getIndicator() != null) {
            removeView(getIndicator().getIndicatorView());
        }
    }


    /**
     * 设置开始的位置 (需要在setAdapter或者setDatas之前调用才有效哦)
     */
    public void setStartPosition(int mStartPosition) {
        this.mStartPosition = mStartPosition;
    }

    public int getStartPosition() {
        return mStartPosition;
    }

    /**
     * 是否允许自动轮播
     * @param isAutoLoop ture 允许，false 不允许
     */
    public void isAutoLoop(boolean isAutoLoop) {
        this.mIsAutoLoop = isAutoLoop;
    }

    /**
     * 添加PageTransformer，可以组合效果
     * {@link ViewPager2.PageTransformer}
     * 如果找不到请导入implementation "androidx.viewpager2:viewpager2:1.0.0"
     */
    public void addPageTransformer(@NonNull ViewPager2.PageTransformer transformer) {
        mCompositePageTransformer.addTransformer(transformer);
    }

    /**
     * 设置PageTransformer，和addPageTransformer不同，这个只支持一种transformer
     */

    public void setPageTransformer(@NonNull ViewPager2.PageTransformer transformer) {
        getViewPager2().setPageTransformer(transformer);
    }

    public void removeTransformer(ViewPager2.PageTransformer transformer) {
        mCompositePageTransformer.removeTransformer(transformer);
    }

    /**
     * 添加 ItemDecoration
     */
    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        getViewPager2().addItemDecoration(decor);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor, int index) {
        getViewPager2().addItemDecoration(decor, index);
    }

    /**
     * 设置轮播间隔时间
     * @param loopTime 时间（毫秒）
     */
    public void setLoopTime(long loopTime) {
        this.mLoopTime = loopTime;
    }

    /**
     * 设置banner的适配器
     *
     * @param isInfiniteLoop 是否支持无限循环
     */
    public void setAdapter(BannerAdapter<?, ? extends RecyclerView.ViewHolder> adapter, boolean isInfiniteLoop) {
        mIsInfiniteLoop = isInfiniteLoop;
        setInfiniteLoop();
        setAdapter(adapter);
    }

    /**
     * 设置轮播滑动过程的时间
     */
    public void setScrollTime(int scrollTime) {
        this.mScrollTime = scrollTime;
    }

    /**
     * 开始轮播
     */
    public void start() {
        if (mIsAutoLoop) {
            stop();
            postDelayed(mLoopTask, mLoopTime);
        }
    }

    /**
     * 停止轮播
     */
    public void stop() {
        if (mIsAutoLoop) {
            removeCallbacks(mLoopTask);
        }
    }

    /**
     * 移除一些引用
     */
    public void destroy() {
        if (getViewPager2() != null && mPageChangeCallback != null) {
            getViewPager2().unregisterOnPageChangeCallback(mPageChangeCallback);
            mPageChangeCallback = null;
        }
        stop();
    }

    /**
     * 设置banner轮播方向
     * @param orientation {@link Orientation}
     */
    public void setOrientation(@Orientation int orientation) {
        getViewPager2().setOrientation(orientation);
    }

    /**
     * 设置banner圆角
     * 默认没有圆角，需要取消圆角把半径设置为0即可
     *
     * @param radius 圆角半径
     */
    public void setBannerRound(float radius) {
        mBannerRadius = radius;
    }

    /**
     * 改变最小滑动距离
     */

    public void setTouchSlop(int mTouchSlop) {
        this.mTouchSlop = mTouchSlop;
    }

    /**
     * 为banner添加画廊效果
     *
     * @param itemWidth  item左右展示的宽度,单位dp
     * @param pageMargin 页面间距,单位dp
     */
    public void setBannerGalleryEffect(int itemWidth, int pageMargin) {
        setBannerGalleryEffect(itemWidth, pageMargin, .85f);
    }

    /**
     * 添加viewpager切换事件
     * <p>
     * 在viewpager2中切换事件{@link ViewPager2.OnPageChangeCallback}是一个抽象类，
     * 为了方便使用习惯这里用的是和viewpager一样的{@link ViewPager.OnPageChangeListener}接口
     * </p>
     */
    public void addOnPageChangeListener(OnPageChangeListener pageListener) {
        this.mOnPageChangeListener = pageListener;
    }

    /**
     * 为banner添加画廊效果
     * @param leftItemWidth  item左展示的宽度,单位dp
     * @param rightItemWidth item右展示的宽度,单位dp
     * @param pageMargin     页面间距,单位dp
     */
    public void setBannerGalleryEffect(int leftItemWidth, int rightItemWidth, int pageMargin) {
        setBannerGalleryEffect(leftItemWidth, rightItemWidth, pageMargin, .85f);
    }

    /**
     * 设置banner圆角(第二种方式，和上面的方法不要同时使用)，只支持5.0以上
     */
    public void setBannerRound2(float radius) {
        BannerUtils.setBannerRound(this, radius);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({HORIZONTAL, VERTICAL})
    public @interface Orientation {
    }

    static class AutoLoopTask implements Runnable {
        private final WeakReference<Banner> reference;

        AutoLoopTask(Banner banner) {
            this.reference = new WeakReference<>(banner);
        }

        @Override
        public void run() {
            Banner banner = reference.get();
            if (banner != null && banner.mIsAutoLoop) {
                int count = banner.getItemCount();
                if (count == 0) {
                    return;
                }
                int next = (banner.getCurrentItem() + 1) % count;
                banner.setCurrentItem(next);
                banner.postDelayed(banner.mLoopTask, banner.mLoopTime);
            }
        }
    }

    /**
     * 为banner添加画廊效果
     *
     * @param itemWidth  item左右展示的宽度,单位dp
     * @param pageMargin 页面间距,单位dp
     * @param scale      缩放[0-1],1代表不缩放
     */
    public void setBannerGalleryEffect(int itemWidth, int pageMargin, float scale) {
        setBannerGalleryEffect(itemWidth, itemWidth, pageMargin, scale);
    }

    /**
     * 为banner添加画廊效果
     *
     * @param leftItemWidth  item左展示的宽度,单位dp
     * @param rightItemWidth item右展示的宽度,单位dp
     * @param pageMargin     页面间距,单位dp
     * @param scale          缩放[0-1],1代表不缩放
     */
    public void setBannerGalleryEffect(int leftItemWidth, int rightItemWidth, int pageMargin, float scale) {
        if (pageMargin > 0) {
            addPageTransformer(new MarginPageTransformer(BannerUtils.dp2px(pageMargin)));
        }
        if (scale < 1 && scale > 0) {
            addPageTransformer(new ScaleInTransformer(scale));
        }
        setRecyclerViewPadding(leftItemWidth > 0 ? BannerUtils.dp2px(leftItemWidth + pageMargin) : 0,
                rightItemWidth > 0 ? BannerUtils.dp2px(rightItemWidth + pageMargin) : 0);
    }

    /**
     * 为banner添加魅族效果
     *
     * @param itemWidth item左右展示的宽度,单位dp
     */
    public void setBannerGalleryMZ(int itemWidth) {
        setBannerGalleryMZ(itemWidth, .88f);
    }

    /**
     * 为banner添加魅族效果
     *
     * @param itemWidth item左右展示的宽度,单位dp
     * @param scale     缩放[0-1],1代表不缩放
     */
    public void setBannerGalleryMZ(int itemWidth, float scale) {
        if (scale < 1 && scale > 0) {
            addPageTransformer(new MZScaleInTransformer(scale));
        }
        setRecyclerViewPadding(BannerUtils.dp2px(itemWidth));
    }

    /**
     * 设置轮播指示器(显示在banner上)
     */
    public void setIndicator(Indicator indicator) {
        setIndicator(indicator, true);
    }

    /**
     * 设置轮播指示器(如果你的指示器写在布局文件中，attachToBanner传false)
     *
     * @param attachToBanner 是否将指示器添加到banner中，false 代表你可以将指示器通过布局放在任何位置
     *                       注意：设置为false后，内置的 setIndicatorGravity()和setIndicatorMargins() 方法将失效。
     *                       想改变可以自己调用系统提供的属性在布局文件中进行设置。具体可以参照demo
     */
    public void setIndicator(Indicator indicator, boolean attachToBanner) {
        removeIndicator();
        indicator.getIndicatorConfig().setAttachToBanner(attachToBanner);
        this.mIndicator = indicator;
        initIndicator();
    }

    public void setIndicatorSelectedColor(@ColorInt int color) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setSelectedColor(color);
        }
    }

    public void setIndicatorSelectedColorRes(@ColorRes int color) {
        setIndicatorSelectedColor(ContextCompat.getColor(getContext(), color));
    }

    public void setIndicatorNormalColor(@ColorInt int color) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setNormalColor(color);
        }
    }

    public void setIndicatorNormalColorRes(@ColorRes int color) {
        setIndicatorNormalColor(ContextCompat.getColor(getContext(), color));
    }

    public void setIndicatorGravity(@IndicatorConfig.Direction int gravity) {
        if (getIndicatorConfig() != null && getIndicatorConfig().isAttachToBanner()) {
            getIndicatorConfig().setGravity(gravity);
            getIndicator().getIndicatorView().postInvalidate();
        }
    }

    public void setIndicatorSpace(int indicatorSpace) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setIndicatorSpace(indicatorSpace);
        }
    }

    public void setIndicatorMargins(IndicatorConfig.Margins margins) {
        if (getIndicatorConfig() != null && getIndicatorConfig().isAttachToBanner()) {
            getIndicatorConfig().setMargins(margins);
            getIndicator().getIndicatorView().requestLayout();
        }
    }

    public void setIndicatorWidth(int normalWidth, int selectedWidth) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setNormalWidth(normalWidth);
            getIndicatorConfig().setSelectedWidth(selectedWidth);
        }
    }

    public void setIndicatorNormalWidth(int normalWidth) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setNormalWidth(normalWidth);
        }
    }

    public void setIndicatorSelectedWidth(int selectedWidth) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setSelectedWidth(selectedWidth);
        }
    }

    public void setIndicatorRadius(int indicatorRadius) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setRadius(indicatorRadius);
        }
    }
    public void setIndicatorHeight(int indicatorHeight) {
        if (getIndicatorConfig() != null) {
            getIndicatorConfig().setHeight(indicatorHeight);
        }
    }

    /**
     * 生命周期控制
     */
    public void addBannerLifecycleObserver(LifecycleOwner owner) {
        if (owner != null) {
            owner.getLifecycle().addObserver(new BannerLifecycleObserverAdapter(owner, this));
        }
    }

    @Override
    public void onStart() {
        start();
    }

    @Override
    public void onStop() {
        stop();
    }

    @Override
    public void onDestroy() {
        destroy();
    }

}
