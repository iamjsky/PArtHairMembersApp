package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ClassName            BusinessHour
 * Created by JSky on   2021-11-26
 * <p>
 * Description
 */
@Data
public class BusinessHour {
    @SerializedName("header")
    @Expose
    public Header header;
    @SerializedName("business_hour_list")
    @Expose
    public List<BusinessHourData> businessHourDataList = new ArrayList<>();

    @Data
    public class Header{
        @SerializedName("code")
        @Expose
        public Integer code;
        @SerializedName("message")
        @Expose
        public String message;
    }

    @Data
    public class BusinessHourData{
        @SerializedName("idx")
        @Expose
        public Integer idx;
        @SerializedName("time")
        @Expose
        public String time;
        @SerializedName("state")
        @Expose
        public Integer state;
    }
}
