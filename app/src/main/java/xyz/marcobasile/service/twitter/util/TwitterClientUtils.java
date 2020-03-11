package xyz.marcobasile.service.twitter.util;

import android.content.Context;
import android.net.Uri;
import android.os.FileUtils;

import com.twitter.sdk.android.core.models.Media;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.MediaService;
import com.twitter.sdk.android.core.services.StatusesService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class TwitterClientUtils {

    private static StatusesService status;
    private static MediaService media;

    public static void services(StatusesService status,
                                     MediaService media) {
        TwitterClientUtils.status = status;
        TwitterClientUtils.media = media;
    }

    public static Call<Tweet> tweet(String tweetText) {
        return makeTweetUpdate(tweetText, null);
    }

    public static Call<Tweet> tweet(String tweetText, Long mediaId) {
        return makeTweetUpdate(tweetText, mediaId.toString());
    }

    public static Call<Media> picUpload(Uri pictureUri, Context ctx) {

        byte[] imageFileBytes = getFileFromUri(pictureUri, ctx);
        return picUpload(imageFileBytes, ctx);
    }

    public static Call<Media> picUpload(byte[] pictureBytes, Context ctx) {

        RequestBody mediaBody = RequestBody.create(MediaType.parse("image/jpeg"), pictureBytes);
        return media.upload(mediaBody, null, null);
    }

    public static byte[] getFileFromUri(Uri pictureUri, Context ctx) {

        InputStream inputStream = inputStreamFromUri(ctx, pictureUri);
        return bytesFromStream(inputStream);
    }

    private static Call<Tweet> makeTweetUpdate(String tweetText, String mediaIdString) {
        return status.update(tweetText, null,false, null,
                null,null,false, false, mediaIdString);
    }


    private static InputStream inputStreamFromUri(Context ctx, Uri pictureUri) {

        InputStream inputStream = null;
        try {

            inputStream = ctx.getContentResolver().openInputStream(pictureUri);
        } catch (FileNotFoundException e) {}

        return inputStream;
    }

    private static byte[] bytesFromStream(InputStream is) {

        byte[] fileBytes = null;

        try {

            int available = is.available();
            fileBytes = new byte[available];
            is.read(fileBytes);

        } catch (IOException e) {}

        return fileBytes;
    }
}
