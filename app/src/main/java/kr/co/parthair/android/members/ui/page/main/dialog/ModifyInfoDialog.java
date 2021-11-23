package kr.co.parthair.android.members.ui.page.main.dialog;

import android.content.Context;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.common.base.BaseFullStyleDialog;

/**
 * ClassName            ModifyInfoDialog
 * Created by JSky on   2021-11-23
 * <p>
 * Description
 */
public class ModifyInfoDialog extends BaseFullStyleDialog {

    public ModifyInfoDialog(Context context) {
        super(context);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_modify_info);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked(){
        dismiss();
    }

}
