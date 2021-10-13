package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ClassName            MainNews
 * Created by JSky on   2021-10-12
 * <p>
 * Description
 */
@Data
public class NewsDataModel {
    @SerializedName("header")
    @Expose
    public Header header;

    @SerializedName("news_data")
    @Expose
    public List<NewsData> newsData = new ArrayList<>();

    public List<NewsData> newsNoticeList = new ArrayList<>();
    public List<NewsData> newsEventsList = new ArrayList<>();
    public List<NewsData> newsCouponsList = new ArrayList<>();

    @Data
    public class Header {
        @SerializedName("code")
        @Expose
        public Integer code;
        @SerializedName("message")
        @Expose
        public String message;

    }

    @Data
    public class NewsData {
        @SerializedName("idx")
        @Expose
        public Integer idx;
        @SerializedName("category")
        @Expose
        public Integer category;
        @SerializedName("midx")
        @Expose
        public Integer midx;
        @SerializedName("user_nickname")
        @Expose
        public String user_nickname;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("content")
        @Expose
        public String content;
        @SerializedName("top")
        @Expose
        public Integer top;
        @SerializedName("secret")
        @Expose
        public Integer secret;
        @SerializedName("regdate")
        @Expose
        public String regDate;
        @SerializedName("view")
        @Expose
        public Integer view;
        @SerializedName("state")
        @Expose
        public Integer state;
        @SerializedName("linked_coupon_idx")
        @Expose
        public Integer linked_coupon_idx;
        @SerializedName("icon_image_url")
        @Expose
        public String icon_image_url;
    }

}
