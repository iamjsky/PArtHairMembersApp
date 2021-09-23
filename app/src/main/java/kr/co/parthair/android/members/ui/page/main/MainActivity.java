package kr.co.parthair.android.members.ui.page.main;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
    @OnClick(R.id.openDrawer)
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        if(layout_drawer.getVisibility() == View.VISIBLE){
            openDrawerClicked();
            return;
        }
        super.onBackPressed();
    }
}