package com.example.mpproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebugDBFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebugDBFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View vMain;
    Context context;

    public DebugDBFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DebugDBFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DebugDBFragment newInstance(String param1, String param2) {
        DebugDBFragment fragment = new DebugDBFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vMain = inflater.inflate(R.layout.fragment_debug_d_b, container, false);
        context = container.getContext();
        TextView tView = (TextView)vMain.findViewById(R.id.tDebugDB);
        MemoDAO memoDAO = MemoDAO.getInstance(context);

        String str = memoDAO.dbgGetAllMemo();
        tView.setText(str);

        return vMain;
    }
}











