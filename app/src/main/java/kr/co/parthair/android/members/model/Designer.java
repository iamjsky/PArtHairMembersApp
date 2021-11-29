package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ClassName            Designer
 * Created by JSky on   2021-11-29
 * <p>
 * Description
 */
@Data
public class Designer {
    @SerializedName("header")
    @Expose
    public Header header;
    @SerializedName("designer_info")
    @Expose
    public List<DesignerInfo> designerInfo = new ArrayList<>();

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
    public class DesignerInfo{
        @SerializedName("idx")
        @Expose
        public Integer idx;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("content")
        @Expose
        public String content;
        @SerializedName("created_at")
        @Expose
        public String created_at;
    }
}
