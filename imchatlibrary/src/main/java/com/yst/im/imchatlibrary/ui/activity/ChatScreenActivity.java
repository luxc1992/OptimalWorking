package com.yst.im.imchatlibrary.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.adapter.CommonFragmentPagerAdapter;
import com.yst.im.imchatlibrary.adapter.NewsListAdapter;
import com.yst.im.imchatlibrary.asynctask.AsyncTaskUtils;
import com.yst.im.imchatlibrary.asynctask.BaseAsyncCallBack;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractSortEnumType;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.FileUpLoadEntity;
import com.yst.im.imchatlibrary.bean.ForWardingEntity;
import com.yst.im.imchatlibrary.bean.GroupDetailsEntity;
import com.yst.im.imchatlibrary.bean.HistoryMsgEntity;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.bean.IntentGroupEntity;
import com.yst.im.imchatlibrary.bean.LoginEntity;
import com.yst.im.imchatlibrary.bean.Record;
import com.yst.im.imchatlibrary.bean.SelectGroupStateEntity;
import com.yst.im.imchatlibrary.bean.TemplateMessageEntity;
import com.yst.im.imchatlibrary.bean.WebViewShareEntity;
import com.yst.im.imchatlibrary.enumclass.IntentGroupDetail;
import com.yst.im.imchatlibrary.manager.AudioRecordButton;
import com.yst.im.imchatlibrary.manager.MediaManager;
import com.yst.im.imchatlibrary.model.FileUploadModel;
import com.yst.im.imchatlibrary.model.FindGroupDetailModel;
import com.yst.im.imchatlibrary.model.FriendRegistrationModel;
import com.yst.im.imchatlibrary.model.GetOfflineMessageModel;
import com.yst.im.imchatlibrary.model.LoginModel;
import com.yst.im.imchatlibrary.model.QuitLoginModel;
import com.yst.im.imchatlibrary.model.SelectGroupStateModel;
import com.yst.im.imchatlibrary.model.UpdateMessageStateModel;
import com.yst.im.imchatlibrary.ui.fragment.ChatEmotionFragment;
import com.yst.im.imchatlibrary.ui.listener.EditChangedListener;
import com.yst.im.imchatlibrary.utils.AddFriendUtils;
import com.yst.im.imchatlibrary.utils.FileUtils;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.MediaUtils;
import com.yst.im.imchatlibrary.utils.MoreFunctionUtils;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.GlobalOnItemClickManagerUtils;
import com.yst.im.imchatlibrary.utils.ImPermissionHelper;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.utils.MultiImageSelector;
import com.yst.im.imchatlibrary.utils.NoScrollViewPager;
import com.yst.im.imchatlibrary.utils.SignUtils;
import com.yst.im.imchatlibrary.widget.AbstractImSmsDialog;
import com.yst.im.imchatlibrary.widget.ContainsEmojiEditText;
import com.yst.im.imsdk.ChatType;
import com.yst.im.imsdk.MessageConstant;
import com.yst.im.imsdk.bean.MessageBean;
import com.yst.im.imsdk.bean.RxBusEntity;
import com.yst.im.imsdk.dao.MessageDaoUtils;
import com.yst.im.imsdk.utils.ImThreadPoolUtils;
import com.yst.im.imsdk.utils.LocalLog;
import com.yst.im.imsdk.utils.RxBusConstants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;

import static com.yst.im.imchatlibrary.utils.BaseUtils.getUUid;
import static com.yst.im.imchatlibrary.utils.BaseUtils.keyBoardCancle;
import static com.yst.im.imchatlibrary.utils.Constants.REQUEST_SS;
import static com.yst.im.imsdk.MessageConstant.CONS_STR_FALSE;
import static com.yst.im.imsdk.MessageConstant.NEWS_ACCEPT_PICTURE;
import static com.yst.im.imsdk.MessageConstant.NEWS_ACCEPT_TEMPLATE_MESSAGE;
import static com.yst.im.imsdk.MessageConstant.NEWS_ACCEPT_TEXT;
import static com.yst.im.imsdk.MessageConstant.NEWS_ACCEPT_VOICE;
import static com.yst.im.imsdk.MessageConstant.NEWS_SEND_PICTURE;
import static com.yst.im.imsdk.MessageConstant.NEWS_SEND_RECALL;
import static com.yst.im.imsdk.MessageConstant.NEWS_SEND_TEMPLATE_MESSAGE;
import static com.yst.im.imsdk.MessageConstant.NEWS_SEND_TEXT;
import static com.yst.im.imsdk.MessageConstant.NEWS_SEND_VOICE;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_100;
import static com.yst.im.imsdk.MessageConstant.NUM_3;
import static com.yst.im.imsdk.MessageConstant.NUM_65;
import static com.yst.im.imsdk.MessageConstant.NUM_7;
import static com.yst.im.imsdk.MessageConstant.TEMPLATE_MESSAGE;
import static com.yst.im.imsdk.MessageConstant.TYPE_AVI;
import static com.yst.im.imsdk.MessageConstant.TYPE_DELETE_USER;
import static com.yst.im.imsdk.MessageConstant.TYPE_INVITE_JINO_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_JION_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_OUTLOGIN;
import static com.yst.im.imsdk.MessageConstant.TYPE_PICTURE;
import static com.yst.im.imsdk.MessageConstant.TYPE_RECALL;
import static com.yst.im.imsdk.MessageConstant.TYPE_RECALL_HISTORY;
import static com.yst.im.imsdk.MessageConstant.TYPE_TEMPLATE;
import static com.yst.im.imsdk.MessageConstant.TYPE_TXT;
import static com.yst.im.imsdk.MessageConstant.TYPE_USER_JINO_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_USER_OUT_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_USER_OUT_LOGIN;
import static com.yst.im.imsdk.MessageConstant.TYPE_VOICE;
import static com.yst.im.imsdk.utils.BaseUtils.getSystemTime;

/**
 * 单聊 群聊 页面
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/4.
 */
