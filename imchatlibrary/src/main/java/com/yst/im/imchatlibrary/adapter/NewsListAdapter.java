package com.yst.im.imchatlibrary.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
//import com.shuyu.gsyvideoplayer.GSYVideoManager;
//import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
//import com.shuyu.gsyvideoplayer.utils.Debuger;
//import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
//import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.Record;
import com.yst.im.imchatlibrary.bean.TemplateMessageEntity;
import com.yst.im.imchatlibrary.manager.MediaManager;
import com.yst.im.imchatlibrary.model.RecallMessageModel;
import com.yst.im.imchatlibrary.ui.activity.ForwardingActivity;
import com.yst.im.imchatlibrary.ui.activity.NewsPhotoViewActivity;
import com.yst.im.imchatlibrary.ui.listener.ViewOnLongClick;
import com.yst.im.imchatlibrary.utils.CommonsUtils;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.utils.PopupWindowUtils;
import com.yst.im.imchatlibrary.widget.AbstractImSmsDialog;
import com.yst.im.imchatlibrary.widget.GifTextView;
import com.yst.im.imchatlibrary.widget.ImRoundedImageView;
//import com.yst.im.imchatlibrary.widget.SampleCoverVideo;
import com.yst.im.imsdk.MessageConstant;
import com.yst.im.imsdk.bean.MessageBean;
import com.yst.im.imsdk.dao.MessageDaoUtils;
import com.yst.im.imsdk.utils.BaseUtils;
import com.yst.im.imsdk.utils.ImThreadPoolUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import okhttp3.Call;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity.NEWS_RECALL;
import static com.yst.im.imchatlibrary.utils.BaseUtils.getRingDuring;
import static com.yst.im.imchatlibrary.utils.BaseUtils.getStringNoEmpty;
import static com.yst.im.imchatlibrary.utils.DefendMpUtils.HTTP;
import static com.yst.im.imchatlibrary.utils.DefendMpUtils.HTTPS;
import static com.yst.im.imchatlibrary.utils.DefendMpUtils.WIDE_VINE;
import static com.yst.im.imchatlibrary.utils.PopupWindowUtils.PopWindowType.MsgSetting;
import static com.yst.im.imsdk.MessageConstant.CONS_STR_FALSE;
import static com.yst.im.imsdk.MessageConstant.CONS_STR_TRUE;
import static com.yst.im.imsdk.MessageConstant.NEWS_ACCEPT_PICTURE;
import static com.yst.im.imsdk.MessageConstant.NEWS_ACCEPT_TEMPLATE_MESSAGE;
import static com.yst.im.imsdk.MessageConstant.NEWS_ACCEPT_TEXT;
import static com.yst.im.imsdk.MessageConstant.NEWS_ACCEPT_VOICE;
import static com.yst.im.imsdk.MessageConstant.NEWS_SEND_PICTURE;
import static com.yst.im.imsdk.MessageConstant.NEWS_SEND_RECALL;
import static com.yst.im.imsdk.MessageConstant.NEWS_SEND_TEMPLATE_MESSAGE;
import static com.yst.im.imsdk.MessageConstant.NEWS_SEND_TEXT;
import static com.yst.im.imsdk.MessageConstant.NEWS_SEND_VOICE;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_1000;
import static com.yst.im.imsdk.MessageConstant.NUM_180;
import static com.yst.im.imsdk.MessageConstant.NUM_2;
import static com.yst.im.imsdk.MessageConstant.NUM_512;
import static com.yst.im.imsdk.MessageConstant.NUM_60;
import static com.yst.im.imsdk.MessageConstant.TEMPLATE_BOTTOM;
import static com.yst.im.imsdk.MessageConstant.TEMPLATE_LEFT;
import static com.yst.im.imsdk.MessageConstant.TEMPLATE_RIGHT;
import static com.yst.im.imsdk.MessageConstant.TEMPLATE_TOP;
import static com.yst.im.imsdk.MessageConstant.TYPE_AVI;
import static com.yst.im.imsdk.MessageConstant.TYPE_INVITE_JINO_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_JION_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_PICTURE;
import static com.yst.im.imsdk.MessageConstant.TYPE_RECALL;
import static com.yst.im.imsdk.MessageConstant.TYPE_RECALL_HISTORY;
import static com.yst.im.imsdk.MessageConstant.TYPE_USER_JINO_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_USER_OUT_GROUP;
import static com.yst.im.imsdk.utils.BaseUtils.getFormatNewsTimeTxt;
import static com.yst.im.imsdk.utils.BaseUtils.getSystemTime;
import static com.yst.im.imsdk.utils.BaseUtils.timeDate;

/**
 * 首页消息会话列表实体类
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/04/14.
 */
public class NewsListAdapter extends BaseAdapter implements RecallMessageModel.RecallMessageListenerCallBack {
    private Activity context;
    private List<MessageBean> newsList;
    private LayoutInflater mInflater;
    private List<AnimationDrawable> mAnimationDrawables = new ArrayList<>();
    private ClipboardManager mClipboardManager;
    private int pos = -1;
    private Handler handler;
    private MessageBean fordMsg;
    public static final String TAG = "AVI";
    private RecallMessageModel mRecallMessageModel;
    private String version;
    private AbstractImSmsDialog notRecallMsgDialog;
    private List<MessageBean> mReCallNewsList;
    private PopupWindowUtils popupWindowUtils;
//    private StandardGSYVideoPlayer curPlayer;
//
//    protected OrientationUtils orientationUtils;

    protected boolean isPlay;

    protected boolean isFull;
    private boolean isShowNickName;
//    private String remark;

    public NewsListAdapter(Activity context, List<MessageBean> newsList, Handler handler) {
        this.context = context;
        this.newsList = newsList;
        this.mInflater = LayoutInflater.from(context);
        this.handler = handler;
        mRecallMessageModel = new RecallMessageModel(context);
        mReCallNewsList = new ArrayList<>();
        mClipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
    }

