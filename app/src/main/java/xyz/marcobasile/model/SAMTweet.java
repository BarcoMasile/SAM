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

    private Boolean saved;

    private String mediaURL;

    private SAMTwitterUser user;
}
