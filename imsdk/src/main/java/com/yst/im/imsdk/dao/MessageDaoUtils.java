package com.yst.im.imsdk.dao;

import android.util.Log;

import com.yst.im.imsdk.bean.MessageBean;
import com.yst.im.imsdk.utils.LocalLog;

import java.util.List;

/**
 * 数据库操作类
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/27.
 */

public class MessageDaoUtils {

    /**
     * 插入或者更新
     */
    public static void insertOrReplace(final MessageBean mMessageBean) {
        Log.e("insertOrReplace = ", "insertOrReplace = mMessageBean = " + mMessageBean.getContent() + " mMessageBean.getId() = " + mMessageBean.getId());
        LocalLog.e("insertOrReplace = ", "insertOrReplace = mMessageBean = " + mMessageBean.getContent() + " mMessageBean.getId() = " + mMessageBean.getId());
        GreenDaoManager.getInstance().getmDaoSession().getMessageBeanDao().insertOrReplace(mMessageBean);
    }

    /**
     * 根据条件分页、排序 查询
     */
    public static List<MessageBean> queryList(int pageNumber, int pageSize, String acceptId) {
        List<MessageBean> list = GreenDaoManager.getInstance().getmDaoSession().getMessageBeanDao().queryBuilder()
                .offset(pageNumber * pageSize).limit(pageSize)
                .where(MessageBeanDao.Properties.ContactsId.eq(acceptId))
                .orderDesc(MessageBeanDao.Properties.OccureTime)
                .list();
        GreenDaoManager.getInstance().getmDaoSession().clear();
        return list;
    }

    /**
     * 根据条件删除数据
     */
    public static void deleteInTx(final String contactsId) {
        GreenDaoManager.getInstance().getmDaoSession().startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                List<MessageBean> list = GreenDaoManager.getInstance().getmDaoSession().getMessageBeanDao().queryBuilder().where(MessageBeanDao.Properties.ContactsId.eq(contactsId)).list();
                GreenDaoManager.getInstance().getmDaoSession().getMessageBeanDao().deleteInTx(list);
            }
        });
    }

    /**
     * 插入一个列表
     */
    public static void insertOrReplaceInTx(final List<MessageBean> messageBeens) {
        GreenDaoManager.getInstance().getmDaoSession().startAsyncSession().runInTx(new Runnable() {

            @Override
            public void run() {
                GreenDaoManager.getInstance().getmDaoSession().getMessageBeanDao().insertOrReplaceInTx(messageBeens);
            }
        });
    }
}
