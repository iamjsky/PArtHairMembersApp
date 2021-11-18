package kr.co.parthair.android.members.ui.page.common.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.common.base.BaseDialog;

import static kr.co.parthair.android.members.common.MyConstants.NAVER_RESERVATION_URL;
import static kr.co.parthair.android.members.common.MyConstants.RESERVATION_CALL_NUMBER;

/**
 * ClassName            VisitCallDialog
 * Created by JSky on   2021-10-08
 * <p>
 * Description
 */
public class LoadingDialog extends BaseDialog {




    Context mContext;

    public LoadingDialog(Context context) {
        super(context);
        mContext = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        ButterKnife.bind(this);



    }





}
