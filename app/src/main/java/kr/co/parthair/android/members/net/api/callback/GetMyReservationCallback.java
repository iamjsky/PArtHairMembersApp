package kr.co.parthair.android.members.net.api.callback;

import androidx.annotation.Nullable;

import java.util.List;

import kr.co.parthair.android.members.model.Coupons;
import kr.co.parthair.android.members.model.MyReservation;

/**
 * ClassName            GetMyReservationCallback
 * Created by JSky on   2021-11-24
 * <p>
 * Description
 */
public interface GetMyReservationCallback {
    void onSuccess(int code, String msg, @Nullable List<MyReservation.MyReservationData> data);
    void onError(int code, String msg);
}
