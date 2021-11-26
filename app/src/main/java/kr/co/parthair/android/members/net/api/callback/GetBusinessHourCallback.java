package kr.co.parthair.android.members.net.api.callback;

import androidx.annotation.Nullable;

import java.util.List;

import kr.co.parthair.android.members.model.BusinessHour;
import kr.co.parthair.android.members.model.Coupons;

/**
 * ClassName            GetBusinessHourCallback
 * Created by JSky on   2021-11-26
 * <p>
 * Description
 */
public interface GetBusinessHourCallback {
    void onSuccess(int code, String msg, @Nullable List<BusinessHour.BusinessHourData> data);
    void onError(int code, String msg);
}
