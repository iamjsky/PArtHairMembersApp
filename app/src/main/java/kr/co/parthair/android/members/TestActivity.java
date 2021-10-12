package kr.co.parthair.android.members;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;


import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.login.fragment.LoginMainFragment;
import kr.co.parthair.android.members.ui.page.login.fragment.LoginSelectFragment;
import kr.co.parthair.android.members.ui.page.login.fragment.LoginSignUpFragment;
import kr.co.parthair.android.members.ui.page.login.fragment.LoginSignUpInfoFragment;
import kr.co.parthair.android.members.ui.page.main.fragment.MainNewsCouponsFragment;
import kr.co.parthair.android.members.ui.page.main.fragment.MainNewsEventsFragment;
import kr.co.parthair.android.members.ui.page.main.fragment.MainNewsNoticeFragment;

public class TestActivity extends BaseActivity {



    @BindView(R.id.layout_fragment)
    FrameLayout layout_fragment;
    public Fragment fragmentPage;

    public int nowFragmentPage = -1;

    boolean isShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_view);
        ButterKnife.bind(this);

        setFragmentPage(0);


    }

    @OnClick(R.id.btn_test1)
    public void btn_test1Clicked(){
        setFragmentPage(0);
    }
    @OnClick(R.id.btn_test2)
    public void btn_test2Clicked(){
        setFragmentPage(1);
    }
    @OnClick(R.id.btn_test3)
    public void btn_test3Clicked(){
        setFragmentPage(2);
    }
    public void setFragmentPage(int page) {
        String tag = "";
        switch (page) {
            case 0:
                fragmentPage = new MainNewsNoticeFragment();
                nowFragmentPage = 0;
                tag = 0 + "";
                break;
            case 1:
                fragmentPage = new MainNewsEventsFragment();
                nowFragmentPage = 1;
                tag = 1 + "";
                break;
            case 2:
                fragmentPage = new MainNewsCouponsFragment();
                nowFragmentPage = 2;
                tag = 2 + "";
                break;


        }
        if (fragmentPage != null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(layout_fragment.getId(), fragmentPage, tag);
            transaction.commitAllowingStateLoss();

        }
    }
}