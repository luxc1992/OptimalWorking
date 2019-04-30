package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yst.basic.framework.base.BaseFragment;
import com.yst.onecity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/22
 */

public class ProvinceFragment extends BaseFragment {
    @BindView(R.id.tv_frag)
    TextView tvFrag;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_list;
    }
    public static ProvinceFragment newInstance() {
        ProvinceFragment fragment = new ProvinceFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("type", position);
//        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void init() {
        tvFrag.setText("---------------0222");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
