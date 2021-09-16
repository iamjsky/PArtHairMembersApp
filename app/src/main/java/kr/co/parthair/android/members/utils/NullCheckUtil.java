package kr.co.parthair.android.members.utils;

import androidx.annotation.Nullable;

/**
 * ClassName            NullCheckUtil
 * Created by JSky on   2021-09-16
 * <p>
 * Description
 */
public class NullCheckUtil {
    public static boolean String_IsNotNull(@Nullable String value){
        if(value != null && !value.equals("")){
            return true;
        }else{
            return false;
        }
    }
}
