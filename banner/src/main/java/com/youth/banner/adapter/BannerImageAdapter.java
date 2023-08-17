package com.youth.banner.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 默认实现的图片适配器，图片加载需要自己实现
 * @noinspection ALL
 */
public abstract class BannerImageAdapter<T> extends BannerAdapter<T, BannerImageAdapter.BannerImageHolder> {

    public BannerImageAdapter(@NonNull List<T> mDatas) {
        super(mDatas);
    }

    public BannerImageAdapter() {
        super();
    }

    @NonNull
    @Override
    public BannerImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return new BannerImageHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerImageHolder holder, int position) {
        onBindView(holder, getBannerData(position), getBannerPosition(position), getBannerCount());
    }

    public abstract void onBindView(@NonNull BannerImageHolder holder, @NonNull T data, int position, int size);

    public static class BannerImageHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;

        public BannerImageHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view;
        }
    }
}
