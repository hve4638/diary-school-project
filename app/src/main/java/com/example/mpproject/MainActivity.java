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
    ActionBar.Tab tMain, tWrite, tSetting;
    long lastBackTime = 0;
    int cFragmentsIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();

        initTab(bar);

        showTabs(true);
    }

    void getMainFragment(String tabName) {
        Fragment inst = getFragment(tabName);
        Bundle data = new Bundle();
        data.putString("tabName", tabName);
        inst.setArguments(data);
    }

    void initTab(ActionBar bar) {
        tMain = appendTab(bar, "메인", new MainFragment());
        appendTab(bar, "날짜순", new MemoDateOrderFragment());
        appendTab(bar, "최근 편집", new MemoRecentOrderFragment());
        appendTab(bar, "디버그", new DebugDBFragment());
    }

    ActionBar.Tab appendTab(ActionBar bar, String text, Fragment fragment) {
        ActionBar.Tab tab = bar.newTab().setText(text).setTabListener(this);
        bar.addTab(tab);

        Bundle data = new Bundle();
        data.putString("tabName", text);
        fragment.setArguments(data);
        mFragmentGlobal[cFragmentsIndex++] = fragment;

        return tab;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        int index = tab.getPosition();
        Fragment inst = mFragmentGlobal[index];

        changeFragmentWithoutChangeNavigation(inst);
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
            long cTime = System.currentTimeMillis();
            if (cTime - lastBackTime < 800) {
                super.onBackPressed();
            } else {
                HUtils.showMessage(getApplicationContext(), "'뒤로' 버튼을 한번 더 누르면 종료됩니다");
                lastBackTime = cTime;
            }
        }
    }

    public void changeMainFragment() {
        changeMainFragment(0);
    }

    public void changeMainFragment(int index) {
        //changeFragment(mFragmentGlobal[index]);
        showTabs(true);
    }

    public void changeWriteFragment() {
        Fragment writeFragment = new WriteFragment();
        HGlobal hGlobal = HGlobal.getInstance();
        hGlobal.setMemoMode(MemoMode.NEW);

        changeFragment(writeFragment);
    }

    public void showTabs(boolean show) {
        ActionBar bar = getSupportActionBar();

        if (show) {
            bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        } else {
            bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        }
    }

    public void changeFragment(Fragment fragment) {
        changeFragmentWithoutChangeNavigation(fragment);
        showTabs(false);
    }

    public void changeFragmentWithoutChangeNavigation(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(android.R.id.content, fragment).commit();
        currentFragment = fragment;
    }

    public void showMessage(String text) {
        HUtils.showMessage(getApplicationContext(), text);
    }
}