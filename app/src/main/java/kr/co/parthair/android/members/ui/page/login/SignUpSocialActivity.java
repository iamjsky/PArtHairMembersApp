package kr.co.parthair.android.members.ui.page.login;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.model.KakaoUserSignUp;
import kr.co.parthair.android.members.net.api.callback.KakaoUserSignUpCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneSignUpCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;

import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;

public class SignUpSocialActivity extends BaseActivity {



    @BindView(R.id.tv_userPhoneNumber)
    TextView tv_userPhoneNumber;
    @BindView(R.id.edtxt_userPhoneNumber)
    EditText edtxt_userPhoneNumber;
    @BindView(R.id.tv_userPassword)
    TextView tv_userPassword;
    @BindView(R.id.edtxt_userPassword)
    EditText edtxt_userPassword;








    @BindView(R.id.layout_kakao_signup_info)
    RelativeLayout layout_kakao_signup_info;

    int login_type = -1;
    String _kakao_id = "";
    String _kakao_nickname = "";
    String _kakao_profile_img = "";
    String user_name = "";
    private static final int PERMISSIONS_REQUEST_CODE = 22;
    private boolean is_Permission = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_social);
        ButterKnife.bind(this);

        if(getIntent() != null){
            Intent intent = new Intent();
            intent = getIntent();
            login_type = intent.getIntExtra("login_type", -1);
            user_name = intent.getStringExtra("user_name");
            _kakao_id = intent.getStringExtra("kakao_id");
            _kakao_nickname = intent.getStringExtra("kakao_nickname");
            _kakao_profile_img = intent.getStringExtra("kakao_profile_img");
        }


        init();
    }

    void setSocialSignUpInterface(){

        Window w = getWindow();
        if(login_type == LOGIN_TYPE_KAKAO){
            layout_kakao_signup_info.setVisibility(View.VISIBLE);
            w.setStatusBarColor(getResources().getColor(R.color.kakao));
        }else{

        }

    }
    void init(){

        setSocialSignUpInterface();


        edtxt_userPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    String phoneNum = edtxt_userPhoneNumber.getText()+"";
                    phoneNum = phoneNum.replace("-","");
                    edtxt_userPhoneNumber.setText(phoneNum);
                    tv_userPhoneNumber.setTextColor(getColor(R.color.ebay_blue));
                    edtxt_userPhoneNumber.setTextColor(getColor(R.color.ebay_blue));

                }else{
                    tv_userPhoneNumber.setTextColor(getColor(R.color.ph_main_color));
                    edtxt_userPhoneNumber.setTextColor(getColor(R.color.ph_main_color));
                    String phoneNum = PhoneNumberUtils.formatNumber(edtxt_userPhoneNumber.getText()+"", Locale.getDefault().getCountry()) + "";
                    edtxt_userPhoneNumber.setText(phoneNum);
                }
            }
        });
        edtxt_userPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tv_userPassword.setTextColor(getColor(R.color.ebay_blue));
                    edtxt_userPassword.setTextColor(getColor(R.color.ebay_blue));

                }else{
                    tv_userPassword.setTextColor(getColor(R.color.ph_main_color));
                    edtxt_userPassword.setTextColor(getColor(R.color.ph_main_color));
                }
            }
        });


        is_Permission = checkPhoneNumberPermission();
        LOG_I("퍼미션 체크 값은? : "+is_Permission);
        if (is_Permission) {
            LOG_I("체크 퍼미션 승인");
            // 휴대폰 정보는 TelephonyManager 를 이용
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


            // READ_PHONE_NUMBERS 또는 READ_PHONE_STATE 권한을 허가 받았는지 확인
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                LOG_I("넘버 스테이트 권한 확인 / 권한 실패");
                return;
            }
            String phoneNum = tm.getLine1Number()+"";
            phoneNum = PhoneNumberUtils.formatNumber(phoneNum, Locale.getDefault().getCountry()) + "";
            edtxt_userPhoneNumber.setText(phoneNum+"");



