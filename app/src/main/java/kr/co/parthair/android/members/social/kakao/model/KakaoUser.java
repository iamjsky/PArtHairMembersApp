package kr.co.parthair.android.members.social.kakao.model;


import lombok.Data;

/**
 * ClassName            KakaoUser
 * Created by JSky on   2020-10-16
 * <p>
 * Description          카카오 유저 데이터
 */
@Data
public class KakaoUser {

    public Long userId;
    public String email;
    public String nickname;
    public String profileImageUrl;
    public String kakaoUserToken;

//    public KakaoUser(String kakaoUserToken, Long userId, String email, String nickname, String profileImageUrl) {
//        this.kakaoUserToken = kakaoUserToken;
//        this.userId = userId;
//        this.email = email;
//        this.nickname = nickname;
//        this.profileImageUrl = profileImageUrl;
//    }
}
