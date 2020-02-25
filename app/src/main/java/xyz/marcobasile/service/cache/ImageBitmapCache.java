package xyz.marcobasile.service.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;


public class ImageBitmapCache extends LruCache<String, Bitmap> {

    private final static int MAX_SIZE = 1024 * 1024 * 32; // 32 MB

    public ImageBitmapCache() {

        super(MAX_SIZE);
    }

/*    @Override
    protected Bitmap create(String key) {

        return null;
    }*/

    @Override
    protected int sizeOf(String key, Bitmap value) {

        return value.getByteCount();
    }
}
