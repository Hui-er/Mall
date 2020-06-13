package com.example.mall.user.service

import com.example.mall.entity.CommonResponse
import com.example.mall.user.pojo.TbUser
import com.example.mall.user.mapper.TbUserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.example.mall.plugins.means
import com.example.mall.plugins.onTrue
import com.example.mall.util.EncryptionUtil
import com.example.mall.util.StatusCodeUtil
import org.springframework.data.redis.core.StringRedisTemplate
import java.util.concurrent.TimeUnit

/**
 *  Created by Liubin on 2019-04-02
 *
 *  用户实现类
 */
@Service
class UserServiceImpl : UserService {

    @Autowired
    private val userMapper: TbUserMapper? = null

    @Autowired
    private val redisTemplate: StringRedisTemplate? = null

    /**
     * 注册
     */
    override fun register(userName: String?, password: String?): CommonResponse {
        val user = userMapper!!.selectUserByUsername(username = userName ?: "")
        return when {
            user != null means "已经注册过" -> StatusCodeUtil.getStatusCode(1003)
            else -> {
                createNewUser(userName, password)
                CommonResponse().success(null)
            }
        }
    }

    /**
     * 创建新用户
     */
    fun createNewUser(username: String?, password: String?) {
        val user = TbUser(username = username, password = password)
        userMapper!!.insertUser(user)
    }

    /**
     * 登录  是否存在 -- 校验密码 -- 生成token  ---返回user类
     */
    override fun login(userName: String?, password: String?): CommonResponse {
        val user = userMapper!!.selectUserByUsername(username = userName ?: "")
        return when {
            user == null means "用户不存在" -> StatusCodeUtil.getStatusCode(1004)
            user.password != password means "密码不正确" -> StatusCodeUtil.getStatusCode(1005)
            else -> {
                //拿缓存的token
                var token = redisTemplate!!.opsForValue().get("token" + user.id + user.username)
                when {
                    token.isNullOrBlank() means "没有缓存的token,去生成" -> token = EncryptionUtil.getBase64("token" + userName)
                }
                //缓存起来
                redisTemplate!!.opsForValue().set("token" + user.id + user.username, token!!, 1800, TimeUnit.SECONDS)
                //生成hashMap
                var map = mapOf(
                        "id" to user.id,
                        "username" to userName,
                        "password" to password,
                        "token" to token,
                        "nickname" to user.nickname,
                        "phone" to user.phone,
                        "headImg" to user.headImg)
                //封装返回体
                CommonResponse().success(map)
            }
        }
    }

    /**
     * 修改用户昵称
     */
    override fun editNickName(userName: String?, token: String?, nickname: String?): CommonResponse {
        nickname.isNullOrBlank() onTrue { return StatusCodeUtil.getStatusCode(1009) } means "昵称不能为空"
        val user = userMapper!!.selectUserByUsername(userName!!)
        user!!.run {
            this.nickname = nickname
            userMapper.updateUser(user)
            CommonResponse().success(null) means "修改成功"
        }
        return CommonResponse()
    }


}