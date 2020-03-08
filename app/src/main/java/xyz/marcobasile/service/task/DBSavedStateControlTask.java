package xyz.marcobasile.service.task;

import android.os.AsyncTask;

import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.repository.TweetRepository;
import xyz.marcobasile.ui.adapter.home.TimelineTweetViewHolder;

public class DBSavedStateControlTask extends AsyncTask<SAMTweet, Void, Boolean> {

    private TimelineTweetViewHolder tweetViewHolder;
    private TweetRepository repo;

    public DBSavedStateControlTask(TimelineTweetViewHolder tweetViewHolder, TweetRepository repo) {
        this.tweetViewHolder = tweetViewHolder;
        this.repo = repo;
    }

    @Override
    protected Boolean doInBackground(SAMTweet... tweets) {

        if (tweets.length != 1) {
            return false;
        }

        SAMTweet tweet = tweets[0];
        SAMTweet _tweet = repo.findById(tweet.getId());
        if (null == _tweet) {
            return false;
        }

        boolean saved = _tweet.getSaved() != null && _tweet.getSaved();
        tweet.setSaved(saved);
        return tweet.getSaved();

    }

    @Override
    protected void onPostExecute(Boolean saved) {
        // aggiorniamo la UI
        tweetViewHolder.saved(saved);
    }
}
