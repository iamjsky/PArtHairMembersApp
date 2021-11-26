package kr.co.parthair.android.members.net.api;

import kr.co.parthair.android.members.common.HttpResponseCode;
import kr.co.parthair.android.members.common.MyConstants;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.model.BusinessHour;
import kr.co.parthair.android.members.model.MyReservation;
import kr.co.parthair.android.members.model.NewsDataModel;
import kr.co.parthair.android.members.model.ReservationInfo;
import kr.co.parthair.android.members.net.RetrofitGenerator;
import kr.co.parthair.android.members.net.api.callback.GetBusinessHourCallback;
import kr.co.parthair.android.members.net.api.callback.GetMyReservationCallback;
import kr.co.parthair.android.members.net.api.callback.GetNewsCallback;
import kr.co.parthair.android.members.net.api.callback.GetReservationInfoCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ClassName            ReservationApi
 * Created by JSky on   2021-11-24
 * <p>
 * Description
 */
public class ReservationApi implements MyConstants, HttpResponseCode {
    private RetrofitGenerator retrofitGenerator = new RetrofitGenerator();
    private ApiService apiService = retrofitGenerator.getApiService();


    public void getReservationInfo(GetReservationInfoCallback callback) {


        apiService.getReservationInfo().enqueue(new Callback<ReservationInfo>() {
            @Override
            public void onResponse(Call<ReservationInfo> call, Response<ReservationInfo> response) {
                if (response.isSuccessful()) {
                    ReservationInfo resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:

                            callback.onSuccess(code, msg, resData.getReservationDataList(), resData.getBlockTimeDataList());

                            break;
                        case NO_CONTENT:
                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                } else {
                    callback.onError(SERVER_ERROR, "getReservationInfo()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<ReservationInfo> call, Throwable t) {
                callback.onError(SERVER_ERROR, "getReservationInfo()>>" + t.toString());
            }
        });


    }

    public void getMyReservation(GetMyReservationCallback callback) {
        String user_token = MyInfo.instance.getUser_token()+"";
        if(user_token.equals("")){
            callback.onError(-1, "TOKEN ERROR");
            // LOG_E("TOKEN ERROR");
            return;
        }

        apiService.getMyReservation(user_token).enqueue(new Callback<MyReservation>() {
            @Override
            public void onResponse(Call<MyReservation> call, Response<MyReservation> response) {
                if (response.isSuccessful()) {
                    MyReservation resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:


                            if(resData.getMyReservationDataList().size() > 0){
                                if(resData.getMyReservationDataList().get(0).getState() == 0){
                                    callback.onSuccess(code, msg, resData.getMyReservationDataList());
                                }else{
                                    callback.onError(code, "예약 내역이 없습니다.");
                                }

                            }else{
                                callback.onError(code, "예약 내역이 없습니다.");
                            }

                            break;
                        case NO_CONTENT:
                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                } else {
                    callback.onError(SERVER_ERROR, "getMyReservation()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<MyReservation> call, Throwable t) {
                callback.onError(SERVER_ERROR, "getMyReservation()>>" + t.toString());
            }
        });


    }

    public void getBusinessHour(GetBusinessHourCallback callback) {


        apiService.getBusinessHour().enqueue(new Callback<BusinessHour>() {
            @Override
            public void onResponse(Call<BusinessHour> call, Response<BusinessHour> response) {
                if (response.isSuccessful()) {
                    BusinessHour resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:

                            callback.onSuccess(code, msg, resData.getBusinessHourDataList());

                            break;
                        case NO_CONTENT:
                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                } else {
                    callback.onError(SERVER_ERROR, "getBusinessHour()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<BusinessHour> call, Throwable t) {
                callback.onError(SERVER_ERROR, "getBusinessHour()>>" + t.toString());
            }
        });


    }
}
