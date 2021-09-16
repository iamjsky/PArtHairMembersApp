package kr.co.parthair.android.members.social.kakao.callback;

import androidx.annotation.NonNull;

import com.kakao.sdk.talk.model.TalkProfile;

/**
 * ClassName            KakaoGetUserProfileCallback
 * Created by JSky on   2020-10-16
 * <p>
 * Description
 */
public interface KakaoGetUserProfileCallback {
    void onSuccess(TalkProfile userProfile);
    void onError(@NonNull Throwable throwable);
}
