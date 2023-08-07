package com.youth.banner.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

/**
 * 默认实现的图片适配器，图片加载需要自己实现
 */
public abstract class BannerImageAdapter<T> extends BannerAdapter<T, BannerImageHolder> {

    /**
     * @noinspection unused
     */
    public BannerImageAdapter(@NonNull List<T> mDatas) {
        super(mDatas);
    }

    public BannerImageAdapter() {
        super();
    }

    @Override
    public BannerImageHolder onCreateHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return new BannerImageHolder(imageView);
    }

}
