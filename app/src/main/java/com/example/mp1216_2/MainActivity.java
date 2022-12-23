package com.example.mp1216_2;

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
    ActionBar.Tab tag_dog, tag_cat, tag_write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tag_dog = bar.newTab().setText("dog").setTabListener(this);
        tag_cat = bar.newTab().setText("cat").setTabListener(this);
        tag_write = bar.newTab().setText("write").setTabListener(this);
        bar.addTab(tag_dog);
        bar.addTab(tag_cat);
        bar.addTab(tag_write);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Fragment myFragInst = null;
        int index = tab.getPosition();

        if(mFragmentGlobal[index] != null) {
            myFragInst = mFragmentGlobal[tab.getPosition()];
        }
        else if (index == 2) {
            myFragInst = new WriteFragment();
            Bundle data = new Bundle();

            data.putString("tabName", tab.getText().toString());

            myFragInst.setArguments(data);
            mFragmentGlobal[tab.getPosition()] = myFragInst;
        }
        else {
            myFragInst = new MyFragment();
            Bundle data = new Bundle();

            data.putString("tabName", tab.getText().toString());

            myFragInst.setArguments(data);
            mFragmentGlobal[tab.getPosition()] = myFragInst;
        }

        ft.replace(android.R.id.content, myFragInst);
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


            if (tabName=="dog") baseLayout.setBackgroundColor(Color.RED);

            return baseLayout;
        }
    }
}