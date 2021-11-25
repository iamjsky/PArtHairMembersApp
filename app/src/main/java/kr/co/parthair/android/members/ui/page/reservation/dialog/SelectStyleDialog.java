package kr.co.parthair.android.members.ui.page.reservation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.model.MainHairStyle;
import kr.co.parthair.android.members.net.api.MainApi;
import kr.co.parthair.android.members.net.api.callback.GetMainHairStyleCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseDialog;
import kr.co.parthair.android.members.ui.page.common.base.BaseFullStyleDialog;
import kr.co.parthair.android.members.ui.page.reservation.ReservationActivity;
import kr.co.parthair.android.members.utils.NullCheckUtil;

/**
 * ClassName            UserBarcodeDialog
 * Created by JSky on   2021-11-23
 * <p>
 * Description
 */
public class SelectStyleDialog extends Dialog {

    @BindView(R.id.layout_styleItemContainer)
    LinearLayout layout_styleItemContainer;
    @BindView(R.id.layout_selectItemGroup)
    LinearLayout layout_selectItemGroup;
    protected MainApi mainApi = new MainApi();

    public List<Integer> selectedStyle = new ArrayList<Integer>();
    Context mContext;

    public SelectStyleDialog(Context context) {
        super(context, R.style.FullScreenDialog_visibleSystemUi);
        mContext = context;

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_style);
        ButterKnife.bind(this);

        mainApi.getMainHairStyle(getMainHairStyleCallback);

    }

    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked(){
        ((ReservationActivity)mContext).selectedStyle = selectedStyle;
        dismiss();

    }
    @OnClick(R.id.iv_back)
    public void iv_backClicked(){
        dismiss();

    }
    @Override
    public void onBackPressed() {
        dismiss();
    }

    GetMainHairStyleCallback getMainHairStyleCallback = new GetMainHairStyleCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable MainHairStyle data) {

                    if (data.getHairData() != null && data.getHairData().size() > 0) {

                        for(MainHairStyle.HairStyleData styleData : data.getHairData()){
                            ViewGroup styleItemView = (ViewGroup) getLayoutInflater().inflate(R.layout.item_select_hair_style, null, false);
                            styleItemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            LinearLayout layout_item = styleItemView.findViewById(R.id.layout_item);
                            LinearLayout layout_expandable = styleItemView.findViewById(R.id.layout_expandable);
                            TextView tv_styleItemTitle = styleItemView.findViewById(R.id.tv_styleItemTitle);
                            Button btn_select = styleItemView.findViewById(R.id.btn_select);

                            layout_item.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(layout_expandable.getVisibility() == View.VISIBLE){

                                        layout_expandable.setVisibility(View.GONE);
                                    }else{
                                        TransitionManager.beginDelayedTransition(layout_item, new AutoTransition());
                                        layout_expandable.setVisibility(View.VISIBLE);
                                    }

                                }
                            });
                            tv_styleItemTitle.setText(styleData.getTitle()+"");

                            btn_select.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.e("_____", styleData.getIdx() + "");
                                    if(!selectedStyle.contains(styleData.getIdx())){
                                        selectedStyle.add(styleData.getIdx());

                                        ViewGroup styleItemTagView = (ViewGroup) getLayoutInflater().inflate(R.layout.item_select_hair_style_tag, null, false);
                                        styleItemTagView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        TextView tv_selectItem = styleItemTagView.findViewById(R.id.tv_selectItem);
                                        tv_selectItem.setText(styleData.getTitle()+"");
                                        layout_selectItemGroup.addView(styleItemTagView);
                                    }






                                }
                            });
                            layout_styleItemContainer.addView(styleItemView);
                        }

                    } else {
                    }




        }

        @Override
        public void onError(int code, String msg) {

        }
    };

}
