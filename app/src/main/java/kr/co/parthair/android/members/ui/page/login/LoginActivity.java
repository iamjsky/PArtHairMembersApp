package kr.co.parthair.android.members.ui.page.login;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.kakao.sdk.user.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.common.MyPreferenceManager;
import kr.co.parthair.android.members.model.KakaoUserSignUp;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.net.api.callback.KakaoUserLoginCallback;
import kr.co.parthair.android.members.net.api.callback.KakaoUserSignUpCallback;
import kr.co.parthair.android.members.social.kakao.KakaoGetUserInfo;
import kr.co.parthair.android.members.social.kakao.KakaoUserLogin;
import kr.co.parthair.android.members.social.kakao.KakaoUserLogout;
import kr.co.parthair.android.members.social.kakao.callback.KakaoGetUserInfoCallback;
import kr.co.parthair.android.members.social.kakao.callback.KakaoLoginCallback;
import kr.co.parthair.android.members.social.kakao.callback.KakaoLogoutCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.common.dialog.LoadingDialog;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;
import kr.co.parthair.android.members.ui.page.main.MainActivity;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.layout_phoneSignUp)
    LinearLayout layout_phoneSignUp;
    @BindView(R.id.layout_phoneLogin)
    LinearLayout layout_phoneLogin;
    @BindView(R.id.layout_kakaoLogin)
    LinearLayout layout_kakaoLogin;


    KakaoUserLogin kakaoUserLogin;
    KakaoGetUserInfo kakaoGetUserInfo;
    KakaoUserLogout kakaoUserLogout;

    LoadingDialog loadingDialog = null;

    @BindView(R.id.sv_body)
    NestedScrollView sv_body;

    @BindView(R.id.layout_topMenu)
    LinearLayout layout_topMenu;

    String _kakao_id = "";
    String _kakao_nickname = "";
    String _kakao_profile_img = "";

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


        Animation phoneSignUpAnim = AnimationUtils.loadAnimation(this, R.anim.layout_up);
        Animation phoneLoginAnim = AnimationUtils.loadAnimation(this, R.anim.layout_up);
        Animation kakaoLoginAnim = AnimationUtils.loadAnimation(this, R.anim.layout_up);
        layout_phoneSignUp.startAnimation(phoneSignUpAnim);
        phoneSignUpAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                layout_phoneSignUp.setVisibility(View.VISIBLE);
                layout_phoneLogin.setVisibility(View.INVISIBLE);
                layout_kakaoLogin.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout_phoneLogin.startAnimation(phoneLoginAnim);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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

        sv_body.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LOG_E(scrollY+"");

                if (scrollY <= 90) {
                    layout_topMenu.setVisibility(View.GONE);
                    getWindow().setStatusBarColor(getResources().getColor(R.color.ph_page_bg_color));
                } else {
                    layout_topMenu.setVisibility(View.VISIBLE);
                    getWindow().setStatusBarColor(getResources().getColor(R.color.ph_menu_tab_color_01));
                }
            }


        });

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
    public void kakaoLogin(String kakao_id, String user_nickname, String user_profile_img) {


        setLoading(true);

        _kakao_id = kakao_id+"";
        _kakao_nickname = user_nickname+"";
        _kakao_profile_img = user_profile_img+"";
        userApi.kakaoLogin(kakao_id, user_nickname, user_profile_img, kakaoUserLoginCallback);

    }


    //region onClick

    @OnClick(R.id.iv_back)
    public void iv_backClicked() {
        onBackPressed();
    }
    @OnClick(R.id.layout_phoneSignUp)
    public void layout_phoneSignUpClicked() {
        Intent intent = new Intent(this, SignUpPhoneActivity.class);
        startActivity(intent);
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

    public KakaoUserSignUpCallback kakaoSignUpCallback = new KakaoUserSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg) {

        }

        @Override
        public void onError(int code, String msg) {

        }
    };
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
           if(code == NOT_FOUND){
               Intent intent = new Intent(mContext, SignUpSocialActivity.class);
               intent.putExtra("login_type", 1);
               intent.putExtra("kakao_id", _kakao_id);
               intent.putExtra("kakao_nickname", _kakao_nickname);
               intent.putExtra("kakao_profile_img", _kakao_profile_img);
               startActivity(intent);

           }else{
               LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "카카오 계정 로그인이 실패 하였습니다.(3)");
               loginMessageDialog.show();
           }

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