    @Override
    public int getCount() {
        return null == newsList ? 0 : newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * 加载不同的条目  条目数小于 getViewTypeCount返回的值
     */
    @Override
    public int getViewTypeCount() {
        return 9;

    }

    /**
     * 返回ListView所加载的item的类型
     */
    @Override
    public int getItemViewType(int position) {
        return newsList.get(position).getMessageType();

    }

    /**
     * 渲染列表布局
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderSendText holderSendText = null;
        ViewHolderSendPicture holderSendPicture = null;
        ViewHolderSendVoice holderSendVoice = null;
        ViewHolderAcceptText holderAcceptText = null;
        ViewHolderAcceptPicture holderAcceptPicture = null;
        ViewHolderAcceptVoice holderAcceptVoice = null;
        ViewHolderSendTemplate viewHolderSendTemplate = null;
        ViewHolderAcceptTemplate viewHolderAcceptTemplate = null;
        ViewHolderReCall holderReCall = null;
        convertView = null;
        final MessageBean msgInfo = newsList.get(position);
        switch (getItemViewType(position)) {
            case NEWS_SEND_RECALL:
                if (convertView == null) {
                    holderReCall = new ViewHolderReCall();
                    convertView = mInflater.inflate(R.layout.item_news_recall, null);
                    holderReCall.mTxtNewsRecall = (TextView) convertView.findViewById(R.id.txt_news_recall);
                    convertView.setTag(holderReCall);
                }
                break;
            case NEWS_SEND_TEXT:
                if (convertView == null) {
                    holderSendText = new ViewHolderSendText();
                    convertView = mInflater.inflate(R.layout.item_news_send_txt, null);
                    holderSendText.mTxtNewsSendTextTime = (TextView) convertView.findViewById(R.id.txt_news_send_text_time);
                    holderSendText.mTxtNewsSendName = (TextView) convertView.findViewById(R.id.txt_news_sendName);
                    holderSendText.mTxtNewsSendMsgCount = (GifTextView) convertView.findViewById(R.id.txt_news_send_msg_count);
                    holderSendText.mImgNewsSendUserIcon = (ImageView) convertView.findViewById(R.id.img_news_send_user_icon);
                    holderSendText.mImgNewsTextFail = (ImageView) convertView.findViewById(R.id.img_news_text_fail);
                    holderSendText.mPbNewsTextProgress = (ProgressBar) convertView.findViewById(R.id.pb_news_text_progress);
                    convertView.setTag(holderSendText);
                } else {
                    holderSendText = (ViewHolderSendText) convertView.getTag();
                }

                break;
            case NEWS_SEND_PICTURE:
                if (convertView == null) {
                    holderSendPicture = new ViewHolderSendPicture();
                    convertView = mInflater.inflate(R.layout.item_news_send_picture, null);
                    holderSendPicture.mTxtNewsSendPictureTime = (TextView) convertView.findViewById(R.id.txt_news_send_picture_time);
                    holderSendPicture.mTxtNewsSendPictureUserName = (TextView) convertView.findViewById(R.id.txt_news_send_picture_user_name);
                    holderSendPicture.mImgNewsAcceptPictureImgUrl = (ImageView) convertView.findViewById(R.id.img_news_accept_picture_img_url);
                    holderSendPicture.mImgPicAcceptPictureUserIcon = (ImageView) convertView.findViewById(R.id.img_pic_accept_picture_user_icon);
                    holderSendPicture.mImgNewsPicFail = (ImageView) convertView.findViewById(R.id.img_news_pic_fail);
                    holderSendPicture.mPbNewsPicProgress = (ProgressBar) convertView.findViewById(R.id.pb_news_pic_progress);
//                    holderSendPicture.mVideoSendPlayer = (SampleCoverVideo) convertView.findViewById(R.id.video_send_player);
                    holderSendPicture.mTxtSendVoiceTime = (TextView) convertView.findViewById(R.id.txt_send_voice_time);
                    holderSendPicture.mRelSendIsShowTime = (RelativeLayout) convertView.findViewById(R.id.rel_send_isShow_time);
                    holderSendPicture.mLilNewsSendPic = (RelativeLayout) convertView.findViewById(R.id.lil_news_send_pic);
                    convertView.setTag(holderSendPicture);
                } else {
                    holderSendPicture = (ViewHolderSendPicture) convertView.getTag();
                }

                break;
            case NEWS_SEND_VOICE:
                if (convertView == null) {
                    holderSendVoice = new ViewHolderSendVoice();
                    convertView = mInflater.inflate(R.layout.item_news_send_voice, null);
                    holderSendVoice.mTxtNewsSendVoiceTime = (TextView) convertView.findViewById(R.id.txt_news_send_voice_time);
                    holderSendVoice.mImgNewsSendRed = (ImageView) convertView.findViewById(R.id.img_news_send_red);
                    holderSendVoice.mTxtNewsSendVoiceSecond = (TextView) convertView.findViewById(R.id.txt_news_send_voice_second);
                    holderSendVoice.mImgNewsRightVoiceLine = (ImageView) convertView.findViewById(R.id.img_news_right_voiceLine);
                    holderSendVoice.mLilNewsRightSinger = (LinearLayout) convertView.findViewById(R.id.lil_news_right_singer);
                    holderSendVoice.mImgNewsSendUserIcon = (ImageView) convertView.findViewById(R.id.img_news_send_user_icon);
                    holderSendVoice.mTxtNewsVoiceUser = (TextView) convertView.findViewById(R.id.txt_news_voice_user);
                    holderSendVoice.mImgNewsVoiceFail = (ImageView) convertView.findViewById(R.id.img_news_voice_fail);
                    holderSendVoice.mPbNewsVoiceProgress = (ProgressBar) convertView.findViewById(R.id.pb_news_voice_progress);
                    convertView.setTag(holderSendVoice);
                } else {
                    holderSendVoice = (ViewHolderSendVoice) convertView.getTag();
                }
                break;

            case NEWS_SEND_TEMPLATE_MESSAGE:
                if (convertView == null) {
                    viewHolderSendTemplate = new ViewHolderSendTemplate();
                    convertView = mInflater.inflate(R.layout.item_news_send_temp, null);
                    viewHolderSendTemplate.mTxtTempSendTextTime = (TextView) convertView.findViewById(R.id.txt_temp_send_text_time);
                    viewHolderSendTemplate.mTxtTempSendName = (TextView) convertView.findViewById(R.id.txt_temp_send_name);
                    viewHolderSendTemplate.mLilTempSendMsgCount = (LinearLayout) convertView.findViewById(R.id.lil_temp_send_msg_count);
                    viewHolderSendTemplate.mLilNewsTempBottom = (LinearLayout) convertView.findViewById(R.id.lil_news_temp_bottom);
                    viewHolderSendTemplate.mTvTempBottomTitle = (TextView) convertView.findViewById(R.id.tv_temp_bottom_title);
                    viewHolderSendTemplate.mTvTempBottomContent = (TextView) convertView.findViewById(R.id.tv_temp_bottom_content);
                    viewHolderSendTemplate.mImgTempBottomPic = (ImageView) convertView.findViewById(R.id.img_temp_bottom_pic);
                    viewHolderSendTemplate.mLilNewsTempTop = (LinearLayout) convertView.findViewById(R.id.lil_news_temp_top);
                    viewHolderSendTemplate.mTvTempTopTitle = (TextView) convertView.findViewById(R.id.tv_temp_top_title);
                    viewHolderSendTemplate.mTvTempTopContent = (TextView) convertView.findViewById(R.id.tv_temp_top_content);
                    viewHolderSendTemplate.mImgTempTopPic = (ImageView) convertView.findViewById(R.id.img_temp_top_pic);
                    viewHolderSendTemplate.mLilNewsTempLeft = (LinearLayout) convertView.findViewById(R.id.lil_news_temp_left);
                    viewHolderSendTemplate.mImgTempLeftPic = (ImageView) convertView.findViewById(R.id.img_temp_left_pic);
                    viewHolderSendTemplate.mTvTempLeftTitle = (TextView) convertView.findViewById(R.id.tv_temp_left_title);
                    viewHolderSendTemplate.mTvTempLeftContent = (TextView) convertView.findViewById(R.id.tv_temp_left_content);
                    viewHolderSendTemplate.mLilNewsTempRight = (LinearLayout) convertView.findViewById(R.id.lil_news_temp_right);
                    viewHolderSendTemplate.mTvTempRightTitle = (TextView) convertView.findViewById(R.id.tv_temp_right_title);
                    viewHolderSendTemplate.mTvTempRightContent = (TextView) convertView.findViewById(R.id.tv_temp_right_content);
                    viewHolderSendTemplate.mImgTempRightPic = (ImageView) convertView.findViewById(R.id.img_temp_right_pic);
                    viewHolderSendTemplate.mImgTempSendUserIcon = (ImRoundedImageView) convertView.findViewById(R.id.img_temp_send_user_icon);
                    viewHolderSendTemplate.mImgNewsTempFail = (ImageView) convertView.findViewById(R.id.img_news_temp_fail);
                    viewHolderSendTemplate.mPbNewsTempProgress = (ProgressBar) convertView.findViewById(R.id.pb_news_temp_progress);

                    convertView.setTag(viewHolderSendTemplate);
                } else {
                    viewHolderSendTemplate = (ViewHolderSendTemplate) convertView.getTag();
                }
                break;

            case NEWS_ACCEPT_TEXT:
                if (convertView == null) {
                    holderAcceptText = new ViewHolderAcceptText();
                    convertView = mInflater.inflate(R.layout.item_news_accept_txt, null);
                    holderAcceptText.mTxtNewsAcceptTime = (TextView) convertView.findViewById(R.id.txt_news_accept_time);
                    holderAcceptText.mImgPicAcceptUserIcon = (ImageView) convertView.findViewById(R.id.img_pic_accept_user_icon);
                    holderAcceptText.mTxtNewsAcceptName = (TextView) convertView.findViewById(R.id.txt_news_accept_name);
                    holderAcceptText.mTxtTimeAcceptMsgCount = (GifTextView) convertView.findViewById(R.id.txt_time_accept_msg_count);
                    convertView.setTag(holderAcceptText);
                } else {
                    holderAcceptText = (ViewHolderAcceptText) convertView.getTag();
                }

                break;
            case NEWS_ACCEPT_PICTURE:
                if (convertView == null) {
                    holderAcceptPicture = new ViewHolderAcceptPicture();
                    convertView = mInflater.inflate(R.layout.item_news_accept_picture, null);
                    holderAcceptPicture.mTxtNewsAcceptPictureTime = (TextView) convertView.findViewById(R.id.txt_news_accept_picture_time);
                    holderAcceptPicture.mImgPicAcceptPictureUserIcon = (ImageView) convertView.findViewById(R.id.img_pic_accept_picture_user_icon);
                    holderAcceptPicture.mTxtNewsAcceptUserName = (TextView) convertView.findViewById(R.id.txt_news_accept_user_name);
                    holderAcceptPicture.mImgNewsAcceptPicture = (ImageView) convertView.findViewById(R.id.img_news_accept_picture);
//                    holderAcceptPicture.mVideoAcceptPlayer = (SampleCoverVideo) convertView.findViewById(R.id.video_accept_player);
                    holderAcceptPicture.mTxtAcceptVoiceTime = (TextView) convertView.findViewById(R.id.txt_accept_voice_time);
                    holderAcceptPicture.mRelAccpetIsShowTime = (RelativeLayout) convertView.findViewById(R.id.rel_accpet_isShow_time);

                    convertView.setTag(holderAcceptPicture);
                } else {
                    holderAcceptPicture = (ViewHolderAcceptPicture) convertView.getTag();
                }

                break;
            case NEWS_ACCEPT_VOICE:
                if (convertView == null) {
                    holderAcceptVoice = new ViewHolderAcceptVoice();
                    convertView = mInflater.inflate(R.layout.item_news_accept_voice, null);
                    holderAcceptVoice.mTxtNewsAcceptVoiceTime = (TextView) convertView.findViewById(R.id.txt_news_accept_voice_time);
                    holderAcceptVoice.mImgPicAcceptUserIcon = (ImageView) convertView.findViewById(R.id.img_pic_accept_user_icon);
                    holderAcceptVoice.mImgAcceptVoiceLine = (ImageView) convertView.findViewById(R.id.img_accept_voiceLine);
                    holderAcceptVoice.mLlAcceptSinger = (LinearLayout) convertView.findViewById(R.id.ll_accept_singer);
                    holderAcceptVoice.mTxtAcceptVoiceSecond = (TextView) convertView.findViewById(R.id.txt_accept_voice_second);
                    holderAcceptVoice.mImgAcceptRed = (ImageView) convertView.findViewById(R.id.img_accept_red);
                    holderAcceptVoice.mTxtNewsAcceptVoiceUser = (TextView) convertView.findViewById(R.id.txt_news_accept_voice_user);
                    convertView.setTag(holderAcceptVoice);
                } else {
                    holderAcceptVoice = (ViewHolderAcceptVoice) convertView.getTag();
                }

                break;
            case NEWS_ACCEPT_TEMPLATE_MESSAGE:
                if (convertView == null) {
                    viewHolderAcceptTemplate = new ViewHolderAcceptTemplate();
                    convertView = mInflater.inflate(R.layout.item_news_accept_temp, null);
                    viewHolderAcceptTemplate.mTxtTempAcceptTime = (TextView) convertView.findViewById(R.id.txt_temp_accept_time);
                    viewHolderAcceptTemplate.mImgPicTempUserIcon = (ImRoundedImageView) convertView.findViewById(R.id.img_pic_temp_user_icon);
                    viewHolderAcceptTemplate.mTxtTempAcceptName = (TextView) convertView.findViewById(R.id.txt_temp_accept_name);
                    viewHolderAcceptTemplate.mLilTempAcceptTemp = (LinearLayout) convertView.findViewById(R.id.lil_temp_accept_temp);
                    viewHolderAcceptTemplate.mLilNewsTempBottom = (LinearLayout) convertView.findViewById(R.id.lil_news_temp_bottom);
                    viewHolderAcceptTemplate.mTvTempBottomTitle = (TextView) convertView.findViewById(R.id.tv_temp_bottom_title);
                    viewHolderAcceptTemplate.mTvTempBottomContent = (TextView) convertView.findViewById(R.id.tv_temp_bottom_content);
                    viewHolderAcceptTemplate.mImgTempBottomPic = (ImageView) convertView.findViewById(R.id.img_temp_bottom_pic);
                    viewHolderAcceptTemplate.mLilNewsTempTop = (LinearLayout) convertView.findViewById(R.id.lil_news_temp_top);
                    viewHolderAcceptTemplate.mTvTempTopTitle = (TextView) convertView.findViewById(R.id.tv_temp_top_title);
                    viewHolderAcceptTemplate.mTvTempTopContent = (TextView) convertView.findViewById(R.id.tv_temp_top_content);
                    viewHolderAcceptTemplate.mImgTempTopPic = (ImageView) convertView.findViewById(R.id.img_temp_top_pic);
                    viewHolderAcceptTemplate.mLilNewsTempLeft = (LinearLayout) convertView.findViewById(R.id.lil_news_temp_left);
                    viewHolderAcceptTemplate.mImgTempLeftPic = (ImageView) convertView.findViewById(R.id.img_temp_left_pic);
                    viewHolderAcceptTemplate.mTvTempLeftTitle = (TextView) convertView.findViewById(R.id.tv_temp_left_title);
                    viewHolderAcceptTemplate.mTvTempLeftContent = (TextView) convertView.findViewById(R.id.tv_temp_left_content);
                    viewHolderAcceptTemplate.mLilNewsTempRight = (LinearLayout) convertView.findViewById(R.id.lil_news_temp_right);
                    viewHolderAcceptTemplate.mTvTempRightTitle = (TextView) convertView.findViewById(R.id.tv_temp_right_title);
                    viewHolderAcceptTemplate.mTvTempRightContent = (TextView) convertView.findViewById(R.id.tv_temp_right_content);
                    viewHolderAcceptTemplate.mImgTempRightPic = (ImageView) convertView.findViewById(R.id.img_temp_right_pic);
                    convertView.setTag(viewHolderAcceptTemplate);
                } else {
                    viewHolderAcceptTemplate = (ViewHolderAcceptTemplate) convertView.getTag();
                }

                break;
            default:
                break;
        }
        notRecallMsg();
        mRecallMessageModel.setFindUserListenerCallBack(this);
        switch (getItemViewType(position)) {
            case NEWS_SEND_RECALL:
                assert holderReCall != null;
                if (msgInfo.getType() == TYPE_RECALL_HISTORY || msgInfo.getType() == TYPE_RECALL) {
                    if (msgInfo.getSenderId().equals(MyApp.manager.getId())) {
                        String content = " 你 撤回了一条消息";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content));
                        }
                    } else {
                        String content = "<font color=\"#5197FF\">" + msgInfo.getNickName() + "</font>" + "撤回了一条消息";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content));
                        }
                    }
                } else if (msgInfo.getType() == TYPE_JION_GROUP) {
                    String substring = msgInfo.getContent().substring(msgInfo.getContent().length() - 8, msgInfo.getContent().length());
                    String substring1 = msgInfo.getContent().substring(0, msgInfo.getContent().length() - 8);
                    String content = "<font color=\"#5197FF\">" + substring1 + "</font>" + substring;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content));
                    }
                    holderReCall.mTxtNewsRecall.setVisibility(View.VISIBLE);
                    if (MyApp.manager.getId().equals(msgInfo.getSenderId())) {
                        holderReCall.mTxtNewsRecall.setVisibility(View.GONE);
                    }
                } else if (msgInfo.getType() == TYPE_INVITE_JINO_GROUP) {
                    String substring = msgInfo.getContent().substring(msgInfo.getContent().length() - 4, msgInfo.getContent().length());
                    String substring1 = msgInfo.getContent().substring(0, msgInfo.getContent().length() - 4);
                    String content = "<font color=\"#5197FF\">" + substring1 + "</font>" + substring;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content));
                    }
                } else if (msgInfo.getType() == TYPE_USER_JINO_GROUP) {
                    String substring = msgInfo.getContent().substring(msgInfo.getContent().length() - 4, msgInfo.getContent().length());
                    String substring1 = msgInfo.getContent().substring(0, 2);
                    String substring2 = msgInfo.getContent().substring(2, msgInfo.getContent().length() - 4);
                    String content = substring1 + "<font color=\"#5197FF\">" + substring2 + "</font>" + substring;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content));
                    }
                } else if (msgInfo.getType() == TYPE_USER_OUT_GROUP) {
                    String quit = "退出群聊";
                    int index;
                    String content = msgInfo.getContent();
                    if (msgInfo.getContent().contains(quit)) {
                        index = msgInfo.getContent().indexOf(quit);
                        content = "<font color=\"#5197FF\">" + msgInfo.getContent().substring(0, index + 4) + "</font>" + content.substring(index + 4, content.length());
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        holderReCall.mTxtNewsRecall.setText(Html.fromHtml(content));
                    }
                }
                break;
            case NEWS_SEND_TEXT:
                assert holderSendText != null;
                switch (msgInfo.getSendState()) {
                    case MessageConstant.CHAT_ITEM_SENDING:
                        holderSendText.mPbNewsTextProgress.setVisibility(View.GONE);
                        holderSendText.mImgNewsTextFail.setVisibility(View.GONE);
                        break;
                    case MessageConstant.CHAT_ITEM_SEND_ERROR:
                        holderSendText.mPbNewsTextProgress.setVisibility(View.GONE);
                        holderSendText.mImgNewsTextFail.setVisibility(View.GONE);
                        break;
                    case MessageConstant.CHAT_ITEM_SEND_SUCCESS:
                        holderSendText.mPbNewsTextProgress.setVisibility(View.GONE);
                        holderSendText.mImgNewsTextFail.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
                if (isShowNickName) {
                    holderSendText.mTxtNewsSendName.setText(msgInfo.getNickName());
                } else {
                    holderSendText.mTxtNewsSendName.setVisibility(View.GONE);
                }
                if (position > 0 && getFormatNewsTimeTxt(newsList.get(position).getOccureTime()).equals(getFormatNewsTimeTxt(newsList.get(position - 1).getOccureTime()))) {
                    holderSendText.mTxtNewsSendTextTime.setText("");
                } else {
                    holderSendText.mTxtNewsSendTextTime.setVisibility(View.VISIBLE);
                    holderSendText.mTxtNewsSendTextTime.setText(getFormatNewsTimeTxt(msgInfo.getOccureTime()));
                }

                onClickUserIcon(holderSendText.mImgNewsSendUserIcon, MyApp.manager.getUserId());
                holderSendText.mTxtNewsSendMsgCount.setSpanText(handler, msgInfo.getContent(), true);
                onItemClickView(holderSendText.mTxtNewsSendMsgCount, position);
                Glide.with(context).load(msgInfo.getPortrait()).error(R.mipmap.icon_default).into(holderSendText.mImgNewsSendUserIcon);
                break;
            case NEWS_SEND_PICTURE:
                assert holderSendPicture != null;
                switch (msgInfo.getSendState()) {
                    case MessageConstant.CHAT_ITEM_SENDING:
                        holderSendPicture.mPbNewsPicProgress.setVisibility(View.VISIBLE);
                        holderSendPicture.mImgNewsPicFail.setVisibility(View.GONE);
                        break;
                    case MessageConstant.CHAT_ITEM_SEND_ERROR:
                        holderSendPicture.mPbNewsPicProgress.setVisibility(View.GONE);
                        holderSendPicture.mImgNewsPicFail.setVisibility(View.VISIBLE);
                        break;
                    case MessageConstant.CHAT_ITEM_SEND_SUCCESS:
                        holderSendPicture.mPbNewsPicProgress.setVisibility(View.GONE);
                        holderSendPicture.mImgNewsPicFail.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
                if (position > 0 && getFormatNewsTimeTxt(newsList.get(position).getOccureTime()).equals(getFormatNewsTimeTxt(newsList.get(position - 1).getOccureTime()))) {
                    holderSendPicture.mTxtNewsSendPictureTime.setText("");
                } else {
                    holderSendPicture.mTxtNewsSendPictureTime.setVisibility(View.VISIBLE);
                    holderSendPicture.mTxtNewsSendPictureTime.setText(getFormatNewsTimeTxt(msgInfo.getOccureTime()));
                }

//                if (msgInfo.getType() == TYPE_AVI) {
//                    holderSendPicture.mVideoSendPlayer.setVisibility(View.VISIBLE);
//                    holderSendPicture.mRelSendIsShowTime.setVisibility(View.VISIBLE);
//                    holderSendPicture.mImgNewsAcceptPictureImgUrl.setVisibility(View.VISIBLE);
//                    holderSendPicture.mImgNewsAcceptPictureImgUrl.setBackgroundColor(ContextCompat.getColor(context, R.color.color0000));
//                    initVideo(holderSendPicture.mVideoSendPlayer, msgInfo.getContent(), position);
//                    ImageView imageView = new ImageView(context);
//                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    //本地文件
//                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yst/im/" + msgInfo.getVersion() + ".jpg");
//                    //加载图片
//                    if (file.exists()) {
//                        Glide.with(context).load(file).into(imageView);
//                        holderSendPicture.mVideoSendPlayer.setThumbImageView(imageView);
//                        int duration = holderSendPicture.mVideoSendPlayer.getDuration();
//                        holderSendPicture.mTxtSendVoiceTime.setText(BaseUtils.timeDate(String.valueOf(duration)));
//                    }
//
//                } else
                if (msgInfo.getType() == TYPE_PICTURE) {
//                    holderSendPicture.mVideoSendPlayer.setVisibility(View.GONE);
                    holderSendPicture.mRelSendIsShowTime.setVisibility(View.GONE);
                    holderSendPicture.mImgNewsAcceptPictureImgUrl.setVisibility(View.VISIBLE);
                    Glide.with(context).load(msgInfo.getContent()).into(holderSendPicture.mImgNewsAcceptPictureImgUrl);
                }
                if (isShowNickName) {
                    holderSendPicture.mTxtNewsSendPictureUserName.setText(msgInfo.getNickName());
                } else {
                    holderSendPicture.mTxtNewsSendPictureUserName.setVisibility(View.GONE);
                }
                onItemClickView(holderSendPicture.mImgNewsAcceptPictureImgUrl, position);
                final ViewHolderSendPicture finalHolderSendPicture = holderSendPicture;
                holderSendPicture.mImgNewsAcceptPictureImgUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if (msgInfo.getType() == TYPE_AVI) {
//                            finalHolderSendPicture.mVideoSendPlayer.startPlayLogic();
//                        } else {
                            context.startActivity(new Intent(context, NewsPhotoViewActivity.class).putExtra("picUrl", msgInfo.getContent()));
//                        }
                    }
                });
                onClickUserIcon(holderSendPicture.mImgPicAcceptPictureUserIcon, MyApp.manager.getUserId());
                Glide.with(context).load(msgInfo.getPortrait()).error(R.mipmap.icon_default).into(holderSendPicture.mImgPicAcceptPictureUserIcon);
                break;
            case NEWS_SEND_VOICE:
                assert holderSendVoice != null;
                switch (msgInfo.getSendState()) {
                    case MessageConstant.CHAT_ITEM_SENDING:
                        holderSendVoice.mPbNewsVoiceProgress.setVisibility(View.GONE);
                        holderSendVoice.mImgNewsVoiceFail.setVisibility(View.GONE);
                        break;
                    case MessageConstant.CHAT_ITEM_SEND_ERROR:
                        holderSendVoice.mPbNewsVoiceProgress.setVisibility(View.GONE);
                        holderSendVoice.mImgNewsVoiceFail.setVisibility(View.GONE);
                        break;
                    case MessageConstant.CHAT_ITEM_SEND_SUCCESS:
                        holderSendVoice.mPbNewsVoiceProgress.setVisibility(View.GONE);
                        holderSendVoice.mImgNewsVoiceFail.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
                if (position > 0 && getFormatNewsTimeTxt(newsList.get(position).getOccureTime()).equals(getFormatNewsTimeTxt(newsList.get(position - 1).getOccureTime()))) {
                    holderSendVoice.mTxtNewsSendVoiceTime.setText("");
                } else {
                    holderSendVoice.mTxtNewsSendVoiceTime.setVisibility(View.VISIBLE);
                    holderSendVoice.mTxtNewsSendVoiceTime.setText(getFormatNewsTimeTxt(msgInfo.getOccureTime()));
                }
                Glide.with(context).load(msgInfo.getPortrait()).error(R.mipmap.icon_default).into(holderSendVoice.mImgNewsSendUserIcon);
                onClickUserIcon(holderSendVoice.mImgNewsSendUserIcon, MyApp.manager.getUserId());
                Gson gson = new Gson();
                final Record record = gson.fromJson(msgInfo.getContent(), Record.class);
                holderSendVoice.mImgNewsSendRed.setVisibility(View.GONE);
                holderSendVoice.mTxtNewsSendVoiceSecond.setText(record.getSecond() <= 0 ? 1 + "''" : record.getSecond() + "''");
                if (isShowNickName) {
                    holderSendVoice.mTxtNewsVoiceUser.setText(msgInfo.getNickName());
                } else {
                    holderSendVoice.mTxtNewsVoiceUser.setVisibility(View.GONE);
                }
                //更改并显示录音条长度
                RelativeLayout.LayoutParams ps = (RelativeLayout.LayoutParams) holderSendVoice.mImgNewsRightVoiceLine.getLayoutParams();
                ps.width = CommonsUtils.getVoiceLineWight(context, msgInfo.getSecond());
                //更改语音长条长度
                holderSendVoice.mImgNewsRightVoiceLine.setLayoutParams(ps);
                onItemClickView(holderSendVoice.mImgNewsRightVoiceLine, position);
                //修改数据库语音状态
                record.setPlayed(CONS_STR_TRUE);
                updateVoiceJson(String.valueOf(msgInfo.getVersion()), JSON.toJSONString(record));
                //只要点击就设置为已播放状态（隐藏小红点）
                newsList.get(position).setPlayed(true);
                notifyDataSetChanged();

                //开始设置监听
                final LinearLayout ieaLlSinger = holderSendVoice.mLilNewsRightSinger;
                holderSendVoice.mImgNewsRightVoiceLine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            upDownLoadVoice(newsList.get(position), position, ieaLlSinger);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case NEWS_SEND_TEMPLATE_MESSAGE:
                assert viewHolderSendTemplate != null;
                switch (msgInfo.getSendState()) {
                    case MessageConstant.CHAT_ITEM_SENDING:
                        viewHolderSendTemplate.mPbNewsTempProgress.setVisibility(View.GONE);
                        viewHolderSendTemplate.mImgNewsTempFail.setVisibility(View.GONE);
                        break;
                    case MessageConstant.CHAT_ITEM_SEND_ERROR:
                        viewHolderSendTemplate.mPbNewsTempProgress.setVisibility(View.GONE);
                        viewHolderSendTemplate.mImgNewsTempFail.setVisibility(View.GONE);
                        break;
                    case MessageConstant.CHAT_ITEM_SEND_SUCCESS:
                        viewHolderSendTemplate.mPbNewsTempProgress.setVisibility(View.GONE);
                        viewHolderSendTemplate.mImgNewsTempFail.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
                if (position > 0 && getFormatNewsTimeTxt(newsList.get(position).getOccureTime()).equals(getFormatNewsTimeTxt(newsList.get(position - 1).getOccureTime()))) {
                    viewHolderSendTemplate.mTxtTempSendTextTime.setText("");
                } else {
                    viewHolderSendTemplate.mTxtTempSendTextTime.setVisibility(View.VISIBLE);
                    viewHolderSendTemplate.mTxtTempSendTextTime.setText(getFormatNewsTimeTxt(msgInfo.getOccureTime()));
                }
                if (isShowNickName) {
                    viewHolderSendTemplate.mTxtTempSendName.setText(msgInfo.getNickName());
                } else {
                    viewHolderSendTemplate.mTxtTempSendName.setVisibility(View.GONE);
                }
                Glide.with(context).load(msgInfo.getPortrait()).placeholder(R.mipmap.icon_default).into(viewHolderSendTemplate.mImgTempSendUserIcon);
                onClickUserIcon(viewHolderSendTemplate.mImgTempSendUserIcon, MyApp.manager.getUserId());
                Gson gsons = new Gson();
                final TemplateMessageEntity templateMessageEntity = gsons.fromJson(newsList.get(position).getContent(), TemplateMessageEntity.class);
                if (templateMessageEntity.getTemplateType().equals(TEMPLATE_LEFT)) {
                    viewHolderSendTemplate.mLilNewsTempLeft.setVisibility(View.VISIBLE);
                    viewHolderSendTemplate.mLilNewsTempRight.setVisibility(View.GONE);
                    viewHolderSendTemplate.mLilNewsTempTop.setVisibility(View.GONE);
                    viewHolderSendTemplate.mLilNewsTempBottom.setVisibility(View.GONE);

                    Glide.with(context).load(templateMessageEntity.getImgUrl()).into(viewHolderSendTemplate.mImgTempLeftPic);
                    viewHolderSendTemplate.mTvTempLeftContent.setText(templateMessageEntity.getTemplateContent());
                    viewHolderSendTemplate.mTvTempLeftTitle.setText(templateMessageEntity.getTemplateTitle());

                } else if (templateMessageEntity.getTemplateType().equals(TEMPLATE_RIGHT)) {
                    viewHolderSendTemplate.mLilNewsTempLeft.setVisibility(View.GONE);
                    viewHolderSendTemplate.mLilNewsTempRight.setVisibility(View.VISIBLE);
                    viewHolderSendTemplate.mLilNewsTempTop.setVisibility(View.GONE);
                    viewHolderSendTemplate.mLilNewsTempBottom.setVisibility(View.GONE);

                    Glide.with(context).load(templateMessageEntity.getImgUrl()).into(viewHolderSendTemplate.mImgTempRightPic);
                    viewHolderSendTemplate.mTvTempRightContent.setText(templateMessageEntity.getTemplateContent());
                    viewHolderSendTemplate.mTvTempRightTitle.setText(templateMessageEntity.getTemplateTitle());

                } else if (templateMessageEntity.getTemplateType().equals(TEMPLATE_TOP)) {
                    viewHolderSendTemplate.mLilNewsTempLeft.setVisibility(View.GONE);
                    viewHolderSendTemplate.mLilNewsTempRight.setVisibility(View.GONE);
                    viewHolderSendTemplate.mLilNewsTempTop.setVisibility(View.VISIBLE);
                    viewHolderSendTemplate.mLilNewsTempBottom.setVisibility(View.GONE);

                    Glide.with(context).load(templateMessageEntity.getImgUrl()).into(viewHolderSendTemplate.mImgTempTopPic);
                    viewHolderSendTemplate.mTvTempTopContent.setText(templateMessageEntity.getTemplateContent());
                    viewHolderSendTemplate.mTvTempTopTitle.setText(templateMessageEntity.getTemplateTitle());

                } else if (templateMessageEntity.getTemplateType().equals(TEMPLATE_BOTTOM)) {
                    viewHolderSendTemplate.mLilNewsTempLeft.setVisibility(View.GONE);
                    viewHolderSendTemplate.mLilNewsTempRight.setVisibility(View.GONE);
                    viewHolderSendTemplate.mLilNewsTempTop.setVisibility(View.GONE);
                    viewHolderSendTemplate.mLilNewsTempBottom.setVisibility(View.VISIBLE);

                    Glide.with(context).load(templateMessageEntity.getImgUrl()).into(viewHolderSendTemplate.mImgTempBottomPic);
                    viewHolderSendTemplate.mTvTempBottomContent.setText(templateMessageEntity.getTemplateContent());
                    viewHolderSendTemplate.mTvTempBottomTitle.setText(templateMessageEntity.getTemplateTitle());
                }
                viewHolderSendTemplate.mLilTempSendMsgCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri contentTempSendUrl = Uri.parse(templateMessageEntity.getImgUrl());
                        intent.setData(contentTempSendUrl);
                        context.startActivity(intent);
                    }
                });
                onItemClickView(viewHolderSendTemplate.mLilTempSendMsgCount, position);
                break;

            case NEWS_ACCEPT_TEXT:
                assert holderAcceptText != null;
                if (position > 0 && getFormatNewsTimeTxt(newsList.get(position).getOccureTime()).equals(getFormatNewsTimeTxt(newsList.get(position - 1).getOccureTime()))) {
                    holderAcceptText.mTxtNewsAcceptTime.setText("");
                } else {
                    holderAcceptText.mTxtNewsAcceptTime.setVisibility(View.VISIBLE);
                    holderAcceptText.mTxtNewsAcceptTime.setText(getFormatNewsTimeTxt(msgInfo.getOccureTime()));
                }
                onClickUserIcon(holderAcceptText.mImgPicAcceptUserIcon, msgInfo.getSenderId());
                Glide.with(context).load(msgInfo.getPortrait()).error(R.mipmap.icon_default).into(holderAcceptText.mImgPicAcceptUserIcon);
                if (isShowNickName) {
                    holderAcceptText.mTxtNewsAcceptName.setText(msgInfo.getNickName());
                } else {
                    holderAcceptText.mTxtNewsAcceptName.setVisibility(View.GONE);
                }
                holderAcceptText.mTxtTimeAcceptMsgCount.setSpanText(handler, msgInfo.getContent(), true);
                onItemClickView(holderAcceptText.mTxtTimeAcceptMsgCount, position);
                break;
            case NEWS_ACCEPT_PICTURE:
                assert holderAcceptPicture != null;
                if (position > 0 && getFormatNewsTimeTxt(newsList.get(position).getOccureTime()).equals(getFormatNewsTimeTxt(newsList.get(position - 1).getOccureTime()))) {
                    holderAcceptPicture.mTxtNewsAcceptPictureTime.setText("");
                } else {
                    holderAcceptPicture.mTxtNewsAcceptPictureTime.setVisibility(View.VISIBLE);
                    holderAcceptPicture.mTxtNewsAcceptPictureTime.setText(getFormatNewsTimeTxt(msgInfo.getOccureTime()));
                }
//                if (msgInfo.getType() == TYPE_AVI) {
//                    holderAcceptPicture.mImgNewsAcceptPicture.setVisibility(View.VISIBLE);
//                    holderAcceptPicture.mVideoAcceptPlayer.setVisibility(View.VISIBLE);
//                    initVideo(holderAcceptPicture.mVideoAcceptPlayer, msgInfo.getContent(), position);
//                    ImageView imageView = new ImageView(context);
//                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    //本地文件
//                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yst/im/" + msgInfo.getVersion() + ".jpg");
//                    //加载图片
//                    if (file.exists()) {
//                        Glide.with(context).load(file).into(imageView);
//                        holderAcceptPicture.mVideoAcceptPlayer.setThumbImageView(imageView);
//                    }
//                } else if (msgInfo.getType() == TYPE_PICTURE) {
                    holderAcceptPicture.mRelAccpetIsShowTime.setVisibility(View.GONE);
//                    holderAcceptPicture.mVideoAcceptPlayer.setVisibility(View.GONE);
                    holderAcceptPicture.mImgNewsAcceptPicture.setVisibility(View.VISIBLE);
                    Glide.with(context).load(msgInfo.getContent()).into(holderAcceptPicture.mImgNewsAcceptPicture);
//                }
                onClickUserIcon(holderAcceptPicture.mImgPicAcceptPictureUserIcon, msgInfo.getSenderId());
                Glide.with(context).load(msgInfo.getPortrait()).error(R.mipmap.icon_default).into(holderAcceptPicture.mImgPicAcceptPictureUserIcon);
                Glide.with(context).load(msgInfo.getContent()).into(holderAcceptPicture.mImgNewsAcceptPicture);
                final ViewHolderAcceptPicture finalHolderAcceptPicture = holderAcceptPicture;
                holderAcceptPicture.mImgNewsAcceptPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if (msgInfo.getType() == TYPE_AVI) {
//                            finalHolderAcceptPicture.mVideoAcceptPlayer.startPlayLogic();
//                            resolveFullBtn(finalHolderAcceptPicture.mVideoAcceptPlayer);
//                        } else {
                            context.startActivity(new Intent(context, NewsPhotoViewActivity.class).putExtra("picUrl", msgInfo.getContent()));
//                        }
                    }
                });
                if (isShowNickName) {
                    holderAcceptPicture.mTxtNewsAcceptUserName.setText(msgInfo.getNickName());
                } else {
                    holderAcceptPicture.mTxtNewsAcceptUserName.setVisibility(View.GONE);
                }
                onItemClickView(holderAcceptPicture.mImgNewsAcceptPicture, position);

                break;
            case NEWS_ACCEPT_VOICE:
                assert holderAcceptVoice != null;
                if (position > 0 && getFormatNewsTimeTxt(newsList.get(position).getOccureTime()).equals(getFormatNewsTimeTxt(newsList.get(position - 1).getOccureTime()))) {
                    holderAcceptVoice.mTxtNewsAcceptVoiceTime.setText("");
                } else {
                    holderAcceptVoice.mTxtNewsAcceptVoiceTime.setVisibility(View.VISIBLE);
                    holderAcceptVoice.mTxtNewsAcceptVoiceTime.setText(getFormatNewsTimeTxt(msgInfo.getOccureTime()));
                }
                Glide.with(context).load(msgInfo.getPortrait()).error(R.mipmap.icon_default).into(holderAcceptVoice.mImgPicAcceptUserIcon);
                onClickUserIcon(holderAcceptVoice.mImgPicAcceptUserIcon, msgInfo.getSenderId());
                if (isShowNickName) {
                    holderAcceptVoice.mTxtNewsAcceptVoiceUser.setText(msgInfo.getNickName());
                } else {
                    holderAcceptVoice.mTxtNewsAcceptVoiceUser.setVisibility(View.GONE);
                }
                Gson gsonAcceptVoice = new Gson();
                final Record recordAccept = gsonAcceptVoice.fromJson(msgInfo.getContent(), Record.class);
                holderAcceptVoice.mTxtAcceptVoiceSecond.setText(recordAccept.getSecond() <= 0 ? 1 + "''" : recordAccept.getSecond() + "''");
                if (CONS_STR_FALSE.equals(recordAccept.isPlayed())) {
                    holderAcceptVoice.mImgAcceptRed.setVisibility(View.VISIBLE);
                } else {
                    holderAcceptVoice.mImgAcceptRed.setVisibility(View.GONE);
                }
                //更改并显示录音条长度
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holderAcceptVoice.mImgAcceptVoiceLine.getLayoutParams();
                params.width = CommonsUtils.getVoiceLineWight(context, msgInfo.getSecond());
                //更改语音长条长度
                holderAcceptVoice.mImgAcceptVoiceLine.setLayoutParams(params);
                onItemClickView(holderAcceptVoice.mImgAcceptVoiceLine, position);
                //开始设置监听
                final LinearLayout llSinger = holderAcceptVoice.mLlAcceptSinger;
                holderAcceptVoice.mImgAcceptVoiceLine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //修改数据库语音状态
                        recordAccept.setPlayed(CONS_STR_TRUE);
                        newsList.get(position).setContent(JSON.toJSONString(recordAccept));
                        notifyDataSetChanged();

                        updateVoiceJson(String.valueOf(msgInfo.getVersion()), JSON.toJSONString(recordAccept));
                        //只要点击就设置为已播放状态（隐藏小红点）
                        try {
                            upDownLoadVoice(msgInfo, position, llSinger);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

            case NEWS_ACCEPT_TEMPLATE_MESSAGE:
                assert viewHolderAcceptTemplate != null;
                if (position > 0 && getFormatNewsTimeTxt(newsList.get(position).getOccureTime()).equals(getFormatNewsTimeTxt(newsList.get(position - 1).getOccureTime()))) {
                    viewHolderAcceptTemplate.mTxtTempAcceptTime.setText("");
                } else {
                    viewHolderAcceptTemplate.mTxtTempAcceptTime.setVisibility(View.VISIBLE);
                    viewHolderAcceptTemplate.mTxtTempAcceptTime.setText(getFormatNewsTimeTxt(msgInfo.getOccureTime()));
                }
                if (isShowNickName) {
                    viewHolderAcceptTemplate.mTxtTempAcceptName.setText(msgInfo.getNickName());
                } else {
                    viewHolderAcceptTemplate.mTxtTempAcceptName.setVisibility(View.GONE);
                }
                Glide.with(context).load(msgInfo.getPortrait()).placeholder(R.mipmap.icon_default).into(viewHolderAcceptTemplate.mImgPicTempUserIcon);
                onClickUserIcon(viewHolderAcceptTemplate.mImgPicTempUserIcon, msgInfo.getSenderId());
                Gson gsonAccept = new Gson();
                final TemplateMessageEntity templateAcceptMessageEntity = gsonAccept.fromJson(newsList.get(position).getContent(), TemplateMessageEntity.class);
                if (templateAcceptMessageEntity.getTemplateType().equals(TEMPLATE_LEFT)) {
                    viewHolderAcceptTemplate.mLilNewsTempLeft.setVisibility(View.VISIBLE);
                    viewHolderAcceptTemplate.mLilNewsTempRight.setVisibility(View.GONE);
                    viewHolderAcceptTemplate.mLilNewsTempTop.setVisibility(View.GONE);
                    viewHolderAcceptTemplate.mLilNewsTempBottom.setVisibility(View.GONE);

                    Glide.with(context).load(templateAcceptMessageEntity.getImgUrl()).into(viewHolderAcceptTemplate.mImgTempLeftPic);
                    viewHolderAcceptTemplate.mTvTempLeftContent.setText(templateAcceptMessageEntity.getTemplateContent());
                    viewHolderAcceptTemplate.mTvTempLeftTitle.setText(templateAcceptMessageEntity.getTemplateTitle());

                } else if (templateAcceptMessageEntity.getTemplateType().equals(TEMPLATE_RIGHT)) {
                    viewHolderAcceptTemplate.mLilNewsTempLeft.setVisibility(View.GONE);
                    viewHolderAcceptTemplate.mLilNewsTempRight.setVisibility(View.VISIBLE);
                    viewHolderAcceptTemplate.mLilNewsTempTop.setVisibility(View.GONE);
                    viewHolderAcceptTemplate.mLilNewsTempBottom.setVisibility(View.GONE);

                    Glide.with(context).load(templateAcceptMessageEntity.getImgUrl()).into(viewHolderAcceptTemplate.mImgTempRightPic);
                    viewHolderAcceptTemplate.mTvTempRightContent.setText(templateAcceptMessageEntity.getTemplateContent());
                    viewHolderAcceptTemplate.mTvTempRightTitle.setText(templateAcceptMessageEntity.getTemplateTitle());

                } else if (templateAcceptMessageEntity.getTemplateType().equals(TEMPLATE_TOP)) {
                    viewHolderAcceptTemplate.mLilNewsTempLeft.setVisibility(View.GONE);
                    viewHolderAcceptTemplate.mLilNewsTempRight.setVisibility(View.GONE);
                    viewHolderAcceptTemplate.mLilNewsTempTop.setVisibility(View.VISIBLE);
                    viewHolderAcceptTemplate.mLilNewsTempBottom.setVisibility(View.GONE);

                    Glide.with(context).load(templateAcceptMessageEntity.getImgUrl()).into(viewHolderAcceptTemplate.mImgTempTopPic);
                    viewHolderAcceptTemplate.mTvTempTopContent.setText(templateAcceptMessageEntity.getTemplateContent());
                    viewHolderAcceptTemplate.mTvTempTopTitle.setText(templateAcceptMessageEntity.getTemplateTitle());

                } else if (templateAcceptMessageEntity.getTemplateType().equals(TEMPLATE_BOTTOM)) {
                    viewHolderAcceptTemplate.mLilNewsTempLeft.setVisibility(View.GONE);
                    viewHolderAcceptTemplate.mLilNewsTempRight.setVisibility(View.GONE);
                    viewHolderAcceptTemplate.mLilNewsTempTop.setVisibility(View.GONE);
                    viewHolderAcceptTemplate.mLilNewsTempBottom.setVisibility(View.VISIBLE);
                    Glide.with(context).load(templateAcceptMessageEntity.getImgUrl()).into(viewHolderAcceptTemplate.mImgTempBottomPic);
                    viewHolderAcceptTemplate.mTvTempBottomContent.setText(templateAcceptMessageEntity.getTemplateContent());
                    viewHolderAcceptTemplate.mTvTempBottomTitle.setText(templateAcceptMessageEntity.getTemplateTitle());
                }
                viewHolderAcceptTemplate.mLilTempAcceptTemp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri contentTempAcceptUrl = Uri.parse(templateAcceptMessageEntity.getImgUrl());
                        intent.setData(contentTempAcceptUrl);
                        context.startActivity(intent);
                    }
                });
                onItemClickView(viewHolderAcceptTemplate.mLilTempAcceptTemp, position);
                break;
            default:
                break;
        }
        //定义消息类型 0json 1xml 2 图片 3视频 4文件 5文件夹  6语音
        return convertView;
    }


    private void updateVoiceJson(String version, String record) {

        OkHttpUtils.post()
                .url(Constants.UPDATE_CONTENT_VOICE)
                .addParams("version", version)
                .addParams("msg", record)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .addParams("requestSourceSystem", Constants.REQUEST_SS)
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ImToastUtil.showShortToast(context, "请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("xcc", "upload: 语音修改" + response);
                    }
                });
    }

    /**
     * 下载语音
     *
     * @param msgInfo 消息实体类
     * @throws Exception 泛
     */
    private void upDownLoadVoice(final MessageBean msgInfo, final int position, final LinearLayout llSinger) throws Exception {
        Gson gson = new Gson();
        Record record = gson.fromJson(msgInfo.getContent(), Record.class);
        String url = record.getPath();
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(),
                        "yst/im/" + getSystemTime() + ".amr") {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ImToastUtil.showShortToast(context, "请求失败");
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        ImLog.e("ImLog", "file = " + file.getAbsolutePath());
                        msgInfo.setPath(file.getAbsolutePath());
                        //这里更新数据库小红点。
                        final AnimationDrawable animationDrawable = (AnimationDrawable) llSinger.getBackground();
                        //重置动画
                        resetAnim(animationDrawable);
                        animationDrawable.start();
                        //处理点击正在播放的语音时，可以停止；再次点击时重新播放。
                        if (pos == position) {
                            if (msgInfo.isPlaying()) {
                                newsList.get(position).setPlaying(false);
                                MediaManager.release();
                                animationDrawable.stop();
                                animationDrawable.selectDrawable(0);
                                return;
                            } else {
                                newsList.get(position).setPlaying(true);
                            }
                        }
                        //记录当前位置正在播放。
                        pos = position;
                        newsList.get(position).setPlaying(true);
                        //播放前重置。
                        MediaManager.release();
                        //开始实质播放
                        MediaManager.playSound(msgInfo.getPath(),
                                new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        //显示动画第一帧
                                        animationDrawable.selectDrawable(0);
                                        animationDrawable.stop();
                                        //播放完毕，当前播放索引置为-1。
                                        pos = -1;
                                    }
                                });
                    }
                });
    }

    @Override
    public void onRecllBack(BaseEntity baseEntity) {
        for (int i = 0; i < newsList.size(); i++) {
            if (version.equals(newsList.get(i).getVersion())) {
                newsList.get(i).setMessageType(NEWS_SEND_RECALL);
                newsList.get(i).setType(TYPE_RECALL_HISTORY);
                notifyDataSetChanged();
                MessageDaoUtils.insertOrReplace(newsList.get(i));
                return;
            }
        }
    }


    private class ViewHolderSendText {
        private TextView mTxtNewsSendTextTime;
        private TextView mTxtNewsSendName;
        private GifTextView mTxtNewsSendMsgCount;
        private ImageView mImgNewsSendUserIcon;
        private ImageView mImgNewsTextFail;
        private ProgressBar mPbNewsTextProgress;
    }

    private class ViewHolderSendVoice {
        private TextView mTxtNewsSendVoiceTime;
        private ImageView mImgNewsSendRed;
        private TextView mTxtNewsSendVoiceSecond;
        private ImageView mImgNewsRightVoiceLine;
        private LinearLayout mLilNewsRightSinger;
        private ImageView mImgNewsSendUserIcon;
        private TextView mTxtNewsVoiceUser;
        private ImageView mImgNewsVoiceFail;
        private ProgressBar mPbNewsVoiceProgress;
    }

    private class ViewHolderSendPicture {
        private TextView mTxtNewsSendPictureTime;
        private TextView mTxtNewsSendPictureUserName;
        private ImageView mImgNewsAcceptPictureImgUrl;
        private ImageView mImgPicAcceptPictureUserIcon;
        private ImageView mImgNewsPicFail;
        private ProgressBar mPbNewsPicProgress;
//        private SampleCoverVideo mVideoSendPlayer;
        private TextView mTxtSendVoiceTime;
        private RelativeLayout mRelSendIsShowTime;
        private RelativeLayout mLilNewsSendPic;
    }

    private class ViewHolderSendTemplate {
        private TextView mTxtTempSendTextTime;
        private TextView mTxtTempSendName;
        private LinearLayout mLilTempSendMsgCount;
        private ImRoundedImageView mImgTempSendUserIcon;
        private LinearLayout mLilNewsTempBottom;
        private TextView mTvTempBottomTitle;
        private TextView mTvTempBottomContent;
        private ImageView mImgTempBottomPic;

        private LinearLayout mLilNewsTempTop;
        private TextView mTvTempTopTitle;
        private TextView mTvTempTopContent;
        private ImageView mImgTempTopPic;

        private LinearLayout mLilNewsTempLeft;
        private ImageView mImgTempLeftPic;
        private TextView mTvTempLeftTitle;
        private TextView mTvTempLeftContent;

        private LinearLayout mLilNewsTempRight;
        private TextView mTvTempRightTitle;
        private TextView mTvTempRightContent;
        private ImageView mImgTempRightPic;
        private ImageView mImgNewsTempFail;
        private ProgressBar mPbNewsTempProgress;
    }


    private class ViewHolderAcceptText {
        private TextView mTxtNewsAcceptTime;
        private ImageView mImgPicAcceptUserIcon;
        private TextView mTxtNewsAcceptName;
        private GifTextView mTxtTimeAcceptMsgCount;
    }

    private class ViewHolderAcceptVoice {
        private TextView mTxtNewsAcceptVoiceTime;
        private ImageView mImgPicAcceptUserIcon;
        private ImageView mImgAcceptVoiceLine;
        private LinearLayout mLlAcceptSinger;
        private TextView mTxtAcceptVoiceSecond;
        private ImageView mImgAcceptRed;
        private TextView mTxtNewsAcceptVoiceUser;
    }

    private class ViewHolderAcceptPicture {
        private TextView mTxtNewsAcceptPictureTime;
        private ImageView mImgPicAcceptPictureUserIcon;
        private TextView mTxtNewsAcceptUserName;
        private ImageView mImgNewsAcceptPicture;
//        private SampleCoverVideo mVideoAcceptPlayer;
        private TextView mTxtAcceptVoiceTime;
        private RelativeLayout mRelAccpetIsShowTime;
    }


    private class ViewHolderReCall {
        private TextView mTxtNewsRecall;
    }

    private class ViewHolderAcceptTemplate {
        private TextView mTxtTempAcceptTime;
        private ImRoundedImageView mImgPicTempUserIcon;
        private TextView mTxtTempAcceptName;
        private LinearLayout mLilTempAcceptTemp;

        private LinearLayout mLilNewsTempBottom;
        private TextView mTvTempBottomTitle;
        private TextView mTvTempBottomContent;
        private ImageView mImgTempBottomPic;

        private LinearLayout mLilNewsTempTop;
        private TextView mTvTempTopTitle;
        private TextView mTvTempTopContent;
        private ImageView mImgTempTopPic;

        private LinearLayout mLilNewsTempLeft;
        private ImageView mImgTempLeftPic;
        private TextView mTvTempLeftTitle;
        private TextView mTvTempLeftContent;

        private LinearLayout mLilNewsTempRight;
        private TextView mTvTempRightTitle;
        private TextView mTvTempRightContent;
        private ImageView mImgTempRightPic;

    }

    /**
     * 重置播放动画
     *
     * @param animationDrawable 图片
     */
    private void resetAnim(AnimationDrawable animationDrawable) {
        if (!mAnimationDrawables.contains(animationDrawable)) {
            mAnimationDrawables.add(animationDrawable);
        }
        for (AnimationDrawable ad : mAnimationDrawables) {
            ad.selectDrawable(0);
            ad.stop();
        }
    }

    /**
     * 点击头像
     *
     * @param imageView
     * @param userId
     */
    private void onClickUserIcon(ImageView imageView, final String userId) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("userId", userId);
