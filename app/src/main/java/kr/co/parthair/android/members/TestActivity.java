package kr.co.parthair.android.members;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.scrollviewtest)
    NestedScrollView scrollviewtest;

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sliding_layout;

    boolean isShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_view);
        ButterKnife.bind(this);



        scrollviewtest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {

                    if (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                        sliding_layout.setTouchEnabled(false);
                        sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                        sliding_layout.setTouchEnabled(true);
                        return true;
                    }


                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED && scrollviewtest.getScrollY() <= 0) {

                        sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);


                    }
                }
                return false;
            }
        });
        scrollviewtest.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("_____", scrollY + "," + sliding_layout.getPanelState());
                if (scrollY <= 0 && sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);


                }
            }
        });


    }


}