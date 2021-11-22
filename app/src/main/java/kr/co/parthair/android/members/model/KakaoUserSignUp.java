package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            KakaoSignUp
 * Created by JSky on   2021-09-30
 * <p>
 * Description
 */
@Data
public class KakaoUserSignUp {
    @SerializedName("header")
    @Expose
    public Header header;
    @SerializedName("user_info")
    @Expose
    public SocialUserInfo socialUserInfo;

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
    public class SocialUserInfo {

        @SerializedName("user_token")
        @Expose
        public String userToken;

        @SerializedName("user_name")
        @Expose
        public String userName;



    }
}
