package com.example.mall.user.controller

import com.example.mall.entity.CommonResponse
import com.example.mall.plugins.means
import com.example.mall.user.service.UserService
import com.example.mall.util.StatusCodeUtil
import jdk.nashorn.internal.parser.Token
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 *  Created by Liubin on 2019-04-02
 *
 *  用户控制器
 */
@RestController
class UserController {
    @Autowired
    var userService: UserService? = null

    @PostMapping(value = ["bin/register"])
    fun register(username: String?, password: String?): CommonResponse {
        when {
            username.isNullOrBlank() means "用户名为空" -> return StatusCodeUtil.getStatusCode(1001)
            password.isNullOrBlank() means "密码为空" -> return StatusCodeUtil.getStatusCode(1002)
        }
        return userService!!.register(username!!, password!!)
    }

    @PostMapping(value = ["bin/login"])
    fun login(username: String?, password: String?): CommonResponse {
        when {
            username.isNullOrBlank() means "用户名为空" -> return StatusCodeUtil.getStatusCode(1001)
            password.isNullOrBlank() means "密码为空" -> return StatusCodeUtil.getStatusCode(1002)
        }
        return userService!!.login(username!!, password!!)
    }

    @PostMapping(value = ["bin/editNickname"])
    fun editNickname(username: String?, token: String?, nickname: String?): CommonResponse {
        return userService!!.editNickName(username, token, nickname)
    }

}