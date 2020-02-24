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

    private SAMTwitterUser user;

    private Integer favoriteCount;

    private int retweetCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SAMTwitterUser getUser() {
        return user;
    }

    public void setUser(SAMTwitterUser user) {
        this.user = user;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }
}
