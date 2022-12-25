package com.example.mpproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Memo {
    public String title, contents;
    public Date date;
    public int id;
    public String user;
    public LockLevel lockLevel;
    public String passwd;
    public Bitmap image;
    public String imagePath;

    public Memo() {
        id = -1;
        title = "";
        contents = "";
        image = null;
        imagePath = "";
        date = new Date();
        lockLevel = LockLevel.NOTHING;
    }


    public void setDate(int year, int month, int day) {
        date.setYear(year-1900);
        date.setMonth(month);
        date.setDate(day);
    }

    public int getYear() {
        return date.getYear() + 1900;
    }

    public int getMonth() {
        return date.getMonth();
    }

    public int getDay() {
        return date.getDate();
    }

    public void parseDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        try {
            date = formatter.parse(str);
        }
        catch(ParseException ex) {
            date = new Date();
        }
    }

    public void tryLoadImage(Context context) {
        image = HUtils.loadBitmap(context, imagePath);
    }

    public void preprocess(Context context) {
        if (title.equals("")) {
            title = HUtils.getDateFormat("MM월 dd일의 일기", date);
        }

        if (image != null && imagePath.equals("")) {
            imagePath = makeFileName();
            HUtils.saveBitmap(context, image, imagePath);
        } else if (image == null && !imagePath.equals("")) {
            imagePath = "";
        }
    }

    private String makeFileName() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("ddMMyy-hhmmss");
        return "File-" + format.format(new Date()) + ".jpeg";
    }

    @Override
    public String toString() {
        return "memo(id:" + id + " date" + date + " lock:" + lockLevel + " passwd:" + passwd + " path:" + imagePath +")";
    }
}
