package kr.co.parthair.android.members.social.kakao

import android.content.Context
import android.util.Log
import kr.co.parthair.android.members.common.MyConstants.TAG
import kr.co.parthair.android.members.social.kakao.callback.KakaoLoginCallback

import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient


/**
 * ClassName            KakaoUserLogin
 * Created by JSky on   2020-10-16
 *
 * Description          카카오 로그인
 */
class KakaoUserLogin {

    private var context: Context;
    private lateinit var loginCallback: KakaoLoginCallback


    constructor(context: Context, loginCallback:KakaoLoginCallback){
        this.context = context;
        this.loginCallback = loginCallback;
    }

    // 로그인 공통 callback 구성
     val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패", error)
            loginCallback.onError(error);
        }
        else if (token != null) {
            Log.d(TAG, "로그인 성공 ${token.accessToken}")

            loginCallback.onSuccess(token.accessToken);



        }

    }

    // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
    fun login(){
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }


}