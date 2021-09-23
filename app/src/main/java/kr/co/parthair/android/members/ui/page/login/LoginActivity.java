package kr.co.parthair.android.members.ui.page.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kakao.sdk.user.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.data.MyInfo;
import kr.co.parthair.android.members.net.api.callback.CheckSignUpCallback;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneLoginCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneSignUpCallback;
import kr.co.parthair.android.members.social.kakao.KakaoGetUserInfo;
import kr.co.parthair.android.members.social.kakao.KakaoUserLogin;
import kr.co.parthair.android.members.social.kakao.KakaoUserLogout;
import kr.co.parthair.android.members.social.kakao.callback.KakaoGetUserInfoCallback;
import kr.co.parthair.android.members.social.kakao.callback.KakaoLoginCallback;
import kr.co.parthair.android.members.social.kakao.callback.KakaoLogoutCallback;
import kr.co.parthair.android.members.ui.page.base.BaseActivity;
import kr.co.parthair.android.members.ui.widget.numpad.NumPadView;

import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;


public class LoginActivity extends BaseActivity {


    @BindView(R.id.edtxt_inputPhoneNumber)
    EditText edtxt_inputPhoneNumber;
    @BindView(R.id.edtxt_inputPhoneLoginPw)
    EditText edtxt_inputPhoneLoginPw;
    @BindView(R.id.edtxt_inputPhoneLoginName)
    EditText edtxt_inputPhoneLoginName;

    //region NUMPAD

    @BindView(R.id.view_numPad)
    NumPadView view_numPad;

    @OnClick(R.id.btn_numPadPhoneLoginNumber)
    public void btn_numPadPhoneLoginNumberClicked(){
        NumPadView.NumPadFinishOnClickListener numPadFinishOnClickListener = new NumPadView.NumPadFinishOnClickListener() {
            @Override
            public void onClick(View view, String data) {
                Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
            }
        };
        view_numPad.setVisible(NUMPAD_PHONE_lOGIN_PHONE, numPadFinishOnClickListener);

    }

    @OnClick(R.id.btn_numPadPhoneLoginPassword)
    public void btn_numPadPhoneLoginPasswordClicked(){
        NumPadView.NumPadFinishOnClickListener numPadFinishOnClickListener = new NumPadView.NumPadFinishOnClickListener() {
            @Override
            public void onClick(View view, String data) {
                Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
            }
        };
        view_numPad.setVisible(NUMPAD_PHONE_LOGIN_PASSWORD, numPadFinishOnClickListener);

    }



    //endregion


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

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    void init() {


        kakaoUserLogin = new KakaoUserLogin(this, kakaoLoginCallback);
        kakaoGetUserInfo = new KakaoGetUserInfo(this, kakaoGetUserInfoCallback);
        kakaoUserLogout = new KakaoUserLogout(this, kakaoLogoutCallback);
    }

    //region OnClick

    @OnClick(R.id.btn_checkSignUp)
    public void btn_checkSignUpClicked() {

        String inputPhoneNumber = edtxt_inputPhoneNumber.getText().toString() + "";

        if (!String_IsNotNull(inputPhoneNumber)) {

            Toast.makeText(this, "휴대폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;

        }

        userApi.checkSignUp(LOGIN_TYPE_PHONE, inputPhoneNumber, checkSignUpCallback);

    }

    @OnClick(R.id.btn_phoneSignUp)
    public void btn_phoneSignUpClicked() {

        String inputPhoneNumber = edtxt_inputPhoneNumber.getText().toString() + "";
        String inputPhoneLoginPw = edtxt_inputPhoneLoginPw.getText().toString() + "";
        String inputPhoneLoginName = edtxt_inputPhoneLoginName.getText().toString() + "";

        if (!String_IsNotNull(inputPhoneNumber)) {

            Toast.makeText(this, "휴대폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;

        }
        if (!String_IsNotNull(inputPhoneLoginPw)) {

            Toast.makeText(this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;

        }
        if (!String_IsNotNull(inputPhoneLoginName)) {

            Toast.makeText(this, "이름를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;

        }

        userApi.phoneSignUp(inputPhoneNumber, inputPhoneLoginPw, inputPhoneLoginName, phoneSignUpCallback);

    }

    @OnClick(R.id.btn_phoneLogin)
    public void btn_phoneLoginClicked() {

        String inputPhoneNumber = edtxt_inputPhoneNumber.getText().toString() + "";
        String inputPhoneLoginPw = edtxt_inputPhoneLoginPw.getText().toString() + "";

        if (!String_IsNotNull(inputPhoneNumber)) {

            Toast.makeText(this, "휴대폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;

        }
        if (!String_IsNotNull(inputPhoneLoginPw)) {

            Toast.makeText(this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;

        }

        userApi.phoneLogin(inputPhoneNumber, inputPhoneLoginPw, phoneLoginCallback);

    }

    @OnClick(R.id.btn_kakaoLogin)
    public void btn_kakaoLoginClicked() {

        kakaoUserLogin.login();

    }

    @OnClick(R.id.btn_kakaoLogout)
    public void btn_kakaoLogoutClicked() {

        kakaoUserLogout.logout();

    }


    //endregion


    //region Callback

    private CheckSignUpCallback checkSignUpCallback = new CheckSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int code, String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    };

    private PhoneSignUpCallback phoneSignUpCallback = new PhoneSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int code, String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    };

    private PhoneLoginCallback phoneLoginCallback = new PhoneLoginCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            userApi.getUserInfo(getUserInfoCallback);
        }

        @Override
        public void onError(int code, String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    };

    private GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            LOG_I(MyInfo.instance.getUserInfo().toString());
        }

        @Override
        public void onError(int code, String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    };

    public KakaoLoginCallback kakaoLoginCallback = new KakaoLoginCallback() {
        @Override
        public void onSuccess(String kakaoUserToken) {
            if (kakaoUserToken != null) {
                kakaoGetUserInfo.getUserInfo();
            }

        }

        @Override
        public void onError(@NonNull Throwable throwable) {
            LOG_E("kakaoLoginCallback : " + throwable.toString());
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
        }

        @Override
        public void onError(@NonNull Throwable throwable) {

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




    //endregion
}