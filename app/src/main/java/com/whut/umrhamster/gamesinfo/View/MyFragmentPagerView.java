package com.whut.umrhamster.gamesinfo.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 12421 on 2018/4/5.
 */

public class MyFragmentPagerView extends FragmentPagerAdapter {
    private String[] titles = new String[]{"新闻","测评"};
    private List<Fragment> fragmentList;
    public MyFragmentPagerView(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
