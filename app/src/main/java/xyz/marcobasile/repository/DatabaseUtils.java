package xyz.marcobasile.repository;

public class DatabaseUtils {

    public static class Tweet {
        // Tabella per i tweet salvati dalla Home Timeline
        public static final String TABLE = "TWEET";

        // Columns
        public static final String ID = "_id";
        public static final String TEXT = "TWEET_TEXT";
        public static final String FAVOURITE_COUNT = "FAVOURITE_COUNT";
        public static final String RETWEET_COUNT = "RETWEET_COUNT";
        public static final String SAVED = "SAVED";
        public static final String MEDIA_URL = "MEDIA_URL";
        public static final String SCREEN_NAME = "SCREEN_NAME";
        public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";

        public static final String[] ALL_COLUMNS = new String[] {
                ID,
                TEXT,
                FAVOURITE_COUNT,
                RETWEET_COUNT,
                SAVED,
                MEDIA_URL,
                SCREEN_NAME,
                PROFILE_IMAGE_URL
        };

        // Query
        public static final String FIND_ONE = ID + "= ?";
        public static final String SEARCH = "upper(" + TEXT + ") LIKE ? ";
        public static final String DELETE = ID + "= ?";
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY," +
                    TEXT + " TEXT NOT NULL," +
                    FAVOURITE_COUNT + " INTEGER NOT NULL," +
                    RETWEET_COUNT + " INTEGER NOT NULL," +
                    SAVED + " INTEGER DEFAULT 0," +
                    MEDIA_URL + " TEXT," +
                    SCREEN_NAME + " TEXT," +
                    PROFILE_IMAGE_URL + " TEXT)";

    }

}