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

public class MainFragment extends Fragment {
    View view;
    Context context;
    MemoDAO memoDAO;
    HLayout hLayout;
    LinearLayout scrollList;
    FloatingActionButton btnAddMemo;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        context = container.getContext();
        init();

        return view;
    }

    void init() {
        memoDAO = MemoDAO.getInstance(context);
        hLayout = new HLayout(context);
        initView();
    }

    void initView() {
        scrollList = (LinearLayout) view.findViewById(R.id.scrollList);
        btnAddMemo = (FloatingActionButton) view.findViewById(R.id.btnAddMemo);

        List<Memo> list = memoDAO.getAllMemo();
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