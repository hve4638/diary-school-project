package com.example.mpproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {
    Fragment mFragmentGlobal[] = new Fragment[3];
    Fragment currentFragment = null;
    FragmentTransaction fragmentTransaction;
    ActionBar.Tab tMain, tWrite, tSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        initTab(bar);


    }

    void initTab(ActionBar bar) {
        tMain = appendTab(bar, "main");
        tWrite = appendTab(bar, "write");
        tSetting = appendTab(bar, "option");
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

        ft.replace(android.R.id.content, inst);
        currentFragment = inst;
    }

    Fragment getFragment(String tabName) {
        switch (tabName) {
            case "main":
                return new MainFragment();
            case "write":
                return new WriteFragment();
            case "option":
            default:
                return new BlankFragment();
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public static class MyFragment extends androidx.fragment.app.Fragment {
        String tabName;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle data = getArguments();
            tabName = data.getString("tabName");
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            LinearLayout baseLayout = new LinearLayout(super.getActivity());
            LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            baseLayout.setOrientation(LinearLayout.VERTICAL);
            baseLayout.setLayoutParams(parm);


            baseLayout.setBackgroundColor(Color.RED);

            return baseLayout;
        }
    }

    @Override
    public void onBackPressed() {
        if (currentFragment instanceof IBackPress) {
            IBackPress iBackPress = (IBackPress)currentFragment;
            iBackPress.onBackPressed();
        }
        else {
            HUtils.showMessage(getApplicationContext(), "no cur");
        }
    }
}