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

import kr.co.parthair.android.members.R;

/**
 * ClassName            MainTabNoticeSliderAdapter
 * Created by JSky on   2021-10-12
 * <p>
 * Description
 */
public class MainNewsSkeletonAdapter extends RecyclerView.Adapter<MainNewsSkeletonAdapter.ViewHolder> {

public class ViewHolder extends RecyclerView.ViewHolder {




    public ViewHolder(@NonNull View itemView) {
        super(itemView);



    }
}



    public MainNewsSkeletonAdapter() {



    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_main_news_skeleton, parent, false);
        ViewHolder vh = new MainNewsSkeletonAdapter.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 2;
    }


}