package com.goldenasia.lottery.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.util.BitmapUtil;
import com.goldenasia.lottery.util.RGBLuminanceSource;
import com.goldenasia.lottery.util.WindowUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2016/12/8.
 */

public class QRCodeFragment extends Activity {
    private static final String TAG = QRCodeFragment.class.getSimpleName();
    @BindView(android.R.id.home)
    ImageButton home;
    @BindView(android.R.id.title)
    TextView title;
    @BindView(R.id.web_html)
    WebView webHtml;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.qrcode)
    LinearLayout qrcodeLayout;
    @BindView(R.id.recharge_orders)
    TextView rechargeOrders;
    @BindView(R.id.recharge_payamt)
    TextView rechargePayamt;
    @BindView(R.id.qrcode_tip)
    TextView qrcodeTip;


    private String html;
    private String time;
    private String payamt;
    private String type;
    private File file = null;
    private OnStatusListener onStatusListener;
    private String forward = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.qrcode_fragment);
        ButterKnife.bind(this);

        WindowUtils.makeWindowTranslucent(this);

        html = getIntent().getStringExtra("html");
        payamt = getIntent().getStringExtra("payamt");
        type = getIntent().getStringExtra("type");
        if (html.isEmpty() || payamt.isEmpty() || type.isEmpty()) {
            return;
        }

        ImageButton backhome = (ImageButton) findViewById(android.R.id.home);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView titleText = (TextView) findViewById(android.R.id.title);
        titleText.setText("二维码扫描");

        Document doc = Jsoup.parse(html, "UTF-8");
        String srcjpg = "";
        String[] codehtml = null;
        if (type.equals("wechat")) {
            codehtml = html.split(",");
            if (codehtml[0].equals("OK")) {
                srcjpg = codehtml[2];
            }
        } else if (type.equals("alipay")) {
            // 扩展名为.png的图片
            Elements jpgs = doc.select("img[src$=.jpg]");
            for (Element jpg : jpgs) {
                srcjpg = jpg.attr("src");
            }
        }

        if (srcjpg.isEmpty()) {
            webHtml.setVisibility(View.VISIBLE);
            qrcodeLayout.setVisibility(View.GONE);
            WebSettings webSettings = webHtml.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);// 这个很关键
            webSettings.setLoadWithOverviewMode(true);

            webHtml.loadData(html, "text/html", "utf-8");
            webHtml.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

            if (onStatusListener != null) {
                onStatusListener.onStatus(202, "充值验证失败");
                MobclickAgent.reportError(getApplicationContext(), "充值失败->\t");
            }
        } else {
            webHtml.setVisibility(View.GONE);
            qrcodeLayout.setVisibility(View.VISIBLE);
            Log.i(TAG, "这里->" + srcjpg);
            String msg = this.getResources().getString(R.string.is_recharge_orders);
            String payamtStr = this.getResources().getString(R.string.is_recharge_payamt);
            if (type.equals("wechat")) {
                if (codehtml != null || codehtml.length > 0) {
                    msg = StringUtils.replaceEach(msg, new String[]{"ORDERS"}, new String[]{codehtml[1]});
                    payamtStr = StringUtils.replaceEach(payamtStr, new String[]{"PAYAMT"}, new String[]{payamt});
                }
            } else if (type.equals("alipay")) {
                Element orders = doc.getElementById("orderId");
                msg = StringUtils.replaceEach(msg, new String[]{"ORDERS"}, new String[]{orders.attr("value")});
                payamtStr = StringUtils.replaceEach(payamtStr, new String[]{"PAYAMT"}, new String[]{payamt});
            }

            rechargePayamt.setText(Html.fromHtml(payamtStr));
            rechargeOrders.setText(Html.fromHtml(msg));
            qrcodeTip.setText(type.equals("alipay") ? "长按二维码跳转到支付宝" : "截屏后，打开微信的扫一扫，在相册中选取截屏的图片即可支付。");
            scanner(srcjpg);
        }
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //统计应用启动数据
        PushAgent.getInstance(this).onAppStart();
    }

    private void scanner(String imageStr) {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(imageStr,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        Bitmap bitmap = null;
                        try {
                            bitmap = BitmapUtil.createQRCode(response, 400);
                        } catch (WriterException e) {
                            // TODO Auto-generated catch block
                            MobclickAgent.reportError(getApplicationContext(), "二维码图片转换错误");
                        }
                        image.setImageBitmap(bitmap);
                    }
                }, 0, 0,
                Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.default_image));
            }
        });
        mQueue.add(imageRequest);

        image.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                /*if (type.equals("wechat")) {
                    //获取当前屏幕的大小
                    Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                    saveImageToGallery(getApplicationContext(), bitmap);*//*
                } else */if (type.equals("alipay")) {
                    saveCurrentImage();
                }
                return true;
            }
        });
    }

    public void setOnStatusListener(OnStatusListener onStatusListener) {
        this.onStatusListener = onStatusListener;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webHtml != null) {
            webHtml.onResume();
        }
    }

    @Override
    protected void onPause() {
        if (webHtml != null) {
            webHtml.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (webHtml != null) {
            webHtml.destroy();
        }
        super.onDestroy();
    }

    //扫码 处理方法
    private void saveCurrentImage() {
        //获取当前屏幕的大小
        int width = getWindow().getDecorView().getRootView().getWidth();
        int height = getWindow().getDecorView().getRootView().getHeight();
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //找到当前页面的根布局
        View view = getWindow().getDecorView().getRootView();
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片,创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        temBitmap = view.getDrawingCache();
        SimpleDateFormat df = new SimpleDateFormat("yyyymmddhhmmss");
        time = df.format(new Date());
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/screen", time + ".png");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    MobclickAgent.reportError(getApplicationContext(), "二维码图片转换查找->" + e.getMessage());
                }
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                temBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/screen/" + time + ".png";
                    final Result result = parseQRcodeBitmap(path);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                // 目前无法利用Intent打开微信
                                Log.i(TAG,result.getText());
                                Uri uri = Uri.parse(result.getText());//Uri.parse("weixin://dl/scan")
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                                QRCodeFragment.this.finish();
                            } catch (Exception e) {
                                if (onStatusListener != null) {
                                    onStatusListener.onStatus(202, "充值验证失败");
                                    MobclickAgent.reportError(getApplicationContext(), "充值二维码扫描错误失败->\t" + result.toString());
                                }
                            }
                        }
                    });
                }
            }).start();
            view.setDrawingCacheEnabled(false);
        }
    }


    //解析二维码图片,返回结果封装在Result对象中
    private Result parseQRcodeBitmap(String bitmapPath) {
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        options.inSampleSize = options.outHeight / 400;
        if (options.inSampleSize <= 0) {
            options.inSampleSize = 1; //防止其值小于或等于0
        }
        /**
         * 辅助节约内存设置
         *
         * options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
         * options.inPurgeable = true;
         * options.inInputShareable = true;
         */
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
        //将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        //开始解析
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
        }
        return result;
    }

    @OnClick({android.R.id.home, R.id.qrcode})
    public void onClick(View view) {
        switch (view.getId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.qrcode:
                break;
        }
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("QRCodeFragment Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /**
     * 选中监听器
     */
    public interface OnStatusListener {
        void onStatus(int status, String memo);
    }

}
