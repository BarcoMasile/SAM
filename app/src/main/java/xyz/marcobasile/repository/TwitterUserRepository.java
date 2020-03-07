package xyz.marcobasile.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Set;

import xyz.marcobasile.model.SAMTwitterUser;

public class TwitterUserRepository /*extends SQLiteOpenHelper*/ {

    private static final int VERSION = 1;
    private static final int PER_PAGE = 100;

    /*public TwitterUserRepository(@Nullable Context context) {
        super(context, DatabaseUtils.TweetUser.TABLE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void save(SAMTwitterUser user) {

    }

    public Set<SAMTwitterUser> findAll() {

        return null;
    }

    public Set<SAMTwitterUser> findAllIn(Set<Long> ids) {

        return null;
    }*/

    // delete: select distinc tweet not in
}
