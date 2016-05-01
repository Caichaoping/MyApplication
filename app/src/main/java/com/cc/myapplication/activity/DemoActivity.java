package com.cc.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cc.myapplication.R;
import com.cc.myapplication.common.UpdateManager;
import com.cc.myapplication.other.photoBrowse.PhotoBrowseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注释：
 * 作者：菠菜 on 2016/5/1 15:30
 * 邮箱：971859818@qq.com
 */
public class DemoActivity extends AppCompatActivity {
    @Bind(R.id.btn_demo1)
    Button btnDemo1;
    @Bind(R.id.btn_demo2)
    Button btnDemo2;

    String[] strings = {"http://pic4.zhimg.com//aa94e197491fb9c44d384c4747773810.jpg",
            "http://www.eoeandroid.com/data/attachment/forum/201107/18/142935bbi8d3zpf3d0dd7z.jpg",
            "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg"};
    @Bind(R.id.btn_demo3)
    Button btnDemo3;
    @Bind(R.id.btn_demo4)
    Button btnDemo4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_demo1, R.id.btn_demo2, R.id.btn_demo3, R.id.btn_demo4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_demo1:
                PhotoBrowseActivity.showPhotoBrowse(DemoActivity.this,strings,0);
                break;
            case R.id.btn_demo2:
                UpdateManager.getUpdateManager().checkAppUpdate(this, true);
                break;
            case R.id.btn_demo3:
                startActivity(new Intent(DemoActivity.this, MyInfoDetailActivity.class));
                break;
            case R.id.btn_demo4:
                startActivity(new Intent(DemoActivity.this, FeedBackActivity.class));
                break;
        }
    }

}
