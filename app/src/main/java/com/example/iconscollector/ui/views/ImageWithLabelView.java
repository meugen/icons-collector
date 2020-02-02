package com.example.iconscollector.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.iconscollector.R;

public class ImageWithLabelView extends FrameLayout {

    private ImageView imageView;
    private TextView labelView;

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

        imageView = findViewById(R.id.image);
        labelView = findViewById(R.id.label);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, new int[] { android.R.attr.text });
            labelView.setText(array.getText(0));
            array.recycle();
        }
    }
}
