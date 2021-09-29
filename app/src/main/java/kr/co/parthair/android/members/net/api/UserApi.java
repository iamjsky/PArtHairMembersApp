package kr.co.parthair.android.members.net.api;

import kr.co.parthair.android.members.common.HttpResponseCode;
import kr.co.parthair.android.members.common.MyConstants;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.model.CheckSignUp;
import kr.co.parthair.android.members.model.PhoneLogin;
import kr.co.parthair.android.members.model.PhoneSignUp;
import kr.co.parthair.android.members.model.GetUserInfo;
import kr.co.parthair.android.members.net.RetrofitGenerator;
import kr.co.parthair.android.members.net.api.callback.CheckSignUpCallback;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
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

    public void checkSignUp(String phoneNum, CheckSignUpCallback callback) {

        apiService.checkSignUp(phoneNum).enqueue(new Callback<CheckSignUp>() {
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


    public void phoneSignUp(String user_phone, String phone_login_pw, String user_name, PhoneSignUpCallback callback) {

        apiService.phoneSignUp(user_phone, phone_login_pw, user_name).enqueue(new Callback<PhoneSignUp>() {
            @Override
            public void onResponse(Call<PhoneSignUp> call, Response<PhoneSignUp> response) {
                if (response.isSuccessful()) {
                    PhoneSignUp resData = response.body();
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
                    callback.onError(SERVER_ERROR, "phoneSignUp()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<PhoneSignUp> call, Throwable t) {
                callback.onError(SERVER_ERROR, "phoneSignUp()>>" + t.toString());
            }
        });


    }

    public void phoneLogin(String user_phone, String phone_login_pw, PhoneLoginCallback callback) {

        MyInfo.instance.setUser_token("");

        apiService.phoneLogin(user_phone, phone_login_pw).enqueue(new Callback<PhoneLogin>() {
            @Override
            public void onResponse(Call<PhoneLogin> call, Response<PhoneLogin> response) {
                if (response.isSuccessful()) {
                    PhoneLogin resData = response.body();
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
                    callback.onError(SERVER_ERROR, "phoneLogin()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<PhoneLogin> call, Throwable t) {
                callback.onError(SERVER_ERROR, "phoneLogin()>>" + t.toString());
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

}
