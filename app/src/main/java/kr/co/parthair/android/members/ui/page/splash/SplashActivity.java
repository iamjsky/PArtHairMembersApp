package kr.co.parthair.android.members.ui.page.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.common.MyPreferenceManager;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.login.LoginActivity;
import kr.co.parthair.android.members.ui.page.main.MainActivity;
import kr.co.parthair.android.members.ui.page.splash.dialog.ResponseErrorDialog;

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
            String user_token = MyPreferenceManager.getString(this,"user_token")+"";
            LOG_E("user_token>>"+user_token);
            if(!user_token.equals("")){
                MyInfo.instance.setUser_token(user_token);
                userApi.getUserInfo(getUserInfoCallback);
            }else{
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }


        }
        checkCount++;
    }

    //region callback

    GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            MyPreferenceManager.setString(mContext, "user_token", MyInfo.instance.getUser_token()+"");
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }

        @Override
        public void onError(int code, String msg) {
            String errMsg = msg;
            if(code == SERVER_ERROR){
                errMsg = "네트워크가 불안정 합니다. 인터넷 연결 상태를 확인해 주세요";
            }
            ResponseErrorDialog responseErrorDialog = new ResponseErrorDialog(mContext, "알림", errMsg);
            responseErrorDialog.show();


        }
    };

    //endregeion

}