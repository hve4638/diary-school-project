package com.example.mpproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MemoListFragment extends Fragment {
    protected View vMain;
    protected Context context;
    protected MemoDAO memoDAO;
    protected HLayout hLayout;
    LinearLayout scrollList;
    FloatingActionButton btnAddMemo;

    public MemoListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_memo_list, container, false);
        context = container.getContext();
        init();

        return vMain;
    }

    void init() {
        memoDAO = MemoDAO.getInstance(context);
        hLayout = new HLayout(context);

        initView();
    }

    void initView() {
        scrollList = (LinearLayout) vMain.findViewById(R.id.scrollList);
        btnAddMemo = (FloatingActionButton) vMain.findViewById(R.id.btnAddMemo);

        List<Memo> list = getMemoList();
        for(Memo memo : list) {
            final int id = memo.id;
            View layout = hLayout.diaryLayout(memo, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMemo(id);
                }
            });
            scrollList.addView(layout);

            View vLine = hLayout.rowLine();
            scrollList.addView(vLine);
        }
        btnAddMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewMemo();
            }
        });
    }

    List<Memo> getMemoList() {
        return memoDAO.getAllMemo();
    }

    void openNewMemo() {
        MainActivity mainAct = ((MainActivity) getActivity());
        mainAct.changeFragment(new EditMemoFragment());
    }

    void openMemo(int id) {
        Memo memo = memoDAO.getMemoById(id);
        if (memo.lockLevel == LockLevel.NOTHING) {
            changeFragment(new ShowMemoFragment(memo));
        } else {
            changeFragmentIfPasswdCorrect(memo);
        }
    }

    void changeFragmentIfPasswdCorrect(Memo memo) {
        String passwd;
        if (memo.lockLevel == LockLevel.MASTER_KEY) {
            HGlobalSetting setting = HGlobalSetting.getInstance();
            passwd = setting.getMasterKey();
        } else {
            passwd = memo.passwd;
        }

        HUtils.showInputPasswdDialog(context, (text) -> {
            if (text.equals(passwd)) {
                changeFragment(new ShowMemoFragment(memo));
            } else {
                showMessage("비밀번호가 틀렸습니다");
                changeFragmentIfPasswdCorrect(memo);
            }
        });
    }

    void changeFragment(Fragment fragment) {
        MainActivity mainAct = ((MainActivity) getActivity());
        mainAct.changeFragment(fragment);
    }

    void showMessage(String text) {
        HUtils.showMessage(context, text);
    }
}