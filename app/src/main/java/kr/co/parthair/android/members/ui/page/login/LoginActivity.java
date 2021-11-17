package kr.co.parthair.android.members.ui.page.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.kakao.sdk.user.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.common.MyPreferenceManager;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.net.api.callback.KakaoUserLoginCallback;
import kr.co.parthair.android.members.social.kakao.KakaoGetUserInfo;
import kr.co.parthair.android.members.social.kakao.KakaoUserLogin;
import kr.co.parthair.android.members.social.kakao.KakaoUserLogout;
import kr.co.parthair.android.members.social.kakao.callback.KakaoGetUserInfoCallback;
import kr.co.parthair.android.members.social.kakao.callback.KakaoLoginCallback;
import kr.co.parthair.android.members.social.kakao.callback.KakaoLogoutCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;
import kr.co.parthair.android.members.ui.page.main.MainActivity;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.layout_phoneLogin)
    LinearLayout layout_phoneLogin;
    @BindView(R.id.layout_kakaoLogin)
    LinearLayout layout_kakaoLogin;
    @BindView(R.id.layout_loading)
    LinearLayout layout_loading;

    KakaoUserLogin kakaoUserLogin;
    KakaoGetUserInfo kakaoGetUserInfo;
    KakaoUserLogout kakaoUserLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        init();

    }

    void init() {
        setLoading(false);
        kakaoUserLogin = new KakaoUserLogin(mContext, kakaoLoginCallback);
        kakaoGetUserInfo = new KakaoGetUserInfo(mContext, kakaoGetUserInfoCallback);
        kakaoUserLogout = new KakaoUserLogout(mContext, kakaoLogoutCallback);

        Animation phoneLoginAnim = AnimationUtils.loadAnimation(this, R.anim.layout_up);
        Animation kakaoLoginAnim = AnimationUtils.loadAnimation(this, R.anim.layout_up);
        layout_phoneLogin.startAnimation(phoneLoginAnim);

        phoneLoginAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                layout_phoneLogin.setVisibility(View.VISIBLE);
                layout_kakaoLogin.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout_kakaoLogin.startAnimation(kakaoLoginAnim);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        kakaoLoginAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                layout_kakaoLogin.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public void setLoading(boolean value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(value){
                    layout_loading.bringToFront();
                    layout_loading.invalidate();
                    layout_loading.setVisibility(View.VISIBLE);
                }else{
                    layout_loading.setVisibility(View.GONE);
                }
            }
        });


    }
    public void kakaoLogin(String kakao_id, String user_nickname, String user_profile_img) {


        setLoading(true);


        userApi.kakaoLogin(kakao_id, user_nickname, user_profile_img, kakaoUserLoginCallback);

    }


    //region onClick

    @OnClick(R.id.iv_back)
    public void iv_backClicked() {
        onBackPressed();
    }

    @OnClick(R.id.layout_phoneLogin)
    public void layout_phoneLoginClicked() {


        Intent intent = new Intent(this, LoginPhoneActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_kakaoLogin)
    public void layout_kakaoLoginClicked() {
        setLoading(true);

        kakaoUserLogin.login();
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

            kakaoLogin(kakaoId, kakaoNickName, kakaoProfileImg);
        }

        @Override
        public void onError(@NonNull Throwable throwable) {
            setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "카카오 계정 정보가 없습니다.");
            loginMessageDialog.show();
        }
    };

    public KakaoLogoutCallback kakaoLogoutCallback = new KakaoLogoutCallback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(@NonNull Throwable throwable) {

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

    private GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            // Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();
            if (!MyInfo.instance.getUser_token().equals("")) {
                MyPreferenceManager.setString(mContext, "user_token", MyInfo.instance.getUser_token() + "");
            }

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