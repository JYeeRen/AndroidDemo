package com.example.mm.telephonebook.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.mm.telephonebook.R;

/**
 * Name:        LoginActivity
 * Author:      Lambo
 * Function:    登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_login;
    private Button btn_register;
    private EditText et_user;
    private EditText et_password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }
    /**
     *  布局控件初始化
     */
    private void initView() {
        et_password = (EditText) findViewById(R.id.et_password);
        et_user = (EditText) findViewById(R.id.et_account);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
    }

    /**
     *  点击事件
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.btn_login:
                startLogin();
                break;
            case R.id.btn_register:
                Intent toRegister=new Intent(this,RegisterActivity.class);
                startActivity(toRegister);
                break;
        }
    }


    /**
     * 执行登录功能:界面跳转到主界面
     */
    private void startLogin() {
        //获取当前输入框中用户名与密码
        String user=getContent(et_user);
        String password=getContent(et_password);
        //存储类 用来读取文件
        //参数含义: 1.用于存储的XML文件 2.文件模式
        SharedPreferences shared=getSharedPreferences("com.example.dllo.demo.INFO",MODE_PRIVATE);
        //参数含义: 1.获取字符串 2.默认值 当获取不到参数1中值时,返回默认值
        String  readPassword=shared.getString(user,null);//2.如果没有user的值,就返回默认值
        //判断用户名密码是否存在
        if(password.equals(readPassword))
        {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


    private String getContent(EditText et)
    {
        return et.getText().toString();
    }
}
