package kr.co.parthair.android.members.common;

import kr.co.parthair.android.members.model.GetUserInfo;
import lombok.Data;

/**
 * ClassName            MyInfo
 * Created by JSky on   2021-09-16
 * <p>
 * Description
 */
@Data
public class MyInfo implements MyConstants {

    public static MyInfo instance = new MyInfo();


    private String user_token = "";

    private GetUserInfo.UserInfo userInfo;

    public boolean isLogin(){
        boolean value = false;

        if(user_token != null && !user_token.equals("") && !user_token.equals("null")){
            value = true;
        }else{
            value = false;
        }
        return value;
    }

}
