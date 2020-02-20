package xyz.marcobasile.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SAMPlace {

    private String fullName;

    private String url;

    private String country;

    // private BoundingBox boundingBox;

    private Map<String, String> attributes;
}
