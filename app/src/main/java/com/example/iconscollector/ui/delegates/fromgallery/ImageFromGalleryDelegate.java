package com.example.iconscollector.ui.delegates.fromgallery;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;

public class ImageFromGalleryDelegate {

    private final Fragment fragment;
    private final Context context;

    public ImageFromGalleryDelegate(Fragment fragment, Context context) {
        this.fragment = fragment;
        this.context = context;
    }

    public boolean chooseImageFromGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        fragment.startActivityForResult(intent, requestCode);
        return true;
    }
}
