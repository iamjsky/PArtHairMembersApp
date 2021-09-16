package kr.co.parthair.android.members.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}