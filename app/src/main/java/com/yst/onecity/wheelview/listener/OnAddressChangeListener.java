package com.yst.onecity.wheelview.listener;
/**
 *(个人信息)服务弹框监听
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/22
 */
public interface OnAddressChangeListener {
	/**
	 * 地址修改回调
	 *
	 * @param province 省
	 * @param city 市
	 * @param district 详情
	 */
	void onAddressChange(String province, String city, String district);
}
