package kr.co.parthair.android.members.ui.page.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import kr.co.parthair.android.members.common.FragmentPageCode;
import kr.co.parthair.android.members.common.HttpResponseCode;
import kr.co.parthair.android.members.common.MyConstants;
import kr.co.parthair.android.members.net.api.UserApi;


public class BaseActivity extends AppCompatActivity implements MyConstants, HttpResponseCode, FragmentPageCode {

    protected Context mContext;
    protected UserApi userApi = new UserApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }

}