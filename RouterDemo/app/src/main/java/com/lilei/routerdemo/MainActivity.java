package com.lilei.routerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lilei.beerouter_anno.RouteNode;
import com.lilei.beerouter_api.UIRouter.UIRouter;

@RouteNode(path = "/main", desc = "首页")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jump(View view) {
//        UIRouter.getInstance().openUri(MainActivity.this, "BeeComp://app/test");
        Boolean isopen;
        isopen = UIRouter.getInstance().openUri(MainActivity.this, "BeeComp://testmodule/test");
        Log.d("TAG", "jump: "+isopen);
    }
}
