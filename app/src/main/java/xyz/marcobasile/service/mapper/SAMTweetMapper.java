package xyz.marcobasile.service.mapper;

import androidx.annotation.NonNull;

import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.model.SAMTwitterUser;


public class SAMTweetMapper {

    private static final String PICTURE_MEDIA_TYPE = "photo";
    private static final String VIDEO_MEDIA_TYPE = "video";
    private static final String GIF_MEDIA_TYPE = "animated_gif";

    private static final String THUMB_MEDIA_SIZE = "thumb";
    private static final String SMALL_MEDIA_SIZE = "small";
//    private static final String MEDIUM_MEDIA_SIZE = "medium";


    public List<SAMTweet> toSAMTweet(@NonNull List<Tweet> tweets) {

        return tweets.stream()
                .filter(tweet -> !tweet.possiblySensitive)
                .map(this::toSAMTweet)
                .collect(Collectors.toList());
    }


    private SAMTweet toSAMTweet(@NonNull Tweet tweet) {

        return SAMTweet.builder()
                .id(tweet.id)
                .text(tweet.text)
                .user(toSAMTwitterUser(tweet.user))
                .favoriteCount(tweet.favoriteCount)
                .retweetCount(tweet.retweetCount)
                .mediaURL(getOneMediaURL(tweet))
                .build();
    }

    private SAMTwitterUser toSAMTwitterUser(@NonNull User user) {

        return SAMTwitterUser.builder()
                .id(user.id)
                .screenName(user.screenName)
                .description(user.description)
                .followersCount(user.followersCount)
                .friendsCount(user.friendsCount)
                .statusesCount(user.statusesCount)
                .profileImageUrl(user.profileImageUrl)
                .location(user.location)
                .build();
    }

    private String getOneMediaURL(Tweet tweet) {

        return tweet.extendedEntities.media
                .stream()
                .filter(mediaEntity -> PICTURE_MEDIA_TYPE.equals(mediaEntity.type))
                .map(this::pickMediaURLForTweetPicture)
                .findAny().orElse(null);
    }

    private String pickMediaURLForTweetPicture(MediaEntity mediaEntity) {

        MediaEntity.Sizes sizes = mediaEntity.sizes;

        StringBuilder sb = new StringBuilder(mediaEntity.mediaUrl);
        if (sizes.small != null) {
            sb.append(":");
            sb.append(SMALL_MEDIA_SIZE);
        }

        return sb.toString();
    }
}