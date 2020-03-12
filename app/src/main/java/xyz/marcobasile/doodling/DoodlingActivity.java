package xyz.marcobasile.doodling;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import xyz.marcobasile.R;

public class DoodlingActivity extends AppCompatActivity {

    public static final String DOODLING_INTENT = "xyz.marcobasile.DOODLING";
    public static final String DOODLE_EXTRA = "xyz.marcobasile.DOODLE";

    private DoodlingView doodlingView;
    private Button cancel, save;
    private FloatingActionButton color_1, color_2, color_3;
    private SeekBar strokeBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doodling_layout);

        setupViews();
        setupListeners();
    }

    public void setupViews() {

        this.doodlingView = findViewById(R.id.doodle_view);
        this.cancel = findViewById(R.id.doodle_cancel_btn);
        this.save = findViewById(R.id.doodle_save_btn);
        this.color_1 = findViewById(R.id.color_1);
        this.color_2 = findViewById(R.id.color_2);
        this.color_3 = findViewById(R.id.color_3);
        
        this.strokeBar = findViewById(R.id.stroke_width_bar);
    }

    public void setupListeners() {

        save.setOnClickListener(view -> {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            doodlingView.getBitmap().compress(Bitmap.CompressFormat.JPEG, 25, bos);
            setIntentResultAndFinish(bos.toByteArray());
        });

        cancel.setOnClickListener(view -> {

            setResult(Activity.RESULT_CANCELED);
            finish();
        });

        color_1.setOnClickListener(colorClickListener(color_1));
        color_2.setOnClickListener(colorClickListener(color_2));
        color_3.setOnClickListener(colorClickListener(color_3));

        strokeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // seekBar.getThumb().set
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int progress = seekBar.getProgress();
                doodlingView.setStrokeWidth(progress);
            }
        });
    }

    private void setIntentResultAndFinish(byte[] b) {

        Intent intent = new Intent(DOODLING_INTENT);
        intent.putExtra(DOODLE_EXTRA, b);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private View.OnClickListener colorClickListener(FloatingActionButton fab) {

        return view -> {
            int color = fab.getBackgroundTintList().getDefaultColor();
            doodlingView.setStrokeColor(color);
        };
    }
}
