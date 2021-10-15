package kr.co.parthair.android.members.ui.page.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.model.MainNoticeImage;

/**
 * ClassName            MainNoticeImageSliderAdapter
 * Created by JSky on   2021-10-06
 * <p>
 * Description
 */
public class MainNoticeImageSliderAdapter extends RecyclerView.Adapter<MainNoticeImageSliderAdapter.SliderViewHolder> {

    private Context mContext;
    private List<MainNoticeImage.SlideImage> sliderImageList;
    View mView;


    public MainNoticeImageSliderAdapter(Context context, List<MainNoticeImage.SlideImage> sliderImageList) {

        mContext = context;
        this.sliderImageList = sliderImageList;

    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_notice_image_slider, parent, false);


        return new SliderViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        holder.bindSliderImage(sliderImageList.get(position).getImg_url());
        mView.setTag("MainNoticeImage_"+position);
    }

    @Override
    public int getItemCount() {
        return sliderImageList.size();
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
