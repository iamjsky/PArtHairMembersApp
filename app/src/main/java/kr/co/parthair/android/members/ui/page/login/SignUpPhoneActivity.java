package kr.co.parthair.android.members.ui.page.login;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.net.api.callback.PhoneSignUpCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.login.dialog.LoginMessageDialog;

import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;

public class SignUpPhoneActivity extends BaseActivity {


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




    @BindView(R.id.sv_policyBody)
    NestedScrollView sv_policyBody;

    @BindView(R.id.layout_policyTopMenu)
    LinearLayout layout_policyTopMenu;
    @BindView(R.id.sv_privacyBody)
    NestedScrollView sv_privacyBody;

    @BindView(R.id.layout_privacyTopMenu)
    LinearLayout layout_privacyTopMenu;

    private static final int PERMISSIONS_REQUEST_CODE = 22;
    private boolean is_Permission = false;

    String user_phone = "";
    String phone_login_pw = "";
    String user_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_phone);
        ButterKnife.bind(this);
        init();

    }

    void init(){
        layout_policy.setVisibility(View.VISIBLE);

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
                    String userPhoneNumber = edtxt_userPhoneNumber.getText().toString() + "";
                    if(userPhoneNumber.length() > 0){
                        tv_userPhoneNumber.setTextColor(getColor(R.color.ph_main_color));
                    }else{
                        tv_userPhoneNumber.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    }

                    edtxt_userPhoneNumber.setTextColor(getColor(R.color.ph_main_color));
                    String phoneNum = PhoneNumberUtils.formatNumber(edtxt_userPhoneNumber.getText()+"", Locale.getDefault().getCountry()) + "";
                    edtxt_userPhoneNumber.setText(phoneNum);
                }
            }
        });

        edtxt_userPhoneNumber.requestFocus();

          edtxt_userPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    tv_userPassword.setTextColor(getColor(R.color.ebay_blue));
                    edtxt_userPassword.setTextColor(getColor(R.color.ebay_blue));

                }else{
                    String userPassword = edtxt_userPassword.getText().toString() + "";

                    if(userPassword.length() > 0){
                        tv_userPassword.setTextColor(getColor(R.color.ph_main_color));
                    }else{
                        tv_userPassword.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    }
                    edtxt_userPassword.setTextColor(getColor(R.color.ph_main_color));
                }
            }
        });
        edtxt_userPasswordChk.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    tv_userPasswordChk.setTextColor(getColor(R.color.ebay_blue));
                    edtxt_userPasswordChk.setTextColor(getColor(R.color.ebay_blue));

                }else{
                    String userPasswordChk = edtxt_userPasswordChk.getText().toString() + "";
                    if(userPasswordChk.length() > 0){
                        tv_userPasswordChk.setTextColor(getColor(R.color.ph_main_color));
                    }else{
                        tv_userPasswordChk.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    }
                    edtxt_userPasswordChk.setTextColor(getColor(R.color.ph_main_color));
                }
            }
        });
        edtxt_userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                     sv_body.post(new Runnable() {
                        @Override
                        public void run() {
                            sv_body.scrollTo(0, sv_body.getBottom());
                        }
                    });
                    tv_userName.setTextColor(getColor(R.color.ebay_blue));
                    edtxt_userName.setTextColor(getColor(R.color.ebay_blue));

                }else{
                    String userName = edtxt_userName.getText().toString() + "";
                    if(userName.length() > 0){
                        tv_userName.setTextColor(getColor(R.color.ph_main_color));
                    }else{
                        tv_userName.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    }
                    edtxt_userName.setTextColor(getColor(R.color.ph_main_color));
                }
            }
        });
        edtxt_userEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    sv_body.post(new Runnable() {
                        @Override
                        public void run() {
                            sv_body.scrollTo(0, sv_body.getBottom());
                        }
                    });
                    tv_userEmail.setTextColor(getColor(R.color.ebay_blue));
                    edtxt_userEmail.setTextColor(getColor(R.color.ebay_blue));

                }else{
                    String userEmail = edtxt_userEmail.getText().toString() + "";
                    if(userEmail.length() > 0){
                        tv_userEmail.setTextColor(getColor(R.color.ph_main_color));
                    }else{
                        tv_userEmail.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    }
                    edtxt_userEmail.setTextColor(getColor(R.color.ph_main_color));
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

        is_Permission = checkPhoneNumberPermission();
        LOG_I("????????? ?????? ??????? : "+is_Permission);
        if (is_Permission) {
            LOG_I("?????? ????????? ??????");
            // ????????? ????????? TelephonyManager ??? ??????
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


            // READ_PHONE_NUMBERS ?????? READ_PHONE_STATE ????????? ?????? ???????????? ??????
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                LOG_I("?????? ???????????? ?????? ?????? / ?????? ??????");
                return;
            }
            String phoneNum = tm.getLine1Number()+"";
            phoneNum = PhoneNumberUtils.formatNumber(phoneNum, Locale.getDefault().getCountry()) + "";
            edtxt_userPhoneNumber.setText(phoneNum+"");



//            LOG_I( "???????????? ?????? : [ getCallState ] >>> " + tm.getCallState());
//            LOG_I( "??????????????? ?????? : [ getDataState ] >>> " + tm.getDataState());
//            LOG_I( "???????????? : [ getLine1Number ] >>> " + tm.getLine1Number());
//            LOG_I( "????????? ISO ???????????? : [ getNetworkCountryIso ] >>> "+tm.getNetworkCountryIso());
//            LOG_I("????????? ISO ???????????? : [ getSimCountryIso ] >>> "+tm.getSimCountryIso());
//            LOG_I("???????????? MCC+MNC : [ getNetworkOperator ] >>> "+tm.getNetworkOperator());
//            LOG_I("???????????? MCC+MNC : [ getSimOperator ] >>> "+tm.getSimOperator());
//            LOG_I("??????????????? : [ getNetworkOperatorName ] >>> "+tm.getNetworkOperatorName());
//            LOG_I("??????????????? : [ getSimOperatorName ] >>> "+tm.getSimOperatorName());
//            LOG_I("SIM ?????? ?????? : [ getSimState ] >>> "+tm.getSimState());
//
//            // ???????????? ?????? ?????? >>> Android ID ??????
//            @SuppressLint("HardwareIds")
//            String android_id = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
//            LOG_I("Android_ID >>> "+android_id);
        }
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
        phoneSignUpPhoneNumber = phoneSignUpPhoneNumber.replace("-","");
        String phoneSignUpPassword = edtxt_userPassword.getText().toString() + "";
        String phoneSignUpPasswordChk = edtxt_userPasswordChk.getText().toString() + "";
        String phoneSignUpName = edtxt_userName.getText().toString() + "";
        String phoneSignUpEmail = edtxt_userEmail.getText().toString() + "";

        if (!String_IsNotNull(phoneSignUpPhoneNumber)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "??????", "????????? ????????? ????????? ?????????.");
            loginMessageDialog.show();
            return false;

        }
        if (!String_IsNotNull(phoneSignUpPassword)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "??????", "??????????????? ????????? ?????????.");
            loginMessageDialog.show();
            return false;

        }
        if (phoneSignUpPassword.length() != 4) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "??????", "???????????? 4????????? ????????? ?????????.");
            loginMessageDialog.show();
            return false;

        }
        if (!String_IsNotNull(phoneSignUpPasswordChk)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "??????", "???????????? ????????? ????????? ?????????.");
            loginMessageDialog.show();
            return false;

        }
        if (phoneSignUpPasswordChk.length() != 4) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "??????", "???????????? ?????? 4????????? ????????? ?????????.");
            loginMessageDialog.show();
            return false;

        }
        if (!phoneSignUpPassword.equals(phoneSignUpPasswordChk)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "??????", "??????????????? ????????? ???????????? ????????????.");
            loginMessageDialog.show();
            return false;

        }
        if (!String_IsNotNull(phoneSignUpName)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "??????", "????????? ????????? ?????????.");
            loginMessageDialog.show();
            return false;

        }
        if(!phoneSignUpName.matches("[a-z|A-Z|???-???|???-???|???-???| ]*")) {
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "??????", "????????? ???????????? ????????? ?????????.");
            loginMessageDialog.show();
            return false;
        }
        
        if (!String_IsNotNull(phoneSignUpEmail)) {

            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "??????", "????????? / ???????????? ????????? ?????????\n???????????? ????????? ?????????.");
            loginMessageDialog.show();
            return false;

        }

        return true;
    }


    boolean checkPhoneNumberPermission(){
        
           // ?????? ????????? ?????? ??????????????? ??????
                boolean mPermissionsGranted = false;
                String[] mRequiredPermissions = new String[1];
                // ?????? ?????? ?????? ?????? ??????
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    mRequiredPermissions[0] = Manifest.permission.READ_PHONE_NUMBERS;

                }else{
                    mRequiredPermissions[0] = Manifest.permission.READ_PRECISE_PHONE_STATE;
                }


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // ?????? ????????? ????????? ????????? ????????????.
                    mPermissionsGranted = hasPermissions(mRequiredPermissions);

                    // ?????? ?????? ?????? ??? ????????? ?????? ??????
                    if (!mPermissionsGranted) {
                        // ????????? ????????????.
                        ActivityCompat.requestPermissions(this, mRequiredPermissions, PERMISSIONS_REQUEST_CODE);
                    }
                } else {
                    mPermissionsGranted = true;
                }
       

                return mPermissionsGranted;
                

      
              

           
        

    }


    public boolean hasPermissions(String[] permissions) {
        // ?????? ????????? ????????? ????????? ????????????.
        for (String permission : permissions) {
            if (checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
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
            phoneSignUpPhoneNumber = phoneSignUpPhoneNumber.replace("-","");
            String phoneSignUpPassword = edtxt_userPassword.getText().toString() + "";
            String phoneSignUpName = edtxt_userName.getText().toString() + "";
            String phoneSignUpEmail = edtxt_userEmail.getText().toString() + "";
            user_phone = phoneSignUpPhoneNumber + "";
            phone_login_pw = phoneSignUpPassword + "";
            user_name = phoneSignUpName + "";

            userApi.phoneSignUp(phoneSignUpPhoneNumber, phoneSignUpPassword, phoneSignUpName, phoneSignUpEmail, phoneSignUpCallback);
        }

    }



    //endregion

    //region callback

    private PhoneSignUpCallback phoneSignUpCallback = new PhoneSignUpCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            setLoading(false);

            Intent intent = new Intent(mContext, SignUpFinishActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("login_type", LOGIN_TYPE_PHONE);
            intent.putExtra("user_phone", user_phone);
            intent.putExtra("user_name", user_name);
            intent.putExtra("phone_login_pw", phone_login_pw);

            startActivity(intent);

        }

        @Override
        public void onError(int code, String msg) {
            setLoading(false);
            user_phone = "";
            phone_login_pw = "";
            user_name = "";
            LoginMessageDialog loginMessageDialog = new LoginMessageDialog(mContext, "??????", msg);
            loginMessageDialog.show();
        }
    };


    //endregion


    
}