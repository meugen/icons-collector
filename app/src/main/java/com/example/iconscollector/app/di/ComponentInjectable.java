package com.example.iconscollector.app.di;

import android.content.Context;
import androidx.annotation.NonNull;

public interface ComponentInjectable extends Injectable {

    @NonNull
    Context requireContext();
}
