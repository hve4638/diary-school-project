package com.example.mpproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MemoFragment extends Fragment implements IFrag {
    protected View vMain;
    protected Context context;
    protected MemoDAO memoDAO;
    protected Memo memo;
    protected EditText edtTitle, edtContents;
    protected ImageButton btnBack, btnEdit, btnOption, btnDate, btnSubmit, btnCamera, btnGallery;
    protected LinearLayout bottomBar;
    protected ImageView imgPicture;
    protected FrameLayout imgFrame;

    public MemoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_memo, container, false);
        context = container.getContext();
        init();
        // Inflate the layout for this fragment
        return vMain;
    }

    protected void init() {
        memoDAO = MemoDAO.getInstance(context);
        initView();
        initListener();

        edtTitle.setText(memo.title);
        edtContents.setText(memo.contents);
        setPicture(memo.image);
    }

    protected void initView() {
        edtTitle = vMain.findViewById(R.id.edtTitle);
        edtContents = vMain.findViewById(R.id.edtContents);
        btnBack = vMain.findViewById(R.id.btnBack);
        btnEdit = vMain.findViewById(R.id.btnEdit);
        btnOption = vMain.findViewById(R.id.btnMemoOption);
        btnDate = vMain.findViewById(R.id.btnMemoDate);
        btnSubmit = vMain.findViewById(R.id.btnEditSubmit);
        btnCamera = vMain.findViewById(R.id.btnCamera);
        btnGallery = vMain.findViewById(R.id.btnGallery);
        bottomBar = vMain.findViewById(R.id.bottomBar);
        imgPicture = vMain.findViewById(R.id.imgPicture);
        imgFrame = vMain.findViewById(R.id.imgFrame);
    }

    protected void initListener() {
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
                            HUtils.showLockDialog(context, memo.lockLevel, (lockLevel) -> {
                                onChangeLockLevel(lockLevel);
                            }, null);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    public void onChangeLockLevel(LockLevel lockLevel) {
        if (memo.lockLevel == lockLevel) return;

        switch(lockLevel) {
            case MASTER_KEY:
                memo.passwd = "";
                memo.lockLevel = lockLevel;
                showMessage("메모를 잠궜습니다");
                break;
            case PRIVATE_KEY:
                HUtils.showInputPasswdDialog(context, (text)-> {
                    setPrivateKey(text);
                    showMessage("메모를 잠궜습니다");
                });
                break;
            case NOTHING:
                memo.passwd = "";
                memo.lockLevel = lockLevel;
                showMessage("잠금이 해제되었습니다");
                break;
        }
    }

    public void setPrivateKey(String text) {
        memo.passwd = text;
        memo.lockLevel = LockLevel.PRIVATE_KEY;
    }

    public void enableWritable(boolean writable) {
        edtTitle.setFocusable(writable);
        edtTitle.setClickable(writable);
        edtTitle.setFocusableInTouchMode(writable);
        edtContents.setFocusable(writable);
        edtContents.setClickable(writable);
        edtContents.setFocusableInTouchMode(writable);
    }

    public void closeFragment() {
        MainActivity mainAct = ((MainActivity)getActivity());
        mainAct.changeMainFragment();
    }

    public void changeFragment(Fragment fragment) {
        MainActivity mainAct = ((MainActivity)getActivity());
        mainAct.changeFragment(fragment);
    }

    public void changeEditFragment() {
        MainActivity mainAct = ((MainActivity)getActivity());
        mainAct.changeFragment(new EditMemoFragment(memo));
    }

    @Override
    public void onBackPressed() {
        closeFragment();
    }

    public void showMessage(String text) {
        HUtils.showMessage(context, text);
    }

    public void setPicture(@Nullable Bitmap image) {
        if (image == null) {
            imgFrame.setVisibility(View.GONE);
        } else {
            imgFrame.setVisibility(View.VISIBLE);
            imgPicture.setImageBitmap(image);
        }
    }
}