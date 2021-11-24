package kr.co.parthair.android.members.ui.page.common.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.FragmentPageCode;
import kr.co.parthair.android.members.common.HttpResponseCode;
import kr.co.parthair.android.members.common.MyConstants;
import kr.co.parthair.android.members.net.api.MainApi;
import kr.co.parthair.android.members.net.api.EtcApi;
import kr.co.parthair.android.members.net.api.ReservationApi;
import kr.co.parthair.android.members.net.api.UserApi;
import kr.co.parthair.android.members.ui.page.common.dialog.LoadingDialog;


public class BaseActivity extends AppCompatActivity implements MyConstants, HttpResponseCode, FragmentPageCode {

    protected Context mContext;
    LoadingDialog loadingDialog = null;
    protected UserApi userApi = new UserApi();
    protected MainApi mainApi = new MainApi();
    protected EtcApi etcApi = new EtcApi();
    protected ReservationApi reservationApi = new ReservationApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }
    public void setLoading(boolean value) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (value) {

                    loadingDialog.show();
                } else {
                    if (loadingDialog != null) {
                        loadingDialog.dismiss();
                    }

                }
            }
        });


    }
}