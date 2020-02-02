package com.example.iconscollector.app;

import android.app.Application;
import androidx.annotation.MainThread;
import com.example.iconscollector.app.di.AppComponent;
import com.example.iconscollector.app.di.ComponentInjectable;

public class App extends Application {

    public static void inject(ComponentInjectable injectable) {
        App app = (App) injectable.requireContext()
                .getApplicationContext();
        injectable.inject(app.getAppComponent());
    }

    private AppComponent appComponent;

    @MainThread
    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = new AppComponent(this);
        }
        return appComponent;
    }
}
