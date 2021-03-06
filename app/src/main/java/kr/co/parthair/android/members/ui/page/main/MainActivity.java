package kr.co.parthair.android.members.ui.page.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.common.MyInfo;
import kr.co.parthair.android.members.common.MyPreferenceManager;
import kr.co.parthair.android.members.model.MainHairStyle;
import kr.co.parthair.android.members.model.MainNoticeImage;
import kr.co.parthair.android.members.model.MyReservation;
import kr.co.parthair.android.members.model.TagListModel;
import kr.co.parthair.android.members.net.api.callback.GetMainHairStyleCallback;
import kr.co.parthair.android.members.net.api.callback.GetMainNoticeImageCallback;
import kr.co.parthair.android.members.net.api.callback.GetMyReservationCallback;
import kr.co.parthair.android.members.net.api.callback.GetUserInfoCallback;
import kr.co.parthair.android.members.ui.page.login.LoginActivity;
import kr.co.parthair.android.members.ui.page.main.adapter.MainHairStyleAdapter;
import kr.co.parthair.android.members.ui.page.main.adapter.MainHairStyleSkeletonAdapter;
import kr.co.parthair.android.members.ui.page.main.adapter.MainNoticeImageSliderAdapter;
import kr.co.parthair.android.members.ui.page.common.base.BaseActivity;
import kr.co.parthair.android.members.ui.page.main.dialog.CallDialog;
import kr.co.parthair.android.members.ui.page.main.dialog.ReservationDialog;
import kr.co.parthair.android.members.ui.page.main.dialog.SocialLoginLinkDialog;
import kr.co.parthair.android.members.ui.page.main.dialog.UserBarcodeDialog;
import kr.co.parthair.android.members.ui.page.main.fragment.MainMyReservationFragment;
import kr.co.parthair.android.members.ui.page.main.fragment.MainNewsCouponsFragment;
import kr.co.parthair.android.members.ui.page.main.fragment.MainNewsEventsFragment;
import kr.co.parthair.android.members.ui.page.main.fragment.MainNewsNoticeFragment;
import kr.co.parthair.android.members.ui.page.reservation.ReservationActivity;
import kr.co.parthair.android.members.ui.page.splash.SplashActivity;
import kr.co.parthair.android.members.utils.NullCheckUtil;

import static kr.co.parthair.android.members.utils.DateUtil.formatDateRemoveTime;

public class MainActivity extends BaseActivity {

    //region navi drawer
    @BindView(R.id.layout_drawer)
    RelativeLayout layout_drawer;
    @BindView(R.id.sv_drawerBody)
    NestedScrollView sv_drawerBody;
    @BindView(R.id.tv_drawerHavePoints)
    TextView tv_drawerHavePoints;
    @BindView(R.id.tv_drawerUserName)
    TextView tv_drawerUserName;
    @BindView(R.id.layout_drawerUserNotLogged)
    LinearLayout layout_drawerUserNotLogged;
    @BindView(R.id.layout_drawerUserLogged)
    LinearLayout layout_drawerUserLogged;
    @BindView(R.id.tv_drawerUserLogout)
    TextView tv_drawerUserLogout;
    @BindView(R.id.iv_drawerGoTop)
    ImageView iv_drawerGoTop;
    @BindView(R.id.iv_drawerGoDown)
    ImageView iv_drawerGoDown;

    @OnClick(R.id.iv_drawerGoTop)
    public void iv_drawerGoTopClicked() {
        sv_drawerBody.post(new Runnable() {
            @Override
            public void run() {
                sv_drawerBody.scrollTo(0,0);
            }
        });

    }
    @OnClick(R.id.iv_drawerGoDown)
    public void iv_drawerGoDownClicked() {
        sv_drawerBody.post(new Runnable() {
            @Override
            public void run() {
                sv_drawerBody.scrollTo(0,sv_drawerBody.getBottom());
            }
        });

    }

    @OnClick(R.id.layout_drawer)
    public void layout_drawerClicked() {
        openDrawerClicked();
    }

