package com.whut.umrhamster.gamesinfo.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.whut.umrhamster.gamesinfo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12421 on 2018/4/5.
 */

public class FragmentMain extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragmentPagerView myFragmentPagerView;

    //toolbar
    private Toolbar toolbar;
    private ImageView imageViewMenu;
    private ImageView imageViewSearch;
    List<Fragment> fragmentList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        initView(view);
        return view;
    }
    void initView(View view){
        tabLayout = view.findViewById(R.id.main_fg_tl);
        viewPager = view.findViewById(R.id.main_fg_vp);
        fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentGameNews());
        fragmentList.add(new FragmentGameEvaluation());
        myFragmentPagerView = new MyFragmentPagerView(getChildFragmentManager(),fragmentList);
        viewPager.setAdapter(myFragmentPagerView);
        tabLayout.setupWithViewPager(viewPager);
        toolbar = view.findViewById(R.id.main_fg_tb);
        imageViewMenu = view.findViewById(R.id.main_fg_tb_menu);
        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imageViewSearch = view.findViewById(R.id.main_fg_tb_search);
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
