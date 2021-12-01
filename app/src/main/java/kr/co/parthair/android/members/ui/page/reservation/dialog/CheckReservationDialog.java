package kr.co.parthair.android.members.ui.page.reservation.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.model.ApplyReservation;
import kr.co.parthair.android.members.net.api.callback.ApplyReservationCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseDialog;

/**
 * ClassName            CheckReservationDialog
 * Created by JSky on   2021-12-01
 * <p>
 * Description
 */
public class CheckReservationDialog extends BaseDialog {

    Context mContext;
    String user_name = "";
    String user_phone = "";
    String reservation_date = "";
    String reservation_time = "";
    String hs_list = "";
    String designer = "";
    int des_idx = -1;
    String memo = "";

    @BindView(R.id.tv_userReservationName)
    TextView tv_userReservationName;
    @BindView(R.id.tv_userReservationPhone)
    TextView tv_userReservationPhone;
    @BindView(R.id.tv_userReservationDate)
    TextView tv_userReservationDate;
    @BindView(R.id.tv_userReservationTime)
    TextView tv_userReservationTime;
    @BindView(R.id.tv_userReservationStyle)
    TextView tv_userReservationStyle;
    @BindView(R.id.tv_userReservationDesigner)
    TextView tv_userReservationDesigner;
    @BindView(R.id.tv_userReservationMemo)
    TextView tv_userReservationMemo;


    public CheckReservationDialog(Context context, String reservation_date, String hs_list, String designer, int des_idx, String memo) {
        super(context);
        mContext = context;

        this.reservation_date = reservation_date+"";
        this.hs_list = hs_list+"";
        this.designer = designer+"";
        this.des_idx = des_idx;
        this.memo = memo+"";
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_reservation);
        ButterKnife.bind(this);

        user_name = MyInfo.instance.getUserInfo().getUserName() + "";
        user_phone = MyInfo.instance.getUserInfo().getUserPhone() + "";

        tv_userReservationName.setText(user_name);
        tv_userReservationPhone.setText(user_phone);
        tv_userReservationDate.setText(reservation_date);
        tv_userReservationTime.setText(reservation_time);
        tv_userReservationStyle.setText(hs_list);
        tv_userReservationDesigner.setText(designer);
        tv_userReservationMemo.setText(memo);

    }



    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked() {
        reservationApi.applyReservation(user_name, user_phone, reservation_date, hs_list, des_idx, memo, applyReservationCallback);

    }
    @OnClick(R.id.btn_cancel)
    public void btn_cancelClicked() {

        dismiss();
    }

    ApplyReservationCallback applyReservationCallback = new ApplyReservationCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable ApplyReservation.ReservationResult data) {
            dismiss();
        }

        @Override
        public void onError(int code, String msg) {

        }
    };
}
