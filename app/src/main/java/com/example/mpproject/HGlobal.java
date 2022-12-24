package com.example.mpproject;

public class HGlobal {
    static private HGlobal inst = null;
    private MemoMode memoMode;
    private Memo memo;

    static public HGlobal getInstance() {
        if (inst == null) {
            inst = new HGlobal();
        }
        return inst;
    }

    private HGlobal() {
        memoMode = MemoMode.NEW;
        memo = null;
    }

    public void setMemoMode(MemoMode mode) {
        memoMode = mode;
    }

    public void setMemoMode(MemoMode mode, Memo memo) {
        memoMode = mode;
        this.memo = memo;
    }

    public Memo getMemo() {
        return memo;
    }

    public MemoMode getWritePageMode() {
        return memoMode;
    }
}
