package xyz.marcobasile.service.twitter.callback;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.twitter.sdk.android.core.models.Tweet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.marcobasile.R;
import xyz.marcobasile.model.PictureHolder;
import xyz.marcobasile.ui.shared.interfaces.ProceduralInterface;

public class PostTweetCallback implements Callback<Tweet> {

    private static final String TAG = PostTweetCallback.class.getName();

    private TextView textView;
    private Button clearAttach;
    private PictureHolder pictureHolder;
    private Button attachIcon;
    private ProceduralInterface toggleProgressBar;

    public PostTweetCallback(TextView textView, Button clearAttach, PictureHolder pictureHolder, Button attachIcon, ProceduralInterface toggleProgressBar) {
        this.textView = textView;
        this.clearAttach = clearAttach;
        this.pictureHolder = pictureHolder;
        this.attachIcon = attachIcon;
        this.toggleProgressBar = toggleProgressBar;
    }

    @Override
    public void onResponse(Call<Tweet> call, Response<Tweet> response) {

        Log.i(TAG,"Tweet pubblicato con successo");
        snackbar("Tweet Successfully Posted");

        textView.setText("");
        clearAttach.setEnabled(false);
        pictureHolder.clear();
        attachIcon.setVisibility(View.INVISIBLE);

        // deve spegnere
        toggleProgressBar.doProcedure();
    }

    @Override
    public void onFailure(Call<Tweet> call, Throwable t) {

        Log.i(TAG,"Tweet NON pubblicato");
        snackbar("Couldn't post tweet");

        // deve spegnere
        toggleProgressBar.doProcedure();
    }

    private void snackbar(String msg) {
        Snackbar snackbar = Snackbar.make(textView, msg, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundResource(R.color.textColor);
        snackbar.show();
    }
}
