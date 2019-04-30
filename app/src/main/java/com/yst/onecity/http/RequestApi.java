package com.yst.onecity.http;

import android.text.TextUtils;

import com.yst.basic.framework.App;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.onecity.Const;
import com.yst.onecity.activity.yanzhengma.EditCodeBean;
import com.yst.onecity.bean.BandBankBean;
import com.yst.onecity.bean.BankBean;
import com.yst.onecity.bean.BankinfroBean;
import com.yst.onecity.bean.CaseBean;
import com.yst.onecity.bean.CashDetailsBean;
import com.yst.onecity.bean.ChangeSpeciallyPersonBean;
import com.yst.onecity.bean.CheckVersionBean;
import com.yst.onecity.bean.CityAdressBean;
import com.yst.onecity.bean.GoodsCommentBean;
import com.yst.onecity.bean.HuntBean;
import com.yst.onecity.bean.HunterBean;
import com.yst.onecity.bean.HunterNumBean;
import com.yst.onecity.bean.InteractionBean;
import com.yst.onecity.bean.LogisticsCompanyBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.MyCardBagDetailBean;
import com.yst.onecity.bean.MyCollectShareBean;
import com.yst.onecity.bean.NoticeBean;
import com.yst.onecity.bean.PersonBean;
import com.yst.onecity.bean.PersondetailBean;
import com.yst.onecity.bean.ProductBean;
import com.yst.onecity.bean.ProductPlanBean;
import com.yst.onecity.bean.ProjectPlanBean;
import com.yst.onecity.bean.ProvinceAdressBean;
import com.yst.onecity.bean.PublishClassifyBean;
import com.yst.onecity.bean.ReapBean;
import com.yst.onecity.bean.ReapRensonBean;
import com.yst.onecity.bean.RecommendedBean;
import com.yst.onecity.bean.SanFangStateBean;
import com.yst.onecity.bean.ServeProjectBean;
import com.yst.onecity.bean.ServerTeamBannerBean;
import com.yst.onecity.bean.ServerTeamClassifyDropListBean;
import com.yst.onecity.bean.ServerTeamListBean;
import com.yst.onecity.bean.ServiceClassifyBean;
import com.yst.onecity.bean.ServiceCommentBean;
import com.yst.onecity.bean.ServiceDataBean;
import com.yst.onecity.bean.SignInBean;
import com.yst.onecity.bean.SpeciallyPersonPhoneBean;
import com.yst.onecity.bean.TeamInfoBean;
import com.yst.onecity.bean.accountsafe.AccountSafeBean;
import com.yst.onecity.bean.accountsafe.RealNameAuthenticationInfoBean;
import com.yst.onecity.bean.agent.AgentListBean;
import com.yst.onecity.bean.agent.ByIdBean;
import com.yst.onecity.bean.agent.ClassifyBean;
import com.yst.onecity.bean.agent.PublishCourseBean;
import com.yst.onecity.bean.goodsmanage.GoodsBean;
import com.yst.onecity.bean.home.BannerContentBean;
import com.yst.onecity.bean.home.CouponBean;
import com.yst.onecity.bean.home.HomeProjectContentBean;
import com.yst.onecity.bean.home.InformationBean;
import com.yst.onecity.bean.home.ProjectClassifyContentBean;
import com.yst.onecity.bean.home.PublishInfoAddProductPlanBean;
import com.yst.onecity.bean.home.SearchEntity;
import com.yst.onecity.bean.issue.CommodityStairClassifyBean;
import com.yst.onecity.bean.issue.CommodityTwoClassifyBean;
import com.yst.onecity.bean.issue.DiaryBean;
import com.yst.onecity.bean.issue.EditProductBean;
import com.yst.onecity.bean.issue.PublishProductBean;
import com.yst.onecity.bean.issue.TemplateBean;
import com.yst.onecity.bean.issue.UpdatePlanBean;
import com.yst.onecity.bean.mine.GroupInfoBean;
import com.yst.onecity.bean.mine.MyCardBagListBean;
import com.yst.onecity.bean.mine.MyCollectGoodBean;
import com.yst.onecity.bean.mine.MyCollectInformationBean;
import com.yst.onecity.bean.mine.MyCollectProductPlan;
import com.yst.onecity.bean.mine.MyInComeListBean;
import com.yst.onecity.bean.mine.MyZxingBean;
import com.yst.onecity.bean.mine.PersonalCenterBean;
import com.yst.onecity.bean.order.AfterSalesDetailContentBean;
import com.yst.onecity.bean.order.OrderDetailsContentBean;
import com.yst.onecity.bean.servermanage.ServerManageBean;

import java.util.HashMap;
import java.util.Map;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NULL;

/**
 * 接口访问
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/1
 */
public class RequestApi {

    /**
     * 登录(账号密码登录)
     * version 1.0.1
     *
     * @param phone     手机号
     * @param password  密码
     * @param loginType 登录方式 （1账号和密码登录 2短信验证码登录）
     * @param callback  回调
     */
    public static void passWordLogin(String phone, String password, String loginType, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("password", password);
        params.put("loginType", loginType);
        OkHttpUtils.getInstace().post(Const.LOGIN, params, callback);
    }

    /**
     * 登录(账号密码登录)
     * version 1.0.1
     *
     * @param phone     手机号
     * @param loginType 登录方式 （1账号和密码登录 2短信验证码登录）
     * @param smsCode   验证码
     * @param callback  回调
     */
    public static void verifyLogin(String phone, String loginType, String smsCode, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("loginType", loginType);
        params.put("smsCode", smsCode);
        OkHttpUtils.getInstace().post(Const.LOGIN, params, callback);
    }

    /**
     * 获取验证码(登录||注册)
     * version 1.0.1
     *
     * @param phone    手机号
     * @param type     标识类型
     * @param callback 回调
     */
    public static void getVerifyCode(String phone, String type, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("type", type);
        OkHttpUtils.getInstace().post(Const.REGISTER_OR_LOGIN_SEND_VERIFY_CODE, params, callback);
    }

    /**
     * 获取猎头订单列表
     * version 1.0.1
     *
     * @param phone    电话号码
     * @param uuid     uuid
     * @param status   订单状态 0待付款 2待发货（已付款） 3待收货 4已评价 6已撤销 7已收货（待评价）8 全部
     * @param userType 0 会员 1 猎头
     * @param page     0   页数
     * @param callback 回调
     */
    public static void getHunterOrderList(String phone, String uuid, int status, int userType, int page, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("status", String.valueOf(status));
        params.put("userType", String.valueOf(userType));
        params.put("page", String.valueOf(page));
        params.put("rows", String.valueOf(10));
        OkHttpUtils.getInstace().post(Const.GET_HUNTER_ORDER_LIST, params, callback);
    }

    /**
     * 猎头个人主页信息
     * version 1.0.1
     *
     * @param phone 手机号
     * @param uuid
     */

