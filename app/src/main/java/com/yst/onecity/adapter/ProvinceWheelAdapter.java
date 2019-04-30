/*
 *  Copyright 2010 Yuri Kanivets
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
package com.yst.onecity.adapter;

import android.content.Context;

import com.yst.onecity.bean.ProvinceAdressBean;

import java.util.List;

/**
 *(个人信息)服务弹框适配
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/6
 */
public class ProvinceWheelAdapter extends BaseWheelAdapter<ProvinceAdressBean.ContentBean> {
	public ProvinceWheelAdapter(Context context, List<ProvinceAdressBean.ContentBean> list) {
		super(context,list);
	}

	@Override
	protected CharSequence getItemText(int index) {
		ProvinceAdressBean.ContentBean data = getItemData(index);
		if(data != null){
			return data.getName();
		}
		return null;
	}
}
