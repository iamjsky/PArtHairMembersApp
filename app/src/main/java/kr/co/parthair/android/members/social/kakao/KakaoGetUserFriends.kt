package kr.co.parthair.android.members.social.kakao

import android.content.Context
import android.util.Log
import kr.co.parthair.android.members.social.kakao.callback.KakaoGetUserFriendsCallback
import com.kakao.sdk.talk.TalkApiClient
import kr.co.parthair.android.members.common.MyConstants.TAG


/**
 * ClassName            KakaoGetUserFriends
 * Created by JSky on   2021-02-01
 *
 * Description
 */
class KakaoGetUserFriends {

    private var context: Context
    private lateinit var getUserFriendsCallback: KakaoGetUserFriendsCallback



    constructor(context: Context, getUserFriendsCallback: KakaoGetUserFriendsCallback){
        this.context = context;
        this.getUserFriendsCallback = getUserFriendsCallback;
    }


    fun getUserFriendsList() {
        TalkApiClient.instance.friends { friends, error ->
            if (error != null) {
                Log.e(TAG, "사용자 친구 목록 요청 실패", error)
                getUserFriendsCallback.onError(error)
            } else if (friends != null) {
                Log.i(TAG, "사용자 친구 목록 요청 성공" +
                        "\njson: ${friends.elements!!.joinToString("\n")}")
                if (friends.elements!!.isEmpty()) {
                    Log.e(TAG, "메시지 보낼 친구가 하나도 없어요 ㅠㅠ")
                }
                getUserFriendsCallback.onSuccess(friends.elements)
            }
        }
    }




}