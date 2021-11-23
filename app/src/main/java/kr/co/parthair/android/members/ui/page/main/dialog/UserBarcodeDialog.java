package kr.co.parthair.android.members.ui.page.main.dialog;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.StringUtils;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.ui.page.common.base.BaseFullStyleDialog;
import kr.co.parthair.android.members.utils.NullCheckUtil;

/**
 * ClassName            UserBarcodeDialog
 * Created by JSky on   2021-11-23
 * <p>
 * Description
 */
public class UserBarcodeDialog extends BaseFullStyleDialog {

    @BindView(R.id.iv_userBarcode)
    ImageView iv_userBarcode;
    @BindView(R.id.tv_userBarcode)
    TextView tv_userBarcode;
    @BindView(R.id.tv_userName)
    TextView tv_userName;

    String userName = "";
    String userBarcodeNumber = "";

    public UserBarcodeDialog(Context context, String userName, String userBarcodeNumber) {
        super(context);
        this.userBarcodeNumber = userBarcodeNumber;
        this.userName = userName;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_user_barcode);
        ButterKnife.bind(this);
        if(NullCheckUtil.String_IsNotNull(userBarcodeNumber)){
            Bitmap barcodeBitmap = null;
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                float barcodeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, mContext.getResources().getDisplayMetrics());
                float barcodeHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, mContext.getResources().getDisplayMetrics());
                BitMatrix bitMatrix = multiFormatWriter.encode(userBarcodeNumber, BarcodeFormat.CODE_128, (int)barcodeWidth, (int)barcodeHeight);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                barcodeBitmap = barcodeEncoder.createBitmap(bitMatrix);
                Glide.with(mContext).load(barcodeBitmap).error(R.color.ph_main_color)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .override((int)barcodeWidth, (int)barcodeHeight)
                        .skipMemoryCache(true)
                        .into(iv_userBarcode);
                tv_userName.setText(userName);
                tv_userBarcode.setText(userBarcodeNumber);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }else{
            tv_userName.setText("");
            tv_userBarcode.setText("");
        }
    }

    @OnClick(R.id.btn_confirm)
    public void btn_confirmClicked(){
        dismiss();

    }

}
