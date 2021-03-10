package org.techtown.doitmission21;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDatabase {
    private static BookDatabase database;
    public static String DATABASE_NAME = "book.db";
    public static String TABLE_BOOK_INFO = "BOOK_INFO";
    public static int DATABASE_VERSION = 1;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public BookDatabase(Context context) {
        this.context = context;
    }

    public static BookDatabase getInstance(Context context) {
        if (database == null) {
            database = new BookDatabase(context);
        }
        return database;
    }

    public boolean open() {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return true;
    }

    public void close() {
        db.close();
        database = null;
    }


    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            String DROP_SQL = "drop table if exists " + TABLE_BOOK_INFO;
            db.execSQL(DROP_SQL);  //동일한 이름의 테이블이 존재하는지 확인해서 drop

            String CREATE_SQL = "create table " + TABLE_BOOK_INFO + "("
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

        }
    }

    public void insertRecord(String title, String author, String contents) {
        String INSERT_SQL = "insert into " + TABLE_BOOK_INFO + "(NAME, AUTHOR, CONTENTS) values" +
                "('" + title + "', '" + author + "', '" + contents + "');";
        db.execSQL(INSERT_SQL);
    }
}
