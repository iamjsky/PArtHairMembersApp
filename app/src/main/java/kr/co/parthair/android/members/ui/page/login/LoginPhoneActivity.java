package kr.co.parthair.android.members.ui.page.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.CommonInfo;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.common.MyPreferenceManager;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneLoginCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.common.dialog.LoadingDialog;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;
import kr.co.parthair.android.members.ui.page.main.MainActivity;
import kr.co.parthair.android.members.ui.widget.numpad.NumPadView;

public class LoginPhoneActivity extends BaseActivity {

    int type = 0;
    static final int LOGIN_INPUT_PHONE_NUMBER = 0;
    static final int LOGIN_INPUT_PASSWORD = 1;

    LoadingDialog loadingDialog = null;

    @BindView(R.id.tv_desc)
    TextView tv_desc;

    @BindView(R.id.tv_phoneNumber)
    TextView tv_phoneNumber;

    @BindView(R.id.layout_phoneNumber)
    LinearLayout layout_phoneNumber;

    @BindView(R.id.layout_password)
    LinearLayout layout_password;

    @BindView(R.id.iv_password_01)
    ImageView iv_password_01;
    @BindView(R.id.iv_password_02)
    ImageView iv_password_02;
    @BindView(R.id.iv_password_03)
    ImageView iv_password_03;
    @BindView(R.id.iv_password_04)
    ImageView iv_password_04;



    @BindView(R.id.layout_inputNumPad)
    LinearLayout layout_inputNumPad;

    String phoneText = "";
    String passwordText = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        ButterKnife.bind(this);

