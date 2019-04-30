package com.yst.im.imsdk.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yst.im.imsdk.bean.MessageBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MESSAGE_BEAN".
*/
public class MessageBeanDao extends AbstractDao<MessageBean, Long> {

    public static final String TABLENAME = "MESSAGE_BEAN";

    /**
     * Properties of entity MessageBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property AccepteId = new Property(0, String.class, "accepteId", false, "ACCEPTE_ID");
        public final static Property Content = new Property(1, String.class, "content", false, "CONTENT");
        public final static Property Event = new Property(2, int.class, "event", false, "EVENT");
        public final static Property GroupChat = new Property(3, boolean.class, "groupChat", false, "GROUP_CHAT");
        public final static Property Id = new Property(4, Long.class, "id", true, "_id");
        public final static Property NickName = new Property(5, String.class, "nickName", false, "NICK_NAME");
        public final static Property OccureTime = new Property(6, long.class, "occureTime", false, "OCCURE_TIME");
        public final static Property Password = new Property(7, String.class, "password", false, "PASSWORD");
        public final static Property RequestSourceSystem = new Property(8, String.class, "requestSourceSystem", false, "REQUEST_SOURCE_SYSTEM");
        public final static Property SenderId = new Property(9, String.class, "senderId", false, "SENDER_ID");
        public final static Property Type = new Property(10, int.class, "type", false, "TYPE");
        public final static Property Version = new Property(11, String.class, "version", false, "VERSION");
        public final static Property MessageType = new Property(12, int.class, "messageType", false, "MESSAGE_TYPE");
        public final static Property NewsId = new Property(13, String.class, "newsId", false, "NEWS_ID");
        public final static Property Path = new Property(14, String.class, "path", false, "PATH");
        public final static Property Second = new Property(15, int.class, "second", false, "SECOND");
        public final static Property SendState = new Property(16, int.class, "sendState", false, "SEND_STATE");
        public final static Property IsPlayed = new Property(17, boolean.class, "isPlayed", false, "IS_PLAYED");
        public final static Property IsPlaying = new Property(18, boolean.class, "isPlaying", false, "IS_PLAYING");
        public final static Property IsRead = new Property(19, int.class, "isRead", false, "IS_READ");
        public final static Property GroupId = new Property(20, String.class, "groupId", false, "GROUP_ID");
        public final static Property Portrait = new Property(21, String.class, "portrait", false, "PORTRAIT");
        public final static Property ContactsId = new Property(22, String.class, "contactsId", false, "CONTACTS_ID");
    };


    public MessageBeanDao(DaoConfig config) {
        super(config);
    }
    
    public MessageBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MESSAGE_BEAN\" (" + //
                "\"ACCEPTE_ID\" TEXT," + // 0: accepteId
                "\"CONTENT\" TEXT," + // 1: content
                "\"EVENT\" INTEGER NOT NULL ," + // 2: event
                "\"GROUP_CHAT\" INTEGER NOT NULL ," + // 3: groupChat
                "\"_id\" INTEGER PRIMARY KEY ," + // 4: id
                "\"NICK_NAME\" TEXT," + // 5: nickName
                "\"OCCURE_TIME\" INTEGER NOT NULL ," + // 6: occureTime
                "\"PASSWORD\" TEXT," + // 7: password
                "\"REQUEST_SOURCE_SYSTEM\" TEXT," + // 8: requestSourceSystem
                "\"SENDER_ID\" TEXT," + // 9: senderId
                "\"TYPE\" INTEGER NOT NULL ," + // 10: type
                "\"VERSION\" TEXT UNIQUE ," + // 11: version
                "\"MESSAGE_TYPE\" INTEGER NOT NULL ," + // 12: messageType
                "\"NEWS_ID\" TEXT," + // 13: newsId
                "\"PATH\" TEXT," + // 14: path
                "\"SECOND\" INTEGER NOT NULL ," + // 15: second
                "\"SEND_STATE\" INTEGER NOT NULL ," + // 16: sendState
                "\"IS_PLAYED\" INTEGER NOT NULL ," + // 17: isPlayed
                "\"IS_PLAYING\" INTEGER NOT NULL ," + // 18: isPlaying
                "\"IS_READ\" INTEGER NOT NULL ," + // 19: isRead
                "\"GROUP_ID\" TEXT," + // 20: groupId
                "\"PORTRAIT\" TEXT," + // 21: portrait
                "\"CONTACTS_ID\" TEXT);"); // 22: contactsId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MESSAGE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MessageBean entity) {
        stmt.clearBindings();
 
        String accepteId = entity.getAccepteId();
        if (accepteId != null) {
            stmt.bindString(1, accepteId);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(2, content);
        }
        stmt.bindLong(3, entity.getEvent());
        stmt.bindLong(4, entity.getGroupChat() ? 1L: 0L);
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(5, id);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(6, nickName);
        }
        stmt.bindLong(7, entity.getOccureTime());
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(8, password);
        }
 
        String requestSourceSystem = entity.getRequestSourceSystem();
        if (requestSourceSystem != null) {
            stmt.bindString(9, requestSourceSystem);
        }
 
        String senderId = entity.getSenderId();
        if (senderId != null) {
            stmt.bindString(10, senderId);
        }
        stmt.bindLong(11, entity.getType());
 
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(12, version);
        }
        stmt.bindLong(13, entity.getMessageType());
 
        String newsId = entity.getNewsId();
        if (newsId != null) {
            stmt.bindString(14, newsId);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(15, path);
        }
        stmt.bindLong(16, entity.getSecond());
        stmt.bindLong(17, entity.getSendState());
        stmt.bindLong(18, entity.getIsPlayed() ? 1L: 0L);
        stmt.bindLong(19, entity.getIsPlaying() ? 1L: 0L);
        stmt.bindLong(20, entity.getIsRead());
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(21, groupId);
        }
 
        String portrait = entity.getPortrait();
        if (portrait != null) {
            stmt.bindString(22, portrait);
        }
 
        String contactsId = entity.getContactsId();
        if (contactsId != null) {
            stmt.bindString(23, contactsId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MessageBean entity) {
        stmt.clearBindings();
 
        String accepteId = entity.getAccepteId();
        if (accepteId != null) {
            stmt.bindString(1, accepteId);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(2, content);
        }
        stmt.bindLong(3, entity.getEvent());
        stmt.bindLong(4, entity.getGroupChat() ? 1L: 0L);
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(5, id);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(6, nickName);
        }
        stmt.bindLong(7, entity.getOccureTime());
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(8, password);
        }
 
        String requestSourceSystem = entity.getRequestSourceSystem();
        if (requestSourceSystem != null) {
            stmt.bindString(9, requestSourceSystem);
        }
 
        String senderId = entity.getSenderId();
        if (senderId != null) {
            stmt.bindString(10, senderId);
        }
        stmt.bindLong(11, entity.getType());
 
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(12, version);
        }
        stmt.bindLong(13, entity.getMessageType());
 
        String newsId = entity.getNewsId();
        if (newsId != null) {
            stmt.bindString(14, newsId);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(15, path);
        }
        stmt.bindLong(16, entity.getSecond());
        stmt.bindLong(17, entity.getSendState());
        stmt.bindLong(18, entity.getIsPlayed() ? 1L: 0L);
        stmt.bindLong(19, entity.getIsPlaying() ? 1L: 0L);
        stmt.bindLong(20, entity.getIsRead());
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(21, groupId);
        }
 
        String portrait = entity.getPortrait();
        if (portrait != null) {
            stmt.bindString(22, portrait);
        }
 
        String contactsId = entity.getContactsId();
        if (contactsId != null) {
            stmt.bindString(23, contactsId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4);
    }    

    @Override
    public MessageBean readEntity(Cursor cursor, int offset) {
        MessageBean entity = new MessageBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // accepteId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // content
            cursor.getInt(offset + 2), // event
            cursor.getShort(offset + 3) != 0, // groupChat
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // id
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // nickName
            cursor.getLong(offset + 6), // occureTime
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // password
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // requestSourceSystem
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // senderId
            cursor.getInt(offset + 10), // type
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // version
            cursor.getInt(offset + 12), // messageType
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // newsId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // path
            cursor.getInt(offset + 15), // second
            cursor.getInt(offset + 16), // sendState
            cursor.getShort(offset + 17) != 0, // isPlayed
            cursor.getShort(offset + 18) != 0, // isPlaying
            cursor.getInt(offset + 19), // isRead
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // groupId
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // portrait
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22) // contactsId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MessageBean entity, int offset) {
        entity.setAccepteId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setContent(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEvent(cursor.getInt(offset + 2));
        entity.setGroupChat(cursor.getShort(offset + 3) != 0);
        entity.setId(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setNickName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setOccureTime(cursor.getLong(offset + 6));
        entity.setPassword(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRequestSourceSystem(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSenderId(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setType(cursor.getInt(offset + 10));
        entity.setVersion(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setMessageType(cursor.getInt(offset + 12));
        entity.setNewsId(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setPath(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setSecond(cursor.getInt(offset + 15));
        entity.setSendState(cursor.getInt(offset + 16));
        entity.setIsPlayed(cursor.getShort(offset + 17) != 0);
        entity.setIsPlaying(cursor.getShort(offset + 18) != 0);
        entity.setIsRead(cursor.getInt(offset + 19));
        entity.setGroupId(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setPortrait(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setContactsId(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MessageBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MessageBean entity) {
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
