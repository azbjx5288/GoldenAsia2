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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_result_mmc, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new HistoryCodeMmcAdapter();
        listView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadDataAsyncTask(getActivity()).execute();
    }

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
}
