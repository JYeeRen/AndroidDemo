package com.example.mm.telephonebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mm.telephonebook.R;
import com.example.mm.telephonebook.beans.CallRecords;

import java.util.ArrayList;

/**
 * Name:  RecordAdapter
 * Author:  Lambo
 * Function:
 */
public class RecordAdapter extends BaseAdapter {
    private ArrayList<CallRecords> recordList;
    private LayoutInflater inflater;

    public RecordAdapter(Context context, ArrayList<CallRecords> recordses) {
        this.recordList = recordses;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_record, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CallRecords bean =recordList.get(position);
        viewHolder.tvName.setText(bean.getName());
        viewHolder.tvAddress.setText(bean.getAddress());
        viewHolder.tvType.setText(bean.getType());
        viewHolder.tvDate.setText(bean.getDate());

        return convertView;
    }

    private class ViewHolder {
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvType;
        private TextView tvDate;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvAddress = (TextView) view.findViewById(R.id.tv_address);
            tvDate = (TextView) view.findViewById(R.id.tv_time);
            tvType = (TextView) view.findViewById(R.id.tv_type);
        }
    }
}
