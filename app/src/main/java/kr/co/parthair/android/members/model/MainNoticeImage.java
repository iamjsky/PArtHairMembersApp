package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ClassName            MainNoticeImage
 * Created by JSky on   2021-10-14
 * <p>
 * Description
 */
@Data
public class MainNoticeImage {

    @SerializedName("header")
    @Expose
    public Header header;

    @SerializedName("slide_img_list")
    @Expose
    public List<SlideImage> slideImageList = new ArrayList<>();

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
    public class SlideImage {
        @SerializedName("idx")
        @Expose
        public Integer idx;
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
        @SerializedName("view")
        @Expose
        public Integer view;
        @SerializedName("state")
        @Expose
        public Integer state;
        @SerializedName("img_url")
        @Expose
        public String img_url;
        @SerializedName("regdate")
        @Expose
        public String regDate;
    }

}
