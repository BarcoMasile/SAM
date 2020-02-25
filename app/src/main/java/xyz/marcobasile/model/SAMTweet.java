package xyz.marcobasile.model;

import com.twitter.sdk.android.core.models.Place;
import com.twitter.sdk.android.core.models.User;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SAMTweet {

    private Long id;

    private String text;

    private Integer favoriteCount;

    private int retweetCount;

    private boolean saved;

    private String mediaURL;

    private SAMTwitterUser user;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public boolean isSaved() {
        return saved;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public SAMTwitterUser getUser() {
        return user;
    }
}
