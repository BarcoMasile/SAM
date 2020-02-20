package xyz.marcobasile.service.mapper;

import androidx.annotation.NonNull;

import com.twitter.sdk.android.core.models.Place;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;
import java.util.stream.Collectors;

import xyz.marcobasile.model.SAMPlace;
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
                .createdAt(tweet.createdAt)
                .favoriteCount(tweet.favoriteCount)
                .retweetCount(tweet.retweetCount)
                .place(toSAMPlace(tweet.place))
                .build();
    }

    private SAMTwitterUser toSAMTwitterUser(@NonNull User user) {

        return SAMTwitterUser.builder()
                .id(user.id)
                .screenName(user.screenName)
                .profileImageUrl(user.profileImageUrl)
                .build();
    }

    private SAMPlace toSAMPlace(@NonNull Place place) {

        return SAMPlace.builder()
                .url(place.url)
                .country(place.country)
                .fullName(place.fullName)
                .attributes(place.attributes)
                .build();
    }

}