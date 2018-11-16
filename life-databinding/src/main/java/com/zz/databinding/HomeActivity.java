package com.zz.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zz.databinding.databinding.ActivityHomeBinding;
import com.zz.databinding.fragment.DiscoverFragment;
import com.zz.databinding.fragment.HomeFragment;
import com.zz.databinding.fragment.MoreFragment;

/**
 * Created by tao.zeng on 2018/11/16.
 */

public class HomeActivity extends AppCompatActivity {

    private final static String[] TITLES = {"index", "discover", "self"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        ViewPager vpContainer = mBinding.vpContainer;
        TabLayout tabContainer = mBinding.tabContainer;
        vpContainer.setAdapter(new MyAdapter());
        tabContainer.setupWithViewPager(vpContainer);
    }

    private class MyAdapter extends FragmentPagerAdapter {

        MyAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new DiscoverFragment();
                    break;
                case 2:
                    fragment = new MoreFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }
}
