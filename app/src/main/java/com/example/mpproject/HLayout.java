package com.example.mpproject;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import java.text.SimpleDateFormat;

public class HLayout {
    final int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;
    final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    Context context;

    public HLayout(Context context) {
        this.context = context;
    }

    public View makeTextView(String text) {
        TextView tView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        tView.setLayoutParams(params);
        tView.setText(text);

        return tView;
    }

    public View rowLine() {
        return rowLine(Color.DKGRAY);
    }

    public View rowLine(@ColorInt int color) {
        return rowLine(color, 3);
    }

    public View rowLine(@ColorInt int color, int height) {
        View view = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(params);
        view.setBackgroundColor(color);
        return view;
    }

    public View weightLayout(float weight) {
        TextView tView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, weight);
        tView.setLayoutParams(params);

        return tView;
    }

    public View wrappedTextView(String text) {
        return wrappedTextViewExtWeight(text, 0f);
    }

    public View wrappedTextViewExtSize(String text, float size) {
        TextView tView = (TextView)wrappedTextViewExtWeight(text, 0f);
        tView.setTextSize(size);
        return tView;
    }

    public View wrappedTextViewExtWeight(String text, float weight) {
        TextView tView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, weight);
        tView.setLayoutParams(params);
        tView.setText(text);

        return tView;
    }

    public LinearLayout rowLinear() {
        return rowLinear(0f);
    }

    public LinearLayout rowLinear(float weight) {
        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT, weight);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        return layout;
    }

    public LinearLayout columnLinear() {
        return columnLinear(0f);
    }

    public LinearLayout columnLinear(float weight) {
        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT, weight);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        return layout;
    }

    public View diaryLayout(Memo memo, View.OnClickListener listener) {
        LinearLayout view = (LinearLayout) diaryLayout(memo);
        view.setOnClickListener(listener);

        return view;
    }


    public View diaryLayout(Memo memo) {
        LinearLayout layout = rowLinear();
        layout.setMinimumHeight(80);
        layout.setPadding(30, 30, 30, 30);

        LinearLayout col1 = columnLinear(0f);
        LinearLayout col2 = columnLinear(0f);

        View lock = imageLock();
        if (memo.lockLevel == LockLevel.NOTHING) {
            lock.setVisibility(View.INVISIBLE);
        }
        layout.addView(col1);
        layout.addView(lock);
        layout.addView(weightLayout(1f));
        layout.addView(col2);

        TextView titleView = (TextView)wrappedTextViewExtSize(memo.title, 20);
        TextView dateView = (TextView)wrappedTextViewExtSize(getDateString(memo), 10);

        col1.addView(titleView);
        col2.addView(weightLayout(1f));
        col2.addView(dateView);

        return layout;
    }

    private String getDateString(Memo memo) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        return formatter.format(memo.date);
    }

    public View imageLock() {
        ImageView iView = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(70, 70);
        iView.setLayoutParams(params);
        iView.setImageResource(R.drawable.ic_lock);

        iView.setBackground(null);
        iView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iView.setPadding(20, 5, 20, 5);

        return iView;
    }
}
