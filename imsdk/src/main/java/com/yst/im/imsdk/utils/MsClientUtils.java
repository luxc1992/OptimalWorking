package com.yst.im.imsdk.utils;

import com.alibaba.fastjson.JSON;
import com.yst.im.imsdk.bean.MessageBean;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 聊天通道封装
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/11.
 */
public class MsClientUtils {

	private static Charset charset = Charset.forName("UTF-8");

	private static MessageBean baseInfo;

	public static void loadBaseInfo(MessageBean baseInfos){
		baseInfo=baseInfos;
	}
	/**
	 * 包装发送的内容
	 * @param content 内容
	 * @param accepteId 接受者标识
	 * @param senderId 发送的id
	 */
	public static MessageBean integrated(String portrait,String content, String accepteId, String senderId, int event, long id, int type,
										 String version, boolean isGroupChat, String niceName, long occureTime, String password, String requsetSourceSystem){
		baseInfo.setPortrait(portrait);
		baseInfo.setContent(content);
		baseInfo.setAccepteId(accepteId);
		baseInfo.setEvent(event);
		baseInfo.setSenderId(senderId);
		baseInfo.setId(id);
		baseInfo.setVersion(version);
		baseInfo.setType(type);
		baseInfo.setGroupChat(isGroupChat);
		baseInfo.setNickName(niceName);
		baseInfo.setOccureTime(occureTime);
		baseInfo.setPassword(password);
		baseInfo.setRequestSourceSystem(requsetSourceSystem);
		return baseInfo;
	}

	/**
	 * 登录方法
	 *
	 * @param senderId 发送id
	 * @param requestSourceSystem 请求来源
	 * @return 登录实体类
	 */
	public static MessageBean loginBaseInfo(String senderId, String requestSourceSystem){
		baseInfo.setAccepteId("-1");
		baseInfo.setEvent(0);
		baseInfo.setSenderId(senderId);
		baseInfo.setRequestSourceSystem(requestSourceSystem);
		return baseInfo;
	}
	/**
	 * 计算baseinfo的长度
	 */
	public static int lengSizeInit(MessageBean baseInfo){
		String s = JSON.toJSONString(baseInfo);
		byte[] bys = new byte[0];
		try {
			bys = s.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bys.length;
	}
	/**
	 * 计算报文大小
	 * @param baseInfo 实体
	 * @return 字节长度
	 */
	public static ByteBuffer lengByteBuffer(MessageBean baseInfo){
		ByteBuffer bb = ByteBuffer.allocate(4+ MsClientUtils.lengSizeInit(baseInfo));
		bb.putInt(MsClientUtils.lengSizeInit(baseInfo));
		bb.put(charset.encode(JSON.toJSONString(baseInfo)));
		bb.position(0);
		return bb;
	}
	/**
	 * 对传送的实体进行加密
	 * @param baseHeader 加密
	 * @return 0
	 */
	public static boolean encrySign(MessageBean baseHeader){

		return true;
	}
	/**
	 * 对传送的实体进行解密
	 * @return 0
	 */
	public static boolean decrySign(MessageBean baseHeader){
		return true;
	}
}
