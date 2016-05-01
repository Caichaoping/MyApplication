package com.cc.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.cc.myapplication.R;
import com.cc.myapplication.utils.KeyBoardUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 注释：意见反馈
 * 作者：菠菜 on 2016/5/1 17:19
 * 邮箱：971859818@qq.com
 */
public class FeedBackActivity extends AppCompatActivity implements TextWatcher {
    @Bind(R.id.et_feed)
    EditText etFeed;
    @Bind(R.id.tv_feedtip)
    TextView tvFeedtip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        KeyBoardUtil.openKeybord(etFeed, FeedBackActivity.this);
        etFeed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(120)});
        etFeed.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        tvFeedtip.setHint("还可输入"+(120-s.toString().length())+"字");
    }
}
