package xyz.marcobasile.service.mapper;

import androidx.annotation.NonNull;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;
import java.util.stream.Collectors;

import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.model.SAMTwitterUser;


public class SAMTweetMapper {


    public List<SAMTweet> toSAMTweet(@NonNull List<Tweet> tweets) {

        return tweets.stream()
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
}