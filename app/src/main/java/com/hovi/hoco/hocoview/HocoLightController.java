package com.hovi.hoco.hocoview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hovi.hoco.R;

import java.util.ArrayList;

public class HocoLightController extends HocoController{

    private ArrayList<TextView> textviewLever;
    private ArrayList<ImageView> imageViewLever;
    private HocoVerticalSeekBar seekBar;
    private int lever = 0; // 0-> 4

    public HocoLightController(@NonNull Context context) {
        super(context);
        init();
    }

    public HocoLightController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HocoLightController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HocoLightController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        textviewLever = new ArrayList<>();
        imageViewLever = new ArrayList<>();
    }

    @Override
    public void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hoco_light_controller, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        textviewLever.add(findViewById(R.id.hoco_ligth_tat));
        textviewLever.add(findViewById(R.id.hoco_ligth_emdui));
        textviewLever.add(findViewById(R.id.hoco_ligth_toiuu));
        textviewLever.add(findViewById(R.id.hoco_ligth_rorang));
        textviewLever.add(findViewById(R.id.hoco_ligth_rucro));

        imageViewLever.add(findViewById(R.id.hoco_ligth_ova5));
        imageViewLever.add(findViewById(R.id.hoco_ligth_ova4));
        imageViewLever.add(findViewById(R.id.hoco_ligth_ova3));
        imageViewLever.add(findViewById(R.id.hoco_ligth_ova2));
        imageViewLever.add(findViewById(R.id.hoco_ligth_ova1));

        seekBar = findViewById(R.id.hoco_ligth_seekbar);
        setupIU();
    }

    private void setupIU(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateStateUI(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        updateStateUI(100);
        updateStateUI(0);
    }

    private void updateStateUI(int progress){
        int position = (progress+15) / 25;
        if(lever != position){
            textviewLever.get(lever).setTextSize(14f);
            textviewLever.get(lever).setTypeface(null, Typeface.NORMAL);
            lever = position;
            textviewLever.get(lever).setTextSize(18f);
            textviewLever.get(lever).setTypeface(null, Typeface.BOLD);

            for(int i = 0; i < imageViewLever.size(); i++){
                if(i <= lever){
                    imageViewLever.get(i).setVisibility(VISIBLE);
                }else{
                    imageViewLever.get(i).setVisibility(INVISIBLE);
                }
            }
        }
    }
}