    public static void getHunterInfro(String phone, String uuid, AbstractNetWorkCallback<HunterBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.SET_LIETOUINFRO, params, callback);
    }

    /**
     * 设置交易密码获取验证码接口
     * version 1.0.1
     *
     * @param phone    手机号
     * @param callback 回调
     */
    public static void getVerifyCodeForSetTradePwd(String phone, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.GET_VERIFY_CODE_FOR_SET_TRADE_PWD, params, callback);
    }

    /**
     * 获取实名认证验证码接口
     * version 1.0.1
     *
     * @param phone    手机号
     * @param callback 回调
     */
    public static void getRealNameAuthenticationVerifyCode(String phone, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.REAL_NAME_AUTHENTICATION_SEND_VERIFY_CODE, params, callback);
    }

    /**
     * 实名认证接口
     * version 1.0.1
     *
     * @param uuid     用户uuid
     * @param phone    手机号
     * @param name     真实姓名
     * @param card     身份证号码
     * @param code     验证码
     * @param callback 回调
     */
    public static void realNameAuthentication(String uuid, String phone, String name, String card, String code, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("name", name);
        params.put("card", card.toUpperCase());
        params.put("code", code);
        OkHttpUtils.getInstace().post(Const.REAL_NAME_AUTHENTICATION, params, callback);
    }

    /**
     * 实名认证信息回显
     * version 1.0.1
     *
     * @param uuid     用户uuid
     * @param phone    手机号
     * @param callback 回调
     */
    public static void realNameAuthenticationInfo(String uuid, String phone, AbstractNetWorkCallback<RealNameAuthenticationInfoBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);

        OkHttpUtils.getInstace().post(Const.REAL_NAME_AUTHENTICATION_INFO, params, callback);
    }

    /**
     * 设置交易密码接口
     * version 1.0.1
     *
     * @param uuid     用户uuid
     * @param phone    手机号
     * @param pwd      交易密码
     * @param code     验证码
     * @param callback 回调
     */
    public static void setTradePwd(String uuid, String phone, String pwd, String code, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("transactionpassword", pwd);
        params.put("code", code);

        OkHttpUtils.getInstace().post(Const.SET_TRADE_PWD, params, callback);
    }

    /**
     * 获取我的粉丝列表
     * version 1.0.1
     *
     * @param phone    手机号
     * @param uuid     uuid
     * @param callback 回调
     */
    public static void getFansList(String id, String phone, String uuid, int page, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("id", id);
        params.put("page", String.valueOf(page));

        OkHttpUtils.getInstace().post(Const.MY_FANS_LIST, params, callback);
    }

    /**
     * 获取我的关注列表
     * version 1.0.1
     *
     * @param phone    手机号
     * @param uuid     uuid
     * @param callback 回调
     */
    public static void getAttentionsList(String id, String phone, String uuid, int page, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("page", String.valueOf(page));

        OkHttpUtils.getInstace().post(Const.MY_ATTENTION_LIST, params, callback);
    }

    /**
     * 获取商品管理列表
     * version 1.0.1
     *
     * @param phone    手机号
     * @param uuid     uuid
     * @param type     0 出售中 1 已下架
     * @param callback 回调
     */
    public static void getGoodsManagerList(String phone, String uuid, String type, String page, String rows, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("type", type);
        params.put("page", page);
        params.put("rows", rows);

        OkHttpUtils.getInstace().post(Const.GOODSMANGELIST, params, callback);
    }

    /**
     * 商品列表批量操作
     * version 1.0.1
     *
     * @param phone    手机号
     * @param uuid     uuid
     * @param type     0 下架 1 上架 2 删除
     * @param pIds     逗号拼接起来的商品id
     * @param callback 回调
     */
    public static void goodsManageBatchOperation(String pIds, String phone, String uuid, String type, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("type", type);
        params.put("pids", pIds);
        OkHttpUtils.getInstace().post(Const.GOODSMANAGE_BATCH_OPERATION, params, callback);
    }

    /**
     * 忘记交易密码验证二要素
     * version 1.0.1
     *
     * @param uuid     用户uuid
     * @param phone    手机号
     * @param name     姓名
     * @param idCard   身份证号码
     * @param callback 回调
     */
    public static void forgetTradePwdCheckIdCard(String uuid, String phone, String name, String idCard, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("name", name);
        params.put("cardNo", idCard);

        OkHttpUtils.getInstace().post(Const.FORGET_TRADE_PWD_CHECK_IDCARD, params, callback);
    }

    /**
     * 解绑银行卡
     * <p>
     * version 1.0.1
     */
    public static void setUnBindBank(String uuid, String phone, String code, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("code", code);

        OkHttpUtils.getInstace().post(Const.UNBANK_BANK, params, callback);
    }

    /**
     * 绑定银行卡
     *
     * @param uuid     用户uuid
     * @param phone    手机号
     * @param cardNum  银行卡号
     * @param bankName 开户行
     * @param callback 回调
     * @version 1.0.1
     */
    public static void getBindBank(String uuid, String phone, String cardNum, String bankName, String bankNano, AbstractNetWorkCallback<BankBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("cardNum", cardNum);
        params.put("bankNano", bankNano);
        params.put("bankName", bankName);
        OkHttpUtils.getInstace().post(Const.BIND_BANK, params, callback);
    }

    /**
     * 银行卡验证手机号
     * <p>
     * version 1.0.1
     */
    public static void setOuBindBank(String phone, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.BANK_PSW, params, callback);
    }

    /**
     * 获取银行卡信息
     *
     * @version 1.0.1
     */
    public static void getBindInfo(String uuid, String phone, AbstractNetWorkCallback<BankinfroBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.BANK_LIST, params, callback);
    }

    /**
     * 获取银行的开户行
     * <p>
     * version 1.0.1
     */
    public static void setOpenBank(String bankCard, AbstractNetWorkCallback<BandBankBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("bankCard", bankCard);

        OkHttpUtils.getInstace().post(Const.OPEN_BANK, params, callback);
    }

    /**
     * * 关注
     * version 1.0.1
     *
     * @param bid      被关注用户id
     * @param type     0关注 1取消关注
     * @param phone    用户手机号
     * @param uuid     uuid
     * @param callback 回调
     */
    public static void attention(String bid, String type, String phone, String uuid, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("type", type);
        params.put("bid", bid);

        OkHttpUtils.getInstace().post(Const.ATTENTION, params, callback);
    }

    /**
     * 注册
     * version 1.0.1
     *
     * @param phone    手机号
     * @param mark     登录方式
     * @param smsCode  验证码
     * @param password 密码
     * @param callback 回调
     */
    public static void register(String phone, String smsCode, String password, String mark, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("mark", mark);
        params.put("smsCode", smsCode);
        params.put("password", password);

        OkHttpUtils.getInstace().post(Const.REGISTER, params, callback);
    }

    /**
     * 忘记密码
     * version 1.0.1
     *
     * @param phone    手机号
     * @param smsCode  验证码
     * @param password 密码
     * @param callback 回调
     */
    public static void forgetPassWord(String phone, String smsCode, String password, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("smsCode", smsCode);
        params.put("rePassword", password);

        OkHttpUtils.getInstace().post(Const.FORGET_PSW, params, callback);
    }

    /**
     * 判断用户输入的交易密码是否正确（修改交易密码前判断）
     *
     * @param uuid        uuid
     * @param phone       手机号
     * @param oldpassword 旧密码
     */
    public static void updateTradePwdCheckedOldTradePwd(String uuid, String phone, String oldpassword, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("oldpassword", oldpassword);

        OkHttpUtils.getInstace().post(Const.UPDATE_TRADE_PWD_CHECKED_OLD_TRADE_PWD, params, callback);
    }

    /**
     * 猎头订单详情
     * version 1.0.1
     *
     * @param phone   手机号
     * @param uuid    uuid
     * @param orderNo 订单号
     * @param status  订单状态 0待付款 2待发货（已付款） 3待收货 4已评价 6已撤销 7已收货（待评价）
     */
    public static void getHunterOrderDetails(String phone, String uuid, String orderNo, int status, com.yst.onecity.http.AbstractNetWorkCallback<OrderDetailsContentBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("orderNo", orderNo);
        params.put("status", String.valueOf(status));
        OkHttpUtils.getInstace().post(Const.HUNTER_ORDER_DETAILS, params, callback);
    }

    /**
     * 搜索我的粉丝列表
     * version 1.0.1
     *
     * @param phone    手机号
     * @param uuid     uuid
     * @param callback 回调
     */
    public static void searchFansList(String phone, String uuid, String key, int page, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("key", key);
        params.put("page", String.valueOf(page));

        OkHttpUtils.getInstace().post(Const.MY_FANS_LIST, params, callback);
    }

    /**
     * 搜索我的关注列表
     * version 1.0.1
     *
     * @param phone    手机号
     * @param uuid     uuid
     * @param callback 回调
     */
    public static void searchAttentionsList(String phone, String uuid, String key, int page, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("key", key);
        params.put("page", String.valueOf(page));

        OkHttpUtils.getInstace().post(Const.MY_ATTENTION_LIST, params, callback);
    }


    /**
     * 发布 已有商品列表 添加产品计划的商品-根据商品的名称搜索匹配的商品
     *
     * @param name     name
     * @param uuid     uuid
     * @param phone    手机号
     * @param callback 回调
     */
    public static void getProductListByName(String name, String uuid, String phone, int page, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("name", name);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("page", page + "");
        OkHttpUtils.getInstace().post(Const.PRODUCT_NAME, params, callback);
//        OkHttpUtils.getInstace().post("http://192.168.30.240:10001/mobile/after/solr/getProductListByName", params, callback);

    }


    /**
     * 我的收入列表
     * version 1.0.1
     *
     * @param memberId   账号ID
     * @param recordType 标识类型
     * @param phone      手机
     * @param uuid       uuid
     * @param callback   回调
     */

    public static void getMyInComeListData(String memberId, String recordType, String phone, String uuid, String page, AbstractNetWorkCallback<MyInComeListBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("memberId", memberId);
        params.put("recordType", recordType);
        params.put("page", page);
        params.put("rows", "10");
        OkHttpUtils.getInstace().post(Const.MY_INCOME_LIST, params, callback);
    }

    /**
     * 获取物流公司列表
     * version 1.0.1
     *
     * @param uuid     uuid
     * @param phone    手机号
     * @param callback 回调
     */
    public static void getLogisticsCompanyList(String uuid, String phone, AbstractNetWorkCallback<LogisticsCompanyBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.GET_LOGISTICS_COMPANY_LIST, params, callback);
    }

    /**
     * 查看评价内容
     * version 1.0.1
     *
     * @param phone     用户账号
     * @param uuid      uuid
     * @param commentId 评论id
     * @param callback  回调
     */
    public static void getProductEvaluateContent(String phone, String uuid, String commentId, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("commentId", commentId);
        OkHttpUtils.getInstace().post(Const.GET_EVALUATE_CONTENT, params, callback);
    }

    /**
     * 修改交易密码
     *
     * @param uuid     uuid
     * @param phone    手机号
     * @param tradePwd 交易密码
     * @param callback 回调
     */
    public static void updateTradePwd(String uuid, String phone, String tradePwd, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("transactionpassword", tradePwd);

        OkHttpUtils.getInstace().post(Const.UPDATE_TRADE_PWD, params, callback);
    }

    /**
     * 获取评价列表
     * version 1.0.1
     *
     * @param uuid     uuid
     * @param phone    用户账号
     * @param orderNo  订单号
     * @param callback 回调
     */
    public static void getEvaluateList(String uuid, String phone, String orderNo, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("orderNo", orderNo);
        OkHttpUtils.getInstace().post(Const.GET_EVAUATE_LIST, params, callback);
    }


    /**
     * 添加评论
     * version 1.0.1
     *
     * @param phone     用户账号
     * @param uuid      uuid
     * @param content   评论内容
     * @param startNum  星星数
     * @param orderId   订单id
     * @param anonymity 是否匿名0是 1否
     * @param address   评论图片 逗号拼接（非必传）
     * @param callback  回调
     */
    public static void addEvaluate(String phone, String uuid, String content, int startNum, String orderId, int anonymity, String address, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("content", content);
        params.put("startNum", String.valueOf(startNum));
        params.put("orderId", orderId);
        params.put("anonymity", String.valueOf(anonymity));
        params.put("address", address);
        params.put("source", String.valueOf(0));
        OkHttpUtils.getInstace().post(Const.ADD_EVAUATE, params, callback);
    }

    /**
     * 消息通知，互动列表
     *
     * @param phone    手机号
     * @param uuid     uuid
     * @param callback 回调
     * @author jiaofan
     * @version 1.0.1
     */
    public static void getHuDongMsgList(String phone, String id, String uuid, String page, String rows, AbstractNetWorkCallback<InteractionBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("id", id);
        params.put("uuid", uuid);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.GET_HUDONG_MSG_LIST, params, callback);
    }

    /**
     * 获取售后列表
     * version 1.0.1
     *
     * @param userType 0普通用户，1猎头
     * @param page     当前页
     * @param phone    用户账号
     * @param uuid     uuid
     * @param callback 回调
     */
    public static void getAfterSalesList(int userType, int page, String phone, String uuid, AbstractNetWorkCallback callback) {
        String url = userType == 0 ? Const.AFTER_SALES_LIST_MEMBER : Const.AFTER_SALES_LIST_HUNTER;
        Map<String, Object> params = new HashMap<>(16);
        params.put("page", String.valueOf(page));
        params.put("rows", String.valueOf(10));
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(url, params, callback);
    }

    /**
     * 修改登录密码
     *
     * @param uuid        uuid
     * @param phone       手机号
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     * @param callback    回调
     */
    public static void updateLoginPwd(String uuid, String phone, String setLoginPassword, String newPassword, String oldPassword, int type, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        if (type == NO2) {
            params.put("newPassword", newPassword);
            params.put("oldPassword", oldPassword);
        } else {
            params.put("newPassword", setLoginPassword);
        }
        params.put("type", String.valueOf(type));

        OkHttpUtils.getInstace().post(Const.UPDATE_LOGIN_PWD, params, callback);
    }

    /**
     * 查找供应商商品的供应商列表
     *
     * @param uuid     uuid
     * @param phone    手机号
     * @param callback 回调
     */
    public static void getSupplierAllList(String uuid, String phone, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.SUPPLIER_ALLLIST, params, callback);
    }


    /**
     * 消息通知，系统通知列表
     *
     * @param phone    手机号
     * @param uuid     uuid
     * @param callback 回调
     * @author jiaofan
     * @version 1.0.1
     */
    public static void getSystemMsgList(String phone, String id, String uuid, String page, String rows, AbstractNetWorkCallback<NoticeBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("id", id);
        params.put("uuid", uuid);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.GET_SYSTEM_MSG_LIST, params, callback);
    }

    /**
     * 申请售后选择商品列表
     * version 1.0.1
     *
     * @param phone    用户账号
     * @param uuid     uuid
     * @param orderNo  订单号
     * @param callback 回调
     */
    public static void getApplyAfterSalesList(String phone, String uuid, String orderNo, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("orderNo", orderNo);
        OkHttpUtils.getInstace().post(Const.CHOOSE_PRODUCT_AFTER_SALES_LIST, params, callback);
    }

    /**
     * 删除订单
     * version 1.0.1
     *
     * @param phone    用户账号
     * @param uuid     uuid
     * @param orderNo  主订单编号
     * @param type     1 会员 2 猎头
     * @param callback 回调
     */
    public static void deleteOrder(String phone, String uuid, String orderNo, int type, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("orderNo", orderNo);
        params.put("type", String.valueOf(type));
        OkHttpUtils.getInstace().post(Const.DELETE_ORDER, params, callback);
    }

    /**
     * 取消订单
     * version 1.0.1
     *
     * @param phone    猎头手机号
     * @param uuid     uuid
     * @param oIds     多个订单id拼接（eg: 1,2,3）
     * @param userType 用户类型
     * @param callback 回调
     */
    public static void cancleOrderForHunter(String phone, String uuid, String oIds, int userType, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("oIds", oIds);
        String url = userType == 0 ? Const.CANCLE_ORDER_FOR_MEMBER : Const.CANCLE_ORDER_FOR_HUNTE;
        OkHttpUtils.getInstace().post(url, params, callback);
    }

    /**
     * 猎豆列表
     *
     * @version 1.0.1
     */
    public static void myhunt(String uuid, String phone, int page, AbstractNetWorkCallback<HuntBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("page", String.valueOf(page));
        OkHttpUtils.getInstace().post(Const.MY_LIEDOU, params, callback);
    }

    /**
     * 我的 修改个人信息
     *
     * @param phone                 用户账号
     * @param uuid                  uuid
     * @param logoAttachmentAddress 图片地址
     * @param nickname              名称
     * @param content               简介
     */
    public static void setPersoninfo(String id, String uuid, String phone, String logoAttachmentAddress, String nickname, String content, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("logoAttachmentAddress", logoAttachmentAddress);
        params.put("nickname", nickname);
        params.put("content", content);
        OkHttpUtils.getInstace().post(Const.SEETING_INFRO, params, callback);
    }

    /**
     * 我的 获取个人信息
     *
     * @param phone 用户账号
     * @param uuid  uuid
     */
    public static void getPersoninfo(String uuid, String phone, AbstractNetWorkCallback<PersondetailBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.GET_INFRO, params, callback);
    }

    /**
     * 我的 版本检测
     *
     * @param versionNo 版本号
     * @param mark      来源， 0 安卓 1 iOS 2 PC
     */
    public static void myCheckVerson(String versionNo, String clientType, int mark, AbstractNetWorkCallback<CheckVersionBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("versionNo", versionNo);
        params.put("clientType", clientType);
        params.put("mark", String.valueOf(mark));
        OkHttpUtils.getInstace().post(Const.CHECK_VERSON, params, callback);
    }

    /**
     * 我的 地区查询
     *
     * @param type 0省，1市，2区
     * @param id   省id
     */

    public static void myadress(String phone, String uuid, String type, String id, AbstractNetWorkCallback<ProvinceAdressBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("type", type);
        params.put("id", id);
        OkHttpUtils.getInstace().post(Const.SETTING_ADRESS, params, callback);
    }

    /**
     * 地区市查询
     *
     * @param type 0省，1市，2区
     * @param id   省id
     */

    public static void myCityAdress(String phone, String uuid, String type, String id, AbstractNetWorkCallback<CityAdressBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("type", type);
        params.put("id", id);
        OkHttpUtils.getInstace().post(Const.SETTING_ADRESS, params, callback);
    }

    /**
     * 我的  会员个人信息回显
     */
    public static void myPersonInfro(String memberId, AbstractNetWorkCallback<PersonBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("memberId", memberId);
        OkHttpUtils.getInstace().post(Const.MEEBER_INDRO, params, callback);
    }

    /**
     * 徒弟数量
     */
    public static void getHunterNum(String phone, String uuid, AbstractNetWorkCallback<HunterNumBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.HUNTER_NUM, params, callback);
    }

    /**
     * 徒弟列表
     */
    public static void getHunterNum(int memberId, String phone, String uuid, AbstractNetWorkCallback<HunterNumBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("memberId", memberId);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.HUNTER_LIST, params, callback);
    }

    /**
     * 发布产品计划
     *
     * @param modelType  类型：0视频 1一图 2二图 3三图
     * @param title      标题
     * @param describes  描述
     * @param address    发布地址
     * @param longitude  经度
     * @param latitude   纬度
     * @param productIds 关联商品id集合(逗号拼接)
     * @param covers     封面图地址集合(逗号拼接) modelType不等于0必填
     * @param video      视频地址 modelType等于0必填
     * @param cover      视频封面地址 modelType等于0必填
     */
    public static void myPlan(String phone, String uuid, int modelType, String title, String describes, String address, String longitude, String latitude, String productIds, String covers, String video, String cover, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("modelType", modelType);
        params.put("title", title);
        params.put("describes", describes);
        params.put("address", address);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("productIds", productIds);
        params.put("covers", covers);
        params.put("video", video);
        params.put("cover", cover);
        OkHttpUtils.getInstace().post(Const.UPDATA_LIST, params, callback);
    }

    /**
     * 我的  意见反馈
     *
     * @param feedbackContent 反馈内容
     * @param feedbackAccount 反馈账号
     */
    public static void myCoupleList(String phone, String uuid, String feedbackContent, String feedbackAccount, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("feedbackContent", feedbackContent);
        params.put("feedbackAccount", feedbackAccount);
        OkHttpUtils.getInstace().post(Const.SETTING_COUPLE, params, callback);
    }

    /**
     * 收货人列表
     *
     * @param phone    用户登录的手机号
     * @param uuid     uuid
     * @param callback 回调
     * @author jiaofan
     * @version 1.0.1
     */
    public static void myAddressList(String id, String phone, String uuid, AbstractNetWorkCallback<ReapBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("id", id);
        OkHttpUtils.getInstace().post(Const.SHOPING_LIST, params, callback);
    }

    /**
     * 删除收货人列表
     *
     * @param phone    用户登录的手机号
     * @param uuid     uuid
     * @param id       地址id
     * @param callback 回调
     * @author jiaofan
     * @version 1.0.1
     */
    public static void deleteAddress(String phone, String uuid, String id, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("id", id);
        OkHttpUtils.getInstace().post(Const.DELETE_ADRESS, params, callback);
    }

    /**
     * 设置默认地址
     *
     * @param phone    用户登录的手机号
     * @param uuid     uuid
     * @param aid      地址id
     * @param callback 回调
     * @author jiaofan
     * @version 1.0.1
     */
    public static void setDefaultAddress(String phone, String uuid, String aid, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("aid", aid);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.SET_DEFAULT_ADDRESS, params, callback);
    }

    /**
     * 编辑收货地址 回显
     *
     * @param phone    用户登录手机号
     * @param uuid     uuid
     * @param aid      地址id
     * @param callback 回调
     * @author jiaofan
     * @version 1.0.1
     */
    public static void getAddressInfo(String phone, String uuid, String aid, AbstractNetWorkCallback<ReapRensonBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("aid", aid);
        OkHttpUtils.getInstace().post(Const.SET_ADRESS, params, callback);
    }

    /**
     * 新增收货地址
     *
     * @param phone          用户登录的手机号
     * @param uuid           uuid
     * @param status         是否是默认地址
     * @param userName       填写的名字
     * @param mobile         填写的手机号
     * @param proAreaId      省id
     * @param districtAreaId 区id
     * @param cityAreaId     市id
     * @param detailAddress  详细地址
     * @param code           邮编
     * @param callback       回调
     * @author jiaofan
     * @version 1.0.1
     */
    public static void addAddress(String id, String phone, String uuid, String status, String userName, String mobile, String proAreaId, String districtAreaId, String cityAreaId, String detailAddress, String code, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("status", status);
        params.put("userName", userName);
        params.put("mobile", mobile);
        params.put("proAreaId", proAreaId);
        params.put("districtAreaId", districtAreaId);
        params.put("cityAreaId", cityAreaId);
        params.put("detailAddress", detailAddress);
        params.put("code", code);
        OkHttpUtils.getInstace().post(Const.ADD_ADRESS, params, callback);
    }


    /**
     * 编辑收货地址
     *
     * @param phone          用户登录的手机号
     * @param uuid           uuid
     * @param status         1为默认地址
     * @param userName       收货人姓名
     * @param mobile         收货人手机号
     * @param proAreaId      省id
     * @param districtAreaId 区id
     * @param cityAreaId     市id
     * @param detailAddress  详细地址
     * @param code           邮编
     * @param id             收货地址id
     * @author jiaofan
     * @version 1.0.1
     */
    public static void updateAddress(String phone, String uuid, String status, String userName, String mobile, String proAreaId, String districtAreaId, String cityAreaId, String detailAddress, String code, String id, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("status", status);
        params.put("userName", userName);
        params.put("mobile", mobile);
        params.put("proAreaId", proAreaId);
        params.put("districtAreaId", districtAreaId);
        params.put("cityAreaId", cityAreaId);
        params.put("detailAddress", detailAddress);
        params.put("code", code);
        params.put("id", id);
        OkHttpUtils.getInstace().post(Const.UPDATA_LIST, params, callback);
    }

    /**
     * 账号安全
     *
     * @param phone    手机号
     * @param uuid     uuid
     * @param callback 回调
     */
    public static void getAccountSafe(String id, String phone, String uuid, AbstractNetWorkCallback<AccountSafeBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.ACCOUTN_SAFE, params, callback);
    }

    //***************************************************陈佳迪****************************************************

    /**
     * 资讯评论
     *
     * @param content        评论内容
     * @param consultationId 资讯id
     * @param type           类型：0资讯 1产品计划
     * @param phone          用户手机号
     * @param uuid           uuid
     */
    public static void infoComment(String content, String consultationId, int type, String phone, String uuid, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("content", content);
        params.put("consultationId", consultationId);
        params.put("type", String.valueOf(type));
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.INFO_COMMENT, params, callback);
    }

    /**
     * 产品计划评论
     *
     * @param content        评论内容
     * @param consultationId 资讯id
     * @param type           类型：0资讯 1产品计划
     * @param phone          用户手机号
     * @param uuid           uuid
     */
    public static void infoPlanComment(String content, String consultationId, int type, String phone, String uuid, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("content", content);
        params.put("planId", consultationId);
        params.put("type", String.valueOf(type));
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.INFO_COMMENT, params, callback);
    }

    /**
     * 创建运费模板（卖家包承担运费）
     *
     * @param name           运费模板名称
     * @param isFreeShipping 是否包邮 0是自定义， 1包邮
     * @param costType       结算方式0
     * @param phone          手机号
     * @param uuid           uuid
     * @param callback       回调
     */
    public static void addFreeEstablishFreight(String name, String isFreeShipping, int costType, String phone, String uuid, AbstractNetWorkCallback<TemplateBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("name", name);
        params.put("isFreeShipping", isFreeShipping);
        params.put("costType", String.valueOf(costType));
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.ADD_ESTABLISH_FREIGHT, params, callback);
    }

    /**
     * 创建运费模板（自定义承担运费）
     *
     * @param name            运费模板名称
     * @param isFreeShipping  是否包邮 0是自定义， 1包邮
     * @param costType        结算方式0
     * @param freightRuleJson 运费规则json格式的字符串，当运费为包邮是，此处不填
     * @param phone           手机号
     * @param uuid            uuid
     * @param callback        回调
     */
    public static void addEstablishFreight(String name, String isFreeShipping, int costType, String freightRuleJson, String phone, String uuid, AbstractNetWorkCallback<TemplateBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("name", name);
        params.put("isFreeShipping", isFreeShipping);
        params.put("costType", String.valueOf(costType));
        params.put("freightRuleJson", freightRuleJson);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.ADD_ESTABLISH_FREIGHT, params, callback);
    }

    //***************************************************卢旭昌****************************************************

    /**
     * 绑定第三方账号接口
     *
     * @param phone      手机号
     * @param bindStatus 绑定类型 1QQ 2 微信
     * @param opendId    qqid
     * @param nickName   qq昵称
     */
    public static void bindThirdAccount(String phone, int bindStatus, String opendId, String nickName, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        if (bindStatus == NO1) {
            params.put("qqOppendId", opendId);
            params.put("qqNickName", nickName);
        } else {
            params.put("wxOppendId", opendId);
            params.put("wxNickName", nickName);
        }
        params.put("phone", phone);
        params.put("bindStatus", String.valueOf(bindStatus));
        OkHttpUtils.getInstace().post(Const.BIND_THIRD_ACCOUNT, params, callback);
    }

    /**
     * 解除绑定第三方账号接口
     *
     * @param type  类型：1解除qq绑定，2解除微信绑定
     * @param phone 用户手机号
     * @param uuid  uuid
     */
    public static void removeBindThirdAccount(String phone, String uuid, int type, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("type", String.valueOf(type));
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.REMOVE_BIND_THIRD_ACCOUNT, params, callback);
    }

    /**
     * 获取服务专员中心 商品库列表
     */
    public static void serverCenterProductList(AbstractNetWorkCallback<ProductBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("name", NULL);
        OkHttpUtils.getInstace().post(Const.SERVER_CENTER_PRODUCT_LIST, params, callback);
    }

    /**
     * 修改群聊名称
     * uuid true 请求URL   String  用户唯一标识
     * phone true 请求URL   string  登录用户手机号
     * chatGroup true 请求URL   string  群名称
     * groupId true 请求URL   long  群id
     * token
     */
    public static void updateChatGroupName(String chatGroup, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", App.manager.getUuid());
        params.put("phone", App.manager.getPhoneNum());
        params.put("chatGroup", chatGroup);
        params.put("groupId", String.valueOf(App.manager.getGroupId()));
        params.put("token", MyApp.manager.getToken());
        OkHttpUtils.getInstace().post(Const.UPDATE_CHAT_GROUP_NAME, params, callback);
    }

    /**
     * 检查用户是否为专员
     *
     * @param callback
     */
    public static void checkMemberIsHunter(AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", App.manager.getUuid());
        params.put("phone", App.manager.getPhoneNum());
        OkHttpUtils.getInstace().post(Const.CHECK_MEMBER_IS_HUNTER, params, callback);
    }

    /**
     * 检查三方账号是否绑定过手机号
     *
     * @param callback
     */
    public static void checkThirdAccountIsBind(int type, String openId, AbstractNetWorkCallback<SanFangStateBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("loginType", String.valueOf(type));
        params.put("openId", openId);
        OkHttpUtils.getInstace().post(Const.CHECK_THIRD_ACCOUNT_IS_BIND, params, callback);
    }
    //***************************************************车建奇****************************************************


    //***************************************************廉锦雪****************************************************


    //***************************************************焦帆****************************************************


    //***************************************************翟延武****************************************************

    /**
     * 发布供应商商品列表 展示平台上所有的供应商商品/根据商品名称或供应商搜索
     *
     * @param supplierId 供应商id
     * @param page       页数
     * @param rows       rows
     * @param name       查找name商品
     * @param phone      phone
     * @param uuid       uuid
     * @param callback   回调
     */
    public static void getProductListBySupplierId(String supplierId, String page, String rows, String name, String phone, String uuid, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        if (!TextUtils.isEmpty(supplierId)) {
            params.put("supplierId", supplierId);
        } else {
            params.put("supplierId", "");
        }
        params.put("page", page);
        params.put("rows", rows);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("name", name);
        OkHttpUtils.getInstace().post(Const.SUPPLIERID_PRODUCT, params, callback);
    }

    /**
     * 添加商品运费模板列表-
     *
     * @param uuid     uuid
     * @param phone    手机号
     * @param callback 回调
     */
    public static void getFreightRuleMainListByUserId(String uuid, String phone, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.FREIGHT_RULE, params, callback);
    }

    /**
     * 猎头商品添加
     *
     * @param productJson             商品的信息json格式的字符串
     * @param imageJSON               商品图片json格式字符串
     * @param description             商品的简介
     * @param specificationAndValJson 规格与规格值的json格式字符串
     * @param productSkuJSON          商品属性json格式字符串
     * @param uuid                    uuid
     * @param phone                   手机号
     * @param callback                回调
     */
    public static void addProductByHunter(String productJson, String imageJSON, String description, String specificationAndValJson, String productSkuJSON, String uuid, String phone, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("productJson", productJson);
        params.put("imageJSON", imageJSON);
        params.put("description", description);
        params.put("specificationAndValJson", specificationAndValJson);
        params.put("productSkuJSON", productSkuJSON);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.ADD_PRODUCT, params, callback);
