package com.zz.databinding.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zz.databinding.databinding.FragmentDiscoverBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends BaseFragment {


    private FragmentDiscoverBinding mBinding;

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (mBinding == null) {
            mBinding = FragmentDiscoverBinding.inflate(inflater);
            initView();
        }
        return mBinding.getRoot();
    }

    private void initView() {
        ListView lvContainer = mBinding.lvContainer;
        String[] data = {"Java", "DataBase", "Swift", "Redis", "Object-c", "Android"};
        lvContainer.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, data));
    }

}
