package com.youth.banner.adapter;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.config.BannerConfig;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BannerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final List<T> mData = new ArrayList<>();
    private int mIncreaseCount = BannerConfig.INCREASE_COUNT;

    public BannerAdapter(@NonNull List<T> data) {
        setData(data);
    }

    public BannerAdapter() {
    }

    /**
     * 设置实体集合（可以在自己的adapter自定义，不一定非要使用）
     */
    public void setData(@NonNull List<T> data) {
        int lengthOld = mData.size();
        if (data.isEmpty()) {
            mData.clear();
            notifyItemRangeRemoved(0, lengthOld);
        } else {
            mData.addAll(data);
            notifyItemRangeInserted(0, data.size());
        }
    }

    /**
     * 获取指定的实体
     * @param position 这里传的position非实际图片位置，在无线循环时需要转换
     */
    public T getBannerData(int position) {
        return mData.get(getBannerPosition(position));
    }

    @Override
    public int getItemCount() {
        return getBannerCount() > 1 ? getBannerCount() + mIncreaseCount : getBannerCount();
    }

    public int getBannerCount() {
        return mData.size();
    }

    /**
     * @noinspection unused
     */
    public List<T> getData() {
        return mData;
    }

    public int getBannerPosition(int position) {
        return BannerUtils.getRealPosition(mIncreaseCount == BannerConfig.INCREASE_COUNT, position, getBannerCount());
    }

    public void setIncreaseCount(int increaseCount) {
        this.mIncreaseCount = increaseCount;
    }

}