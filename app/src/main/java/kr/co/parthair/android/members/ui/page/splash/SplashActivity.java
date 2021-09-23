package kr.co.parthair.android.members.ui.page.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.login.LoginActivity;

public class SplashActivity extends BaseActivity {

    Timer timer;
    int checkCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        startTimer();
    }

    public void startTimer() {
        timer = new Timer(true);
        Handler handler = new Handler();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        increaseProgress();
                    }
                });

            }
        }, 0, 10);
    }

    private void increaseProgress() {
        if (checkCount >= 100) {
            timer.cancel();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }
        checkCount++;
    }

}