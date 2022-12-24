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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout scrollList;
    View view;
    Context context;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WriteFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        initView();
        return view;
    }

    void initView() {
        scrollList = (LinearLayout) view.findViewById(R.id.scrollList);
        View v = view.findViewById(R.id.vline);

        ViewGroup.LayoutParams params = v.getLayoutParams();
        System.out.println("params : " + params.width + "," + params.height);

        for(int i=0;i<100; i++) {
            int color = (i % 2 == 0) ? Color.BLUE : Color.RED;
            LinearLayout layout = (LinearLayout)makeDiaryLayout(color);
            scrollList.addView(layout);

            View vLine = makeLine();
            scrollList.addView(vLine);

            //System.out.println("text : " + i);
        }
        //scrollList.addView();
    }

    View makeDiaryLayout(int color) {
        LinearLayout layout = new LinearLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setPadding(20, 50, 20, 50);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        //layout.setBackgroundColor(color);
        layout.addView(makeTextView());
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("hello");
            }
        });

        return layout;
    }

    View makeTextView() {
        TextView tView = new TextView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tView.setLayoutParams(params);
        tView.setText("text test");

        return tView;
    }

    View makeLine() {
        View view = new View(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        //view.setPadding(20, 50, 20, 50);
        view.setBackgroundColor(Color.RED);
        return view;

    }

    void showMessage(String text) {
        HUtils.showMessage(context.getApplicationContext(), text);
    }
}