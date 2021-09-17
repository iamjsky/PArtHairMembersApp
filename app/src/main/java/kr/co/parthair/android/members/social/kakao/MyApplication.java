package kr.co.parthair.android.members.social.kakao;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

/**
 * ClassName            MyApplication
 * Created by JSky on   2021-01-28
 * <p>
 * Description
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getMyApplicationContext() {
        if (instance == null) {
            throw new IllegalStateException("This Application does not inherit ");
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;




        // Kakao Sdk 초기화
        KakaoSdk.init(this, "eb98ac0118f296e66e0db7a2682b8b57");



    }
}
