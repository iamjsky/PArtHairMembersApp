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

import static kr.co.parthair.android.members.common.MyConstants.VISIT_CHECK_CALL_NUMBER;

/**
 * ClassName            VisitCallDialog
 * Created by JSky on   2021-10-08
 * <p>
 * Description
 */
public class CallDialog extends BaseDialog {


    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_desc)
    TextView tv_desc;

    Context mContext;
    String title = "";
    String desc = "";
    String callNumber = "";

    public CallDialog(Context context, String _title, String _desc, String _callNumber) {
        super(context);
        mContext = context;
        title = _title;
        desc = _desc;
        callNumber = _callNumber;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_visit_call);
        ButterKnife.bind(this);

        tv_title.setText(title);
        tv_desc.setText(desc);

    }



    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(callNumber));
        mContext.startActivity(intent);
        dismiss();
    }
    @OnClick(R.id.btn_cancel)
    public void btn_cancelClicked() {

        dismiss();
    }
}
