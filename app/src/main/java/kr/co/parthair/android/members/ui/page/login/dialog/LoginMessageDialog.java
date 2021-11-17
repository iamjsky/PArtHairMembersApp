package kr.co.parthair.android.members.ui.page.login.dialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.common.base.BaseDialog;
import kr.co.parthair.android.members.ui.page.login.LoginActivity;
import kr.co.parthair.android.members.ui.page.login.LoginActivityBack;

/**
 * ClassName            LoginMessageDialog
 * Created by JSky on   2021-09-29
 * <p>
 * Description
 */
public class LoginMessageDialog extends BaseDialog {


    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_desc)
    TextView tv_desc;

    String title,desc;
    Context mContext;

    public LoginMessageDialog(Context context) {
        super(context);
    }
    public LoginMessageDialog(Context context, String title, String desc) {
        super(context);
        mContext = context;
        this.title = title;
        this.desc = desc;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login_message);
        ButterKnife.bind(this);

        tv_title.setText(title);
        tv_desc.setText(desc);

    }



    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked() {

        dismiss();
    }








}
