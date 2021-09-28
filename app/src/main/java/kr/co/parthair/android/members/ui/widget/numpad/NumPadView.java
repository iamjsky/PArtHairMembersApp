package kr.co.parthair.android.members.ui.widget.numpad;

import android.content.Context;
import android.content.res.TypedArray;
import android.telephony.PhoneNumberUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.data.CommonInfo;


/**
 * ClassName            NumPadView
 * Created by JSky on   2021-09-23
 * <p>
 * Description
 */
public class NumPadView extends RelativeLayout {

    TextView tv_desc, tv_numPadPhone;
    LinearLayout layout_numPadPassword;
    RelativeLayout layout_numPad;
    static final String PHONE = "phone";
    static final String PASSWORD = "password";
    static final String PASSWORD_CHECK = "password_check";
    String type = "phone";
    private NumPadFinishOnClickListener listener;
    String phoneText="";
    String passwordText = "";
    String passwordCheckText = "";
    Context mContext;
    ImageView iv_password_01, iv_password_02, iv_password_03, iv_password_04;



    public interface NumPadFinishOnClickListener {
        void onClick(View view, String data);
    }
    public NumPadView(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    public NumPadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        getAttrs(attrs);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.view_numpad, this, false);
        addView(v);
        ButterKnife.bind(this);

        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_numPadPhone = (TextView) findViewById(R.id.tv_numPadPhone);
//        tv_numPadPassword = (TextView) findViewById(R.id.tv_numPadPassword);

        layout_numPadPassword = (LinearLayout) findViewById(R.id.layout_numPadPassword);
        layout_numPad = (RelativeLayout) findViewById(R.id.layout_numPad);

