package kr.co.parthair.android.members.net.api.callback;

import androidx.annotation.Nullable;

import java.util.List;

import kr.co.parthair.android.members.model.Coupons;
import kr.co.parthair.android.members.model.Designer;

/**
 * ClassName            GetDesignerInfoCallback
 * Created by JSky on   2021-11-29
 * <p>
 * Description
 */
public interface GetDesignerInfoCallback {
    void onSuccess(int code, String msg, @Nullable List<Designer.DesignerInfo> data);
    void onError(int code, String msg);
}
