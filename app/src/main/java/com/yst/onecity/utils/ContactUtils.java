package com.yst.onecity.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.yst.onecity.bean.ContactsBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能描述  获取联系人数据
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2017/12/28.
 */
public class ContactUtils {

    private static final String[] PHONES_PROJECTION = new String[] { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };

    /**
     * 获取系统联系人信息
     *
     * @return
     */
    public static List<ContactsBean> getSystemContactInfos(Context mContext) {

        List<ContactsBean> infos = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        if (cursor != null) {

            while (cursor.moveToNext()) {

                ContactsBean info = new ContactsBean();
                String contactName = cursor.getString(0);
                String phoneNumber = cursor.getString(1);
                info.setContactName(contactName);
                info.setPhoneNumber(phoneNumber);
                infos.add(info);
                info = null;
            }
            cursor.close();

        }
        return infos;
    }

    /**
     * 线程
     */
    public static class Executorsa {
        public static final ThreadFactory S_THREAD_FACTORY = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(5);

            @Override
            public Thread newThread(@NonNull Runnable runnable) {
                return new Thread(runnable, "" + mCount.getAndIncrement());
            }
        };
        public static Executor executor =new Executor() {

            @Override
            public void execute(@NonNull Runnable command) {

            }
        };

    }
}
