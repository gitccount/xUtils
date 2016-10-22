package com.example.xutils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import frament.DbFragment;
import frament.HttpFragment;
import frament.ImageFragment;
import frament.InjectFragment;

@ContentView(R.layout.activity_main)//Activity注解
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);//注解  加载布局,控件
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new InjectFragment();
                    case 1:
                        return new HttpFragment();
                    case 2:
                        return new ImageFragment();
                    case 3:
                        return new DbFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "注解模块";
                    case 1:
                        return "网络模块";
                    case 2:
                        return "图片模块";
                    case 3:
                        return "数据库模块";
                }
                return null;
            }
        });
    }
}
