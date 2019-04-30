package com.yst.onecity.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.im.Config;
import com.im.SoInstallMgr;
import com.im.Util;
import com.im.av.logic.manage.IMCommitManager;
import com.taobao.av.logic.media.TaoMediaRecorder;
import com.taobao.av.ui.view.CircularProgressDrawable;
import com.taobao.av.ui.view.SizeChangedNotifier;
import com.taobao.av.ui.view.recordline.ClipManager;
import com.taobao.av.ui.view.recordline.RecorderTimeline;
import com.taobao.av.ui.view.recordline.VideoBean;
import com.taobao.av.util.CameraHelper;
import com.taobao.av.util.DensityUtil;
import com.taobao.av.util.FileUtils;
import com.taobao.av.util.MediaFileUtils;
import com.taobao.av.util.PermissionUtils;
import com.taobao.av.util.SystemUtil;
import com.taobao.media.MediaEncoder;
import com.taobao.media.MediaEncoderMgr;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.bean.EventBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Formatter;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO101;
import static com.yst.onecity.Constant.NO4;

/**
 * 首页-发布视频资讯
 *
 * @author songbinbin
 * @version 1.0.1
 * @date 2018/2/25
 */
public class PublishInfoVideoActivity extends BaseActivity implements View.OnClickListener, SizeChangedNotifier.Listener {

    @BindView(R.id.publish_video_iv_back)
    ImageView ivBack;
    @BindView(R.id.publish_video_iv_cameraRotate)
    ImageView ivCameraRotate;
    @BindView(R.id.publish_video_camera_frame)
    SizeChangedNotifier frame;
    @BindView(R.id.publish_video_iv_recorder)
    ImageView ivRecorder;
    @BindView(R.id.publish_video_iv_ok)
    ImageView ivOk;
    @BindView(R.id.publish_video_iv_delete)
    ImageView ivDelete;
    @BindView(R.id.publish_video_camera_view)
    SurfaceView mCameraPreview;
    @BindView(R.id.publish_video_iv_notice_recordlimit)
    ImageView ivNoticeRecordLimit;

    /**
     * 视频质量
     */
    private int quality = 1;
    /**
     * 最大录制时间
     */
    private int maxDuration = '\uea60';
    /**
     * 最小录制时长
     */
    private int minDuration = 5000;

    /**
     * 视频录制管理类
     */
    private ClipManager mClipManager;
    /**
     * 视频类型（长/短）
     */
    private int mVideoType = 0;
    private boolean enableClickRecord = false;
    private Handler mSafeHandler;
    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;
    private boolean isVideoRecording = false;
    private TaoMediaRecorder mTaoMediaRecorder;
    private int cameraPosition = SystemUtil.getCameraFacingBack();
    private int mPreviewWidth = 640;
    private int mPreviewHeight = 480;
    private AudioManager mAudioManager;
    private RecorderTimeline mRecorderTimeline;
    private Animation alpahAnimation;
    private Animation scaleAnimation;
    private View mProgressDialogView;
    private CircularProgressDrawable mProgressDrawable;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;

