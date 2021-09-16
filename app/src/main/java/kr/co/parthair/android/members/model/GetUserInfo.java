package kr.co.parthair.android.members.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * ClassName            UserInfo
 * Created by JSky on   2021-09-16
 * <p>
 * Description
 */
@Data
public class GetUserInfo {
    @SerializedName("header")
    @Expose
    public Header header;
    @SerializedName("user_info")
    @Expose
    public UserInfo userInfo;
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
    public class UserInfo {

        @SerializedName("idx")
        @Expose
        public Integer idx;
        @SerializedName("login_type")
        @Expose
        public String loginType;
        @SerializedName("user_token")
        @Expose
        public String userToken;
        @SerializedName("phone_login_pw")
        @Expose
        public String phoneLoginPw;
        @SerializedName("user_phone")
        @Expose
        public String userPhone;
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("user_nickname")
        @Expose
        public Object userNickname;
        @SerializedName("kakao_id")
        @Expose
        public Object kakaoId;
        @SerializedName("user_points")
        @Expose
        public String userPoints;
        @SerializedName("created_date")
        @Expose
        public Object createdDate;
        @SerializedName("login_date")
        @Expose
        public Object loginDate;
        @SerializedName("last_visit_date")
        @Expose
        public Object lastVisitDate;
        @SerializedName("link_state")
        @Expose
        public String linkState;
        @SerializedName("linked_idx")
        @Expose
        public String linkedIdx;

    }
}
