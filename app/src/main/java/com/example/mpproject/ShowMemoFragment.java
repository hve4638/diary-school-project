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

public class ShowMemoFragment extends MemoFragment {
    public ShowMemoFragment() {

    }

    public ShowMemoFragment(Memo memo) {
        this.memo = memo;
    }

    @Override
    protected void initView() {
        super.initView();

        btnDate.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        bottomBar.setVisibility(View.GONE);
        enableWritable(false);
        memo.tryLoadImage(context);
    }

    @Override
    protected void initListener() {
        super.initListener();

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
        edtTitle.setOnClickListener(doubleClickToEdit());
        edtContents.setOnClickListener(doubleClickToEdit());
    }

    private View.OnClickListener doubleClickToEdit() {
        return new View.OnClickListener() {
            private long lastClickTime = 0;

            @Override
            public void onClick(View v) {
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < 500) {
                    changeEditFragment();
                }

                lastClickTime = clickTime;
            }
        };
    }

    public void changeEditFragment() {
        MainActivity mainAct = ((MainActivity) getActivity());
        mainAct.changeFragment(new EditMemoFragment(memo));
    }

    @Override
    public void onChangeLockLevel(LockLevel lockLevel) {
        super.onChangeLockLevel(lockLevel);
        memoDAO.editMemo(memo);
    }

    public void setPrivateKey(String text) {
        memo.passwd = text;
        memo.lockLevel = LockLevel.PRIVATE_KEY;
        memoDAO.editMemo(memo);
    }
}



















