package com.example.mm.telephonebook.fragmennt;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mm.telephonebook.R;
import com.example.mm.telephonebook.adapter.RecordAdapter;
import com.example.mm.telephonebook.beans.CallRecords;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Name:        ContactNumberFragment
 * Author:      Lambo
 * Function:    通话记录界面
 */
public class ContactNumberFragment extends Fragment {
    private static String NUMBER = CallLog.Calls.NUMBER;
    private static String CACHED_NAME = CallLog.Calls.CACHED_NAME;
    private static String TYPE = CallLog.Calls.TYPE;
    private static String DATE = CallLog.Calls.DATE;
    private ListView listView_conntact;
    //Handler来根据接收的消息，处理UI更新
    //Thread线程发出Handler消息，通知更新UI。
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            ArrayList<CallRecords> numberList = (ArrayList<CallRecords>) msg.obj;
            RecordAdapter adapter = new RecordAdapter(getActivity(), numberList);
            listView_conntact.setAdapter(adapter);
            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_number, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView_conntact = (ListView) view.findViewById(R.id.Listview_conntact);
        listView_conntact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position代表当前点击的item位置
                //parent---> 当前的listview
                //1.获取到当前listview对应的adapter
                Adapter adapter = parent.getAdapter();//就是chatAdapter
                //2.通过Adapter的getItem()方法,获取到对应位置的item数据
                CallRecords bean = (CallRecords) adapter.getItem(position);
                String phone = handleNum(bean.getAddress());
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ReadContactNumber readContactNumber = new ReadContactNumber();
        Thread thread = new Thread(readContactNumber);
        thread.start();

    }

    private class ReadContactNumber implements Runnable {

        @Override
        public void run() {
            //获取内容提供者
            ContentResolver resolver = getActivity().getContentResolver();
            Uri uri = CallLog.Calls.CONTENT_URI;
            String[] projection = {NUMBER, CACHED_NAME, TYPE, DATE};
            Cursor cursor = resolver.query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(NUMBER);
                int cachedIndex = cursor.getColumnIndex(CACHED_NAME);
                int typeIndex = cursor.getColumnIndex(TYPE);
                int dateIndex = cursor.getColumnIndex(DATE);
                List<CallRecords> numberList = new ArrayList<>();
                do {
                    //取值
                    String cachedName = cursor.getString(cachedIndex);
                    String number = cursor.getString(numberIndex);
                    if (cachedName == null) {
                        cachedName = "未知号码";
                    }
                    String type = cursor.getString(typeIndex);
                    if (type.equals("1")) {
                        type = "已接";
                    } else if (type.equals("2")) {
                        type = "已拨";
                    } else if (type.equals("3")) {
                        type = "未接";
                    }

                    String dateNumber = cursor.getString(dateIndex);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(dateNumber));
                    String date = format.format(d);
                    //存值
                    CallRecords bean = new CallRecords(number, cachedName, date, type);
                    numberList.add(bean);

                } while (cursor.moveToNext());
                //需要关闭游标
                cursor.close();
                Message message = new Message();
                message.obj = numberList;
                handler.sendMessage(message);

            }
        }
    }

    //电话号码格式化 去掉空格和-
    private String handleNum(String desNumber) {
        return desNumber.replace("-", "").replace(" ", "").replace("(", "").replace(")", "");

    }
}

