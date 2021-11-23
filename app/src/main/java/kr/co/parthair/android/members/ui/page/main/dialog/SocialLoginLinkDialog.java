package kr.co.parthair.android.members.ui.page.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.common.base.BaseDialog;
import kr.co.parthair.android.members.ui.page.common.base.BaseFullStyleDialog;

/**
 * ClassName            SocialLoginLinkDialog
 * Created by JSky on   2021-11-23
 * <p>
 * Description
 */
public class SocialLoginLinkDialog extends BaseFullStyleDialog {



    public SocialLoginLinkDialog(Context context) {
        super(context);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_social_login_link);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.btn_cancel)
    public void btn_cancelClicked(){
        dismiss();
    }
    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked(){
        dismiss();
    }

}
