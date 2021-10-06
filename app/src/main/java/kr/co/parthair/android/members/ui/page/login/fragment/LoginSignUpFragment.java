package kr.co.parthair.android.members.ui.page.login.fragment;

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
import kr.co.parthair.android.members.common.callback.AgreeCallback;
import kr.co.parthair.android.members.social.kakao.KakaoGetUserInfo;
import kr.co.parthair.android.members.social.kakao.KakaoUserLogin;
import kr.co.parthair.android.members.social.kakao.KakaoUserLogout;
import kr.co.parthair.android.members.social.kakao.callback.KakaoGetUserInfoCallback;
import kr.co.parthair.android.members.social.kakao.callback.KakaoLoginCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseFragment;
import kr.co.parthair.android.members.ui.page.login.LoginActivity;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;
import kr.co.parthair.android.members.ui.widget.numpad.NumPadView;

/**
 * ClassName            LoginSignUpFragment
 * Created by JSky on   2021-09-28
 * <p>
 * Description
 */
public class LoginSignUpFragment extends BaseFragment {

        KakaoUserLogin kakaoUserLogin;
    KakaoGetUserInfo kakaoGetUserInfo;
    KakaoUserLogout kakaoUserLogout;


    //region NUMPAD

    private String userPhoneId = "";
    private String userPhoneIdPw = "";
    private String userPhoneIdPwChk = "";

    @BindView(R.id.view_numPad)
    NumPadView view_numPad;

    private int signUp_type = -1;


    NumPadView.NumPadFinishOnClickListener numPadPhoneNumberFinishOnClickListener = new NumPadView.NumPadFinishOnClickListener() {
        @Override
        public void onClick(View view, String data) {
         //   Toast.makeText(mParent, data, Toast.LENGTH_SHORT).show();
            userPhoneId = data;
            userPhoneIdPw = "";
            userPhoneIdPwChk = "";
            view_numPad.setVisible(NUMPAD_PHONE_SIGNUP_PASSWORD, numPadPasswordFinishOnClickListener);
        }

        @Override
        public void onCancel(String type) {
            userPhoneId = "";
            userPhoneIdPw = "";
            userPhoneIdPwChk = "";
        }
    };

    NumPadView.NumPadFinishOnClickListener numPadPasswordFinishOnClickListener = new NumPadView.NumPadFinishOnClickListener() {
        @Override
        public void onClick(View view, String data) {
            userPhoneIdPw = data;
            //  Toast.makeText(mParent, data, Toast.LENGTH_SHORT).show();
            view_numPad.setVisiblePwChk(userPhoneId, userPhoneIdPw, numPadPasswordCheckFinishOnClickListener);
        }
        @Override
        public void onCancel(String type) {
            userPhoneId = "";
            userPhoneIdPw = "";
            userPhoneIdPwChk = "";
            view_numPad.setVisible(NUMPAD_PHONE_SIGNUP_PHONE, numPadPhoneNumberFinishOnClickListener);
        }
    };
    NumPadView.NumPadFinishOnClickListener numPadPasswordCheckFinishOnClickListener = new NumPadView.NumPadFinishOnClickListener() {
        @Override
        public void onClick(View view, String data) {
            userPhoneIdPwChk = data;

            phoneSignUpInfoMove();
            //Toast.makeText(mParent, userPhoneId + "," + userPhoneIdPw + "," + userPhoneIdPwChk, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel(String type) {
            userPhoneIdPw = "";
            userPhoneIdPwChk = "";
            view_numPad.setVisible(NUMPAD_PHONE_SIGNUP_PASSWORD, numPadPasswordFinishOnClickListener);
        }
    };




    //endregion

    public LoginSignUpFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_signup, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    void init(){
        kakaoUserLogin = new KakaoUserLogin(mParent, kakaoLoginCallback);
        kakaoGetUserInfo = new KakaoGetUserInfo(mParent, kakaoGetUserInfoCallback);
      //  kakaoUserLogout = new KakaoUserLogout(this, kakaoLogoutCallback);
    }

    public void phoneSignUpInfoMove() {



        ((LoginActivity)mParent).setLoading(true);
        ((LoginActivity)mParent).setPhoneSignUpData_01(signUp_type, userPhoneId, userPhoneIdPw);
        ((LoginActivity)mParent).setFragmentPage(FRAGMENT_LOGIN_SIGNUP_INFO);
        ((LoginActivity)mParent).setLoading(false);

    }
    public void kakaoSignUpInfoMove() {

        ((LoginActivity)mParent).setLoading(true);
        kakaoUserLogin.login();



    }

    //region onClick

    @OnClick(R.id.iv_back)
    public void iv_backClicked(){

        ((LoginActivity)mParent).onBackPressed();

    }

    @OnClick(R.id.btn_phoneSignUp)
    public void btn_phoneSignUpClicked(){
        signUp_type = 0;
        ((LoginActivity)mParent).visiblePolicy(policyAgreeCallback);
    }

    @OnClick(R.id.btn_kakaoSignUp)
    public void btn_kakaoSignUpClicked(){
        signUp_type = 1;
        ((LoginActivity)mParent).visiblePolicy(policyAgreeCallback);
    }



    //endregion


    //region callback



    public AgreeCallback policyAgreeCallback = new AgreeCallback() {
        @Override
        public void agree() {
            ((LoginActivity)mParent).visiblePrivacy(privacyAgreeCallback);
        }

        @Override
        public void cancel() {
            signUp_type = -1;
        }
    };

    public AgreeCallback privacyAgreeCallback = new AgreeCallback() {
        @Override
        public void agree() {
            if(signUp_type == 0){
                userPhoneId = "";
                userPhoneIdPw = "";
                userPhoneIdPwChk = "";
                view_numPad.setVisible(NUMPAD_PHONE_SIGNUP_PHONE, numPadPhoneNumberFinishOnClickListener);
            }else if(signUp_type == 1){

                kakaoSignUpInfoMove();


            }

        }

        @Override
        public void cancel() {

            ((LoginActivity)mParent).visiblePolicy(policyAgreeCallback);
        }
    };

    public KakaoLoginCallback kakaoLoginCallback = new KakaoLoginCallback() {
        @Override
        public void onSuccess(String kakaoUserToken) {
            if (kakaoUserToken != null) {
                kakaoGetUserInfo.getUserInfo();
            }else{
                ((LoginActivity)mParent).setLoading(false);
                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "카카오 계정 로그인이 실패 하였습니다.");
                loginMessageDialog.show();
            }

        }

        @Override
        public void onError(@NonNull Throwable throwable) {
            LOG_E("kakaoLoginCallback : " + throwable.toString());
            ((LoginActivity)mParent).setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "카카오 계정 로그인이 실패 하였습니다.");
            loginMessageDialog.show();
        }
    };

    public KakaoGetUserInfoCallback kakaoGetUserInfoCallback = new KakaoGetUserInfoCallback() {
        @Override
        public void onSuccess(User user) {
            ((LoginActivity)mParent).setLoading(false);
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


            ((LoginActivity)mParent).setKakaoSignUpData_01(signUp_type, kakaoId, kakaoNickName, kakaoProfileImg, kakaoEmail);
            ((LoginActivity)mParent).setFragmentPage(FRAGMENT_LOGIN_SIGNUP_INFO);
            ((LoginActivity)mParent).setLoading(false);


        }

        @Override
        public void onError(@NonNull Throwable throwable) {
            ((LoginActivity)mParent).setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "카카오 계정 정보가 없습니다.");
            loginMessageDialog.show();
        }
    };

    //endregion

}
