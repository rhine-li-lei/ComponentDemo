package com.lilei.testmodle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lilei.beerouter_anno.RouteNode;

/**
 * Created by lilei
 * Date : 2018/9/26
 */
@RouteNode(path = "/test",desc = "测试")
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
    }
}
