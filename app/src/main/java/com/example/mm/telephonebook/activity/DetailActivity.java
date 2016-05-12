package com.example.mm.telephonebook.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.mm.telephonebook.R;
import com.example.mm.telephonebook.beans.ContactNumber;

/**
 * Name:        DetailActivity
 * Author:      Lambo
 * Function:    联系人详情界面
 */

public class DetailActivity extends AppCompatActivity {
    private TextView tv_name;
    private TextView tv_phone;
    private Button btn_call;
    private Button btn_sms;

    private ContactNumber bean;
    private String name;
    private String phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);
        initView();
        initDate();
        tv_phone.setText(phone);
        tv_name.setText(name);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intentCall);
            }
        });
        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSms=new Intent(DetailActivity.this,ChatActivity.class);
                intentSms.putExtra("phone",phone);
                startActivity(intentSms);
            }
        });
    }

    private void initDate() {
        Intent intent=getIntent();
        bean= (ContactNumber) intent.getSerializableExtra("People");
        phone=bean.getContactnumber();
        name=bean.getName();

    }

    private void initView() {
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_phone= (TextView) findViewById(R.id.tv_number);
        btn_call= (Button) findViewById(R.id.btn_call);
        btn_sms= (Button) findViewById(R.id.btn_sms);
    }


}
