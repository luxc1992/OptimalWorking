package com.yst.onecity.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.yst.basic.framework.base.BaseFragment;
import com.yst.onecity.R;

import butterknife.BindView;

/**
 * 地址弹框的fragment
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/22
 */

public class AddressFragment extends BaseFragment {
    @BindView(R.id.tv_frag)
    TextView tvFrag;

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_list;
    }

    public static AddressFragment newInstance() {
        AddressFragment fragment = new AddressFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("type", position);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void init() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            int type = bundle.getInt("type");
            switch (type) {
                case 0:
                    tvFrag.setText("frag000");
                    break;
                case 1:
                    tvFrag.setText("frag111");
                    break;
                case 2:
                    tvFrag.setText("frag222");
                    break;
                default:
                    break;

            }
        }
    }
}
