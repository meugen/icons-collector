package com.example.iconscollector.app.di;

import android.content.Context;
import androidx.fragment.app.Fragment;
import com.example.iconscollector.ui.delegates.captureimage.CaptureImageDelegate;
import com.example.iconscollector.ui.delegates.fromgallery.ImageFromGalleryDelegate;
import com.example.iconscollector.ui.delegates.permissions.PermissionsDelegate;

public class AppComponent {

    private final Context context;

    public AppComponent(Context context) {
        this.context = context;
    }

    public CaptureImageDelegate getCaptureImageDelegate(Fragment fragment) {
        return new CaptureImageDelegate(fragment, context);
    }

    public ImageFromGalleryDelegate getImageFromGalleryDelegate(Fragment frament) {
        return new ImageFromGalleryDelegate(frament, context);
    }

    public PermissionsDelegate getPermissionsDelegate(Fragment fragment) {
        return new PermissionsDelegate(fragment);
    }
}
