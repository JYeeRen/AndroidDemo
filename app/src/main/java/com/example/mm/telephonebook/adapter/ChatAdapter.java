package com.example.mm.telephonebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mm.telephonebook.R;
import com.example.mm.telephonebook.beans.SmsBean;

import java.util.List;

/**
 * Name:        ChatAdapter
 * Author:      Lambo
 * Function:    短信界面适配器
 */
public class ChatAdapter extends BaseAdapter {
    private static final int TYPE_SEND = 0;
    private static final int TYPE_RECEIVE = 1;
    private List<SmsBean> smsList;
    private LayoutInflater inflater;

    public ChatAdapter(Context context, List<SmsBean> smsList) {
        this.smsList = smsList;
        this.inflater = LayoutInflater.from(context);
    }
    //
    public void sendMessage(SmsBean bean)
    {
        smsList.add(bean);
        //通知适配器界面更新显示
        notifyDataSetChanged();
    }

    //根据position确定当前行布局的类型
    @Override
    public int getItemViewType(int position) {
        SmsBean sms = smsList.get(position);
        if (sms.getType().equals("1")) {
            return TYPE_RECEIVE;
        } else {
            return TYPE_SEND;
        }

    }

    //行布局类型的总数 发送,接收2方
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return smsList.size();
    }

    @Override
    public Object getItem(int position) {
        return smsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //根据position确定当前行布局类型
        int viewType = getItemViewType(position);
        SendHolder sendHolder = null;
        ReceiveHolder receiveHolder = null;
        if (convertView == null) {
            switch (viewType) {
                case TYPE_SEND:
                    convertView = inflater.inflate(R.layout.item_chat_send, null);
                    sendHolder = new SendHolder(convertView);
                    convertView.setTag(sendHolder);
                    break;
                case TYPE_RECEIVE:
                    convertView = inflater.inflate(R.layout.item_chat_receive, null);
                    receiveHolder = new ReceiveHolder(convertView);
                    convertView.setTag(receiveHolder);
                    break;
            }
        } else {
            switch (viewType) {
                case TYPE_SEND:
                    sendHolder = (SendHolder) convertView.getTag();
                    break;
                case TYPE_RECEIVE:
                    receiveHolder = (ReceiveHolder) convertView.getTag();
                    break;
            }
        }
        SmsBean sms = smsList.get(position);//获取对应行布局数据
        String body = sms.getBody();
        String date = sms.getDate();
        switch (viewType) {
            case TYPE_SEND:
                sendHolder.tvBody.setText(body);
                sendHolder.tvDate.setText(date);
                break;
            case TYPE_RECEIVE:
                receiveHolder.tvBody.setText(body);
                receiveHolder.tvDate.setText(date);
                break;
        }

        return convertView;
    }

    //两个布局就要创建两个viewholder
    private class SendHolder {
        private TextView tvBody;
        private TextView tvDate;

        public SendHolder(View view) {
            tvBody = (TextView) view.findViewById(R.id.tv_body);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
        }
    }

    private class ReceiveHolder {
        private TextView tvBody;
        private TextView tvDate;

        public ReceiveHolder(View view) {
            tvBody = (TextView) view.findViewById(R.id.tv_body);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
        }
    }
}
