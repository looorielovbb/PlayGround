package com.youth.banner.adapter;


import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.R;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.holder.IViewHolder;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public abstract class BannerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements IViewHolder<T, VH> {
    protected List<T> mDatas = new ArrayList<>();
    private OnBannerListener<T> mOnBannerListener;
    private VH mViewHolder;
    private int mIncreaseCount = BannerConfig.INCREASE_COUNT;

    public BannerAdapter(List<T> datas) {
        setDatas(datas);
    }

    /**
     * 设置实体集合（可以在自己的adapter自定义，不一定非要使用）
     */
    public void setDatas(@NonNull List<T> datas) {
        Log.d("TAG", "datas: "+ Arrays.deepToString(datas.toArray()));
        int lengthOld = mDatas.size();
        if (datas.isEmpty()){
            mDatas.clear();
            notifyItemRangeRemoved(0,lengthOld);
        } else {
            mDatas.addAll(datas);
            notifyItemRangeInserted(0,datas.size());
        }
        Log.d("TAG", "setDatas: "+Arrays.deepToString(mDatas.toArray()));
    }

    /**
     * 获取指定的实体（可以在自己的adapter自定义，不一定非要使用）
     * @param position 真实的position
     */
    public T getData(int position) {
        return mDatas.get(position);
    }

    /**
     * 获取指定的实体（可以在自己的adapter自定义，不一定非要使用）
     *
     * @param position 这里传的position不是真实的，获取时转换了一次
     */
    public T getRealData(int position) {
        return mDatas.get(getRealPosition(position));
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        mViewHolder = holder;
        int real = getRealPosition(position);
        T data = mDatas.get(real);
        holder.itemView.setTag(R.id.banner_data_key, data);
        holder.itemView.setTag(R.id.banner_pos_key, real);
        onBindView(holder, mDatas.get(real), real, getRealCount());
        if (mOnBannerListener != null) {
            holder.itemView.setOnClickListener(view -> mOnBannerListener.OnBannerClick(data, real));
        }
    }

    @Override
    public int getItemCount() {
        return getRealCount() > 1 ? getRealCount() + mIncreaseCount : getRealCount();
    }

    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public int getRealPosition(int position) {
        return BannerUtils.getRealPosition(mIncreaseCount == BannerConfig.INCREASE_COUNT, position, getRealCount());
    }

    public void setOnBannerListener(OnBannerListener<T> listener) {
        this.mOnBannerListener = listener;
    }

    public VH getViewHolder() {
        return mViewHolder;
    }

    public void setIncreaseCount(int increaseCount) {
        this.mIncreaseCount = increaseCount;
    }

}