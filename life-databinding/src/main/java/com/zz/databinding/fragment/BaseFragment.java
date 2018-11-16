package com.zz.databinding.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by tao.zeng on 2018/11/16.
 */

public class BaseFragment extends Fragment {

    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