    @OnClick({R.id.iv_openDrawer, R.id.iv_topMenuOpenDrawer})
    public void openDrawerClicked() {
        refreshUserInfo();
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


    @OnClick(R.id.layout_drawerReservation)
    public void layout_drawerReservationClicked(){
        Intent intent = new Intent(this, ReservationActivity.class);
        startActivity(intent);
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


        LOG_E("mainNoticeImage.getSlideImageList().size() : " + mainNoticeImage.getSlideImageList().size() + "");
        sliderHandler = new Handler();
        //?????? ?????? ??? ?????? ????????? ???
        vp_noticeImageSlider.setOffscreenPageLimit(1);
        mainNoticeImageSliderAdapter = new MainNoticeImageSliderAdapter(this, mainNoticeImage.getSlideImageList());
        vp_noticeImageSlider.setAdapter(mainNoticeImageSliderAdapter);

        vp_noticeImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                //   LOG_I("onPageScrolled position>>" + position);

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


    //region coupons

    @BindView(R.id.layout_couponContainer)
    FrameLayout layout_couponContainer;
    public Fragment couponFragmentPage;

    //endregion

    //region news

    @BindViews({R.id.btn_news_events, R.id.btn_news_notice})
    Button[] btn_news_buttons;

    @BindView(R.id.layout_news)
    FrameLayout layout_news;
    public Fragment newsFragmentPage;




    //endregion

    //region hairStyle

    @BindView(R.id.recv_hairStyle)
    RecyclerView recv_hairStyle;
    @BindView(R.id.layout_mainHairStyleEmpty)
    LinearLayout layout_mainHairStyleEmpty;


    MainHairStyleAdapter mainHairStyleAdapter;


    //endregion

    //region userBarcode

    @BindView(R.id.layout_userBarcode)
    RelativeLayout layout_userBarcode;

    @BindView(R.id.iv_userBarcode)
    ImageView iv_userBarcode;
    @BindView(R.id.tv_userBarcode_01)
    TextView tv_userBarcode_01;
    @BindView(R.id.tv_userBarcode_02)
    TextView tv_userBarcode_02;
    @BindView(R.id.tv_userBarcode_03)
    TextView tv_userBarcode_03;
    @BindView(R.id.tv_userBarcode_04)
    TextView tv_userBarcode_04;

    //endregion

    //region userReservation
    @BindView(R.id.layout_myReservationContainer)
    FrameLayout layout_myReservationContainer;
    public Fragment myReservationFragmentPage;

//    @BindView(R.id.tv_userReservationDate)
//    TextView tv_userReservationDate;
//    @BindView(R.id.tv_userReservationTime)
//    TextView tv_userReservationTime;
//    @BindView(R.id.tv_userReservationStyle)
//    TextView tv_userReservationStyle;
//    @BindView(R.id.tv_userReservationDesigner)
//    TextView tv_userReservationDesigner;


    //endregion

    private long closeAppTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LOG_E("MyInfo.instance.getUser_token()>>" + MyInfo.instance.getUser_token());
//        SocialLoginLinkDialog socialLoginLinkDialog = new SocialLoginLinkDialog(this);
//        socialLoginLinkDialog.show();

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
            moveTaskToBack(true); // ???????????? ?????????????????? ??????
            finishAndRemoveTask(); // ???????????? ?????? + ????????? ??????????????? ?????????

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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recv_hairStyle.setLayoutManager(linearLayoutManager);
        MainHairStyleSkeletonAdapter mainHairStyleSkeletonAdapter = new MainHairStyleSkeletonAdapter();
        recv_hairStyle.setAdapter(mainHairStyleSkeletonAdapter);

        btn_news_buttonsClicked(btn_news_buttons[0]);
        setCouponFragmentPage();
        refreshData();


    }

    void refreshData() {
        refreshUserInfo();
        refreshMainNoticeImage();
        refreshMainHairStyle();
        refreshReservation();

    }

    void refreshUserInfo() {



        if (!MyInfo.instance.isLogin()) {
            LOG_E("MyInfo.instance.isLogin() chchchcheck : " + MyInfo.instance.isLogin());

            layout_userLogged.setVisibility(View.GONE);
            layout_userNotLogged.setVisibility(View.VISIBLE);

            layout_drawerUserLogged.setVisibility(View.GONE);
            layout_drawerUserNotLogged.setVisibility(View.VISIBLE);

            layout_userBarcode.setVisibility(View.GONE);

            return;
        } else {


            layout_userLogged.setVisibility(View.VISIBLE);
            layout_userNotLogged.setVisibility(View.GONE);
            layout_drawerUserLogged.setVisibility(View.VISIBLE);
            layout_drawerUserNotLogged.setVisibility(View.GONE);

        }

        iv_userInfo_userProfileImg.setBackground(new ShapeDrawable(new OvalShape()));
        iv_userInfo_userProfileImg.setClipToOutline(true);


        userApi.getUserInfo(getUserInfoCallback);
    }

    void refreshMainNoticeImage() {

        mainApi.getMainNoticeImage(getMainNoticeImageCallback);
    }

    void refreshMainHairStyle() {
        mainApi.getMainHairStyle(getMainHairStyleCallback);
    }

    public void setStatusBarTransparent() {

        Window w = getWindow();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            w.setStatusBarColor(Color.TRANSPARENT);

        }


    }

