package com.yst.im.imsdk.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.yst.im.imsdk.bean.ContactsEntity;
import com.yst.im.imsdk.bean.MessageBean;

import com.yst.im.imsdk.dao.ContactsEntityDao;
import com.yst.im.imsdk.dao.MessageBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig contactsEntityDaoConfig;
    private final DaoConfig messageBeanDaoConfig;

    private final ContactsEntityDao contactsEntityDao;
    private final MessageBeanDao messageBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        contactsEntityDaoConfig = daoConfigMap.get(ContactsEntityDao.class).clone();
        contactsEntityDaoConfig.initIdentityScope(type);

        messageBeanDaoConfig = daoConfigMap.get(MessageBeanDao.class).clone();
        messageBeanDaoConfig.initIdentityScope(type);

        contactsEntityDao = new ContactsEntityDao(contactsEntityDaoConfig, this);
        messageBeanDao = new MessageBeanDao(messageBeanDaoConfig, this);

        registerDao(ContactsEntity.class, contactsEntityDao);
        registerDao(MessageBean.class, messageBeanDao);
    }
    
    public void clear() {
        contactsEntityDaoConfig.getIdentityScope().clear();
        messageBeanDaoConfig.getIdentityScope().clear();
    }

    public ContactsEntityDao getContactsEntityDao() {
        return contactsEntityDao;
    }

    public MessageBeanDao getMessageBeanDao() {
        return messageBeanDao;
    }

}
