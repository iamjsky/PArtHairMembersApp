package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ClassName            MyReservation
 * Created by JSky on   2021-11-24
 * <p>
 * Description
 */
@Data
public class MyReservation {
    @SerializedName("header")
    @Expose
    public Header header;
    @SerializedName("my_reservationList")
    @Expose
    public List<MyReservationData> myReservationDataList = new ArrayList<>();

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
    public class MyReservationData {

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
        @SerializedName("reservation_time")
        @Expose
        public String reservation_time;
        @SerializedName("hs_idx")
        @Expose
        public Integer hsIdx;
        @SerializedName("des_idx")
        @Expose
        public Integer desIdx;
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

        @SerializedName("hs_list")
        @Expose
        public String hs_list;
    }

}
