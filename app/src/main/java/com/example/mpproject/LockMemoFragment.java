package com.example.mpproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;


public class LockMemoFragment extends MemoListFragment {
    ImageView vMemoLock;

    @Override
    List<Memo> getMemoList() {
        return new LinkedList<Memo>();
    }

    @Override
    void init() {
        super.init();

        scrollList.setVisibility(View.GONE);
        btnAddMemo.setVisibility(View.GONE);
        vMemoLock.setVisibility(View.VISIBLE);
        vMemoLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlock();
            }
        });
    }

    void initView() {
        super.initView();
        vMemoLock = vMain.findViewById(R.id.vMemoLock);
    }

    void unlock() {
        MainActivity mainAct = ((MainActivity) getActivity());
        mainAct.checkGlobalLock(()->{
            mainAct.changeMainFragment();
        });
    }
}