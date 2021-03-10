package org.techtown.doitmission22;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BookDatabase {
    public static final String TAG = "BookDatabase";
    public static final String DATABASE_NAME = "book.db";
    public static final String TABLE_NAME = "BOOK_INFO";
    public static final int DATABASE_VERSION = 1;

    private static BookDatabase database;
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public BookDatabase(Context context) {
        this.context = context;
    }

    public void close() {
        db.close();
        database = null;
    }

    public boolean open() {
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();

        return true;
    }

    public static BookDatabase getInstance(Context context) {
        if (database == null) {
            database = new BookDatabase(context);
        }
        return database;
    }

    public class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String DROP_SQL = "drop table if exists " + TABLE_NAME;
            db.execSQL(DROP_SQL);

            String CREATE_SQL = "create table " + TABLE_NAME + "("
                    + "  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  NAME TEXT, "
                    + "  AUTHOR TEXT, "
                    + "  CONTENTS TEXT, "
                    + "  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";
            db.execSQL(CREATE_SQL);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < 2) {   // version 1

            }
        }

        private void insertRecord(SQLiteDatabase db, String title, String author, String contents) {
            String INSERT_SQL = "insert into " + TABLE_NAME + "(NAME, AUTHOR, CONTENTS) values ('" + title + "', '" + author + "', '" + contents + "');";
            db.execSQL(INSERT_SQL);
        }
    }

//    public Cursor rawQuery(String SQL) {
//        Cursor c1 = null;
//        try {
//            c1 = db.rawQuery(SQL, null);
//        } catch(Exception ex) {
//            Log.e(TAG, "Exception in executeQuery", ex);
//        }
//
//        return c1;
//    }
//
//    public boolean execSQL(String SQL) {
//        try {
//            Log.d(TAG, "SQL : " + SQL);
//            db.execSQL(SQL);
//        } catch(Exception ex) {
//            Log.e(TAG, "Exception in executeQuery", ex);
//            return false;
//        }
//
//        return true;
//    }

    public void insertRecord(String title, String author, String contents) {
        String INSERT_SQL = "insert into " + TABLE_NAME + "(NAME, AUTHOR, CONTENTS) values ('" + title + "', '" + author + "', '" + contents + "');";
        db.execSQL(INSERT_SQL);
    }

    public ArrayList<BookInfo> selectAll() {
        ArrayList<BookInfo> bookInfos = new ArrayList<>();

        try {
            Cursor cursor = db.rawQuery("select NAME, AUTHOR, CONTENTS from " + TABLE_NAME, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String title = cursor.getString(0);
                String author = cursor.getString(1);
                String contents = cursor.getString(2);

                BookInfo info = new BookInfo(title, author, contents);
                bookInfos.add(info);
            }

        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }

        return bookInfos;
    }
}
