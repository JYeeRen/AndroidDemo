package com.example.mm.telephonebook.fragmennt;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mm.telephonebook.activity.DetailActivity;
import com.example.mm.telephonebook.adapter.ContactAdapter;
import com.example.mm.telephonebook.beans.CallRecords;
import com.example.mm.telephonebook.beans.ContactNumber;
import com.example.mm.telephonebook.beans.Person;
import com.example.mm.telephonebook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Name:  CallFragment
 * Author:  Lambo
 * Function:联系人
 */
public class ContactPersonFragment extends Fragment {
    private static final String DISPLAY_NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    private static final String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private ListView listView;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            ArrayList<ContactNumber> peopleList = (ArrayList<ContactNumber>) msg.obj;
            ContactAdapter adapter = new ContactAdapter(getActivity(), peopleList);
            listView.setAdapter(adapter);
            return false;
        }
    });

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_person, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.Listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter = parent.getAdapter();
                ContactNumber bean = (ContactNumber) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("People",bean);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        contacts=new ArrayList<>();
//        //读取联系人
//        //contentprovider 内容提供者,Android四大组件之一,用来向其他应用共享数据
//        //获取其他应用的数据,需要用到contentResolver
//        ContentResolver resolver = getActivity().getContentResolver();
//        //Cursor 游标 query参数说明如下
//        //1.Uri资源定位符
//        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//        //2.String[] 查询结果的列
//        String[] projection = {DISPLAY_NAME, NUMBER};
//        //3. 4. 查询条件
//        String selection = "display_name=? and sex=?";
//        String[] selecionArgs = {"蓝波", "女"};
//        //5. 排序 根据某个字段进行排序
//        String sortOrder = "age";
//        Cursor cursor = resolver.query(uri, projection, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            int displayIndex = cursor.getColumnIndex(DISPLAY_NAME);
//            int numberIndex = cursor.getColumnIndex(NUMBER);
//            do {
//                //取值
//                String displayName = cursor.getString(displayIndex);
//                String number = cursor.getString(numberIndex);
//                //存值
//                ContactNumber bean=new ContactNumber(displayName,number);
//                contacts.add(bean);
//
//            } while (cursor.moveToNext());
//            //需要关闭游标
//            cursor.close();
//            ContactAdapter adapter=new ContactAdapter(getActivity(),contacts);
//            listView.setAdapter(adapter);
        ReadPeople readPeople = new ReadPeople();
        Thread thread = new Thread(readPeople);
        thread.start();


    }

    private class ReadPeople implements Runnable {
        @Override
        public void run() {
            ContentResolver resolver = getActivity().getContentResolver();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = {DISPLAY_NAME, NUMBER};
            String selection = "display_name=? and sex=?";
            String[] selecionArgs = {"蓝波", "女"};
            String sortOrder = "age";
            Cursor cursor = resolver.query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int displayIndex = cursor.getColumnIndex(DISPLAY_NAME);
                int numberIndex = cursor.getColumnIndex(NUMBER);
                List<ContactNumber> peopleList = new ArrayList<>();
                do {
                    //取值
                    String displayName = cursor.getString(displayIndex);
                    String number = cursor.getString(numberIndex);
                    //存值
                    ContactNumber bean = new ContactNumber(displayName, number);
                    peopleList.add(bean);

                } while (cursor.moveToNext());
                //需要关闭游标
                cursor.close();
                Message message = new Message();
                message.obj = peopleList;
                handler.sendMessage(message);
//                handler.sendEmptyMessage(0);

            }
        }


    }

    //电话号码格式化 去掉空格和-
    private String handleNum(String desNumber) {
        return desNumber.replace("-", "").replace(" ", "").replace("(", "").replace(")", "");

    }
}
