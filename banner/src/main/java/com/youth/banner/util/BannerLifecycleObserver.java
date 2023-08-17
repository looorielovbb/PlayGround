package com.youth.banner.util;

import androidx.lifecycle.LifecycleObserver;

public interface BannerLifecycleObserver extends LifecycleObserver {

    void onStop();

    void onStart();

    void onDestroy();
}
