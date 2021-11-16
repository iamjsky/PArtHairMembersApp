package kr.co.parthair.android.members.net.api;

import kr.co.parthair.android.members.common.HttpResponseCode;
import kr.co.parthair.android.members.common.MyConstants;
import kr.co.parthair.android.members.model.Coupons;
import kr.co.parthair.android.members.model.HaveCoupons;
import kr.co.parthair.android.members.model.MainNoticeImage;
import kr.co.parthair.android.members.model.TagListModel;
import kr.co.parthair.android.members.net.RetrofitGenerator;
import kr.co.parthair.android.members.net.api.callback.GetCouponListCallback;
import kr.co.parthair.android.members.net.api.callback.GetHaveCouponListCallback;
import kr.co.parthair.android.members.net.api.callback.GetMainNoticeImageCallback;
import kr.co.parthair.android.members.net.api.callback.TagListCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ClassName            EtcApi
 * Created by JSky on   2021-10-15
 * <p>
 * Description
 */
public class EtcApi implements MyConstants, HttpResponseCode {
    private RetrofitGenerator retrofitGenerator = new RetrofitGenerator();
    private ApiService apiService = retrofitGenerator.getApiService();

    public void getCouponList(GetCouponListCallback callback) {


        apiService.getCouponList().enqueue(new Callback<Coupons>() {
            @Override
            public void onResponse(Call<Coupons> call, Response<Coupons> response) {
                if (response.isSuccessful()) {
                    Coupons resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:



                            callback.onSuccess(code, msg, resData.getCouponList());
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                } else {
                    callback.onError(SERVER_ERROR, "getCouponList()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<Coupons> call, Throwable t) {
                callback.onError(SERVER_ERROR, "getCouponList()>>" + t.toString());
            }
        });


    }
    public void getHaveCouponList(GetHaveCouponListCallback callback) {


        apiService.getHaveCouponList().enqueue(new Callback<HaveCoupons>() {
            @Override
            public void onResponse(Call<HaveCoupons> call, Response<HaveCoupons> response) {
                if (response.isSuccessful()) {
                    HaveCoupons resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:



                            callback.onSuccess(code, msg, resData.getHaveCouponList());
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                } else {
                    callback.onError(SERVER_ERROR, "getHaveCouponList()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<HaveCoupons> call, Throwable t) {
                callback.onError(SERVER_ERROR, "getHaveCouponList()>>" + t.toString());
            }
        });


    }

    public void getTagList(TagListCallback callback) {


        apiService.getTagList().enqueue(new Callback<TagListModel>() {
            @Override
            public void onResponse(Call<TagListModel> call, Response<TagListModel> response) {
                if (response.isSuccessful()) {
                    TagListModel resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:



                            callback.onSuccess(code, msg, resData.getTagInfoList());
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                } else {
                    callback.onError(SERVER_ERROR, "getTagList()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<TagListModel> call, Throwable t) {
                callback.onError(SERVER_ERROR, "getTagList()>>" + t.toString());
            }
        });


    }

}
