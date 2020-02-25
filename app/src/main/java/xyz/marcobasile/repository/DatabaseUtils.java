package xyz.marcobasile.repository;

public class DatabaseUtils {

    public static class Tweet {
        // Tabella per i tweet salvati dalla Home Timeline
        public static final String TABLE = "TWEET";

        // Columns
        public static final String ID = "ID";
        public static final String TEXT = "TWEET_TEXT";
        public static final String USER_ID = "USER_ID";
        public static final String FAVOURITE_COUNT = "FAVOURITE_COUNT";
        public static final String PLACE = "PLACE";
        public static final String RETWEET_COUNT = "RETWEET_COUNT";

        // Query
        public static final String SAVE = "";
        public static final String FIND_ONE = "";
        public static final String SEARCH = "";
        public static final String REMOVE = "";
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY," +
                    TEXT + " TEXT NOT NULL," +
                    USER_ID + " INTEGER REFERENCES TW_USER(ID)," +
                    FAVOURITE_COUNT + " INTEGER NOT NULL," +
                    PLACE + " TEXT," +
                    RETWEET_COUNT + " INTEGER NOT NULL)";

    }

    public static class TweetUser {
        // Tabella per gli utenti dei tweet salvati
        public static final String TABLE = "TW_USER";

        // Columns
        public static final String ID = "ID";
        public static final String SCREEN_NAME = "SCREEN_NAME";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String FOLLOWERS_COUNT = "FOLLOWERS_COUNT";
        public static final String FRIENDS_COUNT = "FRIENDS_COUNT";
        public static final String STATUSES_COUNT = "STATUSES_COUNT";
        public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
        public static final String PROFILE_IMAGE_BLOB = "PROFILE_IMAGE_BLOB";
        public static final String LATITUDE = "LATITUDE";
        public static final String LONGITUDE = "LONGITUDE";

        // Query
        public static final String SAVE = "";
        public static final String FIND_ONE = "";
        public static final String SEARCH = "";
        public static final String REMOVE = "";
        public static final String CREATE_TABLE =
                "CREATE TABLE "+ TABLE +" (" +
                    ID + " INTEGER PRIMARY KEY," +
                    SCREEN_NAME + " TEXT NOT NULL," +
                    DESCRIPTION + " TEXT," +
                    FOLLOWERS_COUNT + " INTEGER," +
                    FRIENDS_COUNT + " INTEGER," +
                    STATUSES_COUNT + " INTEGER," +
                    PROFILE_IMAGE_URL + " TEXT," +
                    PROFILE_IMAGE_BLOB + " BLOB," +
                    LATITUDE + " TEXT," +
                    LONGITUDE + " TEXT)";
    }

}