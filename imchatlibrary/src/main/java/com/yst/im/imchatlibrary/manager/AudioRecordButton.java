package com.yst.im.imchatlibrary.manager;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.utils.FileUtils;
import com.yst.im.imsdk.MessageConstant;
import com.yst.im.imsdk.utils.ImThreadPoolUtils;


/**
 * 录音按钮核心类，包括点击、响应、与弹出对话框交互等操作
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/2.
 */
public class AudioRecordButton extends AppCompatButton implements AudioManager.AudioStageListener {

    /**
     * 三个对话框的状态常量
     */
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_TO_CANCEL = 3;

    /**
     * 垂直方向滑动取消的临界距离
     */
    private static final int DISTANCE_Y_CANCEL = 50;
    /**
     * 取消录音的状态值
     */
    private static final int MSG_VOICE_STOP = 4;
    /**
     * 当前状态
     */
    private int mCurrentState = STATE_NORMAL;
    /**
     * 正在录音标记
     */
    private boolean isRecording = false;
    /**
     * 录音对话框
     */
    private DialogManager mDialogManager;
    /**
     * 核心录音类
     */
    private AudioManager mAudioManager;
    /**
     * 当前录音时长
     */
    private float mTime = 0;
    /**
     * 是否触发了onlongclick，准备好了
     */
    private boolean mReady;
    /**
     * 标记是否强制终止
     */
    private boolean isOverTime = false;
    /**
     * 最大录音时长（单位:s）。def:60s
     */
    private int mMaxRecordTime = 60;

    /**
     * 上下文
     */
    Context mContext;
    /**
     * 震动类
     */
    private Vibrator vibrator;
    /**
     * 提醒倒计时
     */
    private int mRemainedTime = 10;
    /**
     * 设置是否允许录音,这个是是否有录音权限
     */
    private boolean mHasRecordPromission = true;
    /**
     * 是否允许短时间内再次点击录音，主要是防止故意多次连续点击。
     */
    private boolean canRecord = true;
    /**
     * 是否触发过震动
     */
    private boolean isShcok;

    public boolean isHasRecordPromission() {
        return mHasRecordPromission;
    }

    public void setHasRecordPromission(boolean hasRecordPromission) {
        this.mHasRecordPromission = hasRecordPromission;
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    public AudioRecordButton(Context context) {
        this(context, null);
    }

    public AudioRecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;


        //初始化语音对话框
        mDialogManager = new DialogManager(getContext());

        //获取录音保存位置
        String dir = FileUtils.getAppRecordDir(mContext).toString();
        //实例化录音核心类
        mAudioManager = AudioManager.getInstance(dir);

        mAudioManager.setOnAudioStageListener(this);
    }

    public interface AudioFinishRecorderListener {
        /**
         * 录音完成
         *
         * @param seconds 时长
         * @param filePath 路径
         */
        void onFinished(float seconds, String filePath);
    }

