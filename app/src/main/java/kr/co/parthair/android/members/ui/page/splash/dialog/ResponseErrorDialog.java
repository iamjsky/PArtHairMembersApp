package kr.co.parthair.android.members.ui.page.splash.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.common.base.BaseDialog;
import kr.co.parthair.android.members.ui.page.main.MainActivity;

/**
 * ClassName            ResponseErrorDialog
 * Created by JSky on   2021-10-08
 * <p>
 * Description
 */
public class ResponseErrorDialog extends BaseDialog {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_desc)
    TextView tv_desc;

    String title,desc;
    Context mContext;

    public ResponseErrorDialog(Context context) {
        super(context);
    }
    public ResponseErrorDialog(Context context, String title, String desc) {
        super(context);
        mContext = context;
        this.title = title;
        this.desc = desc;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_response_error);
        ButterKnife.bind(this);

        tv_title.setText(title);
        tv_desc.setText(desc);

    }



    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked() {


        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        dismiss();
    }


}
