package xyz.marcobasile.model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SAMTwitterUser {

    private long id;

    private String screenName;

    private String description;

    private int followersCount;

    private int friendsCount;

    private int statusesCount;

    private String profileImageUrl;

    // private Bitmap profileImage;

    private String location;

    private LatLng latLng;
}
