package com.yst.im.imchatlibrary.utils;

import com.yst.im.imsdk.ImSdkApplication;

/**
 * 连接地址  常量
 *
 * @author lierpeng
 * @version 1.0.0
 * @date 2018/03/28
 */
public class Constants {
    /**
     * 请求来源  app   pjyc
     */
    public static final String REQUEST_SS = ImSdkApplication.channel;

    /**
     * 端口号
     */
    public static final int PORT = 53535;
    public static final boolean DEBUG = Boolean.parseBoolean("true");

    /**
     * 文件路径管理类
     */
    public static class FilePath {
        public static final String ROOT_PATH = "yst/";
        public static final String RECORD_DIR = "im/";
        public static final String RECORD_PATH = ROOT_PATH + RECORD_DIR;
        public static final String PHOTO_FILE_NAME = "temp_photo.png";
    }

    /**
     * 正式
     */
//    public static final String HOST = "113.209.232.177";
//    public static final String BASE_URL = "http://113.209.232.176:8080";
//    public static final String FILE_URL = "http://113.209.232.176:12315";

//        /**
//     * 黄凡
//     */
//    public static final String HOST = "192.168.10.222";
//    public static final String BASE_URL = "http://192.168.10.222:8080";
//    public static final String FILE_URL = "http://192.168.10.222:12315";

    /**
     * 测试
     */
    public static final String HOST = "192.168.50.254";
    public static final String BASE_URL = "http://192.168.50.254:8080";
    public static final String FILE_URL = "http://192.168.50.254:12315";

    /**
     * 获取群主信息及群公告
     */
    public static final String URL_GROUP_CHAT_OWNER = BASE_URL + "/API/queryGroupChatOwner";

    /**
     * 邀请用户加入聊天室
     */
    public static final String URL_INVITE_USER = BASE_URL + "/API/invitingUsersAddGroupChat";

    /**
     * 申请加入
     */
    public static final String URL_JOIN_GROUP_CHAT = BASE_URL + "/API/applyJoinGroupChat";


    /**
     * 群置顶
     */
    public static final String URL_GROUP_STICK = BASE_URL + "/API/groupStick";

    /**
     * 查询待审批人员列表
     */
    public static final String URL_GROUP_NEW_QUERYUSER = BASE_URL + "/API/getApprovedList";
    /**
     * 审核用户加入群
     */
    public static final String URL_CHECK_JOIN_GROUP = BASE_URL + "/API/checkJoinGroup";
    /**
     * 解散群聊
     */
    public static final String URL_DISSOLVE_GROUP = BASE_URL + "/API/disMissGroupChat";
    /**
     * 修改登录密码
     */
    public static final String URL_UPDATE_PASSWORD = BASE_URL + "/API/updatePassword";

    /**
     * 文件上传
     */
    public static final String URL_FILE_UPLOAD = FILE_URL + "/API/fileUpload";

    /**
     * 修改群公告
     */
    public static final String URL_UPDATE_GROUP_NOTICE = BASE_URL + "/API/updateGroupChatNotice";

    /**
     * 修改群聊室名称
     */
    public static final String URL_UPDATE_GROUP_NAME = BASE_URL + "/API/updateGroupName";
    /**
     * 踢出群聊
     */
    public static final String URL_KICK_OUT_GROUP = BASE_URL + "/API/KickOutGroup";

    /**
     * 获取群聊详情
     */
    public static final String URL_GROUP_DETAIL = BASE_URL + "/API/findGroupInfo";
    /**
     * 最近联系人
     */
    public static final String URL_RECENT_CONTACTS = BASE_URL + "/API/getRecentlyLinkMan";
    /**
     * 退出登录
     */
    public static final String URL_QUIT_LOGIN = BASE_URL + "/API/quitLogin";

    /**
     * 判断token是否过期  0 不过期
     */
    public static final String URL_JUDGE_TOKEN = BASE_URL + "/API/selectTokenFromRedis";

    /**
     * 修改绑定的手机号
     */
    public static final String URL_UPDATE_BIND_PHONE = BASE_URL + "/API/updateBindingPhone";

    /**
     * 密码验证
     */
    public static final String URL_VERIFY_PASSWORD = BASE_URL + "/API/verifyPassword";

    /**
     * 删除系统通知
     */
    public static final String URL_DELETE_NOTIFICATION = BASE_URL + "/API/deleteSystematicNotification";

    /**
     * 查询用户个信息
     */
    public static final String URL_FIND_USER_INFO = BASE_URL + "/API/findUserInformation";
    /**
     * 忘记密码接口
     */
    public static final String URL_FORGOT_PASSWORD = BASE_URL + "/API/forgetPassword";

