package xyz.marcobasile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SAMTwitterUser {

    private long id;

    private String screenName;

    private String profileImageUrl;

}
