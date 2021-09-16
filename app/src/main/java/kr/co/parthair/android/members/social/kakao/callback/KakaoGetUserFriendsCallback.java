package kr.co.parthair.android.members.social.kakao.callback;

import androidx.annotation.NonNull;

import com.kakao.sdk.talk.model.Friend;

import java.util.List;

/**
 * ClassName            KakaoGetUserFriendsCallback
 * Created by JSky on   2021-02-01
 * <p>
 * Description
 */
public interface KakaoGetUserFriendsCallback {

        void onSuccess(List<Friend> friends);
        void onError(@NonNull Throwable throwable);
}
