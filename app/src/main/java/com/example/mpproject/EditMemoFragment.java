package com.example.mpproject;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.File;
import java.io.InputStream;

public class EditMemoFragment extends MemoFragment {
    static final int REQUEST_GALLERY = 1;
    static final int REQUEST_CAMERA = 2;
    MemoMode mode;

    public EditMemoFragment() {
        this.memo = new Memo();
        mode = MemoMode.NEW;
    }

    public EditMemoFragment(Memo memo) {
        this.memo = memo;
        mode = MemoMode.EDIT;
        System.out.println("\n\n\n");
        System.out.println("image:" + memo.toString());
        System.out.println("\n\n\n");
    }

    @Override
    protected void initView() {
        super.initView();

        btnBack.setVisibility(View.GONE);
        btnEdit.setVisibility(View.GONE);
        btnCamera.setVisibility(View.GONE);
        enableWritable(true);
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

                //AlertDialog.THEME_HOLO_LIGHT
                DatePickerDialog dialog = new DatePickerDialog(context, listener, memo.getYear(), memo.getMonth(), memo.getDay());
                DatePicker datePicker = dialog.getDatePicker();

                dialog.show();

                dialog.getWindow().getDecorView().setBackgroundColor(Color.argb(255, 100, 100, 100));
                //int h = dialog.getWindow().getDecorView().getHeight();
                //int ww = dialog.getWindow().getDecorView().get();
                //System.out.println("  >>>>>>>>>>>>>> # "+ w + ", "+ h);

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMemo();
                back();
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_GALLERY);
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
        if (memo.hasNoContents()) return;
        memo.preprocess(context);
        memoDAO.addMemo(memo);
        showMessage("저장되었습니다");
    }

    void saveMemoEditMode() {
        memo.preprocess(context);
        memoDAO.editMemo(memo);
        showMessage("저장되었습니다");
    }

    void back() {
        if (mode == MemoMode.NEW) {
            closeFragment();
        } else {
            changeFragment(new ShowMemoFragment(memo));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GALLERY:
                onActivityGallery(resultCode, data);
                break;
            case REQUEST_CAMERA:
                onActivityCamera(resultCode, data);
                break;
        }
    }

    private void onActivityGallery(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            try {
                InputStream in = context.getContentResolver().openInputStream(data.getData());

                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();

                memo.imagePath = "";
                memo.image = img;
                setPicture(img);
            } catch (Exception ex) {
                showMessage("오류가 발생했습니다");
            }
        }
    }

    private void onActivityCamera(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bitmap img = (Bitmap) data.getExtras().get("data");
            setPicture(img);
            showMessage("ok???");
        } else if (resultCode == Activity.RESULT_CANCELED) {
            showMessage("canceled");
        }
    }
}



















