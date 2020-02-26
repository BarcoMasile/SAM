package xyz.marcobasile.service.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedDeque;

import xyz.marcobasile.service.ContentProvider;

public class BitmapDownloader extends AsyncTask<String, Integer, Bitmap> {

    private final static String TAG = BitmapDownloader.class.getName();

    private final ContentProvider provider = ContentProvider.getInstance();
    private final String imageURL;
    private ConcurrentLinkedDeque<String> stack;

    public BitmapDownloader(String imageURL) {
        super();
        this.imageURL = imageURL;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        String currentURL = stack.pop();
        provider.putImage(imageURL, bitmap); // provider.putImage(currentURL, bitmap);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        Bitmap bitmap = null;

        if (strings.length == 0) {
            return null;
        }

        stack.push(strings[0]);

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);

        } catch (IOException e) {

            Log.i(TAG, "Impossibile scaricare immagine all'url: " + imageURL);
        }

        return bitmap;
    }
}
