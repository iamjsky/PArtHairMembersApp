package kr.co.parthair.android.members.ui.page.main.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.common.base.BaseDialog;

import static kr.co.parthair.android.members.common.MyConstants.NAVER_RESERVATION_URL;
import static kr.co.parthair.android.members.common.MyConstants.RESERVATION_CALL_NUMBER;
import static kr.co.parthair.android.members.common.MyConstants.VISIT_CHECK_CALL_NUMBER;

/**
 * ClassName            VisitCallDialog
 * Created by JSky on   2021-10-08
 * <p>
 * Description
 */
public class ReservationDialog extends BaseDialog {




    Context mContext;

    public ReservationDialog(Context context) {
        super(context);
        mContext = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reservation_select);
        ButterKnife.bind(this);



    }




    @OnClick(R.id.layout_reservationNaver)
    public void layout_reservationNaverClicked() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(NAVER_RESERVATION_URL));
        mContext.startActivity(intent);
        dismiss();
    }

    @OnClick(R.id.layout_reservationApp)
    public void layout_reservationAppClicked() {

        dismiss();
    }
    @OnClick(R.id.layout_reservationCall)
    public void layout_reservationCallClicked() {
        dismiss();
        CallDialog callDialog = new CallDialog(mContext, "전화로 예약하기내",
                "코로나19 확산 방지를 위한 출입관리를 위해\n아래의 번호로 전화를 겁니다.\n\n080-205-0000\n\n안심콜(CALL) 등록을 하시겠습니까?",
                RESERVATION_CALL_NUMBER);
        callDialog.show();


    }
    @OnClick(R.id.btn_cancel)
    public void btn_cancelClicked() {

        dismiss();
    }
}
