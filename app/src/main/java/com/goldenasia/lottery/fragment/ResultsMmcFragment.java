package com.goldenasia.lottery.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.db.MmcElevenSelectFiveWinHistory;
import com.goldenasia.lottery.db.MmcElevenSelectFiveWinHistoryDao;
import com.goldenasia.lottery.db.MmcKuaiSanWinHistory;
import com.goldenasia.lottery.db.MmcKuaiSanWinHistoryDao;
import com.goldenasia.lottery.db.MmcWinHistory;
import com.goldenasia.lottery.db.MmcWinHistoryDao;
import com.goldenasia.lottery.view.adapter.HistoryCodeMmcAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Gan on 2017/9/29.
 * 秒秒彩开奖fragment
 */

public class ResultsMmcFragment extends BaseFragment {
    private static final String TAG = ResultsMmcFragment.class.getSimpleName();

    @BindView(R.id.history_lottery_listviewcode)
    ListView listView;
    private static HistoryCodeMmcAdapter adapter;
    private Lottery lottery;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_result_mmc, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lottery = (Lottery) getArguments().getSerializable("lottery");

        adapter = new HistoryCodeMmcAdapter();
        listView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        switch (lottery.getLotteryId()) {
            case 15://亚洲妙妙彩
                adapter.setmLoteryType(1);
                new LoadDataAsyncTask(getActivity()).execute();
                break;
            case 44://11选5秒秒彩
                adapter.setmLoteryType(2);
                new ElevenSelect5LoadDataAsyncTask(getActivity()).execute();
                break;
            case 45://快三秒秒彩
                adapter.setmLoteryType(3);
                new KuaiSanLoadDataAsyncTask(getActivity()).execute();
        }
    }

    //亚洲妙妙彩
    private static class LoadDataAsyncTask extends AsyncTask<Void, Void, List<MmcWinHistory>> {
        private final WeakReference<Context> mContext;

        public LoadDataAsyncTask(Context context) {
            mContext = new WeakReference<Context>(context);
        }

        @Override
        protected List<MmcWinHistory> doInBackground(Void... params) {

            Context context = mContext.get();

            if(context==null){
                return  null;
            }
            MmcWinHistoryDao mMmcWinHistoryDao = new MmcWinHistoryDao(context);

            return mMmcWinHistoryDao.getAllMmcWinHistory();
        }

        @Override
        protected void onPostExecute(List<MmcWinHistory> mmcWinHistories) {
            if(mmcWinHistories!=null&&adapter!=null) {
                adapter.setCodeList(mmcWinHistories);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
    //11选5秒秒彩
    private static class ElevenSelect5LoadDataAsyncTask extends AsyncTask<Void, Void, List<MmcElevenSelectFiveWinHistory>> {
        private final WeakReference<Context> mContext;

        public ElevenSelect5LoadDataAsyncTask(Context context) {
            mContext = new WeakReference<Context>(context);
        }

        @Override
        protected List<MmcElevenSelectFiveWinHistory> doInBackground(Void... params) {

            Context context = mContext.get();

            if(context==null){
                return  null;
            }
            MmcElevenSelectFiveWinHistoryDao mMmcWinHistoryDao = new MmcElevenSelectFiveWinHistoryDao(context);

            return mMmcWinHistoryDao.getAllMmcWinHistory();
        }

        @Override
        protected void onPostExecute(List<MmcElevenSelectFiveWinHistory> mmcWinHistories) {
            if(mmcWinHistories!=null&&adapter!=null) {
                adapter.setCodeList(mmcWinHistories);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
    //快三秒秒彩
    private static class KuaiSanLoadDataAsyncTask extends AsyncTask<Void, Void, List<MmcKuaiSanWinHistory>> {
        private final WeakReference<Context> mContext;

        public KuaiSanLoadDataAsyncTask(Context context) {
            mContext = new WeakReference<Context>(context);
        }

        @Override
        protected List<MmcKuaiSanWinHistory> doInBackground(Void... params) {

            Context context = mContext.get();

            if(context==null){
                return  null;
            }
            MmcKuaiSanWinHistoryDao mMmcWinHistoryDao = new MmcKuaiSanWinHistoryDao(context);

            return mMmcWinHistoryDao.getAllMmcWinHistory();
        }

        @Override
        protected void onPostExecute(List<MmcKuaiSanWinHistory> mmcWinHistories) {
            if(mmcWinHistories!=null&&adapter!=null) {
                adapter.setCodeList(mmcWinHistories);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
