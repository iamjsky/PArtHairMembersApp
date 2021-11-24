package kr.co.parthair.android.members.ui.page.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.kakao.sdk.user.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.net.api.UserApi;
import kr.co.parthair.android.members.net.api.callback.KakaoUserLoginCallback;
import kr.co.parthair.android.members.net.api.callback.KakaoUserSignUpCallback;
import kr.co.parthair.android.members.social.kakao.KakaoGetUserInfo;
import kr.co.parthair.android.members.social.kakao.KakaoUserLogin;
import kr.co.parthair.android.members.social.kakao.callback.KakaoGetUserInfoCallback;
import kr.co.parthair.android.members.social.kakao.callback.KakaoLoginCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseDialog;
import kr.co.parthair.android.members.ui.page.common.base.BaseFullStyleDialog;
import kr.co.parthair.android.members.ui.page.common.dialog.LoadingDialog;
import kr.co.parthair.android.members.ui.page.login.SignUpSocialActivity;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;

/**
 * ClassName            SocialLoginLinkDialog
 * Created by JSky on   2021-11-23
 * <p>
 * Description
 */
public class SocialLoginLinkDialog extends BaseFullStyleDialog {
    protected UserApi userApi = new UserApi();
    KakaoUserLogin kakaoUserLogin;
    KakaoGetUserInfo kakaoGetUserInfo;

    @BindView(R.id.layout_socialLinkMsg)
    LinearLayout layout_socialLinkMsg;
    @BindView(R.id.layout_loading)
    LinearLayout layout_loading;
    @BindView(R.id.layout_linkedFinish)
    LinearLayout layout_linkedFinish;

    public SocialLoginLinkDialog(Context context) {
        super(context);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_social_login_link);
        ButterKnife.bind(this);
        kakaoUserLogin = new KakaoUserLogin(mContext, kakaoLoginCallback);
        kakaoGetUserInfo = new KakaoGetUserInfo(mContext, kakaoGetUserInfoCallback);
        layout_socialLinkMsg.setVisibility(View.VISIBLE);
        layout_loading.setVisibility(View.GONE);
        layout_linkedFinish.setVisibility(View.GONE);
    }

    public void setLoading(boolean value) {

        if(value){
            layout_loading.setVisibility(View.VISIBLE);
        }else{
            layout_loading.setVisibility(View.GONE);
        }

    }
    void linkSocialKakao(String kakao_id, String user_nickname, String user_profile_img){
        setLoading(true);
        String user_phone = MyInfo.instance.getUserInfo().getUserPhone() + "";
        String phone_login_pw = MyInfo.instance.getUserInfo().getPhoneLoginPw() + "";
        userApi.kakaoSignUp(kakao_id, user_nickname, user_profile_img, user_phone, phone_login_pw, kakaoUserSignUpCallback);
    }


    @OnClick(R.id.btn_cancel)
    public void btn_cancelClicked(){
        dismiss();
    }
    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked(){
        kakaoUserLogin.login();
    }
    @OnClick(R.id.btn_finish)
    public void btn_finishClicked(){
        dismiss();
    }
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

            linkSocialKakao(kakaoId, kakaoNickName, kakaoProfileImg);
        }

        @Override
        public void onError(@NonNull Throwable throwable) {
            setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "카카오 계정 정보가 없습니다.");
            loginMessageDialog.show();
        }
    };

    private KakaoUserSignUpCallback kakaoUserSignUpCallback = new KakaoUserSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg, String userName) {
            setLoading(false);
            layout_socialLinkMsg.setVisibility(View.GONE);
            layout_loading.setVisibility(View.GONE);
            layout_linkedFinish.setVisibility(View.VISIBLE);


        }

        @Override
        public void onError(int code, String msg) {
            setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", msg);
            loginMessageDialog.show();
        }
    };

}
