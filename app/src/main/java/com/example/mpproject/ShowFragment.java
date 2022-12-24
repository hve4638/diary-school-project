package com.example.mpproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowFragment extends Fragment implements IFrag {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View vMain;
    Context context;
    Memo memo;
    EditText edtTitle, edtContents;
    ImageButton btnBack, btnEdit;

    public ShowFragment() {
        // Required empty public constructor
    }

    public ShowFragment(Memo memo) {
        this.memo = memo;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowFragment newInstance(String param1, String param2) {
        ShowFragment fragment = new ShowFragment();
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
        vMain = inflater.inflate(R.layout.fragment_show, container, false);
        context = container.getContext();
        init();
        // Inflate the layout for this fragment
        return vMain;
    }

    void init() {
        initView();

        edtTitle.setText(memo.title);
        edtContents.setText(memo.contents);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEditFragment();
            }
        });
    }

    void initView() {
        edtTitle = vMain.findViewById(R.id.edtTitle);
        edtContents = vMain.findViewById(R.id.edtContents);
        btnBack = vMain.findViewById(R.id.btnBack);
        btnEdit = vMain.findViewById(R.id.btnEdit);
    }

    void closeFragment() {
        MainActivity mainAct = ((MainActivity)getActivity());
        mainAct.changeMainFragment();
    }

    void changeEditFragment() {
        MainActivity mainAct = ((MainActivity)getActivity());
        mainAct.changeFragment(new WriteFragment(memo));
    }

    @Override
    public void onBackPressed() {
        closeFragment();
    }
}



















