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

public class MemoListFragment extends Fragment {
    protected View vMain;
    protected Context context;
    protected MemoDAO memoDAO;
    protected HLayout hLayout;
    LinearLayout scrollList;
    FloatingActionButton btnAddMemo;

    public MemoListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_memo_list, container, false);
        context = container.getContext();
        init();

        return vMain;
    }

    void init() {
        memoDAO = MemoDAO.getInstance(context);
        hLayout = new HLayout(context);

        initView();
    }

    void initView() {
        scrollList = (LinearLayout) vMain.findViewById(R.id.scrollList);
        btnAddMemo = (FloatingActionButton) vMain.findViewById(R.id.btnAddMemo);

        List<Memo> list = getMemoList();
        for(Memo memo : list) {
            final int id = memo.id;
            View layout = hLayout.diaryLayout(memo, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMemo(id);
                }
            });
            scrollList.addView(layout);

            View vLine = hLayout.rowLine();
            scrollList.addView(vLine);
        }
        btnAddMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeWritePage();
            }
        });
    }

    List<Memo> getMemoList() {
        return memoDAO.getAllMemo();
    }

    void changeWritePage() {
        MainActivity mainAct = ((MainActivity) getActivity());
        mainAct.changeWriteFragment();
    }

    void openMemo(int id) {
        Memo memo = memoDAO.getMemoById(id);

        MainActivity mainAct = ((MainActivity) getActivity());
        mainAct.changeFragment(new ShowMemoFragment(memo));
    }

    void showMessage(String text) {
        HUtils.showMessage(context, text);
    }
}