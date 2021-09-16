package kr.co.parthair.android.members.net.api.callback;

/**
 * ClassName            CheckSignUpCallback
 * Created by JSky on   2021-09-16
 * <p>
 * Description
 */
public interface CheckSignUpCallback {
    void onSuccess(int code, String msg);
    void onError(int code, String msg);
}
