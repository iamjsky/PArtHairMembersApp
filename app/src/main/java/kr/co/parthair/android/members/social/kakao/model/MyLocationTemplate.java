package kr.co.parthair.android.members.social.kakao.model;

import com.kakao.sdk.template.model.Button;
import com.kakao.sdk.template.model.Content;
import com.kakao.sdk.template.model.DefaultTemplate;
import com.kakao.sdk.template.model.Social;

import java.util.List;

import lombok.Data;

@Data
public class MyLocationTemplate implements DefaultTemplate {

    public String address;
    public Content content;
    public String addressTitle;
    public Social social;
    public List<Button> buttons;
    public String buttonTitle;

    /**
     * "location" 고정 값
     */
    public String objectType;



}
