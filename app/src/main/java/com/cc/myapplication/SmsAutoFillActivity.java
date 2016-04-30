package com.cc.myapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注释：短信自动填充
 * 作者：菠菜 on 2016/4/30 11:46
 * 邮箱：971859818@qq.com
 */
public class SmsAutoFillActivity extends AppCompatActivity {
    @Bind(R.id.btn_back)
    ImageView btnBack;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.btn_getcode)
    TextView btnGetcode;
    @Bind(R.id.btn_bind)
    Button btnBind;

    private MyCount myCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        ButterKnife.bind(this);
        initView();
    }


    // 界面初始化
    private void initView() {
        myCount = new MyCount(60000, 1000);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @OnClick({R.id.btn_getcode, R.id.btn_bind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getcode:
                myCount.start();
                break;
            case R.id.btn_bind:
                Log.d("cc","点击了绑定");
                break;
        }
    }


    // 验证码倒计时

    class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnGetcode.setEnabled(false);
            btnGetcode.setText(millisUntilFinished / 1000 + "秒后再次获取");
        }

        @Override
        public void onFinish() {
            btnGetcode.setEnabled(true);
            btnGetcode.setText("获取验证码");
        }
    }
}