public class ChatScreenActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        View.OnClickListener,
        FileUploadModel.FileUploadListenerCallBack,
        UpdateMessageStateModel.UpdateMessageStateListenerCallBack,
        GetOfflineMessageModel.GetOfflineMessageListenerCallBack,
        SelectGroupStateModel.SelectGroupStateListenerCallBack,
        QuitLoginModel.QuitLoginListenerCallBack,
        LoginModel.LoginListenerCallBack, FindGroupDetailModel.FindGroupDetailListenerCallBack {

    private SmartRefreshLayout mRefNewsRefresh;
    private ListView mLvNewsSession;
    private TextView mTxtNewsVoice;
    private ContainsEmojiEditText mEdtNewsInput;
    private AudioRecordButton mArbNewsVoiceChoose;
    private TextView mTxtNewsEmoji;
    private TextView mTxtNewsMore;
    private TextView mTxtNewsSend;
    private GridView mGvNewsSendType;
    private RelativeLayout mRllNewsEmotionLayout;
    private NoScrollViewPager mVpNewsEmoji;
    private List<MessageBean> newsList;
    private NewsListAdapter newsListAdapter;
    private String mSendMsg;
    private boolean isNewsMoreOpen = false;
    private boolean isNewsVoice = true;
    private ArrayList<Fragment> mEmojiFragments;
    private SimpleAdapter mSimAdapter;
    private boolean isShowEmoji = false;
    private boolean isScrollBottom = true;
    private InputMethodManager mInputMethodManager;
    private ImPermissionHelper mHelper;
    private int pageSize = 10;
    private int pageNumber = 0;
    private boolean isPause;
    /**
     * 是否请求网路数据
     */
    private boolean isRequestNetData = false;
    private CommonFragmentPagerAdapter mEmojiAdapter;
    private Uri albumnUri;
    private List<MessageBean> mReCallNewsList;
    /**
     * 转发
     */
    public static final int NEWS_RECALL = 110;
    public static final int REQUESTCAMERA = 1000;
    public static final int REQUESTALBUMN = 1001;
    public static final int REQUESTCROP = 1002;
    /**
     * 权限申请自定义码
     */
    public static final int GET_PERMISSION_REQUEST = 100;
    private String cameraPath;
    private String cropPath;
    private File cropFile;

    private static final int REQUEST_IMAGE = 1001;
    private ChatEmotionFragment chatEmotionFragment;
    private AbstractTitleView mTitleViewNewsTitle;
    private FileUploadModel mFileUploadModel;
    private UpdateMessageStateModel mUpdateMessageStateModel;
    private GetOfflineMessageModel mGetOfflineMessageModel;
    private SelectGroupStateModel mSelectGroupStateModel;
    private QuitLoginModel mQuitLoginModel;
    private LoginModel mLoginModel;
    private String version = "";
    private String lastId = "";
    private IntentChatEntity intentChatEntity;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImLog.e("ImLog", "isScrollBottom = handleMessage " + isScrollBottom);
            if (isScrollBottom) {
                newsListAdapter.notifyDataSetChanged();
                mLvNewsSession.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                mLvNewsSession.setSelection(newsList.size() - 1);
            } else {
                mLvNewsSession.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
                newsListAdapter.notifyDataSetChanged();
                isScrollBottom = true;
            }
            newsListAdapter.notifyDataSetChanged();
        }
    };

    private int relation = -1;
    private AbstractImSmsDialog notRecallMsgDialog;
    private List<GroupDetailsEntity.GroupsBean> groups = new ArrayList<>();
    private MediaUtils.LoadVideoImageTask loadVideoImageTask;

    @Override
    protected int getLayout() {
        return R.layout.activity_news_session;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initVideoFeature() {
        super.initVideoFeature();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
    }

    /**
     * 初始化
     */
    @Override
    protected void initView() {
        MyApp.addActivity(this);
        mRefNewsRefresh = (SmartRefreshLayout) findViewById(R.id.ref_news_refresh);
        mLvNewsSession = (ListView) findViewById(R.id.lv_news_session);
        mTxtNewsVoice = (TextView) findViewById(R.id.txt_news_voice);
        mEdtNewsInput = (ContainsEmojiEditText) findViewById(R.id.edt_news_input);
        mArbNewsVoiceChoose = (AudioRecordButton) findViewById(R.id.arb_news_voice_choose);
        mTxtNewsEmoji = (TextView) findViewById(R.id.txt_news_emoji);
        mTxtNewsMore = (TextView) findViewById(R.id.txt_news_more);
        mTxtNewsSend = (TextView) findViewById(R.id.txt_news_send);
        mGvNewsSendType = (GridView) findViewById(R.id.gv_news_sendType);
        mRllNewsEmotionLayout = (RelativeLayout) findViewById(R.id.rll_news_emotion_layout);
        mVpNewsEmoji = (NoScrollViewPager) findViewById(R.id.vp_news_emoji);
        mTitleViewNewsTitle = (AbstractTitleView) findViewById(R.id.titleView_news_title);
        mTxtNewsVoice.setOnClickListener(this);
        mEdtNewsInput.setOnClickListener(this);
        mTxtNewsEmoji.setOnClickListener(this);
        mTxtNewsMore.setOnClickListener(this);
        mTxtNewsSend.setOnClickListener(this);
        mFileUploadModel = new FileUploadModel(this);
        mFileUploadModel.setFileUploadListenerCallBack(this);
        mRllNewsEmotionLayout.setVisibility(View.GONE);
        mUpdateMessageStateModel = new UpdateMessageStateModel(this);
        mUpdateMessageStateModel.setUpdateMessageStateListenerCallBack(this);
        mGetOfflineMessageModel = new GetOfflineMessageModel(this);
        mGetOfflineMessageModel.setGetOfflineMessageListenerCallBack(this);
        mSelectGroupStateModel = new SelectGroupStateModel(this);
        mSelectGroupStateModel.setSelectGroupStateListenerCallBack(this);
        FindGroupDetailModel mFindGroupDetailModel = new FindGroupDetailModel(this);
        mFindGroupDetailModel.setFindGroupDetailListenerCallBack(this);
        mQuitLoginModel = new QuitLoginModel(this);
        mQuitLoginModel.setQuitLoginListenerCallBack(this);
        mLoginModel = new LoginModel(this);
        mLoginModel.setLoginListenerCallBack(this);
        //初始化输入法
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        intentChatEntity = (IntentChatEntity) getIntent().getSerializableExtra("acceptUser");
        mFindGroupDetailModel.getFindGroupDetail(intentChatEntity.getAcceptId());
        if (null != intentChatEntity) {
            MyApp.conId = MyApp.manager.getId() + intentChatEntity.getAcceptId();
            //私聊
            if (intentChatEntity.getChatType() == ChatType.C2C) {
                mTitleViewNewsTitle.setRightTvText("");

                mTitleViewNewsTitle.setTitleText(intentChatEntity.getAcceptName());
                mTitleViewNewsTitle.getRightImageIv().setVisibility(View.GONE);
                mTitleViewNewsTitle.getRightImageIv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChatScreenActivity.this, PersonalInfoActivity.class);
                        intent.putExtra("userId", intentChatEntity.getAcceptId());
                        startActivity(intent);
                    }
                });
                //群聊
            } else if (intentChatEntity.getChatType() == ChatType.GROUP) {
                if (intentChatEntity.getAcceptName().length() > NUM_7) {
                    String groupName = intentChatEntity.getAcceptName().substring(0, 7);
                    mTitleViewNewsTitle.setTitleText(groupName + "(" + intentChatEntity.getGroupNum() + ")");
                } else {
                    mTitleViewNewsTitle.setTitleText(intentChatEntity.getAcceptName() + "(" + intentChatEntity.getGroupNum() + ")");
                }
                mTitleViewNewsTitle.setRightTvText("详情");
                mTitleViewNewsTitle.getRightImageIv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentGroupEntity intentGroupEntity = new IntentGroupEntity();
                        intentGroupEntity.setGroupId(intentChatEntity.getAcceptId());
                        intentGroupEntity.setGroupNum(intentChatEntity.getGroupNum());
                        intentGroupEntity.setIntentGroupDetail(IntentGroupDetail.InGroup);
                        GroupDetailsActivity.getJumpGroup(ChatScreenActivity.this, intentGroupEntity);
                    }
                });
            }
        }


        mTitleViewNewsTitle.getLeftBackImageTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtNewsInput.clearFocus();
                if (intentChatEntity.getChatType() == ChatType.C2C) {
                    mUpdateMessageStateModel.updateMessageState(intentChatEntity.getAcceptId(), "1", SignUtils.getTimeStamp());
                } else {
                    mUpdateMessageStateModel.updateMessageState(intentChatEntity.getAcceptId(), "0", SignUtils.getTimeStamp());
                }
            }
        });


        // 创建按钮隐藏显示
        mEdtNewsInput.addTextChangedListener(new EditChangedListener(this, mEdtNewsInput, null, 200, AbstractSortEnumType.disorderly) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mTxtNewsSend.setVisibility(View.GONE);
                    mTxtNewsMore.setVisibility(View.VISIBLE);
                } else {
                    mTxtNewsSend.setVisibility(View.VISIBLE);
                    mTxtNewsMore.setVisibility(View.GONE);
                }
            }
        });
        //更多功能图片适配器
        mSimAdapter = new SimpleAdapter(this,
                MoreFunctionUtils.getInstanceDate(),
                R.layout.item_grid_news_more,
                MoreFunctionUtils.fromArray,
                MoreFunctionUtils.toArray);
        mGvNewsSendType.setAdapter(mSimAdapter);
        //录音及权限
        initListener();
        //根据是否有焦点更新背景
        mEdtNewsInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mGvNewsSendType.setVisibility(View.GONE);
                    mRllNewsEmotionLayout.setVisibility(View.GONE);
                }
            }
        });
        mEmojiFragments = new ArrayList<>();
        chatEmotionFragment = new ChatEmotionFragment();
        mEmojiFragments.add(chatEmotionFragment);
        mEmojiAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), mEmojiFragments);
        mVpNewsEmoji.setAdapter(mEmojiAdapter);
        mVpNewsEmoji.setCurrentItem(0);
        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(mEdtNewsInput);
        notRecallMsg();
    }

    /**
     * 选择图片 及权限
     */
    private void startPhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager
                            .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                            .PERMISSION_GRANTED) {
                cameraPath = System.currentTimeMillis() + ".png";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                        Environment.getExternalStorageDirectory(), cameraPath)));
                startActivityForResult(intent, REQUESTCAMERA);
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(ChatScreenActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA}, GET_PERMISSION_REQUEST);
            }
        } else {
            cameraPath = System.currentTimeMillis() + ".png";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), cameraPath)));
            startActivityForResult(intent, REQUESTCAMERA);
        }
    }

    private void notRecallMsg() {
        notRecallMsgDialog = new AbstractImSmsDialog(this, true) {
            @Override
            public void sureClick() {
                super.sureClick();
                notRecallMsgDialog.dismissDialog();
            }
        };
    }

    @Override
    protected boolean getStatus() {
        return true;
    }

    /**
     * 页面跳转
     *
     * @param context          上下文
     * @param intentChatEntity 传递数据实体
     */
    public static void getJumpChatSource(Context context, IntentChatEntity intentChatEntity) {
        AddFriendUtils addFriendUtils = new AddFriendUtils(context, intentChatEntity);
    }

    /**
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImLog.e("sss", data + "");

        switch (requestCode) {
            case REQUESTCAMERA:
                if (!TextUtils.isEmpty(cameraPath)) {
                    Uri photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), cameraPath));
                    startPhotoZoom(photoUri);
                }
                break;
            case REQUESTCROP:
                ImLog.e("sss", "--crop: " + cropFile);
                if (cropFile.exists()) {
                    ImLog.e("sss", "--crop: " + cropFile);
                    try {
                        mFileUploadModel.getUpLoadPicture(cropFile, TYPE_PICTURE, NEWS_SEND_PICTURE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case TEMPLATE_MESSAGE:
                if (null == data) {
                    return;
                }
                WebViewShareEntity webViewShareEntity = (WebViewShareEntity) data.getSerializableExtra("webViewShareEntity");
                TemplateMessageEntity templateMessageEntity = new TemplateMessageEntity();
                templateMessageEntity.setImgUrl(webViewShareEntity.getImgUrl());
                templateMessageEntity.setShareSource("im");
                templateMessageEntity.setTemplateTitle(webViewShareEntity.getTitle());
                templateMessageEntity.setTemplateContent(webViewShareEntity.getContent());
                templateMessageEntity.setShareSourceImgUrl("http");
                templateMessageEntity.setTemplateType("top");

                String tempContent = JSON.toJSONString(templateMessageEntity);
                final MessageBean mBaseInfo = new MessageBean();
                mBaseInfo.setContent(tempContent);
                mBaseInfo.setSenderId(MyApp.manager.getId());
                mBaseInfo.setType(TYPE_TEMPLATE);
                mBaseInfo.setVersion(getUUid());
                if (intentChatEntity.getChatType().equals(ChatType.GROUP)) {
                    mBaseInfo.setEvent(3);
                    mBaseInfo.setAccepteId(intentChatEntity.getAcceptId());
                } else {
                    mBaseInfo.setEvent(1);
                    mBaseInfo.setAccepteId(intentChatEntity.getAcceptId());
                }
                if (MyApp.isConnectionSocket) {
                    mBaseInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_SUCCESS);
                } else {
                    mBaseInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_ERROR);
                }
                mBaseInfo.setNickName(MyApp.manager.getNickName());
                mBaseInfo.setOccureTime(getSystemTime());
                mBaseInfo.setPortrait(MyApp.manager.getUserIcon());
                mBaseInfo.setMessageType(NEWS_SEND_TEMPLATE_MESSAGE);
                mBaseInfo.setRequestSourceSystem(REQUEST_SS);
                mBaseInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_SUCCESS);
                sendMsg(mBaseInfo);

                break;
            case REQUEST_IMAGE:
                if (null == data) {
                    return;
                }
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList<String> urlList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    for (int i = 0; i < urlList.size(); i++) {
                        File scalFile = FileUtils.scal(urlList.get(i));
                        try {
                            Thread.sleep(300);
                            mFileUploadModel.getUpLoadPicture(scalFile, TYPE_PICTURE, NEWS_SEND_PICTURE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;
            case NEWS_RECALL:
                if (null == data) {
                    return;
                }
                ForWardingEntity forWardingEntity = (ForWardingEntity) data.getSerializableExtra("forWardingEntity");
                mReCallNewsList.addAll(newsListAdapter.getReCallList());
                if (null != forWardingEntity) {
                    List<String> strings = Arrays.asList(forWardingEntity.getAcceptId().split(","));
                    List<String> events = Arrays.asList(forWardingEntity.getEvent().split(","));
                    for (int i = 0; i < strings.size(); i++) {
                        MessageBean messageBean = new MessageBean();
                        messageBean.setContent(mReCallNewsList.get(0).getContent());
                        messageBean.setEvent(Integer.parseInt(events.get(i)));
                        messageBean.setAccepteId(strings.get(i));
                        messageBean.setNickName(MyApp.manager.getNickName());
                        messageBean.setSenderId(MyApp.manager.getId());
                        messageBean.setPortrait(MyApp.manager.getUserIcon());
                        String uUid = getUUid();
                        messageBean.setVersion(uUid);
                        messageBean.setType(mReCallNewsList.get(0).getType());
                        messageBean.setOccureTime(getSystemTime());
                        if (mReCallNewsList.get(0).getType() == TYPE_TXT) {
                            messageBean.setMessageType(MessageConstant.NEWS_SEND_TEXT);
                        } else if (mReCallNewsList.get(0).getType() == TYPE_PICTURE) {
                            messageBean.setMessageType(MessageConstant.NEWS_SEND_PICTURE);
                        } else if (mReCallNewsList.get(0).getType() == TYPE_AVI) {
                            messageBean.setMessageType(MessageConstant.NEWS_SEND_PICTURE);
                        } else if (mReCallNewsList.get(0).getType() == TYPE_VOICE) {
                            messageBean.setMessageType(MessageConstant.NEWS_SEND_VOICE);
                        } else if (mReCallNewsList.get(0).getType() == TYPE_TEMPLATE) {
                            messageBean.setMessageType(MessageConstant.NEWS_SEND_TEMPLATE_MESSAGE);
                        }
                        ImLog.e("imxxx", messageBean.toString());
                        sendMsg(messageBean);
                        if (intentChatEntity.getAcceptId().equals(newsList.get(i).getAccepteId())) {
                            newsList.add(messageBean);
                            newsListAdapter.notifyDataSetChanged();
                        }
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 初始化语音
     */
    private void initListener() {
        mArbNewsVoiceChoose.setHasRecordPromission(false);
        //        授权处理
        mHelper = new ImPermissionHelper(this);
        mHelper.requestPermissions("请授予[录音]，[读写]权限，否则无法录音",
                new ImPermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
                        mArbNewsVoiceChoose.setHasRecordPromission(true);
                        mArbNewsVoiceChoose.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
                            @Override
                            public void onFinished(float seconds, String filePath) {
                                Record record = new Record();
                                record.setSecond((int) seconds <= 0 ? 1 : (int) seconds);
                                record.setPath(filePath);
                                ImLog.e("ImLog", "语音路径=  " + filePath);
                                record.setPlayed(CONS_STR_FALSE);
                                record.setPlaying(CONS_STR_FALSE);
                                try {
                                    upLoadVoice(record);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void doAfterDenied(String... permission) {
                        mArbNewsVoiceChoose.setHasRecordPromission(false);
                        Toast.makeText(ChatScreenActivity.this, "请授权,否则无法录音", Toast.LENGTH_SHORT).show();
                    }
                }, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    /**
     * 初始化下拉刷新
     */
    private void listRefresh() {
        mRefNewsRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                isScrollBottom = false;
                if (!isRequestNetData) {
                    getLocalDb();
                } else {
                    getHistoryMessage();
                }
                refreshlayout.finishRefresh(NUM_100);
            }
        });

    }


    private void getHistoryMessage() {
        pageNumber++;
        //结束加载
        try {
            if (intentChatEntity.getChatType().equals(ChatType.GROUP)) {
                getGroupChatHistory(intentChatEntity.getAcceptId(), REQUEST_SS, pageSize, pageNumber, lastId);
            } else {
                getHistory(MyApp.manager.getId(), intentChatEntity.getAcceptId(), pageSize, pageNumber, lastId);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询本地数据
     */
    private void getLocalDb() {
        try {
            if (intentChatEntity.getChatType().equals(ChatType.GROUP)) {
                getLocalMessageBeanList(MyApp.manager.getId() + intentChatEntity.getAcceptId());
            } else {
                getLocalMessageBeanList(MyApp.manager.getId() + intentChatEntity.getAcceptId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询本地数据
     */
    private void getLocalMessageBeanList(String acceptId) {
        ImLog.e("ImLog", "本地查询条件 ->>> con_id= " + MyApp.manager.getId() + acceptId);
        List<MessageBean> list = MessageDaoUtils.queryList(pageNumber, pageSize, acceptId);
        if (list.size() == 0) {
            isRequestNetData = true;
            pageNumber = 0;
            if (newsList.size() > 0) {
                lastId = String.valueOf(newsList.get(0).getOccureTime());
            }
            ImLog.e("ImLog", "本地查询没了 ->>> lastId= " + lastId);
            getHistoryMessage();
        } else {
            Collections.reverse(list);
            ImLog.e("ImLog", "本地查询 ->>> 条数= " + list.size());
            ImLog.e("ImLog", "本地查询 ->>> = " + list.toString());
            for (int j = 0; j < list.size(); j++) {
                for (int i = 0; i < groups.size(); i++) {
                    if (groups.get(i).getUserId().equals(list.get(j).getSenderId()) && !list.get(j).getSenderId().equals(MyApp.manager.getId())) {
                        list.get(j).setNickName(groups.get(i).getNickName());
                    }
                }
                newsList.add(j, list.get(j));
            }
            handler.sendEmptyMessage(0);
            pageNumber++;
        }
        ImLog.e("ImLog", "list = " + list.size());
    }

    @Override
    protected void initData() {
        newsList = new ArrayList<>();
        mReCallNewsList = new ArrayList<>();
        newsListAdapter = new NewsListAdapter(ChatScreenActivity.this, newsList, handler);
        mLvNewsSession.setAdapter(newsListAdapter);
        mLvNewsSession.setSelection(newsListAdapter.getCount());
        mGvNewsSendType.setOnItemClickListener(this);
        listRefresh();
        if (intentChatEntity.getChatType().equals(ChatType.GROUP)) {
            newsListAdapter.setNickNameState(true);
            mSelectGroupStateModel.getSelectGroupState(intentChatEntity.getAcceptId());
        } else {
            newsListAdapter.setNickNameState(false);
//            newsListAdapter.setFriendRemark(intentChatEntity.getAcceptName());
        }
        getLocalDb();
        mGetOfflineMessageModel.getGetOfflineMessage();
    }

    @Subscribe(code = RxBusConstants.CONST_UPDATE_NAME, threadMode = ThreadMode.MAIN)
    public void messageReceive(String titleName) {
        if (titleName != null) {
            mTitleViewNewsTitle.setTitleText(titleName);
        }
    }

    /**
     * 消息回调
     *
     * @param msgInfo 实体类
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageReceive(MessageBean msgInfo) {
        if (msgInfo.getEvent() == NUM_3) {
            for (int i = 0; i < groups.size(); i++) {
                if (String.valueOf(groups.get(i).getUserId()).equals(msgInfo.getSenderId())) {
                    msgInfo.setNickName(groups.get(i).getNickName());
                }
            }
        }
        if (msgInfo == null) {
            ImLog.e("ImLog", "MessageBean ---ChatScreenActivity-- 为空");
            return;
        }
        //处理不用存储的通知
        if (msgInfo.getEvent() == NUM_1 || msgInfo.getEvent() == NUM_3) {
            switch (msgInfo.getType()) {
                case NUM_65:
                    ImToastUtil.showLongToast(ChatScreenActivity.this, "解散群聊");
                    finish();
                    break;
                case TYPE_DELETE_USER:
                    finish();
                    break;
                case TYPE_OUTLOGIN:
                    if (relation != NUM_0) {
                        finish();
                    }
                    break;
//                case TYPE_USER_OUT_LOGIN:
//                    MyApp.getMsClient().close();
//                    MyApp.getMsClient().isUserOut = true;
//                    startActivity(new Intent(ChatScreenActivity.this, SingleLoginActivity.class));
//                    finish();
//                    break;
                default:
                    break;
            }
        }

        boolean isReceive = (msgInfo.getEvent() == NUM_1 && intentChatEntity.getChatType() == ChatType.C2C
                && msgInfo.getSenderId().equals(intentChatEntity.getAcceptId()))
                || (msgInfo.getEvent() == NUM_3 && intentChatEntity.getChatType() == ChatType.GROUP
                && msgInfo.getGroupId().equals(intentChatEntity.getAcceptId()));
        ImLog.e("ImLog", "intentChatEntity.getAcceptId() = " + intentChatEntity.getAcceptId());
        ImLog.e("ImLog", "isReceive = " + isReceive);
        if (isReceive) {
            ImLog.e("ImLog", "ImLog.getType() = " + msgInfo.getType());
            switch (msgInfo.getType()) {
                case TYPE_TXT:
                    msgInfo.setMessageType(NEWS_ACCEPT_TEXT);
                    newsList.add(newsList.size(), msgInfo);
                    handler.sendEmptyMessage(0);
                    break;
                case TYPE_PICTURE:
                    msgInfo.setMessageType(NEWS_ACCEPT_PICTURE);
                    newsList.add(newsList.size(), msgInfo);
                    handler.sendEmptyMessage(0);
                    break;
                case TYPE_AVI:
                    msgInfo.setMessageType(NEWS_ACCEPT_PICTURE);
                    newsList.add(newsList.size(), msgInfo);
                    handler.sendEmptyMessage(0);
                    break;
                case TYPE_VOICE:
                    msgInfo.setMessageType(NEWS_ACCEPT_VOICE);
                    Gson gson = new Gson();
                    Record record = gson.fromJson(msgInfo.getContent(), Record.class);
                    msgInfo.setPath(record.getPath());
                    newsList.add(newsList.size(), msgInfo);
                    handler.sendEmptyMessage(0);
                    break;
                case TYPE_TEMPLATE:
                    if (msgInfo.getSenderId().equals(MyApp.manager.getId())) {
                        msgInfo.setMessageType(MessageConstant.NEWS_SEND_TEMPLATE_MESSAGE);
                        newsList.add(newsList.size(), msgInfo);
                    } else {
                        msgInfo.setMessageType(MessageConstant.NEWS_ACCEPT_TEMPLATE_MESSAGE);
                        newsList.add(newsList.size(), msgInfo);
                    }
                    handler.sendEmptyMessage(0);
                    break;
                case TYPE_RECALL:
                case TYPE_RECALL_HISTORY:
                    for (int i = 0; i < newsList.size(); i++) {
                        if (msgInfo.getVersion().equals(newsList.get(i).getVersion())) {
                            newsList.get(i).setMessageType(NEWS_SEND_RECALL);
                            newsList.get(i).setType(TYPE_RECALL_HISTORY);
                        }
                    }
                    handler.sendEmptyMessage(0);
                    break;
                case TYPE_INVITE_JINO_GROUP:
                    msgInfo.setMessageType(NEWS_SEND_RECALL);
                    newsList.add(newsList.size(), msgInfo);
                    handler.sendEmptyMessage(0);
                    break;
                case TYPE_USER_JINO_GROUP:
                    msgInfo.setMessageType(NEWS_SEND_RECALL);
                    newsList.add(newsList.size(), msgInfo);
                    handler.sendEmptyMessage(0);
                    break;
                case TYPE_USER_OUT_GROUP:
                    msgInfo.setMessageType(NEWS_SEND_RECALL);
                    newsList.add(newsList.size(), msgInfo);
                    handler.sendEmptyMessage(0);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                //  跳转相册
                MultiImageSelector.create(this)
                        .multi()
                        .count(9)
                        .showCamera(false)
                        .start(this, REQUEST_IMAGE);
                break;
            case 1:
                startPhone();
                break;
            case 2:
                Intent tempIntent = new Intent(ChatScreenActivity.this, ImWebViewActivity.class);
                startActivityForResult(tempIntent, TEMPLATE_MESSAGE);
                break;
            default:
                break;
        }
    }

    /**
     * 上传语音
     *
     * @param record 语音实体
     * @throws Exception 泛异常
     */
    private void upLoadVoice(final Record record) throws Exception {
        File fileVoice = new File(record.getPath());
        OkHttpUtils.post()
                .url(Constants.URL_FILE_UPLOAD)
                .addFile("urlFile", fileVoice.getName(), fileVoice)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ImToastUtil.showShortToast(ChatScreenActivity.this, "请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ImLog.e("ImLog", "response = " + response);
                        Gson gson = new Gson();
                        FileUpLoadEntity upLoad = gson.fromJson(response, FileUpLoadEntity.class);
                        if (upLoad.getCode() == 0) {
                            record.setPath(upLoad.getContent());
                            final MessageBean msgInfo = new MessageBean();
                            msgInfo.setContent(JSON.toJSONString(record));
                            msgInfo.setMessageType(NEWS_SEND_VOICE);
                            msgInfo.setNickName(MyApp.manager.getNickName());
                            msgInfo.setSenderId(MyApp.manager.getId());
                            if (intentChatEntity.getChatType().equals(ChatType.GROUP)) {
                                msgInfo.setEvent(3);
                                msgInfo.setAccepteId(intentChatEntity.getAcceptId());
                            } else {
                                msgInfo.setEvent(1);
                                msgInfo.setAccepteId(intentChatEntity.getAcceptId());
                            }
                            if (MyApp.isConnectionSocket) {
                                msgInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_SUCCESS);
                            } else {
                                msgInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_ERROR);
                            }
                            msgInfo.setGroupChat(true);
//                            消息类型
                            msgInfo.setType(TYPE_VOICE);
                            msgInfo.setMessageType(NEWS_SEND_VOICE);
                            msgInfo.setOccureTime(getSystemTime());
                            msgInfo.setPortrait(MyApp.manager.getUserIcon());
                            msgInfo.setVersion(getUUid());
                            msgInfo.setRequestSourceSystem(REQUEST_SS);
                            msgInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_SUCCESS);
                            newsList.add(newsList.size(), msgInfo);
                            handler.sendEmptyMessage(0);
                            sendMsg(msgInfo);

                        } else {
                            ImToastUtil.showShortToast(ChatScreenActivity.this, upLoad.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取历史消息
     *
     * @param senderId 用户id
     * @param lastId   本地种最后一条消息的Id
     * @throws IOException 异常
     */
    public void getHistory(final String senderId, final String accepteId, int pageSize, int pageNumber, String lastId) throws IOException {
        OkHttpUtils
                .post()
                .url(Constants.GET_HISTORY_MSG)
                .addParams("senderId", senderId)
                .addParams("accepteId", accepteId)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNumber", String.valueOf(pageNumber))
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .addParams("lastId", lastId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ImToastUtil.showShortToast(ChatScreenActivity.this, "请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("xxxx", "onResponse: " + response);
                        Gson gson = new Gson();
                        HistoryMsgEntity historyMsgEntity = gson.fromJson(response, HistoryMsgEntity.class);
                        if (historyMsgEntity.getCode() == 0) {
                            List<HistoryMsgEntity.MessageBean> messageBeen = historyMsgEntity.getContent();
                            if (messageBeen.size() == 0) {
                                ImToastUtil.showShortToast(ChatScreenActivity.this, historyMsgEntity.getMsg());
                                return;
                            }
                            Collections.reverse(messageBeen);
                            for (int i = 0; i < messageBeen.size(); i++) {
                                MessageBean mess = new MessageBean();
                                mess.setContactsId(senderId + accepteId);
                                mess.setRequestSourceSystem(messageBeen.get(i).getRequestSourceSystem());
                                mess.setNickName(messageBeen.get(i).getNickName());
                                mess.setOccureTime(messageBeen.get(i).getOccureTime());
                                mess.setAccepteId(messageBeen.get(i).getAccepteId());
                                mess.setSenderId(messageBeen.get(i).getSenderId());
                                mess.setContent(messageBeen.get(i).getContent());
                                mess.setId(null);
                                mess.setGroupChat(messageBeen.get(i).isGroupChat());
                                mess.setType(messageBeen.get(i).getType());
                                mess.setPassword(messageBeen.get(i).getPassword());
                                mess.setVersion(messageBeen.get(i).getVersion());
                                mess.setOccureTime(messageBeen.get(i).getOccureTime());
                                mess.setPortrait(messageBeen.get(i).getPortrait());
                                if (messageBeen.get(i).getSenderId().equals(MyApp.manager.getId())) {
                                    switch (messageBeen.get(i).getType()) {
                                        case TYPE_TXT:
                                            mess.setMessageType(NEWS_SEND_TEXT);
                                            break;
                                        case TYPE_PICTURE:
                                        case TYPE_AVI:
                                            mess.setMessageType(NEWS_SEND_PICTURE);
                                            break;
                                        case TYPE_VOICE:
                                            mess.setMessageType(NEWS_SEND_VOICE);
                                            break;
                                        case TYPE_TEMPLATE:
                                            mess.setMessageType(NEWS_SEND_TEMPLATE_MESSAGE);
                                            break;
                                        case TYPE_RECALL_HISTORY:
                                        case TYPE_RECALL:
                                            mess.setMessageType(NEWS_SEND_RECALL);
                                            break;
                                        default:
                                            break;
                                    }
                                } else {
                                    switch (messageBeen.get(i).getType()) {
                                        case TYPE_TXT:
                                            mess.setMessageType(NEWS_ACCEPT_TEXT);
                                            break;
                                        case TYPE_PICTURE:
                                        case TYPE_AVI:
                                            mess.setMessageType(NEWS_ACCEPT_PICTURE);
                                            break;
                                        case TYPE_VOICE:
                                            mess.setMessageType(NEWS_ACCEPT_VOICE);
                                            break;
                                        case TYPE_TEMPLATE:
                                            mess.setMessageType(NEWS_ACCEPT_TEMPLATE_MESSAGE);
                                            break;
                                        case TYPE_RECALL_HISTORY:
                                        case TYPE_RECALL:
                                            mess.setMessageType(NEWS_SEND_RECALL);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                newsList.add(i, mess);
                            }
                            MessageDaoUtils.insertOrReplaceInTx(newsList);
                            handler.sendEmptyMessage(0);
                        } else {
                            ImToastUtil.showShortToast(ChatScreenActivity.this, historyMsgEntity.getMsg());
                        }
                    }
                });
    }

    /**
     * 获取历史消息
     *
     * @param groupId 用户id
     * @param lastId  本地种最后一条消息的Id
     * @throws IOException 异常
     */
    public void getGroupChatHistory(String groupId, final String requestSourceSystem, int pageSize, final int pageNumber, String lastId) throws IOException {
        OkHttpUtils
                .post()
                .url(Constants.GROUP_HISTORY)
                .addParams("groupId", groupId)
                .addParams("token", MyApp.manager.getToken())
                .addParams("id", MyApp.manager.getId())
                .addParams("requestSourceSystem", requestSourceSystem)
                .addParams("pageSize", String.valueOf(pageSize))
                .addParams("pageNumber", String.valueOf(pageNumber))
                .addParams("lastId", lastId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ImToastUtil.showShortToast(ChatScreenActivity.this, "请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        HistoryMsgEntity historyMsgEntity = gson.fromJson(response, HistoryMsgEntity.class);
                        if (historyMsgEntity.getCode() == 0) {
                            List<HistoryMsgEntity.MessageBean> messageBeen = historyMsgEntity.getContent();
                            if (messageBeen.size() == 0) {
                                ImToastUtil.showShortToast(ChatScreenActivity.this, historyMsgEntity.getMsg());
                                return;
                            }
                            Collections.reverse(messageBeen);
                            for (int i = 0; i < messageBeen.size(); i++) {
                                for (int j = 0; j < groups.size(); j++) {
                                    if (groups.get(j).getUserId().equals(messageBeen.get(i).getSenderId()) && !messageBeen.get(i).getSenderId().equals(MyApp.manager.getId())) {
                                        messageBeen.get(i).setNickName(groups.get(j).getNickName());
                                    }
                                }


                                MessageBean mess = new MessageBean();
                                mess.setContactsId(MyApp.manager.getId() + intentChatEntity.getAcceptId());
                                mess.setRequestSourceSystem(messageBeen.get(i).getRequestSourceSystem());
                                mess.setNickName(messageBeen.get(i).getNickName());
                                mess.setOccureTime(messageBeen.get(i).getOccureTime());
                                mess.setAccepteId(messageBeen.get(i).getAccepteId());
                                mess.setSenderId(messageBeen.get(i).getSenderId());
                                mess.setContent(messageBeen.get(i).getContent());
                                mess.setId(null);
                                mess.setGroupChat(messageBeen.get(i).isGroupChat());
                                mess.setType(messageBeen.get(i).getType());
                                mess.setPassword(messageBeen.get(i).getPassword());
                                mess.setVersion(messageBeen.get(i).getVersion());
                                mess.setOccureTime(messageBeen.get(i).getOccureTime());
                                mess.setPortrait(messageBeen.get(i).getPortrait());
                                if (messageBeen.get(i).getSenderId().equals(MyApp.manager.getId())) {
                                    switch (messageBeen.get(i).getType()) {
                                        case TYPE_TXT:
                                            mess.setMessageType(NEWS_SEND_TEXT);
                                            break;
                                        case TYPE_PICTURE:
                                        case TYPE_AVI:
                                            mess.setMessageType(NEWS_SEND_PICTURE);
                                            break;
                                        case TYPE_VOICE:
                                            mess.setMessageType(NEWS_SEND_VOICE);
                                            break;
                                        case TYPE_TEMPLATE:
                                            mess.setMessageType(NEWS_SEND_TEMPLATE_MESSAGE);
                                            break;
                                        case TYPE_INVITE_JINO_GROUP:
                                        case TYPE_RECALL_HISTORY:
                                        case TYPE_JION_GROUP:
                                        case TYPE_USER_JINO_GROUP:
                                        case TYPE_USER_OUT_GROUP:
                                        case TYPE_RECALL:
                                            messageBeen.get(i).setMessageType(NEWS_SEND_RECALL);
                                            mess.setMessageType(messageBeen.get(i).getMessageType());
                                            break;
                                        default:
                                            break;
                                    }
                                } else {
                                    switch (messageBeen.get(i).getType()) {
                                        case TYPE_TXT:
                                            mess.setMessageType(NEWS_ACCEPT_TEXT);
                                            break;
                                        case TYPE_PICTURE:
                                        case TYPE_AVI:
                                            mess.setMessageType(NEWS_ACCEPT_PICTURE);
                                            break;
                                        case TYPE_VOICE:
                                            mess.setMessageType(NEWS_ACCEPT_VOICE);
                                            break;
                                        case TYPE_TEMPLATE:
                                            mess.setMessageType(NEWS_ACCEPT_TEMPLATE_MESSAGE);
                                            break;
                                        case TYPE_INVITE_JINO_GROUP:
                                        case TYPE_JION_GROUP:
                                        case TYPE_RECALL_HISTORY:
                                        case TYPE_USER_JINO_GROUP:
                                        case TYPE_USER_OUT_GROUP:
                                        case TYPE_RECALL:
                                            mess.setMessageType(NEWS_SEND_RECALL);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                newsList.add(i, mess);
                            }
                            MessageDaoUtils.insertOrReplaceInTx(newsList);
                            handler.sendEmptyMessage(0);
                        } else {
                            ImToastUtil.showShortToast(ChatScreenActivity.this, historyMsgEntity.getMsg());
                        }
                    }
                });
    }

    /**
     * 直接把参数交给mHelper就行了
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                //读写内存权限
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;
                if (!writeGranted) {
                    size++;
                }
                //录音权限
                int recordPermissionResult = grantResults[1];
                boolean recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!recordPermissionGranted) {
                    size++;
                }
                //相机权限
                int cameraPermissionResult = grantResults[2];
                boolean cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    size++;
                }
                if (size == 0) {
                    cameraPath = System.currentTimeMillis() + ".png";
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                            Environment.getExternalStorageDirectory(), cameraPath)));
                    startActivityForResult(intent, REQUESTCAMERA);
                } else {
                    Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                }
            }
        }
        mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onPause() {
        //保证在退出该页面时，终止语音播放
        mEdtNewsInput.clearFocus();
        MediaManager.release();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置回掉
        if (!isNewsMoreOpen) {
            mGvNewsSendType.setVisibility(View.GONE);
            isNewsMoreOpen = true;
        }
        if (!isShowEmoji) {
            mRllNewsEmotionLayout.setVisibility(View.GONE);
            isShowEmoji = true;
        }
        isPause = false;
    }

    @Subscribe(code = RxBusConstants.CONST_LOCAL_UPDATE, threadMode = ThreadMode.MAIN)
    public void socketReceive(RxBusEntity rxBusEntity) {
        if (rxBusEntity == null) {
            ImLog.e("ImLog", "MessageBean --ExpandableListViewFragment--- 为空");
            return;
        }
        if ((rxBusEntity.getCode() == RxBusConstants.CONST_CODE_MESSAGE_CALLBACK)) {

            ImLog.e("ImLog", "消息回执=  " + rxBusEntity.getContent());
            for (int i = 0; i < newsList.size(); i++) {
                if (rxBusEntity.getContent().equals(newsList.get(i).getVersion())) {
                    newsList.get(i).setSendState(MessageConstant.CHAT_ITEM_SEND_SUCCESS);
                    newsListAdapter.notifyDataSetChanged();
                }
            }
        } else if (rxBusEntity.getCode() == RxBusConstants.CONST_LOCAL_UPDATE) {
            pageNumber = 0;
            newsList.clear();
            if (null != newsListAdapter) {
                newsListAdapter.notifyDataSetChanged();
            }
        } else if (rxBusEntity.getCode() == RxBusConstants.CONST_CODE_RESET_CONN) {
            if (MyApp.activityList.size() == NUM_0) {
                initClient();
            }
//            if (MyApp.isLogin) {
//                MyApp.isLogin = false;
//            } else {
//                if (isActive) {
//                    mLoginModel.login(MyApp.manager.getPhone(), MyApp.manager.getPassword());
//                }
//            }
        }
    }

    /**
     * 点击事件
     *
     * @param v view
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.txt_news_voice) {
            keyBoardCancle(this);
            if (isNewsVoice) {
                mEdtNewsInput.setVisibility(View.GONE);
                mArbNewsVoiceChoose.setVisibility(View.VISIBLE);
                isNewsVoice = false;
            } else {
                mEdtNewsInput.setVisibility(View.VISIBLE);
                mArbNewsVoiceChoose.setVisibility(View.GONE);
                isNewsVoice = true;
            }

            //输入框
        } else if (i == R.id.edt_news_input) {
            //设置输入框可聚集
            mEdtNewsInput.setFocusable(true);
            //设置触摸聚焦
            mEdtNewsInput.setFocusableInTouchMode(true);
            //请求焦点
            mEdtNewsInput.requestFocus();
            //获取焦点
            mEdtNewsInput.findFocus();
            // 显示输入
            mInputMethodManager.showSoftInput(mEdtNewsInput, InputMethodManager.SHOW_FORCED);
            mGvNewsSendType.setVisibility(View.GONE);
            mRllNewsEmotionLayout.setVisibility(View.GONE);
            isNewsMoreOpen = false;

            //发送按钮
        } else if (i == R.id.txt_news_send) {
            mSendMsg = mEdtNewsInput.getText().toString();
            if ("".equals(mSendMsg) || mSendMsg.length() == 0) {
                Toast.makeText(ChatScreenActivity.this, "请输入用户ID", Toast.LENGTH_SHORT).show();
                return;
            }
            final MessageBean mBaseInfo = new MessageBean();
            mBaseInfo.setContent(mSendMsg);
            mBaseInfo.setSenderId(MyApp.manager.getId());
            mBaseInfo.setType(0);
            mBaseInfo.setVersion(getUUid());
            mBaseInfo.setOccureTime(getSystemTime());
            mBaseInfo.setNickName(MyApp.manager.getNickName());
            mBaseInfo.setPortrait(MyApp.manager.getUserIcon());
            mBaseInfo.setMessageType(NEWS_SEND_TEXT);
            mBaseInfo.setRequestSourceSystem(REQUEST_SS);
            mBaseInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_SUCCESS);
            if (MyApp.isConnectionSocket) {
                mBaseInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_SUCCESS);
            } else {
                mBaseInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_ERROR);
            }
            if (intentChatEntity.getChatType().equals(ChatType.GROUP)) {
                mBaseInfo.setEvent(3);
                mBaseInfo.setAccepteId(intentChatEntity.getAcceptId());
                newsList.add(newsList.size(), mBaseInfo);
                handler.sendEmptyMessage(0);
                sendMsg(mBaseInfo);
                mEdtNewsInput.setText("");
            } else {
                mBaseInfo.setEvent(1);
                mBaseInfo.setAccepteId(intentChatEntity.getAcceptId());
                newsList.add(mBaseInfo);
                handler.sendEmptyMessage(0);
                sendMsg(mBaseInfo);
                mEdtNewsInput.setText("");
            }
            //表情
        } else if (i == R.id.txt_news_emoji) {
            mGvNewsSendType.setVisibility(View.GONE);
            isNewsMoreOpen = true;
            keyBoardCancle(this);
            if (!isShowEmoji) {
                mRllNewsEmotionLayout.setVisibility(View.GONE);
                isShowEmoji = true;
            } else {
                mRllNewsEmotionLayout.setVisibility(View.VISIBLE);
                isShowEmoji = false;
            }
            if (mInputMethodManager.isActive()) {
                // 隐藏输入法
                mInputMethodManager.hideSoftInputFromWindow(mEdtNewsInput.getWindowToken(), 0);
            }
            // 更多--暂时只有图片 拍照

        } else if (i == R.id.txt_news_more) {
            mRllNewsEmotionLayout.setVisibility(View.GONE);
            isShowEmoji = true;
            if (mInputMethodManager.isActive()) {
                // 隐藏输入法
                mInputMethodManager.hideSoftInputFromWindow(mEdtNewsInput.getWindowToken(), 0);
            }
            if (!isNewsMoreOpen) {
                mGvNewsSendType.setVisibility(View.GONE);
                isNewsMoreOpen = true;
            } else {
                mGvNewsSendType.setVisibility(View.VISIBLE);
                isNewsMoreOpen = false;
            }
        }

    }

    /**
     * 发送消息
     *
     * @param mBaseInfo 消息实体
     */
    private void sendMsg(final MessageBean mBaseInfo) {
        if (mBaseInfo.getEvent() == NUM_1) {
            mBaseInfo.setContactsId(MyApp.manager.getId() + mBaseInfo.getAccepteId());
        } else {
            mBaseInfo.setContactsId(MyApp.manager.getId() + mBaseInfo.getAccepteId());
        }
        mBaseInfo.setIsRead(0);
        mBaseInfo.setId(null);
        mBaseInfo.setOccureTime(getSystemTime());
        ImThreadPoolUtils.THREAD_FACTORY.newThread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyApp.getMsClient().sendMessage(mBaseInfo.getPortrait(), mBaseInfo.getContent(), mBaseInfo.getAccepteId(), mBaseInfo.getSenderId(), mBaseInfo.getEvent(), -1L, mBaseInfo.getType()
                            , mBaseInfo.getVersion(), mBaseInfo.isGroupChat(), mBaseInfo.getNickName(), mBaseInfo.getOccureTime(), mBaseInfo.getPassword(), mBaseInfo.getRequestSourceSystem());
                    MessageDaoUtils.insertOrReplace(mBaseInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    LocalLog.e("ImLog", "e - " + e.getMessage());
                    Log.e("ImLog", "e - " + e.getMessage());
                }
            }
        }).start();
    }


    /**
     * 初始化长连接
     */
    private void initClient() {
        ImThreadPoolUtils.THREAD_FACTORY.newThread(new Runnable() {
            @Override
            public void run() {
                MyApp.getMsClient().init(Constants.HOST, Constants.PORT, MyApp.manager.getId(), REQUEST_SS, MyApp.manager.getToken());
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApp.conId = "";
    }

    @Override
    public void onFileUpload(final FileUpLoadEntity fileUpLoadEntity) {
        ImLog.e("地址", fileUpLoadEntity.getContent());
        if (fileUpLoadEntity.getCode() == 0) {
            final MessageBean msgInfo = new MessageBean();
            msgInfo.setContent(fileUpLoadEntity.getContent());
            msgInfo.setGroupChat(false);
            msgInfo.setRequestSourceSystem(REQUEST_SS);
            msgInfo.setNickName(MyApp.manager.getNickName());
            msgInfo.setMessageType(fileUpLoadEntity.getMessageType());
            msgInfo.setType(fileUpLoadEntity.getType());
            msgInfo.setSenderId(MyApp.manager.getId());
            if (intentChatEntity.getChatType().equals(ChatType.GROUP)) {
                msgInfo.setEvent(3);
                msgInfo.setAccepteId(intentChatEntity.getAcceptId());
            } else {
                msgInfo.setEvent(1);
                msgInfo.setAccepteId(intentChatEntity.getAcceptId());
            }
            if (MyApp.isConnectionSocket) {
                msgInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_SUCCESS);
            } else {
                msgInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_ERROR);
            }
            msgInfo.setSendState(MessageConstant.CHAT_ITEM_SEND_SUCCESS);
            msgInfo.setPortrait(MyApp.manager.getUserIcon());
            msgInfo.setOccureTime(getSystemTime());
            msgInfo.setVersion(getUUid());
            newsList.add(msgInfo);
            handler.sendEmptyMessage(0);
            sendMsg(msgInfo);
        } else {
            Toast.makeText(ChatScreenActivity.this, fileUpLoadEntity.getMsg(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onUpdateMessageState(BaseEntity baseEntity) {
        finish();
    }

    @Override
    public void onSelectGroupState(SelectGroupStateEntity.ContentBean selectGroupStateEntity) {
        relation = selectGroupStateEntity.getRelation();
    }

    @Override
    public void onGetOfflineMessage(HistoryMsgEntity historyMsgEntity) {
        if (historyMsgEntity.getCode() == 0) {
            List<HistoryMsgEntity.MessageBean> messageBeen = historyMsgEntity.getContent();
            List<MessageBean> messageBeens = new ArrayList<>();
            if (messageBeen.size() == 0) {
                return;
            }
            for (int i = 0; i < messageBeen.size(); i++) {
                if (messageBeen.get(i).getEvent() == NUM_1 || messageBeen.get(i).getEvent() == NUM_3) {
                    if (messageBeen.get(i).getType() == NUM_65
                            || messageBeen.get(i).getType() == TYPE_DELETE_USER
                            || messageBeen.get(i).getType() == TYPE_OUTLOGIN
                            || messageBeen.get(i).getType() == TYPE_USER_OUT_LOGIN) {
                        continue;
                    }
                    MessageBean mess = new MessageBean();
                    if (messageBeen.get(i).getEvent() == NUM_1) {
                        if (MyApp.manager.getId().equals(messageBeen.get(i).getSenderId())) {
                            mess.setContactsId(MyApp.manager.getId() + messageBeen.get(i).getAccepteId());
                        } else {
                            mess.setContactsId(MyApp.manager.getId() + messageBeen.get(i).getSenderId());
                        }
                    } else {
                        mess.setContactsId(MyApp.manager.getId() + messageBeen.get(i).getGroupId());
                    }
                    mess.setRequestSourceSystem(messageBeen.get(i).getRequestSourceSystem());
                    mess.setNickName(messageBeen.get(i).getNickName());
                    mess.setOccureTime(messageBeen.get(i).getOccureTime());
                    mess.setAccepteId(messageBeen.get(i).getAccepteId());
                    mess.setSenderId(messageBeen.get(i).getSenderId());
                    mess.setContent(messageBeen.get(i).getContent());
                    mess.setId(null);
                    mess.setGroupChat(messageBeen.get(i).isGroupChat());
                    mess.setType(messageBeen.get(i).getType());
                    mess.setPassword(messageBeen.get(i).getPassword());
                    mess.setVersion(messageBeen.get(i).getVersion());
                    mess.setOccureTime(messageBeen.get(i).getOccureTime());
                    mess.setPortrait(messageBeen.get(i).getPortrait());
                    if (MyApp.manager.getId().equals(messageBeen.get(i).getSenderId())) {
                        switch (messageBeen.get(i).getType()) {
                            case TYPE_TXT:
                                messageBeen.get(i).setMessageType(NEWS_SEND_TEXT);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            case TYPE_PICTURE:
                                messageBeen.get(i).setMessageType(NEWS_SEND_PICTURE);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            case TYPE_AVI:
                                messageBeen.get(i).setMessageType(NEWS_SEND_PICTURE);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            case TYPE_VOICE:
                                messageBeen.get(i).setMessageType(NEWS_SEND_VOICE);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            case TYPE_INVITE_JINO_GROUP:
                            case TYPE_RECALL_HISTORY:
                            case TYPE_JION_GROUP:
                            case TYPE_USER_JINO_GROUP:
                            case TYPE_USER_OUT_GROUP:
                                messageBeen.get(i).setMessageType(NEWS_SEND_RECALL);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            case TYPE_RECALL:
                                messageBeen.get(i).setType(TYPE_RECALL_HISTORY);
                                mess.setType(messageBeen.get(i).getType());
                                messageBeen.get(i).setMessageType(NEWS_SEND_RECALL);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            default:
                                break;
                        }
                    } else {
                        switch (messageBeen.get(i).getType()) {
                            case TYPE_TXT:
                                messageBeen.get(i).setMessageType(NEWS_ACCEPT_TEXT);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            case TYPE_AVI:
                                messageBeen.get(i).setMessageType(NEWS_SEND_PICTURE);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            case TYPE_PICTURE:
                                messageBeen.get(i).setMessageType(NEWS_ACCEPT_PICTURE);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            case TYPE_VOICE:
                                messageBeen.get(i).setMessageType(NEWS_ACCEPT_VOICE);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            case TYPE_RECALL:
                                messageBeen.get(i).setType(TYPE_RECALL_HISTORY);
                                mess.setType(messageBeen.get(i).getType());
                                messageBeen.get(i).setMessageType(NEWS_SEND_RECALL);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            case TYPE_INVITE_JINO_GROUP:
                            case TYPE_RECALL_HISTORY:
                            case TYPE_JION_GROUP:
                            case TYPE_USER_JINO_GROUP:
                            case TYPE_USER_OUT_GROUP:
                                messageBeen.get(i).setMessageType(NEWS_SEND_RECALL);
                                mess.setMessageType(messageBeen.get(i).getMessageType());
                                break;
                            default:
                                break;
                        }
                    }
                    messageBeens.add(mess);
                    MessageDaoUtils.insertOrReplace(mess);
                    handler.sendEmptyMessage(0);
                }
            }
        }
    }

    /**
     * 设置退出提示： 对退出的设置时间监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (intentChatEntity.getChatType() == ChatType.C2C) {
                mUpdateMessageStateModel.updateMessageState(intentChatEntity.getAcceptId(), "1", SignUtils.getTimeStamp());
            } else {
                mUpdateMessageStateModel.updateMessageState(intentChatEntity.getAcceptId(), "0", SignUtils.getTimeStamp());
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onQuitLogin(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            MyApp.manager.setLoginState(false);
        }
        mLoginModel.login(MyApp.manager.getPhone(), MyApp.manager.getPassword());
    }

    @Override
    public void onUserLogin(LoginEntity contentBean) {
        if (contentBean.getCode() == NUM_0) {
            MyApp.manager.setLoginState(true);
            MyApp.manager.setId(contentBean.getContent().getUserId());
            MyApp.manager.setToken(contentBean.getContent().getToken());
            MyApp.manager.setNickName(contentBean.getContent().getNickName());
            MyApp.manager.setPhone(contentBean.getContent().getPhone());
            MyApp.manager.setUserIcon(contentBean.getContent().getUserIcon());
            MyApp.manager.setAddress(contentBean.getContent().getAddress());
            MyApp.manager.setSex(contentBean.getContent().getSex());
            ImLog.e("ImLog", "onUserLogin = 重连登陆成功 -->>>> Home");
            initClient();
            pageNumber = 0;
            newsList.clear();
            mGetOfflineMessageModel.getGetOfflineMessage();
        }
    }


    @Override
    public void onFindGroupDetail(GroupDetailsEntity groupDetailsEntity) {
        groups.clear();
        groups.addAll(groupDetailsEntity.getGroups());
    }

    /**
     * 对图片进行裁剪
     *
     * @param uri uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        cropPath = System.currentTimeMillis() + ".png";
        cropFile = new File(Environment.getExternalStorageDirectory(), cropPath);
        intent.putExtra("output", Uri.fromFile(cropFile));
        intent.putExtra("outputFormat", "JPEG");
        startActivityForResult(intent, REQUESTCROP);
    }

}
