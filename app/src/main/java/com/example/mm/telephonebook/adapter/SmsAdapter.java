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
 * Name:  CallFragment
 * Author:  Lambo
 * Function:
 */
public class SmsAdapter extends BaseAdapter {
    private List<SmsBean> smsList;
    private LayoutInflater inflater;

    public SmsAdapter(Context context, List<SmsBean> smsList) {
        this.inflater = LayoutInflater.from(context);
        this.smsList = smsList;
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_sms, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SmsBean bean = smsList.get(position);
        holder.tvAddress.setText(bean.getAddress());
        holder.tvBody.setText(bean.getBody());
        holder.tvDate.setText(bean.getDate());

        return convertView;
    }

    private class ViewHolder {
        private TextView tvDate;
        private TextView tvBody;
        private TextView tvAddress;

        public ViewHolder(View view) {
            tvAddress = (TextView) view.findViewById(R.id.tv_address);
            tvBody = (TextView) view.findViewById(R.id.tv_body);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
        }
    }
}
