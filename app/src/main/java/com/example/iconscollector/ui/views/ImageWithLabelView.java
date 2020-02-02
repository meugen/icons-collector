package com.example.iconscollector.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.example.iconscollector.R;

public class ImageWithLabelView extends FrameLayout {

    private ImageView imageView;
    private TextView labelView;

    @Nullable
    private Uri uri;

    @Nullable
    private OnSelectImageListener listener;

    public ImageWithLabelView(Context context) {
        super(context);
        init(context, null);
    }

    public ImageWithLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageWithLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        View.inflate(context, R.layout.view_image_with_label, this);

        imageView = ViewCompat.requireViewById(this, R.id.image);
        imageView.setOnClickListener(v -> toggleImage());
        labelView = ViewCompat.requireViewById(this, R.id.label);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, new int[] { android.R.attr.text });
            labelView.setText(array.getText(0));
            array.recycle();
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable base = super.onSaveInstanceState();
        return new SavedState(base, uri);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            super.onRestoreInstanceState(savedState.getBase());
            setImageURI(savedState.getUri());
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    public void setImageURI(@Nullable Uri uri) {
        this.uri = uri;
        if (uri == null) {
            imageView.setImageResource(R.drawable.baseline_image_black_48);
            labelView.setVisibility(View.VISIBLE);
        } else {
            imageView.setImageURI(uri);
            labelView.setVisibility(View.GONE);
        }
    }

    public void setSelectImageListener(final OnSelectImageListener listener) {
        this.listener = listener;
    }

    private void toggleImage() {
        if (uri != null) {
            setImageURI(null);
            return;
        }

        if (listener != null) {
            listener.onSelectImage(this);
        }
    }

    public interface OnSelectImageListener {

        void onSelectImage(ImageWithLabelView view);
    }

    public static class SavedState implements Parcelable {

        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                Parcelable base = source.readParcelable(loader);
                Uri uri = source.readParcelable(loader);
                return new SavedState(base, uri);
            }

            @Override
            public SavedState createFromParcel(Parcel source) {
                return createFromParcel(source, SavedState.class.getClassLoader());
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        private final Parcelable base;
        private final Uri uri;

        public SavedState(Parcelable base, Uri uri) {
            this.base = base;
            this.uri = uri;
        }

        public Parcelable getBase() {
            return base;
        }

        public Uri getUri() {
            return uri;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(base, flags);
            dest.writeParcelable(uri, flags);
        }
    }
}
