package com.example.mpproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MemoDateOrderFragment extends MemoListFragment {
    @Override
    List<Memo> getMemoList() {
        return memoDAO.getAllMemo("date");
    }
}