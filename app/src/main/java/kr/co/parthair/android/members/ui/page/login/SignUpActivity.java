package kr.co.parthair.android.members.ui.page.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.net.api.callback.PhoneSignUpCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.common.dialog.LoadingDialog;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;

import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;

public class SignUpActivity extends BaseActivity {


    @BindView(R.id.layout_policy)
    RelativeLayout layout_policy;
    @BindView(R.id.layout_privacy)
    RelativeLayout layout_privacy;

    @BindView(R.id.tv_userPhoneNumber)
    TextView tv_userPhoneNumber;
    @BindView(R.id.edtxt_userPhoneNumber)
    EditText edtxt_userPhoneNumber;
    @BindView(R.id.tv_userName)
    TextView tv_userName;
    @BindView(R.id.edtxt_userName)
    EditText edtxt_userName;
    @BindView(R.id.tv_userEmail)
    TextView tv_userEmail;
    @BindView(R.id.edtxt_userEmail)
    EditText edtxt_userEmail;
    @BindView(R.id.tv_userPassword)
    TextView tv_userPassword;
    @BindView(R.id.edtxt_userPassword)
    EditText edtxt_userPassword;
    @BindView(R.id.tv_userPasswordChk)
    TextView tv_userPasswordChk;
    @BindView(R.id.edtxt_userPasswordChk)
    EditText edtxt_userPasswordChk;
    @BindView(R.id.sv_body)
    NestedScrollView sv_body;

    @BindView(R.id.layout_topMenu)
    LinearLayout layout_topMenu;

    @BindView(R.id.layout_signUpFinish)
    RelativeLayout layout_signUpFinish;


    @BindView(R.id.sv_policyBody)
    NestedScrollView sv_policyBody;

    @BindView(R.id.layout_policyTopMenu)
    LinearLayout layout_policyTopMenu;
    @BindView(R.id.sv_privacyBody)
    NestedScrollView sv_privacyBody;

