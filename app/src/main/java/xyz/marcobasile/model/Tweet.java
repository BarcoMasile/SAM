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
