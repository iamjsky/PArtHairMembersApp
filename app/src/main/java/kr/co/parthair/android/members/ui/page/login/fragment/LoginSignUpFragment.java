package kr.co.parthair.android.members.ui.page.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.callback.AgreeCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneSignUpCallback;
import kr.co.parthair.android.members.ui.page.base.BaseFragment;
import kr.co.parthair.android.members.ui.page.login.LoginActivity;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;
import kr.co.parthair.android.members.ui.widget.numpad.NumPadView;

import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;

/**
 * ClassName            LoginSignUpFragment
 * Created by JSky on   2021-09-28
 * <p>
 * Description
 */
public class LoginSignUpFragment extends BaseFragment {


    //region NUMPAD

    private String userPhoneId = "";
    private String userPhoneIdPw = "";
    private String userPhoneIdPwChk = "";

    @BindView(R.id.view_numPad)
    NumPadView view_numPad;

    private int login_type = -1;


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
            btn_phoneSignUpInfoMove();
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

    }

    public void btn_phoneSignUpInfoMove() {



        if (!String_IsNotNull(userPhoneId)) {


            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "휴대폰 번호를 입력해 주세요.");
            loginMessageDialog.show();
            return;

        }
        if (!String_IsNotNull(userPhoneIdPw)) {


            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "비밀번호를 입력해 주세요.");
            loginMessageDialog.show();
            return;

        }


        ((LoginActivity)mParent).setPhoneSignUpData_01(userPhoneId, userPhoneIdPw);
        ((LoginActivity)mParent).setFragmentPage(FRAGMENT_LOGIN_SIGNUP_INFO);


    }


    //region onClick

    @OnClick(R.id.iv_back)
    public void iv_backClicked(){

        ((LoginActivity)mParent).onBackPressed();

    }

    @OnClick(R.id.btn_phoneSignUp)
    public void btn_phoneSignUpClicked(){
        login_type = 0;
        ((LoginActivity)mParent).visiblePolicy(policyAgreeCallback);
    }

    @OnClick(R.id.btn_kakaoSignUp)
    public void btn_kakaoSignUpClicked(){
        login_type = 1;
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
            login_type = -1;
        }
    };

    public AgreeCallback privacyAgreeCallback = new AgreeCallback() {
        @Override
        public void agree() {
            if(login_type == 0){
                userPhoneId = "";
                userPhoneIdPw = "";
                userPhoneIdPwChk = "";
                view_numPad.setVisible(NUMPAD_PHONE_SIGNUP_PHONE, numPadPhoneNumberFinishOnClickListener);
            }else if(login_type == 1){

            }

        }

        @Override
        public void cancel() {

            ((LoginActivity)mParent).visiblePolicy(policyAgreeCallback);
        }
    };

    private PhoneSignUpCallback phoneSignUpCallback = new PhoneSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", msg);
            loginMessageDialog.show();
        }

        @Override
        public void onError(int code, String msg) {
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", msg);
            loginMessageDialog.show();
        }
    };

    //endregion

}
