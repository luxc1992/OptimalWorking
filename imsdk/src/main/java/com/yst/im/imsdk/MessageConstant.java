package com.yst.im.imsdk;

/**
 * 消息类型常量值
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/4.
 */
public class MessageConstant {
    /**
     * 定义消息类型 0 json 1xml 2 图片 3视频 4文件 5文件夹  6语音  51 消息模板
     */
    public static final int TYPE_TXT = 0;
    public static final int TYPE_XML = 1;
    public static final int TYPE_PICTURE = 2;
    public static final int TYPE_AVI = 3;
    public static final int TYPE_FILE = 4;
    public static final int TYPE_FOLDER = 5;
    public static final int TYPE_VOICE = 6;
    /**
     * 解除好友关系
     */
    public static final int TYPE_DELETE_USER = 62;
    /**
     * 被踢下线
     */
    public static final int TYPE_USER_OUT_LOGIN = 68;
    /**
     * 用户推出群聊
     */
    public static final int TYPE_USER_OUT_GROUP = 71;
    /**
     * 用户加入群聊
     */
    public static final int TYPE_USER_JINO_GROUP = 74;
    /**
     * 邀请用户加入群聊
     */
    public static final int TYPE_INVITE_JINO_GROUP = 76;
    /**
     * 撤回消息，历史记录使用
     */
    public static final int TYPE_RECALL_HISTORY = 79;
    /**
     * 邀请加入群聊
     */
    public static final int TYPE_JION_GROUP = 80;

    /**
     * 撤回消息通知使用
     */
    public static final int TYPE_RECALL = 81;

    /**
     * 发送文本
     */
    public static final int NEWS_SEND_TEXT = 0;
    /**
     * 发送图片
     */
    public static final int NEWS_SEND_PICTURE = 1;
    /**
     * 发送语音
     */
    public static final int NEWS_SEND_VOICE = 2;
    /**
     * 接收文本
     */
    public static final int NEWS_ACCEPT_TEXT = 3;
    /**
     * 接收图片
     */
    public static final int NEWS_ACCEPT_PICTURE = 4;
    /**
     * 接收语音
     */
    public static final int NEWS_ACCEPT_VOICE = 5;

    /**
     * 撤回
     */
    public static final int NEWS_SEND_RECALL = 8;

    public static final int TEMPLATE_MESSAGE = 10;

    /**
     * 模板消息type
     */
    public static final int TYPE_TEMPLATE = 51;
    /**
     * 移除群聊
     */
    public static final int TYPE_OUTLOGIN = 70;
    public static final int NEWS_SEND_TEMPLATE_MESSAGE = 6;
    public static final int NEWS_ACCEPT_TEMPLATE_MESSAGE = 7;

    /**
     * 模板消息 自定义类型 图片的上下左右
     */
    public static final String TEMPLATE_TOP = "top";
    public static final String TEMPLATE_BOTTOM = "bottom";
    public static final String TEMPLATE_LEFT = "left";
    public static final String TEMPLATE_RIGHT = "right";
    public static final String MAN = "男";

    public static final String CONTENT = "content";
    public static final String FILE_NAME = "file";
    public static final String GIF = "GIF";
    public static final String GROUP_CHAR = "GROUP_CHAR";

    public static final String GROUP_ADD = "jia";
    public static final String GROUP_DELETE = "jian";
    /**
     * int 常量
     */
    public static final int NUM_0 = 0;
    public static final int NUM_1 = 1;
    public static final int NUM_2 = 2;
    public static final int NUM_3 = 3;
    public static final int NUM_4 = 4;
    public static final int NUM_5 = 5;
    public static final int NUM_6 = 6;
    public static final int NUM_7 = 7;
    public static final int NUM_8 = 8;
    public static final int NUM_9 = 9;
    public static final int NUM_10 = 10;
    public static final int NUM_11 = 11;
    public static final int NUM_13= 13;
    public static final int NUM_15 = 15;
    public static final int NUM_16 = 16;
    public static final int NUM_17 = 17;
    public static final int NUM_19 = 19;
    public static final int NUM_22 = 22;
    public static final int NUM_23 = 23;
    public static final int NUM_24 = 24;
    public static final int NUM_25 = 25;
    public static final int NUM_26 = 26;
    public static final int NUM_27 = 27;
    public static final int NUM_30 = 30;
    public static final int NUM_31 = 31;
    public static final int NUM_32 = 32;
    public static final int NUM_51 = 51;
    public static final int NUM_52 = 52;
    public static final int NUM_58 = 58;
    public static final int NUM_59 = 59;
    public static final int NUM_60 = 60;
    /** 解散群聊 */
    public static final int NUM_65 = 65;
    public static final int NUM_61 = 61;
    public static final int NUM_68 = 68;
    public static final int NUM_70 = 70;
    public static final int NUM_71 = 71;
    public static final int NUM_76 = 76;
    public static final int NUM_80 = 80;
    public static final int NUM_90 = 90;
    public static final int NUM_99 = 99;
    public static final int NUM_100 = 100;
    public static final int NUM_180 = 180;
    public static final int NUM_256 = 256;
    public static final int NUM_270 = 270;
    public static final int NUM_360 = 360;
    public static final int NUM_512 = 512;
    public static final int NUM_800 = 800;
    public static final int NUM_1000 = 1000;
    public static final int NUM_1024 = 1024;

    /**
     * String常量
     */
    public static final String CONS_STR_NULL = "null";
    public static final String CONS_STR_TRUE = "true";
    public static final String CONS_STR_FALSE = "false";

    /**
     * 群组用户类型  -1 管理员  0 群组成员  1 不在该群组
     */
    public static final int USER_TYPE_MANGER = -1;
    public static final int USER_TYPE_MEMBER = 0;
    public static final int USER_TYPE_OTHER = 1;

    /**
     * 请求来源  pjyc    xinchangcheng
     */
    public static final String REQUEST_SS = ImSdkApplication.channel;
    public static final String COMMA = ",";
    public static final String CHAT_C2C = "私信";
    public static final String CHAT_GROUP = "约聊";

    /**
     * Base64 工具类
     */
    public static final char CHAR_0X01 = 0x01;
    public static final char CHAR_CAPITAL_Z = 'Z';
    public static final char CHAR_CAPITAL_A = 'A';
    public static final char CHAR_LOWERCASE_Z = 'z';
    public static final char CHAR_LOWERCASE_A = 'a';
    public static final char CHAR_NUMBER_0 = '0';
    public static final char CHAR_NUMBER_9 = '9';
    public static final char CHAR_0X3 = 0x3;
    public static final char CHAR_0XF = 0xf;
    public static final int CHAR_0X023 = 0x023;
    public static final float FLOAT_8F = 0.8f;

    /**
     * 是否第一次启动
     */
    public static final String FIRST_OPEN = "first_open";

    /**
     * 0x003-发送中  0x004-发送失败  0x005-发送成功
     */
    public static final int CHAT_ITEM_SENDING = 0x003;
    public static final int CHAT_ITEM_SEND_ERROR = 0x004;
    public static final int CHAT_ITEM_SEND_SUCCESS = 0x005;
}
