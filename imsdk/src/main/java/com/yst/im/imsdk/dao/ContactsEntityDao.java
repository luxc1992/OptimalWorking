package com.yst.im.imsdk.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yst.im.imsdk.bean.ContactsEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CONTACTS_ENTITY".
*/
public class ContactsEntityDao extends AbstractDao<ContactsEntity, Long> {

    public static final String TABLENAME = "CONTACTS_ENTITY";

    /**
     * Properties of entity ContactsEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Type = new Property(1, int.class, "type", false, "TYPE");
        public final static Property Event = new Property(2, int.class, "event", false, "EVENT");
        public final static Property Version = new Property(3, String.class, "version", false, "VERSION");
        public final static Property SenderId = new Property(4, String.class, "senderId", false, "SENDER_ID");
        public final static Property AccepteId = new Property(5, String.class, "accepteId", false, "ACCEPTE_ID");
        public final static Property Password = new Property(6, String.class, "password", false, "PASSWORD");
        public final static Property Content = new Property(7, String.class, "content", false, "CONTENT");
        public final static Property RequestSourceSystem = new Property(8, String.class, "requestSourceSystem", false, "REQUEST_SOURCE_SYSTEM");
        public final static Property NickName = new Property(9, String.class, "nickName", false, "NICK_NAME");
        public final static Property OccureTime = new Property(10, long.class, "occureTime", false, "OCCURE_TIME");
        public final static Property GroupId = new Property(11, String.class, "groupId", false, "GROUP_ID");
        public final static Property Portrait = new Property(12, String.class, "portrait", false, "PORTRAIT");
        public final static Property MsgStatus = new Property(13, String.class, "msgStatus", false, "MSG_STATUS");
        public final static Property IsRead = new Property(14, String.class, "isRead", false, "IS_READ");
        public final static Property Count = new Property(15, int.class, "count", false, "COUNT");
        public final static Property IsStick = new Property(16, int.class, "isStick", false, "IS_STICK");
        public final static Property IsShield = new Property(17, int.class, "isShield", false, "IS_SHIELD");
        public final static Property StickTime = new Property(18, long.class, "stickTime", false, "STICK_TIME");
        public final static Property GroupChat = new Property(19, boolean.class, "groupChat", false, "GROUP_CHAT");
        public final static Property IsReCall = new Property(20, boolean.class, "isReCall", false, "IS_RE_CALL");
        public final static Property IsChecked = new Property(21, boolean.class, "isChecked", false, "IS_CHECKED");
        public final static Property GroupNum = new Property(22, String.class, "groupNum", false, "GROUP_NUM");
        public final static Property ContactsId = new Property(23, String.class, "contactsId", false, "CONTACTS_ID");
    };


    public ContactsEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ContactsEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CONTACTS_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TYPE\" INTEGER NOT NULL ," + // 1: type
                "\"EVENT\" INTEGER NOT NULL ," + // 2: event
                "\"VERSION\" TEXT," + // 3: version
                "\"SENDER_ID\" TEXT," + // 4: senderId
                "\"ACCEPTE_ID\" TEXT," + // 5: accepteId
                "\"PASSWORD\" TEXT," + // 6: password
                "\"CONTENT\" TEXT," + // 7: content
                "\"REQUEST_SOURCE_SYSTEM\" TEXT," + // 8: requestSourceSystem
                "\"NICK_NAME\" TEXT," + // 9: nickName
                "\"OCCURE_TIME\" INTEGER NOT NULL ," + // 10: occureTime
                "\"GROUP_ID\" TEXT," + // 11: groupId
                "\"PORTRAIT\" TEXT," + // 12: portrait
                "\"MSG_STATUS\" TEXT," + // 13: msgStatus
                "\"IS_READ\" TEXT," + // 14: isRead
                "\"COUNT\" INTEGER NOT NULL ," + // 15: count
                "\"IS_STICK\" INTEGER NOT NULL ," + // 16: isStick
                "\"IS_SHIELD\" INTEGER NOT NULL ," + // 17: isShield
                "\"STICK_TIME\" INTEGER NOT NULL ," + // 18: stickTime
                "\"GROUP_CHAT\" INTEGER NOT NULL ," + // 19: groupChat
                "\"IS_RE_CALL\" INTEGER NOT NULL ," + // 20: isReCall
                "\"IS_CHECKED\" INTEGER NOT NULL ," + // 21: isChecked
                "\"GROUP_NUM\" TEXT," + // 22: groupNum
                "\"CONTACTS_ID\" TEXT UNIQUE );"); // 23: contactsId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CONTACTS_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ContactsEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getType());
        stmt.bindLong(3, entity.getEvent());
 
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(4, version);
        }
 
        String senderId = entity.getSenderId();
        if (senderId != null) {
            stmt.bindString(5, senderId);
        }
 
        String accepteId = entity.getAccepteId();
        if (accepteId != null) {
            stmt.bindString(6, accepteId);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(7, password);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(8, content);
        }
 
        String requestSourceSystem = entity.getRequestSourceSystem();
        if (requestSourceSystem != null) {
            stmt.bindString(9, requestSourceSystem);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(10, nickName);
        }
        stmt.bindLong(11, entity.getOccureTime());
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(12, groupId);
        }
 
        String portrait = entity.getPortrait();
        if (portrait != null) {
            stmt.bindString(13, portrait);
        }
 
        String msgStatus = entity.getMsgStatus();
        if (msgStatus != null) {
            stmt.bindString(14, msgStatus);
        }
 
        String isRead = entity.getIsRead();
        if (isRead != null) {
            stmt.bindString(15, isRead);
        }
        stmt.bindLong(16, entity.getCount());
        stmt.bindLong(17, entity.getIsStick());
        stmt.bindLong(18, entity.getIsShield());
        stmt.bindLong(19, entity.getStickTime());
        stmt.bindLong(20, entity.getGroupChat() ? 1L: 0L);
        stmt.bindLong(21, entity.getIsReCall() ? 1L: 0L);
        stmt.bindLong(22, entity.getIsChecked() ? 1L: 0L);
 
        String groupNum = entity.getGroupNum();
        if (groupNum != null) {
            stmt.bindString(23, groupNum);
        }
 
        String contactsId = entity.getContactsId();
        if (contactsId != null) {
            stmt.bindString(24, contactsId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ContactsEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getType());
        stmt.bindLong(3, entity.getEvent());
 
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(4, version);
        }
 
        String senderId = entity.getSenderId();
        if (senderId != null) {
            stmt.bindString(5, senderId);
        }
 
        String accepteId = entity.getAccepteId();
        if (accepteId != null) {
            stmt.bindString(6, accepteId);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(7, password);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(8, content);
        }
 
        String requestSourceSystem = entity.getRequestSourceSystem();
        if (requestSourceSystem != null) {
            stmt.bindString(9, requestSourceSystem);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(10, nickName);
        }
        stmt.bindLong(11, entity.getOccureTime());
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(12, groupId);
        }
 
        String portrait = entity.getPortrait();
        if (portrait != null) {
            stmt.bindString(13, portrait);
        }
 
        String msgStatus = entity.getMsgStatus();
        if (msgStatus != null) {
            stmt.bindString(14, msgStatus);
        }
 
        String isRead = entity.getIsRead();
        if (isRead != null) {
            stmt.bindString(15, isRead);
        }
        stmt.bindLong(16, entity.getCount());
        stmt.bindLong(17, entity.getIsStick());
        stmt.bindLong(18, entity.getIsShield());
        stmt.bindLong(19, entity.getStickTime());
        stmt.bindLong(20, entity.getGroupChat() ? 1L: 0L);
        stmt.bindLong(21, entity.getIsReCall() ? 1L: 0L);
        stmt.bindLong(22, entity.getIsChecked() ? 1L: 0L);
 
        String groupNum = entity.getGroupNum();
        if (groupNum != null) {
            stmt.bindString(23, groupNum);
        }
 
        String contactsId = entity.getContactsId();
        if (contactsId != null) {
            stmt.bindString(24, contactsId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ContactsEntity readEntity(Cursor cursor, int offset) {
        ContactsEntity entity = new ContactsEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // type
            cursor.getInt(offset + 2), // event
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // version
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // senderId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // accepteId
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // password
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // content
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // requestSourceSystem
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // nickName
            cursor.getLong(offset + 10), // occureTime
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // groupId
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // portrait
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // msgStatus
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // isRead
            cursor.getInt(offset + 15), // count
            cursor.getInt(offset + 16), // isStick
            cursor.getInt(offset + 17), // isShield
            cursor.getLong(offset + 18), // stickTime
            cursor.getShort(offset + 19) != 0, // groupChat
            cursor.getShort(offset + 20) != 0, // isReCall
            cursor.getShort(offset + 21) != 0, // isChecked
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // groupNum
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23) // contactsId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ContactsEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setType(cursor.getInt(offset + 1));
        entity.setEvent(cursor.getInt(offset + 2));
        entity.setVersion(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSenderId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAccepteId(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPassword(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setContent(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRequestSourceSystem(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setNickName(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setOccureTime(cursor.getLong(offset + 10));
        entity.setGroupId(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setPortrait(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setMsgStatus(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setIsRead(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCount(cursor.getInt(offset + 15));
        entity.setIsStick(cursor.getInt(offset + 16));
        entity.setIsShield(cursor.getInt(offset + 17));
        entity.setStickTime(cursor.getLong(offset + 18));
        entity.setGroupChat(cursor.getShort(offset + 19) != 0);
        entity.setIsReCall(cursor.getShort(offset + 20) != 0);
        entity.setIsChecked(cursor.getShort(offset + 21) != 0);
        entity.setGroupNum(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setContactsId(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ContactsEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ContactsEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
