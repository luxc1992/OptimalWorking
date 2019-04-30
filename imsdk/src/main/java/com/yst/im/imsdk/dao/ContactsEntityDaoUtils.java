package com.yst.im.imsdk.dao;

import android.util.Log;

import com.yst.im.imsdk.bean.ContactsEntity;
import com.yst.im.imsdk.bean.MessageBean;
import com.yst.im.imsdk.utils.LocalLog;

import java.util.List;

import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_3;

/**
 * 数据库操作类
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/27.
 */

public class ContactsEntityDaoUtils {

    /**
     * 插入或者更新
     */
    public static void insertOrReplace(final ContactsEntity mContactsEntity) {
        Log.e("insertOrReplace = ", "insertOrReplace = mContactsEntity = " + mContactsEntity.getContent() + " mContactsEntity.getId() = " + mContactsEntity.getId());
        LocalLog.e("insertOrReplace = ", "insertOrReplace = mContactsEntity = " + mContactsEntity.getContent() + " mContactsEntity.getId() = " + mContactsEntity.getId());
        GreenDaoManager.getInstance().getmDaoSession().getContactsEntityDao().insertOrReplace(mContactsEntity);
    }

    /**
     * 是否有这一条最近聊天记录,如果大于0 ，则表示已经有了
     */
    public static List<ContactsEntity> queryConstacts(String id) {
        Log.e("queryConstacts = ", "queryConstacts = id = " + id);
        List<ContactsEntity> list = GreenDaoManager.getInstance().getmDaoSession().getContactsEntityDao().queryBuilder().where(ContactsEntityDao.Properties.ContactsId.eq(id)).list();
        return list;
    }

    /**
     * 查询所有
     */
    public static List<ContactsEntity> queryConstactsAll() {
        List<ContactsEntity> list = GreenDaoManager.getInstance().getmDaoSession().getContactsEntityDao().queryBuilder()
                .distinct()
                .orderAsc(ContactsEntityDao.Properties.IsStick)
                .orderDesc(ContactsEntityDao.Properties.OccureTime)
                .list();
        return list;
    }

    /**
     * 根据条件删除数据
     */
    public static void deleteInTx(final String contactsId) {
        List<ContactsEntity> list = GreenDaoManager.getInstance().getmDaoSession().getContactsEntityDao().queryBuilder().where(ContactsEntityDao.Properties.ContactsId.eq(contactsId)).list();
        GreenDaoManager.getInstance().getmDaoSession().getContactsEntityDao().deleteInTx(list);
    }

    /**
     * 插入一个列表
     */
    public static void insertOrReplaceInTx(final List<ContactsEntity> messageBeens) {
        GreenDaoManager.getInstance().getmDaoSession().startAsyncSession().runInTx(new Runnable() {

            @Override
            public void run() {
                GreenDaoManager.getInstance().getmDaoSession().getContactsEntityDao().insertOrReplaceInTx(messageBeens);
            }
        });
    }

    /**
     * 更新一个实体
     */
    public static void update(final ContactsEntity mContactsEntity) {
        GreenDaoManager.getInstance().getmDaoSession().getContactsEntityDao().update(mContactsEntity);
    }
}
