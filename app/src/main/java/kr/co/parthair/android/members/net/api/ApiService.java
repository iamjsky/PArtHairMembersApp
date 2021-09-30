package kr.co.parthair.android.members.net.api;

import kr.co.parthair.android.members.model.CheckSignUp;
import kr.co.parthair.android.members.model.KakaoUserLogin;
import kr.co.parthair.android.members.model.KakaoUserSignUp;
import kr.co.parthair.android.members.model.PhoneUserLogin;
import kr.co.parthair.android.members.model.PhoneUserSignUp;
import kr.co.parthair.android.members.model.GetUserInfo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * ClassName            ApiService
 * Created by JSky on   2021-09-16
 * <p>
 * Description
 */
public interface ApiService {




    @FormUrlEncoded
    @POST("checkSignUp.php")
    Call<CheckSignUp> checkSignUp(
            @Field("type") int type,
            @Field("value") String value



    );


    @FormUrlEncoded
    @POST("phoneSignUp.php")
    Call<PhoneUserSignUp> phoneSignUp(
            @Field("user_phone") String user_phone,
            @Field("phone_login_pw") String phone_login_pw,
            @Field("user_name") String user_name,
            @Field("user_email") String user_email



    );

    @FormUrlEncoded
    @POST("phoneLogin.php")
    Call<PhoneUserLogin> phoneLogin(
            @Field("user_phone") String user_phone,
            @Field("phone_login_pw") String phone_login_pw



    );

    @FormUrlEncoded
    @POST("getUserInfo.php")
    Call<GetUserInfo> getUserInfo(
            @Field("user_token") String user_token



    );

    @FormUrlEncoded
    @POST("kakaoSignUp.php")
    Call<KakaoUserSignUp> kakaoSignUp(
            @Field("kakao_id") String kakao_id,
            @Field("user_nickname") String user_nickname,
            @Field("user_profile_img") String user_profile_img,
            @Field("user_phone") String user_phone,
            @Field("user_name") String user_name,
            @Field("user_email") String user_email





    );

    @FormUrlEncoded
    @POST("kakaoLogin.php")
    Call<KakaoUserLogin> kakaoLogin(
            @Field("kakao_id") String kakao_id,
            @Field("user_nickname") String user_nickname,
            @Field("user_profile_img") String user_profile_img



    );
}
