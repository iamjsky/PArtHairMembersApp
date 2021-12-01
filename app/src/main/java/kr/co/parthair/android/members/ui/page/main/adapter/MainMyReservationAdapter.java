package kr.co.parthair.android.members.ui.page.main.adapter;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.model.MyReservation;
import kr.co.parthair.android.members.model.NewsDataModel;

import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;

/**
 * ClassName            MainNewsEventsAdapter
 * Created by JSky on   2021-10-15
 * <p>
 * Description
 */
public class MainMyReservationAdapter extends RecyclerView.Adapter<MainMyReservationAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_userReservationDate;
        TextView tv_userReservationTime;
        TextView tv_userReservationStyle;
        TextView tv_userReservationDesigner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_userReservationDate = (TextView) itemView.findViewById(R.id.tv_userReservationDate);
            tv_userReservationTime = (TextView) itemView.findViewById(R.id.tv_userReservationTime);
            tv_userReservationStyle = (TextView) itemView.findViewById(R.id.tv_userReservationStyle);
            tv_userReservationDesigner = (TextView) itemView.findViewById(R.id.tv_userReservationDesigner);


        }
    }

    private List<MyReservation.MyReservationData> mList = null;


    public MainMyReservationAdapter(List<MyReservation.MyReservationData> data) {
        mList = new ArrayList<>(data);


    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public MainMyReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_main_my_reservation, parent, false);
        MainMyReservationAdapter.ViewHolder vh = new MainMyReservationAdapter.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull MainMyReservationAdapter.ViewHolder holder, int position) {
        MyReservation.MyReservationData item = mList.get(position);

        if(String_IsNotNull(item.getReservationDate())){
            holder.tv_userReservationDate.setText(item.getReservationDate() + "");
        }
        if(String_IsNotNull(item.getReservation_time())){
            holder.tv_userReservationTime.setText(item.getReservation_time() + "");
        }
        if(String_IsNotNull(item.getHs_list())){
            holder.tv_userReservationStyle.setText(item.getHs_list() + "");
        }
        if(String_IsNotNull(item.getDesigner())){
            holder.tv_userReservationDesigner.setText(item.getDesigner() + "");
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
