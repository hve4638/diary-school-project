package com.example.mpproject;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DebugDBFragment extends Fragment {
    View vMain;
    Context context;

    public DebugDBFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vMain = inflater.inflate(R.layout.fragment_debug_d_b, container, false);
        context = container.getContext();
        TextView tView = (TextView)vMain.findViewById(R.id.tDebugDB);
        MemoDAO memoDAO = MemoDAO.getInstance(context);

        HGlobalSetting setting = HGlobalSetting.getInstance();
        String str = "";

        str += "MasterKey : " + setting.getMasterKey() + "\n";
        str += "GlobalLock : " + setting.getGlobalLock() + "\n";
        str += "\n\n\n\n";

        str += memoDAO.dbgGetAllMemo();
        tView.setText(str);

        return vMain;
    }
}











