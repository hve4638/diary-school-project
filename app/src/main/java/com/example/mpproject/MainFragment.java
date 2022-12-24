package com.example.mpproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    LinearLayout scrollList;
    FloatingActionButton btnAddMemo;
    View view;
    Context context;
    MemoDAO memoDAO;
    HLayout hLayout;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


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
            View layout = makeDiaryLayout(memo);
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

    View makeDiaryLayout(Memo memo) {
        final int id = memo.id;
        LinearLayout layout = hLayout.rowLinear();
        layout.setPadding(30, 30, 30, 30);

        LinearLayout col1 = hLayout.columnLinear(1f);
        LinearLayout col2 = hLayout.columnLinear(1f);

        layout.addView(col1);
        layout.addView(hLayout.weightLayout(1f));
        layout.addView(col2);

        TextView titleView = (TextView)hLayout.wrappedTextViewExtSize(memo.title, 20);
        TextView dateView = (TextView)hLayout.wrappedTextViewExtSize(getDateString(memo), 10);

        col1.addView(titleView);
        col2.addView(hLayout.weightLayout(1f));
        col2.addView(dateView);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMessage("id: " + id);
                changeWritePage(id);
            }
        });

        return layout;
    }

    String getDateString(Memo memo) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");   // yyyy-MM-dd HH:mm:ss
        return formatter.format(memo.date);
    }

    void showMessage(String text) {
        HUtils.showMessage(context, text);
    }

    void changeWritePage() {
        MainActivity mainAct = ((MainActivity) getActivity());
        mainAct.changeWriteFragment();
    }

    void changeWritePage(int id) {
        MainActivity mainAct = ((MainActivity) getActivity());
        mainAct.changeWriteFragment(id);
    }
}