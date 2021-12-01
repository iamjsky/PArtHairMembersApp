package kr.co.parthair.android.members.ui.page.reservation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.model.ApplyReservation;
import kr.co.parthair.android.members.model.BusinessHour;
import kr.co.parthair.android.members.model.MainHairStyle;
import kr.co.parthair.android.members.model.TagListModel;
import kr.co.parthair.android.members.net.api.callback.ApplyReservationCallback;
import kr.co.parthair.android.members.net.api.callback.GetBusinessHourCallback;
import kr.co.parthair.android.members.net.api.callback.GetMainHairStyleCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.main.adapter.MainHairStyleAdapter;
import kr.co.parthair.android.members.ui.page.reservation.dialog.CheckReservationDialog;
import kr.co.parthair.android.members.ui.page.reservation.dialog.DesignerInfoDialog;
import kr.co.parthair.android.members.ui.page.reservation.dialog.ReservationInfoDialog;
import kr.co.parthair.android.members.ui.page.reservation.dialog.SelectStyleDialog;

public class ReservationActivity extends BaseActivity {

    public List<Integer> selectedStyle = null;
    public String selectedDate = "";
    public String selectedDesigner = "";
    public int selectedDesIdx = -1;
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

    @BindView(R.id.btn_dateSelect)
    Button btn_dateSelect;
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
        selectedStyle = null;
        selectedDate = "";

        super.onBackPressed();
    }

    void init() {


        mainApi.getMainHairStyle(getMainHairStyleCallback);


    }
    //region onClick

    @OnClick(R.id.iv_back)
    public void iv_backClicked(){
        onBackPressed();
    }

    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked(){
        String user_name = MyInfo.instance.getUserInfo().getUserName() + "";
        String user_phone = MyInfo.instance.getUserInfo().getUserPhone() + "";
        SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd (E) a HH:mm");
        Date selectDate = null;
        try {
            LOG_E("selectedDate : " + selectedDate);
            selectDate = sdf.parse(selectedDate);
            selectedDate = _sdf.format(selectDate)+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String reservation_date = selectedDate + "";
        String hs_list = tv_styleInput.getText().toString() + "";
        int des_idx = selectedDesIdx;
        String memo = "memo";

        //Toast.makeText(mContext, des_idx + "," + reservation_date + "," + hs_list, Toast.LENGTH_SHORT).show();
        CheckReservationDialog checkReservationDialog = new CheckReservationDialog(this, reservation_date, hs_list, selectedDesigner, des_idx, memo);
        checkReservationDialog.show();

    }

    @OnClick(R.id.btn_designerSelect)
    public void btn_designerSelectClicked(){
        selectedDesigner = "";
        selectedDesIdx = -1;
        DesignerInfoDialog designerInfoDialog = new DesignerInfoDialog(this);
        designerInfoDialog.show();

        designerInfoDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!selectedDesigner.equals("") && selectedDesIdx != -1) {

                    tv_designerInput.setText(selectedDesigner);
                    tv_designerInput.setBackgroundResource(R.drawable.bg_rounded_08);
                    tv_designerInput.setTextColor(getColor(R.color.ebay_blue));
                    tv_designer.setTextColor(getColor(R.color.ebay_blue));
                    btn_designerSelect.setEnabled(true);
                    btn_dateSelect.setEnabled(true);
                    btn_styleSelect.setEnabled(true);


                } else {
                    tv_designerInput.setText("");
                    tv_designerInput.setBackgroundResource(R.drawable.bg_rounded_04);
                    tv_designerInput.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    tv_designer.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    btn_designerSelect.setEnabled(true);
                    btn_dateSelect.setEnabled(false);
                    btn_styleSelect.setEnabled(false);

                }
            }
        });
    }

    @OnClick(R.id.btn_dateSelect)
    public void btn_dateSelectClicked() {
        selectedDate = "";
        ReservationInfoDialog reservationInfoDialog = new ReservationInfoDialog(this, selectedDesIdx);
        reservationInfoDialog.show();

        reservationInfoDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!selectedDate.equals("")) {
                    SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:m");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd (E) a HH:mm");
                    Date selectDate = null;
                    try {
                        selectDate = _sdf.parse(selectedDate);
                        selectedDate = sdf.format(selectDate)+"";
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tv_dateInput.setText(selectedDate);
                    tv_dateInput.setBackgroundResource(R.drawable.bg_rounded_08);
                    tv_dateInput.setTextColor(getColor(R.color.ebay_blue));
                    tv_date.setTextColor(getColor(R.color.ebay_blue));
                    btn_designerSelect.setEnabled(true);
                    btn_dateSelect.setEnabled(true);
                    btn_styleSelect.setEnabled(true);


                } else {
                    tv_dateInput.setText("");
                    tv_dateInput.setBackgroundResource(R.drawable.bg_rounded_04);
                    tv_dateInput.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    tv_date.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    btn_designerSelect.setEnabled(true);
                    btn_dateSelect.setEnabled(true);
                    btn_styleSelect.setEnabled(false);

                }
            }
        });
//        tv_dateInput.setBackgroundResource(R.drawable.bg_rounded_08);
    }

    @OnClick(R.id.btn_styleSelect)
    public void btn_styleSelectClicked() {
        selectedStyle = null;
        selectedStyle = new ArrayList<Integer>();
        SelectStyleDialog selectStyleDialog = new SelectStyleDialog(this);
        selectStyleDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                LOG_E("selectedStyle SIZE : " + selectedStyle.size());
                if (selectedStyle.size() > 0 && hairStyleDataList != null) {
                    StringBuilder sb = new StringBuilder();

                    for (int selectedStyleIdx : selectedStyle) {
                        for (MainHairStyle.HairStyleData data : hairStyleDataList) {
                            if (data.getIdx() == selectedStyleIdx) {
                                if (sb.length() > 0) {
                                    sb.append(", " + data.getTitle());
                                } else {
                                    sb.append(data.getTitle());
                                }

                            }
                        }
                    }
                    tv_styleInput.setText(sb.toString() + "");
                    tv_styleInput.setBackgroundResource(R.drawable.bg_rounded_08);
                    tv_styleInput.setTextColor(getColor(R.color.ebay_blue));
                    tv_style.setTextColor(getColor(R.color.ebay_blue));
                    btn_designerSelect.setEnabled(true);
                    btn_dateSelect.setEnabled(true);
                    btn_styleSelect.setEnabled(true);
                } else {
                    tv_styleInput.setText("");
                    tv_styleInput.setBackgroundResource(R.drawable.bg_rounded_04);
                    tv_styleInput.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    tv_style.setTextColor(getColor(R.color.ph_light_gray_color_06));
                    btn_designerSelect.setEnabled(true);
                    btn_dateSelect.setEnabled(true);
                    btn_styleSelect.setEnabled(true);
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