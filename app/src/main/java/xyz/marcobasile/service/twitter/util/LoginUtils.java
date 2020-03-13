package xyz.marcobasile.service.twitter.util;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import xyz.marcobasile.R;
import xyz.marcobasile.service.twitter.TwitterClient;


public class LoginUtils {

    public static TwitterLoginCallback makeCallback(Context ctx, TwitterLoginButton loginBtn,
                                                    BottomNavigationView navView) {
        return new TwitterLoginCallback(ctx, loginBtn, navView);
    }

    public static boolean isUserAuthenticated() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        return null != session;
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void loginSuccessfulToast(Context ctx) {
        Toast toast = Toast.makeText(ctx, R.string.toast_login_successful, Toast.LENGTH_LONG);
        toast.getView().setBackgroundColor(ctx.getColor(R.color.login_success_color));
        toast.show();
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void loginFailureToast(Context ctx) {
        Toast toast = Toast.makeText(ctx, R.string.toast_login_failure, Toast.LENGTH_LONG);
        toast.getView().setBackgroundColor(ctx.getColor(R.color.login_failure_color));
        toast.show();
    }

    private static class TwitterLoginCallback extends Callback<TwitterSession> {
        private final String TAG = this.getClass().getName();

        private Context ctx;
        private final TwitterLoginButton loginBtn;
        private final BottomNavigationView navView;

        TwitterLoginCallback(Context ctx, TwitterLoginButton loginBtn,
                             BottomNavigationView navView) {
            this.ctx = ctx;
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

            loginSuccessfulToast(ctx);

            //Generic procedure da usare qui per popolare il fragment e avviare il timer
        }

        @Override
        public void failure(TwitterException exception) {
            Log.w(TAG, "Twitter Login failure");
            Log.d(TAG, "Message: " + exception.getLocalizedMessage());
            TwitterCore.getInstance().getSessionManager().clearActiveSession(); // non dovrebbe far danni
            loginFailureToast(ctx);
        }
    };
}
