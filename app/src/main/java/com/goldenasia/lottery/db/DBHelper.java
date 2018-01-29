package com.goldenasia.lottery.db;

import android.content.Context;

/**
 * Created by Gan on 2018/1/29.
 */

public class DBHelper {

    private Context context;

    public DBHelper(Context context) {
        this.context = context;
    }

    public void deleteAllTable(){
        MmcWinHistoryDao mmcWinHistoryDao = new MmcWinHistoryDao(context);
        MmcElevenSelectFiveWinHistoryDao mmcElevenSelectFiveWinHistoryDao = new MmcElevenSelectFiveWinHistoryDao(context);
        MmcKuaiSanWinHistoryDao mmcKuaiSanWinHistoryDao = new MmcKuaiSanWinHistoryDao(context);

        mmcWinHistoryDao.deleteAllMmcWinHistory();
        mmcElevenSelectFiveWinHistoryDao.deleteAllMmcWinHistory();
        mmcKuaiSanWinHistoryDao.deleteAllMmcWinHistory();
    }
}
