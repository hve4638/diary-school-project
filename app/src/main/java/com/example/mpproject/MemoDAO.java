package com.example.mpproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDAO extends SQLiteOpenHelper {
    public MemoDAO(Context context) {
        super(context, "diary.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE memo (id INTEGER PRIMARY KEY, user Text, locklevel INTEGER, title TEXT, contents TEXT)");
        //db.execSQL("CREATE TABLE memo (user CHAR(20) PRIMARY KEY, id INTEGER, locklevel INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS groupTBL");
        onCreate(db);
    }
}