package kr.co.parthair.android.members.ui.page.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.common.MyPreferenceManager;
import kr.co.parthair.android.members.model.TagListModel;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneLoginCallback;
import kr.co.parthair.android.members.net.api.callback.TagListCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;
import kr.co.parthair.android.members.ui.page.main.MainActivity;
import kr.co.parthair.android.members.ui.page.splash.dialog.ResponseErrorDialog;

public class SplashActivity extends BaseActivity {

    Timer timer;
    int checkCount = 0;
    @BindView(R.id.layout_checkPermission)
    LinearLayout layout_checkPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        checkPermission();

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
            String user_token = MyPreferenceManager.getString(this, "user_token") + "";
            LOG_E("user_token>>" + user_token);
            LOG_E("user_token>>" + MyInfo.instance.getUser_token());
            if (!user_token.equals("")) {
                MyInfo.instance.setUser_token(user_token);
                userApi.getUserInfo(getUserInfoCallback);
            } else {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }


        }
        checkCount++;
    }

    void checkPermission() {

        int CALL_PHONE = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int READ_PHONE_NUMBER;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            READ_PHONE_NUMBER = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS);
        }else{
            READ_PHONE_NUMBER = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        }


        if (CALL_PHONE == PackageManager.PERMISSION_DENIED || READ_PHONE_NUMBER == PackageManager.PERMISSION_DENIED
        ) {

            layout_checkPermission.setVisibility(View.VISIBLE);


        } else {
            layout_checkPermission.setVisibility(View.GONE);
            etcApi.getTagList(tagListCallback);
        }


    }

    //region callback

    GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            String user_phone = MyInfo.instance.getUserInfo().getUserPhone() + "";
            String phone_login_pw = MyInfo.instance.getUserInfo().getPhoneLoginPw() + "";
            userApi.phoneLogin(user_phone, phone_login_pw, phoneLoginCallback);


        }

        @Override
        public void onError(int code, String msg) {
            String errMsg = msg;
            if (code == SERVER_ERROR) {
                errMsg = "네트워크가 불안정 합니다. 인터넷 연결 상태를 확인해 주세요";
                ResponseErrorDialog responseErrorDialog = new ResponseErrorDialog(mContext, "알림", errMsg);
                responseErrorDialog.show();
            }else if(code == NOT_FOUND){
                MyPreferenceManager.setString(mContext, "user_token", "");
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }



        }
    };
    public TagListCallback tagListCallback = new TagListCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable List<TagListModel.TagInfo> data) {
            LOG_I("TagListModel.instance.getTagInfoList().size() : " + data.size());
            TagListModel.instance.setTagInfoList(data);
            LOG_I("TagListModel.instance.getTagInfoList().size() : " + TagListModel.instance.getTagInfoList().size());
            startTimer();
        }

        @Override
        public void onError(int code, String msg) {
            String errMsg = msg;
            if (code == SERVER_ERROR) {
                errMsg = "네트워크가 불안정 합니다. 인터넷 연결 상태를 확인해 주세요";
                ResponseErrorDialog responseErrorDialog = new ResponseErrorDialog(mContext, "알림", errMsg);
                responseErrorDialog.show();
            }else{
                startTimer();
            }

        }
    };

    private PhoneLoginCallback phoneLoginCallback = new PhoneLoginCallback() {
        @Override
        public void onSuccess(int code, String msg) {

            MyPreferenceManager.setString(mContext, "user_token", MyInfo.instance.getUser_token() + "");
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }

        @Override
        public void onError(int code, String msg) {
            setLoading(false);
            MyPreferenceManager.setString(mContext, "user_token", "");
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }
    };

    //endregion


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            int CALL_PHONE = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            int READ_PHONE_NUMBER;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                READ_PHONE_NUMBER = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS);
            }else{
                READ_PHONE_NUMBER = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            }

            if (CALL_PHONE == PackageManager.PERMISSION_DENIED || READ_PHONE_NUMBER == PackageManager.PERMISSION_DENIED
            ) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                intent.setData(uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1000);

            } else {
                layout_checkPermission.setVisibility(View.GONE);
                etcApi.getTagList(tagListCallback);
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            int CALL_PHONE = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            int READ_PHONE_NUMBER;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                READ_PHONE_NUMBER = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS);
            }else{
                READ_PHONE_NUMBER = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            }

            if (CALL_PHONE == PackageManager.PERMISSION_DENIED || READ_PHONE_NUMBER == PackageManager.PERMISSION_DENIED
            ) {
                layout_checkPermission.setVisibility(View.VISIBLE);

            } else {
                layout_checkPermission.setVisibility(View.GONE);
                etcApi.getTagList(tagListCallback);
            }

        }

    }

    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_NUMBERS}, 1000);
        }else{
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE}, 1000);
        }
    }
}
