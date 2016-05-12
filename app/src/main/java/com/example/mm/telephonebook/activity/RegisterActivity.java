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
 * Name:      RegisterActivity
 * Author:    Lambo
 * Function:  注册界面
 */
public class RegisterActivity extends AppCompatActivity {
    public static final String KEY_USER = "user";
    public static final String KEY_PASSWORD = "password";
    private EditText et_user;
    private EditText et_password;
    private EditText et_again;
    private Button btn_finish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_user = (EditText) findViewById(R.id.et_account);
        et_again = (EditText) findViewById(R.id.et_repeat);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = getContent(et_user);
                String password = getContent(et_password);
                String again = getContent(et_again);
                if (!password.equals(again)) return;
                Intent intent = new Intent();
                intent.putExtra(KEY_USER, user);
                intent.putExtra(KEY_PASSWORD, password);
                //对用户名密码进行保存
                saveDate(user, password);
                //参数:1.resultCode 2.intent
                setResult(110, intent);
                //关闭当前activity
                finish();

            }
        });
    }

    private String getContent(EditText et) {
        return et.getText().toString();
    }

    //android储存数据的方式
    //1.ShareadPreferences轻量级数据存储
    //2.SQLite数据库是一种轻量级的数据库

    /**
     * @param userName 用户名
     * @param password 密码
     */
    private void saveDate(String userName, String password) {
        SharedPreferences shared = getSharedPreferences("com.example.dllo.demo.INFO", MODE_PRIVATE);//1.填文件名字 2.访问权限
        //获取Editor对象 ----> 因为存储数据时 通过editor
        SharedPreferences.Editor editor = shared.edit();
        //key ---->用户名 value---->密码
        editor.putString(userName, password);
        editor.commit();
    }


}
