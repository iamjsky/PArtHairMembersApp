package kr.co.parthair.android.members.ui.page.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.base.BaseFragment;
import kr.co.parthair.android.members.ui.page.login.LoginActivity;

/**
 * ClassName            LoginMainFragment
 * Created by JSky on   2021-09-28
 * <p>
 * Description
 */
public class LoginMainFragment extends BaseFragment {



    public LoginMainFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_main, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    //region onClick

    @OnClick(R.id.btn_login)
    public void btn_loginClicked(){

        ((LoginActivity)mParent).setFragmentPage(FRAGMENT_LOGIN_SELECT);

    }

    @OnClick(R.id.btn_signUp)
    public void btn_signUpClicked(){

        ((LoginActivity)mParent).setFragmentPage(FRAGMENT_LOGIN_SIGNUP);

    }

    //endregion

}
