package com.cc.myapplication.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.myapplication.R;
import com.cc.myapplication.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

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
                if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                    ToastUtil.showToastShort(SmsAutoFillActivity.this, "亲！手机号不能为空噢");
                    return;
                }
                myCount.start();
                ToastUtil.showToastShort(SmsAutoFillActivity.this, "验证码正在快马加鞭赶过来....");
                BmobSMS.requestSMSCode(SmsAutoFillActivity.this, etPhone.getText().toString(), "模板1", new RequestSMSCodeListener() {

                    @Override
                    public void done(Integer smsId, BmobException ex) {
                        if (ex == null) {//验证码发送成功
                            Log.i("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                            ToastUtil.showToastShort(SmsAutoFillActivity.this, "发送成功，短信id" + smsId);
                        }
                    }
                });
                break;
            case R.id.btn_bind:
                if (TextUtils.isEmpty(etCode.getText().toString().trim())) {
                    ToastUtil.showToastShort(SmsAutoFillActivity.this, "亲！未输入验证码");
                    return;
                }
                BmobSMS.verifySmsCode(SmsAutoFillActivity.this, etPhone.getText().toString(), etCode.getText().toString(), new VerifySMSCodeListener() {

                    @Override
                    public void done(BmobException ex) {
                        if (ex == null) {//短信验证码已验证成功
                            ToastUtil.showToastShort(SmsAutoFillActivity.this, "绑定成功");
                        } else {
                            ToastUtil.showToastShort(SmsAutoFillActivity.this, "验证码错误");
                        }
                    }
                });
                Log.d("cc", "点击了绑定");
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

