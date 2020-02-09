package xyz.marcobasile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Tweet {
    private String from;
    private String body;
    private Long likes;
    private Long retweets;
}