    public void setCouponFragmentPage() {
        String tag = "";


                couponFragmentPage = new MainNewsCouponsFragment();

                tag = FRAGMENT_MAIN_COUPONS + "";





        if (couponFragmentPage != null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(layout_couponContainer.getId(), couponFragmentPage, tag);
            transaction.commitAllowingStateLoss();

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



        }
        if (newsFragmentPage != null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(layout_news.getId(), newsFragmentPage, tag);
            transaction.commitAllowingStateLoss();

        }
    }

    public void userLogout(){
        MyPreferenceManager.setString(this, "user_token", "");
        LOG_E("MyInfo.instance.getUser_token() : " + MyInfo.instance.getUser_token());
        MyInfo.instance.setUser_token("");
        MyPreferenceManager.setBoolean(mContext, "social_login_link_visible", false);
        LOG_E("getBoolean: " + MyPreferenceManager.getBoolean(mContext, "social_login_link_visible"));
        LOG_E("MyInfo.instance.isLogin() : " + MyInfo.instance.isLogin());
        LOG_E("MyInfo.instance.getUser_token() : " + MyInfo.instance.getUser_token());
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    public void refreshReservation(){
        LOG_E("MyInfo.instance.isLogin()!!!!" + MyInfo.instance.isLogin());
        String tag = "";


        myReservationFragmentPage = new MainMyReservationFragment();

        tag = FRAGMENT_MAIN_MY_RESERVATION + "";





        if (myReservationFragmentPage != null) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(layout_myReservationContainer.getId(), myReservationFragmentPage, tag);
            transaction.commitAllowingStateLoss();

        }







    }
    //region onClick

