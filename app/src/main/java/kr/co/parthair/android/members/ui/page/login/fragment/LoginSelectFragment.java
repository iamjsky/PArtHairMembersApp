package kr.co.parthair.android.members.ui.page.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import kr.co.parthair.android.members.ui.page.base.BaseFragment;
import kr.co.parthair.android.members.ui.page.login.LoginActivity;
import kr.co.parthair.android.members.ui.page.main.MainActivity;
import kr.co.parthair.android.members.ui.widget.numpad.NumPadView;

import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;

/**
 * ClassName            LoginSelectFragment
 * Created by JSky on   2021-09-28
 * <p>
 * Description
 */
public class LoginSelectFragment extends BaseFragment {


    KakaoUserLogin kakaoUserLogin;
    KakaoGetUserInfo kakaoGetUserInfo;
    KakaoUserLogout kakaoUserLogout;

    private String userPhoneId = "";
    private String userPhoneIdPw = "";
    //private String userPhoneIdPwChk = "";



    //region NUMPAD

    @BindView(R.id.view_numPad)
    NumPadView view_numPad;


    NumPadView.NumPadFinishOnClickListener numPadPhoneNumberFinishOnClickListener = new NumPadView.NumPadFinishOnClickListener() {
        @Override
        public void onClick(View view, String data) {
            Toast.makeText(mParent, data, Toast.LENGTH_SHORT).show();
            userPhoneId = data;
            view_numPad.setVisible(NUMPAD_PHONE_LOGIN_PASSWORD, numPadPasswordFinishOnClickListener);
        }
    };

    NumPadView.NumPadFinishOnClickListener numPadPasswordFinishOnClickListener = new NumPadView.NumPadFinishOnClickListener() {
        @Override
        public void onClick(View view, String data) {
            userPhoneIdPw = data;
            Toast.makeText(mParent, data, Toast.LENGTH_SHORT).show();
            phoneLogin(userPhoneId, userPhoneIdPw);
        }
    };
//    NumPadView.NumPadFinishOnClickListener numPadPasswordCheckFinishOnClickListener = new NumPadView.NumPadFinishOnClickListener() {
//        @Override
//        public void onClick(View view, String data) {
//            Toast.makeText(mParent, data, Toast.LENGTH_SHORT).show();
//        }
//    };




    //endregion

    public LoginSelectFragment() {
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

        String inputPhoneNumber = userPhoneId + "";
        String inputPhoneLoginPw = userPhoneIdPw + "";

        if (!String_IsNotNull(inputPhoneNumber)) {

            Toast.makeText(mParent, "휴대폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;

        }
        if (!String_IsNotNull(inputPhoneLoginPw)) {

            Toast.makeText(mParent, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;

        }

        userApi.phoneLogin(inputPhoneNumber, inputPhoneLoginPw, phoneLoginCallback);

    }

    //region onClick

    @OnClick(R.id.iv_back)
    public void iv_backClicked(){

        ((LoginActivity)mParent).onBackPressed();

    }

    @OnClick(R.id.btn_phoneLogin)
    public void btn_phoneLoginClicked(){
        LOG_I("???");
        view_numPad.setVisible(NUMPAD_PHONE_lOGIN_PHONE, numPadPhoneNumberFinishOnClickListener);

    }


    //endregion



    //region callback

    private CheckSignUpCallback checkSignUpCallback = new CheckSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int code, String msg) {
            Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();
        }
    };

    private PhoneSignUpCallback phoneSignUpCallback = new PhoneSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int code, String msg) {
            Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();
        }
    };

    private PhoneLoginCallback phoneLoginCallback = new PhoneLoginCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();
            userApi.getUserInfo(getUserInfoCallback);
        }

        @Override
        public void onError(int code, String msg) {
            Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();
        }
    };

    private GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();
            LOG_I(MyInfo.instance.getUserInfo().toString());
            Intent intent = new Intent(mParent, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mParent.startActivity(intent);
        }

        @Override
        public void onError(int code, String msg) {
            Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();
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
