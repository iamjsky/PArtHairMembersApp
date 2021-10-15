package kr.co.parthair.android.members.net.api.callback;

import androidx.annotation.Nullable;

import java.util.List;

import kr.co.parthair.android.members.model.Coupons;
import kr.co.parthair.android.members.model.MainNoticeImage;

/**
 * ClassName            GetCouponListCallback
 * Created by JSky on   2021-10-15
 * <p>
 * Description
 */
public interface GetCouponListCallback {
    void onSuccess(int code, String msg, @Nullable List<Coupons.CouponList> data);
    void onError(int code, String msg);
}
