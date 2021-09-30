package kr.co.parthair.android.members.net.api.callback;

/**
 * ClassName            KakaoLoginCallback
 * Created by JSky on   2021-09-30
 * <p>
 * Description
 */
public interface KakaoUserLoginCallback {
    void onSuccess(int code, String msg);
    void onError(int code, String msg);
}
