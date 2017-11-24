package com.goldenasia.lottery.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Gan on 2017/9/29.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    // 数据库名称，会在程序的目录中生成goldenasia.db数据库文件
    public static String DATABASE_NAME = "goldenasia.db";
    // 数据库version
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);

    }
    // 初次运行程序会执行该onCreate方法,如果不是初次运行程序则不会执行该方法,防止重复建表。
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            //建立MmcWinHistory表
            TableUtils.createTable(connectionSource, MmcWinHistory.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //如果不是初次运行并且DATABASE_VERSION数值增加的时候，则会执行该方法，可以在该方法中删除原来的表并建立新表，在要修改数据表结构的时候使用。
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, MmcWinHistory.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放 DAO
     */
    @Override
    public void close() {
        super.close();
    }
}
