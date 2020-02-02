package com.example.iconscollector.ui.dialogs.phototype;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import com.example.iconscollector.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SelectPhotoTypeDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_picture_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.requireViewById(view, R.id.capture_image)
                .setOnClickListener(v -> onCaptureImageSelected());
        ViewCompat.requireViewById(view, R.id.from_gallery)
                .setOnClickListener(v -> onFromGallerySelected());
    }

    private void onCaptureImageSelected() {
        Fragment fragment = getTargetFragment();
        if (fragment instanceof OnPictureTypeSelectedListener) {
            OnPictureTypeSelectedListener listener = (OnPictureTypeSelectedListener) fragment;
            listener.onCaptureImage(getTargetRequestCode());
        }
        dismiss();
    }

    private void onFromGallerySelected() {
        Fragment fragment = getTargetFragment();
        if (fragment instanceof OnPictureTypeSelectedListener) {
            OnPictureTypeSelectedListener listener = (OnPictureTypeSelectedListener) fragment;
            listener.onFromGallery(getTargetRequestCode());
        }
        dismiss();
    }

    public interface OnPictureTypeSelectedListener {

        void onCaptureImage(int requestCode);

        void onFromGallery(int requestCode);
    }
}