    /**
     * 完善用户信息
     */
    public static final String URL_FINISH_USER_INFO = BASE_URL + "/API/consummateUserInformation";

    /**
     * 设置头像
     */
    public static final String URL_SET_LOGO = BASE_URL + "/API/setLogo";
    /**
     * 获取一级地区
     */
    public static final String URL_ADDRESS_INFO = BASE_URL + "/API/getAddressInfo";

    /**
     * 获取二级地区列表
     */
    public static final String URL_SECOND_ADDRESS_INFO = BASE_URL + "/API/getSecondAddressInfo";

    /**
     * 获取手机验证码
     */
    public static final String URL_VERIFICATION_INFO = BASE_URL + "/API/getVerificationCode";

    /**
     * 优工连不经过同意直接添加为好友
     */
    public static final String URL_FRIENDREGISTRATION = BASE_URL + "/API/friendRegistration";


    /**
     * 注册
     */
    public static final String URL_REGISTER = BASE_URL + "/API/imRegister";


    /**
     * 添加好友
     */
    public static final String URL_ADD_USER = BASE_URL + "/API/addUser";


    /**
     * 好友置顶
     */
    public static final String URL_FRIEND_STICK = BASE_URL + "/API/friendStick";

    /**
     * 修改好友备注http://192.168.50.254:8080/API/updateFriendRemark
     */
    public static final String URL_UPDATE_FRIEND_REMARK = BASE_URL + "/API/updateFriendRemark";

    /**
     * 审核添加好友接口
     */
    public static final String URL_CHECK_ADD_FRIEND = BASE_URL + "/API/checkAddFriend";

    /**
     * 查询好友申请
     */
    public static final String URL_QUERY_ADD_FRIEND = BASE_URL + "/API/queryAddFriend";

    /**
     * 查询好友列表
     */
    public static final String URL_FIND_USER_LIST = BASE_URL + "/API/findUserList";
    /**
     * 修改地址
     */
    public static final String URL_SET_ADDRESS = BASE_URL + "/API/setAddress";

    /**
     * 设置姓名
     */
    public static final String URL_SET_NAME = BASE_URL + "/API/setName";

    /**
     * 设置性别
     */
    public static final String URL_SET_SEX = BASE_URL + "/API/setSex";

    /**
     * 删除单聊历史消息
     */
    public static final String URL_DELETE_SINGLE_CHAT_MESSAGE = BASE_URL + "/API/deleteSingleChatMessage";

    /**
     * 用户撤回消息
     */
    public static final String URL_RECALL_MESSAGE = BASE_URL + "/API/recallMessage";
    /**
     * 转发
     */
    public static final String URL_FORWARDING_MESSAGE = BASE_URL + "/API/transpondMessage";

    /**
     * 查询群聊室状态
     */
    public static final String URL_GROUP_SHIELD_STATE = BASE_URL + "/API/queryIsStickAndIsShield";

    /**
     * 取消屏蔽群消息
     */
    public static final String URL_CANCEL_SHIELD_GROUP_CHAT = BASE_URL + "/API/cancelShieldGroupChat";

    /**
     * 屏蔽消息
     */
    public static final String URL_SHIELD_GROUP_CHAT = BASE_URL + "/API/shieldGroupChat";
    /**
     * 删除好友申请
     */
    public static final String URL_DELETE_FRIEND_APPLY = BASE_URL + "/API/deleteAddFriendsApplication";

    /**
     * 用户获取单聊历史消息
     */
    public static final String URL_SINGLE_CHAT_HISTORY = BASE_URL + "/API/getSingleChatHistoryMessage";

    /**
     * 用户删除群聊历史记录
     */
    public static final String URL_DELETE_GROUP_CHAT_MESSAGE = BASE_URL + "/API/deleteGroupChatMessage";

    /**
     * 用户获取群历史消息
     */
    public static final String URL_GROUP_CHAT_HISTORY = BASE_URL + "/API/getGroupChatHistoryMessage";

    /**
     * 找人
     */
    public static final String URL_FIND_USER_BY_PHONE = BASE_URL + "/API/findUserByPhone";

    /**
     * 找群
     */
    public static final String URL_FIND_GROUP_BY_DIM = BASE_URL + "/API/queryGroupChatByDim";

    /**
     * 搜索群聊室和用户
     */
    public static final String URL_DIM_SEARCH = BASE_URL + "/API/dimSearch";
    /**
     * 用户获取离线消息
     */
    public static final String URL_GET_OFF_MSG = BASE_URL + "/API/getOfflineMessage";
    /**
     * 修改群备注
     */
    public static final String URL_UPDATE_GROUP_REMARK = BASE_URL + "/API/updateGroupRemark";
    /**
     * 删除加群申请
     */
    public static final String URL_DELETE_GROUP_APPLY = BASE_URL + "/API/deleteAddGroupChatApplication";