    private int mType;
    private final String TAG;
    private boolean mSurfaceAcquired;
    private final SurfaceHolder.Callback surfaceCallback;
    /**
     * 分段视频角标
     */
    private int mVideoIndex;
    private boolean isUserPress;
    private boolean isLongPress;
    private Runnable mLongPressRunnable;
    private final View.OnTouchListener Recorder_OnTouchListener;
    private long startTime;
    private Runnable runnableTimer;
    private final AudioManager.OnAudioFocusChangeListener mAudioFocusListener;
    /**
     * 从哪个页面跳转来，决定拍摄完成后去到哪
     * 0，首页
     * 1，发布产品计划
     */
    private String from;


    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_info_video;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        //本类上下文
        Context mContext = this;
        quality = 0;
        //判断手机是否支持录制视频
        if (checkIsUnSupportVersion()) {
            Toast.makeText(this, getString(R.string.taorecorder_notsupport), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            final String enCode = "MediaEncode";
            if (MediaEncoder.isLoadMediaEncodeFailed() && SoInstallMgr.loadFileSo(enCode, "", mContext)) {
                MediaEncoder.setLoadMediaEncodeFailed(false);
            }
            //设置视频类型（长/短）
            setVideoType();
            mSafeHandler = new Handler();
            mSurfaceHolder = mCameraPreview.getHolder();
            addSurfaceCallBack();
            mSurfaceHolder.setType(3);

            mAudioManager = (AudioManager) getApplication().getSystemService(Context.AUDIO_SERVICE);
            //请求音频焦点
            requestAudioFocus();
            //视频录制管理类
            mClipManager = new ClipManager();
            //设置最大录制时长
            mClipManager.setMaxDuration(maxDuration);
            //设置最小录制时长
            mClipManager.setMinDuration(minDuration);
            //时长进度条
            mRecorderTimeline = new RecorderTimeline(findViewById(R.id.record_timeline), mClipManager);
            //设置进度条
            mRecorderTimeline.setItemDrawable(R.drawable.aliwx_sv_recorder_timeline_clip_selector);
            //透明动画
            alpahAnimation = AnimationUtils.loadAnimation(this, R.anim.taorecorder_alpha_reverse);
            //缩放动画
            scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.taorecorder_scale_reverse);
            //初始化处理进度progressDialog
            initProgressDialog();

            mFormatBuilder = new StringBuilder();
            mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
            String model = Build.MODEL;
            if (Config.isDebug()) {
                Log.d(TAG + "@sv", "Build.MODEL = " + model);
            }
        }
    }

    /**
     * 设置监听项
     */
    @Override
    public void setListener() {
        //录制视频按钮监听
        ivRecorder.setOnTouchListener(Recorder_OnTouchListener);
        //确定
        ivOk.setOnClickListener(this);
        //关闭页面
        ivBack.setOnClickListener(this);
        //切换镜头
        ivCameraRotate.setOnClickListener(this);
        //摄影区大小
        frame.setOnSizeChangedListener(this);
        //删除
        ivDelete.setOnClickListener(this);
    }

    /**
     * 录制时间在onCreate方法里设置
     */
    public PublishInfoVideoActivity() {
        mType = Config.CURRENT_TYPE;
        TAG = "IMRecordVideo";
        mSurfaceAcquired = false;
        surfaceCallback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mSurfaceAcquired = true;
                startPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mSurfaceAcquired = false;
                stopPreview();
            }
        };
        mVideoIndex = 0;
        isUserPress = false;
        isLongPress = false;
        mLongPressRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isVideoRecording && mTaoMediaRecorder != null && mTaoMediaRecorder.canStartRecord()) {
                    isLongPress = true;
                    startRecord();
                } else if (mTaoMediaRecorder == null) {
                    recordError();
                }

            }
        };

        Recorder_OnTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case 0:
                        if (!isUserPress) {
                            if (isVideoRecording && enableClickRecord && 1 == mVideoType) {
                                stopRecord();
                                return true;
                            }
                            isUserPress = true;
                        }

                        getSafeHandler().postDelayed(mLongPressRunnable, 250L);
                        break;
                    case 1:
                    case 3:
                        if (isUserPress) {
                            getSafeHandler().removeCallbacks(mLongPressRunnable);
                            if (isLongPress) {
                                if (isVideoRecording) {
                                    stopRecord();
                                }
                            } else if (!isVideoRecording && mTaoMediaRecorder != null && mTaoMediaRecorder.canStartRecord() && enableClickRecord && 1 == mVideoType) {
                                startRecord();
                            } else if (mTaoMediaRecorder == null) {
                                recordError();
                            }
                        }
                        isUserPress = false;
                        isLongPress = false;
                    case 2:
                    default:
                        break;
                }
                return true;
            }
        };

        startTime = 0L;

        //录制时长计时器
        runnableTimer = new Runnable() {
            @Override
            public void run() {
                if (mTaoMediaRecorder != null) {
                    if (startTime == 0L && !mTaoMediaRecorder.isRecording()) {
                        getSafeHandler().postDelayed(this, 25L);
                    } else {
                        if (startTime == 0L) {
                            startTime = System.currentTimeMillis();
                        }
                        mClipManager.onRecordFrame(System.currentTimeMillis() - startTime);
                        //设置录制时长
                        setRecordTime();

                        if (mClipManager.isMaxDurationReached()) {
                            onMaxDurationReached();
                        } else {
                            getSafeHandler().postDelayed(this, 25L);
                        }
                    }
                }
            }
        };
        mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case -3:
                    case -2:
                    case -1:
                    case 0:
                    case 1:
                    default:
                }
            }
        };
    }

    private void addSurfaceCallBack() {
        mSurfaceHolder.removeCallback(surfaceCallback);
        mSurfaceHolder.addCallback(surfaceCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean checkIsUnSupportVersion() {
        return !Util.hasMediaEncodeSo(this) || SystemUtil.isSpecialMobileType() || SystemUtil.isLowPhone(this) || !MediaFileUtils.checkSDCardAvailable();
    }

    private boolean isSupportFocusModeChange() {
        return !SystemUtil.isMobileInFocusModeBlackList();
    }

    /**
     * 设置视频类型（长视频/短视频）
     */
    private void setVideoType() {
        //长/短视频判断节点
        final int minToMaxDuration = 9000;
        if (maxDuration >= minToMaxDuration) {
            mVideoType = 1;
        } else {
            mVideoType = 0;
        }
    }

    private void startPreview() {
        final String auto = "auto";
        if (mCamera != null && mSurfaceAcquired) {
            try {
                addSurfaceCallBack();
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.startPreview();
            } catch (Exception var2) {
                return;
            }
            Camera.Parameters cameraParams = mCamera.getParameters();
            if (auto.equals(cameraParams.getFocusMode())) {
                mCamera.autoFocus(null);
            }
            startCollect();
        }
    }

    private void startCollect() {
        initMediaRecorder();
        mTaoMediaRecorder.prepareCamera(mCamera);
        mTaoMediaRecorder.setOrientationHintByCameraPostion(this, mCamera, cameraPosition);
        try {
            mTaoMediaRecorder.prepare();
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    private void initMediaRecorder() {
        if (mTaoMediaRecorder == null) {
            mTaoMediaRecorder = new TaoMediaRecorder(this);
            mTaoMediaRecorder.setVideoSource(1);
            mTaoMediaRecorder.setAudioSource(0);
            mTaoMediaRecorder.setOutputFormat(2);
            mTaoMediaRecorder.setAudioEncoder(0);
            mTaoMediaRecorder.setVideoEncoder(2);
            mTaoMediaRecorder.setVideoSize(mPreviewWidth, mPreviewHeight);
            mTaoMediaRecorder.setQuality(quality);
        }
    }

    /**
     * 视频需要摄像头权限提示
     */
    private void openCameraError() {
        if (!isFinishing()) {
            Toast.makeText(this, getString(R.string.taorecorder_camera_permission_deny), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * 需要录音权限提示
     */
    private void recordError() {
        if (!isFinishing()) {
            Toast.makeText(this, getString(R.string.taorecorder_record_fail), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * 打开摄像头
     *
     * @return 是否打开摄像头
     */
    private boolean initCameraImpl() {
        mCamera = CameraHelper.openCamera(cameraPosition);
        if (mCamera == null) {
            //开启摄像头失败
            openCameraError();
            return false;
        } else {
            Camera.Parameters parameters = mCamera.getParameters();
            setupPreviewSize(parameters);
            CameraHelper.setFocusArea(parameters, new Rect(-100, -100, 100, 100));
            CameraHelper.setPreviewFrameRate(parameters, 20);
            if (isSupportFocusModeChange()) {
                CameraHelper.setFocusMode(parameters);
            }

            CameraHelper.setCameraDisplayOrientation(this, cameraPosition, mCamera);
            mCamera.setParameters(parameters);
            return true;
        }
    }

    /**
     * 初始化相机
     *
     * @return 是否初始
     */
    private boolean initCamera() {
        try {
            return initCameraImpl();
        } catch (Exception var2) {
            IMCommitManager.addErrorTrack("@sv", "initCamera", var2);
            return false;
        }
    }

    private void setupPreviewSize(Camera.Parameters cameraParams) {
        Camera.Size validSize;
        Camera.Size[] sizeList = CameraHelper.choosePreviewSize(cameraParams, 480, 480);
        if (sizeList.length == 0) {
            validSize = cameraParams.getPreviewSize();
        } else {
            validSize = sizeList[0];
        }
        mPreviewWidth = validSize.width;
        mPreviewHeight = validSize.height;
        cameraParams.setPreviewSize(validSize.width, validSize.height);
    }

    private void stopPreview() {
        boolean stopSuc = true;

        try {
            if (mTaoMediaRecorder != null) {
                mTaoMediaRecorder.stop();
                isVideoRecording = false;
            }

            removeHolderAndStopPreview(true);
        } catch (Exception var3) {
            stopSuc = false;
            IMCommitManager.addErrorTrack("@sv", "stopPreview", var3);
        }

        if (!stopSuc) {
            openCameraError();
        }
    }

    private boolean removeHolderAndStopPreview(boolean needLock) {
        try {
            if (mCamera != null) {
                if (mSurfaceHolder != null) {
                    mSurfaceHolder.removeCallback(surfaceCallback);
                }

                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                if (needLock) {
                    mCamera.lock();
                }

                mCamera.release();
                mCamera = null;
                return true;
            } else {
                return false;
            }
        } catch (Exception var3) {
            IMCommitManager.addErrorTrack("@sv", "removeHolderAndStopPreview", var3);
            return false;
        }
    }

    private String getLastOutputFile() {
        return "temp_" + mVideoIndex + ".mp4";
    }

    /**
     * 发送录制时长 Handler
     *
     * @return handler
     */
    private Handler getSafeHandler() {
        if (mSafeHandler != null) {
            return mSafeHandler;
        } else {
            mSafeHandler = new Handler();
            return mSafeHandler;
        }
    }

    /**
     * 开始录像
     */
    private void startRecord() {
        IMCommitManager.commitClick(IMCommitManager.getActivityPageName(this), "Video_Recording");
        resetRecorderState();
        mRecorderTimeline.stopAnim();
        if (mClipManager.isReachJumpTime()) {
            toIMPlayRecordVideoActivity();
            isVideoRecording = false;
        } else {
            //存储视频
            VideoBean vb = new VideoBean();
            vb.videoFile = mTaoMediaRecorder.getFileDir() + File.separator + getLastOutputFile();
            mClipManager.onRecordStarted(vb);
            startTime = 0L;
            if (mSafeHandler != null) {
                getSafeHandler().post(runnableTimer);
            }

            mRecorderTimeline.stopAnim();

            ivRecorder.startAnimation(alpahAnimation);
            mTaoMediaRecorder.setOutputFile(getLastOutputFile());
            mTaoMediaRecorder.start();
            isVideoRecording = true;
        }
    }

    /**
     * 暂停录制
     */
    private void stopRecord() {
        //显示图标
        showIcon();
        mClipManager.onRecordPaused();
        if (mSafeHandler != null) {
            getSafeHandler().removeCallbacks(runnableTimer);
        }

        if (ivRecorder.isShown() && !mClipManager.isMaxDurationReached()) {
            mRecorderTimeline.startAnim();
        } else {
            mRecorderTimeline.stopAnim();
        }

        ivRecorder.clearAnimation();
        ++mVideoIndex;
        mTaoMediaRecorder.stop();
        if (mClipManager.isLastClipMinTime()) {
            deleteLastClip();
        }

        isVideoRecording = false;
        if (mClipManager.isReachJumpTime()) {
            toIMPlayRecordVideoActivity();
        }

    }

    private void onMaxDurationReached() {
        isVideoRecording = false;
        stopRecord();
    }

    /**
     * 设置录制时间
     */
    private void setRecordTime() {
        //总时长
        int totalTime = mClipManager.getDuration();
        //大于0且不大于最大时长
        if (totalTime >= 0 && totalTime < maxDuration) {
            mFormatBuilder.setLength(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new EventBean("publish"));
        IMCommitManager.pageAppear(this);
        if (!mClipManager.isMaxDurationReached()) {
            mRecorderTimeline.startAnim();
        }

        if (!initCamera()) {
            openCameraError();
        } else {
            startPreview();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        IMCommitManager.pageDisappear(this);
        if (isVideoRecording) {
            stopRecord();
        }
        mRecorderTimeline.stopAnim();
        ivRecorder.clearAnimation();
        stopPreview();
    }

    @Override
    public void onClick(View v) {
        if (!isVideoRecording) {
            int id = v.getId();
            //退出
            if (id == R.id.publish_video_iv_back) {
                IMCommitManager.commitClick(IMCommitManager.getActivityPageName(this), "Video_Return");
                pressBack();
                //旋转镜头
            } else if (id == R.id.publish_video_iv_cameraRotate) {
                IMCommitManager.commitClick(IMCommitManager.getActivityPageName(this), "Video_FrontBack");
                rotateCamera();
                //删除录制的视频
            } else if (id == R.id.publish_video_iv_delete) {
                while (mVideoIndex > 0) {
                    mClipManager.removeLastClip();
                    --mVideoIndex;
                }
            } else if (id == R.id.publish_video_iv_ok) {
                IMCommitManager.commitClick(IMCommitManager.getActivityPageName(this), "Video_Confirm");
                if (mClipManager.isMinDurationReached()) {
                    toIMPlayRecordVideoActivity();

                } else {
                    toIMPlayRecordVideoActivity();
                }
            }

        }
    }

    private void rotateCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraCount = Camera.getNumberOfCameras();

        for (int i = 0; i < cameraCount; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraPosition == SystemUtil.getCameraFacingBack()) {
                if (cameraInfo.facing == SystemUtil.getCameraFacingFront()) {
                    if (!removeHolderAndStopPreview(false)) {
                        openCameraError();
                        return;
                    }

                    cameraPosition = SystemUtil.getCameraFacingFront();
                    if (!initCamera()) {
                        openCameraError();
                        return;
                    }

                    startPreview();
                    if (mType == 1) {
                        ivCameraRotate.setImageResource(R.drawable.aliwx_sv_st_camera_pre);
                    } else if (mType == 0) {
                        ivCameraRotate.setImageResource(R.drawable.aliwx_sv_wx_camera_pre);
                    }
                    break;
                }
            } else if (cameraInfo.facing == SystemUtil.getCameraFacingBack()) {
                if (!removeHolderAndStopPreview(false)) {
                    openCameraError();
                    return;
                }

                cameraPosition = SystemUtil.getCameraFacingBack();
                if (!initCamera()) {
                    openCameraError();
                    return;
                }

                startPreview();

                ivCameraRotate.setImageResource(R.drawable.aliwx_sv_wx_camera_nor);
                break;
            }
        }

    }

    /**
     * 删除最后一段
     */
    private void deleteLastClip() {
        mClipManager.removeLastClip();
        --mVideoIndex;

        if (mClipManager.isMaxDurationReached()) {
            ivRecorder.setEnabled(false);
            ivRecorder.setAlpha(0.5F);
        } else {
            ivRecorder.setEnabled(true);
            ivRecorder.setAlpha(1.0F);
        }

        mRecorderTimeline.stopAnim();
        mRecorderTimeline.startAnim();

        if (mClipManager.isEmpty()) {
            ivDelete.setAlpha(0.5F);
        } else {
            ivDelete.setAlpha(1.0F);
        }

        setRecordTime();
    }

    private void toIMPlayRecordVideoActivity() {
        int totaltime = mClipManager.getDuration();
        if (totaltime < mClipManager.getMinDuration()) {
            Toast toast = Toast.makeText(PublishInfoVideoActivity.this, "最少拍摄 5 秒", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        showIcon();
        showProgressDialog();


        class LoggerThreadFactory implements ThreadFactory {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread t = new Thread(r);
                t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {

                    }
                });
                return t;
            }
        }
        ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(1,new LoggerThreadFactory());
        scheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                String[] input = new String[mVideoIndex];
                for (int tempVideoPath = 0; tempVideoPath < mVideoIndex; ++tempVideoPath) {
                    input[tempVideoPath] = mTaoMediaRecorder.getFileDir() + File.separator + "temp_" + tempVideoPath + ".mp4";
                }
                String var7 = mTaoMediaRecorder.getFileDir() + File.separator + "temp_output.mp4";
                FileUtils.deleteFile(var7);
                MediaEncoderMgr.mergeMp4Files(input, var7);
                mTaoMediaRecorder.setOutputFile("temp_output.mp4");
                String tempJpgPath = mTaoMediaRecorder.getJpegFile();
                String uUIdString = UUID.randomUUID().toString().replaceAll("-", "");
                final String targetVideoPath = mTaoMediaRecorder.getFileDir() + File.separator + uUIdString + "_output.mp4";
                final String targetJpgPath = mTaoMediaRecorder.getFileDir() + File.separator + uUIdString + "_output.jpg";
                FileUtils.copyFile(tempJpgPath, targetJpgPath);
                FileUtils.copyFile(var7, targetVideoPath);
                getSafeHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgressDialog();
                        int size1 = 0;
                        if (targetVideoPath != null && (new File(targetVideoPath)).isFile()) {
                            File file = new File(targetVideoPath);
                            size1 = (int) file.length();
                        }
                        Log.d("videoSize", "getDuration:" + mClipManager.getDuration() + "");
                        int totalTime = mClipManager.getDuration();
                        if (totalTime < minDuration) {
                            Toast toast = Toast.makeText(PublishInfoVideoActivity.this, "录制时长不能小于5秒钟", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            mFormatBuilder.setLength(0);
                            String sTime = mFormatter.format("%d.%d 秒", new Object[]{Integer.valueOf(totalTime / 1000), Integer.valueOf(totalTime / 100 % 10)}).toString();
                            final String from0 = "0";
                            final String from1 = "1";
                            final String from2 = "2";
                            final String from3 = "3";
                            if (from0.equals(from)) {
                                Intent intent1 = new Intent(PublishInfoVideoActivity.this, EditVideoActivity.class);
                                //视频路径
                                intent1.putExtra("videoPath", targetVideoPath);
                                //图片路径
                                intent1.putExtra("framePicPath", targetJpgPath);
                                //录制时间
                                intent1.putExtra("videoDuration", sTime);
                                //录制大小
                                intent1.putExtra("videoSize", size1);
                                startActivity(intent1);
                                finish();
                            } else if (from1.equals(from)) {
                                Intent intent1 = new Intent();
                                //视频路径
                                intent1.putExtra("videoPath", targetVideoPath);
                                //图片路径
                                intent1.putExtra("framePicPath", targetJpgPath);
                                //录制时间
                                intent1.putExtra("videoDuration", sTime);
                                //录制大小
                                intent1.putExtra("videoSize", size1);
                                PublishInfoVideoActivity.this.setResult(RESULT_OK, intent1);
                                finish();
                            } else if (from2.equals(from)) {
                                Intent intent1 = new Intent();
                                //视频路径
                                intent1.putExtra("videoPath", targetVideoPath);
                                //图片路径
                                intent1.putExtra("framePicPath", targetJpgPath);
                                //录制时间
                                intent1.putExtra("videoDuration", sTime);
                                //录制大小
                                intent1.putExtra("videoSize", size1);
                                PublishInfoVideoActivity.this.setResult(789, intent1);
                                finish();
                            } else  if (from3.equals(from)){
                                Intent intent1 = new Intent();
                                //视频路径
                                intent1.putExtra("videoPath", targetVideoPath);
                                //图片路径
                                intent1.putExtra("framePicPath", targetJpgPath);
                                //录制时间
                                intent1.putExtra("videoDuration", sTime);
                                //录制大小
                                intent1.putExtra("videoSize", size1);
                                PublishInfoVideoActivity.this.setResult(7891, intent1);
                                finish();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (NO101 == requestCode) {
            if (-1 == resultCode) {
                setResult(-1, data);
                finish();
                return;
            }
        }

    }

    /**
     * 关闭页面
     */
    private void pressBack() {
        if (!isVideoRecording) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == NO4) {
            if (mProgressDialogView != null && mProgressDialogView.isShown()) {
                return true;
            } else {
                pressBack();
                return true;
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void requestAudioFocus() {
        mAudioManager.requestAudioFocus(mAudioFocusListener, 3, 1);
    }

    private void abandonAudioFocus() {
        if (mAudioManager != null) {
            mAudioManager.abandonAudioFocus(mAudioFocusListener);
        }

    }

    @Override
    public void onSizeChanged(View view, int w, int h, int oldw, int oldh) {
        final float float0 = 0.0F;
        float scaleX = (float) w / 480.0F;
        float scaleY = (float) h / 480.0F;
        if (scaleX != float0 && scaleY != float0) {
            short rotation = 90;
            short previewWidth = 640;
            short preivewHeight = 480;
            short surfaceWidth;
            short surfaceHeight;
            switch (rotation) {
                case 90:
                case 270:
                    surfaceWidth = preivewHeight;
                    surfaceHeight = previewWidth;
                    break;
                default:
                    surfaceWidth = previewWidth;
                    surfaceHeight = preivewHeight;
            }

            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mCameraPreview.getLayoutParams();
            lp.gravity = 51;
            lp.width = (int) (scaleX * (float) surfaceWidth);
            lp.height = (int) (scaleY * (float) surfaceHeight);
            lp.setMargins(0, 0, 0, 0);
            mCameraPreview.setLayoutParams(lp);
        }
    }

    /**
     * 初始 确定视频 处理等待
     */
    private void initProgressDialog() {
        mProgressDialogView = findViewById(R.id.publish_video_view_dialog);
        int ringWidth = DensityUtil.dip2px(this, 2.0F);
        mProgressDrawable = new CircularProgressDrawable(-1, (float) ringWidth);
        ImageView mProgressView = findViewById(R.id.taorecorder_uik_circularProgress);
        TextView mProgressTextView = findViewById(R.id.taorecorder_uik_progressText);
        mProgressView.setImageDrawable(mProgressDrawable);
        mProgressTextView.setText(getString(R.string.taorecorder_doing));
    }

    /**
     * 开启圆形ProgressDialog
     */
    private void showProgressDialog() {
        if (mProgressDialogView != null && !mProgressDialogView.isShown()) {
            mProgressDrawable.start();
            mProgressDialogView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 关闭圆形ProgressDialog
     */
    private void dismissProgressDialog() {
        if (mProgressDialogView != null && mProgressDialogView.isShown()) {
            mProgressDialogView.setVisibility(View.GONE);
            mProgressDrawable.stop();
        }
    }

    /**
     * 显示图标
     */
    private void showIcon() {
        ivBack.setVisibility(View.VISIBLE);
        ivCameraRotate.setVisibility(View.VISIBLE);
        ivDelete.setAlpha(1.0F);

        if (cameraPosition == SystemUtil.getCameraFacingBack()) {
            ivCameraRotate.setImageResource(R.drawable.aliwx_sv_wx_camera_nor);
        } else {
            if (mType == 1) {
                ivCameraRotate.setImageResource(R.drawable.aliwx_sv_st_camera_pre);
            } else if (mType == 0) {
                ivCameraRotate.setImageResource(R.drawable.aliwx_sv_wx_camera_pre);
            }
        }

    }

    /**
     * 重置录像
     */
    private void resetRecorderState() {
        mClipManager.setLastClipSelected(false);
    }

    @Override
    protected void onDestroy() {
        abandonAudioFocus();
        //透明动画
        if (alpahAnimation != null) {
            alpahAnimation.cancel();
            alpahAnimation.reset();
            alpahAnimation = null;
        }
        //缩放动画
        if (scaleAnimation != null) {
            scaleAnimation.cancel();
            scaleAnimation.reset();
            scaleAnimation = null;
        }
        //时间条
        if (mRecorderTimeline != null) {
            mRecorderTimeline.destory();
        }

        if (mSafeHandler != null) {
            getSafeHandler().removeCallbacksAndMessages(null);
            mSafeHandler = null;
        }

        super.onDestroy();
    }
}
