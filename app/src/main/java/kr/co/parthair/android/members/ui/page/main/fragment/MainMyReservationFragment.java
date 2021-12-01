package kr.co.parthair.android.members.ui.page.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.model.Coupons;
import kr.co.parthair.android.members.model.MyReservation;
import kr.co.parthair.android.members.model.ReservationInfo;
import kr.co.parthair.android.members.net.api.callback.GetCouponListCallback;
import kr.co.parthair.android.members.net.api.callback.GetMyReservationCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseFragment;
import kr.co.parthair.android.members.ui.page.main.adapter.MainMyReservationAdapter;
import kr.co.parthair.android.members.ui.page.main.adapter.MainNewsCouponsAdapter;
import kr.co.parthair.android.members.ui.page.main.adapter.MainNewsEventsAdapter;
import kr.co.parthair.android.members.ui.page.main.adapter.MainNewsSkeletonAdapter;

/**
 * ClassName            MainMyReservationFragment
 * Created by JSky on   2021-12-01
 * <p>
 * Description
 */
public class MainMyReservationFragment  extends BaseFragment {

    @BindView(R.id.recv_body)
    RecyclerView recv_body;
    @BindView(R.id.tv_emptyMyReservationList)
    TextView tv_emptyMyReservationList;

    @BindView(R.id.iv_leftArrow)
    ImageView iv_leftArrow;
    @BindView(R.id.iv_rightArrow)
    ImageView iv_rightArrow;

    private MainMyReservationAdapter mainMyReservationAdapter;
    private int spanCount = 1;

    public MainMyReservationFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_my_reservation, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mParent);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recv_body.setLayoutManager(linearLayoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recv_body);

        iv_leftArrow.setVisibility(View.GONE);
        iv_rightArrow.setVisibility(View.GONE);

        if(!MyInfo.instance.isLogin()){

            recv_body.setVisibility(View.GONE);
            tv_emptyMyReservationList.setText("로그인 후 예약 내역을 확인할 수 있습니다.");
            tv_emptyMyReservationList.setVisibility(View.VISIBLE);
        }else{

            recv_body.setVisibility(View.VISIBLE);
            tv_emptyMyReservationList.setVisibility(View.GONE);
            reservationApi.getMyReservation(getMyReservationCallback);
        }



        return view;
    }


    //region onClick


    //endregion

    //region callback

    GetMyReservationCallback getMyReservationCallback = new GetMyReservationCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable List<MyReservation.MyReservationData> data) {
            if (data.size() > 0) {
                if(data.size() > 1){
                    iv_leftArrow.setVisibility(View.VISIBLE);
                    iv_rightArrow.setVisibility(View.VISIBLE);
                }else{
                    iv_leftArrow.setVisibility(View.GONE);
                    iv_rightArrow.setVisibility(View.GONE);
                }
                tv_emptyMyReservationList.setVisibility(View.GONE);
                mainMyReservationAdapter = new MainMyReservationAdapter(data);
                recv_body.setAdapter(mainMyReservationAdapter);
                recv_body.setLayoutManager(new GridLayoutManager(mParent, spanCount, RecyclerView.HORIZONTAL, false));    // 가로
            } else {
                iv_leftArrow.setVisibility(View.GONE);
                iv_rightArrow.setVisibility(View.GONE);
                recv_body.setVisibility(View.GONE);
                tv_emptyMyReservationList.setText("예약 내역이 없습니다.");
                tv_emptyMyReservationList.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public void onError(int code, String msg) {
            LOG_E("onError? : " + code + "," + msg);
            iv_leftArrow.setVisibility(View.GONE);
            iv_rightArrow.setVisibility(View.GONE);
            recv_body.setVisibility(View.GONE);
            if(code == NO_CONTENT){
                tv_emptyMyReservationList.setText("예약 내역이 없습니다.");
            }else{
                tv_emptyMyReservationList.setText("예약 내역을 받아올 수 없습니다.");
            }

            tv_emptyMyReservationList.setVisibility(View.VISIBLE);
        }
    };

//endregion


}