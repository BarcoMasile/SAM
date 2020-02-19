package xyz.marcobasile.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Tweet {
    private String by;
    private String body;
    private Instant creationDate;
    private String url;
    private String profilePicUrl;
    private Long likes;
    private Long retweets;
}
/*
Tweet
    @SerializedName("created_at")
    public final String createdAt;

    @SerializedName("favorite_count")
    public final Integer favoriteCount;

    @SerializedName("id_str")
    public final String idStr;

    @SerializedName("place")
    public final Place place;

    @SerializedName("possibly_sensitive")
    public final boolean possiblySensitive;

    @SerializedName("scopes")
    public final Object scopes;

    @SerializedName("retweet_count")
    public final int retweetCount;

    @SerializedName(value = "text", alternate = {"full_text"})
    public final String text;

    @SerializedName("user")
    public final User user;

Place
    @SerializedName("full_name")
    public final String fullName;

    @SerializedName("url")
    public final String url;

    @SerializedName("country")
    public final String country;

    @SerializedName("bounding_box")
    public final BoundingBox boundingBox;

    @SerializedName("attributes")
    public final Map<String, String> attributes;

 */