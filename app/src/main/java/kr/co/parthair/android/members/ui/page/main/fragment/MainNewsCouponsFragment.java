package kr.co.parthair.android.members.ui.page.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import kr.co.parthair.android.members.model.Coupons;
import kr.co.parthair.android.members.model.NewsDataModel;
import kr.co.parthair.android.members.net.api.callback.GetCouponListCallback;
import kr.co.parthair.android.members.net.api.callback.GetNewsCallback;
import kr.co.parthair.android.members.ui.page.main.adapter.MainNewsCouponsAdapter;
import kr.co.parthair.android.members.ui.page.common.base.BaseFragment;

/**
 * ClassName            MainNewsNoticeFragment
 * Created by JSky on   2021-10-12
 * <p>
 * Description
 */
public class MainNewsCouponsFragment extends BaseFragment {

    @BindView(R.id.recv_body)
    RecyclerView recv_body;

    private MainNewsCouponsAdapter mainNewsCouponsAdapter;
    private int spanCount = 1;

    public MainNewsCouponsFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_news_coupons, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mParent);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recv_body.setLayoutManager(linearLayoutManager);
//        SnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(recv_body);


        etcApi.getCouponList(getCouponListCallback);


        return view;
    }


    //region onClick



    //endregion

    //region callback

    GetCouponListCallback getCouponListCallback = new GetCouponListCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable List<Coupons.CouponList> data) {
            if(data.size() > 0){
                mainNewsCouponsAdapter = new MainNewsCouponsAdapter(data);
                recv_body.setAdapter(mainNewsCouponsAdapter);
                recv_body.setLayoutManager(new GridLayoutManager(mParent, spanCount, RecyclerView.HORIZONTAL, false));	// 가로
            }


        }

        @Override
        public void onError(int code, String msg) {
            LOG_E("onError? : " + code + "," +msg);
        }
    };

    //endregion


}