        init();
    }

    void init() {
        Window w = getWindow();
        w.setStatusBarColor(getResources().getColor(R.color.ph_main_color));
        setInputType(LOGIN_INPUT_PHONE_NUMBER);


    }

    void setInputType(int _type) {
        type = _type;

        switch (type) {
            case LOGIN_INPUT_PHONE_NUMBER:
                layout_inputNumPad.setVisibility(View.INVISIBLE);
                Animation numPadAnim = AnimationUtils.loadAnimation(this, R.anim.numpad_up);

                layout_inputNumPad.startAnimation(numPadAnim);

                numPadAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        layout_inputNumPad.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                phoneText = "";
                passwordText = "";
                tv_phoneNumber.setText("");
                tv_desc.setText("휴대폰 번호를 입력해 주세요.");
                layout_phoneNumber.setVisibility(View.VISIBLE);
                layout_password.setVisibility(View.GONE);
                break;

            case LOGIN_INPUT_PASSWORD:
                layout_inputNumPad.setVisibility(View.INVISIBLE);
                Animation pwNumPadAnim = AnimationUtils.loadAnimation(this, R.anim.numpad_up);

                layout_inputNumPad.startAnimation(pwNumPadAnim);

                pwNumPadAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        layout_inputNumPad.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                passwordText = "";
                iv_password_01.setImageResource(R.drawable.bg_password_round_default_01);
                iv_password_02.setImageResource(R.drawable.bg_password_round_default_01);
                iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                tv_desc.setText("비밀번호를 입력해 주세요.");
                layout_phoneNumber.setVisibility(View.GONE);
                layout_password.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void setLoading(boolean value) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (value) {

                    loadingDialog.show();
                } else {
                    if (loadingDialog != null) {
                        loadingDialog.dismiss();
                    }

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (type == LOGIN_INPUT_PHONE_NUMBER) {
            //setInputType(LOGIN_INPUT_PHONE_NUMBER);
            super.onBackPressed();
        } else if (type == LOGIN_INPUT_PASSWORD) {
            setInputType(LOGIN_INPUT_PHONE_NUMBER);
        }

    }

    //region onClick

    @OnClick({R.id.btn_numPad_0, R.id.btn_numPad_1, R.id.btn_numPad_2,
            R.id.btn_numPad_3, R.id.btn_numPad_4, R.id.btn_numPad_5,
            R.id.btn_numPad_6, R.id.btn_numPad_7, R.id.btn_numPad_8,
            R.id.btn_numPad_9, R.id.btn_numPad_cancel, R.id.btn_numPad_delete})
    public void btn_numPadClicked(View view) {
        switch (type) {
            case LOGIN_INPUT_PHONE_NUMBER:
                String numPadNumber = tv_phoneNumber.getText().toString() + "";
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
                            numPadNumber = numPadNumber.substring(0, numPadNumber.length() - 1);
                        } else {
                            numPadNumber = "";
                        }
                        break;

                    default:
                        break;
                }

                tv_phoneNumber.setText(
                        PhoneNumberUtils.formatNumber(numPadNumber, Locale.getDefault().getCountry()) + "");
                phoneText = tv_phoneNumber.getText().toString() + "";
                phoneText = phoneText.replace("-", "");
                break;

            case LOGIN_INPUT_PASSWORD:
                String numPadNumberPw = passwordText + "";
                switch (view.getId()) {
                    case R.id.btn_numPad_0:
                        numPadNumberPw += "0";
                        break;
                    case R.id.btn_numPad_1:
                        numPadNumberPw += "1";
                        break;
                    case R.id.btn_numPad_2:
                        numPadNumberPw += "2";
                        break;
                    case R.id.btn_numPad_3:
                        numPadNumberPw += "3";
                        break;
                    case R.id.btn_numPad_4:
                        numPadNumberPw += "4";
                        break;
                    case R.id.btn_numPad_5:
                        numPadNumberPw += "5";
                        break;
                    case R.id.btn_numPad_6:
                        numPadNumberPw += "6";
                        break;
                    case R.id.btn_numPad_7:
                        numPadNumberPw += "7";
                        break;
                    case R.id.btn_numPad_8:
                        numPadNumberPw += "8";
                        break;
                    case R.id.btn_numPad_9:
                        numPadNumberPw += "9";
                        break;
                    case R.id.btn_numPad_cancel:
                        numPadNumberPw = "";
                        iv_password_01.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                        break;
                    case R.id.btn_numPad_delete:
                        // LOG_D("numPadNumber.length()>>" + numPadNumber.length());
                        if (numPadNumberPw.length() > 0) {
                            numPadNumberPw = numPadNumberPw.substring(0, numPadNumberPw.length() - 1);

                        } else {
                            numPadNumberPw = "";

                        }
                        break;

                    default:
                        break;
                }
                // Log.e("NumPadView", numPadNumberPw + "," + numPadNumberPw.length());
                switch (numPadNumberPw.length()) {
                    case 0:
                        iv_password_01.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                        break;
                    case 1:
                        iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                        break;
                    case 2:
                        iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                        break;
                    case 3:
                        iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                        break;
                    case 4:
                    default:
                        iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_full_01);
                        break;
                }


                if (numPadNumberPw.length() > 4) {
                    return;
                } else {
                    //tv_numPadPassword.setText(numPadNumberPw + "");
                    passwordText = numPadNumberPw + "";
                }


                break;

            default:
                break;
        }


    }


    @OnClick({R.id.btn_numPadBack, R.id.iv_back})
    public void backKeyClicked(View view) {


        onBackPressed();


    }

    @OnClick(R.id.btn_numPadInputFinish)
    public void btn_numPadInputFinishClicked() {
        if (type == LOGIN_INPUT_PHONE_NUMBER) {
            if (phoneText.length() == 0) {
                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "휴대폰 번호를 입력해 주세요.");
                loginMessageDialog.show();
            } else {
                setInputType(LOGIN_INPUT_PASSWORD);

            }


        } else if (type == LOGIN_INPUT_PASSWORD) {
            if (passwordText.length() < 4) {

                LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "비밀번호 4자리를 입력해 주세요.");
                loginMessageDialog.show();

            } else {
                setLoading(true);
                userApi.phoneLogin(phoneText, passwordText, phoneLoginCallback);
            }


        }
    }
    //endregion


    //region callback

    private PhoneLoginCallback phoneLoginCallback = new PhoneLoginCallback() {
        @Override
        public void onSuccess(int code, String msg) {

            userApi.getUserInfo(getUserInfoCallback);
        }

        @Override
        public void onError(int code, String msg) {
            setLoading(false);
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", msg);
            loginMessageDialog.show();

        }
    };

    private GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            // Toast.makeText(mParent, msg, Toast.LENGTH_SHORT).show();

                MyPreferenceManager.setString(mContext, "user_token", MyInfo.instance.getUser_token() + "");


            setLoading(false);
            LOG_I(MyInfo.instance.getUserInfo().toString());
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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