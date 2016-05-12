package com.example.mm.telephonebook.fragmennt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mm.telephonebook.R;

/**
 * Name:        CallFragment
 * Author:      Lambo
 * Function:    电话拨号界面
 */
public class CallFragment extends Fragment implements View.OnClickListener {
    private TextView tv_call;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    private ImageButton btn_call;
    private ImageButton btn_delete;
    private String phoneNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, null);
        initView(view);
        initDate();
        return view;
    }

    /**
     * 初始化监听
     */
    private void initDate() {
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    /**
     *  初始化窗口
     */
    private void initView(View view) {
        tv_call = (TextView) view.findViewById(R.id.tv_call);
        btn0 = (Button) view.findViewById(R.id.btn_call_zero);
        btn1 = (Button) view.findViewById(R.id.btn_call_one);
        btn2 = (Button) view.findViewById(R.id.btn_call_two);
        btn3 = (Button) view.findViewById(R.id.btn_call_three);
        btn4 = (Button) view.findViewById(R.id.btn_call_four);
        btn5 = (Button) view.findViewById(R.id.btn_call_five);
        btn6 = (Button) view.findViewById(R.id.btn_call_six);
        btn7 = (Button) view.findViewById(R.id.btn_call_seven);
        btn8 = (Button) view.findViewById(R.id.btn_call_eight);
        btn9 = (Button) view.findViewById(R.id.btn_call_nine);
        btn_call = (ImageButton) view.findViewById(R.id.btn_call_phone);
        btn_delete = (ImageButton) view.findViewById(R.id.btn_call_delete);
    }

    /**
     *
     *  显示电话号码
     */
    @Override
    public void onClick(View view) {
        phoneNumber = tv_call.getText().toString();

        switch (view.getId()) {
            case R.id.btn_call_zero:
                phoneNumber += 0;
                break;
            case R.id.btn_call_one:
                phoneNumber += 1;
                break;
            case R.id.btn_call_two:
                phoneNumber += 2;
                break;
            case R.id.btn_call_three:
                phoneNumber += 3;
                break;
            case R.id.btn_call_four:
                phoneNumber += 4;
                break;
            case R.id.btn_call_five:
                phoneNumber += 5;
                break;
            case R.id.btn_call_six:
                phoneNumber += 6;
                break;
            case R.id.btn_call_seven:
                phoneNumber += 7;
                break;
            case R.id.btn_call_eight:
                phoneNumber += 8;
                break;
            case R.id.btn_call_nine:
                phoneNumber += 9;
                break;
            case R.id.btn_call_phone:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
                break;
            case R.id.btn_call_delete:
                if (phoneNumber.length() > 0) {
                    phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                }
                break;
        }
        tv_call.setText(phoneNumber);
    }
}