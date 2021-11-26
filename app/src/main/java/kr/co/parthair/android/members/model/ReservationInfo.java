package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ClassName            ReservationInfo
 * Created by JSky on   2021-11-26
 * <p>
 * Description
 */
@Data
public class ReservationInfo {

    @SerializedName("header")
    @Expose
    public Header header;
    @SerializedName("business_time")
    @Expose
    public BusinessTimeData businessTime;
    @SerializedName("reservation_list")
    @Expose
    public List<ReservationData> reservationDataList = new ArrayList<>();
    @SerializedName("block_time_list")
    @Expose
    public List<BlockTimeData> blockTimeDataList = new ArrayList<>();

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
    public class BusinessTimeData{
        @SerializedName("open_time")
        @Expose
        public String openTime;
        @SerializedName("close_time")
        @Expose
        public String closeTime;
    }

    @Data
    public class ReservationData{

        @SerializedName("reservation_date")
        @Expose
        public String reservationDate;
        @SerializedName("state")
        @Expose
        public Integer state;
        @SerializedName("reservation_year")
        @Expose
        public String reservation_year;
        @SerializedName("reservation_month")
        @Expose
        public String reservation_month;
        @SerializedName("reservation_time")
        @Expose
        public String reservation_time;
        @SerializedName("reservation_day")
        @Expose
        public String reservation_day;
    }
    @Data
    public class BlockTimeData{

        @SerializedName("time")
        @Expose
        public String blockTime;

    }
}
