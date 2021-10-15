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
import kr.co.parthair.android.members.model.NewsDataModel;


import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;

/**
 * ClassName            MainTabNoticeSliderAdapter
 * Created by JSky on   2021-10-12
 * <p>
 * Description
 */
public class MainNewsNoticeAdapter extends RecyclerView.Adapter<MainNewsNoticeAdapter.ViewHolder> {

public class ViewHolder extends RecyclerView.ViewHolder {
    LinearLayout layout_item;
    ImageView iv_item_icon;
    TextView tv_item_title;
    TextView tv_item_desc;



    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        layout_item = (LinearLayout) itemView.findViewById(R.id.layout_item);
        iv_item_icon = (ImageView) itemView.findViewById(R.id.iv_item_icon);
        tv_item_title = (TextView) itemView.findViewById(R.id.tv_item_title);
        tv_item_desc = (TextView) itemView.findViewById(R.id.tv_item_desc);
        float[] roundSize = {40,40,40,40,40,40,40,40};
        iv_item_icon.setBackground(new ShapeDrawable(new RoundRectShape(roundSize,null,null)));
        iv_item_icon.setClipToOutline(true);

    }
}

    private List<NewsDataModel.NewsData> mList = null;


    public MainNewsNoticeAdapter(List<NewsDataModel.NewsData> data) {
        mList = new ArrayList<>(data);


    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_main_news_notice, parent, false);
        ViewHolder vh = new MainNewsNoticeAdapter.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsDataModel.NewsData item = mList.get(position);

        Glide.with(holder.iv_item_icon.getContext()).load(R.color.ph_gray_color_01)
                .error(R.color.ph_gray_color_01)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(100, 100)
                .skipMemoryCache(true)
                .into(holder.iv_item_icon);

        holder.tv_item_title.setText("제목없음");
        holder.tv_item_desc.setText("내용없음");
        String icon_img_url = item.getIcon_img_url()+"";
        if(String_IsNotNull(icon_img_url)){
            Glide.with(holder.iv_item_icon.getContext()).load(icon_img_url)
                    .error(R.color.ph_gray_color_01)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .override(100, 100)
                    .skipMemoryCache(true)
                    .into(holder.iv_item_icon);
        }
        if(String_IsNotNull(item.getTitle())){
            holder.tv_item_title.setText(item.getTitle() + "");
        }
        if(String_IsNotNull(item.getContent())){
            holder.tv_item_desc.setText(item.getContent() + "");
        }



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}