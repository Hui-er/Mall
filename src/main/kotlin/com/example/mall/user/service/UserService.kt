package com.example.mall.user.service

import com.example.mall.entity.CommonResponse

/**
 *  Created by Liubin on 2019-04-02
 *  用户服务类接口
 */

interface UserService {

    fun register(userName: String?, password: String?): CommonResponse

    fun login(userName: String?, password: String?): CommonResponse

    fun editNickName(userName: String?, token: String?, nickname: String?): CommonResponse
}