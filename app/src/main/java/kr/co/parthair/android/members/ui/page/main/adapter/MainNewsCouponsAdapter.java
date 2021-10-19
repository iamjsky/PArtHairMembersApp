package kr.co.parthair.android.members.ui.page.main.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.model.Coupons;
import kr.co.parthair.android.members.model.NewsDataModel;

import static kr.co.parthair.android.members.utils.DateUtil.formatDateRemoveTime;
import static kr.co.parthair.android.members.utils.DateUtil.formatDateRemoveTime2;
import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;

/**
 * ClassName            MainNewsCouponsAdapter
 * Created by JSky on   2021-10-15
 * <p>
 * Description
 */
public class MainNewsCouponsAdapter extends RecyclerView.Adapter<MainNewsCouponsAdapter.ViewHolder> {

//    int[] couponBackgroundArr = {
//            R.drawable.bg_coupon_rounded_01,
//            R.drawable.bg_coupon_rounded_02,
//            R.drawable.bg_coupon_rounded_03,
//            R.drawable.bg_coupon_rounded_04,
//            R.drawable.bg_coupon_rounded_05
//    };
int[] couponBackgroundArr = {
        R.drawable.bg_coupons_01,
        R.drawable.bg_coupons_01,
        R.drawable.bg_coupons_01,
        R.drawable.bg_coupons_01,
        R.drawable.bg_coupons_01
};

    int beforeRandomNum = -1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_item;
        RelativeLayout layout_couponBody;
        TextView tv_item_title;
        TextView tv_item_desc;
        TextView tv_item_date;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = (LinearLayout) itemView.findViewById(R.id.layout_item);
            layout_couponBody = (RelativeLayout) itemView.findViewById(R.id.layout_couponBody);
            tv_item_title = (TextView) itemView.findViewById(R.id.tv_item_title);
            tv_item_desc = (TextView) itemView.findViewById(R.id.tv_item_desc);
            tv_item_date = (TextView) itemView.findViewById(R.id.tv_item_date);


        }
    }

    private List<Coupons.CouponList> mList = null;


    public MainNewsCouponsAdapter(List<Coupons.CouponList> data) {
        mList = new ArrayList<>(data);


    }

    Context mContext;
    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public MainNewsCouponsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_main_news_coupons, parent, false);
        MainNewsCouponsAdapter.ViewHolder vh = new MainNewsCouponsAdapter.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull MainNewsCouponsAdapter.ViewHolder holder, int position) {
        Coupons.CouponList item = mList.get(position);



        holder.tv_item_title.setText("제목없음");
        holder.tv_item_desc.setText("내용없음");
        holder.tv_item_date.setText("기간 무제한");
        int randomNum = beforeRandomNum;
        while(randomNum == beforeRandomNum){
            randomNum = (int) (Math.random() * couponBackgroundArr.length);
        }
        beforeRandomNum = randomNum;

            holder.layout_couponBody.setBackground(mContext.getDrawable(couponBackgroundArr[randomNum]));



        if(String_IsNotNull(item.getTitle())){
            holder.tv_item_title.setText(item.getTitle() + "");
        }
        if(String_IsNotNull(item.getContent())){
            holder.tv_item_desc.setText(item.getContent() + "");
        }
        if(String_IsNotNull(item.getStart_date()) && String_IsNotNull(item.getEnd_date())){
            holder.tv_item_date.setText(formatDateRemoveTime2(item.getStart_date()) + " ~ " + formatDateRemoveTime2(item.getEnd_date()));
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
