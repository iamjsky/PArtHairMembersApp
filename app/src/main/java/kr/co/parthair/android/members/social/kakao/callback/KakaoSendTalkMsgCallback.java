package kr.co.parthair.android.members.social.kakao.callback;

import androidx.annotation.NonNull;

/**
 * ClassName            KakaoSendTalkMsgCallback
 * Created by JSky on   2020-10-16
 * <p>
 * Description
 */
public interface KakaoSendTalkMsgCallback {
    void onSuccess(String msg);
    void onError(@NonNull Throwable throwable);
}
