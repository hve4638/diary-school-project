package com.example.mpproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

enum LockLevel {
    NOTHING(0),
    MASTER_KEY(1),
    PRIVATE_KEY(2);

    private final int value;
    private LockLevel(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}

public class Memo {
    public String title, contents;
    public Date date;
    public int id;
    public String user;
    public LockLevel lockLevel;
    public String passwd;

    public Memo() {
        id = -1;
        title = "";
        contents = "";
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
}
