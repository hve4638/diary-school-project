package com.example.mpproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MemoDAO {
    static MemoDAO inst = null;
    MemoDBHelper memoDB = null;
    SQLiteDatabase writable;
    SQLiteDatabase readable;
    int lastId;
    int lastEditId;

    static public MemoDAO getInstance(Context context) {
        if (inst == null) {
            inst = new MemoDAO(context);
        }
        return inst;
    }

    private MemoDAO(Context context) {
        memoDB = new MemoDBHelper(context);
        writable = memoDB.getWritableDatabase();
        readable = memoDB.getReadableDatabase();

        lastId = getLastId();
        lastEditId = getLastEditId();
    }

    public int addMemo(Memo memo) {
        int id = lastId++;
        ContentValues cv = getMemoContentValues(memo, id);

        long rowNo = writable.insert("memo", null, cv);
        if (rowNo == -1) {
            System.out.println("MemoDAO.addMemo() : Error occur!");
            return -1;
        } else {
            return id;
        }
    }
    public void editMemo(Memo memo) {
        ContentValues cv = getMemoContentValues(memo, memo.id);

        writable.update("memo", cv, "id=?", new String[] { ((Integer)memo.id).toString() });
    }

    private String getDateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");   // yyyy-MM-dd HH:mm:ss
        String format = formatter.format(date);

        return format;
    }

    private ContentValues getMemoContentValues(Memo memo, int id) {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("lastedit", lastEditId++);
        cv.put("user", memo.user);
        cv.put("locklevel", memo.lockLevel.getValue());
        cv.put("title", memo.title);
        cv.put("contents", memo.contents);
        cv.put("passwd", memo.passwd);
        cv.put("date", getDateFormat(memo.date));
        return cv;
    }

    private int getLastId() {
        Cursor cursor = readable.rawQuery("SELECT id FROM memo order by id desc;", null);

        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }

    private int getLastEditId() {
        Cursor cursor = readable.rawQuery("SELECT lastedit FROM memo order by lastedit desc;", null);

        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }

    public List<Memo> getAllMemo() {
        List<Memo> list = new LinkedList<Memo>();
        Cursor cursor = writable.rawQuery("SELECT title, contents, id, date FROM memo;", null);

        while(cursor.moveToNext()) {
            Memo memo = new Memo();
            memo.title = cursor.getString(0);
            memo.contents = cursor.getString(1);
            memo.id = cursor.getInt(2);
            memo.parseDate(cursor.getString(3));

            list.add(memo);
        }

        return list;
    }

    public Memo getMemoById(int id) {
        Memo memo = new Memo();
        Cursor cursor = writable.rawQuery("SELECT title, contents, id FROM memo WHERE id = "+id+";", null);

        if (cursor.moveToNext()) {
            memo.title = cursor.getString(0);
            memo.contents = cursor.getString(1);
            memo.id = cursor.getInt(2);
        }

        return memo;
    }
}

class MemoDBHelper extends SQLiteOpenHelper {
    public MemoDBHelper(Context context) {
        super(context, "diary.db", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE memo (id INTEGER PRIMARY KEY, user Text, title TEXT, contents TEXT, locklevel INTEGER, passwd TEXT, date TEXT, lastedit INTEGER)");
        //db.execSQL("CREATE TABLE memo (user CHAR(20) PRIMARY KEY, id INTEGER, locklevel INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS memo");
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
}
