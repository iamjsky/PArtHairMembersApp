package kr.co.parthair.android.members.ui.login;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.data.MyInfo;
import kr.co.parthair.android.members.net.api.callback.CheckSignUpCallback;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneLoginCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneSignUpCallback;
import kr.co.parthair.android.members.ui.base.BaseActivity;

import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.edtxt_inputPhoneNumber)
    EditText edtxt_inputPhoneNumber;
    @BindView(R.id.edtxt_inputPhoneLoginPw)
    EditText edtxt_inputPhoneLoginPw;
    @BindView(R.id.edtxt_inputPhoneLoginName)
    EditText edtxt_inputPhoneLoginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
    //endregion
}