    @BindView(R.id.layout_privacyTopMenu)
    LinearLayout layout_privacyTopMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        init();

    }

    void init(){
        layout_signUpFinish.setVisibility(View.GONE);
        layout_policy.setVisibility(View.VISIBLE);

        edtxt_userPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tv_userPhoneNumber.setTextColor(getColor(R.color.ebay_blue));
                    edtxt_userPhoneNumber.setTextColor(getColor(R.color.paypal_blue));

                }else{
                    tv_userPhoneNumber.setTextColor(getColor(R.color.ph_main_color));
                    edtxt_userPhoneNumber.setTextColor(getColor(R.color.ph_main_color));
                }
            }
        });
        edtxt_userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tv_userName.setTextColor(getColor(R.color.ebay_blue));
                    edtxt_userName.setTextColor(getColor(R.color.paypal_blue));

                }else{
                    tv_userName.setTextColor(getColor(R.color.ph_main_color));
                    edtxt_userName.setTextColor(getColor(R.color.ph_main_color));
                }
            }
        });
        edtxt_userEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tv_userEmail.setTextColor(getColor(R.color.ebay_blue));
                    edtxt_userEmail.setTextColor(getColor(R.color.paypal_blue));

                }else{
                    tv_userEmail.setTextColor(getColor(R.color.ph_main_color));
                    edtxt_userEmail.setTextColor(getColor(R.color.ph_main_color));
                }
            }
        });
        edtxt_userPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tv_userPassword.setTextColor(getColor(R.color.ebay_blue));
                    edtxt_userPassword.setTextColor(getColor(R.color.paypal_blue));

                }else{
                    tv_userPassword.setTextColor(getColor(R.color.ph_main_color));
                    edtxt_userPassword.setTextColor(getColor(R.color.ph_main_color));
                }
            }
        });
        edtxt_userPasswordChk.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tv_userPasswordChk.setTextColor(getColor(R.color.ebay_blue));
                    edtxt_userPasswordChk.setTextColor(getColor(R.color.paypal_blue));

                }else{
                    tv_userPasswordChk.setTextColor(getColor(R.color.ph_main_color));
                    edtxt_userPasswordChk.setTextColor(getColor(R.color.ph_main_color));
                }
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
        sv_policyBody.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LOG_E(scrollY+"");

                if (scrollY <= 90) {
                    layout_policyTopMenu.setVisibility(View.GONE);
                    getWindow().setStatusBarColor(getResources().getColor(R.color.ph_page_bg_color));
                } else {
                    layout_policyTopMenu.setVisibility(View.VISIBLE);
                    getWindow().setStatusBarColor(getResources().getColor(R.color.ph_menu_tab_color_01));
                }
            }


        });
        sv_privacyBody.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LOG_E(scrollY+"");

                if (scrollY <= 90) {
                    layout_privacyTopMenu.setVisibility(View.GONE);
                    getWindow().setStatusBarColor(getResources().getColor(R.color.ph_page_bg_color));
                } else {
                    layout_privacyTopMenu.setVisibility(View.VISIBLE);
                    getWindow().setStatusBarColor(getResources().getColor(R.color.ph_menu_tab_color_01));
                }
            }


        });
    }

    @Override
    public void onBackPressed() {
        if(layout_policy.getVisibility() == View.VISIBLE){
            super.onBackPressed();
        }else if(layout_privacy.getVisibility() == View.VISIBLE){

            layout_policy.setVisibility(View.VISIBLE);
            layout_privacy.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
        }

    }

    boolean checkInputField(){
        String phoneSignUpPhoneNumber = edtxt_userPhoneNumber.getText().toString() + "";
        String phoneSignUpPassword = edtxt_userPassword.getText().toString() + "";
        String phoneSignUpPasswordChk = edtxt_userPasswordChk.getText().toString() + "";
        String phoneSignUpName = edtxt_userName.getText().toString() + "";
        String phoneSignUpEmail = edtxt_userEmail.getText().toString() + "";

        if (!String_IsNotNull(phoneSignUpPhoneNumber)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "휴대폰 번호를 입력해 주세요.");
            loginMessageDialog.show();
            return false;

        }
        if (!String_IsNotNull(phoneSignUpPassword)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "비밀번호를 입력해 주세요.");
            loginMessageDialog.show();
            return false;

        }
        if (phoneSignUpPassword.length() != 4) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "비밀번호 4자리를 입력해 주세요.");
            loginMessageDialog.show();
            return false;

        }
        if (!String_IsNotNull(phoneSignUpPasswordChk)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "비밀번호 확인을 입력해 주세요.");
            loginMessageDialog.show();
            return false;

        }
        if (phoneSignUpPasswordChk.length() != 4) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "비밀번호 확인 4자리를 입력해 주세요.");
            loginMessageDialog.show();
            return false;

        }
        if (!phoneSignUpPassword.equals(phoneSignUpPasswordChk)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "비밀번호와 확인이 일치하지 않습니다.");
            loginMessageDialog.show();
            return false;

        }
        if (!String_IsNotNull(phoneSignUpName)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "이름를 입력해 주세요.");
            loginMessageDialog.show();
            return false;

        }
        if (!String_IsNotNull(phoneSignUpEmail)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "아이디 / 비밀번호 찾기에 사용할\n이메일을 입력해 주세요.");
            loginMessageDialog.show();
            return false;

        }

        return true;
    }

    //region onClick

    @OnClick(R.id.btn_policyAgree)
    public void btn_policyAgreeClicked(){
        layout_privacy.setVisibility(View.VISIBLE);
        layout_policy.setVisibility(View.GONE);
    }
    @OnClick(R.id.btn_privacyAgree)
    public void btn_privacyAgreeClicked(){
        layout_policy.setVisibility(View.GONE);
        layout_privacy.setVisibility(View.GONE);
    }
    @OnClick({R.id.iv_back,R.id.iv_policyBack,R.id.iv_privacyBack,R.id.iv_backTop,R.id.iv_privacyBackTop,R.id.iv_policyBackTop})
    public void iv_backClicked(){
        onBackPressed();
    }


    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked(){
        if(checkInputField()){
            String phoneSignUpPhoneNumber = edtxt_userPhoneNumber.getText().toString() + "";
            String phoneSignUpPassword = edtxt_userPassword.getText().toString() + "";
            String phoneSignUpName = edtxt_userName.getText().toString() + "";
            String phoneSignUpEmail = edtxt_userEmail.getText().toString() + "";
            userApi.phoneSignUp(phoneSignUpPhoneNumber, phoneSignUpPassword, phoneSignUpName, phoneSignUpEmail, phoneSignUpCallback);
        }

    }

    @OnClick(R.id.btn_goLogin)
    public void btn_goLoginClicked(){
        onBackPressed();
    }

    //endregion

    //region callback

    private PhoneSignUpCallback phoneSignUpCallback = new PhoneSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            setLoading(false);
            layout_signUpFinish.setVisibility(View.VISIBLE);


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