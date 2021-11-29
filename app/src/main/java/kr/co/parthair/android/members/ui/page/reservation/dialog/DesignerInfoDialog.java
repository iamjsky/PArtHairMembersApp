package kr.co.parthair.android.members.ui.page.reservation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.model.MainHairStyle;
import kr.co.parthair.android.members.net.api.MainApi;
import kr.co.parthair.android.members.net.api.callback.GetMainHairStyleCallback;
import kr.co.parthair.android.members.ui.page.reservation.ReservationActivity;

/**
 * ClassName            UserBarcodeDialog
 * Created by JSky on   2021-11-23
 * <p>
 * Description
 */
public class DesignerInfoDialog extends Dialog {

    @BindView(R.id.recv_designerList)
    RecyclerView recv_designerList;

    @BindView(R.id.layout_designer_01)
    LinearLayout layout_designer_01;

    Context mContext;

    public DesignerInfoDialog(Context context) {
        super(context, R.style.FullScreenDialog_visibleSystemUi);
        mContext = context;

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_designer_info);
        ButterKnife.bind(this);


    }


    @OnClick(R.id.iv_back)
    public void iv_backClicked() {
        dismiss();

    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    @OnClick(R.id.layout_designer_01)
    public void layout_designer_01Clicked(){
        ((ReservationActivity)mContext).selectedDesIdx = 1;
        ((ReservationActivity)mContext).selectedDesigner = "소연 원장";
        dismiss();
    }
}
