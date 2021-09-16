package kr.co.parthair.android.members.social.kakao.callback;

import androidx.annotation.NonNull;

import com.kakao.sdk.link.model.LinkResult;

/**
 * ClassName            KakaoSendLinkMsgCallback
 * Created by JSky on   2020-10-16
 * <p>
 * Description          카
 */
public interface KakaoSendLinkMsgCallback {
    void onSuccess(LinkResult linkResult);
    void onError(@NonNull Throwable throwable);
}
