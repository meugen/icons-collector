package com.example.iconscollector.ui.fragments.icons;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.iconscollector.R;
import com.example.iconscollector.app.App;
import com.example.iconscollector.app.di.AppComponent;
import com.example.iconscollector.app.di.ComponentInjectable;
import com.example.iconscollector.ui.delegates.captureimage.CaptureImageDelegate;
import com.example.iconscollector.ui.delegates.fromgallery.ImageFromGalleryDelegate;
import com.example.iconscollector.ui.delegates.permissions.Permission;
import com.example.iconscollector.ui.delegates.permissions.PermissionsDelegate;
import com.example.iconscollector.ui.dialogs.phototype.SelectPhotoTypeDialog;

public class IconsCollectorFragment extends Fragment implements
        SelectPhotoTypeDialog.OnPictureTypeSelectedListener, ComponentInjectable,
        IconsCollectorBinding.OnSelectImageWithRequestListener {

    private static final String ARG_URI = "uri";

    private IconsCollectorBinding binding;

    private PermissionsDelegate permissionsDelegate;
    private CaptureImageDelegate captureImageDelegate;
    private ImageFromGalleryDelegate imageFromGalleryDelegate;

    private Uri uri = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.inject(this);
        uri = savedInstanceState == null ? null : savedInstanceState.getParcelable(ARG_URI);
    }

    @Override
    public void inject(AppComponent component) {
        binding = new IconsCollectorBinding();

        permissionsDelegate = component.getPermissionsDelegate(this);
        captureImageDelegate = component.getCaptureImageDelegate(this);
        imageFromGalleryDelegate = component.getImageFromGalleryDelegate(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_icons_collector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.attachView(this, view);

        binding.setOnSelectImageListener(this);
        binding.get(R.id.next_action)
                .setOnClickListener(v -> onNextAction());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_URI, uri);
    }

    @Override
    public void onCaptureImage(int requestCode) {
        if (permissionsDelegate.checkPermission(Permission.WRITE_STORAGE)) {
            captureImage(requestCode);
        } else {
            requestWriteStoragePermission(requestCode);
        }
    }

    @Override
    public void onFromGallery(int requestCode) {
        imageFromGalleryDelegate.chooseImageFromGallery(requestCode);
    }

    private void onNextAction() {
        if (!binding.checkForErrors()) {
            return;
        }
        Toast.makeText(
                requireContext(),
                "send images and comment somewhere",
                Toast.LENGTH_LONG
        ).show();
        // TODO send images and comment somewhere
    }

    @Override
    public void onSelectImage(int requestCode) {
        SelectPhotoTypeDialog dialog = new SelectPhotoTypeDialog();
        dialog.setTargetFragment(this, requestCode);
        dialog.show(requireFragmentManager(), "select-photo-type");
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
        Uri _uri = data == null ? null : data.getData();
        if (_uri == null) {
            _uri = this.uri;
        }
        binding.displayImageByRequest(requestCode, _uri);
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
