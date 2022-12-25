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
        // Required empty public constructor
    }

    public ShowMemoFragment(Memo memo) {
        this.memo = memo;
    }

    @Override
    protected void initView() {
        super.initView();

        btnDate.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
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

    public void changeEditFragment() {
        MainActivity mainAct = ((MainActivity) getActivity());
        mainAct.changeFragment(new EditMemoFragment(memo));
    }
}



















