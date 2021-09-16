package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            PhoneLogin
 * Created by JSky on   2021-09-16
 * <p>
 * Description
 */
@Data
public class PhoneLogin {
    @SerializedName("header")
    @Expose
    public Header header;


    @Data
    public class Header{
        @SerializedName("code")
        @Expose
        public Integer code;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("user_token")
        @Expose
        public String user_token;
    }
}
