package kr.co.parthair.android.members.social.kakao

import android.content.Context
import android.util.Log
import kr.co.parthair.android.members.common.MyConstants.TAG
import kr.co.parthair.android.members.social.kakao.callback.KakaoSendLinkMsgCallback
import kr.co.parthair.android.members.social.kakao.model.MyLocationTemplate
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.talk.TalkApiClient


/**
 * ClassName            KakaoSendLinkMsg
 * Created by JSky on   2021-01-28
 *
 * Description
 */
class KakaoSendLinkMsg  {
    private var context: Context;
    private lateinit var sendLinkMsgCallback: KakaoSendLinkMsgCallback


    constructor(context: Context, sendTalkMsgCallback: KakaoSendLinkMsgCallback){
        this.context = context;
        this.sendLinkMsgCallback = sendTalkMsgCallback;
    }
    fun sendMsg(template: MyLocationTemplate){
        // 카카오톡 친구 목록 가져오기
        Log.e(TAG, "sendMsg()")



//                    val template = LocationTemplate(
//                            address = "경기 성남시 분당구 판교역로 235 에이치스퀘어 N동 8층",
//                            addressTitle = "카카오 판교오피스 카페톡",
//                            content = Content(
//                                    title = "신메뉴 출시❤️ 체리블라썸라떼",
//                                    description = "이번 주는 체리블라썸라떼 1+1",
//                                    imageUrl = "http://mud-kage.kakao.co.kr/dn/bSbH9w/btqgegaEDfW/vD9KKV0hEintg6bZT4v4WK/kakaolink40_original.png",
//                                    link = Link(
//                                            webUrl = "https://developers.com",
//                                            mobileWebUrl = "https://developers.kakao.com"
//                                    )
//                            ),
//                            social = Social(
//                                    likeCount = 286,
//                                    commentCount = 45,
//                                    sharedCount = 845
//                            )
//                    )

//                    // 메시지 보내기

                    LinkClient.instance.defaultTemplate(context, template) { linkResult, error ->
                        if (error != null) {
                            Log.e(TAG, "카카오링크 보내기 실패", error)
                            sendLinkMsgCallback.onError(error)
                        }
                        else if (linkResult != null) {
                            Log.d(TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                            sendLinkMsgCallback.onSuccess(linkResult)
                          //  startActivity(linkResult.intent)

                            // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                            Log.w(TAG, "Warning Msg: ${linkResult.warningMsg}")
                            Log.w(TAG, "Argument Msg: ${linkResult.argumentMsg}")
                        }
                    }


                    TalkApiClient.instance.sendDefaultMemo(template) { error ->
                        if (error != null) {
                            Log.e(TAG, "나에게 보내기 실패", error)
                        } else {
                            Log.i(TAG, "나에게 보내기 성공")
                        }
                    }


    }



}