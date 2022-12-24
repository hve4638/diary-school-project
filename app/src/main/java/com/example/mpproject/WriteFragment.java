package com.example.mpproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WriteFragment extends Fragment implements IBackPress {
    private static final String DIALOG_DATE = "MainActivity.DateDialog";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View vMain;
    ImageButton btnDate, btnSubmit;
    Context context;
    FrameLayout frameDate;
    DatePicker datePicker;
    EditText edtText;
    String title;
    String contents;


    public WriteFragment() {

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
    public static WriteFragment newInstance(String param1, String param2) {
        WriteFragment fragment = new WriteFragment();
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
        // Inflate the layout for this fragment
        vMain = inflater.inflate(R.layout.fragment_write, container, false);
        context = container.getContext();
        initView();

        btnDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean isVisibility = (frameDate.getVisibility() == View.VISIBLE);
                setEnableDate(isVisibility);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HUtils.showMessage(context, "submit");
            }
        });
        datePicker.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                HUtils.showMessage(context, "focus");
                if (!hasFocus) {
                    //setEnableDate(false);
                } else {
                }
            }
        });
        
        return vMain;
    }

    void initView() {
        btnDate = vMain.findViewById(R.id.btnWriteDate);
        btnSubmit = vMain.findViewById(R.id.btnWriteSubmit);
        frameDate = vMain.findViewById(R.id.frameDate);

        edtText = vMain.findViewById(R.id.edtText);
        datePicker = vMain.findViewById(R.id.datePicker);
    }

    void setEnableDate(boolean isVisibility) {
        if (isVisibility) {
            frameDate.setVisibility(View.GONE);
            edtText.setClickable(true);
            edtText.setFocusable(true);
            edtText.setFocusableInTouchMode(true);
        } else {
            frameDate.setVisibility(View.VISIBLE);
            edtText.setClickable(false);
            edtText.setFocusable(false);
            edtText.setFocusableInTouchMode(false);
            closeKeyboard();
        }
    }

    void closeKeyboard() {
        MainActivity activity = (MainActivity) getActivity();
        HUtils.closeKeyboard(activity, edtText);
    }

    @Override
    public void onBackPressed() {
        requireActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        requireActivity().getSupportFragmentManager().popBackStack();
        HUtils.showMessage(context, "jhhjhj");

    }
}























