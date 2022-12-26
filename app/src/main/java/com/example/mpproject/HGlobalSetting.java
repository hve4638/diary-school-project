package com.example.mpproject;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HGlobalSetting {
    private Map<String, String> map;

    static private HGlobalSetting inst = null;
    static public HGlobalSetting getInstance() {
        if (inst == null) {
            inst = new HGlobalSetting();
        }
        return inst;
    }

    private HGlobalSetting() {
        map = new HashMap<String, String>();
        setGlobalLock(false);
        setMasterKey("1234");
    }

    public void setMasterKey(String key) {
        map.put("master-key", key);
    }

    public String getMasterKey() {
        String key = map.get("master-key");
        return (key == null) ? "" : key;
    }

    public void setGlobalLock(boolean lock) {
        map.put("global-lock", (lock) ? "T" : "F");
    }

    public boolean getGlobalLock() {
        String key = map.get("global-lock");

        if (key == null) return false;
        return (key.equals("T")) ? true : false;
    }

    public void save(Context context) {
        FileMap fm = new FileMap(context, "option.csv");

        if (fm.write(map)) {
        } else {
        }
    }

    public void load(Context context) {
        FileMap fm = new FileMap(context, "option.csv");
        if (fm.read(map)) {
        } else {
        }
    }

}
