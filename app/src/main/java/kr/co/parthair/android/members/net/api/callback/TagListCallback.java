package kr.co.parthair.android.members.net.api.callback;

import androidx.annotation.Nullable;

import java.util.List;

import kr.co.parthair.android.members.model.NewsDataModel;
import kr.co.parthair.android.members.model.TagListModel;

/**
 * ClassName            TagListCallback
 * Created by JSky on   2021-11-15
 * <p>
 * Description
 */
public interface TagListCallback {
    void onSuccess(int code, String msg, @Nullable List<TagListModel.TagInfo> data);
    void onError(int code, String msg);
}
