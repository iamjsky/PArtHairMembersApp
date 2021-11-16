package kr.co.parthair.android.members.net.api;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import kr.co.parthair.android.members.common.HttpResponseCode;
import kr.co.parthair.android.members.common.MyConstants;
import kr.co.parthair.android.members.model.MainHairStyle;
import kr.co.parthair.android.members.model.MainNoticeImage;
import kr.co.parthair.android.members.model.NewsDataModel;
import kr.co.parthair.android.members.model.TagListModel;
import kr.co.parthair.android.members.net.RetrofitGenerator;
import kr.co.parthair.android.members.net.api.callback.GetMainHairStyleCallback;
import kr.co.parthair.android.members.net.api.callback.GetMainNoticeImageCallback;
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
public class MainApi implements MyConstants, HttpResponseCode {
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
                            for (NewsDataModel.NewsData newsData : resData.getNewsData()) {
                                int category = newsData.getCategory();
                                /*
                                    0   notice
                                    1   events


                                 */
                                switch (category) {
                                    case 0:
                                        resData.getNewsNoticeList().add(newsData);
                                        break;
                                    case 1:
                                        resData.getNewsEventsList().add(newsData);
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

    public void getMainNoticeImage(GetMainNoticeImageCallback callback) {


        apiService.getMainNoticeImage().enqueue(new Callback<MainNoticeImage>() {
            @Override
            public void onResponse(Call<MainNoticeImage> call, Response<MainNoticeImage> response) {
                if (response.isSuccessful()) {
                    MainNoticeImage resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:



                            callback.onSuccess(code, msg, resData);
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                } else {
                    callback.onError(SERVER_ERROR, "getMainNoticeImage()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<MainNoticeImage> call, Throwable t) {
                callback.onError(SERVER_ERROR, "getMainNoticeImage()>>" + t.toString());
            }
        });


    }

    public void getMainHairStyle(GetMainHairStyleCallback callback) {


        apiService.getMainHairStyle().enqueue(new Callback<MainHairStyle>() {
            @Override
            public void onResponse(Call<MainHairStyle> call, Response<MainHairStyle> response) {
                if (response.isSuccessful()) {
                    MainHairStyle resData = response.body();
                    int code = resData.getHeader().getCode();
                    String msg = resData.getHeader().getMessage();

                    switch (code) {
                        case OK:

                            for(MainHairStyle.HairStyleData data : resData.getHairData()){
                                String tags = data.getTag()+"";

                                String[] splitTags = tags.split(",");
                                List<TagListModel.TagInfo> styleTagList = new ArrayList<>();

                                    for(int i=0; i < splitTags.length; i++){
                                        TagListModel.TagInfo styleTag = new TagListModel.TagInfo();
                                        styleTag.setIdx(Integer.parseInt(splitTags[i]));
                                        LOG_I("styleTag.getIdx() : " + styleTag.getIdx());
                                        LOG_I("TagListModel.instance.getTagInfoList().size() : " + TagListModel.instance.getTagInfoList().size());
                                        for(int j=0; j < TagListModel.instance.getTagInfoList().size(); j++){
                                            if(TagListModel.instance.getTagInfoList().get(j).getIdx() == styleTag.getIdx()){
                                                styleTag.setCategory(TagListModel.instance.getTagInfoList().get(j).getCategory());
                                                styleTag.setName(TagListModel.instance.getTagInfoList().get(j).getName());
                                            }
                                        }

                                        styleTagList.add(styleTag);
                                    }


                                data.setStyleTag(styleTagList);
                            }



                            callback.onSuccess(code, msg, resData);
                            break;

                        case NOT_FOUND:
                        case ERROR:
                            callback.onError(code, msg);
                            break;


                    }


                } else {
                    callback.onError(SERVER_ERROR, "getMainHairStyle()>>" + "response is not successful");
                }
            }

            @Override
            public void onFailure(Call<MainHairStyle> call, Throwable t) {
                callback.onError(SERVER_ERROR, "getMainHairStyle()>>" + t.toString());
            }
        });


    }

}
