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

public class HocoButton extends ConstraintLayout {

    private Drawable iconID;
    private ImageView iconView;
    private ImageView bgView;

    //
    public HocoButton(@NonNull Context context) {
        super(context);
    }

    public HocoButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        initializeViews(context);
    }

    public HocoButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        initializeViews(context);
    }

    public HocoButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
        initializeViews(context);
    }

    private void init(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HocoButton);
        iconID = typedArray.getDrawable(R.styleable.HocoButton_hoco_src);
        typedArray.recycle();
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hoco_button, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        iconView = findViewById(R.id.hoco_button_src);
        bgView = findViewById(R.id.hoco_button_bg);
        updateUI();
    }

    private void updateUI() {
        iconView.setImageDrawable(iconID);
        enableView(false);
    }

    public void enableView(boolean isEnable){
        if(isEnable) {
            bgView.setImageResource(R.drawable.bg_button);
            iconView.setColorFilter(getContext().getResources().getColor(R.color.white));
        }
        else{
            bgView.setImageResource(R.drawable.bg_button_disable);
            iconView.setColorFilter(getContext().getResources().getColor(R.color.gray));
        }
    }
}
