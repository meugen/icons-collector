package com.example.iconscollector.ui.delegates.captureimage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CaptureImageDelegate {

    private static final String AUTHORITY = "com.example.iconscollector.fileprovider";

    private final Fragment fragment;
    private final Context context;

    public CaptureImageDelegate(Fragment fragment, Context context) {
        this.fragment = fragment;
        this.context = context;
    }

    @Nullable
    public Uri captureImage(int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return null;
        }

        File photoFile;
        try {
            photoFile = createTempFile();
        } catch (IOException ex) {
            return null;
        }

        Uri photoURI = FileProvider.getUriForFile(context, AUTHORITY, photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        fragment.startActivityForResult(intent, requestCode);
        return photoURI;
    }

    private File createTempFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH)
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
}
