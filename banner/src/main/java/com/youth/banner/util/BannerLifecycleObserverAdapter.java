package com.youth.banner.util;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

@SuppressWarnings("unused")
public class BannerLifecycleObserverAdapter implements LifecycleObserver {
    private final BannerLifecycleObserver mObserver;

    public BannerLifecycleObserverAdapter(LifecycleOwner lifecycleOwner, BannerLifecycleObserver observer) {
        mObserver = observer;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        mObserver.onStart();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        mObserver.onStop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        mObserver.onDestroy();
    }

}