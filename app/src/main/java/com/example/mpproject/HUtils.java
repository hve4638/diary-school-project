package com.example.mpproject;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

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
}
