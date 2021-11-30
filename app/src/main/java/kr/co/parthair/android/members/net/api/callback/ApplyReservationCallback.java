package kr.co.parthair.android.members.net.api.callback;

import androidx.annotation.Nullable;

import java.util.List;

import kr.co.parthair.android.members.model.ApplyReservation;
import kr.co.parthair.android.members.model.TagListModel;

/**
 * ClassName            ApplyReservationCallback
 * Created by JSky on   2021-11-30
 * <p>
 * Description
 */
public interface ApplyReservationCallback {
    void onSuccess(int code, String msg, @Nullable ApplyReservation.ReservationResult data);
    void onError(int code, String msg);
}
