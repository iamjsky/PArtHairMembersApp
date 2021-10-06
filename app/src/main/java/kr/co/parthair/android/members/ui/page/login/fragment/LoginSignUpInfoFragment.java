package kr.co.parthair.android.members.ui.page.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.net.api.callback.KakaoUserSignUpCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneSignUpCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseFragment;
import kr.co.parthair.android.members.ui.page.login.LoginActivity;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;

import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;

/**
 * ClassName            LoginSignUpInfoFragment
 * Created by JSky on   2021-09-28
 * <p>
 * Description
 */
public class LoginSignUpInfoFragment extends BaseFragment {

    @BindView(R.id.nsv_scrollView)
    NestedScrollView nsv_scrollView;
    @BindView(R.id.layout_topMenu)
    LinearLayout layout_topMenu;

    @BindView(R.id.edtxt_phoneNumber)
    EditText edtxt_phoneNumber;
    @BindView(R.id.edtxt_name)
    EditText edtxt_name;
    @BindView(R.id.edtxt_email)
    EditText edtxt_email;


    public LoginSignUpInfoFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_signup_info, container, false);
        ButterKnife.bind(this, view);

        nsv_scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LOG_E(scrollY+"");

                if (scrollY <= 90) {
                    layout_topMenu.setVisibility(View.GONE);
                    mParent.getWindow().setStatusBarColor(getResources().getColor(R.color.ph_page_bg_color));
                } else {
                    layout_topMenu.setVisibility(View.VISIBLE);
                    mParent.getWindow().setStatusBarColor(getResources().getColor(R.color.ph_menu_tab_color_01));
                }
            }


        });

        setUserInfoField();

        return view;
    }

    private void setUserInfoField() {

        int signUp_type = ((LoginActivity) mParent).getSignUpType();

        String phoneNumber = "";
        String email = "";

        if (signUp_type == 0) {
            ArrayList<String> phoneSignUpData = ((LoginActivity) mParent).getPhoneSignUpData();
            edtxt_phoneNumber.setText(phoneSignUpData.get(0) + "");
        } else if (signUp_type == 1) {

            ArrayList<String> kakaoSignUpData = ((LoginActivity) mParent).getKakaoSignUpData();
            edtxt_email.setText(kakaoSignUpData.get(5) + "");
        }
    }

    //region onClick

    @OnClick(R.id.iv_back)
    public void iv_backClicked() {
        ((LoginActivity) mParent).onBackPressed();
    }

    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked() {
        int signUp_type = ((LoginActivity) mParent).getSignUpType();

        if (signUp_type == 0) {

            String phoneSignUpPhoneNumber = edtxt_phoneNumber.getText().toString() + "";
            String phoneSignUpName = edtxt_name.getText().toString() + "";
            String phoneSignUpEmail = edtxt_email.getText().toString() + "";

            if (!String_IsNotNull(phoneSignUpPhoneNumber)) {

                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "휴대폰 번호를 입력해 주세요.");
                loginMessageDialog.show();
                return;

            }
            if (!String_IsNotNull(phoneSignUpName)) {

                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "이름를 입력해 주세요.");
                loginMessageDialog.show();
                return;

            }
            if (!String_IsNotNull(phoneSignUpEmail)) {

                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "아이디 / 비밀번호 찾기에 사용할\n이메일을 입력해 주세요.");
                loginMessageDialog.show();
                return;

            }

            ((LoginActivity) mParent).setPhoneSignUpData_02(phoneSignUpPhoneNumber, phoneSignUpName, phoneSignUpEmail);
            ArrayList<String> phoneSignUpData = ((LoginActivity) mParent).getPhoneSignUpData();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i < phoneSignUpData.size(); i++){
                sb.append(phoneSignUpData.get(i)+",");
            }
            Toast.makeText(mParent, "휴대폰 번호 계정 가입 준비 완료>>"+sb.toString(), Toast.LENGTH_LONG).show();

            ((LoginActivity)mParent).setLoading(true);
             userApi.phoneSignUp(phoneSignUpData, phoneSignUpCallback);




        } else if (signUp_type == 1) {

            String kakaoSignUpPhoneNumber = edtxt_phoneNumber.getText().toString() + "";
            String kakaoSignUpName = edtxt_name.getText().toString() + "";
            String kakaoSignUpEmail = edtxt_email.getText().toString() + "";


            if (!String_IsNotNull(kakaoSignUpPhoneNumber)) {

                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "휴대폰 번호를 입력해 주세요.");
                loginMessageDialog.show();
                return;

            }
            if (!String_IsNotNull(kakaoSignUpName)) {

                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "이름를 입력해 주세요.");
                loginMessageDialog.show();
                return;

            }
            if (!String_IsNotNull(kakaoSignUpEmail)) {

                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", "아이디 / 비밀번호 찾기를 위해\n사용할 이메일을 입력해 주세요.");
                loginMessageDialog.show();
                return;

            }


            ((LoginActivity) mParent).setKakaoSignUpData_02(kakaoSignUpPhoneNumber, kakaoSignUpName, kakaoSignUpEmail);

            ArrayList<String> kakaoSignUpData = ((LoginActivity) mParent).getKakaoSignUpData();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i < kakaoSignUpData.size(); i++){
                sb.append(kakaoSignUpData.get(i)+",");
            }

            Toast.makeText(mParent, "카카오 계정 가입 준비 완료>>"+sb.toString(), Toast.LENGTH_LONG).show();


            ((LoginActivity)mParent).setLoading(true);
             userApi.kakaoSignUp(kakaoSignUpData, kakaoUserSignUpCallback);

        }

    }

    //endregion

    //region callback

    private PhoneSignUpCallback phoneSignUpCallback = new PhoneSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            ((LoginActivity)mParent).setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", msg);
            loginMessageDialog.show();
            ((LoginActivity) mParent).onBackPressed();
        }

        @Override
        public void onError(int code, String msg) {
            ((LoginActivity)mParent).setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", msg);
            loginMessageDialog.show();
        }
    };
    private KakaoUserSignUpCallback kakaoUserSignUpCallback = new KakaoUserSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            ((LoginActivity)mParent).setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", msg);
            loginMessageDialog.show();
            ((LoginActivity) mParent).onBackPressed();
        }

        @Override
        public void onError(int code, String msg) {
            ((LoginActivity)mParent).setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mParent, "알림", msg);
            loginMessageDialog.show();
        }
    };

    //endregion
}
