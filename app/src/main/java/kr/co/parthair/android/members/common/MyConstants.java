package kr.co.parthair.android.members.common;

import android.util.Log;

/**
 * ClassName            MyConstants
 * Created by JSky on   2021-09-16
 * <p>
 * Description
 */
public interface MyConstants {

    String TAG = "ph_debug";
    boolean DEBUG_MODE = true;

    int LOGIN_TYPE_PHONE = 0;
    int LOGIN_TYPE_KAKAO = 1;
    int LOGIN_TYPE_LINKED = 9;

    String NUMPAD_PHONE_lOGIN_PHONE = "login_phone";
    String NUMPAD_PHONE_LOGIN_PASSWORD = "login_password";
    String NUMPAD_PHONE_SIGNUP_PHONE = "signup_phone";
    String NUMPAD_PHONE_SIGNUP_PASSWORD = "signup_password";
    String NUMPAD_PHONE_LOGIN_PASSWORD_CHECK = "signup_passwordChk";



    default void LOG_D(String msg){
        if(DEBUG_MODE){
            Log.d(TAG, msg);
        }
    }
    default void LOG_I(String msg){
        if(DEBUG_MODE){
            Log.i(TAG, msg);
        }
    }
    default void LOG_E(String msg){
        if(DEBUG_MODE){
            Log.e(TAG, msg);
        }
    }

}
