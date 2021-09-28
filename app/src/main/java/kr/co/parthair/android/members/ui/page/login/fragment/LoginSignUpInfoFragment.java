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
 * ClassName            LoginSignUpInfoFragment
 * Created by JSky on   2021-09-28
 * <p>
 * Description
 */
public class LoginSignUpInfoFragment extends BaseFragment {

    public LoginSignUpInfoFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_signup_info, container, false);


        return view;
    }
}
