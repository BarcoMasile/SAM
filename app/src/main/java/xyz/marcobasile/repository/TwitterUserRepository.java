package xyz.marcobasile.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TwitterUserRepository extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final int PER_PAGE = 100;

    public TwitterUserRepository(String tableName, @Nullable Context context) {
        super(context, tableName, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // delete: select distinc tweet not in
}
