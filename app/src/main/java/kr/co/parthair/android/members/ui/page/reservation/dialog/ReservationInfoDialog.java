package kr.co.parthair.android.members.ui.page.reservation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.model.MainHairStyle;
import kr.co.parthair.android.members.model.ReservationInfo;
import kr.co.parthair.android.members.net.api.MainApi;
import kr.co.parthair.android.members.net.api.ReservationApi;
import kr.co.parthair.android.members.net.api.callback.GetMainHairStyleCallback;
import kr.co.parthair.android.members.net.api.callback.GetReservationInfoCallback;
import kr.co.parthair.android.members.ui.page.reservation.ReservationActivity;

/**
 * ClassName            ReservationInfoDialog
 * Created by JSky on   2021-11-23
 * <p>
 * Description
 */
public class ReservationInfoDialog extends Dialog {
    @BindView(R.id.view_calendar)
    MaterialCalendarView view_calendar;


    protected ReservationApi reservationApi = new ReservationApi();
    private List<List<ReservationInfo.ReservationData>> reservationArr = new ArrayList<>();

    @BindViews({R.id.btn_1000, R.id.btn_1030, R.id.btn_1100,
            R.id.btn_1130, R.id.btn_1200, R.id.btn_1230,
            R.id.btn_1300, R.id.btn_1330, R.id.btn_1400,
            R.id.btn_1430, R.id.btn_1500, R.id.btn_1530,
            R.id.btn_1600, R.id.btn_1630, R.id.btn_1700,
            R.id.btn_1730, R.id.btn_1800, R.id.btn_1830,
            R.id.btn_1900})
    Button[] btn_times;

    String selectedTime = "";
    String selectedDate = "";
    Context mContext;

    public ReservationInfoDialog(Context context) {
        super(context, R.style.FullScreenDialog_visibleSystemUi);
        mContext = context;

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reservation_info);
        ButterKnife.bind(this);

        init();
    }

    void init(){
        view_calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                Date selectDate = null;
                try {
                    selectDate = sdf.parse(date.getYear()+"-"+date.getMonth()+"-"+date.getDay());
                    selectedDate = sdf.format(selectDate)+"";
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Toast.makeText(mContext, sdf.format(selectDate), Toast.LENGTH_SHORT).show();
            }
        });
        for(Button btn_time : btn_times){
            btn_time.setPaintFlags(0);
            btn_time.setTextColor(mContext.getColor(R.color.ph_main_color));
        }
        reservationApi.getReservationInfo(getReservationInfoCallback);
    }
    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked(){
        if(selectedDate.equals("") || selectedTime.equals("")){
            return;
        }
        ((ReservationActivity)mContext).selectedDate = selectedDate + " " + selectedTime;
        dismiss();

    }
    @OnClick(R.id.iv_back)
    public void iv_backClicked() {
        dismiss();

    }

    @Override
    public void onBackPressed() {
        dismiss();
    }


    @OnClick({R.id.btn_1000, R.id.btn_1030, R.id.btn_1100,
            R.id.btn_1130, R.id.btn_1200, R.id.btn_1230,
            R.id.btn_1300, R.id.btn_1330, R.id.btn_1400,
            R.id.btn_1430, R.id.btn_1500, R.id.btn_1530,
            R.id.btn_1600, R.id.btn_1630, R.id.btn_1700,
            R.id.btn_1730, R.id.btn_1800, R.id.btn_1830,
            R.id.btn_1900})
    public void btn_timesClicked(Button btn){
        Log.e("_____", btn.getText().toString());
        selectedTime = btn.getText()+"";
    }

    GetReservationInfoCallback getReservationInfoCallback = new GetReservationInfoCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable List<ReservationInfo.ReservationData> reservationData, @Nullable List<ReservationInfo.BlockTimeData> blockTimeData) {
            //Log.e("_____","???" + reservationData.size());
            if(blockTimeData.size() > 0){
                for (Button btn_time : btn_times) {
                    for (ReservationInfo.BlockTimeData _blockTimeData : blockTimeData) {

                        if (_blockTimeData.getBlockTime().equals(btn_time.getText().toString())) {
                            btn_time.setPaintFlags(btn_time.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                            btn_time.setTextColor(mContext.getColor(R.color.ph_light_gray_color_01));
                            btn_time.setEnabled(false);
                        }

                    }

                }
            }
            if(reservationData.size() > 0){
                for (Button btn_time : btn_times) {
                    for (ReservationInfo.ReservationData _reservationData : reservationData) {
                        Log.e("_____", _reservationData.getReservation_time() + ":" + btn_time.getText());
                        if (_reservationData.getReservation_time().equals(btn_time.getText().toString())) {
                            btn_time.setPaintFlags(btn_time.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                            btn_time.setTextColor(mContext.getColor(R.color.ph_light_gray_color_01));
                            btn_time.setEnabled(false);
                        }

                    }

                }
            }







        }

        @Override
        public void onError(int code, String msg) {
            Log.e("_____",code + "??!!!? : " + msg);
        }
    };

}
