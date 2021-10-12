package kr.co.parthair.android.members.ui.page.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.BoardCategoryNum;
import kr.co.parthair.android.members.model.NewsDataModel;
import kr.co.parthair.android.members.net.api.callback.GetNewsCallback;
import kr.co.parthair.android.members.ui.page.common.adapter.MainNewsNoticeAdapter;
import kr.co.parthair.android.members.ui.page.common.base.BaseFragment;

/**
 * ClassName            MainNewsNoticeFragment
 * Created by JSky on   2021-10-12
 * <p>
 * Description
 */
public class MainNewsNoticeFragment extends BaseFragment implements BoardCategoryNum {

    @BindView(R.id.recv_body)
    RecyclerView recv_body;

    private MainNewsNoticeAdapter mainNewsNoticeAdapter;

    public MainNewsNoticeFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_news_notice, container, false);
        ButterKnife.bind(this, view);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mParent);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recv_body.setLayoutManager(linearLayoutManager);

        boardApi.getNews(getNewsCallback);


        return view;
    }


    //region onClick



    //endregion

    //region callback

    GetNewsCallback getNewsCallback = new GetNewsCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable NewsDataModel data) {
            mainNewsNoticeAdapter = new MainNewsNoticeAdapter(data.getNewsNoticeList());
            recv_body.setAdapter(mainNewsNoticeAdapter);
            recv_body.setLayoutManager(new LinearLayoutManager(mParent, RecyclerView.HORIZONTAL, false));	// 가로


        }

        @Override
        public void onError(int code, String msg) {
            LOG_E("onError? : " + code + "," +msg);
        }
    };

    //endregion
}
