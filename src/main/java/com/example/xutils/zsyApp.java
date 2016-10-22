package com.example.xutils;

import android.app.Application;

import org.xutils.x;

/*
 * Created by dell on 2016/6/17.
 */
public class zsyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//框架初始化
    }
}
