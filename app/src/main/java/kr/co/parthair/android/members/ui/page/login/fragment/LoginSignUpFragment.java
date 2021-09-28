package kr.co.parthair.android.members.ui.page.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.base.BaseFragment;
import kr.co.parthair.android.members.ui.page.login.LoginActivity;

/**
 * ClassName            LoginSignUpFragment
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
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    void init(){

    }

    public void checkSignUp() {

        String inputPhoneNumber = edtxt_inputPhoneNumber.getText().toString() + "";

        if (!String_IsNotNull(inputPhoneNumber)) {

            Toast.makeText(this, "휴대폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;

        }

        userApi.checkSignUp(LOGIN_TYPE_PHONE, inputPhoneNumber, checkSignUpCallback);

    }


    //region onClick

    @OnClick(R.id.iv_back)
    public void iv_backClicked(){

        ((LoginActivity)mParent).onBackPressed();

    }

    @OnClick(R.id.btn_phoneSignUp)
    public void btn_phoneSignUpClicked(){

    }

    @OnClick(R.id.btn_kakaoSignUp)
    public void btn_kakaoSignUpClicked(){

    }



    //endregion


    //region callback



    //endregion

}
