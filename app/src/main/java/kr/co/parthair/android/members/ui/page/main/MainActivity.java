package kr.co.parthair.android.members.ui.page.main;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager2.widget.ViewPager2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.ui.page.common.adapter.MainNoticeImageSliderAdapter;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;

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

    //region main top image slider(notice)

    @BindView(R.id.vp_noticeImageSlider)
    ViewPager2 vp_noticeImageSlider;

    @BindView(R.id.layout_noticeImageSliderIndicator)
    LinearLayout layout_noticeImageSliderIndicator;

    //dummy images
    private String[] images = new String[] {
            "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
    };



    private void setNoticeImageSlider(){
        //미리 로딩 할 다음 데이터 수
        vp_noticeImageSlider.setOffscreenPageLimit(1);

        vp_noticeImageSlider.setAdapter(new MainNoticeImageSliderAdapter(this, images));
        vp_noticeImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                LOG_I("onPageScrolled position>>" + position);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                LOG_I("onPageSelected position>>" + position);
                setCurrentNoticeImageIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                LOG_I("onPageScrollStateChanged state>>" + state);
            }
        });

        setupNoticeImageIndicator(images.length);

    }
    private void setupNoticeImageIndicator(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(10, 10, 10, 10);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.bg_indicator_default));
            indicators[i].setLayoutParams(params);
            layout_noticeImageSliderIndicator.addView(indicators[i]);
        }
        setCurrentNoticeImageIndicator(0);
    }

    private void setCurrentNoticeImageIndicator(int position) {
        int childCount = layout_noticeImageSliderIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layout_noticeImageSliderIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_default
                ));
            }
        }
    }

    //endregion


    @BindView(R.id.nsv_scrollView)
    NestedScrollView nsv_scrollView;
    @BindView(R.id.layout_topMenu)
    LinearLayout layout_topMenu;

    private long closeAppTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();

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


    void init(){
        setupActivity();

    }

    private void setupActivity(){

        //scrollview set
        getWindow().setStatusBarColor(getResources().getColor(R.color.ph_page_bg_color_02));
        nsv_scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LOG_D(scrollY+"");
                if(scrollY >= 45){
                    layout_topMenu.setVisibility(View.VISIBLE);
                    getWindow().setStatusBarColor(getResources().getColor(R.color.ph_menu_tab_color_01));
                }else{
                    layout_topMenu.setVisibility(View.GONE);
                    getWindow().setStatusBarColor(getResources().getColor(R.color.ph_page_bg_color_02));
                }

            }
        });

        setNoticeImageSlider();
        refreshUserInfo();
    }



    void refreshUserInfo(){
        GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
            @Override
            public void onSuccess(int code, String msg) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        };
        userApi.getUserInfo(getUserInfoCallback);
    }



    //region callback

//    GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
//        @Override
//        public void onSuccess(int code, String msg) {
//
//        }
//
//        @Override
//        public void onError(int code, String msg) {
//
//        }
//    };


    //endregion


}