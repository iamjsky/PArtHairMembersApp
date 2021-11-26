package kr.co.parthair.android.members.net.api.callback;

import androidx.annotation.Nullable;

import java.util.List;

import kr.co.parthair.android.members.model.MyReservation;
import kr.co.parthair.android.members.model.ReservationInfo;

/**
 * ClassName            GetReservationInfoCallback
 * Created by JSky on   2021-11-26
 * <p>
 * Description
 */
public interface GetReservationInfoCallback {
    void onSuccess(int code, String msg, @Nullable List<ReservationInfo.ReservationData> reservationData, @Nullable List<ReservationInfo.BlockTimeData> blockTimedata);
    void onError(int code, String msg);
}
