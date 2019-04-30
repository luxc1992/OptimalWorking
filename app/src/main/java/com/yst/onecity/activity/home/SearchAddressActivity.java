package com.yst.onecity.activity.home;

import android.content.Intent;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.view.ContainsEmojiEditText;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/5/15
 */
public class SearchAddressActivity extends BaseActivity implements Inputtips.InputtipsListener, AdapterView.OnItemClickListener {

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_input_address)
    ContainsEmojiEditText etInputAddress;
    @BindView(R.id.listView)
    ListView listView;
    private String nowCity;
    private List<Tip> mData = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_serach_address;
    }

    @Override
    public void initData() {
        initTitleBar("搜索地址");
        if (getIntent().getExtras() != null) {
            nowCity = getIntent().getExtras().getString("city");
            tvCity.setText(nowCity);
        }
    }


    @Override
    public void setListener() {
        etInputAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                autoSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        listView.setOnItemClickListener(this);
    }

    private void autoSearch(String address) {
        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        InputtipsQuery inputQuery = new InputtipsQuery(address, nowCity);
        //限制在当前城市
        inputQuery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(SearchAddressActivity.this, inputQuery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    private void initCtrllor() {
        AbstractCommonAdapter<Tip> adapter = new AbstractCommonAdapter<Tip>(context, mData, R.layout.item_serach_address) {
            @Override
            public void convert(CommonViewHolder holder, int position, Tip item) {
                holder.setText(R.id.tv_address_name, item.getName());
                holder.setText(R.id.tv_detail_address, item.getAddress());
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if (i == Constant.NO1000) {
            if (list != null) {
                mData = list;
                initCtrllor();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.putExtra("searchAddress", mData.get(i).getName());
        intent.putExtra("point", (Parcelable) mData.get(i).getPoint());
        setResult(RESULT_OK, intent);
        finish();
    }
}
