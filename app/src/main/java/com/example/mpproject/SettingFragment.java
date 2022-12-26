package com.example.mpproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    protected View vMain;
    protected Context context;
    protected HGlobalSetting setting;
    protected HLayout hLayout;
    LinearLayout layout;

    public SettingFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_setting, container, false);
        context = container.getContext();
        setting = HGlobalSetting.getInstance();

        init();
        return vMain;
    }

    void init() {
        hLayout = new HLayout(context);
        initView();

    }

    void initView() {
        //layout = vMain.findViewById(R.id.setting);

    }
}