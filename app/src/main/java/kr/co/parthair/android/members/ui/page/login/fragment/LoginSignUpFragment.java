package kr.co.parthair.android.members.ui.page.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.base.BaseFragment;

/**
 * ClassName            LoginMainFragment
 * Created by JSky on   2021-09-28
 * <p>
 * Description
 */
public class LoginSignUpFragment extends BaseFragment {

    public LoginSignUpFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_signup, container, false);


        return view;
    }
}
