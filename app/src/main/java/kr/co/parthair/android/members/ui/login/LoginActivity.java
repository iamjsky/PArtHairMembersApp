package kr.co.parthair.android.members.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

    //region NUMPAD

    @BindView(R.id.layout_numPad)
    RelativeLayout layout_numPad;
    @BindView(R.id.tv_numPadNumber)
    TextView tv_numPadNumber;

    @OnClick(R.id.btn_numPadInputFinish)
    public void btn_numPadInputFinishClicked() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String numPadNumber = tv_numPadNumber.getText().toString() + "";
                layout_numPad.setVisibility(View.GONE);
                Toast.makeText(mContext, numPadNumber, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @OnClick(R.id.btn_visibleNumPad)
    public void btn_visibleNumPadClicked() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_numPadNumber.setText("");
                layout_numPad.setVisibility(View.VISIBLE);
            }
        });

    }

    @OnClick({R.id.btn_numPad_0, R.id.btn_numPad_1, R.id.btn_numPad_2,
            R.id.btn_numPad_3, R.id.btn_numPad_4, R.id.btn_numPad_5,
            R.id.btn_numPad_6, R.id.btn_numPad_7, R.id.btn_numPad_8,
            R.id.btn_numPad_9, R.id.btn_numPad_cancel, R.id.btn_numPad_delete})
    public void btn_numPadClicked(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String numPadNumber = tv_numPadNumber.getText().toString() + "";
                switch (view.getId()) {
                    case R.id.btn_numPad_0:
                        numPadNumber += "0";
                        break;
                    case R.id.btn_numPad_1:
                        numPadNumber += "1";
                        break;
                    case R.id.btn_numPad_2:
                        numPadNumber += "2";
                        break;
                    case R.id.btn_numPad_3:
                        numPadNumber += "3";
                        break;
                    case R.id.btn_numPad_4:
                        numPadNumber += "4";
                        break;
                    case R.id.btn_numPad_5:
                        numPadNumber += "5";
                        break;
                    case R.id.btn_numPad_6:
                        numPadNumber += "6";
                        break;
                    case R.id.btn_numPad_7:
                        numPadNumber += "7";
                        break;
                    case R.id.btn_numPad_8:
                        numPadNumber += "8";
                        break;
                    case R.id.btn_numPad_9:
                        numPadNumber += "9";
                        break;
                    case R.id.btn_numPad_cancel:
                        numPadNumber = "";
                        break;
                    case R.id.btn_numPad_delete:
                       // LOG_D("numPadNumber.length()>>" + numPadNumber.length());
                        if (numPadNumber.length() > 0) {
                            numPadNumber = numPadNumber.substring(0, numPadNumber.length() -1);
                        } else {
                            numPadNumber = "";
                        }
                        break;

                    default:
                        break;
                }

                tv_numPadNumber.setText(numPadNumber + "");
            }
        });


    }

    //endregion


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