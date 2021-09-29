package kr.co.parthair.android.members.ui.page.login;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.CommonInfo;
import kr.co.parthair.android.members.common.callback.AgreeCallback;
import kr.co.parthair.android.members.ui.page.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.login.fragment.LoginMainFragment;
import kr.co.parthair.android.members.ui.page.login.fragment.LoginSelectFragment;
import kr.co.parthair.android.members.ui.page.login.fragment.LoginSignUpFragment;
import kr.co.parthair.android.members.ui.page.login.fragment.LoginSignUpInfoFragment;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.layout_fragment)
    FrameLayout layout_fragment;
    public Fragment fragmentPage;

    public int nowFragmentPage = -1;

    @BindView(R.id.layout_loading)
    LinearLayout layout_loading;

    //이용약관
    @BindView(R.id.layout_policy)
    RelativeLayout layout_policy;
    //개인정보처리방침
    @BindView(R.id.layout_privacy)
    RelativeLayout layout_privacy;

    //region 최종 가입 단계 정보
    private int signUp_type = -1;
    private String phoneSignUpPhoneNumber = "";
    private String phoneSignUpPassword = "";
    private String phoneSignUpUserName = "";
    private String phoneSignUpUserEmail = "";

    private String kakaoSignUpKakaoId = "";
    private String kakaoSignUpKakaoNickName = "";
    private String kakaoSignUpKakaoProfileImg = "";

    private String kakaoSignUpPhoneNumber = "";
    private String kakaoSignUpUserName = "";
    private String kakaoSignUpUserEmail = "";

    public int getSignUpType(){

        return signUp_type;
    }
    public ArrayList<String> getPhoneSignUpData(){
        ArrayList<String> phoneLoginDataList = new ArrayList<>();
        phoneLoginDataList.add(phoneSignUpPhoneNumber);
        phoneLoginDataList.add(phoneSignUpPassword);
        phoneLoginDataList.add(phoneSignUpUserName);
        phoneLoginDataList.add(phoneSignUpUserEmail);
        return phoneLoginDataList;
    }
    public void setPhoneSignUpData_01(int signUp_type, String value1, String value2){
        this.signUp_type = signUp_type;
        phoneSignUpPhoneNumber = value1;
        phoneSignUpPassword = value2;
    }
    public void setPhoneSignUpData_02(String value1, String value2, String value3){

        phoneSignUpPhoneNumber = value1;
        phoneSignUpUserName = value2;
        phoneSignUpUserEmail = value3;
    }

    public ArrayList<String> getKakaoSignUpData(){
        ArrayList<String> kakaoLoginDataList = new ArrayList<>();
        kakaoLoginDataList.add(kakaoSignUpKakaoId);
        kakaoLoginDataList.add(kakaoSignUpKakaoNickName);
        kakaoLoginDataList.add(kakaoSignUpKakaoProfileImg);
        kakaoLoginDataList.add(kakaoSignUpPhoneNumber);
        kakaoLoginDataList.add(kakaoSignUpUserName);
        kakaoLoginDataList.add(kakaoSignUpUserEmail);
        return kakaoLoginDataList;
    }
    public void setKakaoSignUpData_01(int signUp_type, String value1, String value2, String value3, String value4){
        this.signUp_type = signUp_type;
        kakaoSignUpKakaoId = value1;
        kakaoSignUpKakaoNickName = value2;
        kakaoSignUpKakaoProfileImg = value3;
        kakaoSignUpUserEmail = value4;
    }
    public void setKakaoSignUpData_02(String value1, String value2, String value3){

        kakaoSignUpPhoneNumber = value1;
        kakaoSignUpUserName = value2;
        kakaoSignUpUserEmail = value3;
    }
    //endregion




