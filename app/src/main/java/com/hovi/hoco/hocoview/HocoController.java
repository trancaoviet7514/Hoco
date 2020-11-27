package com.hovi.hoco.hocoview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hovi.hoco.R;

public class HocoController extends ConstraintLayout {

    //
    public HocoController(@NonNull Context context) {
        super(context);
    }

    public HocoController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        initializeViews(context);
    }

    public HocoController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        initializeViews(context);
    }

    public HocoController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
        initializeViews(context);
    }

    private void init(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HocoButton);
        typedArray.recycle();
    }

    public void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hoco_controller, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateUI();
    }

    private void updateUI() {

    }
}
