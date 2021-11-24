package kr.co.parthair.android.members.ui.page.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import butterknife.ButterKnife;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;

public class ReservationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        ButterKnife.bind(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}