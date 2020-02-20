package xyz.marcobasile.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import xyz.marcobasile.model.SAMTweet;

public class TweetRepository extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final int PER_PAGE = 100;

    public TweetRepository(String tableName, @Nullable Context context) {
        super(context, tableName, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void save(List<SAMTweet> tweets) {

    }

    public List<SAMTweet> getPage(Long page) {

        return new ArrayList<>();
    }

    public void save(SAMTweet tweet) {

    }

    public SAMTweet getSAMTweetById(Long id) {

        return null;
    }

    public List<SAMTweet> searchByString(String search) {

        return null;
    }

    public void removeFromFavourites(Long id) {

    }
}
