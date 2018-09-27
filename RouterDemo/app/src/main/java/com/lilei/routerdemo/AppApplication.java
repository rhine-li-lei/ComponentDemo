package com.lilei.routerdemo;

import android.app.Application;

import com.lilei.beerouter_api.UIRouter.UIRouter;

/**
 * Created by lilei
 * Date : 2018/9/27
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        UIRouter.getInstance().registerUI("app");
        UIRouter.getInstance().registerUI("testmodule");
    }

}