//    KakaoUserLogin kakaoUserLogin;
//    KakaoGetUserInfo kakaoGetUserInfo;
//    KakaoUserLogout kakaoUserLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        init();
    }

    @Override
    public void onBackPressed() {
        if(CommonInfo.instance.isNumPadVisible){
            return;
        }
        if(layout_policy.getVisibility() == View.VISIBLE){
            iv_policyBackClicked();
            return;
        }
        if(layout_privacy.getVisibility() == View.VISIBLE){
            iv_privacyBackClicked();
            return;
        }
        switch (nowFragmentPage) {
            case FRAGMENT_LOGIN_MAIN:
                //super.onBackPressed();
                break;

            case FRAGMENT_LOGIN_SELECT:
                setFragmentPage(FRAGMENT_LOGIN_MAIN);
                break;
            case FRAGMENT_LOGIN_SIGNUP:
                setFragmentPage(FRAGMENT_LOGIN_MAIN);
                break;
            case FRAGMENT_LOGIN_SIGNUP_INFO:
                setFragmentPage(FRAGMENT_LOGIN_MAIN);
                break;



        }

    }

    void init() {

        setFragmentPage(FRAGMENT_LOGIN_MAIN);

//        kakaoUserLogin = new KakaoUserLogin(this, kakaoLoginCallback);
//        kakaoGetUserInfo = new KakaoGetUserInfo(this, kakaoGetUserInfoCallback);
//        kakaoUserLogout = new KakaoUserLogout(this, kakaoLogoutCallback);
    }


    public void setFragmentPage(int page) {
        String tag = "";
        switch (page) {
            case FRAGMENT_LOGIN_MAIN:
                fragmentPage = new LoginMainFragment();
                nowFragmentPage = FRAGMENT_LOGIN_MAIN;
                tag = FRAGMENT_LOGIN_MAIN + "";
                break;
            case FRAGMENT_LOGIN_SELECT:
                fragmentPage = new LoginSelectFragment();
                nowFragmentPage = FRAGMENT_LOGIN_SELECT;
                tag = FRAGMENT_LOGIN_SELECT + "";
                break;
            case FRAGMENT_LOGIN_SIGNUP:
                fragmentPage = new LoginSignUpFragment();
                nowFragmentPage = FRAGMENT_LOGIN_SIGNUP;
                tag = FRAGMENT_LOGIN_SIGNUP + "";
                break;
            case FRAGMENT_LOGIN_SIGNUP_INFO:
                fragmentPage = new LoginSignUpInfoFragment();
                nowFragmentPage = FRAGMENT_LOGIN_SIGNUP_INFO;
                tag = FRAGMENT_LOGIN_SIGNUP_INFO + "";
                break;

        }
        if (fragmentPage != null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(layout_fragment.getId(), fragmentPage, tag);
            transaction.commitAllowingStateLoss();

        }
    }

    public void setLoading(boolean value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(value){
                    layout_loading.setVisibility(View.VISIBLE);
                }else{
                    layout_loading.setVisibility(View.GONE);
                }
            }
        });


    }

    public AgreeCallback policyAgreeCallback;
    public AgreeCallback privacyAgreeCallback;

    public void visiblePolicy(AgreeCallback callback){
        policyAgreeCallback = callback;
        layout_policy.setVisibility(View.VISIBLE);
    }
    public void visiblePrivacy(AgreeCallback callback){
        privacyAgreeCallback = callback;
        layout_privacy.setVisibility(View.VISIBLE);
    }
    //region OnClick

    @OnClick(R.id.iv_policyBack)
    public void iv_policyBackClicked(){

        layout_policy.setVisibility(View.GONE);
        policyAgreeCallback.cancel();

    }
    @OnClick(R.id.iv_privacyBack)
    public void iv_privacyBackClicked(){

        layout_privacy.setVisibility(View.GONE);
        privacyAgreeCallback.cancel();

    }
    @OnClick(R.id.btn_policyAgree)
    public void btn_policyAgreeClicked(){

        layout_policy.setVisibility(View.GONE);
        policyAgreeCallback.agree();

    }
    @OnClick(R.id.btn_privacyAgree)
    public void btn_privacyAgreeClicked(){

        layout_privacy.setVisibility(View.GONE);
        privacyAgreeCallback.agree();

    }
//    @OnClick(R.id.btn_checkSignUp)
//    public void btn_checkSignUpClicked() {
//
//        String inputPhoneNumber = edtxt_inputPhoneNumber.getText().toString() + "";
//
//        if (!String_IsNotNull(inputPhoneNumber)) {
//
//            Toast.makeText(this, "휴대폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//
//        userApi.checkSignUp(LOGIN_TYPE_PHONE, inputPhoneNumber, checkSignUpCallback);
//
//    }
//
//    @OnClick(R.id.btn_phoneSignUp)
//    public void btn_phoneSignUpClicked() {
//
//        String inputPhoneNumber = edtxt_inputPhoneNumber.getText().toString() + "";
//        String inputPhoneLoginPw = edtxt_inputPhoneLoginPw.getText().toString() + "";
//        String inputPhoneLoginName = edtxt_inputPhoneLoginName.getText().toString() + "";
//
//        if (!String_IsNotNull(inputPhoneNumber)) {
//
//            Toast.makeText(this, "휴대폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//        if (!String_IsNotNull(inputPhoneLoginPw)) {
//
//            Toast.makeText(this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//        if (!String_IsNotNull(inputPhoneLoginName)) {
//
//            Toast.makeText(this, "이름를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//
//        userApi.phoneSignUp(inputPhoneNumber, inputPhoneLoginPw, inputPhoneLoginName, phoneSignUpCallback);
//
//    }
//
//    @OnClick(R.id.btn_phoneLogin_)
//    public void btn_phoneLoginClicked() {
//
//        String inputPhoneNumber = edtxt_inputPhoneNumber.getText().toString() + "";
//        String inputPhoneLoginPw = edtxt_inputPhoneLoginPw.getText().toString() + "";
//
//        if (!String_IsNotNull(inputPhoneNumber)) {
//
//            Toast.makeText(this, "휴대폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//        if (!String_IsNotNull(inputPhoneLoginPw)) {
//
//            Toast.makeText(this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//
//        userApi.phoneLogin(inputPhoneNumber, inputPhoneLoginPw, phoneLoginCallback);
//
//    }
//
//    @OnClick(R.id.btn_kakaoLogin)
//    public void btn_kakaoLoginClicked() {
//
//        kakaoUserLogin.login();
//
//    }
//
//    @OnClick(R.id.btn_kakaoLogout)
//    public void btn_kakaoLogoutClicked() {
//
//        kakaoUserLogout.logout();
//
//    }


    //endregion


    //region Callback

