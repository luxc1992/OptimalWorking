package com.yst.onecity.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.yst.onecity.bean.issue.CityBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 选择城市数据库管理类
 * 可以返回城市以及城市id
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/5/17
 */
public class DbManager {
    private static final String ASSETS_NAME = "china_cities.db";
    private static final String DB_NAME = "china_cities.db";
    private static final String TABLE_NAME = "city";
    private static final String NAME = "name";
    private static final String PINYIN = "pinyin";
    private static final String ID = "id";
    private static final int BUFFER_SIZE = 1024;
    private String dbPath;
    private Context mContext;

    public DbManager(Context context) {
        this.mContext = context;
        dbPath = File.separator + "data"
                + Environment.getDataDirectory().getAbsolutePath() + File.separator
                + context.getPackageName() + File.separator + "databases" + File.separator;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void copyDBFile() {
        File dir = new File(dbPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dbFile = new File(dbPath + DB_NAME);
        if (!dbFile.exists()) {
            InputStream is;
            OutputStream os;
            try {
                is = mContext.getResources().getAssets().open(ASSETS_NAME);
                os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = is.read(buffer, 0, buffer.length)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<CityBean> getAllCities() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath + DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        List<CityBean> result = new ArrayList<>();
        CityBean city;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            city = new CityBean(name, pinyin, id);
            result.add(city);
        }
        cursor.close();
        db.close();
        Collections.sort(result, new CityComparator());
        return result;
    }

    public List<CityBean> searchCity(final String keyword) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath + DB_NAME, null);
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where name like \"%" + keyword
                + "%\" or pinyin like \"%" + keyword + "%\"", null);
        List<CityBean> result = new ArrayList<>();
        CityBean city;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(PINYIN));
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            city = new CityBean(name, pinyin, id);
            result.add(city);
        }
        cursor.close();
        db.close();
        Collections.sort(result, new CityComparator());
        return result;
    }

    private int cityid;

    public int findIdByName(String keyword) {
        String sql = "select id from city where name like \"%" + keyword + "%\"";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath + DB_NAME, null);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            cityid = cursor.getInt(cursor.getColumnIndex(ID));
        }
        return cityid;
    }

    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<CityBean> {
        @Override
        public int compare(CityBean lhs, CityBean rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }
}
