package kr.co.parthair.android.members.net.api.callback;

import androidx.annotation.Nullable;

import kr.co.parthair.android.members.model.NewsDataModel;

/**
 * ClassName            GetNewsDataCallback
 * Created by JSky on   2021-10-12
 * <p>
 * Description
 */
public interface GetNewsCallback {
    void onSuccess(int code, String msg, @Nullable NewsDataModel data);
    void onError(int code, String msg);
}
