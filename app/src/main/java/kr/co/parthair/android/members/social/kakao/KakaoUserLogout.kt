package kr.co.parthair.android.members.social.kakao

import android.content.Context
import android.util.Log
import kr.co.parthair.android.members.data.MyConstants.TAG
import kr.co.parthair.android.members.social.kakao.callback.KakaoLogoutCallback
import com.kakao.sdk.user.UserApiClient


/**
 * ClassName            KakaoUserLogout
 * Created by JSky on   2020-10-16
 *
 * Description          카카오 계정 로그아웃
 */
class KakaoUserLogout {

    private var context: Context;
    private lateinit var logoutCallback: KakaoLogoutCallback


    constructor(context: Context, logoutCallback:KakaoLogoutCallback){
        this.context = context;
        this.logoutCallback = logoutCallback;
    }



    // 로그아웃
    fun logout(){

        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                logoutCallback.onError(error)
            }
            else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                logoutCallback.onSuccess()
            }
        }



    }


}