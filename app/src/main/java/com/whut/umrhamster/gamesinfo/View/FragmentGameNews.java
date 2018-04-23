package com.whut.umrhamster.gamesinfo.View;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.umrhamster.gamesinfo.R;

/**
 * Created by 12421 on 2018/4/5.
 */

public class FragmentGameNews extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gamenews,container,false);
        return view;
    }
}
