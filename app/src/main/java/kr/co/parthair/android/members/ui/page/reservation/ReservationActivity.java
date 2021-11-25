package kr.co.parthair.android.members.ui.page.reservation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.model.MainHairStyle;
import kr.co.parthair.android.members.model.TagListModel;
import kr.co.parthair.android.members.net.api.callback.GetMainHairStyleCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.main.adapter.MainHairStyleAdapter;
import kr.co.parthair.android.members.ui.page.reservation.dialog.SelectStyleDialog;

public class ReservationActivity extends BaseActivity {

    public List<Integer> selectedStyle = null;
    public List<MainHairStyle.HairStyleData> hairStyleDataList = new ArrayList<>();

    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_style)
    TextView tv_style;
    @BindView(R.id.tv_designer)
    TextView tv_designer;


    @BindView(R.id.tv_dateInput)
    TextView tv_dateInput;
    @BindView(R.id.tv_designerInput)
    TextView tv_designerInput;
    @BindView(R.id.tv_styleInput)
    TextView tv_styleInput;

    @BindView(R.id.btn_styleSelect)
    Button btn_styleSelect;
    @BindView(R.id.btn_designerSelect)
    Button btn_designerSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        ButterKnife.bind(this);
        init();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void init(){
        mainApi.getMainHairStyle(getMainHairStyleCallback);

    }
    //region onClick

    @OnClick(R.id.btn_dateSelect)
    public void btn_dateSelectClicked(){
        tv_dateInput.setBackgroundResource(R.drawable.bg_rounded_08);
    }
    @OnClick(R.id.btn_styleSelect)
    public void btn_styleSelectClicked(){
        selectedStyle = null;
        selectedStyle = new ArrayList<Integer>();
        SelectStyleDialog selectStyleDialog = new SelectStyleDialog(this);
        selectStyleDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                LOG_E("selectedStyle SIZE : " + selectedStyle.size());
                if(selectedStyle.size() > 0 && hairStyleDataList != null){
                    StringBuilder sb = new StringBuilder();
//                    for(MainHairStyle.HairStyleData data : hairStyleDataList){
//                        for(int selectedStyleIdx : selectedStyle){
//                            if(data.getIdx() == selectedStyleIdx){
//                                if(sb.length() > 0){
//                                    sb.append(", " + data.getTitle());
//                                }else{
//                                    sb.append(data.getTitle());
//                                }
//
//                            }
//                        }
//                    }
                    for(int selectedStyleIdx : selectedStyle){
                        for(MainHairStyle.HairStyleData data : hairStyleDataList){
                            if(data.getIdx() == selectedStyleIdx){
                                if(sb.length() > 0){
                                    sb.append(", " + data.getTitle());
                                }else{
                                    sb.append(data.getTitle());
                                }

                            }
                        }
                    }
                    tv_styleInput.setText(sb.toString()+"");
                    tv_styleInput.setBackgroundResource(R.drawable.bg_rounded_08);
                    tv_styleInput.setTextColor(getColor(R.color.ebay_blue));
                    tv_style.setTextColor(getColor(R.color.ebay_blue));
                }else{
                    tv_styleInput.setText("");
                    tv_styleInput.setBackgroundResource(R.drawable.bg_rounded_04);
                    tv_styleInput.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    tv_style.setTextColor(getColor(R.color.ph_light_gray_color_06));
                }

            }
        });
        selectStyleDialog.show();
    }

    //endregion

    //region callback

    GetMainHairStyleCallback getMainHairStyleCallback = new GetMainHairStyleCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable MainHairStyle data) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (data.getHairData() != null && data.getHairData().size() > 0) {
                        hairStyleDataList = data.getHairData();
                    } else {
                        hairStyleDataList = null;
                    }
                }
            });


        }

        @Override
        public void onError(int code, String msg) {
        }
    };

    //endregion

}