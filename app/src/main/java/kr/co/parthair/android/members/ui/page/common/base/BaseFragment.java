package kr.co.parthair.android.members.ui.page.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.co.parthair.android.members.common.FragmentPageCode;
import kr.co.parthair.android.members.common.HttpResponseCode;
import kr.co.parthair.android.members.common.MyConstants;
import kr.co.parthair.android.members.net.api.UserApi;

/**
 * ClassName            BaseFragment
 * Created by JSky on   2021-09-28
 * <p>
 * Description
 */
public class BaseFragment extends Fragment implements MyConstants, HttpResponseCode, FragmentPageCode {

    protected BaseActivity mParent;
    protected UserApi userApi = new UserApi();
    protected String pageTag = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageTag = getTag() +"";
        mParent = (BaseActivity) getActivity();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
