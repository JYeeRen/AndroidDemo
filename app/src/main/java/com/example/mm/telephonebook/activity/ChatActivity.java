package com.example.mm.telephonebook.activity;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mm.telephonebook.R;
import com.example.mm.telephonebook.adapter.ChatAdapter;
import com.example.mm.telephonebook.beans.ContactNumber;
import com.example.mm.telephonebook.beans.SmsBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Name:        ChatActivity
 * Author:      Lambo
 * Function:    短信聊天界面
 */

public class ChatActivity extends AppCompatActivity {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    //声明布局中的控件
    private ListView lvChat;
    private Button btnSend;
    private EditText etInput;


    //声明接到的实体类
    private SmsBean bean;               //短信内容类
    private String desNumber;           //用于存放电话号码
    private SmsReceiver receiver;       //接收短信类
    private String threadId;            //线程号

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initData();
        //android四大组件之一 广播接收器BroadcastReceiver,系统广播 自定义广播
        //系统广播:在系统执行某些特定的事件时,会发生广播; 开机/电量变化/网络状态变化/
        //自定义广播:开发者在app中选择一个特定的时刻
        //广播是可以跨应用的,作为四大组件之一需要注册
        //注册方式:1.静态:在Manifest中注册2.动态:在java代码中注册
        //对广播接收器进行注册**动态注册**必须解除注册
        receiver = new SmsReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SMS_RECEIVED);
        registerReceiver(receiver, filter);


    }
    //当前acitivity被关闭时 自动调用


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        //获取上一界面传递的intent
        Intent intent = getIntent();
        //获取传递过来的实体类
        bean = (SmsBean) intent.getSerializableExtra("SmsBean");
        if (bean == null) {
            Log.d("b", "b");
            desNumber = (String) intent.getSerializableExtra("phone");
            threadId = null;
        } else {
            Log.d("a", "a");
            threadId = bean.getThreadId();
            desNumber = bean.getAddress();
        }
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击发送按钮所执行的代码
                //获取输入框内文本
                String content = etInput.getText().toString();
                //判断输入框内内容是否合法
                if (content == null || content.trim().length() == 0) {
                    return;
                }
                sendMessage(desNumber, content);
                etInput.setText("");//清空
            }
        });
//        query(threadId);
        readMessages();
    }

    private void sendMessage(String desNumber, String content) {
        //初始化短信管理器
        SmsManager manager = SmsManager.getDefault();
        //发送短信
        manager.sendTextMessage(desNumber, null, content, null, null);//1.目标手机号2.自己手机号 可以填空3.发送短信的内容
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d = new Date();
        String date = sf.format(d);

        SmsBean bean = new SmsBean("2", date, desNumber, content, "", "");
        ChatAdapter adapter = (ChatAdapter) lvChat.getAdapter();
        adapter.sendMessage(bean);
        lvChat.smoothScrollToPosition(adapter.getCount() - 1);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        lvChat = (ListView) findViewById(R.id.list_view_chat);
        btnSend = (Button) findViewById(R.id.btn_send);
        etInput = (EditText) findViewById(R.id.et_input);
    }

    /*
    新方法

     */
    //线程
    private class ReadMessage implements Runnable {

        @Override
        public void run() {
            query(threadId);

        }
    }

    private void readMessages() {
        ReadMessage readMessage = new ReadMessage();
        Thread thread = new Thread(readMessage);
        thread.start();
    }

    /**
     * @param receiveThreadId
     */
    private void query(String receiveThreadId) {
        ContentResolver resolver = getContentResolver();
        String selection;
        String[] selectionArgs;
        if (receiveThreadId != null) {
            selection = "thread_id =? ";
            selectionArgs = new String[]{receiveThreadId};
        } else {
            selection = "address = ?";
            selectionArgs = new String[]{desNumber};
        }


        Cursor cursor = resolver.query(Telephony.Sms.CONTENT_URI, null, selection, selectionArgs, "date");
        final List<SmsBean> smsList = new ArrayList<>();
        final ChatAdapter adapter = new ChatAdapter(this, smsList);
        lvChat.setAdapter(adapter);

        if (cursor != null && cursor.moveToFirst()) {
            int typeIndex = cursor.getColumnIndex(Telephony.Sms.TYPE);
            int dateIndex = cursor.getColumnIndex(Telephony.Sms.DATE);
            int addressIndex = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
            int bodyIndex = cursor.getColumnIndex(Telephony.Sms.BODY);
            int readIndex = cursor.getColumnIndex(Telephony.Sms.READ);
            int threadIdIndex = cursor.getColumnIndex(Telephony.Sms.THREAD_ID);//用来查询用户


            do {
                String type = cursor.getString(typeIndex);
                String dateNumber = cursor.getString(dateIndex);
                String address = cursor.getString(addressIndex);
                String body = cursor.getString(bodyIndex);
                String read = cursor.getString(readIndex);
                String threadId = cursor.getString(threadIdIndex);
                //格式转换
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date d = new Date(Long.parseLong(dateNumber));
                String date = format.format(d);

                SmsBean sms = new SmsBean(type, date, address, body, read, threadId);
                smsList.add(sms);
            } while (cursor.moveToNext());
            cursor.close();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    adapter.notifyDataSetInvalidated();
//                    lvChat.smoothScrollToPosition(smsList.size() - 1);
//
//                }
//            }, 1000);
            adapter.notifyDataSetInvalidated();
            lvChat.smoothScrollToPosition(smsList.size() - 1);

        }
    }

    private class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAGGGGG", "onReceive");
            //接收到广播时,会执行此方法
            //短信内容储存在Intent中
            //在Intent中读取所需信息
            Object[] objs = (Object[]) intent.getExtras().get("pdus");//系统规定值
            String body = "";
            String address = "";
            for (int i = 0; i < objs.length; i++) {
                Object obj = objs[i];
                SmsMessage message = SmsMessage.createFromPdu((byte[]) obj);
                body += message.getMessageBody();
                address = message.getOriginatingAddress();

            }
            //进行判断当前页面是属于哪个手机号
            if (handleNum(desNumber).equals(address)) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date d = new Date();
                String date = sf.format(d);
                SmsBean bean = new SmsBean("1", date, desNumber, body, "", "");//1 接收 2发送
                ChatAdapter adapter = (ChatAdapter) lvChat.getAdapter();
                adapter.sendMessage(bean);
                lvChat.smoothScrollToPosition(adapter.getCount() - 1);

            }
        }
    }

    //电话号码格式化 去掉空格和-
    private String handleNum(String desNumber) {
        return desNumber.replace("-", "").replace(" ", "").replace("(", "").replace(")", "");

    }
}
