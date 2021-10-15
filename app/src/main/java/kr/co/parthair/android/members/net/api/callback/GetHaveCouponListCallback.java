package kr.co.parthair.android.members.net.api.callback;

import androidx.annotation.Nullable;

import java.util.List;

import kr.co.parthair.android.members.model.Coupons;
import kr.co.parthair.android.members.model.HaveCoupons;

/**
 * ClassName            GetHaveCouponListCallback
 * Created by JSky on   2021-10-15
 * <p>
 * Description
 */
public interface GetHaveCouponListCallback {
    void onSuccess(int code, String msg, @Nullable List<HaveCoupons.HaveCouponList> data);
    void onError(int code, String msg);
}