    @OnClick({R.id.layout_goLogin, R.id.layout_drawerGoLogin})
    public void layout_goLoginClicked() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_callVisitCheck)
    public void layout_callVisitCheckClicked() {
        CallDialog callDialog = new CallDialog(this, "???????????? ?????????(CALL) ??????",
                "?????????19 ?????? ????????? ?????? ??????????????? ??????\n????????? ????????? ????????? ?????????.\n\n080-205-0000\n\n?????????(CALL) ????????? ???????????????????",
                VISIT_CHECK_CALL_NUMBER);
        callDialog.show();

    }

    @OnClick(R.id.layout_naverReservation)
    public void layout_naverReservationClicked() {

        ReservationDialog reservationDialog = new ReservationDialog(this);
        reservationDialog.show();


    }

    @OnClick(R.id.tv_drawerUserLogout)
    public void tv_drawerUserLogout() {
        if(!MyInfo.instance.isLogin()){
            layout_goLoginClicked();
        }else{
            userLogout();
        }


    }


    @OnClick({R.id.btn_news_notice, R.id.btn_news_events})
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


        }

    }

    @OnClick(R.id.layout_userBarcode)
    public void layout_userBarcodeClicked(){
        String userName = MyInfo.instance.getUserInfo().getUserName() + "";
        String userBarcodeNumber = MyInfo.instance.getUserInfo().getUserBarcode() + "";
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        UserBarcodeDialog userBarcodeDialog = new UserBarcodeDialog(this, userName, userBarcodeNumber);
        userBarcodeDialog.show();
        userBarcodeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });
    }



    //endregion

    //region callback

    GetUserInfoCallback getUserInfoCallback = new GetUserInfoCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            if(MyInfo.instance.isLogin()){
                String userProfileImgUrl = MyInfo.instance.getUserInfo().getUserProfileImg() + "";

                if (!userProfileImgUrl.equals("")) {
                    Glide.with(mContext).load(MyInfo.instance.getUserInfo()
                            .getUserProfileImg()).error(R.color.ph_main_color)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .override(100, 100)
                            .skipMemoryCache(true)
                            .into(iv_userInfo_userProfileImg);
                } else {
                    Glide.with(mContext).load(R.color.ph_main_color)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .override(100, 100)
                            .skipMemoryCache(true)
                            .into(iv_userInfo_userProfileImg);
                }
                layout_userBarcode.setVisibility(View.VISIBLE);
                String userBarcodeNumber = MyInfo.instance.getUserInfo().getUserBarcode() + "";
                if(NullCheckUtil.String_IsNotNull(userBarcodeNumber)){
                    Bitmap barcodeBitmap = null;
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        float barcodeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 230f, getResources().getDisplayMetrics());
                        float barcodeHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, getResources().getDisplayMetrics());
                        BitMatrix bitMatrix = multiFormatWriter.encode(userBarcodeNumber, BarcodeFormat.CODE_128, (int)barcodeWidth, (int)barcodeHeight);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        barcodeBitmap = barcodeEncoder.createBitmap(bitMatrix);
                        Glide.with(mContext).load(barcodeBitmap).error(R.color.ph_main_color)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .override((int)barcodeWidth, (int)barcodeHeight)
                                .skipMemoryCache(true)
                                .into(iv_userBarcode);
                        tv_userBarcode_01.setText(userBarcodeNumber.substring(0, 4));
                        tv_userBarcode_02.setText(userBarcodeNumber.substring(4, 8));
                        tv_userBarcode_03.setText(userBarcodeNumber.substring(8, 12));
                        tv_userBarcode_04.setText(userBarcodeNumber.substring(12, 16));
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }else{
                    tv_userBarcode_01.setText("");
                    tv_userBarcode_02.setText("");
                    tv_userBarcode_03.setText("");
                    tv_userBarcode_04.setText("");
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

                tv_drawerUserName.setText(MyInfo.instance.getUserInfo().getUserName() + "");
                tv_drawerHavePoints.setText(MyInfo.instance.getUserInfo().getUserPoints() + "");

                //social linked check
                LOG_E("getBoolean22: " + MyPreferenceManager.getBoolean(mContext, "social_login_link_visible"));
                if(!MyPreferenceManager.getBoolean(mContext, "social_login_link_visible")){
                    if(!NullCheckUtil.String_IsNotNull(MyInfo.instance.getUserInfo().getKakaoId()) || MyInfo.instance.getUserInfo().getKakaoId().equals("-1")){
                        MyPreferenceManager.setBoolean(mContext, "social_login_link_visible", true);
                        SocialLoginLinkDialog socialLoginLinkDialog = new SocialLoginLinkDialog(mContext);
                        socialLoginLinkDialog.show();
                    }
                }

            } else{
                layout_userBarcode.setVisibility(View.GONE);

            }



        }

        @Override
        public void onError(int code, String msg) {
            layout_userBarcode.setVisibility(View.GONE);
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
    GetMainHairStyleCallback getMainHairStyleCallback = new GetMainHairStyleCallback() {
        @Override
        public void onSuccess(int code, String msg, @Nullable MainHairStyle data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (data.getHairData() != null && data.getHairData().size() > 0) {
                        layout_mainHairStyleEmpty.setVisibility(View.GONE);
                        mainHairStyleAdapter = new MainHairStyleAdapter(data.getHairData());
                        recv_hairStyle.setAdapter(mainHairStyleAdapter);
                        mainHairStyleAdapter.notifyDataSetChanged();
                        for(int i=0; i < data.getHairData().size(); i++){
                            List<TagListModel.TagInfo> styleTagList = data.getHairData().get(i).getStyleTag();
                            for(int j=0; j < styleTagList.size(); j++){
                                LOG_I(styleTagList.get(j).getName()+"");
                            }
                        }
                    } else {
                        layout_mainHairStyleEmpty.setVisibility(View.VISIBLE);
                    }
                }
            });


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