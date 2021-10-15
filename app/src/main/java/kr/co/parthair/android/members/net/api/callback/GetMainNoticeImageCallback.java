package kr.co.parthair.android.members.net.api.callback;

import androidx.annotation.Nullable;

import kr.co.parthair.android.members.model.MainNoticeImage;
import kr.co.parthair.android.members.model.NewsDataModel;

/**
 * ClassName            GetMainNoticeImageCallback
 * Created by JSky on   2021-10-14
 * <p>
 * Description
 */
public interface GetMainNoticeImageCallback {
    void onSuccess(int code, String msg, @Nullable MainNoticeImage data);
    void onError(int code, String msg);
}
