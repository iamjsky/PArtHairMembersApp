package kr.co.parthair.android.members.net.api;

import kr.co.parthair.android.members.common.HttpResponseCode;
import kr.co.parthair.android.members.common.MyConstants;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.model.ApplyReservation;
import kr.co.parthair.android.members.model.BusinessHour;
import kr.co.parthair.android.members.model.Designer;
import kr.co.parthair.android.members.model.MyReservation;
import kr.co.parthair.android.members.model.NewsDataModel;
import kr.co.parthair.android.members.model.ReservationInfo;
import kr.co.parthair.android.members.net.RetrofitGenerator;
import kr.co.parthair.android.members.net.api.callback.ApplyReservationCallback;
import kr.co.parthair.android.members.net.api.callback.GetBusinessHourCallback;
import kr.co.parthair.android.members.net.api.callback.GetDesignerInfoCallback;
import kr.co.parthair.android.members.net.api.callback.GetMyReservationCallback;
import kr.co.parthair.android.members.net.api.callback.GetNewsCallback;
import kr.co.parthair.android.members.net.api.callback.GetReservationInfoCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

/**
 * ClassName            ReservationApi
 * Created by JSky on   2021-11-24
 * <p>
 * Description
 */
public class ReservationApi implements MyConstants, HttpResponseCode {
    private RetrofitGenerator retrofitGenerator = new RetrofitGenerator();
    private ApiService apiService = retrofitGenerator.getApiService();


    public void getReservationInfo(int des_idx, String selected_date, GetReservationInfoCallback callback) {


        apiService.getReservationInfo(des_idx, selected_date).enqueue(new Callback<ReservationInfo>() {
            @Override
            public void onResponse(Call<ReservationInfo> call, Response<ReservationInfo> response) {
                if (response.isSuccessful()) {
                    ReservationInfo resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case NO_CONTENT:
                        case OK:

                            callback.onSuccess(code, msg, resData.getReservationDataList(), resData.getBlockTimeDataList());

                            break;

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
        String user_token = MyInfo.instance.getUser_token() + "";
        if (user_token.equals("")) {
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


                            if (resData.getMyReservationDataList().size() > 0) {

                                    callback.onSuccess(code, msg, resData.getMyReservationDataList());


                            } else {
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

    public void getDesignerList(GetDesignerInfoCallback callback) {


        apiService.getDesignerList().enqueue(new Callback<Designer>() {
            @Override
            public void onResponse(Call<Designer> call, Response<Designer> response) {
                if (response.isSuccessful()) {
                    Designer resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:

                            callback.onSuccess(code, msg, resData.getDesignerInfo());

                            break;
                        case NO_CONTENT:
                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                } else {
                    callback.onError(SERVER_ERROR, "getDesignerList()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<Designer> call, Throwable t) {
                callback.onError(SERVER_ERROR, "getDesignerList()>>" + t.toString());
            }
        });


    }

    public void applyReservation(
            String user_name,
            String user_phone,
            String reservation_date,
            String hs_list,
            int des_idx,
            String memo,
            ApplyReservationCallback callback) {

        String user_token = MyInfo.instance.getUser_token() + "";
        if (user_token.equals("")) {
            callback.onError(-1, "TOKEN ERROR");
            // LOG_E("TOKEN ERROR");
            return;
        }

        apiService.applyReservation(
                user_token,
                user_name,
                user_phone,
                reservation_date,
                hs_list,
                des_idx,
                memo).enqueue(new Callback<ApplyReservation>() {
            @Override
            public void onResponse(Call<ApplyReservation> call, Response<ApplyReservation> response) {
                if (response.isSuccessful()) {
                    ApplyReservation resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:

                            callback.onSuccess(code, msg, resData.getReservationResult());

                            break;
                        case NO_CONTENT:
                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                } else {
                    callback.onError(SERVER_ERROR, "applyReservation()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<ApplyReservation> call, Throwable t) {
                callback.onError(SERVER_ERROR, "applyReservation()>>" + t.toString());
            }
        });


    }
}
