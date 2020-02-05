package xyz.marcobasile.twitter.util;

import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


public class LoginUtils {

    public static TwitterLoginCallback makeCallback(TwitterLoginButton loginBtn,
                                                   BottomNavigationView navView) {
        return new TwitterLoginCallback(loginBtn, navView);
    }

    public static boolean isUserAuthenticated() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        return null != session;
    }

    private static class TwitterLoginCallback extends Callback<TwitterSession> {
        private final String TAG = this.getClass().getName();

        private final TwitterLoginButton loginBtn;
        private final BottomNavigationView navView;

        public TwitterLoginCallback(TwitterLoginButton loginBtn,
                                    BottomNavigationView navView) {
            this.loginBtn = loginBtn;
            this.navView = navView;
        }

        @Override
        public void success(Result<TwitterSession> result) {
            Log.i(TAG, "Twitter Login successful");

            String currentUserUsername = result.data.getUserName();
            TwitterAuthToken currentUserToken = result.data.getAuthToken();
            Log.d(TAG, "Twitter auth result, username: " + currentUserUsername + ", userId: " + currentUserUsername + ", authToken: " + currentUserToken);

            loginBtn.setVisibility(View.INVISIBLE);
            navView.setVisibility(View.VISIBLE);
        }

        @Override
        public void failure(TwitterException exception) {
            Log.w(TAG, "Twitter Login failure");
            Log.d(TAG, "Message: " + exception.getLocalizedMessage());
        }
    };
}
