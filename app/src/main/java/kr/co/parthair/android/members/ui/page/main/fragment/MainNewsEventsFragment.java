package kr.co.parthair.android.members.ui.page.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.BoardCategoryNum;
import kr.co.parthair.android.members.model.NewsDataModel;
import kr.co.parthair.android.members.net.api.callback.GetNewsCallback;
import kr.co.parthair.android.members.ui.page.common.base.BaseFragment;

/**
 * ClassName            MainNewsNoticeFragment
 * Created by JSky on   2021-10-12
 * <p>
 * Description
 */
public class MainNewsEventsFragment extends BaseFragment implements BoardCategoryNum {

    @BindView(R.id.recv_body)
    RecyclerView recv_body;

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


        boardApi.getNews(getNewsCallback);


        return view;
    }


    //region onClick



    //endregion

    //region callback

    GetNewsCallback getNewsCallback = new GetNewsCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable NewsDataModel data) {




        }

        @Override
        public void onError(int code, String msg) {
            LOG_E("onError? : " + code + "," +msg);
        }
    };

    //endregion



}
