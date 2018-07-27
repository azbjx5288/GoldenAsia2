package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.goldenasia.lottery.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UMessageActivity extends Activity {


//    @BindView(R.id.description)
//    TextView tv_description;
    @BindView(R.id.title)
    TextView tv_title;
    @BindView(R.id.content)
    TextView tv_content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umessage);
        ButterKnife.bind(this);

        Bundle bundle = this.getIntent().getExtras();
//        String description = bundle.getString("description");
        String title = bundle.getString("title");
        String content = bundle.getString("content");

//        tv_description.setText(description);
        tv_title.setText(title);
        tv_content.setText(content);
    }

}
