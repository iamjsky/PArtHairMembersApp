package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ClassName            Coupons
 * Created by JSky on   2021-10-15
 * <p>
 * Description
 */
@Data
public class Coupons {

    @SerializedName("header")
    @Expose
    public Header header;

    @SerializedName("coupon_list")
    @Expose
    public List<CouponList> couponList = new ArrayList<>();

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
    public class CouponList {
        @SerializedName("idx")
        @Expose
        public Integer idx;

        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("content")
        @Expose
        public String content;
        @SerializedName("state")
        @Expose
        public Integer state;
        @SerializedName("start_date")
        @Expose
        public String start_date;
        @SerializedName("end_date")
        @Expose
        public String end_date;
    }


}
