package com.example.mm.telephonebook.fragmennt;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mm.telephonebook.R;
import com.example.mm.telephonebook.activity.ChatActivity;
import com.example.mm.telephonebook.adapter.ChatAdapter;
import com.example.mm.telephonebook.adapter.SmsAdapter;
import com.example.mm.telephonebook.beans.SmsBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Name:  SmsFragment
 * Author:  Lambo
 * Function: 短信
 */
public class SmsFragment extends Fragment {
    private ListView listView;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //** 接收从子线程发送过来的数据 msg是下面发送过来的message obj就是集合 *主线程代码
            List<SmsBean> smsList = (List<SmsBean>) msg.obj;
            //初始化 适配器
            SmsAdapter adapter = new SmsAdapter(getActivity(), smsList);
            listView.setAdapter(adapter);
//            ChatAdapter adapter = new ChatAdapter(getActivity(), smsList);//新换版
//            listView.setAdapter(adapter);

            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forthfragment, null);
        return view;
    }
    //主线程中进行耗时的操作,会阻塞UI(导致卡顿)
    //Android中 只有主线程才能更改UI,在子线程读取数据但是不能更改UI(把数据显示到页面)
    //在主线程可以更改UI但是不能读取数据
    //所以需要进行线程间的通信-->


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //为listview添加行布局点击事件
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position代表当前点击的item位置
                //parent---> 当前的listview
                //1.获取到当前listview对应的adapter
                Adapter adapter = parent.getAdapter();//就是chatAdapter
                //2.通过Adapter的getItem()方法,获取到对应位置的item数据
                SmsBean bean = (SmsBean) adapter.getItem(position);
                //3.实体类集成Serializable接口,序列化——>才可以传递
                Intent intent = new Intent(getActivity(), ChatActivity.class);//实现页面跳转,Intent构造方法 1.当前acitivity2.目标acitivity
                //把需要传递的值传递到Intent中
                intent.putExtra("SmsBean", bean);//1.key2.values
                //页面跳转
//                startActivity(intent);换一种跳转
                //待返回值的跳转
                startActivityForResult(intent,101);// 1.Intent 2.RequestCode
                Toast.makeText(getActivity(), bean.getBody(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //回调方法 通过startActivityForResult()方法跳转的页面 关闭后会执行这个方法

    /**
     *
     * @param requestCode 用来区分结果由哪个位置来处理
     * @param resultCode
     * @param data 第二个activity传递回来的值
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            readMessage();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //开启线程有两种方法,集成Thread类或者实现Runnable接口
        readMessage();
        //这里可以更改UI
    }
    //读取信息
    private void readMessage() {
        ReadSmsable readable = new ReadSmsable();
        Thread thread = new Thread(readable);
        thread.start();
    }

    //实现Runnable接口,处理线程
    private class ReadSmsable implements Runnable {

        @Override
        public void run() {
            //此部分代码,执行在子线程中
            //在这部分是读取短信 contentresolver
            ContentResolver resolver = getActivity().getContentResolver();

            //游标
            Cursor cursor = resolver.query(Telephony.Threads.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int typeIndex = cursor.getColumnIndex(Telephony.Sms.TYPE);
                int dateIndex = cursor.getColumnIndex(Telephony.Sms.DATE);
                int addressIndex = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
                int bodyIndex = cursor.getColumnIndex(Telephony.Sms.BODY);
                int readIndex = cursor.getColumnIndex(Telephony.Sms.READ);
                int threadIdIndex = cursor.getColumnIndex(Telephony.Sms.THREAD_ID);//用来查询用户
                List<SmsBean> smsList = new ArrayList<>();
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
//                SmsAdapter adapter=new SmsAdapter(getActivity(),smsList);
//                listView.setAdapter(adapter);
                //线程间通信用handler  把集合发送到主线程需要用message
                Message message = new Message();
                //把集合赋值给Message的obj属性
                message.obj = smsList;
                //通过Handler把Message发送到主线程
                handler.sendMessage(message);


            }
        }
    }
}