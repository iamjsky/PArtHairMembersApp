package kr.co.parthair.android.members.ui.page.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kakao.sdk.user.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.common.MyPreferenceManager;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.net.api.callback.KakaoUserLoginCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneLoginCallback;
import kr.co.parthair.android.members.social.kakao.KakaoGetUserInfo;
import kr.co.parthair.android.members.social.kakao.KakaoUserLogin;
import kr.co.parthair.android.members.social.kakao.callback.KakaoGetUserInfoCallback;
import kr.co.parthair.android.members.social.kakao.callback.KakaoLoginCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.common.dialog.LoadingDialog;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;
import kr.co.parthair.android.members.ui.page.main.MainActivity;

public class SignUpFinishActivity extends BaseActivity {

    LoadingDialog loadingDialog = null;

    @BindView(R.id.layout_signUpFinish)
    RelativeLayout layout_signUpFinish;

    @BindView(R.id.iv_userProfileImg)
    ImageView iv_userProfileImg;

    @BindView(R.id.tv_userName)
    TextView tv_userName;

    @BindView(R.id.tv_title_01)
    TextView tv_title_01;
    @BindView(R.id.tv_title_02)
    TextView tv_title_02;
    @BindView(R.id.tv_desc)
    TextView tv_desc;


    KakaoUserLogin kakaoUserLogin;
    KakaoGetUserInfo kakaoGetUserInfo;

