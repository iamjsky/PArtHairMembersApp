package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            ApplyReservation
 * Created by JSky on   2021-11-30
 * <p>
 * Description
 */
@Data
public class ApplyReservation {

    @SerializedName("header")
    @Expose
    public Header header;
    @SerializedName("result")
    @Expose
    public ReservationResult reservationResult;

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
    public class ReservationResult {

        @SerializedName("idx")
        @Expose
        public Integer idx;
        @SerializedName("midx")
        @Expose
        public Integer midx;
        @SerializedName("user_phone")
        @Expose
        public String userPhone;
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("reservation_date")
        @Expose
        public String reservationDate;
        @SerializedName("des_idx")
        @Expose
        public Integer desIdx;
        @SerializedName("hs_idx")
        @Expose
        public Integer hsIdx;
        @SerializedName("memo")
        @Expose
        public String memo;
        @SerializedName("state")
        @Expose
        public Integer state;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("designer")
        @Expose
        public String designer;
        @SerializedName("hair_style")
        @Expose
        public String hairStyle;

    }
}