//        OkHttpUtils.getInstace().post("http://192.168.30.240:10001/mobile/after/product/addProductByHunter", params, callback);
    }

    /**
     * 猎头商品编辑
     *
     * @param productJson             商品的信息json格式的字符串
     * @param imageJSON               商品图片json格式字符串
     * @param description             商品的简介
     * @param specificationAndValJson 规格与规格值的json格式字符串
     * @param productSkuJSON          商品属性json格式字符串
     * @param uuid                    uuid
     * @param phone                   手机号
     * @param callback                回调
     */
    public static void updateProductByHunter(String productJson, String imageJSON, String description, String specificationAndValJson, String productSkuJSON, String uuid, String phone, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("productJson", productJson);
        params.put("imageJSON", imageJSON);
        params.put("description", description);
        params.put("specificationAndValJson", specificationAndValJson);
        params.put("productSkuJSON", productSkuJSON);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.UPDATE_PRODUCT, params, callback);
//        OkHttpUtils.getInstace().post("http://192.168.30.240:10001/mobile/after/product/addProductByHunter", params, callback);
    }

    /**
     * 提现
     * version 1.0.1
     *
     * @param uuid       uuid
     * @param phone      手机号
     * @param ip         ip
     * @param money      提现金额
     * @param bankBindId 银行卡号
     * @param passwd     密码
     * @param callback   回调
     */
    public static void cash(String uuid, String phone, String ip, String money, String bankBindId, String passwd, AbstractNetWorkCallback<GroupInfoBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("ip", ip);
        params.put("money", money);
        params.put("bankBindId", bankBindId);
        params.put("passwd", passwd);
        OkHttpUtils.getInstace().post(Const.CASH, params, callback);
    }

    /**
     * 匹配通讯录好友列表
     *
     * @param phoneNum 手机号
     * @param callback 回调
     */
    public static void getContact(String phoneNum, AbstractNetWorkCallback<SpeciallyPersonPhoneBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phones", phoneNum);
        params.put("phone", App.manager.getPhoneNum());
        params.put("uuid", App.manager.getUuid());
        OkHttpUtils.getInstace().post(Const.MATCHING_PHONEBOOK, params, callback);
    }

    /**
     * 其他服务专员列表
     *
     * @param callback 回调
     */
    public static void getOtherSpeciallyList(AbstractNetWorkCallback<SpeciallyPersonPhoneBean> callback) {
        Map<String, Object> params = new HashMap<String, Object>(16);
        params.put("phone", App.manager.getPhoneNum());
        params.put("uuid", App.manager.getUuid());
        OkHttpUtils.getInstace().post(Const.OHTER_SPECIALLYPERSON, params, callback);
    }

    /**
     * 根据昵称和手机号查询专员
     *
     * @param param    手机号或者昵称
     * @param callback 回调
     */
    public static void searchPerson(String param, AbstractNetWorkCallback<SpeciallyPersonPhoneBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("param", param);
        params.put("uuid", App.manager.getUuid());
        params.put("phone", App.manager.getPhoneNum());
        OkHttpUtils.getInstace().post(Const.BYNAME_PHONE_QUERY_ATTACHE, params, callback);
    }


    /**
     * 发布--商品编辑--商品信息回显-
     *
     * @param productId 商品id
     * @param phone     手机号
     * @param uuid      uuid
     * @param callback  回调
     */
    public static void getHunterProductContent(String productId, String phone, String uuid, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("productId", productId);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.HUNTER_PROODUCT, params, callback);
    }

    /**
     * 发布 ---查询已发布的产品咨询的商品列表--编辑列表
     *
     * @param productPlanId 产品计划id
     * @param phone         手机号
     * @param uuid          uuid
     * @param callback      回调
     */
    public static void getProductListByProductPlanId(String productPlanId, String phone, String uuid, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("productPlanId", productPlanId);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.COMPILE_HUNTER_PROODUCT, params, callback);
    }

    /**
     * 运费模板详情
     *
     * @param freightId id
     * @param callback  回调
     */
    public static void getFreightRuleContent(String freightId, String phone, String uuid, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("freightId", freightId);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.FREIGHT_RULE_CODE, params, callback);
    }

    //***************************************************刘曼青****************************************************

    /**
     * 我的二维码
     *
     * @param uuid
     * @param phone
     * @param callback
     */
    public static void myZxing(String id, String uuid, String phone, AbstractNetWorkCallback<MyZxingBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.MY_ZXING, params, callback);
    }

    /**
     * 我的发布——资讯
     *
     * @param uuid
     * @param phone
     * @param callback
     */
    public static void myIssueInformation(String type, String uuid, String phone, String page, String rows, AbstractNetWorkCallback<InformationBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("type", type);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.MY_ISSUE, params, callback);
    }

    /**
     * 我的发布——产品计划
     *
     * @param uuid
     * @param phone
     * @param callback
     */
    public static void myIssueProductPlan(String type, String uuid, String phone, String page, String rows, AbstractNetWorkCallback<ProductPlanBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("type", type);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.MY_ISSUE, params, callback);
    }

    /**
     * 我的发布——项目计划
     *
     * @param uuid
     * @param phone
     * @param callback
     */
    public static void myIssueProjectPlan(String type, String uuid, String phone, String page, String rows, AbstractNetWorkCallback<ProjectPlanBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("type", type);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.MY_ISSUE, params, callback);
    }

    /**
     * 添加产品计划列表
     *
     * @param page     页码
     * @param rows     行数
     * @param callback 回调
     */
    public static void addProductPlanList(String page, String rows, AbstractNetWorkCallback<PublishInfoAddProductPlanBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.ADD_PRODUCTPLAN_LIST, params, callback);
    }

    /**
     * 我的收藏-产品计划
     *
     * @param phone
     * @param uuid
     * @param callback
     */
    public static void myCollectProductPlan(String phone, String uuid, String page, String rows, AbstractNetWorkCallback<MyCollectProductPlan> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.MY_COLLECT_PRODUCTPLAN, params, callback);
    }

    /**
     * 我的收藏-商品
     *
     * @param phone
     * @param uuid
     * @param callback
     */
    public static void myCollectGood(String phone, String uuid, String page, String rows, AbstractNetWorkCallback<MyCollectGoodBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.MY_COLLECT_GOOGS, params, callback);
    }

    /**
     * 我的收藏-资讯
     *
     * @param phone
     * @param uuid
     * @param callback
     */
    public static void myCollectInformation(String phone, String uuid, String page, String rows, AbstractNetWorkCallback<MyCollectInformationBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.MY_COLLECT_INFORMATION, params, callback);
    }

    /**
     * 我的发布-删除资讯
     *
     * @param id       资讯id
     * @param phone    用户账号
     * @param uuid
     * @param callback
     */
    public static void myIssueDeleteInformation(String id, String uuid, String phone, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.MY_ISSUE_DELETE_INFORMATION, params, callback);
    }

    /**
     * 我的发布-删除项目计划
     *
     * @param id       资讯id
     * @param phone    用户账号
     * @param uuid
     * @param callback
     */
    public static void myIssueDeleteProjectPlan(String id, String uuid, String phone, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.MY_ISSUE_DELETE_PROJECTPLAN, params, callback);
    }

    /**
     * 我的发布-删除产品计划
     *
     * @param id       资讯id
     * @param phone    用户账号
     * @param uuid
     * @param callback
     */
    public static void myIssueDeleteProductPlan(String id, String uuid, String phone, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.MY_ISSUE_DELETE_PRODUCTPLAN, params, callback);
    }

    /**
     * 取消收藏接口-资讯
     *
     * @param type     类型
     * @param uuid
     * @param phone
     * @param callback
     */
    public static void collectInformation(String consultationId, String type, String uuid, String phone, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("consultationId", consultationId);
        params.put("type", type);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.COLLECT, params, callback);
    }

    /**
     * 取消收藏接口-产品计划
     *
     * @param type     类型
     * @param uuid
     * @param phone
     * @param callback
     */
    public static void collectProductPlan(String planId, String type, String uuid, String phone, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("planId", planId);
        params.put("type", type);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.COLLECT, params, callback);
    }

    /**
     * 取消收藏接口-商品
     *
     * @param type     类型
     * @param uuid
     * @param phone
     * @param callback
     */
    public static void collectGood(String productId, String type, String uuid, String phone, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("productId", productId);
        params.put("type", type);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.COLLECT, params, callback);
    }
    //***************************************************宋滨滨****************************************************

    /**
     * 获取发布资讯选择发布分类
     * version 1.0.1
     */
    public static void getPublishClassify(AbstractNetWorkCallback<PublishClassifyBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        OkHttpUtils.getInstace().post(Const.PUBLISH_CLASSIFY, params, callback);
    }

    /**
     * 首页-发布图文资讯
     *
     * @param title                  标题
     * @param uuid                   uuid
     * @param content                正文
     * @param detailSummary          摘要（目前暂无此内容，非必填）
     * @param covers                 封面图
     * @param phone                  电话
     * @param plantId                产品计划id
     * @param consultationClassifyId 分类id
     * @param modelType              类型：1一图 2二图 3三图
     * @param callback               回调
     */
    public static void publishInfoOnGraphics(String title, String uuid, String content, String detailSummary
            , String covers, String phone, String plantId, String consultationClassifyId, String modelType, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("title", title);
        params.put("uuid", uuid);
        params.put("content", content);
        params.put("detailSummary", detailSummary);
        params.put("covers", covers);
        params.put("phone", phone);
        params.put("plantId", plantId);
        params.put("consultationClassifyId", consultationClassifyId);
        params.put("modelType", modelType);
        OkHttpUtils.getInstace().post(Const.PUBLISH_INFO, params, callback);
    }

    /**
     * 发布视频资讯
     *
     * @param title                  标题
     * @param uuid                   uuid
     * @param memberId               memberId
     * @param video                  视频路径
     * @param cover                  视频封面路径
     * @param phone                  账号
     * @param plantId                计划id
     * @param consultationClassifyId 分类id
     * @param callback               回调
     */
    public static void publishVideo(String memberId, String title, String uuid, String video, String cover
            , String phone, String plantId, String consultationClassifyId, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("title", title);
        params.put("uuid", uuid);
        params.put("video", video);
        params.put("cover", cover);
        params.put("phone", phone);
        params.put("plantId", plantId);
        params.put("consultationClassifyId", consultationClassifyId);
        params.put("memberId", memberId);
        OkHttpUtils.getInstace().post(Const.PUBLISH_VIDEO, params, callback);
    }

    /**
     * 发布产品计划
     *
     * @param phone         电话
     * @param modelType     类型：0视频 1一图 2二图 3三图
     * @param title         标题
     * @param describes     描述
     * @param address       发布地址
     * @param longitude     经度
     * @param latitude      纬度
     * @param productIds    关联商品id集合(逗号拼接)
     * @param covers        封面图地址集合(逗号拼接) modelType不等于0必填
     * @param projectPlanId 关联项目计划
     * @param video         视频地址 modelType等于0必填
     * @param cover         视频封面地址 modelType等于0必填
     */
    public static void publishProductPlan(String uuid, String phone, String modelType, String title, String describes, String address
            , String longitude, String latitude, String productIds, String covers, String projectPlanId, String video, String cover, String source, AbstractNetWorkCallback<PublishProductBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("modelType", modelType);
        params.put("title", title);
        params.put("describes", describes);
        params.put("address", address);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("productIds", productIds);
        params.put("covers", covers);
        params.put("projectPlanId", projectPlanId);
        params.put("video", video);
        params.put("cover", cover);
        //来源 0:自创产品计划 1:项目计划
        params.put("source", source);
        OkHttpUtils.getInstace().post(Const.PUBLISH_PRODUCT_PLAN, params, callback);
    }

    /**
     * 修改产品计划
     *
     * @param id            产品计划id
     * @param uuid          uuid
     * @param phone         电话
     * @param modelType     类型：0视频 1一图 2二图 3三图
     * @param title         标题
     * @param describes     描述
     * @param address       发布地址
     * @param longitude     经度
     * @param latitude      纬度
     * @param productIds    关联商品id集合(逗号拼接)
     * @param covers        封面图地址集合(逗号拼接) modelType不等于0必填
     * @param projectPlanId 关联项目计划
     * @param video         视频地址 modelType等于0必填
     */
    public static void editProductPlan(String id, String uuid, String phone, String modelType, String title, String describes, String address
            , String longitude, String latitude, String productIds, String covers, String projectPlanId, String video, AbstractNetWorkCallback<EditProductBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("modelType", modelType);
        params.put("title", title);
        params.put("describes", describes);
        params.put("address", address);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("productIds", productIds);
        params.put("covers", covers);
        params.put("projectPlanId", projectPlanId);
        params.put("video", video);
        OkHttpUtils.getInstace().post(Const.EDIT_PRODUCT_PLAN, params, callback);
    }

    /**
     * 发布资讯-添加产品计划
     *
     * @param keyword
     * @param page
     * @param rows
     * @param memberId
     * @param callback
     */
    public static void addProductSearch(String memberId, String keyword, String page, String rows, AbstractNetWorkCallback<PublishInfoAddProductPlanBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("memberId", memberId);
        params.put("keyword", keyword);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.PUBLISH_PROJECT_SEARCH, params, callback);
    }

    /**
     * 添加发布日记
     *
     * @param phone         电话
     * @param uuid          uuid
     * @param title         标题
     * @param content       内容
     * @param productPlanId 产品计划id
     * @param video         视频地址
     * @param covers        图片地址
     * @param modelType     展示类型: 0:视频 1:1图 2:2图 3:3图
     * @param callback      回调
     */
    public static void publishDiary(String phone, String uuid, String title, String content, String productPlanId
            , String video, String covers, String modelType, AbstractNetWorkCallback<DiaryBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("title", title);
        params.put("content", content);
        params.put("productPlanId", productPlanId);
        params.put("video", video);
        params.put("covers", covers);
        params.put("modelType", modelType);
        OkHttpUtils.getInstace().post(Const.PUBLISH_DAILY, params, callback);
    }

    /**
     * 修改发布日记
     *
     * @param phone         电话
     * @param uuid          uuid
     * @param title         标题
     * @param content       内容
     * @param productPlanId 产品计划日记id
     * @param video         视频地址
     * @param covers        图片地址
     * @param modelType     展示类型: 0:视频 1:1图 2:2图 3:3图
     * @param act           操作类型  0 修改  1 删除
     * @param callback      回调
     */
    public static void updataPublishDiary(String phone, String uuid, String title, String content, String productPlanId
            , String video, String covers, String modelType, String act, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("title", title);
        params.put("content", content);
        params.put("id", productPlanId);
        params.put("video", video);
        params.put("covers", covers);
        params.put("modelType", modelType);
        params.put("act", act);
        OkHttpUtils.getInstace().post(Const.UPDATE_PUBLISH_DAILY, params, callback);
    }

    /**
     * 发布项目计划
     *
     * @param phone     电话
     * @param imgUrl    封面图地址
     * @param title     标题
     * @param describes 描述
     * @param address   发布地址
     * @param longitude 经度
     * @param latitude  纬度
     * @param uuid      uuid
     * @param callback  回调
     */
    public static void publishProject(String phone, String imgUrl, String title, String describes, String address
            , String longitude, String latitude, String uuid, String planClassifyId, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("imgUrl", imgUrl);
        params.put("title", title);
        params.put("describes", describes);
        params.put("address", address);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("uuid", uuid);
        params.put("planClassifyId", planClassifyId);
        OkHttpUtils.getInstace().post(Const.PUBLISH_PROJECT, params, callback);
    }

    /**
     * 获取服务订单列表
     * version 1.0.1
     *
     * @param id       用户id
     * @param status   订单状态 0待付款 2待发货（已付款） 3待收货 4已评价 6已撤销 7已收货（待评价）8 全部
     * @param userType 0 买家（订单） 1 卖家（服务团队）
     * @param page     0   页数
     * @param callback 回调
     */
    public static void getServiceOrderList(String uuid, String phone, int status, int page, int id, int userType, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("status", String.valueOf(status));
        params.put("page", String.valueOf(page));
        params.put("rows", String.valueOf(10));
        params.put("id", String.valueOf(id));
        params.put("userType", String.valueOf(userType));
        OkHttpUtils.getInstace().post(Const.GET_SERVICE_ORDER_LIST, params, callback);
    }

    /**
     * 获取服务订单详情
     * version 1.0.1
     *
     * @param orderId  订单id
     * @param userType 0 买家（订单） 1 卖家（服务团队）
     * @param id       用户id
     * @param callback 回调
     */
    public static void getServiceOrderDetail(String uuid, String phone, String orderId, int userType, int id, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("orderId", orderId);
        params.put("longitude", "0");
        params.put("latitude", "0");
        params.put("userType", String.valueOf(userType));
        params.put("id", String.valueOf(id));
        OkHttpUtils.getInstace().post(Const.GET_SERVICE_ORDER_DETAIL, params, callback);
    }

    /**
     * 删除服务订单
     * version 1.1.0
     * author song
     *
     * @param uuid     uuid
     * @param phone    用户电话
     * @param id       id
     * @param oIds     订单id
     * @param type     删除类型  1 会员删除  2  服务团队删除
     * @param callback 回调
     */
    public static void deleteServiceOrder(String uuid, String phone, int id, String oIds, String type, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("id", String.valueOf(id));
        params.put("oIds", oIds);
        params.put("type", type);
        OkHttpUtils.getInstace().post(Const.DELETE_SERVICE_ORDER, params, callback);
    }

    /**
     * 取消服务订单
     * version 1.1.0
     * author song
     *
     * @param uuid     uuid
     * @param phone    用户电话
     * @param id       id
     * @param oIds     订单id
     * @param callback 回调
     */
    public static void cancelServiceOrder(String uuid, String phone, int id, String oIds, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("memberId", String.valueOf(id));
        params.put("orderId", oIds);
        OkHttpUtils.getInstace().post(Const.CANCEL_SERVICE_ORDER, params, callback);
    }

    /**
     * 评价服务订单
     * version 1.1.0
     * author song
     *
     * @param uuid         uuid
     * @param phone        用户电话
     * @param id           id
     * @param orderId      订单id
     * @param content      评价内容
     * @param startNum     评价星级
     * @param anonymity    是否匿名 0匿名 1不匿名
     * @param imageAddress 评价图片
     * @param callback     回调
     */
    public static void evaluateServiceOrder(String uuid, String phone, int id, String orderId, String content, String startNum
            , String anonymity, String imageAddress, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("id", String.valueOf(id));
        params.put("orderId", orderId);
        params.put("content", content);
        params.put("startNum", startNum);
        params.put("anonymity", anonymity);
        params.put("imageAddress", imageAddress);
        //source android 传 0
        params.put("source", "0");
        //订单类型 0商品  1服务 2卡联盟
        params.put("commentType", "1");
        OkHttpUtils.getInstace().post(Const.EVALUATE_SERVICE_ORDER, params, callback);
    }

    /**
     * 查看服务订单评价详情
     * version 1.1.0
     * author song
     *
     * @param uuid     uuid
     * @param phone    用户电话
     * @param orderId  订单id
     * @param callback 回调
     */
    public static void getServiceOrderEvaluateDetail(String uuid, String phone, String orderId, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("orderId", orderId);
        OkHttpUtils.getInstace().post(Const.GET_SERVICE_ORDER_EVALUATE_DETAIL, params, callback);
    }

    /**
     * 回复服务订单评价
     * version 1.1.0
     * author song
     *
     * @param uuid      uuid
     * @param phone     用户电话
     * @param memberId  登录人id
     * @param commentId 评论id
     * @param content   评论内容
     * @param callback  回调
     */
    public static void replyServiceOrderEvaluate(String uuid, String phone, int memberId, String commentId, String content, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("memberId", Integer.toString(memberId));
        params.put("commentId", commentId);
        params.put("content", content);
        OkHttpUtils.getInstace().post(Const.REPLY_SERVICE_ORDER_EVALUATE, params, callback);
    }

    /**
     * 申请退款
     * version 1.1.0
     * author song
     *
     * @param uuid     uuid
     * @param phone    用户电话
     * @param id       登录人id
     * @param orderId  订单id
     * @param refundId 退款原因
     * @param remark   备注
     * @param callback 回调
     */
    public static void applicationForRefund(String uuid, String phone, int id, String orderId, String refundId, String remark
            , String orderStatus, String status, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("id", Integer.toString(id));
        params.put("OrderId", orderId);
        params.put("refundId", refundId);
        params.put("remark", remark);
        params.put("orderStatus", orderStatus);
        params.put("status", status);
        OkHttpUtils.getInstace().post(Const.APPLICATION_FOR_REFUND, params, callback);
    }

    /**
     * 获取服务订单售后详情
     * version 1.1.0
     * author song
     *
     * @param uuid          uuid
     * @param phone         用户电话
     * @param returnOrderId 订单id
     * @param callback      回调
     */
    public static void getRefundDetail(String uuid, String phone, String returnOrderId, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("returnOrderId", returnOrderId);
        OkHttpUtils.getInstace().post(Const.GET_REFUND_DETAIL, params, callback);
    }

    /**
     * 确认退款
     * version 1.1.0
     * author song
     *
     * @param uuid      uuid
     * @param phone     用户电话
     * @param orderNums 订单编号
     * @param callback  回调
     */
    public static void confirmRefund(String uuid,String phone, String orderNums,AbstractNetWorkCallback callback){
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("orderNums", orderNums);
        OkHttpUtils.getInstace().post(Const.CONFIRM_REFUND, params, callback);
    }

    /**
     * 拒绝退款
     * version 1.1.0
     * author song
     *
     * @param uuid      uuid
     * @param phone     用户电话
     * @param proId     订单id
     * @param callback  回调
     */
    public static void refuseRefund(String uuid,String phone, String proId, String refuseReason,AbstractNetWorkCallback callback){
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("proId", proId);
        params.put("refuseReason", refuseReason);
        //1.商品订单  2.服务订单
        params.put("type", "2");
        OkHttpUtils.getInstace().post(Const.REFUSE_REFUND, params, callback);
    }

    //***************************************************陈晓伟****************************************************

    /**
     * 查看售后详情（普通会员）
     * version 1.0.1
     *
     * @param returnOrderId 退货订单id
     * @param callback      回调
     */
    public static void afterSalesDetail(String returnOrderId, AbstractNetWorkCallback<AfterSalesDetailContentBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("returnOrderId", returnOrderId);
        OkHttpUtils.getInstace().post(Const.AFTER_SALES_DETAIL_MEMBER, params, callback);
    }

    /**
     * 确认退款
     * version 1.0.1
     *
     * @param phone     猎头手机号
     * @param uuid      uuid
     * @param orderNums 多个订单号拼接（eg: 1,2,3）
     * @param callback  回调
     */
    public static void agreeReturnMoney(String phone, String uuid, String orderNums, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("orderNums", orderNums);
        OkHttpUtils.getInstace().post(Const.AGREE_RETURN_MONEY, params, callback);
    }

    /**
     * 拒绝退款
     * version 1.0.1
     *
     * @param proId        退货申请订单id
     * @param refuseReason 拒绝理由
     * @param type         1商品订单，2服务订单
     * @param callback     回调
     */
    public static void refuseReturnMoney(String phone, String uuid, String proId, String refuseReason, int type, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("proId", proId);
        params.put("refuseReason", refuseReason);
        params.put("type", String.valueOf(type));
        OkHttpUtils.getInstace().post(Const.REFUSE_RETURN_MONEY, params, callback);
    }

    /**
     * 确认发货
     * version 1.0.1
     *
     * @param phone              猎头账号
     * @param uuid               uuid
     * @param flag               1 无需发货 0 需要发货
     * @param orderNo            主订单号
     * @param logisticsCompanyId 物流公司id
     * @param logisticsNo        物流单号
     * @param callback           回调
     */
    public static void confirmSend(String phone, String uuid, int flag, String orderNo, String logisticsCompanyId, String logisticsNo, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("flag", String.valueOf(flag));
        params.put("orderNo", orderNo);
        if (flag == 0) {
            params.put("logisticsCompanyId", logisticsCompanyId);
            params.put("logisticsNo", logisticsNo);
        }
        OkHttpUtils.getInstace().post(Const.CONFIRM_SEND, params, callback);
    }

    /**
     * 获取售后原因列表
     * version 1.0.1
     *
     * @param phone    账号
     * @param uuid     uuid
     * @param callback 回调
     */
    public static void getAfterSalesReasonList(String phone, String uuid, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.AFTER_SALES_LIST_REASON, params, callback);
    }

    /**
     * 回复商品评论
     * version 1.0.1
     *
     * @param phone    账号
     * @param uuid     uuid
     * @param content  回复内容
     * @param parentId 回复的那条评论id
     * @param callback 回调
     */
    public static void replyEvaluate(String phone, String uuid, String content, String parentId, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("content", content);
        params.put("parentId", parentId);
        params.put("source", "0");
        //identity 回复人身份 0猎头 1普通用户
        params.put("identity", String.valueOf(0));
        OkHttpUtils.getInstace().post(Const.REPLY_EVALUATE, params, callback);
    }

    /**
     * 第三方绑定
     *
     * @param thirdPartyType        登录类型（1.QQ 2.微信）
     * @param phone                 手机号
     * @param logoAttachmentAddress 头像
     * @param mOppendId             qq唯一标识 微信唯一标识
     * @param mNickName             qq昵称 微信昵称
     * @param bindStatus            绑定状态 0 未绑定 1 QQ 2 微信 3 QQ +微信
     * @param smsCode               短信验证码
     * @param callback              回调
     */
    public static void qqOrWxLogin(String thirdPartyType, String phone, String logoAttachmentAddress, String mOppendId, String mNickName, String bindStatus, String smsCode, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("thirdPartyType", thirdPartyType);
        params.put("phone", phone);
        params.put("logoAttachmentAddress", logoAttachmentAddress);
        if (thirdPartyType.equals(String.valueOf(NO1))) {
            params.put("qqOppendId", mOppendId);
            params.put("qqNickName", mNickName);
        } else {
            params.put("wxOppendId", mOppendId);
            params.put("wxNickName", mNickName);
        }
        params.put("bindStatus", bindStatus);
        params.put("smsCode", smsCode);
        OkHttpUtils.getInstace().post(Const.QQORWX_LOGIN, params, callback);
    }

    /**
     * 确认收货
     * version 1.0.1
     *
     * @param phone    用户账号
     * @param uuid     uuid
     * @param orderNo  主订单号
     * @param callback 回调
     */
    public static void confirmReceive(String phone, String uuid, String orderNo, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("orderNo", orderNo);
        OkHttpUtils.getInstace().post(Const.CONFIRM_RECEIVE, params, callback);
    }

    /**
     * 确认退货
     * version 1.0.1
     *
     * @param phone          猎头账号
     * @param uuid           uuid
     * @param returnOrderId  退款单号
     * @param receiveAddress 收货地址
     * @param receiveName    收货人
     * @param receivePhone   收货手机号
     * @param callback       回调
     */
    public static void agreeReturnGoods(String phone, String uuid, String returnOrderId, String receiveAddress, String receiveName, String receivePhone, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("returnOrderId", returnOrderId);
        params.put("receiveAddress", receiveAddress);
        params.put("receiveName", receiveName);
        params.put("receivePhone", receivePhone);
        OkHttpUtils.getInstace().post(Const.AGREE_RETURN_GOODS, params, callback);
    }

    /**
     * 申请退款\退货
     * version 1.0.1
     *
     * @param phone           用户账号
     * @param uuid            uuid
     * @param orderId         订单id
     * @param returnOrderType 0退货退款 1仅退款
     * @param returnReasonId  退货原因id
     * @param remark          退款说明
     * @param imgAddress      图片地址 逗号拼接
     * @param orderStatus     选择订单状态 0 已完成 1 未完成
     * @param num             退款个数
     * @param callback        回调
     */
    public static void applyAfterSales(String phone, String uuid, String orderId, int returnOrderType, String returnReasonId, String remark, String imgAddress, int orderStatus, int num, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("orderId", orderId);
        params.put("returnOrderType", String.valueOf(returnOrderType));
        params.put("returnReasonId", returnReasonId);
        params.put("remark", remark);
        params.put("imgAddress", imgAddress);
        params.put("orderStatus", String.valueOf(orderStatus));
        params.put("num", String.valueOf(num));
        OkHttpUtils.getInstace().post(Const.APPLY_AFTER_SALES, params, callback);
    }

    /**
     * 三方支付
     * version 1.0.1
     *
     * @param phone     用户手机号
     * @param uuid      uuid
     * @param orderNums 多个订单号拼接（eg: 1,2,3）
     * @param type      2微信 3 支付宝
     * @param ip        发起支付的终端ip
     * @param callback  回调
     */
    public static void pay(String phone, String uuid, String orderNums, int type, String ip, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("orderNums", orderNums);
        params.put("type", String.valueOf(type));
        params.put("ip", ip);
        OkHttpUtils.getInstace().post(Const.PAY, params, callback);
    }

    /**
     * 通过手机号获取第三方登录信息
     *
     * @param phone    手机号
     * @param callback 回调
     */
    public static void getSanFangBindState(String phone, AbstractNetWorkCallback<SanFangStateBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.BYPHONE_SANFANG_LOGIN_MESSAGE, params, callback);
    }

    /**
     * 通过openId获取是否绑定手机号
     *
     * @param openId    id
     * @param loginType 登录方式
     * @param callback  回调
     */
    public static void getIsBindPhone(String openId, int loginType, AbstractNetWorkCallback<SanFangStateBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("openId", openId);
        params.put("loginType", String.valueOf(loginType));
        OkHttpUtils.getInstace().post(Const.BYPOPENID_ISBINDPHONE, params, callback);
    }

    /**
     * 切换服务专员
     *
     * @param phone    phone
     * @param uuid     uuid
     * @param hunterId 专员id
     * @param callback 回调
     */
    public static void changeSpeciallyPerson(String phone, String uuid, String hunterId, AbstractNetWorkCallback<ChangeSpeciallyPersonBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("hunterId", hunterId);
        OkHttpUtils.getInstace().post(Const.CHANGE_ATTACHE_SHOW_MESSAGE, params, callback);
    }

    /**
     * 回复商品评论
     *
     * @param phone    phone
     * @param uuid     uuid
     * @param content  回复内容
     * @param parentId 父评论id
     * @param identity (回复人身份 0猎头 1普通用户)
     * @param source   客户端
     * @param callback 回调
     */
    public static void replyComment(String phone, String uuid, String content, String parentId, String identity, String source, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("content", content);
        params.put("parentId", parentId);
        params.put("identity", identity);
        params.put("source", source);
        OkHttpUtils.getInstace().post(Const.REPLY_COMMENT, params, callback);
    }

    /**
     * 猎头店铺   向我提问
     *
     * @param phone      phone
     * @param uuid       uuid
     * @param content    提问内容
     * @param merchantId 猎头id
     * @param callback   回调
     */
    public static void askMe(String phone, String uuid, String content, String merchantId, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("content", content);
        params.put("merchantId", merchantId);
        OkHttpUtils.getInstace().post(Const.ASK_ME, params, callback);
    }

    /**
     * 猎头店铺   向我提问回复
     *
     * @param phone    phone
     * @param uuid     uuid
     * @param content  提问内容
     * @param quizId   提问或者评论的id
     * @param callback 回调
     */
    public static void replyAskMe(String phone, String uuid, String content, String quizId, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("content", content);
        params.put("quizId", quizId);
        OkHttpUtils.getInstace().post(Const.REPLY_ASK_ME, params, callback);
    }

    public static void getCashDetails(String phone, String uuid, String id, AbstractNetWorkCallback<CashDetailsBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("id", id);
        OkHttpUtils.getInstace().post(Const.CASH_DETAILS, params, callback);
    }

    /**
     * 消息的阅读状态
     *
     * @param phone    phone
     * @param uuid     uuid
     * @param callback 回调
     */
    public static void getMessageState(String phone, String uuid, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.INTERACT, params, callback);
    }

    /**
     * 商品的一级分类
     *
     * @param callback 回调
     */
    public static void getOneList(AbstractNetWorkCallback<CommodityStairClassifyBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        OkHttpUtils.getInstace().post(Const.STAIR_CLASSIFT, params, callback);
    }

    /**
     * 商品的二级分类
     *
     * @param callback 回调
     */
    public static void getTwoList(String parentId, AbstractNetWorkCallback<CommodityTwoClassifyBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("parentId", parentId);
        OkHttpUtils.getInstace().post(Const.TWO_CLASSIFT, params, callback);
    }

    /**
     * 修改项目计划
     *
     * @param phone          phone
     * @param cover          封面图地址
     * @param title          标题
     * @param describes      正文
     * @param address        城市
     * @param longitude      经度
     * @param latitude       纬度
     * @param uuid           uuid
     * @param planClassifyId 项目分类ID
     * @param id             项目计划ID
     * @author jiaofan
     * @version 1.0.1
     */
    public static void updateProjectPlan(String phone, String uuid, String cover, String title, String describes, String address, String longitude, String latitude, String id, String planClassifyId, AbstractNetWorkCallback<UpdatePlanBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("cover", cover);
        params.put("title", title);
        params.put("describes", describes);
        params.put("address", address);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("id", id);
        params.put("planClassifyId", planClassifyId);
        OkHttpUtils.getInstace().post(Const.UPDATE_PROJECT_PLAN, params, callback);
    }

    /**
     * 获取首页轮播图
     *
     * @param callback 回调
     */
    public static void getBanner(AbstractNetWorkCallback<BannerContentBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        OkHttpUtils.getInstace().post(Const.HOME_BANNER, params, callback);
    }

    /**
     * 获取首页课题分类
     *
     * @param callback 回调
     */
    public static void getHomeClassify(AbstractNetWorkCallback<ProjectClassifyContentBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        OkHttpUtils.getInstace().post(Const.HOME_PROJECT_CLASSIFY, params, callback);
    }

    /**
     * 签到
     *
     * @param id
     * @param uuid     uuid
     * @param phone    手机号
     * @param callback
     */
    public static void signin(String id, String uuid, String phone, AbstractNetWorkCallback<SignInBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("id", id);
        OkHttpUtils.getInstace().post(Const.SIGNIN, params, callback);
    }

    /**
     * 个人中心
     *
     * @param id
     * @param callback
     */
    public static void getPersonalCenter(String id, String uuid, String phone, AbstractNetWorkCallback<PersonalCenterBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.PERSONAL_CENTER, params, callback);
    }

    /**
     * 服务团队轮播图
     *
     * @param callback 回调
     * @author chenjiadi
     * @version 1.1.0
     */
    public static void getServerTeamBanner(AbstractNetWorkCallback<ServerTeamBannerBean> callback) {
        OkHttpUtils.getInstace().post(Const.SERVER_TEAM_BANNER, null, callback);
    }

    /**
     * 服务团队分类下拉列表
     *
     * @param callback 回调
     * @author chenjiadi
     * @version 1.1.0
     */
    public static void getServerTeamClassifyDropList(AbstractNetWorkCallback<ServerTeamClassifyDropListBean> callback) {
        OkHttpUtils.getInstace().post(Const.SERVER_TEAM_CLASSIFY_DROP, null, callback);
    }

    /**
     * 服务团队列表
     *
     * @param typeId     团队分类id
     * @param low        距离
     * @param numberType 智能排序类型 1 离我最近 2 好评优先
     * @param longitude  经度
     * @param latitude   维度
     * @param page       分页开始页数
     * @param rows       分页显示条数
     * @param callback   回调
     * @author chenjiadi
     * @version 1.1.0
     */
    public static void getServerTeamList(String typeId, String low, String numberType, String longitude, String latitude, int page, int rows, AbstractNetWorkCallback<ServerTeamListBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        if (!TextUtils.isEmpty(typeId)) {
            params.put("typeId", typeId);
        }
        if (!TextUtils.isEmpty(low)) {
            params.put("low", low);
        }
        if (!TextUtils.isEmpty(numberType)) {
            params.put("numberType", numberType);
        } else {
            params.put("numberType", "0");
        }
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("page", String.valueOf(page));
        params.put("rows", String.valueOf(rows));
        OkHttpUtils.getInstace().post(Const.SERVER_TEAM_LIST, params, callback);
    }

    /**
     * 根据课题分类id获取课题
     *
     * @param topicClassifyId 课题分类id
     * @param page            页数
     * @param callback        回调
     */
    public static void getProjectList(String topicClassifyId, int page, AbstractNetWorkCallback<HomeProjectContentBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("page", String.valueOf(page));
        params.put("rows", String.valueOf(10));
        params.put("topicClassifyId", topicClassifyId);
        OkHttpUtils.getInstace().post(Const.HOME_LIST_BY_CLASSIFY, params, callback);
    }

    /**
     * 我的收藏服务项目
     *
     * @param id
     * @param callback
     */
    public static void getServicProjectList(String page, String rows, String id, String uuid, String phone, AbstractNetWorkCallback<ServeProjectBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("rows", rows);
        params.put("page", page);
        OkHttpUtils.getInstace().post(Const.COLLECT_SERVICPROJECT, params, callback);
    }

    /**
     * 我的收藏分享
     *
     * @param page
     * @param rows
     * @param uuid
     * @param phone
     * @param callback
     */
    public static void getCollectShare(String page, String rows, String uuid, String phone, AbstractNetWorkCallback<MyCollectShareBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("rows", rows);
        params.put("page", page);
        OkHttpUtils.getInstace().post(Const.COLLECT_SHARE, params, callback);
    }

    /**
     * 案例管理列表
     *
     * @param phone     手机号
     * @param uuid      uuid
     * @param page      页面
     * @param rows      条数
     * @param callback  回调
     */
    public static void caseManagerList(String phone, String uuid, int page,String rows, AbstractNetWorkCallback<CaseBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("page", String.valueOf(page));
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.CASE_MANAGER_LIST, params, callback);
    }

    /**
     * 案例管理删除
     *
     * @param caseIds  案例id 字符串拼接
     * @param callback 回调
     */
    public static void deleteCaseManager(String phone, String uuid,String caseIds, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("caseIds", caseIds);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.DELETE_CASE_MANAGER, params, callback);
    }

    /**
     * 扫二维码的验证
     *
     * @param orderId 订单id
     */
    public static void scanErCode(String orderId, String code, String phone, String uuid, AbstractNetWorkCallback<EditCodeBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("orderId", orderId);
        params.put("code", code);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.SCAN_ER_CODE, params, callback);
    }


    /**
     * 商品评价
     *
     * @param serviceId serviceId
     * @param phone     手机号
     * @param uuid      uuid
     * @param type      0列表 1未回复 2中差评 3好评
     */
    public static void goodsComment(String serviceId, String phone, String uuid, String type, AbstractNetWorkCallback<GoodsCommentBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("serviceId", serviceId);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("type", type);
        OkHttpUtils.getInstace().post(Const.GOODS_COMMENT, params, callback);
    }

    /**
     * 服务列表
     *
     * @param memberId 登录人id
     * @param flag     操作标识 1.上架中，2.待上架，3已下架
     * @param page     当前第几页
     * @param rows     每页显示几条
     */
    public static void getServiceList(String memberId, String flag, String page, String rows, AbstractNetWorkCallback<ServerManageBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("memberId", memberId);
        params.put("flag", flag);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.SERVICE_LIST, params, callback);
    }

    /**
     * 课题列表
     *
     * @param hunterId 经纪人id
     * @param page     页数
     * @param callback 回调
     */
    public static void getTopicList(String hunterId, String page, AbstractNetWorkCallback<AgentListBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("hunterId", hunterId);
        params.put("page", page);
        OkHttpUtils.getInstace().post(Const.GET_TOPIC_LIST, params, callback);
    }

    /**
     * 我的卡包列表
     *
     * @param uuid
     * @param phone
     * @param callback
     */
    public static void getCardBagList(String uuid, String phone, AbstractNetWorkCallback<MyCardBagListBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.MINE_CARDBAG, params, callback);
    }

    /**
     * 我的卡包详情
     *
     * @param serviceOrderId
     * @param uuid
     * @param phone
     * @param callback
     */
    public static void getCardBagDetail(String serviceOrderId, String uuid, String phone, AbstractNetWorkCallback<MyCardBagDetailBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("serviceOrderId", serviceOrderId);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.MINE_CARDBAG_DETAIL, params, callback);
    }

    /**
     * 删除课题
     *
     * @param hunterId 经纪人id
     * @param topicIds 课题id
     * @param callback 回调
     */
    public static void deleteTopic(String hunterId, String topicIds, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("hunterId", hunterId);
        params.put("topicIds", topicIds);
        OkHttpUtils.getInstace().post(Const.DELETE_TOPIC, params, callback);
    }

    /**
     * 获取 推荐列表
     *
     * @param status   登录状态
     * @param type     类型
     * @param memberId 用户id
     * @param page     页码
     * @param callback callback
     */

    public static void getRecommendedList(String status, String type, String memberId, int page, AbstractNetWorkCallback<RecommendedBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("status", status);
        params.put("type", type);
        params.put("memberId", memberId);
        params.put("page", String.valueOf(page));
        params.put("rows", "10");
        OkHttpUtils.getInstace().post(Const.RECOMMENDED_LIST, params, callback);
    }

    /**
     * 获取关注列表
     *
     * @param memberId 用户id
     * @param type     类型
     * @param page     页面
     * @param callback callback
     */
    public static void getAttentionList(String memberId, int type, int page, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("type", String.valueOf(type));
        params.put("memberId", memberId);
        params.put("page", String.valueOf(page));
        params.put("rows", "10");

        OkHttpUtils.getInstace().post(Const.ATTENTION_LIST, params, callback);
    }

    /**
     * 获取行业分类
     *
     * @param callback
     */
    public static void getIndustrySort(AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        OkHttpUtils.getInstace().post(Const.INDUSTRY_SORT, params, callback);
    }

    /**
     * 通过行业id 获取服务列表
     *
     * @param memberId
     * @param serviceTypeId
     * @param page
     * @param callback
     */
    public static void getServerProjectByIndustrySortId(String memberId, String serviceTypeId, int page, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("type", "1");
        params.put("serviceTypeId", serviceTypeId);
        params.put("memberId", memberId);
        params.put("page", String.valueOf(page));
        params.put("rows", "10");
        OkHttpUtils.getInstace().post(Const.GET_SERVER_PROJECT_LIST_BY_INDUSTRY_SORT, params, callback);

    }

    /**
     * 获取 卡联盟消费列表
     *
     * @param phone      phone
     * @param uuid       uuid
     * @param merchantId merchantId
     * @param callback   callback
     */
    public static void getCardTypeList(String phone, String uuid, String merchantId,String serviceOrderId, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("merchantId", merchantId);
        params.put("serviceOrderId", serviceOrderId);
        OkHttpUtils.getInstace().post(Const.GET_CARD_TYPE_LIST, params, callback);

    }


    /**
     * 获取 卡联盟消费详情
     *
     * @param phone          phone
     * @param uuid           uuid
     * @param serviceOrderId id
     * @param callback       callback
     */
    public static void getClubCardDetail(String phone, String uuid, String serviceOrderId, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("serviceOrderId", serviceOrderId);
        OkHttpUtils.getInstace().post(Const.GET_CLUB_CARDD_ETAIL, params, callback);
    }

    /**
     * 获取卡联盟订单列表
     *
     * @param phone    手机号
     * @param uuid     uuid
     * @param memberId 用户id
     * @param status   订单状态
     * @param page     页码
     * @param callback callback
     */
    public static void getCardUnionOrderList(String phone, String uuid, String memberId, String status, int page, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("id", memberId);
        params.put("status", TextUtils.isEmpty(status) ? "" : String.valueOf(status));
        params.put("page", String.valueOf(page));
        params.put("rows", "10");

        OkHttpUtils.getInstace().post(Const.CARD_UNION_ORDER_LIST, params, callback);
    }

    /**
     * 删除 卡联盟订单
     *
     * @param memberId 用户id
     * @param orderId  订单id
     * @param type  0 会员删除  1 服务团队删除
     * @param callback callback
     */
    public static void deleteCardUnionOrder(String memberId, String orderId,String type, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", App.manager.getPhoneNum());
        params.put("uuid", App.manager.getUuid());
        params.put("type",type);
        params.put("id", memberId);
        params.put("oIds", orderId);

        OkHttpUtils.getInstace().post(Const.DELETE_CARD_UNION_ORDER, params, callback);
    }

    /**
     * 删除 卡联盟订单
     *
     * @param phone    手机号
     * @param orderId  订单id
     * @param uuid     uuid
     * @param callback callback
     */
    public static void cancelCardUnionOrder(String phone, String uuid, String orderId, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("serviceOrderId", orderId);

        OkHttpUtils.getInstace().post(Const.CANCEL_CARD_UNION_ORDER, params, callback);
    }


    /**
     * 显示出后台添加的热门搜
     *
     * @param callback
     */
    public static void getHotSeachKeyWord(AbstractNetWorkCallback<SearchEntity> callback) {
        Map<String, Object> params = new HashMap<>(16);
        OkHttpUtils.getInstace().post(Const.HOT_SEARCH_KEYWORD, params, callback);
    }

    /**
     * 优惠券信息查询
     *
     * @param callback
     */
    public static void getCoupon(AbstractNetWorkCallback<CouponBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        OkHttpUtils.getInstace().post(Const.GET_COUPON, params, callback);
    }

    /**
     * 会员登陆领取优惠券
     *
     * @param callback
     */
    public static void receiveCoupon(String couponId, String uuid, String phone, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("couponId", couponId);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.RECEIVE_COUPON, params, callback);
    }

    /**
     * 是否领取过新人红包
     *
     * @param uuid
     * @param phone
     * @param callback
     */
    public static void getMemberIsReceiveCoupon(String uuid, String phone, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        OkHttpUtils.getInstace().post(Const.GET_MEMBER_IS_RECEIVE_COUPON, params, callback);

    }

    /**
     * 搜索项目
     *
     * @param memberId
     * @param page
     * @param callback
     */
    public static void getServerProjectBySearch(String type, String memberId, String title, int page, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("memberId", memberId);
        params.put("type", type);
        params.put("serviceTypeId", "");
        params.put("title", title);
        params.put("page", String.valueOf(page));
        params.put("rows", "10");
        OkHttpUtils.getInstace().post(Const.GET_SERVER_PROJECT_LIST_BY_INDUSTRY_SORT, params, callback);
    }

    /**
     * 根据标题搜索资讯
     *
     * @param title    搜索输入关键字
     * @param page     页数
     * @param callback 回调
     */
    public static void getConsultationByTitle(String title, String page, AbstractNetWorkCallback<ByIdBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("title", title);
        params.put("page", page);
        params.put("rows", "5");
        OkHttpUtils.getInstace().post(Const.GET_CONSULTATION_BY_TITLE, params, callback);
    }

    /**
     * 根据分类展示列表
     *
     * @param id       分类id
     * @param page     页数
     * @param callback 回调
     */
    public static void getConsultation(String id, String page, AbstractNetWorkCallback<ByIdBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("consultationClassifyId", id);
        params.put("type", "0");
        params.put("page", page);
        params.put("rows", "5");
        OkHttpUtils.getInstace().post(Const.GET_CONSULTATION, params, callback);
    }

    /**
     * 获取课题分类
     *
     * @param callback 回调
     */
    public static void getClassify(AbstractNetWorkCallback<ClassifyBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        OkHttpUtils.getInstace().post(Const.HOME_PROJECT_CLASSIFY, params, callback);
    }

    /**
     * 批量管理服务
     *
     * @param type        1.下架 2.上架 3.删除
     * @param serviceIds  服务id字符串
     * @param callback    回调
     */
    public static void serviceOperate(String type, String serviceIds, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("type", type);
        params.put("serviceIds", serviceIds);
        OkHttpUtils.getInstace().post(Const.OPERATE_SERVICE, params, callback);
    }

    /**
     * 根据资讯id获取回显列表数据
     * @param consultationIds 资讯id
     * @param callback 回调
     */
    public static void byIdRequestData(String consultationIds, AbstractNetWorkCallback<PublishCourseBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("consultationIds", consultationIds);
        OkHttpUtils.getInstace().post(Const.BY_ID_REQUEST_DATA, params, callback);
    }

    /**
     * 创建服务团队完成
     *
     * @param memberId          登录人id
     * @param nikeName          服务团队名称
     * @param content           服务团队介绍
     * @param headImg           服务团队头像
     * @param serviceCategoryId 服务分类id 关联一级分类
     * @param address           详细地址
     * @param idCardImg1        身份证图片正面
     * @param idCardImg2        身份证图片反面
     * @param teamImg           服务团队图片
     * @param licenseImg        营业执照图片
     * @param cityId            市id
     * @param provinceId        省id
     * @param countyd           县id
     * @param longitude         经度
     * @param latitude          纬度
     */
    public static void completeCreateTeam(String memberId, String nikeName, String content, String headImg, String serviceCategoryId, String address, String idCardImg1, String idCardImg2, String teamImg, String licenseImg, String cityId, String provinceId, String countyd, String longitude, String latitude, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("memberId", memberId);
        params.put("nikeName", nikeName);
        params.put("content", content);
        params.put("headImg", headImg);
        params.put("serviceCategoryId", serviceCategoryId);
        params.put("address", address);
        params.put("idCardImg1", idCardImg1);
        params.put("idCardImg2", idCardImg2);
        params.put("teamImg", teamImg);
        params.put("licenseImg", licenseImg);
        params.put("cityId", cityId);
        params.put("provinceId", provinceId);
        params.put("countyd", countyd);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        OkHttpUtils.getInstace().post(Const.COMPLETE_CREATE_SERVICE, params, callback);
    }

    /**
     * 商品列
     *
     * @param phone    登录
     * @param uuid     登录手机号
     * @param type     登录手机号
     * @param page     分页开始页
     * @param rows     每页条数
     * @param callback
     */
    public static void getGoodsList(String phone, String uuid, String type, String page, String rows, AbstractNetWorkCallback<GoodsBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("type", type);
        params.put("page", page);
        params.put("rows", rows);
        OkHttpUtils.getInstace().post(Const.GET_GOODS_LIST, params, callback);
    }

    /**
     * 搜索分享
     *
     * @param memberId
     * @param page
     * @param callback
     */
    public static void getconsultationbytitle(String memberId, String title, int page, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("memberId", memberId);
        params.put("title", title);
        params.put("page", String.valueOf(page));
        params.put("rows", "10");
        OkHttpUtils.getInstace().post(Const.GET_CONSULTATION_BY_TITLE, params, callback);
    }

    /**
     * 发布课题
     *
     * @param hunterId 用户id
     * @param imageAddress 封面
     * @param title 标题
     * @param content 简介
     * @param topicClassifyId 分类id
     * @param consultationIds 资讯id
     * @param callback 回调
     */
    public static void publishCourse(String hunterId, String imageAddress, String title, String content, String topicClassifyId, String consultationIds, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("hunterId", hunterId);
        params.put("imageAddress", imageAddress);
        params.put("title", title);
        params.put("content", content);
        params.put("topicClassifyId", topicClassifyId);
        params.put("consultationIds", consultationIds);
        OkHttpUtils.getInstace().post(Const.PUBLISH_COURSE, params, callback);
    }

    /**
     * 获取是否有未读消息
     *
     * @param phone    手机号
     * @param uuid     uuid
     * @param callback 回调
     */
    public static void getMessage(String phone, String uuid, AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.GET_MESSAGE, params, callback);
    }

    /**
     * 商品评价
     *
     * @param phone 手机号
     * @param uuid  uuid
     * @param type  0列表 1未回复 2中差评 3好评
     */
    public static void goodsComment(String phone, String uuid, String type, AbstractNetWorkCallback<GoodsCommentBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("type", type);
        OkHttpUtils.getInstace().post(Const.GOODS_COMMENT, params, callback);
    }


    /**
     * 修改团队信息
     *
     * @param advisorId 团队id
     * @param phone     手机号
     * @param uuid      uuid
     */
    public static void updateTeamInfo(String advisorId, String phone, String uuid, AbstractNetWorkCallback<TeamInfoBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("advisorId", advisorId);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.UPDATE_TEAM_INFO, params, callback);
    }

    /**
     * 输入验证码
     *
     * @param phone   手机号
     * @param uuid    uuid
     * @param code    验证码
     */
    public static void yanZhengCode(String phone, String uuid, String code, AbstractNetWorkCallback<EditCodeBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("code", code);
        OkHttpUtils.getInstace().post(Const.EDIT_YANZHEN_CODE, params, callback);
    }

    /**
     * 服务评价
     *
     * @param phone 手机号
     * @param uuid  uuid
     * @param type  0全部 1未回复 2中差评 3好评
     */
    public static void serviceComment(String phone, String uuid, String type, AbstractNetWorkCallback<ServiceCommentBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("type", type);
        OkHttpUtils.getInstace().post(Const.SERVICE_COMMENT, params, callback);
    }

    /**
     * 获取团队信息
     *
     * @param advisorId 服务团队id
     * @param phone     phone
     * @param uuid      uuid
     */
    public static void getTeamInfo(String advisorId, String phone, String uuid, AbstractNetWorkCallback<TeamInfoBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("advisorId", advisorId);
        params.put("phone", phone);
        params.put("uuid", uuid);
        OkHttpUtils.getInstace().post(Const.GET_TEAM_INFO, params, callback);
    }

    /**
     * 修改团队信息
     *
     * @param advisorId  服务团队id
     * @param imgUrl     头像
     * @param nikeName   昵称
     * @param address    地址
     * @param phone      电话
     * @param content    简介
     * @param status     经营状态
     * @param cityId     市id
     * @param provinceId 省id
     * @param countyId   区id
     */
    public static void updateTeamInfo(String advisorId, String uuid, String imgUrl,
                                      String nikeName, String address,
                                      String phone, String telPhone,String content,
                                      String status, String cityId,
                                      String provinceId, String countyId,
                                      AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("advisorId", advisorId);
        params.put("imgUrl", imgUrl);
        params.put("uuid", uuid);
        params.put("nikeName", nikeName);
        params.put("address", address);
        params.put("content", content);
        params.put("status", status);
        params.put("countyId", countyId);
        params.put("phone", phone);
        params.put("telPhone", telPhone);
        params.put("cityId", cityId);
        params.put("provinceId", provinceId);
        OkHttpUtils.getInstace().post(Const.UPDATE_TEAM_INFO, params, callback);
    }

    /**
     * 回复评论
     *
     * @param id                用户Id
     * @param commentId         父评论id
     * @param content           回复评论内容
     * @param operationId       被操作人id
     * @param objectName        父评论名
     * @param operationObjectId 被操作对象id
     * @param serviceId         服务id
     */
    public static void replayComment(String id, String commentId,
                                     String content,
                                     String operationId, String objectName,
                                     String operationObjectId, String serviceId,String phone,String uuid,
                                     AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("id", id);
        params.put("commentId", commentId);
        params.put("content", content);
        params.put("operationId", operationId);
        params.put("objectName", objectName);
        params.put("operationObjectId", operationObjectId);
        params.put("serviceId", serviceId);
        params.put("phone",phone);
        params.put("uuid",uuid);
        OkHttpUtils.getInstace().post(Const.REPLAY_COMMENT, params, callback);
    }



    /**
     * 获取创建服务分类列表
     *
     * @param merchantId 用户的id
     * @param callback 回调
     */
    public static void getServiceClassifyList(String merchantId, AbstractNetWorkCallback<ServiceClassifyBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("merchantId", merchantId);
        OkHttpUtils.getInstace().post(Const.SERVICE_CLASSIFY_LIST, params, callback);
    }

    /**
     * 创建服务
     *
     * @param log 服务封面
     * @param serviceType 服务类型 0.到店 1.到家
     * @param serviceTypeId 服务分类id
     * @param title 服务名称
     * @param price 服务价格
     * @param status 上架状态 0.下架 1.上架
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param merchantId 创建人id
     * @param content 服务描述
     * @param callback 回调
     */
    public static void toCreateService(String log,String images,String serviceType,String serviceTypeId,String title,String price,
                                       String status,String startTime,String endTime,String merchantId,String content,String productId,int type,String serviceId,
                                       AbstractNetWorkCallback<MsgBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("log", log);
        params.put("merchantId", String.valueOf(App.manager.getId()));
        params.put("serviceType", serviceType);
        params.put("images", images);
        params.put("serviceTypeId", serviceTypeId);
        params.put("title", title);
        params.put("price", price);
        params.put("status", status);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("content", content);
        params.put("productIds", productId);
        if (type==NO1){
            OkHttpUtils.getInstace().post(Const.CREATE_SERVICE, params, callback);
        }else {
            params.put("serviceId", serviceId);
            OkHttpUtils.getInstace().post(Const.EDIT_SERVICE, params, callback);
        }

    }

    /**
     *  获取服务数据 回显
     * @param serviceId 服务ID
     * @param callback 回调
     */
    public static void getServiceData(String serviceId, AbstractNetWorkCallback<ServiceDataBean> callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("serviceId", serviceId);
        OkHttpUtils.getInstace().post(Const.GET_SERVICE_DATA, params, callback);
    }

    /**
     * 获取资讯评论回复列表
     * @param phone
     * @param uuid
     * @param commentId
     * @param consultationId
     * @param callback
     */
    public static void getConsultationReplyCommentMessage(String phone, String uuid,String commentId,String consultationId,AbstractNetWorkCallback callback){
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("commentId", commentId);
        params.put("consultationId", consultationId);

        OkHttpUtils.getInstace().post(Const.GET_CONSULTATION_REPLY_COMMENT_MESSAGE, params, callback);
    }

    /**
     * 给资讯评论进行回复
     * @param phone
     * @param uuid
     * @param commentId
     * @param content
     * @param callback
     */
    public static void replyConsultationComment(String phone, String uuid,String commentId,String content,AbstractNetWorkCallback callback){
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("parent", commentId);
        params.put("content", content);

        OkHttpUtils.getInstace().post(Const.REPLY_CONSULTATION_COMMENT, params, callback);

    }

    /**
     * 卡联盟评价商品
     *
     * @param phone          手机号
     * @param uuid           uuid
     * @param serviceOrderId 订单Id
     * @param memberId       用户Id
     * @param startNum       星星数
     * @param content        评论内容
     * @param anonymity      是否匿名 0是 1否
     * @param isPicture      是否有图 0没有 1 有
     * @param commentImgUrl  评论图片 （根据isPicture 选填）
     * @param callback       回调
     */
    public static void addCardEvaluate(String phone, String uuid, String serviceOrderId, String memberId, int startNum, String content, int anonymity, int isPicture, String commentImgUrl, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("phone", phone);
        params.put("uuid", uuid);
        params.put("serviceOrderId", serviceOrderId);
        params.put("memberId", memberId);
        params.put("startNum", String.valueOf(startNum));
        params.put("content", content);
        params.put("anonymity", String.valueOf(anonymity));
        params.put("isPicture", String.valueOf(isPicture));
        params.put("commentImgUrl", commentImgUrl);
        params.put("source", String.valueOf(0));
        OkHttpUtils.getInstace().post(Const.CARD_ORDER_COMMENT, params, callback);
    }

    /**
     *  获取卡联盟订单详情
     * @param uuid
     * @param phone
     * @param orderId
     * @param orderType
     * @param userType
     * @param id
     * @param callback
     */
    public static void getCardUnionOrderDetail(String uuid, String phone, String orderId, int orderType,int userType, int id, AbstractNetWorkCallback callback) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("uuid", uuid);
        params.put("phone", phone);
        params.put("orderId", orderId);
        params.put("orderType", String.valueOf(orderType));
        params.put("userType", String.valueOf(userType));
        params.put("id", String.valueOf(id));
        OkHttpUtils.getInstace().post(Const.GET_SERVICE_ORDER_DETAIL, params, callback);
    }
}