    /**
     * 验证验证码
     */
    public static final String URL_VERIFICATIONVERIFY = BASE_URL + "/API/verificationVerify";

    /**
     * 我创建的群聊
     */
    public static final String URL_QUERYCREATEGROUPCHAT = BASE_URL + "/API/queryCreateGroupChat";

    /**
     * 我加入的群聊
     */
    public static final String URL_QUERYJOINGROUPCHAT = BASE_URL + "/API/queryJoinGroupChat";

    /**
     * 搜索创建的群聊
     */
    public static final String URL_SEARCHCREATEDGROUPCHAT = BASE_URL + "/API/searchCreatedGroupChat";

    /**
     * 搜索加入的群聊
     */
    public static final String URL_SEARCHJOINEDGROUPCHAT = BASE_URL + "/API/searchJoinedGroupChat";

    /**
     * 设置界面 - 修改密码
     */
    public static final String URL_AMENDPASSWORD = BASE_URL + "/API/amendPassword";

    /**
     * 查询好友详情
     */
    public static final String URL_SELECTFRIENDINFO = BASE_URL + "/API/selectFriendInfo";

    /**
     * 屏蔽好友
     */
    public static final String URL_SHIELDSINGLECHAT = BASE_URL + "/API/shieldSingleChat";


    /**
     * 退出群聊
     */
    public static final String URL_QUIT_GROUP = BASE_URL + "/API/quitGroupChat";

    /**
     * 删除好友
     */
    public static final String URL_DELETE_USER = BASE_URL + "/API/deleteFriends";

    /**
     * 在群中搜索成员（可根据备注和昵称）
     */
    public static final String URL_SEARCH_USER_IN_GROUP = BASE_URL + "/API/searchUserInGroup";

    /**
     * 修改消息备份表中消息的状态
     */
    public static final String URL_UPDATEMESSAGESTATE = BASE_URL + "/API/updateMessageState";

    /**
     * 注册
     */
    public static final String REGISTER_URL = BASE_URL + "/API/register";


    /**
     * 添加好友
     */
    public static final String ADD_USER = BASE_URL + "/API/addUser";

    /**
     * 查询好友列表
     */
    public static final String FIND_USER_LIST = BASE_URL + "/API/findUserList";

    /**
     * 查询好友信息
     */
    public static final String FIND_USER_INFO = BASE_URL + "/API/findUser";

    /**
     * 获得离线消息
     */
    public static final String GET_OFF_MSG = BASE_URL + "/API/getOfflineMessage/";


    /**
     * 查询历史消息
     */
    public static final String GET_HISTORY_MSG = BASE_URL + "/API/getHistoryMessage/";

    /**
     * 查询删除消息
     */
    public static final String DELETE_MSG = BASE_URL + "/API/deleteMessage/";

    /**
     * 邀请入群列表
     */
    public static final String URL_INVITE_LIST = BASE_URL + "/API/selectUserChatNotInGroup";

    /**
     * 踢出群聊
     */
    public static final String URL_DROP_GROUP_CHAT = BASE_URL + "/API/dropGroupChat";

    /**
     * 创建群聊
     */
    public static final String URL_TAKE_GROUP_CHAT = BASE_URL + "/API/takeGroupChat";

    /**
     * 系统通知列表
     */
    public static final String URL_SYSTEMATIC_NOTIFICATION = BASE_URL + "/API/systematicNotification";


    /**
     * 查询两个用户是否是好友
     */
    public static final String URL_REATION_BETWEEN_THE_PERSONS = BASE_URL + "/API/selectReationBetweenThePersons";

    /**
     * 加入群聊
     */
    public static final String JOIN_GROUP_CHAT = BASE_URL + "/API/addGroupChat";

    /**
     * 退出群聊
     */
    public static final String EXIT_GROUP_CHAT = BASE_URL + "/API/exitGroupChat";

    /**
     * 所有群聊
     */
    public static final String GETALL_GROUP_CHAT = BASE_URL + "/API/findAllGroupChat";

    /**
     * 获取群聊详情
     */
    public static final String GET_GROUP_CHATDETAIL = BASE_URL + "/API/findGroupDetail";


    /**
     * 被邀请者
     */
    public static final String GREAT_GROUP_QUERY_USER = BASE_URL + "/API/queryUserList";


