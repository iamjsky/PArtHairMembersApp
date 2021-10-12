package kr.co.parthair.android.members.net.api;

import kr.co.parthair.android.members.common.BoardCategoryNum;
import kr.co.parthair.android.members.common.HttpResponseCode;
import kr.co.parthair.android.members.common.MyConstants;
import kr.co.parthair.android.members.model.NewsDataModel;
import kr.co.parthair.android.members.net.RetrofitGenerator;
import kr.co.parthair.android.members.net.api.callback.GetNewsCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * ClassName            BoardApi
 * Created by JSky on   2021-10-12
 * <p>
 * Description
 */
public class BoardApi implements MyConstants, HttpResponseCode, BoardCategoryNum {
    private RetrofitGenerator retrofitGenerator = new RetrofitGenerator();
    private ApiService apiService = retrofitGenerator.getApiService();

    public void getNews(GetNewsCallback callback) {



        apiService.getNews().enqueue(new Callback<NewsDataModel>() {
            @Override
            public void onResponse(Call<NewsDataModel> call, Response<NewsDataModel> response) {
                if (response.isSuccessful()) {
                    NewsDataModel resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:
                            for(NewsDataModel.NewsData newsData : resData.getNewsData()){
                                int category = newsData.getCategory();
                                switch (category) {
                                    case MAIN_NEWS_NOTICE:
                                        resData.getNewsNoticeList().add(newsData);
                                        break;
                                    case MAIN_NEWS_EVENTS:
                                        resData.getNewsEventsList().add(newsData);
                                        break;
                                    case MAIN_NEWS_COUPONS:
                                        resData.getNewsCouponsList().add(newsData);
                                        break;



                                }
                            }



                            callback.onSuccess(code, msg, resData);
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                } else {
                    callback.onError(SERVER_ERROR, "getNews()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<NewsDataModel> call, Throwable t) {
                callback.onError(SERVER_ERROR, "getNews()>>" + t.toString());
            }
        });


    }
}
