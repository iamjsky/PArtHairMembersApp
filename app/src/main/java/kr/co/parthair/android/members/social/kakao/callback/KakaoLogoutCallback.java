package kr.co.parthair.android.members.social.kakao.callback;

import androidx.annotation.NonNull;

/**
 * ClassName            KakaoLoginCallback
 * Created by JSky on   2020-10-16
 * <p>
 * Description          카카오 로그인 콜백
 */
public interface KakaoLogoutCallback {
    void onSuccess();
    void onError(@NonNull Throwable throwable);
}
