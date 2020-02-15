package xyz.marcobasile.model;

import android.net.Uri;

public class PictureHolder {

    private Uri pictureUri;

    public PictureHolder() {}

    public void setPictureUri(Uri pictureUri) {
        this.pictureUri = pictureUri;
    }

    public Uri getPictureUri() {
        return pictureUri;
    }

    public void clear() {
        pictureUri = null;
    }
}
