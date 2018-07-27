package com.goldenasia.lottery.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 *快三秒秒彩
 * Created by Gan on 2018/1/29.
 */

public class MmcKuaiSanWinHistoryDao {
    private Dao<MmcKuaiSanWinHistory, Integer> dao;

    public MmcKuaiSanWinHistoryDao(Context context) {
        DatabaseHelper dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        try {
            dao = dbHelper.getDao(MmcKuaiSanWinHistory.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void savaMmcWinHistory(MmcKuaiSanWinHistory bean) {
        try {
            // 如果表中没有该MmcWinHistory则保存，根据主键是否相同来标示是否是同一MmcWinHistory
            dao.createIfNotExists(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MmcKuaiSanWinHistory> getAllMmcWinHistory() {
        try {
            return dao.queryBuilder().orderBy("uid", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(MmcKuaiSanWinHistory mmcWinHistory) {
        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("uid", mmcWinHistory.getUid());
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int deleteAllMmcWinHistory() {
        List<MmcKuaiSanWinHistory> allMmcWinHistory=getAllMmcWinHistory();
        if(allMmcWinHistory==null||allMmcWinHistory.size()==0)
            return 0;
        
        int  count=0;
        for(MmcKuaiSanWinHistory mmcWinHistory:allMmcWinHistory) {
            delete(mmcWinHistory);
            count++;
        }
        return  count;
    }

    // 查询记录的总数 select count(*) from database
    public int getCount() {
        try {
            return (int)(dao.queryBuilder().countOf());
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 分页查询
     *  sql = "select * from database limit ?,?"
     * @param currentPage 当前页
     * @param pageSize 每页显示的记录
     * @return 当前页的记录
     */
    public List<MmcKuaiSanWinHistory> getAllItems(int currentPage, int pageSize) {
        long firstResult = (currentPage - 1) * pageSize;
        long maxResult = currentPage * pageSize;
        List<MmcKuaiSanWinHistory> allMmcWinHistory=null;
        try {
            allMmcWinHistory=dao.queryBuilder().orderBy("uid", false).offset(firstResult).limit(maxResult).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allMmcWinHistory;
    }

}
