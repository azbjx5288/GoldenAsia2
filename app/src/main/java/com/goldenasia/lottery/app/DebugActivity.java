package com.goldenasia.lottery.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.base.Preferences;
import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.base.net.NetStateHelper;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.base.thread.Future;
import com.goldenasia.lottery.base.thread.FutureListener;
import com.goldenasia.lottery.base.thread.ThreadPool;
import com.goldenasia.lottery.data.LoginCommand;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.LotteryListCommand;
import com.goldenasia.lottery.data.UserInfo;
import com.goldenasia.lottery.fragment.GoldenLoginFragment;
import com.goldenasia.lottery.fragment.NoticeListFragment;
import com.goldenasia.lottery.fragment.ShoppingFragment;
import com.google.gson.reflect.TypeToken;
import com.umeng.message.PushAgent;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;

/**
 * Created by Alashi on 2015/12/22.
 */
public class DebugActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DebugActivity.class.getSimpleName();

    private TextView logView;
    private SparseArray<Object> array = new SparseArray<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        logView = (TextView)findViewById(R.id.text);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                launch(array.keyAt(arg2));
            }
        });
        loadData();
        findViewById(R.id.fab).setOnClickListener(this);
        GoldenAsiaApp.getNetStateHelper().addWeakListener(new NetStateHelper.NetStateListener() {
            @Override
            public void onStateChange(boolean isConnected) {
                String connect = GoldenAsiaApp.getNetStateHelper().isConnected()?
                        "网络连接：可以": "网络连接：无网络";
                logView.setText(connect);
            }
        });
        String connect = GoldenAsiaApp.getNetStateHelper().isConnected()?
                "网络连接：可以": "网络连接：无网络";
        logView.setText(connect);
        //统计应用启动数据
        PushAgent.getInstance(this).onAppStart();
    }

    private void loadData() {
        array.append(0, "登录");
        array.append(1, "清除session");
        array.append(2, "清除session&用户名&密码");
        array.append(3, "访问lotteryList接口");

        addItem("登录页", GoldenLoginFragment.class);
        addItem("测试Fragment页面", TestFragment.class);
        addItem("公告", NoticeListFragment.class);
        addItem("购物车", ShoppingFragment.class);
    }

    private void launch(int key) {
        switch (key) {
            case 0:
                testLogin();
                break;
            case 1:
                GoldenAsiaApp.getUserCentre().saveSession(null);
                break;
            case 2:
                GoldenAsiaApp.getUserCentre().saveSession(null);
                GoldenAsiaApp.getUserCentre().saveLoginInfo(null, null);
                GoldenAsiaApp.getUserCentre().setUserInfo(null);
                break;
            case 3:
                testGetLotteryListCommand();
                break;

            default: {
                Object object = array.get(key);
                if (object instanceof DataItem) {
                    FragmentLauncher.launch(DebugActivity.this, ((DataItem)object).fragment);
                } else {
                    Log.e(TAG, "launch: 未处理的列表项, key=" + key + ", v=" + object);
                }
                break;
            }
        }
    }

    private void testThreadPool() {
        GoldenAsiaApp.getThreadPool().submit(new ThreadPool.Job<Void>() {
            @Override
            public Void run(ThreadPool.JobContext jc) {
                Log.i(TAG, "run: ThreadPool Job running!");
                return null;
            }
        }, new FutureListener<Void>() {
            @Override
            public void onFutureDone(Future<Void> future) {
                Toast.makeText(DebugActivity.this, "线程池，回调到UI线程", Toast.LENGTH_SHORT).show();
            }
        }, true);
    }

    private void testLogin() {
        LoginCommand command = new LoginCommand();
        command.setUsername("zddavy");
        command.setEncpassword(DigestUtils.md5Hex("davy123"));
        RestRequestManager.executeCommand(this, command, restCallback, 0 , this);
    }

    private void testGetLotteryListCommand() {
        LotteryListCommand command = new LotteryListCommand();
        //command.setLotteryID(1);
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Lottery>>>(){};
        RestRequestManager.executeCommand(this, command,typeToken,
                restCallback, 0 , this);
    }

    private RestCallback restCallback = new RestCallback() {
        private String[] stateString = {"IDLE", "RUNNING", "DONE", "QUIT"};
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            //网络请求调用成功，处理结果
            Log.i(TAG, "onRestComplete: " + response);
            //Log.i(TAG, "onRestComplete: isHasMore.getData = "
            //        + response.getData().getClass() + ", " + response.getData());
            if (response.getData() instanceof UserInfo) {
                UserInfo info = (UserInfo) response.getData();
                Log.d(TAG, "onRestComplete: "+ GsonHelper.toJson(info));
                Log.d(TAG, "onRestComplete: "+ info.getLastTime());
            }
            return true;
        }
        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            //请求失败，异常情况处理
            Log.w(TAG, "onRestError() called with: errCode = [" + errCode + "], errDesc = [" + errDesc + "]");
            //logView.setText("出错了：/n errCode=" + errCode + ", errDesc=" + errDesc);
            return false;
        }
        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            //状态响应，如开始时弹等待对话框，结束时隐藏对话框
            logView.setText(stateString[state]);
            Log.d(TAG, "onRestStateChanged: " + stateString[state]);
        }
    };

    private void addItem(String text, Class<? extends Fragment> fragment) {
        array.append(array.size(), new DataItem(text, fragment));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            String name = Preferences.getString(this, "debug_last_launch_fragment", null);
            try {
                if (name != null && getClassLoader().loadClass(name) != null) {
                    FragmentLauncher.launch(this, name);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static class DataItem {
        String text;
        Class<? extends Fragment> fragment;

        public DataItem(String text, Class<? extends Fragment> fragment) {
            this.text = text;
            this.fragment = fragment;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(DebugActivity.this).inflate(android.R.layout.simple_list_item_1, null);
            }

            TextView view = (TextView) convertView.findViewById(android.R.id.text1);
            Object data = array.get(array.keyAt(position));
            view.setText(position +": "+ data.toString());

            return convertView;
        }
    }
}