//    private CheckSignUpCallback checkSignUpCallback = new CheckSignUpCallback() {
//        @Override
//        public void onSuccess(int code, String msg) {
//            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onError(int code, String msg) {
//            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//        }
//    };
//
//    private PhoneSignUpCallback phoneSignUpCallback = new PhoneSignUpCallback() {
//        @Override
//        public void onSuccess(int code, String msg) {
//            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onError(int code, String msg) {
//            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//        }
//    };
//
//    private PhoneLoginCallback phoneLoginCallback = new PhoneLoginCallback() {
//        @Override
//        public void onSuccess(int code, String msg) {
//            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//            userApi.getUserInfo(getUserInfoCallback);
//        }
//
//        @Override
//        public void onError(int code, String msg) {
//            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//        }
//    };
//
//    private GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
//        @Override
//        public void onSuccess(int code, String msg) {
//            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//            LOG_I(MyInfo.instance.getUserInfo().toString());
//        }
//
//        @Override
//        public void onError(int code, String msg) {
//            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//        }
//    };
//
//    public KakaoLoginCallback kakaoLoginCallback = new KakaoLoginCallback() {
//        @Override
//        public void onSuccess(String kakaoUserToken) {
//            if (kakaoUserToken != null) {
//                kakaoGetUserInfo.getUserInfo();
//            }
//
//        }
//
//        @Override
//        public void onError(@NonNull Throwable throwable) {
//            LOG_E("kakaoLoginCallback : " + throwable.toString());
//        }
//    };
//
//    public KakaoGetUserInfoCallback kakaoGetUserInfoCallback = new KakaoGetUserInfoCallback() {
//        @Override
//        public void onSuccess(User user) {
//            String kakaoId = "";
//            String kakaoNickName = "";
//            String kakaoProfileImg = "";
//            String kakaoEmail = "";
//
//            if (user.getId() != 0 && user.getId() != -1) {
//                kakaoId = user.getId() + "";
//            } else {
//                kakaoId = "";
//            }
//
//            if (user.getKakaoAccount().getProfile().getNickname() != null) {
//                kakaoNickName = user.getKakaoAccount().getProfile().getNickname() + "";
//            } else {
//                kakaoNickName = "이름없음";
//            }
//
//
//            if (user.getKakaoAccount().getProfile().getThumbnailImageUrl() != null) {
//                kakaoProfileImg = user.getKakaoAccount().getProfile().getThumbnailImageUrl() + "";
//            } else {
//                kakaoProfileImg = "";
//            }
//
//            if (user.getKakaoAccount().getEmail() != null &&
//                    user.getKakaoAccount().getEmail().contains("@")) {
//                kakaoEmail = user.getKakaoAccount().getEmail() + "";
//            } else {
//                kakaoEmail = "";
//            }
//
//
////            userApi.socialLogin(
////                    1,
////                    kakaoNickName,
////                    kakaoEmail,
////                    kakaoId,
////                    kakaoProfileImg,
////                    loginCallback);
//        }
//
//        @Override
//        public void onError(@NonNull Throwable throwable) {
//
//        }
//    };
//
//    public KakaoLogoutCallback kakaoLogoutCallback = new KakaoLogoutCallback() {
//        @Override
//        public void onSuccess() {
//
//        }
//
//        @Override
//        public void onError(@NonNull Throwable throwable) {
//
//        }
//    };


    //endregion
}