    private AudioFinishRecorderListener mListener;

    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener) {
        mListener = listener;
    }

    /**
     * 获取音量大小的runnable
     */
    private Runnable mGetVoiceLevelRunnable = new Runnable() {

        @Override
        public void run() {
            while (isRecording) {
                try {

                    //最长mMaxRecordTimes
                    if (mTime > mMaxRecordTime) {
                        mStateHandler.sendEmptyMessage(MSG_VOICE_STOP);
                        return;
                    }

                    Thread.sleep(100);
                    mTime += 0.1f;
                    mStateHandler.sendEmptyMessage(MSG_VOICE_CHANGE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     *     三个状态
     */
    private static final int MSG_AUDIO_PREPARED = 0X110;
    private static final int MSG_VOICE_CHANGE = 0X111;
    private static final int MSG_DIALOG_DIMISS = 0X112;

    private Handler mStateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    // 显示应该是在audio end prepare之后回调
                    mDialogManager.showRecordingDialog();
                    isRecording = true;
                    ImThreadPoolUtils.THREAD_FACTORY.newThread(mGetVoiceLevelRunnable).start();

                    // 需要开启一个线程来变换音量
                    break;
                case MSG_VOICE_CHANGE:
                    //剩余10s
                    showRemainedTime();
                    mDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DIMISS:
                    mDialogManager.dimissDialog();
                    break;
                case MSG_VOICE_STOP:
                    //超时
                    isOverTime = true;
                    mDialogManager.dimissDialog();
                    mAudioManager.release();
                    mListener.onFinished(mTime, mAudioManager.getCurrentFilePath());
                    // 恢复标志位

                    reset();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    private void showRemainedTime() {
        //倒计时
        int remainTime = (int) (mMaxRecordTime - mTime);
        if (remainTime < mRemainedTime) {
            if (!isShcok) {
                isShcok = true;
                doShock();
            }
            mDialogManager.getTipsTxt().setText(String.valueOf("还可以说" + remainTime + "秒  "));
        }

    }

    /**
     * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
     */
    private void doShock() {
        vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        // 停止 开启 停止 开启
        long[] pattern = {100, 400, 100, 400};
        //重复两次上面的pattern 如果只想震动一次，index设为-1
        vibrator.vibrate(pattern, -1);
    }

    /**
     * 在这里面发送一个handler的消息
     */

    @Override
    public void wellPrepared() {
        mStateHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    /**
     * 手指滑动监听
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //响应DOWN事件进行录音准备。放到这里会有问题，比如用户故意连续点击多次，就会出现各种问题。
                // 所以和录制视频处理的思路一样，我们在短时间内只允许点击一次即可。
                if (isHasRecordPromission() && isCanRecord()) {
                    setCanRecord(false);
                    mReady = true;
                    mAudioManager.prepareAudio();
                    //这里在短时间之后再允许点击
                    ImThreadPoolUtils.THREAD_FACTORY.newThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            setCanRecord(true);
                        }
                    }).start();
                }

                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:

                if (isRecording) {

                    // 根据x，y来判断用户是否想要取消
                    if (wantToCancel(x, y)) {
                        changeState(STATE_WANT_TO_CANCEL);
                    } else {
                        if (!isOverTime) {
                            changeState(STATE_RECORDING);
                        }
                    }

                }

                break;
            case MotionEvent.ACTION_UP:
                // 首先判断是否有触发onlongclick事件，没有的话直接返回reset
                if (!mReady) {
                    reset();
                    return super.onTouchEvent(event);
                }
                // 如果按的时间太短，还没准备好或者时间录制太短，就离开了，则显示这个dialog
                if (!isRecording || mTime < MessageConstant.FLOAT_8F) {
                    mDialogManager.tooShort();
                    mAudioManager.cancel();
                    // 持续1.3s
                    mStateHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1000);
                    //正常录制结束
                } else if (mCurrentState == STATE_RECORDING) {
                    if (isOverTime) {
                        //超时
                        return super.onTouchEvent(event);
                    }
                    mDialogManager.dimissDialog();
                    // release释放一个mediarecorder
                    mAudioManager.release();
                    // 并且callbackActivity，保存录音
                    if (mListener != null) {
                        mListener.onFinished(mTime, mAudioManager.getCurrentFilePath());
                    }
                } else if (mCurrentState == STATE_WANT_TO_CANCEL) {
                    // cancel
                    mAudioManager.cancel();
                    mDialogManager.dimissDialog();
                }
                // 恢复标志位
                reset();
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 回复标志位以及状态
     */
    private void reset() {
        isRecording = false;
        changeState(STATE_NORMAL);
        mReady = false;
        mTime = 0;

        isOverTime = false;
        isShcok = false;
    }

    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {
            // 判断是否在左边，右边，上边，下边
            return true;
        }
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }
        return false;
    }

    private void changeState(int state) {
        if (mCurrentState != state) {
            mCurrentState = state;
            switch (mCurrentState) {
                case STATE_NORMAL:
                    setText(mContext.getString(R.string.long_click_record));
                    break;
                case STATE_RECORDING:
                    setBackgroundColor(Color.rgb(0xcd, 0xcd, 0xcd));
                    setText(R.string.hang_up_finsh);
                    setTextColor(Color.WHITE);
                    if (isRecording) {
                        mDialogManager.recording();
                    }
                    break;

                case STATE_WANT_TO_CANCEL:
                    setText(R.string.release_cancel);
                    mDialogManager.wantToCancel();
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public boolean onPreDraw() {
        return false;
    }

    public boolean isCanRecord() {
        return canRecord;
    }

    public void setCanRecord(boolean canRecord) {
        this.canRecord = canRecord;
    }

    public int getMaxRecordTime() {
        return mMaxRecordTime;
    }

    public void setMaxRecordTime(int maxRecordTime) {
        mMaxRecordTime = maxRecordTime;
    }
}
