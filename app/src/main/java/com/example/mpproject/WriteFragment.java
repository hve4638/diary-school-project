package com.example.mpproject;

import android.app.DatePickerDialog;
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

enum MemoMode {
    NEW,
    SHOW,
    EDIT,
    LOCK
}


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WriteFragment extends Fragment implements IFrag {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View vMain;
    Context context;
    ImageButton btnDate, btnSubmit;
    FrameLayout frameDate;
    DatePicker datePicker;
    EditText edtTitle, edtContents;
    MemoDAO memoDAO;
    Memo memo;
    MemoMode mode;
    HGlobal hGlobal;

    public WriteFragment() {
        memo = new Memo();
        mode = MemoMode.NEW;
    }

    public WriteFragment(MemoMode mode, Memo memo) {
        this.memo = memo;
        this.mode = mode;
    }

    public WriteFragment(Memo memo) {
        this.memo = memo;
        this.mode = MemoMode.EDIT;
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
        init();
        
        return vMain;
    }

    void init() {
        memoDAO = MemoDAO.getInstance(context);
        hGlobal = HGlobal.getInstance();
        initView();
        initMemoMode();
        initLayoutListener();
    }

    void initView() {
        btnDate = vMain.findViewById(R.id.btnWriteDate);
        btnSubmit = vMain.findViewById(R.id.btnWriteSubmit);
        frameDate = vMain.findViewById(R.id.frameDate);

        edtTitle = vMain.findViewById(R.id.edtTitle);
        edtContents = vMain.findViewById(R.id.edtContents);
    }

    void initMemoMode() {
        edtTitle.setText(memo.title);
        edtContents.setText(memo.contents);
    }

    void initLayoutListener() {
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        memo.setDate(year, month, day);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(context , listener, memo.getYear(), memo.getMonth(), memo.getDay());
                dialog.show();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMemo();
                back();
            }
        });
    }

    void saveMemo() {
        memo.title = edtTitle.getText().toString();
        memo.contents = edtContents.getText().toString();

        switch(mode) {
            case NEW:
                saveMemoNewMode();
                break;
            case SHOW:
            case EDIT:
                saveMemoEditMode();
                break;
        }
    }

    void saveMemoNewMode() {
        boolean titleIsEmpty, contentsIsEmpty;
        titleIsEmpty = memo.title.equals("");
        contentsIsEmpty = memo.contents.equals("");

        if (titleIsEmpty && contentsIsEmpty) {
            return;
        } else if (titleIsEmpty) {
            memo.title = HUtils.getDateFormat("MM월 dd일의 일기", memo.date);
        }

        memoDAO.addMemo(memo);
    }

    void saveMemoEditMode() {
        boolean titleIsEmpty;
        titleIsEmpty = memo.title.equals("");

        if (titleIsEmpty) {
            memo.title = HUtils.getDateFormat("MM월 dd일의 일기", memo.date);
        }
        memoDAO.editMemo(memo);
    }

    @Override
    public void onBackPressed() {
        saveMemo();
        back();
    }

    void back() {
        if (mode == MemoMode.NEW) {
            closeFragment();
        } else {
            changeShowFragment();
        }
    }

    void closeFragment() {
        MainActivity mainAct = ((MainActivity)getActivity());
        mainAct.changeMainFragment();
    }

    void changeShowFragment() {
        MainActivity mainAct = ((MainActivity)getActivity());
        mainAct.changeFragment(new ShowFragment(memo));
    }

    void showMessage(String text) {
        HUtils.showMessage(context, text);
    }
}























