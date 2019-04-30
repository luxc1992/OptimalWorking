/*
 *  Android Wheel Control.
 *  https://code.google.com/p/android-wheel/
 *  
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

package com.yst.onecity.wheelview;

import android.view.View;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

/**
 *(个人信息)服务弹框工具
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/22
 */
public class MyWheelRecycle {
	private List<View> items;
	private List<View> emptyItems;
	private MyWheelView wheel;
	
	/**
	 * Constructor
	 * @param wheel the wheel view
	 */
	public MyWheelRecycle(MyWheelView wheel) {
		this.wheel = wheel;
	}


	public int recycleItems(LinearLayout layout, int firstItem, ItemsRange range) {
		int index = firstItem;
		for (int i = 0; i < layout.getChildCount();) {
			if (!range.contains(index)) {
				recycleView(layout.getChildAt(i), index);
				layout.removeViewAt(i);
				if (i == 0) {
					firstItem++;
				}
			} else {
				i++;
			}
			index++;
		}
		return firstItem;
	}
	
	/**
	 * Gets item view
	 * @return the cached view
	 */
	public View getItem() {
		return getCachedView(items);
	}

	/**
	 * Gets empty item view
	 * @return the cached empty view
	 */
	public View getEmptyItem() {
		return getCachedView(emptyItems);
	}
	
	/**
	 * Clears all views 
	 */
	public void clearAll() {
		if (items != null) {
			items.clear();
		}
		if (emptyItems != null) {
			emptyItems.clear();
		}
	}

	/**
	 * Adds view to specified cache. Creates a cache list if it is null.
	 * @param view the view to be cached
	 * @param cache the cache list
	 * @return the cache list
	 */
	private List<View> addView(View view, List<View> cache) {
		if (cache == null) {
			cache = new LinkedList<View>();
		}
		
		cache.add(view);
		return cache;
	}

	/**
	 * Adds view to cache. Determines view type (item view or empty one) by index.
	 * @param view the view to be cached
	 * @param index the index of view
	 */
	private void recycleView(View view, int index) {
		int count = wheel.getViewAdapter().getItemsCount();
		boolean b = (index < 0 || index >= count) && !wheel.isCyclic();
		if (b) {
			// empty view
			emptyItems = addView(view, emptyItems);
		} else {
			while (index < 0) {
				index = count + index;
			}
			index %= count;
			items = addView(view, items);
		}
	}
	
	/**
	 * Gets view from specified cache.
	 * @param cache the cache
	 * @return the first view from cache.
	 */
	private View getCachedView(List<View> cache) {
		if (cache != null && cache.size() > 0) {
			View view = cache.get(0);
			cache.remove(0);
			return view;
		}
		return null;
	}

}
