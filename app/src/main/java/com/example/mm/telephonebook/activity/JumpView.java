package com.example.mm.telephonebook.activity;



import android.app.Activity;
import java.util.Timer;
import java.util.TimerTask;
import com.example.mm.telephonebook.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;


public class JumpView extends Activity {
    Intent intent;
    Timer time;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.jump_layout);

        intent = new Intent();
        time = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                intent.setClass(JumpView.this, LoginActivity.class);
                startActivity(intent);
            }
        };

        time.schedule(timerTask, 3000 * 1);
    }

}
