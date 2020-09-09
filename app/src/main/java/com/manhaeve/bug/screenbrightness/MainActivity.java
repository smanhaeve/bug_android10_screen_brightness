package com.manhaeve.bug.screenbrightness;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = findViewById(R.id.text);
        final SeekBar seek = findViewById(R.id.seekbar);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Window w = getWindow();
                if (w == null) return;
                WindowManager.LayoutParams params = w.getAttributes();
                progress -= 1;
                final int max = seekBar.getMax() - 1;
                if (progress == max) {
                    params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
                    text.setText(getString(R.string.brightness_s, params.screenBrightness, "BRIGHTNESS_OVERRIDE_FULL"));
                } else if (progress == -1) {
                    params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
                    text.setText(getString(R.string.brightness_s, params.screenBrightness, "BRIGHTNESS_OVERRIDE_NONE"));
                } else if (progress == 0) {
                    params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
                    text.setText(getString(R.string.brightness_s, params.screenBrightness, "BRIGHTNESS_OVERRIDE_OFF"));
                } else if (progress < max / 2){
                    params.screenBrightness = (float)progress * 0.01f / max;
                    text.setText(getString(R.string.brightness_f, params.screenBrightness));
                } else {
                    params.screenBrightness = 0.005f + (float)(progress - max / 2) * 0.85f / max;
                    text.setText(getString(R.string.brightness_f, params.screenBrightness));
                }
                w.setAttributes(params);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        seek.setProgress(seek.getMax());
    }
}
