package com.example.iconscollector.ui.fragments.common;

import android.util.SparseArray;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class Binding implements LifecycleObserver {

    private View rootView;
    private SparseArray<View> views;

    public final void attachView(Fragment fragment, View view) {
        this.rootView = view;
        this.views = new SparseArray<>();

        fragment.getViewLifecycleOwner().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public final void detachView(LifecycleOwner source) {
        rootView = null;
        if (views != null) {
            views.clear();
        }
        views = null;

        source.getLifecycle().removeObserver(this);
    }

    @Nullable
    public CharSequence getText(@StringRes int textId) {
        if (rootView == null) {
            return null;
        }
        return rootView.getContext().getText(textId);
    }

    @Nullable
    public <V extends View> V getNullable(@IdRes int id) {
        if (rootView == null || views == null) {
            return null;
        }
        View view = views.get(id);
        if (view == null) {
            view = rootView.findViewById(id);
        }
        return (V) view;
    }

    @NonNull
    public <V extends View> V get(@IdRes int id) {
        V view = getNullable(id);
        if (view == null) {
            throw new IllegalArgumentException("No view with id " + id);
        }
        return view;
    }

    public boolean has(@IdRes int id) {
        return getNullable(id) != null;
    }
}
