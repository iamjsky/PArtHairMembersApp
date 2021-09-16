package kr.co.parthair.android.members.social.kakao

import android.content.Context
import android.util.Log
import kr.co.parthair.android.members.data.MyConstants.DEBUG_MODE
import kr.co.parthair.android.members.data.MyConstants.TAG
import kr.co.parthair.android.members.social.kakao.callback.KakaoSendTalkMsgCallback
import kr.co.parthair.android.members.social.kakao.model.MyLocationTemplate
import com.kakao.sdk.talk.TalkApiClient


/**
 * ClassName            KakaoSendTalkMsg
 * Created by JSky on   2021-01-28
 *
 * Description
 */
class KakaoSendTalkMsg  {
    private var context: Context;
    private lateinit var sendTalkMsgCallback: KakaoSendTalkMsgCallback


    constructor(context: Context, sendTalkMsgCallback: KakaoSendTalkMsgCallback){
        this.context = context;
        this.sendTalkMsgCallback = sendTalkMsgCallback;
    }
    fun sendMsg(selectedItems: List<String>, template: MyLocationTemplate){
        // 카카오톡 친구 목록 가져오기

                if (selectedItems.isEmpty()) {
                    Log.e(TAG, "메시지 보낼 친구가 하나도 없어요 ㅠㅠ")
//                    TalkApiClient.instance.sendDefaultMemo(template) { error ->
//                        if (error != null) {
//                            Log.e(TAG, "나에게 보내기 실패", error)
//                        } else {
//                            Log.i(TAG, "나에게 보내기 성공")
//                        }
//                    }

                } else {
                    Log.e(TAG, "sendMsg()")
                    Log.e(TAG, "selectedItems.size" + selectedItems.size)
                    // 메시지 보낼 친구의 UUID 목록
                    var receiverUuids = selectedItems


                    Log.e(TAG, "receiverUuids" + receiverUuids)


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

                    // 메시지 보내기
                    if(DEBUG_MODE){
                        TalkApiClient.instance.sendDefaultMemo(template) { error ->
                            if (error != null) {
                                Log.e(TAG, "나에게 보내기 실패", error)
                            } else {
                                Log.i(TAG, "나에게 보내기 성공")
                            }
                        }

                    }else{

                        TalkApiClient.instance.sendDefaultMessage(receiverUuids, template) { result, error ->
                            if (error != null) {
                                Log.e(TAG, "메시지 보내기 실패", error)
                                sendTalkMsgCallback.onError(error)
                            }
                            else if (result != null) {
                                Log.i(TAG, "메시지 보내기 성공 ${result.successfulReceiverUuids}")

                                if (result.failureInfos != null) {
                                    Log.d(TAG, "메시지 보내기에 일부 성공했으나, 일부 대상에게는 실패 \n${result.failureInfos}")
                                }
                                sendTalkMsgCallback.onSuccess("sendMsgFin")
                            }
                        }
                    }




            }

    }



}