    int login_type = -1;
    String user_phone = "";
    String phone_login_pw = "";
    String user_name = "";
    String user_profile_img = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_finish);
        ButterKnife.bind(this);


        if(getIntent() != null){
            Intent intent = new Intent();
            intent = getIntent();
            login_type = intent.getIntExtra("login_type", LOGIN_TYPE_PHONE);
            user_name = intent.getStringExtra("user_name") + "";
            tv_userName.setText(user_name+"");
            if(login_type == LOGIN_TYPE_PHONE){
                user_phone = intent.getStringExtra("user_phone");
                phone_login_pw = intent.getStringExtra("phone_login_pw");
                layout_signUpFinish.setBackgroundColor(getColor(R.color.ph_page_bg_color));
                tv_title_01.setText("피아트헤어");
                tv_title_02.setText("회원가입 완료");
                tv_desc.setText("이제 가입한 고객 정보로 로그인을 할 수 있습니다 :D");
            }else if(login_type == LOGIN_TYPE_KAKAO){
                user_profile_img = intent.getStringExtra("user_profile_img");
                layout_signUpFinish.setBackgroundColor(getColor(R.color.kakao));
                tv_title_01.setText("카카오 간편 로그인");
                tv_title_02.setText("설정 완료");
                tv_desc.setText("이제 카카오 계정으로 간편 로그인을 할 수 있습니다 :D");
            }

        }


        init();

    }
    @Override
    public void onBackPressed() {
        MyPreferenceManager.setString(mContext, "user_token", "");

        setLoading(false);
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    void init(){
        Window w = getWindow();
        if(login_type == LOGIN_TYPE_PHONE){
            w.setStatusBarColor(getResources().getColor(R.color.ph_page_bg_color));
        }else if(login_type == LOGIN_TYPE_KAKAO){
            w.setStatusBarColor(getResources().getColor(R.color.kakao));
        }
        kakaoUserLogin = new KakaoUserLogin(mContext, kakaoLoginCallback);
        kakaoGetUserInfo = new KakaoGetUserInfo(mContext, kakaoGetUserInfoCallback);

        iv_userProfileImg.setBackground(new ShapeDrawable(new OvalShape()));
        iv_userProfileImg.setClipToOutline(true);

        if (!user_profile_img.equals("")) {
            Glide.with(mContext).load(user_profile_img
                    ).error(R.color.ph_main_color)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .override(100, 100)
                    .skipMemoryCache(true)
                    .into(iv_userProfileImg);
        } else {
            Glide.with(mContext).load(R.color.ph_main_color)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .override(100, 100)
                    .skipMemoryCache(true)
                    .into(iv_userProfileImg);
        }



    }
    public void setLoading(boolean value){
        if(loadingDialog == null){
            loadingDialog = new LoadingDialog(this);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (value) {

                    loadingDialog.show();
                } else {
                    if(loadingDialog != null){
                        loadingDialog.dismiss();
                    }

                }
            }
        });


    }
    //region onClick

    @OnClick(R.id.btn_goLogin)
    public void btn_goLoginClicked(){
        if(login_type == LOGIN_TYPE_PHONE){
            setLoading(true);
            userApi.phoneLogin(user_phone, phone_login_pw, phoneLoginCallback);
        }else if(login_type == LOGIN_TYPE_KAKAO){
            setLoading(true);

            kakaoUserLogin.login();
        }
    }

    //endregion

    //region callback

    public KakaoLoginCallback kakaoLoginCallback = new KakaoLoginCallback() {
        @Override
        public void onSuccess(String kakaoUserToken) {
            if (kakaoUserToken != null) {
                kakaoGetUserInfo.getUserInfo();
            } else {
                setLoading(false);

                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "카카오 계정 로그인이 실패 하였습니다.(1)");
                loginMessageDialog.show();
            }

        }

        @Override
        public void onError(@NonNull Throwable throwable) {
            LOG_E("kakaoLoginCallback : " + throwable.toString());
            setLoading(false);

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "카카오 계정 로그인이 실패 하였습니다.(2)");
            loginMessageDialog.show();
        }
    };

    public KakaoGetUserInfoCallback kakaoGetUserInfoCallback = new KakaoGetUserInfoCallback() {
        @Override
        public void onSuccess(User user) {

            String kakaoId = "";
            String kakaoNickName = "";
            String kakaoProfileImg = "";
            String kakaoEmail = "";

            if (user.getId() != 0 && user.getId() != -1) {
                kakaoId = user.getId() + "";
            } else {
                kakaoId = "";
            }

            if (user.getKakaoAccount().getProfile().getNickname() != null) {
                kakaoNickName = user.getKakaoAccount().getProfile().getNickname() + "";
            } else {
                kakaoNickName = "이름없음";
            }


            if (user.getKakaoAccount().getProfile().getThumbnailImageUrl() != null) {
                kakaoProfileImg = user.getKakaoAccount().getProfile().getThumbnailImageUrl() + "";
            } else {
                kakaoProfileImg = "";
            }

            if (user.getKakaoAccount().getEmail() != null &&
                    user.getKakaoAccount().getEmail().contains("@")) {
                kakaoEmail = user.getKakaoAccount().getEmail() + "";
            } else {
                kakaoEmail = "";
            }


//            userApi.socialLogin(
//                    1,
//                    kakaoNickName,
//                    kakaoEmail,
//                    kakaoId,
//                    kakaoProfileImg,
//                    loginCallback);

//            Toast.makeText(mParent, "카카오 계정 로그인 준비 완료>>"
//                    + kakaoId + ","
//                    + kakaoNickName + ","
//                    + kakaoProfileImg + ","
//                    + kakaoEmail , Toast.LENGTH_LONG).show();
            userApi.kakaoLogin(kakaoId, kakaoNickName, kakaoProfileImg, kakaoUserLoginCallback);
        }

        @Override
        public void onError(@NonNull Throwable throwable) {
            setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "카카오 계정 정보가 없습니다.");
            loginMessageDialog.show();
        }
    };

    public KakaoUserLoginCallback kakaoUserLoginCallback = new KakaoUserLoginCallback() {
        @Override
        public void onSuccess(int code, String msg) {

            userApi.getUserInfo(getUserInfoCallback);
        }

        @Override
        public void onError(int code, String msg) {
            setLoading(false);

                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "카카오 계정 로그인이 실패 하였습니다.(3)");
                loginMessageDialog.show();


        }
    };
    private PhoneLoginCallback phoneLoginCallback = new PhoneLoginCallback() {
        @Override
        public void onSuccess(int code, String msg) {

            userApi.getUserInfo(getUserInfoCallback);
        }

        @Override
        public void onError(int code, String msg) {
            setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", msg);
            loginMessageDialog.show();

        }
    };
    private GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            // Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();

                MyPreferenceManager.setString(mContext, "user_token", MyInfo.instance.getUser_token() + "");


            setLoading(false);
            LOG_I(MyInfo.instance.getUserInfo().toString());
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        @Override
        public void onError(int code, String msg) {
            setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", msg);
            loginMessageDialog.show();
        }
    };

    //endregion
}