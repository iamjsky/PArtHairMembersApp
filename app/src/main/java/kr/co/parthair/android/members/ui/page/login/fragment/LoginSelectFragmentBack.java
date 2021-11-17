package kr.co.parthair.android.members.ui.page.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import kr.co.parthair.android.members.social.kakao.KakaoUserLogout;
import kr.co.parthair.android.members.social.kakao.callback.KakaoGetUserInfoCallback;
import kr.co.parthair.android.members.social.kakao.callback.KakaoLoginCallback;
import kr.co.parthair.android.members.social.kakao.callback.KakaoLogoutCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseFragment;
import kr.co.parthair.android.members.ui.page.login.LoginActivityBack;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;
import kr.co.parthair.android.members.ui.page.main.MainActivity;
import kr.co.parthair.android.members.ui.widget.numpad.NumPadView;

/**
 * ClassName            LoginSelectFragment
 * Created by JSky on   2021-09-28
 * <p>
 * Description
 */
public class LoginSelectFragmentBack extends BaseFragment {


    KakaoUserLogin kakaoUserLogin;
    KakaoGetUserInfo kakaoGetUserInfo;
    KakaoUserLogout kakaoUserLogout;






    //region NUMPAD

    private String userPhoneId = "";
    private String userPhoneIdPw = "";
    //private String userPhoneIdPwChk = "";

    @BindView(R.id.view_numPad)
    NumPadView view_numPad;


    NumPadView.NumPadFinishOnClickListener numPadPhoneNumberFinishOnClickListener = new NumPadView.NumPadFinishOnClickListener() {
        @Override
        public void onClick(View view, String data) {
          //  Toast.makeText(mParent, data, Toast.LENGTH_SHORT).show();
            userPhoneId = data;
            userPhoneIdPw = "";
            view_numPad.setVisible(NUMPAD_PHONE_LOGIN_PASSWORD, numPadPasswordFinishOnClickListener);
        }

        @Override
        public void onCancel(String type) {

        }
    };

    NumPadView.NumPadFinishOnClickListener numPadPasswordFinishOnClickListener = new NumPadView.NumPadFinishOnClickListener() {
        @Override
        public void onClick(View view, String data) {
            userPhoneIdPw = data;
          //  Toast.makeText(mParent, data, Toast.LENGTH_SHORT).show();
            phoneLogin(userPhoneId, userPhoneIdPw);
        }
        @Override
        public void onCancel(String type) {
            userPhoneId = "";
            userPhoneIdPw = "";
            view_numPad.setVisible(NUMPAD_PHONE_lOGIN_PHONE, numPadPhoneNumberFinishOnClickListener);
        }
    };
//    NumPadView.NumPadFinishOnClickListener numPadPasswordCheckFinishOnClickListener = new NumPadView.NumPadFinishOnClickListener() {
//        @Override
//        public void onClick(View view, String data) {
//            Toast.makeText(mParent, data, Toast.LENGTH_SHORT).show();
//        }
//    };




    //endregion

    public LoginSelectFragmentBack() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_select, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }


    void init(){

        kakaoUserLogin = new KakaoUserLogin(mParent, kakaoLoginCallback);
        kakaoGetUserInfo = new KakaoGetUserInfo(mParent, kakaoGetUserInfoCallback);
        kakaoUserLogout = new KakaoUserLogout(mParent, kakaoLogoutCallback);
    }


    public void phoneLogin(String userPhoneId, String userPhoneIdPw) {


        ((LoginActivityBack)mParent).setLoading(true);


        userApi.phoneLogin(userPhoneId, userPhoneIdPw, phoneLoginCallback);

    }

    public void kakaoLogin(String kakao_id, String user_nickname, String user_profile_img) {


        ((LoginActivityBack)mParent).setLoading(true);


        userApi.kakaoLogin(kakao_id, user_nickname, user_profile_img, kakaoUserLoginCallback);

    }

    //region onClick

    @OnClick(R.id.iv_back)
    public void iv_backClicked(){

        ((LoginActivityBack)mParent).onBackPressed();

    }

    @OnClick(R.id.btn_phoneLogin)
    public void btn_phoneLoginClicked(){

        userPhoneId = "";
        userPhoneIdPw = "";
        view_numPad.setVisible(NUMPAD_PHONE_lOGIN_PHONE, numPadPhoneNumberFinishOnClickListener);

    }

    @OnClick(R.id.btn_kakaoLogin)
    public void btn_kakaoLoginClicked(){

        userPhoneId = "";
        userPhoneIdPw = "";
        ((LoginActivityBack)mParent).setLoading(true);
        kakaoUserLogin.login();

    }
    //endregion



    //region callback

    private PhoneLoginCallback phoneLoginCallback = new PhoneLoginCallback() {
        @Override
        public void onSuccess(int code, String msg) {

            userApi.getUserInfo(getUserInfoCallback);
        }

        @Override
        public void onError(int code, String msg) {
            ((LoginActivityBack)mParent).setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", msg);
            loginMessageDialog.show();

        }
    };

    private GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
        @Override
        public void onSuccess(int code, String msg) {
           // Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();
            if(!MyInfo.instance.getUser_token().equals("")){
                MyPreferenceManager.setString(mParent, "user_token", MyInfo.instance.getUser_token()+"");
            }

            ((LoginActivityBack)mParent).setLoading(false);
            LOG_I(MyInfo.instance.getUserInfo().toString());
            Intent intent = new Intent(mParent, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mParent.startActivity(intent);
        }

        @Override
        public void onError(int code, String msg) {
            ((LoginActivityBack)mParent).setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", msg);
            loginMessageDialog.show();
        }
    };



    public KakaoLoginCallback kakaoLoginCallback = new KakaoLoginCallback() {
        @Override
        public void onSuccess(String kakaoUserToken) {
            if (kakaoUserToken != null) {
                kakaoGetUserInfo.getUserInfo();
            }else{
                ((LoginActivityBack)mParent).setLoading(false);
                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "카카오 계정 로그인이 실패 하였습니다.(1)");
                loginMessageDialog.show();
            }

        }

        @Override
        public void onError(@NonNull Throwable throwable) {
            LOG_E("kakaoLoginCallback : " + throwable.toString());
            ((LoginActivityBack)mParent).setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "카카오 계정 로그인이 실패 하였습니다.(2)");
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
            ((LoginActivityBack)mParent).setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "카카오 계정 정보가 없습니다.");
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
            ((LoginActivityBack)mParent).setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "카카오 계정 로그인이 실패 하였습니다.(3)");
            loginMessageDialog.show();
        }
    };

    //endregion



}
