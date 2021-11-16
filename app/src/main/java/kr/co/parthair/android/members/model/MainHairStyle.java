package kr.co.parthair.android.members.model;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ClassName            MainHairStyle
 * Created by JSky on   2021-11-15
 * <p>
 * Description
 */
@Data
public class MainHairStyle {

    @SerializedName("header")
    @Expose
    public Header header;

    @SerializedName("hair_data")
    @Expose
    public List<HairStyleData> hairData = new ArrayList<>();

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
    public class HairStyleData {

        @SerializedName("idx")
        @Expose
        public Integer idx;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("content")
        @Expose
        public String content;
        @SerializedName("style_thumb_img_url")
        @Expose
        public String styleThumbImgUrl;
        @SerializedName("style_1_title")
        @Expose
        public String style1Title;
        @SerializedName("style_1_desc")
        @Expose
        public String style1Desc;
        @SerializedName("style_1_img_url")
        @Expose
        public String style1ImgUrl;
        @SerializedName("style_2_title")
        @Expose
        public String style2Title;
        @SerializedName("style_2_desc")
        @Expose
        public String style2Desc;
        @SerializedName("style_2_img_url")
        @Expose
        public String style2ImgUrl;
        @SerializedName("style_3_title")
        @Expose
        public String style3Title;
        @SerializedName("style_3_desc")
        @Expose
        public String style3Desc;
        @SerializedName("style_3_img_url")
        @Expose
        public String style3ImgUrl;
        @SerializedName("style_4_title")
        @Expose
        public String style4Title;
        @SerializedName("style_4_desc")
        @Expose
        public String style4Desc;
        @SerializedName("style_4_img_url")
        @Expose
        public String style4ImgUrl;
        @SerializedName("state")
        @Expose
        public Integer state;
        @SerializedName("regdate")
        @Expose
        public String regdate;
        @SerializedName("tag")
        @Expose
        public String tag;

        public List<TagListModel.TagInfo> styleTag = new ArrayList<>();

    }




}
