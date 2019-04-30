package com.yst.onecity.activity.issue;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.adapter.issue.AddGoodsAdapter;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.ProductPlanBean;
import com.yst.onecity.bean.issue.AddCommodityBean;
import com.yst.onecity.bean.issue.CommodityBean;
import com.yst.onecity.bean.issue.IssueCommodityNewsBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yst.onecity.Constant.FINISHACTIVITY;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;


/**
 * 添加商品界面
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/02/06
 */
public class AddCommodityActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.issue_add_goods_gridview)
    GridView goodsGridview;
    @BindView(R.id.tev_select_supplier_goods)
    TextView selectSupplierGoods;
    @BindView(R.id.tev_add_goods)
    TextView tevAddGoods;
    @BindView(R.id.tev_select_goods)
    TextView tevSelectGoods;
    @BindView(R.id.add_product_plan_btn_nextStep)
    TextView nextStep;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private AddGoodsAdapter addGoodsAdapter;
    private static final int REQUEST_CODE = 11;
    private final String strProductList = "productList";
    private int page = 0;
    /**
     * 选择的商品集合
     */
    public static List<CommodityBean.ContentBean> productList = new ArrayList<>();
    private Intent intent;
    /**
     * 商品
     */
    private List<ProductPlanBean.ContentBean.ProductSBean> goods;
    /**
     * 编辑产品计划，（包含，产品计划页，商品页，日记，所有信息）
     */
    private ProductPlanBean.ContentBean editContent;
    /**
     * 判断从那个页面来，标识 0：编辑直接跳转商品 1：编辑从添加产品计划跳转商品 2：新增
     */
    private int from = 2;
    /**
     * 产品计划id
     */
    private String productId = "";
    /**
     * 发布产品计划
     * modelType     类型：0视频 1一图 2二图 3三图
     * title         标题
     * describes     描述
     * address       发布地址
     * longitude     经度
     * latitude      纬度
     * productIds    关联商品id集合(逗号拼接)
     * covers        封面图地址集合(逗号拼接) modelType不等于0必填
     * projectPlanId 关联项目计划
     * video         视频地址 modelType等于0必填
     * cover         视频封面地址 modelType等于0必填
     */
    private String modelType = "";
    private String title = "";
    private String describes = "";
    private String address = "";
    private String longitude = "";
    private String latitude = "";
    private String productIds = "";
    private String covers = "";
    private String projectPlanId = "";
    private String video = "";
    private String cover = "";
    /**
     * 0视频  1图片（产品计划页传来）
     */
    private String type;
    private int plantId;
    private String projectId;
    private boolean isEdit;
    private ProductPlanBean.ContentBean.ProductPlanDailyVoBean productPlanDailyVo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_commodity;
    }

    @Override
    public void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        EventBus.getDefault().register(this);
        initTitleBar("添加商品");
        //获取前一页面数据
        intent = getIntent();
        Bundle extras = intent.getExtras();
        from = extras.getInt("from");
        //产品计划id
        projectId = intent.getStringExtra("projectId");
        deleteRepetitionData();
        flushGoodsList();
        getInfo();
    }

    @Override
    public void setListener() {
        super.setListener();
        tevAddGoods.setOnClickListener(this);
        tevSelectGoods.setOnClickListener(this);
        selectSupplierGoods.setOnClickListener(this);
        nextStep.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //添加新商品
            case R.id.tev_add_goods:
                Intent intent = new Intent();
                intent.putExtra("address", address);
                intent.setClass(this, AddNewCommodityActivity.class);
                startActivityForResult(intent, Constant.NO50);
                break;
            //选择
            case R.id.tev_select_goods:
                JumpIntent.jump(this, SelectCommodityActivity.class, 11);
                break;
            //供应商
            case R.id.tev_select_supplier_goods:
                JumpIntent.jump(this, SelectSupplierCommodityActivity.class, 11);
                break;
            //发布
            case R.id.add_product_plan_btn_nextStep:
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < productList.size(); i++) {
                    if (i == 0) {
                        stringBuffer.append(productList.get(i).getProductId());
                    } else {
                        stringBuffer.append(",");
                        stringBuffer.append(productList.get(i).getProductId());
                    }
                }
                //跳转前需要将所有数据传递到下一步
                productIds = stringBuffer.toString();
                Bundle issueBundle = new Bundle();
                issueBundle.putString("modelType", modelType);
                issueBundle.putString("title", title);
                issueBundle.putString("describes", describes);
                issueBundle.putString("address", address);
                issueBundle.putString("longitude", longitude);
                issueBundle.putString("latitude", latitude);
                issueBundle.putString("productIds", productIds);
                issueBundle.putString("covers", covers);
                issueBundle.putString("video", video);
                issueBundle.putString("cover", cover);
                issueBundle.putString("productId", productId);
                issueBundle.putInt("from", from);
                issueBundle.putString("projectId", projectId);
                issueBundle.putBoolean("isEdit", isEdit);
                if (from != NO0) {
                    issueBundle.putSerializable("productPlanDailyVo", productPlanDailyVo);
                }
                //跳转发布日记页面
                JumpIntent.jump(AddCommodityActivity.this, IssueDiaryActivity.class, issueBundle);
                break;
            default:
                break;
        }
    }

    /**
     * 刷新适配器
     */
    private void flushGoodsList() {
        if (null == goodsGridview) {
            return;
        }
        if (addGoodsAdapter == null) {
            addGoodsAdapter = new AddGoodsAdapter(productList, context, 2);
            goodsGridview.setAdapter(addGoodsAdapter);
        } else {
            addGoodsAdapter.setGoodsManagerList(productList, 2);
        }
        addGoodsAdapter.setIScrollPositon(new AddGoodsAdapter.IGetScrollPosition() {
            //删除
            @Override
            public void click(int i) {
                productList.remove(i);
                addGoodsAdapter.notifyDataSetChanged();
            }

            //编辑
            @Override
            public void compile(int i) {
                String id = productList.get(i).getProductId();
                getHunterProductContent(id);
            }
        });
    }

    /**
     * 商品回显
     */
    private void getHunterProductContent(final String id) {
        RequestApi.getHunterProductContent(id, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<IssueCommodityNewsBean>() {
            @Override
            public void onSuccess(IssueCommodityNewsBean hunterProductContentBean) {
                if (1 == hunterProductContentBean.getCode()) {
                    IssueCommodityNewsBean.ContentBean content = hunterProductContentBean.getContent();
                    Bundle bundle = new Bundle();
                    //商品内容
                    bundle.putSerializable("content", content);
                    //商品id
                    bundle.putSerializable("contentId", id);
                    //商品产地
                    bundle.putSerializable("address", address);
                    JumpIntent.jump(AddCommodityActivity.this, AddNewCommodityActivity.class, bundle);
                }
                ToastUtils.show(hunterProductContentBean.getMsg());
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg.toString());
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        //接受选择的商品
        if (REQUEST_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                if (data.getExtras().getSerializable(strProductList) != null) {
                    productList.addAll((List<CommodityBean.ContentBean>) data.getExtras().getSerializable(strProductList));
                    for (int i = 0; i < productList.size(); i++) {
                        CommodityBean.ContentBean contentBean = productList.get(i);
                        String productId1 = contentBean.getProductId();
                        for (int j = 0; j < productList.size(); j++) {
                            String id = productList.get(j).getProductId();
                            if (productId1.equals(id)) {
                                if (j != i) {
                                    productList.remove(i);
                                    ToastUtils.show("不可以添加重复的商品");
                                }
                            }
                        }
                    }
                    flushGoodsList();
                }
            }
        } else if (Constant.NO50 == requestCode) {
            AddCommodityBean.ContentBean goods = (AddCommodityBean.ContentBean) data.getExtras().getSerializable("goods");
            CommodityBean.ContentBean bean = new CommodityBean.ContentBean();
            //生产商品的实体类 刷新adapter
            bean.setAttachment(goods.getAttachment());
            bean.setName(goods.getName());
            bean.setPrice(goods.getPrice() + "");
            bean.setTitle(goods.getTitle());
            bean.setMaxPrice(goods.getMaxPrice());
            bean.setMinPrice(goods.getMinPrice());
            int productId = goods.getProductId();
            for (int i = 0; i < productList.size(); i++) {
                CommodityBean.ContentBean contentBean = productList.get(i);
                String productId1 = contentBean.getProductId();
                int id = Integer.parseInt(productId1);
                if (id == productId) {
                    productList.remove(i);
                    ToastUtils.show("不可以添加重复的商品");
                }
            }
            bean.setProductId(productId + "");
            String address = goods.getAddress();
            if (!TextUtils.isEmpty(address)) {
                bean.setAddress(address);
            }
            productList.add(bean);
            addGoodsAdapter.notifyDataSetChanged();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < productList.size(); i++) {
                if (i == 0) {
                    stringBuffer.append(productList.get(i).getProductId());
                } else {
                    stringBuffer.append(",");
                    stringBuffer.append(productList.get(i).getProductId());
                }
            }
            productIds = stringBuffer.toString();
        }
    }

    /**
     * 获取信息
     */
    private void getInfo() {
        //编辑状态：从添加产品页开始
        if (from == NO1) {
            editContent = (ProductPlanBean.ContentBean) intent.getSerializableExtra("editContent");
            plantId = editContent.getId();
            title = intent.getStringExtra("title");
            describes = intent.getStringExtra("describe");
            int status = editContent.getStatus();
            if (status == NO2) {
                isEdit = true;
            } else {
                isEdit = false;
            }
            productPlanDailyVo = editContent.getProductPlanDailyVo();
            //0视频  1图片  类型
            type = intent.getStringExtra("type");
            //视频路径
            video = intent.getStringExtra("videoPath");
            address = intent.getStringExtra("address");
            //经纬度
            longitude = Double.toString(intent.getDoubleExtra("jd", 0.00));
            latitude = Double.toString(intent.getDoubleExtra("wd", 0.00));
            //图片集合
            List<String> img = intent.getStringArrayListExtra("img");
            //类型 0视频 1图片
            if (editContent.getCurrencyAttachmentVos().size() > 0 && editContent.getCurrencyAttachmentVos().get(0).getVideoCoverAddress() != null) {
                cover = img.get(0);
                modelType = 0 + "";
            } else /*if (Integer.toString(NO1).equals(type))*/ {
                StringBuffer sbImg = new StringBuffer();
                for (int i = 0; i < img.size(); i++) {
                    if (i == 0) {
                        sbImg.append(img.get(i));
                    } else {
                        sbImg.append(",");
                        sbImg.append(img.get(i));
                    }
                }
                covers = sbImg.toString();
                modelType = Integer.toString(img.size());
            }
            //编辑状态：直接到商品页  日记不需要回显
        } else if (from == NO0) {
            editContent = (ProductPlanBean.ContentBean) intent.getSerializableExtra("productPlanBean");
            productPlanDailyVo = editContent.getProductPlanDailyVo();
            title = editContent.getTitle();
            describes = editContent.getDescription();
            if (editContent.getProductPlanDailyVo() != null) {
                modelType = editContent.getProductPlanDailyVo().getModelType() + "";
            }
            video = "";
            List<ProductPlanBean.ContentBean.CurrencyAttachmentVos> currencyAttachmentVos1 = editContent.getCurrencyAttachmentVos();
            if (null != currencyAttachmentVos1) {
                for (int i = 0; i < currencyAttachmentVos1.size(); i++) {
                    video = currencyAttachmentVos1.get(0).getVideoCoverAddress();
                }
            }
            address = editContent.getAddress();
            longitude = "";
            latitude = "";
            projectPlanId = editContent.getId() + "";
            covers = "";
            List<ProductPlanBean.ContentBean.CurrencyAttachmentVos> currencyAttachmentVos = editContent.getCurrencyAttachmentVos();
            if (null != currencyAttachmentVos) {
                StringBuffer sbImg = new StringBuffer();
                for (int i = 0; i < currencyAttachmentVos.size(); i++) {
                    String address = currencyAttachmentVos.get(i).getAddress();
                    if (i == 0) {
                        sbImg.append(address);
                    } else {
                        sbImg.append(",");
                        sbImg.append(address);
                    }
                }
                covers = sbImg.toString();
            }
            //新增状态
        } else if (from == NO2) {
            title = intent.getStringExtra("title");
            describes = intent.getStringExtra("describe");
            //0视频  1图片
            type = intent.getStringExtra("type");
            video = intent.getStringExtra("videoPath");
            address = intent.getStringExtra("address");
            longitude = Double.toString(intent.getDoubleExtra("jd", 0.00));
            latitude = Double.toString(intent.getDoubleExtra("wd", 0.00));
            List<String> img = intent.getStringArrayListExtra("img");
            //类型 0视频 1图片
            if (Integer.toString(NO0).equals(type)) {
                cover = img.get(0);
                modelType = type;
            } else if (Integer.toString(NO1).equals(type)) {
                StringBuffer sbImg = new StringBuffer();
                for (int i = 0; i < img.size(); i++) {
                    if (i == 0) {
                        sbImg.append(img.get(i));
                    } else {
                        sbImg.append(",");
                        sbImg.append(img.get(i));
                    }
                }
                covers = sbImg.toString();
                modelType = Integer.toString(img.size());
            }
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < productList.size(); i++) {
                if (i == 0) {
                    stringBuffer.append(productList.get(i).getProductId());
                } else {
                    stringBuffer.append(",");
                    stringBuffer.append(productList.get(i).getProductId());
                }
            }
            productIds = stringBuffer.toString();
        }
        if (from != NO2) {
            if (null == editContent) {
                return;
            }
            //产品计划id
            productId = Integer.toString(editContent.getId());
            getProductListByProductPlanId();
        } else {
            productList.clear();
        }
        for (int i = 0; i < productList.size(); i++) {
            CommodityBean.ContentBean contentBean = productList.get(i);
            String productId1 = contentBean.getProductId();
            for (int j = 0; j < productList.size(); j++) {
                String id = productList.get(j).getProductId();
                if (productId1.equals(id)) {
                    if (j != i) {
                        productList.remove(i);
                    }
                }
            }
        }
        flushGoodsList();
        addGoodsAdapter.notifyDataSetChanged();
    }


    /**
     * 根据产品计划id获取商品列表
     */
    private void getProductListByProductPlanId() {
        onLoad();
        RequestApi.getProductListByProductPlanId(productId, String.valueOf(page), "10", new AbstractNetWorkCallback<CommodityBean>() {
            @Override
            public void onSuccess(CommodityBean commodityBean) {
                if (commodityBean != null && commodityBean.getCode() == 1) {
                    if (page == 0) {
                        productList.clear();
                        productList = commodityBean.getContent();
                    } else {
                        productList.addAll(commodityBean.getContent());
                    }
                } else {
                    if (page == 0) {
                        productList = new ArrayList<>();
                    } else {
                        ToastUtils.show("已加载全部数据");
                    }
                }
                flushGoodsList();
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }
        });
    }


    @Subscribe
    public void onShareEventMain(EventBean event) {
        if (FINISHACTIVITY.equals(event.getMsg())) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 删除重复的商品
     */
    private void deleteRepetitionData() {
        for (int i = 0; i < productList.size(); i++) {
            CommodityBean.ContentBean contentBean = productList.get(i);
            String productId1 = contentBean.getProductId();
            for (int j = 0; j < productList.size(); j++) {
                String id = productList.get(j).getProductId();
                if (productId1.equals(id)) {
                    if (j != i) {
                        productList.remove(i);
                    }
                }
            }
        }
    }


    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        //加载更多
        page++;
        getProductListByProductPlanId();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        ///上拉刷新
        page = 1;
        getProductListByProductPlanId();
    }

    /**
     * 停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }
}
