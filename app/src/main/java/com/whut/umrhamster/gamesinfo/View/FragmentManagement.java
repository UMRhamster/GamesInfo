package com.whut.umrhamster.gamesinfo.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.whut.umrhamster.gamesinfo.R;

/**
 * Created by 12421 on 2018/4/12.
 */

public class FragmentManagement extends Fragment {
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_management,container,false);
        initView(view);
        return view;
    }
    protected void initView(View view){
        button = view.findViewById(R.id.management_fm_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
