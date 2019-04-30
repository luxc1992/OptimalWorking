
package com.yst.onecity.adapter;

import android.content.Context;

import com.yst.onecity.wheelview.adapter.AbstractWheelTextAdapter;

import java.util.List;
/**
 *(个人信息)服务弹框适配
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/3/6
 */
public abstract class BaseWheelAdapter<T> extends AbstractWheelTextAdapter {


	/** The default items length */
	public static final int DEFAULT_LENGTH = -1;
	protected List<T> mList = null;

	/**
	 * Constructor
	 * @param list the items
	 * @param length the max items length
	 */
	public BaseWheelAdapter(Context context, List<T> list, int length) {
		super(context);
		this.mList = list;
	}

	/**
	 * Contructor
	 * @param list the items
	 */
	public BaseWheelAdapter(Context context, List<T> list) {
		this(context,list, DEFAULT_LENGTH);
	}

	public T getItemData(int index){
		if (index >= 0 && index < mList.size()) {
			return mList.get(index);
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return mList==null? 0 : mList.size();
	}

	/**
	 * 获取item的文本
	 *
	 * @param index the item index 索引
	 * @return 文本
	 */
	@Override
	protected abstract CharSequence getItemText(int index);
}
