package org.techtown.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 1;
    //칼럼 추가
    public static final String TABLE_NAME = "person";
    public static final String PERSON_ID = "_id";
    public static final String PERSON_NAME = "name";
    public static final String PERSON_AGE = "age";
    public static final String PERSON_MOBILE = "mobile";
    //칼럼들을 배열로 가짐
    public static final String[] ALL_COLUMNS = {PERSON_ID, PERSON_NAME, PERSON_AGE, PERSON_MOBILE};

    private static final String CREATE_TABLE = "create table " + TABLE_NAME
            + "("
            + PERSON_ID + " integer primary key autoincrement, "
            + PERSON_NAME + " text, "
            + PERSON_AGE + " integer,"
            + PERSON_MOBILE + " text" + ")";

    public DatabaseHelper(@Nullable Context context) {
        super (context, TABLE_NAME, null, DATABASE_VERSION);
//        super(context, name, factory, version);       factory는 변경할때 필요
    }

    //버전을 업그레이드할때 이전버전의 데이터베이스가 없다면 onCreate 호출
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    //버전을 업그레이드할때 이전버전의 데이터베이스가 있다면 onUpgrade 호출
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //실제로는 데이터를 삭제해선 안되고 보존한채로 버전을 바꿔야함
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}
