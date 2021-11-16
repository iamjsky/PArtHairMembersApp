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
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.List;

import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.model.MainHairStyle;
import kr.co.parthair.android.members.model.NewsDataModel;
import kr.co.parthair.android.members.model.TagListModel;

import static kr.co.parthair.android.members.utils.NullCheckUtil.String_IsNotNull;

/**
 * ClassName            MainTabNoticeSliderAdapter
 * Created by JSky on   2021-10-12
 * <p>
 * Description
 */
public class MainHairStyleAdapter extends RecyclerView.Adapter<MainHairStyleAdapter.ViewHolder> {

public class ViewHolder extends RecyclerView.ViewHolder {
    LinearLayout layout_item;
    SelectableRoundedImageView iv_hairStyle;
    TextView tv_title,tv_tags;




    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        layout_item = (LinearLayout) itemView.findViewById(R.id.layout_item);
        iv_hairStyle = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_hairStyle);
        tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        tv_tags = (TextView) itemView.findViewById(R.id.tv_tags);


    }
}

    private List<MainHairStyle.HairStyleData> mList = null;


    public MainHairStyleAdapter(List<MainHairStyle.HairStyleData> data) {
        mList = new ArrayList<>(data);


    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_main_hair_style, parent, false);
        ViewHolder vh = new MainHairStyleAdapter.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainHairStyle.HairStyleData item = mList.get(position);

        Glide.with(holder.iv_hairStyle.getContext()).load(R.color.ph_gray_color_01)
                .error(R.color.ph_gray_color_01)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(100, 100)
                .skipMemoryCache(true)
                .into(holder.iv_hairStyle);

        holder.tv_title.setText("제목없음");
        holder.tv_tags.setText("");

        String thumbImgUrl = item.getStyleThumbImgUrl()+"";
        if(String_IsNotNull(thumbImgUrl)){
            Glide.with(holder.iv_hairStyle.getContext()).load(thumbImgUrl)
                    .error(R.color.ph_gray_color_01)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .override(320, 480)
                    .skipMemoryCache(true)
                    .into(holder.iv_hairStyle);
        }
        if(String_IsNotNull(item.getTitle())){
            holder.tv_title.setText(item.getTitle() + "");
        }
        if(item.getStyleTag().size() > 0){
            StringBuilder sb = new StringBuilder();
            for(TagListModel.TagInfo tags : item.getStyleTag()){
                String tagName = tags.getName()+"";
                if(String_IsNotNull(tagName)){
                    sb.append("#" + tags.getName() + " ");
                }else{
                    sb.append("#" + item.getTitle());
                }


            }
            holder.tv_tags.setText(sb.toString() + "");
        }else{

        }




    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}