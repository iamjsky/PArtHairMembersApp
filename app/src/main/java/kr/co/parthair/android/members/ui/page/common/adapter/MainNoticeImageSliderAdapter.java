package kr.co.parthair.android.members.ui.page.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import kr.co.parthair.android.members.R;

/**
 * ClassName            MainNoticeImageSliderAdapter
 * Created by JSky on   2021-10-06
 * <p>
 * Description
 */
public class MainNoticeImageSliderAdapter extends RecyclerView.Adapter<MainNoticeImageSliderAdapter.SliderViewHolder> {

    private Context mContext;
    private String[] sliderImageArr;



    public MainNoticeImageSliderAdapter(Context context, String[] imageUrlArr) {

        mContext = context;
        sliderImageArr = imageUrlArr;

    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_notice_image_slider, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.bindSliderImage(sliderImageArr[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImageArr.length;
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_sliderImage);
        }

        public void bindSliderImage(String imageUrl) {
            Glide.with(mContext)
                    .load(imageUrl)
                    .into(mImageView);
        }
    }

}
