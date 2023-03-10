package com.example.mpproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment implements IFrag {
    protected View vMain;
    protected Context context;
    protected HGlobalSetting setting;
    protected HLayout hLayout;
    LinearLayout btnGlobalLock, btnChangeMasterKey, btnCleanMemo;
    CheckBox checkboxGlobalLock;

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
        initViewListener();

        updateGlobalLock();
    }

    void initView() {
        btnGlobalLock = vMain.findViewById(R.id.btnSetGlobalLock);
        btnChangeMasterKey = vMain.findViewById(R.id.btnChangeMasterKey);
        checkboxGlobalLock = vMain.findViewById(R.id.checkboxGlobalLock);
        btnCleanMemo = vMain.findViewById(R.id.btnCleanMemo);
    }

    void initViewListener() {
        btnGlobalLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.setGlobalLock(!setting.getGlobalLock());
                updateGlobalLock();
            }
        });
        btnChangeMasterKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeMasterKey();
            }
        });
        btnCleanMemo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HUtils.showDeleteDialog(context, (dialog, which) -> {
                    HUtils.showInputPasswdDialog(context, "????????? ??? ??????", (text) -> {
                        HGlobalSetting setting = HGlobalSetting.getInstance();
                        String key = setting.getMasterKey();
                        if (key.equals(text)) {
                            MemoDAO.getInstance(context).deleteAllMemo();
                            showMessage("?????? ????????? ??????????????????");
                        } else {
                            showMessage("??????????????? ???????????? ????????????");
                        }
                    });
                }, null);
            }
        });
    }


    void updateGlobalLock() {
        checkboxGlobalLock.setChecked(setting.getGlobalLock());
    }

    void onChangeMasterKey() {
        HUtils.showInputPasswdDialog(context, "?????? ????????? ??? ??????", (text)-> {
            String key = setting.getMasterKey();
            if (text.equals(key)) {
                HUtils.showInputPasswdDialog(context, "????????? ????????? ??? ??????", (text2)-> {
                    setting.setMasterKey(text2);
                    showMessage("????????? ?????? ?????????????????????.");
                });
            } else {
                showMessage("?????? ???????????? ????????????");
            }
        });
    }

    @Override
    public void onBackPressed() {
        saveSetting();
        closeFragment();
    }

    public void saveSetting() {
        setting.save(context);
        showMessage("????????? ?????????????????????");
    }

    public void closeFragment() {
        MainActivity mainAct = ((MainActivity) getActivity());
        mainAct.changeMainFragment();
    }

    private void showMessage(String text) {
        HUtils.showMessage(context, text);
    }
}