package kr.co.parthair.android.members.social.kakao

import android.content.Context
import android.util.Log
import kr.co.parthair.android.members.common.MyConstants.TAG
import kr.co.parthair.android.members.social.kakao.callback.KakaoGetUserProfileCallback
import com.kakao.sdk.talk.TalkApiClient


/**
 * ClassName            KakaoGetUserProfile
 * Created by JSky on   2021-01-28
 *
 * Description
 */
class KakaoGetUserProfile  {
    private var context: Context;
    private lateinit var getUserProfileCallback: KakaoGetUserProfileCallback


    constructor(context: Context, getUserProfileCallback: KakaoGetUserProfileCallback){
        this.context = context;
        this.getUserProfileCallback = getUserProfileCallback;
    }
    fun getProfile(){
        // 카카오톡 프로필 가져오기
        TalkApiClient.instance.profile { profile, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡 프로필 가져오기 실패", error)
            }
            else if (profile != null) {
                Log.i(TAG, "카카오톡 프로필 가져오기 성공" +
                        "\n닉네임: ${profile.nickname}" +
                        "\n프로필사진: ${profile.thumbnailUrl}" +
                        "\n국가코드: ${profile.countryISO}")
                getUserProfileCallback.onSuccess(profile)
            }
        }
    }



}