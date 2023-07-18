package com.youth.banner.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

/**
 * 默认实现的图片适配器，图片加载需要自己实现
 */
@SuppressWarnings("unused")
public abstract class BannerImageAdapter<T> extends BannerAdapter<T, BannerImageHolder> {

    public BannerImageAdapter(List<T> mData) {
        super(mData);
    }

    @NonNull
    @Override
    public BannerImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerImageHolder(imageView);
    }

    @Override
    public abstract void onBindViewHolder(@NonNull BannerImageHolder holder, int position, @NonNull List<Object> payloads);

}
