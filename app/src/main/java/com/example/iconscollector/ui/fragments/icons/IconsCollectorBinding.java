package com.example.iconscollector.ui.fragments.icons;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.EditText;
import androidx.annotation.Nullable;
import com.example.iconscollector.R;
import com.example.iconscollector.ui.fragments.common.Binding;
import com.example.iconscollector.ui.views.ImageWithLabelView;
import com.google.android.material.textfield.TextInputLayout;

public class IconsCollectorBinding extends Binding {

    private static final int FRONT_SIDE_REQUEST = 1;
    private static final int BACK_SIDE_REQUEST = 2;
    private static final int LEFT_SIDE_REQUEST = 3;
    private static final int RIGHT_SIDE_REQUEST = 4;

    public boolean checkForErrors() {
        return validateNotEmpty(get(R.id.comment_layout));
    }

    private boolean validateNotEmpty(TextInputLayout layout) {
        EditText editText = layout.getEditText();
        if (editText == null) {
            return false;
        }
        if (TextUtils.isEmpty(editText.getText())) {
            layout.setError(getText(R.string.icons_collector_scr_required_field_error));
            return false;
        }
        return true;
    }

    public void setOnSelectImageListener(OnSelectImageWithRequestListener listener) {
        OnSelectImageAdapter adapter = new OnSelectImageAdapter(listener);
        this.<ImageWithLabelView>get(R.id.image_front_side).setSelectImageListener(adapter);
        this.<ImageWithLabelView>get(R.id.image_back_side).setSelectImageListener(adapter);
        this.<ImageWithLabelView>get(R.id.image_left_side).setSelectImageListener(adapter);
        this.<ImageWithLabelView>get(R.id.image_right_side).setSelectImageListener(adapter);
    }

    public void displayImageByRequest(int requestCode, Uri uri) {
        ImageWithLabelView view = findViewByRequestCode(requestCode);
        if (view != null) {
            view.setImageURI(uri);
        }
    }

    @Nullable
    private ImageWithLabelView findViewByRequestCode(int requestCode) {
        if (requestCode == FRONT_SIDE_REQUEST) {
            return getNullable(R.id.image_front_side);
        } else if (requestCode == BACK_SIDE_REQUEST) {
            return getNullable(R.id.image_back_side);
        } else if (requestCode == LEFT_SIDE_REQUEST) {
            return getNullable(R.id.image_left_side);
        } else if (requestCode == RIGHT_SIDE_REQUEST) {
            return getNullable(R.id.image_right_side);
        }
        return null;
    }

    public interface OnSelectImageWithRequestListener {

        void onSelectImage(int requestCode);
    }

    private static class OnSelectImageAdapter implements ImageWithLabelView.OnSelectImageListener {

        private final OnSelectImageWithRequestListener listener;

        public OnSelectImageAdapter(OnSelectImageWithRequestListener listener) {
            this.listener = listener;
        }

        @Override
        public void onSelectImage(ImageWithLabelView view) {
            int viewId = view.getId();
            if (viewId == R.id.image_front_side) {
                listener.onSelectImage(FRONT_SIDE_REQUEST);
            } else if (viewId == R.id.image_back_side) {
                listener.onSelectImage(BACK_SIDE_REQUEST);
            } else if (viewId == R.id.image_left_side) {
                listener.onSelectImage(LEFT_SIDE_REQUEST);
            } else if (viewId == R.id.image_right_side) {
                listener.onSelectImage(RIGHT_SIDE_REQUEST);
            }
        }
    }
}
