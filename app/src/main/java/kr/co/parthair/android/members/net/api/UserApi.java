package kr.co.parthair.android.members.net.api;

import java.util.ArrayList;

import kr.co.parthair.android.members.common.HttpResponseCode;
import kr.co.parthair.android.members.common.MyConstants;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.model.CheckSignUp;
import kr.co.parthair.android.members.model.KakaoUserLogin;
import kr.co.parthair.android.members.model.KakaoUserSignUp;
import kr.co.parthair.android.members.model.PhoneUserLogin;
import kr.co.parthair.android.members.model.PhoneUserSignUp;
import kr.co.parthair.android.members.model.GetUserInfo;
import kr.co.parthair.android.members.net.RetrofitGenerator;
import kr.co.parthair.android.members.net.api.callback.CheckSignUpCallback;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.net.api.callback.KakaoUserLoginCallback;
import kr.co.parthair.android.members.net.api.callback.KakaoUserSignUpCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneLoginCallback;
import kr.co.parthair.android.members.net.api.callback.PhoneSignUpCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * ClassName            UserApi
 * Created by JSky on   2021-09-16
 * <p>
 * Description
 */
public class UserApi implements MyConstants, HttpResponseCode {
    private RetrofitGenerator retrofitGenerator = new RetrofitGenerator();
    private ApiService apiService = retrofitGenerator.getApiService();

    public void checkSignUp(int type, String value, CheckSignUpCallback callback) {

        apiService.checkSignUp(type, value).enqueue(new Callback<CheckSignUp>() {
            @Override
            public void onResponse(Call<CheckSignUp> call, Response<CheckSignUp> response) {
                if (response.isSuccessful()) {
                    CheckSignUp resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:
                            callback.onSuccess(code, msg);
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }




                } else {
                    callback.onError(SERVER_ERROR, "checkSignUp()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<CheckSignUp> call, Throwable t) {
                callback.onError(SERVER_ERROR, "checkSignUp()>>" + t.toString());
            }
        });


    }


    public void phoneSignUp(ArrayList<String> phoneSignUpDataList, PhoneSignUpCallback callback) {

        String user_phone = phoneSignUpDataList.get(0) + "";
        String phone_login_pw = phoneSignUpDataList.get(1) + "";
        String user_name = phoneSignUpDataList.get(2) + "";
        String user_email = phoneSignUpDataList.get(3) + "";

        apiService.phoneSignUp(user_phone, phone_login_pw, user_name, user_email).enqueue(new Callback<PhoneUserSignUp>() {
            @Override
            public void onResponse(Call<PhoneUserSignUp> call, Response<PhoneUserSignUp> response) {
                if (response.isSuccessful()) {
                    PhoneUserSignUp resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:
                            callback.onSuccess(code, msg);
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }




                } else {
                    callback.onError(SERVER_ERROR, "PhoneUserSignUp()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<PhoneUserSignUp> call, Throwable t) {
                callback.onError(SERVER_ERROR, "PhoneUserSignUp()>>" + t.toString());
            }
        });


    }

    public void phoneLogin(String user_phone, String phone_login_pw, PhoneLoginCallback callback) {

        MyInfo.instance.setUser_token("");

        apiService.phoneLogin(user_phone, phone_login_pw).enqueue(new Callback<PhoneUserLogin>() {
            @Override
            public void onResponse(Call<PhoneUserLogin> call, Response<PhoneUserLogin> response) {
                if (response.isSuccessful()) {
                    PhoneUserLogin resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();
                    String user_token = "";

                    switch (code) {
                        case OK:
                            user_token = resData.getHeader().getUser_token()+"";
                            MyInfo.instance.setUser_token(user_token);
                            callback.onSuccess(code, msg);
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                    LOG_D(MyInfo.instance.getUser_token()+"");


                } else {
                    callback.onError(SERVER_ERROR, "PhoneUserLogin()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<PhoneUserLogin> call, Throwable t) {
                callback.onError(SERVER_ERROR, "PhoneUserLogin()>>" + t.toString());
            }
        });


    }

    public void getUserInfo(GetUserInfoCallback callback) {

        String user_token = MyInfo.instance.getUser_token()+"";
       if(user_token.equals("")){
           callback.onError(-1, "TOKEN ERROR");
           LOG_E("TOKEN ERROR");
           return;
       }

        apiService.getUserInfo(user_token).enqueue(new Callback<GetUserInfo>() {
            @Override
            public void onResponse(Call<GetUserInfo> call, Response<GetUserInfo> response) {
                if (response.isSuccessful()) {
                    GetUserInfo resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();


                    switch (code) {
                        case OK:
                            MyInfo.instance.setUserInfo(resData.getUserInfo());
                            MyInfo.instance.setUser_token(resData.getUserInfo().getUserToken()+"");
                            callback.onSuccess(code, msg);
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }



                } else {
                    callback.onError(SERVER_ERROR, "getUserInfo()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<GetUserInfo> call, Throwable t) {
                callback.onError(SERVER_ERROR, "getUserInfo()>>" + t.toString());
            }
        });


    }


    public void kakaoSignUp(ArrayList<String> kakaoSignUpDataList, KakaoUserSignUpCallback callback) {

        String kakao_id = kakaoSignUpDataList.get(0) + "";
        String user_nickname = kakaoSignUpDataList.get(1) + "";
        String user_profile_img = kakaoSignUpDataList.get(2) + "";
        String user_phone = kakaoSignUpDataList.get(3) + "";
        String user_name = kakaoSignUpDataList.get(4) + "";
        String user_email = kakaoSignUpDataList.get(5) + "";

        apiService.kakaoSignUp(kakao_id, user_nickname, user_profile_img, user_phone, user_name, user_email).enqueue(new Callback<KakaoUserSignUp>() {
            @Override
            public void onResponse(Call<KakaoUserSignUp> call, Response<KakaoUserSignUp> response) {
                if (response.isSuccessful()) {
                    KakaoUserSignUp resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:
                            callback.onSuccess(code, msg);
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }




                } else {
                    callback.onError(SERVER_ERROR, "KakaoUserSignUp()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<KakaoUserSignUp> call, Throwable t) {
                callback.onError(SERVER_ERROR, "KakaoUserSignUp()>>" + t.toString());
            }
        });


    }

    public void kakaoLogin(String kakao_id, String user_nickname, String user_profile_img, KakaoUserLoginCallback callback) {

        MyInfo.instance.setUser_token("");

        apiService.kakaoLogin(kakao_id, user_nickname, user_profile_img).enqueue(new Callback<KakaoUserLogin>() {
            @Override
            public void onResponse(Call<KakaoUserLogin> call, Response<KakaoUserLogin> response) {
                if (response.isSuccessful()) {
                    KakaoUserLogin resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();
                    String user_token = "";

                    switch (code) {
                        case OK:
                            user_token = resData.getHeader().getUser_token()+"";
                            MyInfo.instance.setUser_token(user_token);
                            callback.onSuccess(code, msg);
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                    LOG_D(MyInfo.instance.getUser_token()+"");


                } else {
                    callback.onError(SERVER_ERROR, "KakaoUserLogin()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<KakaoUserLogin> call, Throwable t) {
                callback.onError(SERVER_ERROR, "KakaoUserLogin()>>" + t.toString());
            }
        });


    }

}
