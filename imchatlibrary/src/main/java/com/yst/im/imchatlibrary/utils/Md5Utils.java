package com.yst.im.imchatlibrary.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 签名工具类
 *
 * @author qinchaoshuai
 * @date 2018/05/03
 */
public class Md5Utils {
    private static final char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 得到参数加密后的MD5值
	 * @param inStr
	 * @return 32byte MD5 Value
	 */
	public static String getMD5(String inStr){
		byte[] inStrBytes = inStr.getBytes();
		try {
			MessageDigest mMD = MessageDigest.getInstance("MD5");
			mMD.update(inStrBytes);
			byte[] mdByte = mMD.digest();
			char[] str = new char[mdByte.length * 2];
			int k = 0;
			for(int i=0;i<mdByte.length;i++) {
				byte temp = mdByte[i];
				str[k++] = chars[temp >>> 4 & 0xf];
				str[k++] = chars[temp & 0xf];
			}
			return new String(str).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
