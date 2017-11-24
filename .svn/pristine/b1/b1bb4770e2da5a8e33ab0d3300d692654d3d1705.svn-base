package com.goldenasia.lottery.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.db.MmcWinHistory;
import com.goldenasia.lottery.db.MmcWinHistoryDao;
import com.goldenasia.lottery.view.adapter.HistoryCodeMmcAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Gan on 2017/9/29.
 * 秒秒彩开奖fragment
 */

public class ResultsMmcFragment extends BaseFragment {
    private static final String TAG = ResultsMmcFragment.class.getSimpleName();

    @Bind(R.id.history_lottery_listviewcode)
    ListView listView;
    private HistoryCodeMmcAdapter adapter;
    private MmcWinHistoryDao mMmcWinHistoryDao;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_result_mmc, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMmcWinHistoryDao = new MmcWinHistoryDao(getActivity());
        adapter = new HistoryCodeMmcAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        new LoadDataAsyncTask().execute();
    }


    class LoadDataAsyncTask extends AsyncTask<Void, Void, List<MmcWinHistory>> {

        @Override
        protected List<MmcWinHistory> doInBackground(Void... params) {
            return mMmcWinHistoryDao.getAllMmcWinHistory();
        }

        @Override
        protected void onPostExecute(List<MmcWinHistory> mmcWinHistories) {
            adapter.setCodeList(mmcWinHistories);
            adapter.notifyDataSetChanged();
        }
    }
}
