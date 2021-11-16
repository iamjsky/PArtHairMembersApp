package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import kr.co.parthair.android.members.common.MyInfo;
import lombok.Data;

/**
 * ClassName            TagListModel
 * Created by JSky on   2021-11-15
 * <p>
 * Description
 */
@Data
public class TagListModel {
    public static TagListModel instance = new TagListModel();

    @SerializedName("header")
    @Expose
    public Header header;

    @SerializedName("tag_data")
    @Expose
    public List<TagInfo> tagInfoList = new ArrayList<>();

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
    public static class TagInfo{
        @SerializedName("idx")
        @Expose
        public Integer idx;
        @SerializedName("category")
        @Expose
        public Integer category;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
    }
}
