package com.example.mm.telephonebook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mm.telephonebook.fragmennt.CallFragment;
import com.example.mm.telephonebook.fragmennt.ContactPersonFragment;
import com.example.mm.telephonebook.fragmennt.SmsFragment;
import com.example.mm.telephonebook.fragmennt.ContactNumberFragment;

import java.util.ArrayList;

/**
 * Name:        MainAdapter
 * Author:      Lambo
 * Function:    主界面适配器
 */
public class MainAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> data;
    private String[] titles={"拨号","通讯记录","联系人","短信"};
    public MainAdapter(FragmentManager fm) {
        super(fm);
        data = new ArrayList<>();//初始化数据
        CallFragment callFragment = new CallFragment();
        ContactNumberFragment secondFragment = new ContactNumberFragment();
        ContactPersonFragment thirdfragment = new ContactPersonFragment();
        SmsFragment forthfragment = new SmsFragment();
        //将Fragment加入到集合中
        data.add(callFragment);
        data.add(secondFragment);
        data.add(thirdfragment);
        data.add(forthfragment);
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size()>0&&data!=null?data.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
