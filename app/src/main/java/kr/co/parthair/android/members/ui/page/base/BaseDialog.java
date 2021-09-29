package kr.co.parthair.android.members.ui.page.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import kr.co.parthair.android.members.R;

/**
 * ClassName            BaseDialog
 * Created by JSky on   2021-09-29
 * <p>
 * Description
 */
public class BaseDialog extends Dialog {
    protected Context mContext;
    public BaseDialog(Context context){
        super( context );
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        if( window != null ) {
            // 백그라운드 투명
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams params = window.getAttributes();
            // 화면에 가득 차도록
            params.width         = WindowManager.LayoutParams.MATCH_PARENT;
            params.height        = WindowManager.LayoutParams.MATCH_PARENT;

            // 열기&닫기 시 애니메이션 설정
            params.windowAnimations = R.style.AnimationPopupStyle;
            window.setAttributes( params );
            // UI 하단 정렬
            window.setGravity( Gravity.CENTER );
        }
    }

}
