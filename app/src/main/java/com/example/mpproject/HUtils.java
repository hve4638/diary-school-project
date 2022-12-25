package com.example.mpproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HUtils {
    static public void showMessage(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    static public void closeKeyboard(MainActivity activity)
    {
        View view = activity.getCurrentFocus();
        closeKeyboard(activity, view);
    }

    static public void closeKeyboard(MainActivity activity, View view)
    {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    static public String getDateFormat(String pattern, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String format = formatter.format(date);

        return format;
    }

    static public void showDeleteDialog(Context context, DialogInterface.OnClickListener positiveListener,
                          DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("삭제");
        dialog.setMessage("정말 삭제하시겠습니까?");
        dialog.setPositiveButton("확인", positiveListener);
        dialog.setNegativeButton("취소", negativeListener);
        dialog.show();
    }

    static public void showLockDialog(Context context, DialogInterface.OnClickListener selectListener, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        final String[] options = new String[] { "잠금 안함", "마스터키 잠금", "개별키 잠금" };
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("잠금");
        dialog.setSingleChoiceItems(options, 0, selectListener);
        dialog.setPositiveButton("확인", positiveListener);
        dialog.setNegativeButton("취소", negativeListener);
        dialog.show();
    }
}
