package com.yst.onecity;

/**
 * 接口常量类、配置类
 *
 * @author lixiangchao
 * @version 1.0.1
 * @date 2018/2/1
 */
public class Const {

    /**
     * 服务器地址（正式环境）
     */
    private static final String URL_ROOT = "http://mobile.pujiyicheng.com/mobile/";
    /**
     * 服务器地址（伪正式环境）
     */
//    private static final String URL_ROOT = "http://113.209.232.110:10001/mobile/";

    /**
     * 赵宇本地
     */
//    private static final String URL_ROOT = "http://192.168.30.203:10001/mobile/";
    /**
     * 赵禹本地
     */
//    private static final String URL_ROOT = "http://192.168.30.203:10001/mobile/";
    /**
     * 李雨本地
     */
//    private static final String URL_ROOT = "http://192.168.30.231:10001/mobile/";
    /**
     * 曹庆飞本地
     */
//    private static final String URL_ROOT = "http://192.168.30.238:10001/mobile/";

    /**
     * 服务器地址（测试环境）
     */
//    private static final String URL_ROOT = "http://192.168.50.248:10001/mobile/";
    /**
     * 修改群聊名称
     */
    public static final String UPDATE_CHAT_GROUP_NAME = URL_ROOT + "after/immember/updateChatGroup";
    /**
     * 商品库列表
     */
    public static final String SERVER_CENTER_PRODUCT_LIST = URL_ROOT + "view/solr/homeProduc";
    /**
     * 账号安全
     */
    public static final String ACCOUTN_SAFE = URL_ROOT + "after/member/getThirdName";
    /**
     * 第三方账号绑定
     */
    public static final String BIND_THIRD_ACCOUNT = URL_ROOT + "view/member/bindThirdParty";
    /**
     * 解除第三方账号绑定
     */
    public static final String REMOVE_BIND_THIRD_ACCOUNT = URL_ROOT + "after/member/removeBinding";
    /**
     * 实名认证发送短信验证码
     */
    public static final String REAL_NAME_AUTHENTICATION_SEND_VERIFY_CODE = URL_ROOT + "view/bankcard/sendsms";
    /**
     * 设置交易密码获取短信验证码
     */
    public static final String GET_VERIFY_CODE_FOR_SET_TRADE_PWD = URL_ROOT + "view/transactionpassword/sendsmstransactionpassword";
    /**
     * 实名认证
     */
    public static final String REAL_NAME_AUTHENTICATION = URL_ROOT + "after/bankcard/checkcardname";
    /**
     * 实名认证信息回显
     */
    public static final String REAL_NAME_AUTHENTICATION_INFO = URL_ROOT + "after/bankcard/getmembercardname";
    /**
     * 实名认证信息回显
     */
    public static final String SET_TRADE_PWD = URL_ROOT + "after/transactionpassword/setuptransactionpassword";
    /**
     * 我的粉丝列表
     */
    public static final String MY_FANS_LIST = URL_ROOT + "after/memberfollow/getMyIntegralDetails";
    /**
     * 我的关注列表
     */
    public static final String MY_ATTENTION_LIST = URL_ROOT + "after/memberfollow/getMemberFollowList";
    /**
     * 登录
     */
    public static final String LOGIN = URL_ROOT + "view/member/member-login";
    /**
     * 注册
     */
    public static final String REGISTER = URL_ROOT + "view/member/member-register";
    /**
     * 注册发送短信验证码
     */
    public static final String REGISTER_OR_LOGIN_SEND_VERIFY_CODE = URL_ROOT + "view/member/sendSms";
    /**
     * 忘记密码
     */
    public static final String FORGET_PSW = URL_ROOT + "view/member/forgetPassword";
    /**
     * 解绑银行卡发送验证码
     */
    public static final String BANK_PSW = URL_ROOT + "view/bankcard/sendsmdunbind";
    /**
     * 获取银行卡信息列表
     */
    public static final String BANK_LIST = URL_ROOT + "after/bankcard/getmemberbankcard";
    /**
     * 解绑银行卡F
     */
    public static final String UNBANK_BANK = URL_ROOT + "after/bankcard/unbind";
    /**
     * 绑定银行卡
     */
    public static final String BIND_BANK = URL_ROOT + "after/bankcard/bundbankcard";
    /**
     * 获取银行的开户行
     */
    public static final String OPEN_BANK = URL_ROOT + "view/bankcard/getbankname";
    /**
     * 获取猎头订单列表
     */
    public static final String GET_HUNTER_ORDER_LIST = URL_ROOT + "after/order/queryOnlineOrders";
    /**
     * 获取服务订单列表
     */
    public static final String GET_SERVICE_ORDER_LIST = URL_ROOT + "after/service/order/queryOnlineServiceOrders";
    /**
     * 获取服务订单详情
     */
    public static final String GET_SERVICE_ORDER_DETAIL = URL_ROOT + "after/service/order/queryServiceOrderDetails";
    /**
     * 删除服务订单
     */
    public static final String DELETE_SERVICE_ORDER = URL_ROOT + "after/service/order/deleteServiceOrderForMember";
    /**
     *取消服务订单
     */
    public static final String CANCEL_SERVICE_ORDER = URL_ROOT + "after/service/cancelServiceOrder";
    /**
     *评价服务订单
     */
    public static final String EVALUATE_SERVICE_ORDER = URL_ROOT + "after/order/ordercomment/commentServiceOrder";
    /**
     * 获取服务订单评价详情
     */
    public static final String GET_SERVICE_ORDER_EVALUATE_DETAIL = URL_ROOT + "after/order/ordercomment/getServiceOrderCommentDetail";
     /**
     * 回复服务订单评价
     */
    public static final String REPLY_SERVICE_ORDER_EVALUATE = URL_ROOT + "after/service/ServiceOrderReply";
    /**
     * 申请退款
     */
    public static final String APPLICATION_FOR_REFUND = URL_ROOT + "after/service/order/refundReturnServiceOrder";
    /**
     * 获取服务订单售后详情
     */
    public static final String GET_REFUND_DETAIL = URL_ROOT + "after/service/order/getserviceReturnOrderDetail";
    /**
     * 确认退款
     */
    public static final String CONFIRM_REFUND = URL_ROOT + "after/payOrder/refund";
    /**
     * 拒绝退款
     */
    public static final String REFUSE_REFUND = URL_ROOT + "after/orderPlace/productAndServiceOrderRefund";
    /**
     * 忘记交易密码验证二要素
     */
    public static final String FORGET_TRADE_PWD_CHECK_IDCARD = URL_ROOT + "after/transactionpassword/checkcardname";
    /**
     * 商品管理列表
     */
    public static final String GOODSMANGELIST = URL_ROOT + "after/product/getmerchantproductlist";
    /**
     * 商品管理列表批量操作(上架 下架 删除操作)
     */
    public static final String GOODSMANAGE_BATCH_OPERATION = URL_ROOT + "after/product/batchoperation";
    /**
     * 关注
     */
    public static final String ATTENTION = URL_ROOT + "after/memberfollow/collect";
    /**
     * 判断用户输入的交易密码是否正确（修改交易密码前判断）
     */
    public static final String UPDATE_TRADE_PWD_CHECKED_OLD_TRADE_PWD = URL_ROOT + "after/transactionpassword/checkpassword";
    /**
     * 判断用户输入的交易密码是否正确（修改交易密码前判断）
     */
    public static final String UPDATE_TRADE_PWD = URL_ROOT + "after/transactionpassword/updatepassword";
    /**
     * 获取猎头订单详情
     */
    public static final String HUNTER_ORDER_DETAILS = URL_ROOT + "after/order/queryOrderDetails";
    /**
     * 修改登录密码
     */
    public static final String UPDATE_LOGIN_PWD = URL_ROOT + "after/member/modifyPassword";
    /**
     * 获取评价详情
     */
    public static final String GET_EVALUATE_CONTENT = URL_ROOT + "after/order/ordercomment/getOrderComment";
    /**
     * 获取物流公司列表
     */
    public static final String GET_LOGISTICS_COMPANY_LIST = URL_ROOT + "after/orderPlace/getlogisticsCompanyList";
    /**
     * 获取评价列表
     */
    public static final String GET_EVALUATE_LIST = URL_ROOT + "after/order/ordercomment/getOrderComment";
    /**
     * 发布 已有商品列表 添加产品计划的商品-根据商品的名称搜索匹配的商品
     */
    public static final String PRODUCT_NAME = URL_ROOT + "after/solr/getProductListByName";
    /**
     * 我的收入列表
     */
    public static final String MY_INCOME_LIST = URL_ROOT + "after/member/queryMemberMoneyDetails";
    /**
     * 修改登录密码
     */
    public static final String UPDATA_OLD_PSW = URL_ROOT + "after/member/modifyPassword";
    /**
     * 发布资讯选择发布分类
     */
    public static final String PUBLISH_CLASSIFY = URL_ROOT + "/view/consultationClassify/findConsultationClassify";
    /**
     * 上传图片
     */
    public static final String UPLOAD_IMG = URL_ROOT + "view/upload/image";
    /**
     * 上传视频
     */
    public static final String UPLOAD_VIDEO = URL_ROOT + "view/upload/video";
    /**
     * 发布图文资讯
     */
    public static final String PUBLISH_INFO = URL_ROOT + "after/consultation/saveImageConsultation";
    /**
     * 发布视频资讯
     */
    public static final String PUBLISH_VIDEO = URL_ROOT + "after/consultation/saveVideoConsultation";
    /**
     * 发布产品计划
     */
    public static final String PUBLISH_PRODUCT_PLAN = URL_ROOT + "after/productplan/saveHomeProductPlan";
    /**
     * 修改产品计划
     */
    public static final String EDIT_PRODUCT_PLAN = URL_ROOT + "after/productplan/updateProductPlan";
    /**
     * 发布日记
     */
    public static final String PUBLISH_DAILY = URL_ROOT + "after/productplandaily/saveProductPlanDaily";
//    public static final String SUPPLIERID_PRODUCT = URL_ROOT + "view/solr/getProductListBySupplierId";
    /**
     * 修改发布日记
     */
    public static final String UPDATE_PUBLISH_DAILY = URL_ROOT + "after/productplandaily/updateProductPlanDaily";
    /**
     * 发布项目计划
     */
    public static final String PUBLISH_PROJECT = URL_ROOT + "after/projectplan/saveProjectPlan";
    /**
     * 资讯-添加产品计划搜索
     */
    public static final String PUBLISH_PROJECT_SEARCH = URL_ROOT + "view/productplan/getProductPlanList";
    /**
     * 发布供应商商品列表 展示平台上所有的供应商商品/根据商品名称或供应商搜索
     */
    public static final String SUPPLIERID_PRODUCT = URL_ROOT + "after/solr/getProductListBySupplierIdAndName";
    /**
     * 我的二维码
     */
    public static final String MY_ZXING = URL_ROOT + "after/member/my_qr_code_info";
    /**
     * 我的发布
     */
    public static final String MY_ISSUE = URL_ROOT + "after/personal/myIssuee";
    /**
     * 我的	猎豆奖励明细列表
     */
    public static final String MY_LIEDOULIST = URL_ROOT + "view/operateruler/getScoreRewardSubsidiaryList";
    /**
     * 我的	猎豆列表
     */
    public static final String MY_LIEDOU = URL_ROOT + "after/personcenter/integral/getIntegralDetails";
    /**
     * 获取评价列表
     */
    public static final String GET_EVAUATE_LIST = URL_ROOT + "after/order/toComment";
    /**
     * 发布评价
     */
    public static final String ADD_EVAUATE = URL_ROOT + "after/order/ordercomment/insertOrderComment";
    /**
     * 获取消息通知页面互动列表
     */
    public static final String GET_HUDONG_MSG_LIST = URL_ROOT + "after/msg/getInteractMsgList";
    /**
     * 获取消息通知页面互动列表
     */
    public static final String GET_SYSTEM_MSG_LIST = URL_ROOT + "after/msg/getSystemMsgList";
    /**
     * 售后列表（普通用户）
     */
    public static final String AFTER_SALES_LIST_MEMBER = URL_ROOT + "after/productreturn/getproductreturnlist";
    /**
     * 查找供应商商品的供应商列表
     */
    public static final String SUPPLIER_ALLLIST = URL_ROOT + "after/solr/getSupplierAllList";
    /**
     * 咨询评论
     */
    public static final String INFO_COMMENT = URL_ROOT + "after/consulationcomment/commentConsulation";
    /**
     * 申请售后选择商品列表
     */
    public static final String CHOOSE_PRODUCT_AFTER_SALES_LIST = URL_ROOT + "after/order/toCustomerService";
    /**
     * 删除订单
     */
    public static final String DELETE_ORDER = URL_ROOT + "after/order/deleteOrderByMember";
    /**
     * 猎头取消订单
     */
    public static final String CANCLE_ORDER_FOR_HUNTE = URL_ROOT + "after/order/cancelOrder";
    /**
     * 普通用户取消订单
     */
    public static final String CANCLE_ORDER_FOR_MEMBER = URL_ROOT + "after/order/cancelOrderForMember";
    /**
     * 添加产品计划列表
     */
    public static final String ADD_PRODUCTPLAN_LIST = URL_ROOT + "view/productplan/getProductPlanList";
    /**
     * 我的收藏-产品计划
     */
    public static final String MY_COLLECT_PRODUCTPLAN = URL_ROOT + "after/collection/getProductPlanDaily";
    /**
     * 我的收藏-资讯
     */
    public static final String MY_COLLECT_INFORMATION = URL_ROOT + "after/collection/getCollectionConsultation";
    /**
     * 我的收藏-商品
     */
    public static final String MY_COLLECT_GOOGS = URL_ROOT + "after/collection/listCollectionProduct";
    /**
     * 我的发布-删除资讯
     */
    public static final String MY_ISSUE_DELETE_INFORMATION = URL_ROOT + "after/personal/deleteMyConsulation";
    /**
     * 我的发布-删除项目计划
     */
    public static final String MY_ISSUE_DELETE_PROJECTPLAN = URL_ROOT + "after/personal/deleteMyProductProject";
    /**
     * 我的发布-删除产品计划
     */
    public static final String MY_ISSUE_DELETE_PRODUCTPLAN = URL_ROOT + "after/personal/deleteMyProductPlan";
    /**
     * 退换售后详情（普通用户）
     */
    public static final String AFTER_SALES_DETAIL_MEMBER = URL_ROOT + "view/productreturn/getreturnorderdetail";
    /**
     * 猎头售后列表
     */
    public static final String AFTER_SALES_LIST_HUNTER = URL_ROOT + "after/productreturn/getmerchantreturnlist";
    /**
     * 确认退款
     */
    public static final String AGREE_RETURN_MONEY = URL_ROOT + "after/payOrder/refund";
    /**
     * 商品拒绝退款
     */
    public static final String REFUSE_RETURN_MONEY = URL_ROOT + "after/orderPlace/productAndServiceOrderRefund";
    /**
     * 确认发货
     */
    public static final String CONFIRM_SEND = URL_ROOT + "after/orderPlace/confirmSend";
    /**
     * 售后列表原因
     */
    public static final String AFTER_SALES_LIST_REASON = URL_ROOT + "after/orderPlace/getReturnReason";
    /**
     * 回复评价
     */
    public static final String REPLY_EVALUATE = URL_ROOT + "after/order/ordercomment/backOrderComment";
    /**
     * 我的 修改个人信息
     */
    public static final String SEETING_INFRO = URL_ROOT + "after/member/modifyMember";
    /**
     * 我的 获取个人信息
     */
    public static final String GET_INFRO = URL_ROOT + "after/member/getMemberInfo";
    /**
     * 我的 版本检测
     */
    public static final String CHECK_VERSON = URL_ROOT + "view/member/checkVersion";
    /**
     * 添加商品运费模板列表
     */
    public static final String FREIGHT_RULE = URL_ROOT + "after/fright/getFreightRuleMainListByUserId";
    /**
     * 猎头商品添加
     */
    public static final String ADD_PRODUCT = URL_ROOT + "after/product/addProductByHunter";
    /**
     * 猎头商品编辑
     */
    public static final String UPDATE_PRODUCT = URL_ROOT + "after/product/updateProductByHunter";
    /**
     * QQ和WX登录接口
     */
    public static final String QQORWX_LOGIN = URL_ROOT + "view/member/thirdParty-login";
    /**
     * 确认收货
     */
    public static final String CONFIRM_RECEIVE = URL_ROOT + "after/orderPlace/confirmReceive";
    /**
     * 地区查询
     */
    public static final String SETTING_ADRESS = URL_ROOT + "after/memberaddress/getPositionList";
    /**
     * 猎头商品添加
     */
    public static final String FREIGH_RULE = URL_ROOT + "after/fright/getFreightRuleContent";
    /**
     * 收货人列表
     */
    public static final String SHOPING_LIST = URL_ROOT + "after/memberaddress/addresslist";
    /**
     * 编辑收货人
     */
    public static final String UPDATA_LIST = URL_ROOT + "after/memberaddress/updateaddress";
    /**
     * 意见反馈
     */
    public static final String SETTING_COUPLE = URL_ROOT + "after/member/memberFeedback";
    /**
     * 添加收货地址
     */
    public static final String ADD_ADRESS = URL_ROOT + "after/memberaddress/saveaddress";
    /**
     * 去编辑收货地址
     */
    public static final String SET_ADRESS = URL_ROOT + "after/memberaddress/toUpdateAddress";
    /**
     * 删除收货地址
     */
    public static final String DELETE_ADRESS = URL_ROOT + "after/memberaddress/deladdressbyid";
    /**
     * 设置默认地址
     */
    public static final String SET_DEFAULT_ADDRESS = URL_ROOT + "after/memberaddress/set-default-address";
    /**
     * 猎头的个人主页
     */
    public static final String SET_LIETOUINFRO = URL_ROOT + "after/member/getMemberBasic";
    /**
     * 创建运费模板
     */
    public static final String ADD_ESTABLISH_FREIGHT = URL_ROOT + "after/fright/addFreightRuleMain";
    /**
     * 确认退货
     */
    public static final String AGREE_RETURN_GOODS = URL_ROOT + "after/orderPlace/agreeRefundGoods";
    /**
     * 提现
     */
    public static final String CASH = URL_ROOT + "after/cash/cash";
    /**
     * 切换专员回显专员信息
     */
    public static final String CHANGE_ATTACHE_SHOW_MESSAGE = URL_ROOT + "after/immember/gethuntermsg";
    /**
     * 匹配通讯录好友
     */
    public static final String MATCHING_PHONEBOOK = URL_ROOT + "after/immember/gethuntersbyphones";
    /**
     * 根据昵称和手机号查询专员
     */
    public static final String BYNAME_PHONE_QUERY_ATTACHE = URL_ROOT + "after/immember/findhunter";
    /**
     * 获取其他服务专员列表
     */
    public static final String OHTER_SPECIALLYPERSON = URL_ROOT + "after/immember/getCommissioner";
    /**
     * 申请售后
     */
    public static final String APPLY_AFTER_SALES = URL_ROOT + "after/orderPlace/refundReturn";
    /**
     * 发布产品计划
     */
    public static final String PRODECE_PLAN = URL_ROOT + "after/productplan/saveHomeProductPlan ";
    /**
     * 收藏接口
     */
    public static final String COLLECT = URL_ROOT + "after/memberfollow/collection";
    /**
     * (我的)个人信息回显
     */
    public static final String PERSON_INDRO = URL_ROOT + "after/member/getMemberInfoByMemberId";
    /**
     * (我的)会员信息回显
     */
    public static final String MEEBER_INDRO = URL_ROOT + "view/member/getMemberInfoByMemberId";
    /**
     * 地区查询
     */
    public static final String GET_POSITION_LIST = URL_ROOT + "after/memberaddress/getPositionList";
    /**
     * 发布--商品编辑--商品信息回显-
     */
    public static final String HUNTER_PROODUCT = URL_ROOT + "after/product/getHunterProductContent";
    /**
     * 发布 ---查询已发布的产品咨询的商品列表--编辑列表
     */
    public static final String COMPILE_HUNTER_PROODUCT = URL_ROOT + "view/solr/getProductListByProductPlanId";
    /**
     * 运费模板回显-
     */
    public static final String FREIGHT_RULE_CODE = URL_ROOT + "after/fright/getFreightRuleContent";
    /**
     * 通过手机号获取第三方登录信息
     */
    public static final String BYPHONE_SANFANG_LOGIN_MESSAGE = URL_ROOT + "view/member/checkOpenId";
    /**
     * 通过openId检查是否绑定过手机号
     */
    public static final String BYPOPENID_ISBINDPHONE = URL_ROOT + "view/member/checkPhone";
    /**
     * 我的徒弟数量
     */
    public static final String HUNTER_NUM = URL_ROOT + "after/personcenter/memberinfo/getMemberApprenticeNum";
    /**
     * 徒弟列表
     */
    public static final String HUNTER_LIST = URL_ROOT + "after/merchant/queryApprenticeList";
    /**
     * 支付
     */
    public static final String PAY = URL_ROOT + "after/payOrder/sendUnifiedOrderForApp";
    /**
     * 评价商品的评论
     */
    public static final String REPLY_COMMENT = URL_ROOT + "after/order/ordercomment/backOrderComment";
    /**
     * 猎头向我提问
     */
    public static final String ASK_ME = URL_ROOT + "after/merchantquiz/saveMerchantQuiz";
    /**
     * 向我提问回复
     */
    public static final String REPLY_ASK_ME = URL_ROOT + "after/merchantquiz/backMerchantQuiz";
    /**
     * 判断用户是否是专员
     */
    public static final String CHECK_MEMBER_IS_HUNTER = URL_ROOT + "after/immember/getmembergroupid";
    /**
     * 提现详情
     */
    public static final String CASH_DETAILS = URL_ROOT + "after/member/withdrawdetail";
    /**
     * 消息阅读状态
     */
    public static final String INTERACT = URL_ROOT + "after/interact/interact";
    /**
     * 检查三方账号是否绑定过手机号
     */
    public static final String CHECK_THIRD_ACCOUNT_IS_BIND = URL_ROOT + "view/member/checkPhone";
    /**
     * 修改项目计划
     */
    public static final String UPDATE_PROJECT_PLAN = URL_ROOT + "after/projectplan/updateProjectPlan";
    /**
     * 商品的一级分类
     */
    public static final String STAIR_CLASSIFT = URL_ROOT + "view/producttype/getOneList";
    /**
     * 商品的二级分类
     */
    public static final String TWO_CLASSIFT = URL_ROOT + "view/producttype/getTwoList";
    /**
     * 签到
     */
    public static final String SIGNIN = URL_ROOT + "after/sign/signByMemberId";
    /**
     * 个人中心
     */
    public static final String PERSONAL_CENTER = URL_ROOT + "after/member/getMemberListNewMsg";
    /**
     * 获取收藏的服务项目
     */
    public static final String COLLECT_SERVICPROJECT = URL_ROOT + "after/collection/getServicProjectList";
    /**
     * 首页轮播图
     */
    public static final String HOME_BANNER = URL_ROOT + "after/consultation/getFirstBannerList";
    /**
     * 首页课题分类
     */
    public static final String HOME_PROJECT_CLASSIFY = URL_ROOT + "after/topic/getAllTopicClass";
    /**
     * 首页根据课题分类查询课题
     */
    public static final String HOME_LIST_BY_CLASSIFY = URL_ROOT + "after/topic/selectTopicbyClassId";
    /**
     * 服务团队轮播图
     */
    public static final String SERVER_TEAM_BANNER = URL_ROOT + "view/serviceProject/getAdvisorBannerList";
    /**
     * 服务团队分类下拉
     */
    public static final String SERVER_TEAM_CLASSIFY_DROP = URL_ROOT + "view/serviceProject/getServiceProjectList";
    /**
     * 服务团队列表
     */
    public static final String SERVER_TEAM_LIST = URL_ROOT + "view/serviceProject/getServiceAdvisorList";
    /**
     * 案例管理列表
     */
    public static final String CASE_MANAGER_LIST = URL_ROOT + "after/case/caseManageMentList";
    /**
     * 案例列表删除
     */
    public static final String DELETE_CASE_MANAGER = URL_ROOT + "after/case/caseManageMentDelete";
    /**
     * 输入验证码
     */
    public static final String EDIT_YANZHEN_CODE = URL_ROOT + "after/service/order/scanCodeOrder";
    /**
     * 扫描二维码
     */
    public static final String SCAN_ER_CODE = URL_ROOT + "after/service/order/scanQRCode";
    /**
     * 课题列表
     */
    public static final String GET_TOPIC_LIST = URL_ROOT + "after/topic/getTopicList";
    /**
     * 删除课题
     */
    public static final String DELETE_TOPIC = URL_ROOT + "after/topic/deleteTopic";
    /**
     * 分享  推荐列表
     */
    public static final String RECOMMENDED_LIST = URL_ROOT + "view/consultation/getConsultation";
    /**
     * 分享 关注列表
     */
    public static final String ATTENTION_LIST = URL_ROOT +"view/consultation/getFollowByListType";
    /**
     * 分享  行业分类
     */
    public static final String INDUSTRY_SORT = URL_ROOT + "after/service/order/selectAllServiceCategory";
    /**
     * 分享  根据行业分类id 获取 服务项目列表
     */
    public static final String GET_SERVER_PROJECT_LIST_BY_INDUSTRY_SORT = URL_ROOT + "view/serviceProject/getServiceProjectByParams";
    /**
     * 用户卡联盟订单列表
     */
    public static final String CARD_UNION_ORDER_LIST = URL_ROOT + "after/card/order/getOrderList";
    /**
     * 商家卡联盟消费列表
     */
    public static final String GET_CARD_TYPE_LIST = URL_ROOT +"after/card/order/getCardTypeList";
    /**
     * 商家卡联盟消费详情
     */
    public static final String GET_CLUB_CARDD_ETAIL = URL_ROOT +"after/card/order/getClubCardDetail";
    /**
     * 显示出后台添加的热门搜
     */
    public static final String HOT_SEARCH_KEYWORD = URL_ROOT + "view/searchkeyword/getSearchKeyWord";
    /**
     * 优惠券信息查询
     */
    public static final String GET_COUPON = URL_ROOT + "view/member/getCoupon";
    /**
     * 会员登陆领取优惠券
     */
    public static final String RECEIVE_COUPON = URL_ROOT + "after/member/receiveCoupon";
    /**
     * 我的卡包-列表
     */
    public static final String MINE_CARDBAG = URL_ROOT + "after/card/order/getMyCardList";
    /**
     * 我的卡包详情
     */
    public static final String MINE_CARDBAG_DETAIL = URL_ROOT + "after/card/order/getClubCardDetail";
    /**
     * 服务评价
     */
    public static final String SERVICE_COMMENT = URL_ROOT + "after/order/ordercomment/getServiceOrderComment";
    /**
     * 商品评价
     */
    public static final String GOODS_COMMENT = URL_ROOT + "after/order/ordercomment/getServiceProductOrderComment";
    /**
     * 服务列表
     */
    public static final String SERVICE_LIST = URL_ROOT + "after/service/selectServiceList";
    /**
     * 根据标题搜索资讯
     */
    public static final String GET_CONSULTATION_BY_TITLE = URL_ROOT + "view/consultation/getconsultationbytitle";
    /**
     * 根据分类展示列表
     */
    public static final String GET_CONSULTATION = URL_ROOT + "view/consultation/getConsultation";
    /**
     * 服务的上下架和删除
     */
    public static final String OPERATE_SERVICE = URL_ROOT + "after/service/updateService";
    /**
     * 用户删除卡联盟订单列表
     */
    public static final String DELETE_CARD_UNION_ORDER = URL_ROOT + "after/service/order/deleteServiceOrderForMember";
    /**
     * 用户取消卡联盟订单列表
     */
    public static final String CANCEL_CARD_UNION_ORDER = URL_ROOT +"after/card/order/cancelOrder";
    /**
     * 根据资讯id获取回显列表
     */
    public static final String BY_ID_REQUEST_DATA = URL_ROOT +"after/topic/getConsuttionDetail";
    /**
     * 获取服务分类
     */
    public static final String GET_SERVICE_CLASS = URL_ROOT + "view/serviceProject/getServiceProjectList";
    /**
     * 创建服务团队完成
     */
    public static final String COMPLETE_CREATE_SERVICE = URL_ROOT + "after/serviceTeam/createShop";
    /**
     * 商品管理列表2
     */
    public static final String GET_GOODS_LIST = URL_ROOT + "after/product/getadvisorproductlist";
    /**
     * 获取团队信息
     */
    public static final String GET_TEAM_INFO = URL_ROOT + "after/advisor/getAdvisorMsg";
    /**
     * 修改团队信息
     */
    public static final String UPDATE_TEAM_INFO = URL_ROOT + "after/advisor/editAdvisorMsg";
    /**
     * 是否领取过新人红包
     */
    public static final String GET_MEMBER_IS_RECEIVE_COUPON = URL_ROOT + "after/member/getMemberIsReceiveCoupon";
    /**
     * 回复评论
     */
    public static final String REPLAY_COMMENT = URL_ROOT + "after/order/ordercomment/replyComment";
    /**
     * 发布课题
     */
    public static final String PUBLISH_COURSE = URL_ROOT +"after/topic/createTask";
    /**
     * 获取未读消息
     */
    public static final String GET_MESSAGE = URL_ROOT +"after/interact/interactSelect";
    /**
     *我的收藏-分享
     */
    public static final String COLLECT_SHARE = URL_ROOT +"after/collection/getCollectionConsultation";
    /**
     * 获取评论的回复列表
     */
    public static final String GET_CONSULTATION_REPLY_COMMENT_MESSAGE = URL_ROOT + "after/msg/getConsultationReplyCommentMessage";

    /**
     * 给评论进行回复
     */
    public static final String REPLY_CONSULTATION_COMMENT = URL_ROOT +"after/consulationcomment/replyconsultationComment";
    /**
     * 卡联盟订单评价
     */
    public static final String CARD_ORDER_COMMENT = URL_ROOT +"after/card/order/commentOrder";

    /**
     * 编辑服务
     */
    public static final String EDIT_SERVICE = URL_ROOT + "after/service/updateServiceProject";
    /**
     * 创建服务
     */
    public static final String CREATE_SERVICE = URL_ROOT + "after/service/createService";

    /**
     * 获取创建服务分类列表
     */
    public static final String SERVICE_CLASSIFY_LIST = URL_ROOT + "after/service/getClassInfo";

    /**
     * 获取服务数据
     */
    public static final String GET_SERVICE_DATA = URL_ROOT + "after/service/selectServiceProjectById";

}
