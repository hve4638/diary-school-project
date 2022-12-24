package com.example.mpproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Window;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {
    Fragment mFragmentGlobal[] = new Fragment[5];
    Fragment mainFragment;
    Fragment currentFragment = null;
    int lastTabIndex = 0;
    FragmentTransaction fragmentTransaction;
    ActionBar.Tab tMain, tWrite, tSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();

        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        initFragment();
        initTab(bar);
    }

    void initFragment() {
    }

    void initTab(ActionBar bar) {
        tMain = appendTab(bar, "main");
        tSetting = appendTab(bar, "option");
        appendTab(bar, "debugdb");
    }

    ActionBar.Tab appendTab(ActionBar bar, String text) {
        ActionBar.Tab tab = bar.newTab().setText(text).setTabListener(this);
        bar.addTab(tab);
        return tab;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        fragmentTransaction = ft;
        Fragment inst = null;
        String tabName = tab.getText().toString();
        int index = tab.getPosition();

        if (mFragmentGlobal[index] == null) {
            inst = getFragment(tabName);
            Bundle data = new Bundle();

            data.putString("tabName", tabName);

            inst.setArguments(data);
            mFragmentGlobal[index] = inst;

        } else {
            inst = mFragmentGlobal[index];
        }

        changeFragment(inst);
    }

    Fragment getFragment(String tabName) {
        switch (tabName) {
            case "main":
                return new MainFragment();
            case "debugdb":
                return new DebugDBFragment();
            case "write":
            case "option":

            default:
                return new BlankFragment();
        }
    }
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) { }
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) { }

    @Override
    public void onBackPressed() {
        if (currentFragment instanceof IFrag) {
            IFrag iBackPress = (IFrag)currentFragment;
            iBackPress.onBackPressed();
        }
        else {
            HUtils.showMessage(getApplicationContext(), "no cur");
        }
    }

    public void changeMainFragment() {
        changeFragment(mFragmentGlobal[0]);
    }

    public void changeWriteFragment() {
        Fragment writeFragment = new WriteFragment();
        HGlobal hGlobal = HGlobal.getInstance();
        hGlobal.setMemoMode(MemoMode.NEW);

        changeFragment(writeFragment);
    }

    public void changeWriteFragment(int id) {
        HGlobal hGlobal = HGlobal.getInstance();
        MemoDAO memoDAO = MemoDAO.getInstance(getApplicationContext());


        Memo memo = memoDAO.getMemoById(id);
        hGlobal.setMemoMode(MemoMode.SHOW, memo);
        Fragment writeFragment = new WriteFragment(MemoMode.SHOW, memo);

        changeFragment(writeFragment);
    }

    public void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(android.R.id.content, fragment).commit();
        currentFragment = fragment;
        //showMessage("change: " + currentFragment);
    }

    public void showMessage(String text) {
        HUtils.showMessage(getApplicationContext(), text);
    }
}