package kr.co.parthair.android.members.ui.page.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.co.parthair.android.members.R;
import kr.co.parthair.android.members.model.NewsDataModel;

/**
 * ClassName            MainTabNoticeSliderAdapter
 * Created by JSky on   2021-10-12
 * <p>
 * Description
 */
public class MainNewsNoticeAdapter extends RecyclerView.Adapter<MainNewsNoticeAdapter.ViewHolder> {

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView iv_item_01,iv_item_02,iv_item_03,iv_item_04,iv_item_05;
    TextView tv_item_title_01,tv_item_title_02,tv_item_title_03,tv_item_title_04,tv_item_title_05;
    TextView tv_item_desc_01, tv_item_desc_02,tv_item_desc_03,tv_item_desc_04,tv_item_desc_05;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        iv_item_01 = (ImageView) itemView.findViewById(R.id.iv_item_01);
        iv_item_02 = (ImageView) itemView.findViewById(R.id.iv_item_02);
        iv_item_03 = (ImageView) itemView.findViewById(R.id.iv_item_03);
        iv_item_04 = (ImageView) itemView.findViewById(R.id.iv_item_04);
        iv_item_05 = (ImageView) itemView.findViewById(R.id.iv_item_05);
        tv_item_title_01 = (TextView) itemView.findViewById(R.id.tv_item_title_01);
        tv_item_title_02 = (TextView) itemView.findViewById(R.id.tv_item_title_02);
        tv_item_title_03 = (TextView) itemView.findViewById(R.id.tv_item_title_03);
        tv_item_title_04 = (TextView) itemView.findViewById(R.id.tv_item_title_04);
        tv_item_title_05 = (TextView) itemView.findViewById(R.id.tv_item_title_05);
        tv_item_desc_01 = (TextView) itemView.findViewById(R.id.tv_item_desc_01);
        tv_item_desc_02 = (TextView) itemView.findViewById(R.id.tv_item_desc_02);
        tv_item_desc_03 = (TextView) itemView.findViewById(R.id.tv_item_desc_03);
        tv_item_desc_04 = (TextView) itemView.findViewById(R.id.tv_item_desc_04);
        tv_item_desc_05 = (TextView) itemView.findViewById(R.id.tv_item_desc_05);

    }
}

    private List<List<NewsDataModel.NewsData>> mList = null;
    private List<NewsDataModel.NewsData> mDataList = null;

    public MainNewsNoticeAdapter(List<NewsDataModel.NewsData> data) {
        mList = new ArrayList<>();
        mDataList = new ArrayList<>();
        for(int i=0; i < data.size(); i++){
            if(((i) % 5) != 0){

                mDataList.add(data.get(i));
            }else{
                if(i != 0){
                    mList.add(mDataList);
                }

            }
        }
        if(data.size() < 5){
            mList.add(mDataList);
        }

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
       // NewsDataModel.NewsData item = mList.get(position);
            for(List<NewsDataModel.NewsData> listItem : mList.get(position)){

            }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}