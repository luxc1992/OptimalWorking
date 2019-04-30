package com.yst.onecity;

/**
 * H5地址常量类
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/3/1.
 */
public class H5Const {
    /**
     * 测试环境
     */
    private static final String URL_ROOT = "http://192.168.50.248:8081/";
    /**
     * 正式环境
     */
//    private static final String URL_ROOT = "http://static.pujiyicheng.com/";
    /**
     * 伪正式
     */
//    private static final String URL_ROOT = "http://113.209.232.110:10001/";
    /**
     * 首页
     */
    public static final String H5_HOME = URL_ROOT + "html/homePage/sy.html";
    /**
     * 收徒有奖
     */
    public static final String H5_SHOUTU = URL_ROOT + "html/singlecenter/shoutu.html";

    /**
     * 徒弟列表
     */
    public static final String H5_TUDI = URL_ROOT + "html/singlecenter/tudi_list.html";


    /**
     * 分享页面
     */
    public static final String H5_FENXIANG = URL_ROOT + "html/homePage/share_app.html ";

    /**
     * 资讯详情页
     */
    public static final String H5_INFODETAIL = URL_ROOT + "html/homePage/news_detail.html";

    /**
     * 产品计划详情页
     */
    public static final String H5_PRODUCTDETAIL = URL_ROOT + "html/lietou/e_changpinjihuaxiangqing.html";

    /**
     * 项目计划详情页
     */
    public static final String H5_PROJECTDETAIL = URL_ROOT + "html/singlecenter/xuanshang.html";
    /**
     * 猎头店铺
     */
    public static final String H5_HUNTER_SHOP_DETAIL = URL_ROOT + "html/lietou/e_dianpu.html";
    /**
     * 商品详情
     */
    public static final String GOODS_DETAIL = URL_ROOT + "html/shopDetail/shopDetail.html";
    /**
     * 待发货订单
     */
    public static final String ORDER_SEND = URL_ROOT + "html/order/my_order.html?status=2";

    /**
     * 订单详情-等待付款
     */
    public static final String GOODS_DDFK = URL_ROOT + "html/order/order_details_ddfk.html";
    /**
     * 订单详情-等待发货
     */
    public static final String GOODS_DDFH = URL_ROOT + "html/order/order_details_ddfh.html";
    /**
     * 订单详情-等待收货
     */
    public static final String GOODS_DDSH = URL_ROOT + "html/order/order_details_ddsh.html";
    /**
     * 订单详情-交易完成
     */
    public static final String GOODS_JYWC = URL_ROOT + "html/order/order_details_jywc.html";
    /**
     * 订单详情-交易关闭
     */
    public static final String GOODS_JYGB = URL_ROOT + "html/order/order_details_jygb.html";


    /**
     * 普济1.1.0版本
     */
    /**
     * 服务团队主页
     */
    public static final String SERVER_TEAM_PAGE = URL_ROOT + "html/service/serviceList.html";
    /**
     * 课题详情
     */
    public static final String PROJECT_DETAILS = URL_ROOT + "html/homektxq/homektfwlb.html";
    /**
     * 商品详情
     */
    public static final String GOODS_DETAIL_NEW = URL_ROOT + "html/shopDetail/shopDetail.html";
    /**
     * 服务项目详情
     */
    public static final String SERVE_TEM_DETAILS = URL_ROOT + "html/homektxq/home_fwxq.html";
    /**
     * 案例详情
     */
    public static final String CASE_DETAIL = URL_ROOT + "html/service/detailsCase.html";


    /**
     * 资讯详情页
     *
     * @version v1.1.0
     */
    public static final String H5_CONSULTING_DETAIL = URL_ROOT + "html/shopDetail/zixunxq.html";
    /**
     * 一卡通
     */
    public static final String H5_BANNER1 = URL_ROOT + "html/sylunbo/yi-card.html";
    /**
     * 邀请好友
     */
    public static final String H5_BANNER2 = URL_ROOT + "html/sylunbo/yaoqinghaoyou.html";
    /**
     * 尊享
     */
    public static final String H5_BANNER3 = URL_ROOT + "html/sylunbo/yonghujilixq.html";
    /**
     * 一卡自由行
     */
    public static final String H5_BANNER4 = URL_ROOT + "html/sylunbo/douyin.html";
    /**
     * 买家商品订单
     */
    public static final String BUYER_GOODS_LIST = URL_ROOT + "html/order/my_order.html";
    /**
     * 购物车
     */
    public static final String SHOP_CART = URL_ROOT + "html/shopDetail/cart.html";
    /**
     * 服务订单-再来一单 跳确认下单
     */
    public static final String SERVICE_PROJECT_CONFIRM_ORDER = URL_ROOT + "html/service/s_order.html";
    /**
     * 卡联盟订单立即支付 跳确认下单
     */
    public static final String PAYMENT = URL_ROOT + "html/service/topay.html";
}
