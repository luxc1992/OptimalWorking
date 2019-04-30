package com.yst.im.imsdk;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.yst.im.imsdk.bean.RxBusEntity;
import com.yst.im.imsdk.bean.SelectAttachObj;
import com.yst.im.imsdk.utils.LocalLog;
import com.yst.im.imsdk.utils.MsClientUtils;
import com.yst.im.imsdk.utils.BaseUtils;
import com.yst.im.imsdk.utils.ImThreadPoolUtils;
import com.yst.im.imsdk.bean.MessageBean;
import com.yst.im.imsdk.utils.RxBusConstants;

import java.net.InetSocketAddress;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import gorden.rxbus2.RxBus;

import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_3;
import static com.yst.im.imsdk.MessageConstant.NUM_4;


/**
 * 聊天方法
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/14.
 */
@Deprecated
public class MsClients implements ClientConnectionInterface {

    private static final String TAG = "im_test";

    private Selector selector = null;

    private Charset charset = Charset.forName("UTF-8");

    private SocketChannel sc = null;

    private int collectionNum = 0;

    private int intNum = 0;

    public Thread mHeartThread;
    public Thread mReadThread;

    public boolean isSendHreadThread = false;
    public boolean isServiceConnectThread = false;

    private String serverHostName;
    private String senderId;
    private String requestSourceSystem;
    private String token;
    private Integer serverPort;
    /**
     * -1 第一次连接， 0 心跳，1 异常，2 发送失败, 3 网络断开，4 重连失败继续重连。
     */
    private int connType = -1;
    /**
     * 是否需要重连
     */
    public boolean isNeedConnectionSocket = true;

    /**
     * 单例模式 单例通道
     */
    private static MsClient msClient = null;

    public boolean isUserOut = false;


    public static MsClient getClient() {
        if (msClient == null) {
            synchronized (MsClient.class) {
                if (msClient == null) {
                    LocalLog.setDefalutTag(TAG);
                    LocalLog.switchLog(false, true);
                    msClient = new MsClient();
                }
            }
        }
        return msClient;
    }


    /**
     * 客户端初始化方法
     *
     * @param senderId            发送者唯一ID
     * @param requestSourceSystem 请求来源
     * @param token               鉴权token
     *                            String serverHostName,Integer serverPort,
     */
    public void init(String serverHostName,
                     Integer serverPort,
                     String senderId,
                     String requestSourceSystem,
                     String token) {
        this.serverHostName = serverHostName;
        this.serverPort = serverPort;
        this.senderId = senderId;
        this.requestSourceSystem = requestSourceSystem;
        this.token = token;
        LocalLog.e(TAG, "---进入重连-初始化参数- --->>>"
                + "--->>> serverHostName = " + serverHostName
                + "--->>> serverPort = " + serverPort
                + "--->>> senderId = " + senderId
                + "--->>> requestSourceSystem = " + requestSourceSystem
                + "--->>> token = " + token
                + "--->>> connType = " + connType
        );
        try {
            LocalLog.e(TAG, "进入重连 -- 重连开始 ");
            Thread.sleep(100);
            sc = SocketChannel.open(new InetSocketAddress(serverHostName, serverPort));
            LocalLog.e(TAG, "进入重连 -- 重连开始 -- SocketChannel.open()");
            //阻塞方式
            sc.configureBlocking(false);
            LocalLog.e(TAG, "进入重连 -- 重连开始 --configureBlocking");
            //注册selector
            selector = Selector.open();
            LocalLog.e(TAG, "进入重连 -- 重连开始 --Selector.open()");
            sc.register(selector, SelectionKey.OP_READ);
            LocalLog.e(TAG, "进入重连 -- 重连开始 --register");
            LocalLog.e(TAG, "初始化 连接 == ");
            //启动接受消息线程
            mReadThread = new ClientThread();
            isSendHreadThread = true;
            mReadThread.start();
            LocalLog.e(TAG, "初始化 接收线程");
            //心跳
            MessageBean baseInfo = new MessageBean();
            MsClientUtils.loadBaseInfo(baseInfo);
            sc.write(this.login(senderId, requestSourceSystem, token));
            LocalLog.e(TAG, "初始化 连接 登录== " + login(senderId, requestSourceSystem, token).toString());
            mHeartThread = ImThreadPoolUtils.THREAD_FACTORY.newThread(new HeartThread(senderId, 10000));
            mHeartThread.start();
            isNeedConnectionSocket = true;
            isUserOut = false;
        } catch (Exception e) {
            e.printStackTrace();
            LocalLog.e(TAG, "the IOException message is ==连接失败== " + e.getMessage() + "--->>> connType = " + connType);
            Log.e(TAG, "the IOException message is ==连接失败== " + e.getMessage() + "--->>> connType = " + connType);
            System.out.println("连接失败，请检查网络是否正常！");
            isNeedConnectionSocket = true;
            isUserOut = false;
            if (isNeedConnectionSocket) {
                isNeedConnectionSocket = false;
                connType = 4;
                closeChannel();
            }
        }
    }