        iv_password_01 = (ImageView) findViewById(R.id.iv_password_01);
        iv_password_02 = (ImageView) findViewById(R.id.iv_password_02);
        iv_password_03 = (ImageView) findViewById(R.id.iv_password_03);
        iv_password_04 = (ImageView) findViewById(R.id.iv_password_04);


    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NumPadView);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NumPadView, defStyle, 0);
        setTypeArray(typedArray);
    }


    public void setVisible(String _type, NumPadFinishOnClickListener listener){
        CommonInfo.instance.isNumPadVisible = true;
        phoneText = "";
        passwordText = "";
        passwordCheckText = "";
        tv_numPadPhone.setText("");
        iv_password_01.setImageResource(R.drawable.bg_password_round_default_01);
        iv_password_02.setImageResource(R.drawable.bg_password_round_default_01);
        iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
        iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
        //tv_numPadPassword.setText("");
        this.listener = listener;
        switch (_type) {
            case PHONE:
                type = PHONE;
                tv_desc.setText("휴대폰 번호를 입력해 주세요");
                tv_numPadPhone.setVisibility(VISIBLE);
                layout_numPadPassword.setVisibility(GONE);

                break;
            case PASSWORD:
                type = PASSWORD;
                tv_desc.setText("비밀번호 4자리를 입력해 주세요");
                tv_numPadPhone.setVisibility(GONE);
                layout_numPadPassword.setVisibility(VISIBLE);
                break;
            case PASSWORD_CHECK:
                type = PASSWORD_CHECK;
                tv_desc.setText("비밀번호 확인 4자리를 입력해 주세요");
                tv_numPadPhone.setVisibility(GONE);
                layout_numPadPassword.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
        this.setVisibility(VISIBLE);
    }
    private void setTypeArray(TypedArray typedArray) {

        if (typedArray.getString(R.styleable.NumPadView_type) != null) {
            String type_string = typedArray.getString(R.styleable.NumPadView_type);
            switch (type_string) {
                case PHONE:
                    type = PHONE;
                    tv_desc.setText("휴대폰 번호를 입력해 주세요");
                    tv_numPadPhone.setVisibility(VISIBLE);
                    layout_numPadPassword.setVisibility(GONE);
                    break;
                case PASSWORD:
                    type = PASSWORD;
                    tv_desc.setText("비밀번호 4자리를 입력해 주세요");
                    tv_numPadPhone.setVisibility(GONE);
                    layout_numPadPassword.setVisibility(VISIBLE);
                    break;
                case PASSWORD_CHECK:
                    type = PASSWORD_CHECK;
                    tv_desc.setText("비밀번호 확인 4자리를 입력해 주세요");
                    tv_numPadPhone.setVisibility(GONE);
                    layout_numPadPassword.setVisibility(VISIBLE);
                    break;
                default:
                    break;
            }
        }


        typedArray.recycle();
    }

   void setText(String text_string) {
        tv_desc.setText(text_string);
    }

    void setText(int text_resID) {
        tv_desc.setText(text_resID);
    }

    @OnClick(R.id.layout_numPadBack)
    public void layout_numPadBackClicked() {
        CommonInfo.instance.isNumPadVisible = false;
        this.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_numPadInputFinish)
    public void btn_numPadInputFinishClicked() {

        switch (type) {
            case PHONE:
                if(phoneText.length() == 0) {
                    Toast.makeText(mContext, "휴대폰 번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                }else{
                    CommonInfo.instance.isNumPadVisible = false;
                    this.setVisibility(View.GONE);
                    listener.onClick(this, phoneText);
                }


                break;
            case PASSWORD:
                if(passwordText.length() < 4){
                    Toast.makeText(mContext, "비밀번호 4자리를 입력해 주세요", Toast.LENGTH_SHORT).show();

                }else{
                    CommonInfo.instance.isNumPadVisible = false;
                    this.setVisibility(View.GONE);
                    listener.onClick(this, passwordText);
                }

            break;
            case PASSWORD_CHECK:
                if(passwordCheckText.length() < 4){
                    Toast.makeText(mContext, "비밀번호 확인 4자리를 입력해 주세요", Toast.LENGTH_SHORT).show();

                }else{
                    CommonInfo.instance.isNumPadVisible = false;
                    this.setVisibility(View.GONE);
                    listener.onClick(this, passwordCheckText);
                }

                break;
            default:
                break;

        }


    }



    @OnClick({R.id.btn_numPad_0, R.id.btn_numPad_1, R.id.btn_numPad_2,
            R.id.btn_numPad_3, R.id.btn_numPad_4, R.id.btn_numPad_5,
            R.id.btn_numPad_6, R.id.btn_numPad_7, R.id.btn_numPad_8,
            R.id.btn_numPad_9, R.id.btn_numPad_cancel, R.id.btn_numPad_delete})
    public void btn_numPadClicked(View view) {
        switch (type) {
            case PHONE:
                String numPadNumber = tv_numPadPhone.getText().toString() + "";
                switch (view.getId()) {
                    case R.id.btn_numPad_0:
                        numPadNumber += "0";
                        break;
                    case R.id.btn_numPad_1:
                        numPadNumber += "1";
                        break;
                    case R.id.btn_numPad_2:
                        numPadNumber += "2";
                        break;
                    case R.id.btn_numPad_3:
                        numPadNumber += "3";
                        break;
                    case R.id.btn_numPad_4:
                        numPadNumber += "4";
                        break;
                    case R.id.btn_numPad_5:
                        numPadNumber += "5";
                        break;
                    case R.id.btn_numPad_6:
                        numPadNumber += "6";
                        break;
                    case R.id.btn_numPad_7:
                        numPadNumber += "7";
                        break;
                    case R.id.btn_numPad_8:
                        numPadNumber += "8";
                        break;
                    case R.id.btn_numPad_9:
                        numPadNumber += "9";
                        break;
                    case R.id.btn_numPad_cancel:
                        numPadNumber = "";
                        break;
                    case R.id.btn_numPad_delete:
                        // LOG_D("numPadNumber.length()>>" + numPadNumber.length());
                        if (numPadNumber.length() > 0) {
                            numPadNumber = numPadNumber.substring(0, numPadNumber.length() - 1);
                        } else {
                            numPadNumber = "";
                        }
                        break;

                    default:
                        break;
                }

                tv_numPadPhone.setText(
                        PhoneNumberUtils.formatNumber(numPadNumber, Locale.getDefault().getCountry()) + "");
                phoneText = tv_numPadPhone.getText().toString()+"";
                phoneText = phoneText.replace("-", "");
                break;

            case PASSWORD:

                    String numPadNumberPw = passwordText + "";
                    switch (view.getId()) {
                        case R.id.btn_numPad_0:
                            numPadNumberPw += "0";
                            break;
                        case R.id.btn_numPad_1:
                            numPadNumberPw += "1";
                            break;
                        case R.id.btn_numPad_2:
                            numPadNumberPw += "2";
                            break;
                        case R.id.btn_numPad_3:
                            numPadNumberPw += "3";
                            break;
                        case R.id.btn_numPad_4:
                            numPadNumberPw += "4";
                            break;
                        case R.id.btn_numPad_5:
                            numPadNumberPw += "5";
                            break;
                        case R.id.btn_numPad_6:
                            numPadNumberPw += "6";
                            break;
                        case R.id.btn_numPad_7:
                            numPadNumberPw += "7";
                            break;
                        case R.id.btn_numPad_8:
                            numPadNumberPw += "8";
                            break;
                        case R.id.btn_numPad_9:
                            numPadNumberPw += "9";
                            break;
                        case R.id.btn_numPad_cancel:
                            numPadNumberPw = "";
                            iv_password_01.setImageResource(R.drawable.bg_password_round_default_01);
                            iv_password_02.setImageResource(R.drawable.bg_password_round_default_01);
                            iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                            iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                            break;
                        case R.id.btn_numPad_delete:
                            // LOG_D("numPadNumber.length()>>" + numPadNumber.length());
                            if (numPadNumberPw.length() > 0) {
                                numPadNumberPw = numPadNumberPw.substring(0, numPadNumberPw.length() - 1);

                            } else {
                                numPadNumberPw = "";

                            }
                            break;

                        default:
                            break;
                    }
                   // Log.e("NumPadView", numPadNumberPw + "," + numPadNumberPw.length());
                    switch(numPadNumberPw.length()){
                      case 0 :
                          iv_password_01.setImageResource(R.drawable.bg_password_round_default_01);
                          iv_password_02.setImageResource(R.drawable.bg_password_round_default_01);
                          iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                          iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                        break;
                        case 1 :
                            iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                            iv_password_02.setImageResource(R.drawable.bg_password_round_default_01);
                            iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                            iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                            break;
                        case 2 :
                            iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                            iv_password_02.setImageResource(R.drawable.bg_password_round_full_01);
                            iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                            iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                            break;
                        case 3 :
                            iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                            iv_password_02.setImageResource(R.drawable.bg_password_round_full_01);
                            iv_password_03.setImageResource(R.drawable.bg_password_round_full_01);
                            iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                            break;
                        case 4 :
                        default:
                            iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                            iv_password_02.setImageResource(R.drawable.bg_password_round_full_01);
                            iv_password_03.setImageResource(R.drawable.bg_password_round_full_01);
                            iv_password_04.setImageResource(R.drawable.bg_password_round_full_01);
                            break;
                    }


                    if(numPadNumberPw.length() > 4){
                        return;
                    }else{
                        //tv_numPadPassword.setText(numPadNumberPw + "");
                        passwordText = numPadNumberPw + "";
                    }









                break;
            case PASSWORD_CHECK:

                String numPadNumberPwCheck = passwordCheckText + "";
                switch (view.getId()) {
                    case R.id.btn_numPad_0:
                        numPadNumberPwCheck += "0";
                        break;
                    case R.id.btn_numPad_1:
                        numPadNumberPwCheck += "1";
                        break;
                    case R.id.btn_numPad_2:
                        numPadNumberPwCheck += "2";
                        break;
                    case R.id.btn_numPad_3:
                        numPadNumberPwCheck += "3";
                        break;
                    case R.id.btn_numPad_4:
                        numPadNumberPwCheck += "4";
                        break;
                    case R.id.btn_numPad_5:
                        numPadNumberPwCheck += "5";
                        break;
                    case R.id.btn_numPad_6:
                        numPadNumberPwCheck += "6";
                        break;
                    case R.id.btn_numPad_7:
                        numPadNumberPwCheck += "7";
                        break;
                    case R.id.btn_numPad_8:
                        numPadNumberPwCheck += "8";
                        break;
                    case R.id.btn_numPad_9:
                        numPadNumberPwCheck += "9";
                        break;
                    case R.id.btn_numPad_cancel:
                        numPadNumberPwCheck = "";
                        iv_password_01.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                        break;
                    case R.id.btn_numPad_delete:
                        // LOG_D("numPadNumber.length()>>" + numPadNumber.length());
                        if (numPadNumberPwCheck.length() > 0) {
                            numPadNumberPwCheck = numPadNumberPwCheck.substring(0, numPadNumberPwCheck.length() - 1);

                        } else {
                            numPadNumberPwCheck = "";

                        }
                        break;

                    default:
                        break;
                }
                // Log.e("NumPadView", numPadNumberPw + "," + numPadNumberPw.length());
                switch(numPadNumberPwCheck.length()){
                    case 0 :
                        iv_password_01.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                        break;
                    case 1 :
                        iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                        break;
                    case 2 :
                        iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_default_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                        break;
                    case 3 :
                        iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_default_01);
                        break;
                    case 4 :
                    default:
                        iv_password_01.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_02.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_03.setImageResource(R.drawable.bg_password_round_full_01);
                        iv_password_04.setImageResource(R.drawable.bg_password_round_full_01);
                        break;
                }


                if(numPadNumberPwCheck.length() > 4){
                    return;
                }else{
                    //tv_numPadPassword.setText(numPadNumberPw + "");
                    passwordCheckText = numPadNumberPwCheck + "";
                }









                break;
            default:
                break;
        }


    }

}
