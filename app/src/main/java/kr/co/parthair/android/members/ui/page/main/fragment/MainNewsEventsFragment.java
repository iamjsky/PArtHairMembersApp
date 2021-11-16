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

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.model.NewsDataModel;
import kr.co.parthair.android.members.net.api.callback.GetNewsCallback;
import kr.co.parthair.android.members.ui.page.main.adapter.MainNewsEventsAdapter;
import kr.co.parthair.android.members.ui.page.common.base.BaseFragment;
import kr.co.parthair.android.members.ui.page.main.adapter.MainNewsSkeletonAdapter;

/**
 * ClassName            MainNewsNoticeFragment
 * Created by JSky on   2021-10-12
 * <p>
 * Description
 */
public class MainNewsEventsFragment extends BaseFragment {

    @BindView(R.id.recv_body)
    RecyclerView recv_body;

    private MainNewsEventsAdapter mainNewsEventsAdapter;
    private int spanCount = 2;

    public MainNewsEventsFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_news_events, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mParent);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recv_body.setLayoutManager(linearLayoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recv_body);

        MainNewsSkeletonAdapter mainNewsSkeletonAdapter = new MainNewsSkeletonAdapter();
        recv_body.setAdapter(mainNewsSkeletonAdapter);
        recv_body.setLayoutManager(new GridLayoutManager(mParent, spanCount, RecyclerView.HORIZONTAL, false));	// 가로

        mainApi.getNews(getNewsCallback);


        return view;
    }


    //region onClick



    //endregion

    //region callback

    GetNewsCallback getNewsCallback = new GetNewsCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable NewsDataModel data) {
            if(data.getNewsEventsList().size() > 0){
                mainNewsEventsAdapter = new MainNewsEventsAdapter(data.getNewsEventsList());
                recv_body.setAdapter(mainNewsEventsAdapter);
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
