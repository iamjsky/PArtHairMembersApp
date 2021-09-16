package kr.co.parthair.android.members.social.kakao.callback;

import androidx.annotation.NonNull;

import com.kakao.sdk.user.model.User;

/**
 * ClassName            KakaoGetUserInfoCallback
 * Created by JSky on   2020-10-16
 * <p>
 * Description
 */
public interface KakaoGetUserInfoCallback {
    void onSuccess(User user);
    void onError(@NonNull Throwable throwable);
}
