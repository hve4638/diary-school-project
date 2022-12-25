package com.example.mpproject;

public enum LockLevel {
    NOTHING(0),
    MASTER_KEY(1),
    PRIVATE_KEY(2);


    private final int value;
    private LockLevel(int value) {
        this.value = value;
    }
    static public LockLevel parseInt(int x) {
        switch (x) {
            case 0:
                return LockLevel.NOTHING;
            case 1:
                return LockLevel.MASTER_KEY;
            case 2:
                return LockLevel.PRIVATE_KEY;
            default:
                return null;
        }
    }
    public int getValue() {
        return value;
    }
}
