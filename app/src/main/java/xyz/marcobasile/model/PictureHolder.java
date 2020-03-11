package xyz.marcobasile.model;

import android.net.Uri;

public class PictureHolder {

    private Uri pictureUri;
    private byte[] pictureBytes;

    public boolean hasBitmap() {
        return pictureBytes != null;
    }

    public boolean hasUri() {
        return pictureUri != null;
    }

    public void setPictureUri(Uri pictureUri) {

        this.pictureBytes = null;
        this.pictureUri = pictureUri;
    }

    public void setPictureBytes(byte[] pictureBytes) {

        this.pictureUri = null;
        this.pictureBytes = pictureBytes;
    }

    public Uri getPictureUri() {
        return pictureUri;
    }

    public byte[] getPictureBytes() {
        return pictureBytes;
    }

    public void clear() {
        pictureUri = null;
        pictureBytes = null;
    }

    public boolean canProvide() {
        return getPictureUri() != null || getPictureBytes() != null;
    }
}
