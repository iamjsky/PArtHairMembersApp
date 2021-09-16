package kr.co.parthair.android.members.net.api.callback;

/**
 * ClassName            PhoneLoginCallback
 * Created by JSky on   2021-09-16
 * <p>
 * Description
 */
public interface PhoneLoginCallback {
    void onSuccess(int code, String msg);
    void onError(int code, String msg);
}
