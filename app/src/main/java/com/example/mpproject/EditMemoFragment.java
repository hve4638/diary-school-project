package com.example.mpproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditMemoFragment extends MemoFragment {
    MemoMode mode;

    public EditMemoFragment() {
        mode = MemoMode.NEW;
    }

    public EditMemoFragment(Memo memo) {
        this.memo = memo;
        mode = MemoMode.EDIT;
    }

    @Override
    protected void initView() {
        super.initView();

        btnBack.setVisibility(View.GONE);
        btnEdit.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        super.initListener();
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
        showMessage("저장되었습니다");
    }

    void saveMemoEditMode() {
        boolean titleIsEmpty;
        titleIsEmpty = memo.title.equals("");

        if (titleIsEmpty) {
            memo.title = HUtils.getDateFormat("MM월 dd일의 일기", memo.date);
        }
        memoDAO.editMemo(memo);
        showMessage("저장되었습니다");
    }

    void back() {
        if (mode == MemoMode.NEW) {
            closeFragment();
        } else {
            showMessage("hiii");
            changeFragment(new ShowMemoFragment(memo));
        }
    }
}



















