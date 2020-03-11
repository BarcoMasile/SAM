package xyz.marcobasile.doodling;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import xyz.marcobasile.R;

public class DoodlingActivity extends AppCompatActivity {

    public static final String DOODLING_INTENT = "xyz.marcobasile.DOODLING";
    public static final String DOODLE_EXTRA = "xyz.marcobasile.DOODLE";

    private DoodlingView doodlingView;
    private Button cancel, save;
    private Handler compressHandler = new Handler(Looper.getMainLooper());

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
    }

    public void setupListeners() {

        save.setOnClickListener(view -> {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            doodlingView.getBitmap().compress(Bitmap.CompressFormat.JPEG, 25, bos);
            setIntentResultAndFinish(bos.toByteArray());

            /*compressHandler.post(() -> {

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                doodlingView.getBitmap().compress(Bitmap.CompressFormat.JPEG, 25, bos);
                setIntentResultAndFinish(bos.toByteArray());
            });*/

        });

        cancel.setOnClickListener(view -> {

            setResult(Activity.RESULT_CANCELED);
            finish();
        });
    }

    private void setIntentResultAndFinish(byte[] b) {

        Intent intent = new Intent(DOODLING_INTENT);
        intent.putExtra(DOODLE_EXTRA, b);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