    /**
     * 拒绝加入
     */
    public static final String GROUP_REFUSER_JOIN = BASE_URL + "/API/refuseUserJoin";

    /**
     * 拒绝加入
     */
    public static final String GROUP_AGREE_JOIN = BASE_URL + "/API/AgreeToJoin";

    /**
     * 移除
     */
    public static final String GROUP_KICK_OUT_GROUP = BASE_URL + "/API/KickOutGroup";

    /**
     * 集群
     */
    public static final String GET_BAST_SEVER = BASE_URL + "/API/getBestServer";


    /**
     * 被邀请者
     */
    public static final String CREATE_INVITED = BASE_URL + "/API/invitingUsers";

    /**
     * 私聊 最近联系人
     */
    public static final String RECENT_CONTACTS = BASE_URL + "/API/getRecentContacts ";

    /**
     * 禁言
     */
    public static final String GROUP_SHUT_PERSON = BASE_URL + "/API/prohibitUserSpeak";

    /**
     * 解除禁言
     */
    public static final String GROUP_REMOVE_SHUT = BASE_URL + "/API/removeProhibitUserSpeak";
    /**
     * 群历史
     */
    public static final String GROUP_HISTORY = BASE_URL + "/API/getGroupHistoryMessage";

    /**
     * 搜索群成员
     */
    public static final String GROUP_MEMBER_SEARCH = BASE_URL + "/API/queryUserList";

    /**
     * 搜索被邀请者
     */
    public static final String GROUP_INVITE_SEARCH = BASE_URL + "/API/queryGroupChatUserInfo";

    /**
     * 根据行业id搜索用户
     */
    public static final String GROUP_INVITE_SEARCH_USER = BASE_URL + "/API/queryUserByIndustry";

    /**
     * 删除
     */
    public static final String GROUP_SHOW_RED_POINT = BASE_URL + "/API/deleteSystemMessageOnRedis";

    /**
     * 登录
     */
    public static final String URL_API_LOGIN = BASE_URL + "/API/imLogin";

    /**
     * SDK登录
     */
    public static final String UPDATE_CONTENT_VOICE = BASE_URL + "/API/updateContentFormat";

    /**
     * 语音json
     */
    public static final String SDK_LOGIN = BASE_URL + "/API/sdkLogin";


    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKfmNmf6ucgLPYsGBjLq34IrTi6YhZSjzCjVDpqzSqaZrZb9q4U6/IpYa4FK4rBrF+Gpkrb0bZW1ej0r/GusZuCsZKLi8v2WvdJ1A3SxBRP6kBcc+v389i+TjmhRXj6wndp5T7gwcgJdU6LqLfYD9+RvQVF8GRXQ/0hTHGLsVhoQIDAQAB";

    public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIp+Y2Z/q5yAs9iwYGMurfgitOLpiFlKPMKNUOmrNKppmtlv2rhTr8ilhrgUrisGsX4amStvRtlbV6PSv8a6xm4KxkouLy/Za90nUDdLEFE/qQFxz6/fz2L5OOaFFePrCd2nlPuDByAl1Touot9gP35G9BUXwZFdD/SFMcYuxWGhAgMBAAECgYAhTM59wMj0soWL3qzubDTsavvva5ObVJxTc0M3TDE3oHjZO2nt1EF6D0LbVC2krtFSrvaRWQpCDRJ/k5cFI7e+NWjfzKwoXNLu+1MHxzRbvsriGRRcbWMvif5RnJJ5wpaeEgq9QuNLHarmM62UHhrcBDUqMFrYBOFhbnj7F0iduQJBAOcSF5l0GEGv2VGd4PbK93MV8jvz1yCU7SpKC6qwkZc5wHv2Ie6083a0abaLymBoDoheytBKCmqDmeqvZQ7ABk8CQQCZb20SqxbtS7pNbWetALakFHVU4XF192pR1+POZ7AlNs8EjXpzVUstjVZ8pHzx0mX6zZdjnsVNkQcSCeAx3Q0PAkEA5ARvc0cjtJYxjh+MYhyxiEiMy+p4TDeJvWRqFNq+IIulzO16WJMJeQbZFoDliLLGPH3GBzo5AxiwJu1DfQWycwJADMyhwavqBrOJgAn/WwwzMC7Qttrzlw6jnN4wj7hQllojHNMcguB7m/otW8pw7c9KWIir91B4TwcpGtUeEarJ+wJAQox4LNwPoy+OjPXVWDOoJ3OEij7Qie6mgjkgrKfC9fJ3cPcKWZIIhD0OEmG2ixN37OdJ5IAO6lYcSfL/pRs87Q==";

}