    public void getCollection() {
        if (isServiceConnectThread) {
            return;
        }
        isServiceConnectThread = true;
    }

    /**
     * 心跳线程
     */
    private class HeartThread implements Runnable {
        private String senderId;
        private int second;

        private HeartThread(String senderId, int second) {
            this.senderId = senderId;
            this.second = second;
        }

        @Override
        public void run() {
            while (isSendHreadThread) {
                LocalLog.e(TAG, "心跳发出-------");
                Log.e(TAG, "心跳发出-------");
                try {
                    Thread.sleep(second);
                    sendMessage("心跳无头像",
                            "This is HeartPackage",
                            "-1",
                            senderId,
                            2,
                            BaseUtils.getSystemTime(),
                            0,
                            String.valueOf(BaseUtils.getSystemTime()),
                            false,
                            senderId,
                            BaseUtils.getSystemTime(),
                            "im",
                            requestSourceSystem);
                } catch (Exception e) {
                    if (isNeedConnectionSocket) {
                        isNeedConnectionSocket = false;
                        closeChannel();
                    }
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 封装登录方法
     *
     * @param senderId            登录id
     * @param requestSourceSystem 请求来源
     * @param token               token值
     */
    @Override
    public ByteBuffer login(String senderId, String requestSourceSystem, String token) {
        MessageBean baseInfo = MsClientUtils.loginBaseInfo(senderId, requestSourceSystem);
        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.clear();
        int length = MsClientUtils.lengSizeInit(baseInfo);
        bb.limit(4 + length);
        bb.putInt(length);
        bb.put(charset.encode(JSONObject.toJSONString(baseInfo)));
        bb.position(0);
        return bb;
    }

    /**
     * 封装发送消息方法
     *
     * @param content  发送内容
     * @param acceptId 接受者id
     * @param senderId 发送者id
     */

    public void sendMessage(String portrait, String content, String acceptId, String senderId, int event,
                            Long id, int type, String version, boolean isGroupChat, String niceName,
                            long occureTime, String password, String requsetSourceSystem) throws IOException {
        MessageBean integrated = MsClientUtils.integrated(portrait, content, acceptId, senderId, event, id, type, version, isGroupChat, niceName, occureTime, password, requsetSourceSystem);
        ByteBuffer byteBuffer = MsClientUtils.lengByteBuffer(integrated);
        try {
            int result = sc.write(byteBuffer);
            if (result != 0) {
                RxBusEntity rxBusEntity = new RxBusEntity();
                rxBusEntity.setCloseText("取消");
                rxBusEntity.setCode(RxBusConstants.CONST_CODE_MESSAGE_CALLBACK);
                rxBusEntity.setTitle("回执");
                rxBusEntity.setContent(version);
                rxBusEntity.setSureText("确定");
                RxBus.get().send(RxBusConstants.CONST_LOCAL_UPDATE, rxBusEntity);
            }
            LocalLog.e(TAG, "当前用户已经进入发送 = " + result);
        } catch (Exception e) {
            if (isNeedConnectionSocket) {
                isNeedConnectionSocket = false;
                closeChannel();
            }
            LocalLog.e(TAG, "Exception发送 = " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void closeChannel() {
        close();
        Log.e("ImLog", "isUserOut = " + isUserOut);
        if (!isUserOut) {
            isUserOut = true;
            RxBusEntity rxBusEntity = new RxBusEntity();
            rxBusEntity.setCode(RxBusConstants.CONST_CODE_RESET_CONN);
            RxBus.get().send(RxBusConstants.CONST_LOCAL_UPDATE, rxBusEntity);
        }
    }

    public void close() {
        isSendHreadThread = false;
        try {
            if (null != mHeartThread) {
                mHeartThread.interrupt();
                mHeartThread = null;
            }

            if (null != mReadThread) {
                mReadThread.interrupt();
                mReadThread = null;
            }

            if (null != selector) {
                selector.close();
                selector = null;
            }

            if (null != sc) {
                sc.socket().close();
                sc = null;
            }

        } catch (IOException e) {
            LocalLog.e(TAG, "进入重连 -- 重连之前 -closeChannel- " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public ByteBuffer loginOut(String senderId, SocketChannel sc) {
        try {
            sc.socket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 定义读取服务器数据的线程
     */
    private class ClientThread extends Thread {
        private ByteBuffer buff;
        private ByteBuffer intBuff;

        @Override
        public void run() {

            LocalLog.e(TAG, "************* IM LOG ****************");
            LocalLog.e(TAG, "run: 当前用户已经进入读取线程");
            creatRece();
        }
    }

    private void creatRece() {
        try {
            //如果该SelectionKey对应的Channel中有可读的数据
            ByteBuffer intBuff = ByteBuffer.allocate(4);
            //读取OBJ有效数据的缓存池
            ByteBuffer buff = ByteBuffer.allocate(1024);
            //遍历每个有可用IO操作Channel对应的SelectionKey

            while (true) {
                LocalLog.e(TAG, "run: 当前已经进入while循环 = " + intNum);
                Log.e("xcc", "client thread while");
                Thread.sleep(1);
                int result = selector.select(0);
                if (result == 0) {
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                LocalLog.e(TAG, "run: 当前已经进入while循环 = " + intNum + "key == " + keys.toString());
                Iterator<SelectionKey> it = keys.iterator();
                intNum++;
                LocalLog.e(TAG, "run: 当前已经进入注册器循环 = " + intNum);
                while (it.hasNext()) {
                    LocalLog.e(TAG, "run: 当前已经进入注册器遍历 = " + intNum);
                    //删除正在处理的SelectionKey
                    SelectionKey sk = it.next();
                    it.remove();
                    try {
                        if (sk.isReadable()) {

                            LocalLog.e(TAG, "run: 当前已经进入长连接可读= " + intNum);
                            intBuff.clear();
                            buff.clear();
                            SocketChannel sc = (SocketChannel) sk.channel();
                            SelectAttachObj curSelectObj = (SelectAttachObj) sk.attachment();
                            //读取INT头信息的缓存池
                            if (curSelectObj == null) {
                                curSelectObj = new SelectAttachObj();
                                sk.attach(curSelectObj);
                            }
                            LocalLog.e(TAG, "run: 当前已经进入长连接可读 头状态 " + intNum + "," + curSelectObj.getCurState().intValue());
                            sc.socket().setSoTimeout(500);
                            //有效数据长度
                            int objLength = 0;
                            //从NIO信道中读出的数据长度
                            int readObj = 0;
                            String content = "";
                            //标记read 状态
                            int readReturn = -2;
                            while (curSelectObj.getCurState().intValue() == 0) {
                                if ((readReturn = sc.read(intBuff)) != 0) {
                                    if (readReturn == -1) {
                                        sk.cancel();
                                        sc.close();
                                        sc.socket().close();
                                        LocalLog.e(TAG, "run: 当前已经进入长连接读取消息长度为0= " + intNum);
                                        break;
                                    }
                                    if (curSelectObj.getCurState().intValue() == 0 && intBuff.position() < 4) {
                                        LocalLog.e(TAG, "run: 当前已经进入长连接消息头不完整= " + intNum);
                                        continue;
                                    }
                                    if (intBuff.position() == 4) {
                                        LocalLog.e(TAG, "run: 当前已经进入长连接消息正常读取= " + intNum);
                                        objLength = intBuff.getInt(0);
                                        //标记读头成功
                                        curSelectObj.setCurState(new Integer(1));
                                        curSelectObj.setObjLength(objLength);
                                        sk.attach(curSelectObj);
                                    }
                                }
                            }
                            if (readReturn == -1 || readReturn == 0) {
                                LocalLog.e(TAG, "run: 当前已经进入长连接读取消息头跳出= " + intNum);
                                break;
                            }
                            while (curSelectObj.getCurState().intValue() == 1) {
                                LocalLog.e(TAG, "run: 当前已经进入长连接读取消息是否完整= " + intNum);
                                //判断客户端close后的循环读取
                                if (readReturn == -1) {
                                    sk.cancel();
                                    sc.close();
                                    sc.socket().close();
                                    LocalLog.e(TAG, "run: 当前已经进入长连接读取消息长度为0= " + intNum);
                                    break;
                                }

                                objLength = curSelectObj.getObjLength();
                                int toReadLen = objLength - curSelectObj.getAlreadyLength().intValue();
                                LocalLog.e(TAG, "run: 当前已经进入长连接读取消息头跳出= " + intNum + " objLength 头== " + objLength);
                                LocalLog.e(TAG, "run: 当前已经进入长连接读取消息头跳出= " + intNum + " toReadLen  头== " + toReadLen);
                                buff.clear();
                                if (toReadLen < 1024) {
                                    buff.limit(toReadLen);
                                }
                                while ((readReturn = sc.read(buff)) != 0) {
                                    if (readReturn == -1) {
                                        sk.cancel();
                                        sc.close();
                                        sc.socket().close();
                                        break;
                                    }
                                    curSelectObj.setAlreadyLength(curSelectObj.getAlreadyLength() + readReturn);
                                    buff.flip();
                                    curSelectObj.setContent(curSelectObj.getContent() + charset.decode(buff));
                                    LocalLog.e(TAG, "curSelectObj.getContent() 放入消息实体== " + curSelectObj.getContent());
                                    toReadLen = objLength - curSelectObj.getAlreadyLength().intValue();
                                    buff.clear();
                                    if (toReadLen < 1024) {
                                        buff.limit(toReadLen);
                                    }
                                }
                                sk.attach(curSelectObj);
                                LocalLog.e(TAG, "curSelectObj.getAlreadyLength().intValue() == " + curSelectObj.getAlreadyLength().intValue());
                                LocalLog.e(TAG, "objLength == " + objLength);
                                // 如果content的长度大于0，即聊天信息不为空
                                if (curSelectObj.getAlreadyLength().intValue() == objLength) {
                                    try {
                                        LocalLog.e(TAG, "run: 当前已经进入长连接读取消息开始= " + intNum);
                                        content = curSelectObj.getContent();
                                        LocalLog.e(TAG, "curSelectObj.getContent()接收缓冲 == " + curSelectObj);
                                        LocalLog.e(TAG, "curSelectObj.getContent()接收数据 == " + curSelectObj.getContent());

                                        Log.e(TAG, "objLength == " + curSelectObj);
                                        Log.e(TAG, "objLength == " + content);
                                        curSelectObj.setAlreadyLength(new Integer(0));
                                        curSelectObj.setContent("");
                                        curSelectObj.setCurState(0);
                                        curSelectObj.setObjLength(0);

                                        sk.attach(curSelectObj);
                                        if (TextUtils.isEmpty(content)) {
                                            LocalLog.e(TAG, "run: 当前已经进入消息读取到空值 = " + intNum + " = " + content);
                                        } else {
                                            JSONObject jsonObject = JSONObject.parseObject(content);
                                            MessageBean baseInfo = JSONObject.toJavaObject(jsonObject, MessageBean.class);
                                            LocalLog.e(TAG, "run: 当前已经进入消息读取 = " + intNum + " = " + baseInfo);
                                            Log.e(TAG, "json接收的消息 == " + baseInfo.getContent());
                                            //TODO 如果登陆成功 调用API查询好友列表
                                            if (baseInfo.getEvent() == MessageConstant.NUM_32) {
                                                isUserOut = true;
                                            }
                                            RxBus.get().send(baseInfo);
                                            if (baseInfo.getEvent() == NUM_1 || baseInfo.getEvent() == NUM_3) {
                                                RxBus.get().send(RxBusConstants.UPDATE_RECENT_LIST);
                                            }
                                        }

                                        //	进行业务判断 0登录 1接受客户端发送内容 2心跳包 3注销 -1错误提示，消息异常，加密异常等错误信息
                                    } catch (Exception e) {
                                        LocalLog.e(TAG, "run: 当前已经进入长连接读取消息失败关闭= " + intNum + "  " + e.getMessage());
                                        e.printStackTrace();
                                    }
                                }
                                if (readReturn == -1 || readReturn == 0) {
                                    break;
                                }
                            }
                            // 将sk对应的Channel设置成准备下一次读取
                            if (sk.isValid()) {
                                sk.interestOps(SelectionKey.OP_READ);
                            }
                        }
                    } catch (Exception e) {
                        LocalLog.e(TAG, "run: 当前已经进入长连接读取消息异常关闭= " + intNum + "  " + e.getMessage());
                        sk.cancel();
                        if (sk.channel() != null) {
                            sk.channel().close();
                            LocalLog.e(TAG, "run: 当前已经进入长连接关闭= " + intNum);
                            ((SocketChannel) sk.channel()).socket().close();
                        }
                        if (isNeedConnectionSocket) {
                            isNeedConnectionSocket = false;
                            closeChannel();
                        }
                        e.printStackTrace();
                    } finally {
                        SelectAttachObj curSelectObj = (SelectAttachObj) sk.attachment();
                        if (curSelectObj != null) {
                            curSelectObj = null;
                        }
                        selector.selectedKeys().remove(sk);
                    }
                }
            }
        } catch (Exception ex) {
            LocalLog.e(TAG, "run: 当前已经进入读取异常 = " + intNum + "  " + ex.getMessage());
            if (isNeedConnectionSocket) {
                isNeedConnectionSocket = false;
                closeChannel();
            }
            ex.printStackTrace();
        }
    }
}
