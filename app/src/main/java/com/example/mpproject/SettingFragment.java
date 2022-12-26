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
                    HUtils.showInputPasswdDialog(context, "마스터 키 입력", (text) -> {
                        HGlobalSetting setting = HGlobalSetting.getInstance();
                        String key = setting.getMasterKey();
                        if (key.equals(text)) {
                            MemoDAO.getInstance(context).deleteAllMemo();
                            showMessage("모든 메모를 삭제했습니다");
                        } else {
                            showMessage("비밀번호가 일치하지 않습니다");
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
        HUtils.showInputPasswdDialog(context, "기존 마스터 키 입력", (text)-> {
            String key = setting.getMasterKey();
            if (text.equals(key)) {
                HUtils.showInputPasswdDialog(context, "새로운 마스터 키 입력", (text2)-> {
                    setting.setMasterKey(text2);
                    showMessage("마스터 키가 변경되었습니다.");
                });
            } else {
                showMessage("키가 일치하지 않습니다");
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
        showMessage("설정이 저장되었습니다");
    }

    public void closeFragment() {
        MainActivity mainAct = ((MainActivity) getActivity());
        mainAct.changeMainFragment();
    }

    private void showMessage(String text) {
        HUtils.showMessage(context, text);
    }
}