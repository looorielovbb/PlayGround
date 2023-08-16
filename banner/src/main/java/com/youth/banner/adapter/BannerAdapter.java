package com.youth.banner.adapter;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.config.BannerConfig;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BannerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final List<T> mDatas = new ArrayList<>();
    private int mIncreaseCount = BannerConfig.INCREASE_COUNT;

    public BannerAdapter(@NonNull List<T> datas) {
        setDatas(datas);
    }

    public BannerAdapter() {
    }

    /**
     * 设置实体集合（可以在自己的adapter自定义，不一定非要使用）
     */
    public void setDatas(@NonNull List<T> datas) {
        int lengthOld = mDatas.size();
        if (datas.isEmpty()) {
            mDatas.clear();
            notifyItemRangeRemoved(0, lengthOld);
        } else {
            mDatas.addAll(datas);
            notifyItemRangeInserted(0, datas.size());
        }
    }

    /**
     * 获取指定的实体
     *
     * @param position 这里传的position非实际图片位置，在无线循环时需要转换
     */
    public T getBannerData(int position) {
        return mDatas.get(getBannerPosition(position));
    }

    @Override
    public int getItemCount() {
        return getBannerCount() > 1 ? getBannerCount() + mIncreaseCount : getBannerCount();
    }

    public int getBannerCount() {
        return mDatas.size();
    }

    /**
     * @noinspection unused
     */
    public List<T> getDatas() {
        return mDatas;
    }

    public int getBannerPosition(int position) {
        return BannerUtils.getRealPosition(mIncreaseCount == BannerConfig.INCREASE_COUNT, position, getBannerCount());
    }

    public void setIncreaseCount(int increaseCount) {
        this.mIncreaseCount = increaseCount;
    }

}