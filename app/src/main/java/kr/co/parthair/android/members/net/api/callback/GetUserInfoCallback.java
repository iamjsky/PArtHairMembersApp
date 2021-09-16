package kr.co.parthair.android.members.net.api.callback;

/**
 * ClassName            UserInfoCallback
 * Created by JSky on   2021-09-16
 * <p>
 * Description
 */
public interface GetUserInfoCallback {
    void onSuccess(int code, String msg);
    void onError(int code, String msg);
}
