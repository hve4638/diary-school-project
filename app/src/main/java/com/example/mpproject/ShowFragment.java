package com.example.mpproject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class ShowFragment extends Fragment implements IFrag {
    View vMain;
    Context context;
    MemoDAO memoDAO;
    Memo memo;
    EditText edtTitle, edtContents;
    ImageButton btnBack, btnEdit, btnOption;

    public ShowFragment() {
        // Required empty public constructor
    }

    public ShowFragment(Memo memo) {
        this.memo = memo;
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
        memoDAO = MemoDAO.getInstance(context);
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
        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.actionDelete) {
                            HUtils.showDeleteDialog(context,
                                    (dialog, which) -> {
                                        memoDAO.deleteMemo(memo);
                                        closeFragment();
                                        showMessage("삭제되었습니다");
                                    },
                            null);
                        } else {

                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });
    }

    void initView() {
        edtTitle = vMain.findViewById(R.id.edtTitle);
        edtContents = vMain.findViewById(R.id.edtContents);
        btnBack = vMain.findViewById(R.id.btnBack);
        btnEdit = vMain.findViewById(R.id.btnEdit);
        btnOption = vMain.findViewById(R.id.btnEditOption);
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

    void showMessage(String text) {
        HUtils.showMessage(context, text);
    }
}



















