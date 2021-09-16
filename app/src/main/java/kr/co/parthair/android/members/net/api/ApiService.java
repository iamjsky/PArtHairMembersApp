package kr.co.parthair.android.members.net.api;

import kr.co.parthair.android.members.model.CheckSignUp;
import kr.co.parthair.android.members.model.PhoneLogin;
import kr.co.parthair.android.members.model.PhoneSignUp;
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
    Call<PhoneSignUp> phoneSignUp(
            @Field("user_phone") String user_phone,
            @Field("phone_login_pw") String phone_login_pw,
            @Field("user_name") String user_name



    );

    @FormUrlEncoded
    @POST("phoneLogin.php")
    Call<PhoneLogin> phoneLogin(
            @Field("user_phone") String user_phone,
            @Field("phone_login_pw") String phone_login_pw



    );

    @FormUrlEncoded
    @POST("getUserInfo.php")
    Call<GetUserInfo> getUserInfo(
            @Field("user_token") String user_token



    );
}
