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
import kr.co.parthair.android.members.ui.page.login.LoginActivity;

import static kr.co.parthair.android.members.common.MyConstants.VISIT_CHECK_CALL_NUMBER;

/**
 * ClassName            VisitCallDialog
 * Created by JSky on   2021-10-08
 * <p>
 * Description
 */
public class VisitCallDialog extends BaseDialog {


    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_desc)
    TextView tv_desc;

    Context mContext;

    public VisitCallDialog(Context context) {
        super(context);
        mContext = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_visit_call);
        ButterKnife.bind(this);

        tv_title.setText("출입관리 안심콜(CALL) 안내");
        tv_desc.setText("코로나19 확산 방지를 위한 출입관리를 위해 아래의 번호로 전화를 겁니다.\n\n080-205-0000\n\n안심콜(CALL) 등록을 하시겠습니까?");

    }



    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(VISIT_CHECK_CALL_NUMBER));
        mContext.startActivity(intent);
        dismiss();
    }
    @OnClick(R.id.btn_cancel)
    public void btn_cancelClicked() {

        dismiss();
    }
}
