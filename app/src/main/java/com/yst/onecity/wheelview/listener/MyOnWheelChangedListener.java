/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.yst.onecity.wheelview.listener;

import com.yst.onecity.wheelview.MyWheelView;

/**
 *(个人信息)服务弹框滑动监听
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/22
 */
public interface MyOnWheelChangedListener {
	/**
	 * Callback method to be invoked when current item changed
	 * @param wheel the wheel view whose state has changed
	 * @param oldValue the old value of current item
	 * @param newValue the new value of current item
	 */
	void onChanged(MyWheelView wheel, int oldValue, int newValue);
}
