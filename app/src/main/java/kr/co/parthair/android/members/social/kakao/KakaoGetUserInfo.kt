package kr.co.parthair.android.members.social.kakao

import android.content.Context
import android.util.Log
import kr.co.parthair.android.members.common.MyConstants.TAG
import kr.co.parthair.android.members.social.kakao.callback.KakaoGetUserInfoCallback


import com.kakao.sdk.user.UserApiClient


/**
 * ClassName            KakaoGetUserInfo
 * Created by JSky on   2020-10-16
 *
 * Description
 */
class KakaoGetUserInfo {

    private var context: Context
    private lateinit var getUserInfoCallback: KakaoGetUserInfoCallback



    constructor(context: Context, getUserInfoCallback: KakaoGetUserInfoCallback){
        this.context = context;
        this.getUserInfoCallback = getUserInfoCallback;
    }


    fun getUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
                getUserInfoCallback.onError(error)
            } else if (user != null) {
                Log.i(TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                getUserInfoCallback.onSuccess(user)
            }
        }
    }




}