//            LOG_I( "음성통화 상태 : [ getCallState ] >>> " + tm.getCallState());
//            LOG_I( "데이터통신 상태 : [ getDataState ] >>> " + tm.getDataState());
//            LOG_I( "전화번호 : [ getLine1Number ] >>> " + tm.getLine1Number());
//            LOG_I( "통신사 ISO 국가코드 : [ getNetworkCountryIso ] >>> "+tm.getNetworkCountryIso());
//            LOG_I("통신사 ISO 국가코드 : [ getSimCountryIso ] >>> "+tm.getSimCountryIso());
//            LOG_I("망사업자 MCC+MNC : [ getNetworkOperator ] >>> "+tm.getNetworkOperator());
//            LOG_I("망사업자 MCC+MNC : [ getSimOperator ] >>> "+tm.getSimOperator());
//            LOG_I("망사업자명 : [ getNetworkOperatorName ] >>> "+tm.getNetworkOperatorName());
//            LOG_I("망사업자명 : [ getSimOperatorName ] >>> "+tm.getSimOperatorName());
//            LOG_I("SIM 카드 상태 : [ getSimState ] >>> "+tm.getSimState());
//
//            // 유니크한 단말 번호 >>> Android ID 사용
//            @SuppressLint("HardwareIds")
//            String android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
//            LOG_I("Android_ID >>> "+android_id);
        }
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();


    }

    boolean checkInputField(){
        String socialSignUpPhoneNumber = edtxt_userPhoneNumber.getText().toString() + "";
        socialSignUpPhoneNumber = socialSignUpPhoneNumber.replace("-","");
        String socialSignUpPassword = edtxt_userPassword.getText().toString() + "";


        if (!String_IsNotNull(socialSignUpPhoneNumber)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "휴대폰 번호를 입력해 주세요.");
            loginMessageDialog.show();
            return false;

        }

        if (!String_IsNotNull(socialSignUpPassword)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "알림", "비밀번호를 입력해 주세요.");
            loginMessageDialog.show();
            return false;

        }

        return true;
    }
    boolean checkPhoneNumberPermission(){

        // 위험 권한을 모두 승인했는지 여부
        boolean mPermissionsGranted = false;
        String[] mRequiredPermissions = new String[1];
        // 승인 받기 위한 권한 목록
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            mRequiredPermissions[0] = Manifest.permission.READ_PHONE_NUMBERS;

        }else{
            mRequiredPermissions[0] = Manifest.permission.READ_PRECISE_PHONE_STATE;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 필수 권한을 가지고 있는지 확인한다.
            mPermissionsGranted = hasPermissions(mRequiredPermissions);

            // 필수 권한 중에 한 개라도 없는 경우
            if (!mPermissionsGranted) {
                // 권한을 요청한다.
                ActivityCompat.requestPermissions(this, mRequiredPermissions, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            mPermissionsGranted = true;
        }


        return mPermissionsGranted;








    }


    public boolean hasPermissions(String[] permissions) {
        // 필수 권한을 가지고 있는지 확인한다.
        for (String permission : permissions) {
            if (checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    void socialSignUpFinish(){
        Window w = getWindow();
        if(login_type == LOGIN_TYPE_KAKAO) {
            layout_kakao_signup_info.setVisibility(View.GONE);
            w.setStatusBarColor(getResources().getColor(R.color.ph_page_bg_color));



            Intent intent = new Intent(mContext, SignUpFinishActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("login_type", LOGIN_TYPE_KAKAO);
            intent.putExtra("user_profile_img", _kakao_profile_img);
            intent.putExtra("user_name", user_name);
            startActivity(intent);


        }
    }
    //region onClick


    @OnClick(R.id.iv_back)
    public void iv_backClicked(){
        onBackPressed();
    }


    @OnClick(R.id.btn_kakaoLogin)
    public void btn_kakaoLoginClicked(){
        if(checkInputField()){

            String kakao_id = _kakao_id + "";
            String user_nickname = _kakao_nickname + "";
            String user_profile_img = _kakao_profile_img + "";
            String user_phone = edtxt_userPhoneNumber.getText().toString() + "";
            user_phone = user_phone.replace("-","");
            String phone_login_pw = edtxt_userPassword.getText().toString() + "";


            userApi.kakaoSignUp(kakao_id, user_nickname, user_profile_img, user_phone, phone_login_pw, kakaoUserSignUpCallback);
        }

    }



    //endregion

    //region callback

    private KakaoUserSignUpCallback kakaoUserSignUpCallback = new KakaoUserSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg, String userName) {
            setLoading(false);
            user_name = userName +"";
            socialSignUpFinish();


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