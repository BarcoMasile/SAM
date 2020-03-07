package xyz.marcobasile.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import xyz.marcobasile.model.SAMTweet;
import xyz.marcobasile.model.SAMTwitterUser;
import xyz.marcobasile.service.ContentProvider;

import static xyz.marcobasile.repository.DatabaseUtils.*;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.*;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.ALL_COLUMNS;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.DELETE;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.FAVOURITE_COUNT;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.FIND_ONE;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.ID;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.MEDIA_URL;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.RETWEET_COUNT;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.SAVED;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.SEARCH;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.TABLE;
import static xyz.marcobasile.repository.DatabaseUtils.Tweet.TEXT;

public class TweetRepository extends SQLiteOpenHelper {

    private final static String TAG = TweetRepository.class.getName();

    private static final int VERSION = 1;
    private static final int PER_PAGE = 50;

    public TweetRepository(@Nullable Context context) {
        super(context, TABLE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,"About to create Tweet Table");
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
    }


    public void save(SAMTweet tweet) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID, tweet.getId());
        values.put(TEXT, tweet.getText());
        values.put(RETWEET_COUNT, tweet.getRetweetCount());
        values.put(FAVOURITE_COUNT, tweet.getFavoriteCount());
        values.put(MEDIA_URL, tweet.getMediaURL());
        values.put(SAVED, tweet.getSaved() ? 1 : 0);
        values.put(SCREEN_NAME, tweet.getUser().getScreenName());
        values.put(PROFILE_IMAGE_URL, tweet.getUser().getProfileImageUrl());

        db.insert(TABLE, null, values);
        db.close();
    }

    public SAMTweet findById(Long id) {

        SAMTweet tweet = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE, ALL_COLUMNS, FIND_ONE, new String[]{id.toString()}, null, null, null);

        if (c.moveToFirst()) {
            tweet = tweetFromCursor(c);
        }

        c.close();
        db.close();

        return tweet;
    }

    public void delete(Long id) {

        SQLiteDatabase db = getWritableDatabase();
        int deletedRows = db.delete(TABLE, DELETE, new String[]{id.toString()});
        db.close();
        Log.i(TAG, "Deleted " + deletedRows + " rows");
    }

    public List<SAMTweet> findAll() {

        List<SAMTweet> tweets;

        Cursor c = findAllCursor();
        tweets = new ArrayList<>(c.getCount());

        boolean hasNext = c.moveToFirst();

        while(hasNext) {

            tweets.add(tweetFromCursor(c));
            hasNext = c.moveToNext();
        }

        ContentProvider.getInstance().enqueueImageDownloadsForTweets(tweets);
        Log.i(TAG, "Found " + tweets.size() + " tweets");
        return tweets;
    }

    public Cursor findAllCursor() {

        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE, ALL_COLUMNS, null, null, null, null, null);
    }

    public List<SAMTweet> searchByString(String search) {

        Cursor c = searchByStringCursor(search);

        return tweetCollectionFromCursor(c);
    }

    public Cursor searchByStringCursor(String search) {

        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE, ALL_COLUMNS, SEARCH, new String[]{search}, null, null, null);
    }

    private List<SAMTweet> tweetCollectionFromCursor(Cursor c) {
        List<SAMTweet> tweets = new ArrayList<>(c.getCount());

        boolean hasNext = c.moveToFirst();

        while(hasNext) {

            tweets.add(tweetFromCursor(c));
            hasNext = c.moveToNext();
        }

        return tweets;
    }

    public SAMTweet tweetFromCursor(Cursor c) {

        return SAMTweet.builder()
                .id(c.getLong(c.getColumnIndex(ID)))
                .text(c.getString(c.getColumnIndex(TEXT)))
                .retweetCount(c.getInt(c.getColumnIndex(RETWEET_COUNT)))
                .favoriteCount(c.getInt(c.getColumnIndex(FAVOURITE_COUNT)))
                .mediaURL(c.getString(c.getColumnIndex(MEDIA_URL)))
                .saved(c.getInt(c.getColumnIndex(SAVED)) == 1)
                .user(
                        SAMTwitterUser.builder()
                                .screenName(c.getString(c.getColumnIndex(SCREEN_NAME)))
                                .profileImageUrl(c.getString(c.getColumnIndex(PROFILE_IMAGE_URL)))
                                .build()
                )
                .build();
    }
}
