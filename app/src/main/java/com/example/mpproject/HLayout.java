package com.example.mpproject;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

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
        layout.setOrientation(LinearLayout.HORIZONTAL);

        return layout;
    }

    public LinearLayout columnLinear() {
        return columnLinear(0f);
    }

    public LinearLayout columnLinear(float weight) {
        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT, weight);
        layout.setOrientation(LinearLayout.VERTICAL);

        return layout;
    }
}