//                ApplyAffirmActivity.getJumpApplyAffirmActivity(context, 2, bundle);
            }
        });
    }

    private void onItemClickView(final View view, final int position) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fordMsg = newsList.get(position);
                int width = view.getWidth();
                int height = view.getHeight();
                int[] ints = new int[2];
                view.getLocationOnScreen(ints);
                ImLog.e("ImLog", "ints[0] == " + ints[0]);
                ImLog.e("ImLog", "ints[1] == " + ints[1]);
                ImLog.e("ImLog", "width == " + width);
                ImLog.e("ImLog", "height == " + height);
                popupWindowUtils = new PopupWindowUtils(context, new PopupWindowUtils.OnPopWindowClickListener() {
                    @Override
                    public void onPopWindowClickListener(View view) {
                        if (newsList.get(position).getMessageType() == MessageConstant.NEWS_SEND_RECALL) {
                            return;
                        }
                        int i = view.getId();
                        if (i == R.id.txt_pop_window_copy) {
                            mReCallNewsList.add(fordMsg);
                            context.startActivityForResult(new Intent(context, ForwardingActivity.class), NEWS_RECALL);
                        } else if (i == R.id.txt_pop_window_recall) {
                            if (newsList.get(position).getSenderId().equals(MyApp.manager.getId())) {
                                long occureTime = newsList.get(position).getOccureTime();
                                if (((getSystemTime() - occureTime) / NUM_1000) > NUM_60 * NUM_2) {
                                    notRecallMsgDialog.showDialog();
                                    notRecallMsgDialog.setText("发送时间超过2分钟的信息，不能被撤回", "确认", "取消");
                                    notRecallMsgDialog.setTextColor(
                                            ContextCompat.getColor(MyApp.getInstance(), R.color.colorBlck666),
                                            ContextCompat.getColor(MyApp.getInstance(), R.color.colorBlck666),
                                            ContextCompat.getColor(MyApp.getInstance(), R.color.colorBlck666));
                                } else {
                                    version = newsList.get(position).getVersion();
                                    mRecallMessageModel.getRecallMessage(getStringNoEmpty(version));
                                }
                            }
                        } else if (i == R.id.txt_pop_window_transport) {
                            ImToastUtil.showShortToast(context, "已复制");
                            ClipData mClipData = ClipData.newPlainText("text", newsList.get(position).getContent());
                            mClipboardManager.setPrimaryClip(mClipData);
                        }
                    }
                }, MsgSetting, isShowRecall(position), isShowCopy(position));
                if (NUM_180 > ints[1]) {
                    popupWindowUtils.show(view, 0, 0);
                } else {
                    popupWindowUtils.show(view, 0, -BaseUtils.px2dip(context, 250) - height);
                }
                return false;
            }
        });
    }

    private void notRecallMsg() {
        notRecallMsgDialog = new AbstractImSmsDialog((Activity) context, true) {
            @Override
            public void sureClick() {
                super.sureClick();
                notRecallMsgDialog.dismissDialog();
            }
        };
    }

