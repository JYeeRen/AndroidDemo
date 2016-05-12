package com.example.mm.telephonebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mm.telephonebook.R;
import com.example.mm.telephonebook.beans.ContactNumber;

import java.util.ArrayList;

/**
 * Name:        ContactAdapter
 * Author:      Lambo
 * Function:    联系人适配器
 */
public class ContactAdapter extends BaseAdapter {
    private ArrayList<ContactNumber> contacts;
    private LayoutInflater inflater;

    public ContactAdapter(Context context, ArrayList<ContactNumber> contacts) {
        this.contacts = contacts;
        //初始化布局注入器,作用 把布局文件转化成View
        inflater = LayoutInflater.from(context);
    }

    //决定ListView显示的条数
    @Override
    public int getCount() {
        return contacts.size();
    }

    //
    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //决定ListView的每一行显示的样式和显示的内容 差不多公式一样
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_contact, null); //convertView含有view内字段
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }
        //取出对应位置的联系人信息
        ContactNumber bean = contacts.get(position);
        holder.tvName.setText(bean.getName());
        holder.tvNumber.setText(bean.getContactnumber());
        return convertView;
    }

    private class ViewHolder {
        private TextView tvName;
        private TextView tvNumber;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvNumber = (TextView) view.findViewById(R.id.tv_number);

        }

    }
}
