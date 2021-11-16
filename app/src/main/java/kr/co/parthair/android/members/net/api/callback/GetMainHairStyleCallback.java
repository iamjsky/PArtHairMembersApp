package kr.co.parthair.android.members.net.api.callback;

import androidx.annotation.Nullable;

import kr.co.parthair.android.members.model.MainHairStyle;
import kr.co.parthair.android.members.model.MainNoticeImage;

/**
 * ClassName            GetMainHairStyleCallback
 * Created by JSky on   2021-10-14
 * <p>
 * Description
 */
public interface GetMainHairStyleCallback {
    void onSuccess(int code, String msg, @Nullable MainHairStyle data);
    void onError(int code, String msg);
}
