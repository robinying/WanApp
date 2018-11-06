package com.yubin.wanapp.activity.splash;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public class SplashFragmentAdapter extends FragmentPagerAdapter {
    private final int pageCount = 3;

    public SplashFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SplashFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
