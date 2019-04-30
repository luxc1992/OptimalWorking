package com.yst.im.imsdk;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 聊天接口 登录登出
 *
 * @author Lierpeng
 * @date  2018/04/14.
 * @version 1.0.0
 */
public interface ClientConnectionInterface {
	/**
	 * 客户端登录
	 *
	 * @param senderId 登录名
	 * @param requestSourceSystem  来源
	 * @param token  token
	 * @return  成功失败
	 */
	ByteBuffer login(String senderId, String requestSourceSystem, String token);

	/**
	 * 用户退出
	 *
	 * @param senderId 登录名
	 * @param sc 通道
	 * @return 退出
	 */
	ByteBuffer loginOut(String senderId, SocketChannel sc);
	
}
