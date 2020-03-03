package xyz.marcobasile.service.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.service.ContentProvider;
import xyz.marcobasile.ui.shared.interfaces.GenericProcedure;

public class BitmapDownloader extends AsyncTask<Set<String>, Integer, List<Bitmap>> {

    private final static String TAG = BitmapDownloader.class.getName();

    private final ContentProvider provider;
    private GenericProcedure ontimeCallback;
    private OnDataReceived callback;

    public BitmapDownloader(ContentProvider provider) {
        super();
        this.provider = provider;
    }

    @Override
    protected void onPostExecute(List<Bitmap> bitmaps) {

        if (ontimeCallback != null) {
            ontimeCallback.doProcedure();
            ontimeCallback = null;
        }

        if (callback != null) {
            callback.accept(provider.tweets());
        }
    }

    @Override
    protected List<Bitmap> doInBackground(Set<String>... _strings) {

        if (_strings.length == 0) {
            return null;
        }

        Set<String> strings = _strings[0];

        return strings.stream()
                .map(this::getBitmapFromURLString)
                .collect(Collectors.toList());
    }

    private Bitmap getBitmapFromURLString(String urlString) {

        if (isCancelled()) {
            return null;
        }

        // Bitmap bitmap = null;
        Bitmap bitmap = provider.getImage(urlString);

        if (bitmap != null) {
            return bitmap;
        }

        // se non presente gia' la scarichiamo
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);

            provider.putImage(urlString, bitmap);

        } catch (IOException e) {

            Log.i(TAG, "Impossibile scaricare immagine all'url: " + urlString);
        }

        return bitmap;
    }

    public void setOnetimeCallback(GenericProcedure callback) {
        this.ontimeCallback = callback;
    }

    public void setCallback(OnDataReceived callback) {
        this.callback = callback;
    }

    public static interface OnDataReceived extends Consumer<List<SAMTweet>> {}
}
