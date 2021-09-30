package kr.co.parthair.android.members.ui.page.main;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.base.BaseActivity;

public class MainActivity extends BaseActivity {

    //region navi drawer
    @BindView(R.id.layout_drawer)
    RelativeLayout layout_drawer;
    @BindView(R.id.layout_body)
    LinearLayout layout_body;

    @OnClick(R.id.layout_drawer)
    public void layout_drawerClicked(){
        openDrawerClicked();
    }
    @OnClick(R.id.iv_openDrawer)
    public void openDrawerClicked(){
        if(layout_drawer.getVisibility() == View.VISIBLE){
            Animation openAnim = AnimationUtils.loadAnimation(this, R.anim.drawer_left);
            layout_body.startAnimation(openAnim);
            openAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    layout_drawer.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


        }else{
            Animation closeAnim = AnimationUtils.loadAnimation(this, R.anim.drawer_right);
            layout_drawer.setVisibility(View.VISIBLE);
            layout_body.startAnimation(closeAnim);
            closeAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }


    }



    //endregion

    @BindView(R.id.nsv_scrollView)
    NestedScrollView nsv_scrollView;

    private long closeAppTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        nsv_scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LOG_D(scrollY+"");
                if(scrollY >= 90){

                    getWindow().setStatusBarColor(getResources().getColor(R.color.white));
                }else{
                    getWindow().setStatusBarColor(getResources().getColor(R.color.ph_page_bg_color));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(layout_drawer.getVisibility() == View.VISIBLE){
            openDrawerClicked();
            return;
        }

        if (System.currentTimeMillis() - closeAppTime >= 2000) {
            closeAppTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), getString(R.string.msg_app_quit), Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - closeAppTime < 2000) {
            moveTaskToBack(true); // 태스크를 백그라운드로 이동
            finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기

            System.exit(0);
        }
    }





}