//
//    private void initVideo(final SampleCoverVideo gsyVideoPlayer, String url, int position) {
//        gsyVideoPlayer.setUpLazy(url, false, null, null, "AVI");
//        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
//        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
//        gsyVideoPlayer.setRotateViewAuto(!getListNeedAutoLand());
//        gsyVideoPlayer.setLockLand(!getListNeedAutoLand());
//        gsyVideoPlayer.setPlayTag("AVI");
//        gsyVideoPlayer.setAutoFullWithSize(true);
//        gsyVideoPlayer.setReleaseWhenLossAudio(false);
//        gsyVideoPlayer.setShowFullAnimation(false);
//        gsyVideoPlayer.setIsTouchWiget(true);
//        gsyVideoPlayer.setNeedLockFull(false);
//        gsyVideoPlayer.setPlayPosition(position);
//        gsyVideoPlayer.isGoneBottom();
//        gsyVideoPlayer.setVideoAllCallBack(new GSYSampleCallBack() {
//            @Override
//            public void onClickStartIcon(String url, Object... objects) {
//                super.onClickStartIcon(url, objects);
//            }
//
//            @Override
//            public void onPrepared(String url, Object... objects) {
//                super.onPrepared(url, objects);
//                Debuger.printfLog("onPrepared");
//                boolean full = gsyVideoPlayer.getCurrentPlayer().isIfCurrentIsFullscreen();
//                if (!gsyVideoPlayer.getCurrentPlayer().isIfCurrentIsFullscreen()) {
//                    GSYVideoManager.instance().setNeedMute(true);
//                }
//                curPlayer = (StandardGSYVideoPlayer) objects[1];
//                isPlay = true;
//                if (getListNeedAutoLand()) {
//                    //重力全屏工具类
//                    initOrientationUtils(gsyVideoPlayer, full);
//                    NewsListAdapter.this.onPrepared();
//                }
//                resolveFullBtn(gsyVideoPlayer);
//            }
//
//            @Override
//            public void onQuitFullscreen(String url, Object... objects) {
//                super.onQuitFullscreen(url, objects);
//                isFull = false;
//                GSYVideoManager.instance().setNeedMute(true);
//                if (getListNeedAutoLand()) {
//                    NewsListAdapter.this.onQuitFullscreen();
//                }
//                gsyVideoPlayer.isGoneBottom();
//            }
//
//            @Override
//            public void onEnterFullscreen(String url, Object... objects) {
//                super.onEnterFullscreen(url, objects);
//                GSYVideoManager.instance().setNeedMute(false);
//                isFull = true;
//                gsyVideoPlayer.isGoneBottom();
//                gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
//            }
//
//            @Override
//            public void onAutoComplete(String url, Object... objects) {
//                super.onAutoComplete(url, objects);
//                gsyVideoPlayer.isGoneBottom();
//                curPlayer = null;
//                isPlay = false;
//                isFull = false;
//                if (getListNeedAutoLand()) {
//                    NewsListAdapter.this.onQuitFullscreen();
//                }
//            }
//        });
//    }

