package kr.co.parthair.android.members.ui.page.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.common.MyPreferenceManager;
import kr.co.parthair.android.members.model.MainNoticeImage;
import kr.co.parthair.android.members.net.api.callback.GetMainNoticeImageCallback;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.ui.page.main.adapter.MainNoticeImageSliderAdapter;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.login.LoginActivity;
import kr.co.parthair.android.members.ui.page.main.dialog.VisitCallDialog;
import kr.co.parthair.android.members.ui.page.main.fragment.MainNewsCouponsFragment;
import kr.co.parthair.android.members.ui.page.main.fragment.MainNewsEventsFragment;
import kr.co.parthair.android.members.ui.page.main.fragment.MainNewsNoticeFragment;

import static kr.co.parthair.android.members.utils.DateUtil.formatDateRemoveTime;

public class MainActivity extends BaseActivity {

    //region navi drawer
    @BindView(R.id.layout_drawer)
    RelativeLayout layout_drawer;
    @BindView(R.id.sv_drawerBody)
    NestedScrollView sv_drawerBody;


    @OnClick(R.id.layout_drawer)
    public void layout_drawerClicked() {
        openDrawerClicked();
    }

    @OnClick({R.id.iv_openDrawer, R.id.iv_topMenuOpenDrawer})
    public void openDrawerClicked() {
        if (layout_drawer.getVisibility() == View.VISIBLE) {

            Animation closeAnim = AnimationUtils.loadAnimation(this, R.anim.drawer_left);
            sv_drawerBody.startAnimation(closeAnim);

            closeAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    layout_drawer.setVisibility(View.GONE);
                    sv_drawerBody.setVisibility(View.GONE);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


        } else {

            Animation openDrawerAnim = AnimationUtils.loadAnimation(this, R.anim.drawer_right);


            layout_drawer.setVisibility(View.VISIBLE);
            sv_drawerBody.setScrollY(0);
            sv_drawerBody.setVisibility(View.VISIBLE);

            sv_drawerBody.startAnimation(openDrawerAnim);

            openDrawerAnim.setAnimationListener(new Animation.AnimationListener() {
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

    //region noticeSlider

    @BindView(R.id.vp_noticeImageSlider)
    ViewPager2 vp_noticeImageSlider;

    @BindView(R.id.layout_noticeImageSliderIndicator)
    LinearLayout layout_noticeImageSliderIndicator;

    private Handler sliderHandler;
    int autoSlidingTime = 5000;
    MainNoticeImageSliderAdapter mainNoticeImageSliderAdapter;

//    private String[] images = new String[]{
//            "https://ldb-phinf.pstatic.net/20210907_148/1631010711173eGWJF_JPEG/rxw1GRNrsJd0HV3jtl6dOtsM.JPG.jpg",
//            "https://ldb-phinf.pstatic.net/20210907_281/1631011229560xrk2x_JPEG/wNKnh84O_Immhj-G63Tv5Wz4.JPG.jpg",
//            "https://ldb-phinf.pstatic.net/20210907_10/1631010837704VXfyi_JPEG/suj_hzUAKNO984WtYMHGNuLc.JPG.jpg",
//            "https://ldb-phinf.pstatic.net/20210913_228/1631463501681Q0sSq_JPEG/7dLb-SUMdH5AkkYTRq13L46A.jpeg.jpg",
//            "https://ldb-phinf.pstatic.net/20210907_111/16310109739858rsCM_JPEG/7i9IWXUaKVOnptoMkTmDldlJ.JPG.jpg",
//            "https://ldb-phinf.pstatic.net/20210907_73/1631011048715SX6Bv_JPEG/cIzLAdwLlnhBb5h_IM7FYcp-.JPG.jpg"
//    };


    boolean firstAnimStart = false;

    private void setNoticeImageSlider(MainNoticeImage mainNoticeImage) {


        LOG_E("mainNoticeImage.getSlideImageList().size() : " + mainNoticeImage.getSlideImageList().size()+"");
        sliderHandler = new Handler();
        //미리 로딩 할 다음 데이터 수
        vp_noticeImageSlider.setOffscreenPageLimit(1);
        mainNoticeImageSliderAdapter = new MainNoticeImageSliderAdapter(this, mainNoticeImage.getSlideImageList());
        vp_noticeImageSlider.setAdapter(mainNoticeImageSliderAdapter);

        vp_noticeImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
             //   LOG_I("onPageScrolled position>>" + position);
                if (!firstAnimStart) {
                    ImageView nowImageView = (ImageView) vp_noticeImageSlider.findViewWithTag("MainNoticeImage_" + vp_noticeImageSlider.getCurrentItem());
                    nowImageView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.image_zoom));
                    firstAnimStart = true;
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                //LOG_I("onPageSelected position>>" + position);
                setCurrentNoticeImageIndicator(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, autoSlidingTime);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                //   LOG_I("onPageScrollStateChanged state>>" + state);
                ImageView nowImageView = (ImageView) vp_noticeImageSlider.findViewWithTag("MainNoticeImage_" + vp_noticeImageSlider.getCurrentItem());

                if (state == 0) {
                    nowImageView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.image_zoom));
                } else if (state == 1) {

                    nowImageView.clearAnimation();

                }
            }
        });

        setupNoticeImageIndicator(mainNoticeImage.getSlideImageList().size());

    }


    private void setupNoticeImageIndicator(int count) {
        layout_noticeImageSliderIndicator.removeAllViews();
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

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            ImageView nowImageView = (ImageView) vp_noticeImageSlider.findViewWithTag("MainNoticeImage_" + vp_noticeImageSlider.getCurrentItem());
            nowImageView.clearAnimation();
            if (vp_noticeImageSlider.getCurrentItem() + 1 == mainNoticeImageSliderAdapter.getItemCount()) {

                vp_noticeImageSlider.setCurrentItem(0);

            } else {
                vp_noticeImageSlider.setCurrentItem(vp_noticeImageSlider.getCurrentItem() + 1);
            }

        }
    };
    //endregion


    @BindView(R.id.nsv_scrollView)
    NestedScrollView nsv_scrollView;
    @BindView(R.id.layout_topMenu)
    LinearLayout layout_topMenu;


    //region userInfo

    @BindView(R.id.layout_userLogged)
    LinearLayout layout_userLogged;
    @BindView(R.id.layout_userNotLogged)
    LinearLayout layout_userNotLogged;
    @BindView(R.id.iv_userInfo_userProfileImg)
    ImageView iv_userInfo_userProfileImg;
    @BindView(R.id.tv_userInfo_userName)
    TextView tv_userInfo_userName;
    @BindView(R.id.tv_userInfo_userCreatedDate)
    TextView tv_userInfo_userCreatedDate;
    @BindView(R.id.tv_userInfo_userLastVisitDate)
    TextView tv_userInfo_userLastVisitDate;
    @BindView(R.id.tv_userInfo_userPoints)
    TextView tv_userInfo_userPoints;
    @BindView(R.id.tv_userInfo_userCoupons)
    TextView tv_userInfo_userCoupons;


    //endregion


    //region news

    @BindViews({ R.id.btn_news_coupons, R.id.btn_news_events,R.id.btn_news_notice})
    Button[] btn_news_buttons;

    @BindView(R.id.layout_news)
    FrameLayout layout_news;
    public Fragment newsFragmentPage;


    //endregion

    private long closeAppTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LOG_E("MyInfo.instance.getUser_token()>>" + MyInfo.instance.getUser_token());
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sliderHandler != null) {
            sliderHandler.postDelayed(sliderRunnable, autoSlidingTime);
        }

        refreshData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sliderHandler != null) {
            sliderHandler.removeCallbacks(sliderRunnable);
        }

    }


    @Override
    public void onBackPressed() {
        if (layout_drawer.getVisibility() == View.VISIBLE) {
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


    void init() {
        setupActivity();

    }

    private void setupActivity() {


        Window w = getWindow();

        setStatusBarTransparent();

        //scrollview set

        nsv_scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
               // LOG_D(scrollY + "");
                if (scrollY >= 45) {

                    w.setStatusBarColor(getResources().getColor(R.color.ph_menu_tab_color_01));
                    layout_topMenu.setVisibility(View.VISIBLE);

                } else {

                    w.setStatusBarColor(Color.TRANSPARENT);
                    layout_topMenu.setVisibility(View.GONE);

                }

            }
        });



        btn_news_buttonsClicked(btn_news_buttons[0]);
        refreshData();


    }

    void refreshData() {
        refreshUserInfo();
        refreshMainNoticeImage();
    }

    void refreshUserInfo() {

        if (MyInfo.instance.getUser_token().equals("")) {

            layout_userLogged.setVisibility(View.GONE);
            layout_userNotLogged.setVisibility(View.VISIBLE);


            return;
        } else {
            layout_userLogged.setVisibility(View.VISIBLE);
            layout_userNotLogged.setVisibility(View.GONE);
        }

        iv_userInfo_userProfileImg.setBackground(new ShapeDrawable(new OvalShape()));
        iv_userInfo_userProfileImg.setClipToOutline(true);


        userApi.getUserInfo(getUserInfoCallback);
    }

    void refreshMainNoticeImage(){

        boardApi.getMainNoticeImage(getMainNoticeImageCallback);
    }

    public void setStatusBarTransparent() {

        Window w = getWindow();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            w.setStatusBarColor(Color.TRANSPARENT);

        }


    }



    public void setNewsFragmentPage(int page) {
        String tag = "";
        switch (page) {
            case FRAGMENT_MAIN_NEWS_NOTICE:
                newsFragmentPage = new MainNewsNoticeFragment();

                tag = FRAGMENT_MAIN_NEWS_NOTICE + "";
                break;
            case FRAGMENT_MAIN_NEWS_EVENTS:
                newsFragmentPage = new MainNewsEventsFragment();

                tag = FRAGMENT_MAIN_NEWS_EVENTS + "";
                break;
            case FRAGMENT_MAIN_NEWS_COUPONS:
                newsFragmentPage = new MainNewsCouponsFragment();

                tag = FRAGMENT_MAIN_NEWS_COUPONS + "";
                break;


        }
        if (newsFragmentPage != null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(layout_news.getId(), newsFragmentPage, tag);
            transaction.commitAllowingStateLoss();

        }
    }
    //region onClick

    @OnClick(R.id.layout_goLogin)
    public void layout_goLoginClicked() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_callVisitCheck)
    public void layout_callVisitCheckClicked() {
        VisitCallDialog visitCallDialog = new VisitCallDialog(this);
        visitCallDialog.show();

    }

    @OnClick(R.id.layout_naverReservation)
    public void layout_naverReservationClicked() {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(NAVER_RESERVATION_URL));
        startActivity(intent);

    }

    @OnClick(R.id.tv_userInfo_userName)
    public void tv_userInfo_userNameClicked() {

        MyPreferenceManager.setString(this, "user_token", "");

    }


    @OnClick({R.id.btn_news_notice, R.id.btn_news_events, R.id.btn_news_coupons})
    public void btn_news_buttonsClicked(View view) {


        for (Button buttons : btn_news_buttons) {
            if (buttons.getId() != view.getId()) {
                buttons.setActivated(false);
                buttons.setTextColor(getColor(R.color.ph_main_color));
                Typeface normalFont = ResourcesCompat.getFont(this, R.font.pretendard_regular);
                buttons.setTypeface(normalFont);

            } else {
                buttons.setActivated(true);
                buttons.setTextColor(getColor(R.color.white));
                Typeface boldFont = ResourcesCompat.getFont(this, R.font.pretendard_bold);
                buttons.setTypeface(boldFont);
            }
        }

        switch (view.getId()) {
            case R.id.btn_news_notice:
                setNewsFragmentPage(FRAGMENT_MAIN_NEWS_NOTICE);
                break;
            case R.id.btn_news_events:
                setNewsFragmentPage(FRAGMENT_MAIN_NEWS_EVENTS);
                break;
            case R.id.btn_news_coupons:
                setNewsFragmentPage(FRAGMENT_MAIN_NEWS_COUPONS);
                break;

        }

    }


    //endregion

    //region callback

    GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            String userProfileImgUrl = MyInfo.instance.getUserInfo().getUserProfileImg() + "";

            if (!userProfileImgUrl.equals("")) {
                Glide.with(mContext).load(MyInfo.instance.getUserInfo()
                        .getUserProfileImg()).error(R.drawable.ic_launcher_background)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .override(100, 100)
                        .skipMemoryCache(true)
                        .into(iv_userInfo_userProfileImg);
            } else {
                Glide.with(mContext).load(R.drawable.ic_launcher_background)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .override(100, 100)
                        .skipMemoryCache(true)
                        .into(iv_userInfo_userProfileImg);
            }

            String createdDate = MyInfo.instance.getUserInfo().getCreatedDate() + "";
            String lastVisitDate = MyInfo.instance.getUserInfo().getLastVisitDate() + "";
            String userCreatedDate = formatDateRemoveTime(createdDate);
            String userLastVisitDate = formatDateRemoveTime(lastVisitDate);

            tv_userInfo_userName.setText(MyInfo.instance.getUserInfo().getUserName() + "");
            tv_userInfo_userCreatedDate.setText(userCreatedDate + "");
            tv_userInfo_userLastVisitDate.setText(userLastVisitDate + "");
            tv_userInfo_userPoints.setText(MyInfo.instance.getUserInfo().getUserPoints() + "");
            tv_userInfo_userCoupons.setText("0");

        }

        @Override
        public void onError(int code, String msg) {

        }
    };

    GetMainNoticeImageCallback getMainNoticeImageCallback = new GetMainNoticeImageCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable MainNoticeImage data) {
            setNoticeImageSlider(data);
        }

        @Override
        public void onError(int code, String msg) {

        }
    };
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