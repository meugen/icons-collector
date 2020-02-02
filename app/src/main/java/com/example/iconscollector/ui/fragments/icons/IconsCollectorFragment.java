package com.example.iconscollector.ui.fragments.icons;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import com.example.iconscollector.R;
import com.example.iconscollector.ui.delegates.captureimage.CaptureImageDelegate;
import com.example.iconscollector.ui.delegates.permissions.Permission;
import com.example.iconscollector.ui.delegates.permissions.PermissionsDelegate;
import com.example.iconscollector.ui.views.ImageWithLabelView;

public class IconsCollectorFragment extends Fragment {

    private static final String ARG_URI = "uri";

    private static final int FRONT_SIDE_REQUEST = 1;
    private static final int BACK_SIDE_REQUEST = 2;
    private static final int LEFT_SIDE_REQUEST = 3;
    private static final int RIGHT_SIDE_REQUEST = 4;

    private PermissionsDelegate permissionsDelegate;
    private CaptureImageDelegate captureImageDelegate;

    private Uri uri = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uri = savedInstanceState == null ? null : savedInstanceState.getParcelable(ARG_URI);

        permissionsDelegate = new PermissionsDelegate(this);
        captureImageDelegate = new CaptureImageDelegate(this, requireContext().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_icons_collector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.<ImageWithLabelView>requireViewById(view, R.id.image_back_side)
                .setSelectImageListener(v -> onSelectImage(BACK_SIDE_REQUEST));
        ViewCompat.<ImageWithLabelView>requireViewById(view, R.id.image_front_side)
                .setSelectImageListener(v -> onSelectImage(FRONT_SIDE_REQUEST));
        ViewCompat.<ImageWithLabelView>requireViewById(view, R.id.image_left_side)
                .setSelectImageListener(v -> onSelectImage(LEFT_SIDE_REQUEST));
        ViewCompat.<ImageWithLabelView>requireViewById(view, R.id.image_right_side)
                .setSelectImageListener(v -> onSelectImage(RIGHT_SIDE_REQUEST));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_URI, uri);
    }

    private void onSelectImage(int requestCode) {
        if (permissionsDelegate.checkPermission(Permission.WRITE_STORAGE)) {
            captureImage(requestCode);
        } else {
            requestWriteStoragePermission(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionsDelegate.checkPermission(Permission.WRITE_STORAGE)) {
            captureImage(requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        ImageWithLabelView view = findViewByRequestCode(requestCode);
        if (view == null) {
            return;
        }
        view.setImageURI(uri);
    }

    @Nullable
    private ImageWithLabelView findViewByRequestCode(int requestCode) {
        View view = getView();
        if (view == null) {
            return null;
        }
        if (requestCode == FRONT_SIDE_REQUEST) {
            return view.findViewById(R.id.image_front_side);
        } else if (requestCode == BACK_SIDE_REQUEST) {
            return view.findViewById(R.id.image_back_side);
        } else if (requestCode == LEFT_SIDE_REQUEST) {
            return view.findViewById(R.id.image_left_side);
        } else if (requestCode == RIGHT_SIDE_REQUEST) {
            return view.findViewById(R.id.image_right_side);
        }
        return null;
    }

    private void captureImage(int requestCode) {
        this.uri = captureImageDelegate.captureImage(requestCode);
        if (this.uri == null) {
            Toast.makeText(
                    requireContext(),
                    R.string.commen_image_capture_error_message,
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void requestWriteStoragePermission(int requestCode) {
        permissionsDelegate.requestPermission(Permission.WRITE_STORAGE, requestCode);
    }
}
