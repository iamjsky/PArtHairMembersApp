package kr.co.parthair.android.members.social.kakao

import android.content.Context
import kr.co.parthair.android.members.social.kakao.callback.KakaoLoginCheckCallback
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient


/**
 * ClassName            KakaoUserLoginCheck
 * Created by JSky on   2020-10-16
 *
 * Description          카카오 로그인 유무 확인
 */
class KakaoUserLoginCheck {

    private var context: Context;
    private lateinit var loginCheckCallback: KakaoLoginCheckCallback


    constructor(context: Context, loginCheckCallback:KakaoLoginCheckCallback){
        this.context = context;
        this.loginCheckCallback = loginCheckCallback;
    }



    // 토큰 유무 체크
    fun loginCheck(){
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (tokenInfo != null) {
                    loginCheckCallback.onError(error.toString());
                }
                else {
                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    loginCheckCallback.onSuccess(tokenInfo);
                }
            }
        }
        else {
            //로그인 필요
            loginCheckCallback.onError("need login");

        }



    }


}