//    /**
//     * 全屏幕按键处理
//     */
//    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
//        if (getListNeedAutoLand() && orientationUtils != null) {
//            resolveFull();
//        }
//        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
//    }

    public boolean isFull() {
        return isFull;
    }

    public List<MessageBean> getReCallList() {
        return mReCallNewsList;
    }

//    /**
//     * 列表时是否需要支持重力旋转
//     *
//     * @return 返回true为支持列表重力全屏
//     */
//    public boolean getListNeedAutoLand() {
//        return false;
//    }
//
//    private void initOrientationUtils(StandardGSYVideoPlayer standardGSYVideoPlayer, boolean full) {
//        orientationUtils = new OrientationUtils((Activity) context, standardGSYVideoPlayer);
//        //是否需要跟随系统旋转设置
//        orientationUtils.setEnable(false);
//        orientationUtils.setIsLand((full) ? 1 : 0);
//    }
//
//    private void resolveFull() {
//        if (getListNeedAutoLand() && orientationUtils != null) {
//            //直接横屏
//            orientationUtils.resolveByClick();
//        }
//    }
//
//    private void onQuitFullscreen() {
//        if (orientationUtils != null) {
//            orientationUtils.backToProtVideo();
//        }
//    }
//
//    public void onAutoComplete() {
//        if (orientationUtils != null) {
//            orientationUtils.setEnable(false);
//            orientationUtils.releaseListener();
//            orientationUtils = null;
//        }
//        isPlay = false;
//    }
//
//    public void onPrepared() {
//        if (orientationUtils == null) {
//            return;
//        }
//        //开始播放了才能旋转和全屏
//        orientationUtils.setEnable(false);
//    }
//
//    public void onConfigurationChanged(Activity activity, Configuration newConfig) {
//        //如果旋转了就全屏
//        if (isPlay && curPlayer != null && orientationUtils != null) {
//            curPlayer.onConfigurationChanged(activity, newConfig, orientationUtils, false, true);
//        }
//    }
//
//    public void onBackPressed() {
//        if (orientationUtils != null) {
//            orientationUtils.backToProtVideo();
//        }
//    }
//
//    public void onDestroy() {
//        if (isPlay && curPlayer != null) {
//            curPlayer.getCurrentPlayer().release();
//        }
//        if (orientationUtils != null) {
//            orientationUtils.releaseListener();
//            orientationUtils = null;
//        }
//    }

    public void setNickNameState(boolean isShowNickName) {
        this.isShowNickName = isShowNickName;
        notifyDataSetChanged();
    }

    private boolean isShowRecall(int position) {
        MessageBean messageBean = newsList.get(position);
        boolean showRecall = messageBean.getMessageType() == NEWS_ACCEPT_PICTURE
                || messageBean.getMessageType() == NEWS_ACCEPT_TEXT
                || messageBean.getMessageType() == NEWS_ACCEPT_TEMPLATE_MESSAGE
                || messageBean.getMessageType() == NEWS_ACCEPT_VOICE;
        if (showRecall) {
            return true;
        }
        return false;
    }

    private boolean isShowCopy(int position) {
        if (newsList.get(position).getMessageType() == NEWS_ACCEPT_PICTURE || newsList.get(position).getMessageType() == NEWS_SEND_PICTURE || newsList.get(position).getMessageType() == NEWS_SEND_VOICE || newsList.get(position).getMessageType() == NEWS_ACCEPT_VOICE) {
            return true;
        }
        return false;
    }

}
