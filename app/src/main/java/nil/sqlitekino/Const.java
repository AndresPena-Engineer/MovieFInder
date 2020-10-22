package nil.sqlitekino;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class Const extends SQLiteOpenHelper {
    public static final String EXTRA_KEY = "dat";
    public static final String IMG_KEY= "img";
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    public static SQLiteDatabase db;
    public static boolean onLaunch = true;

    public Const(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static final String TABLE_NAME = "MOV";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    row._ID + " INTEGER PRIMARY KEY," +
                    row.COL_NAME_TITLE + " TEXT," +
                    row.COL_NAME_PRICE + " TEXT," +
                    row.COL_NAME_GENRE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static class row implements BaseColumns {
        public static final String COL_NAME_TITLE = "title";
        public static final String COL_NAME_GENRE = "genre";
        public static final String COL_NAME_PRICE = "price";
    }
}