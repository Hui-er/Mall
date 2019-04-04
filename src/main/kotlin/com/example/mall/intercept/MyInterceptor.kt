package com.example.mall.intercept

import com.example.mall.plugins.means
import com.example.mall.plugins.write
import com.example.mall.user.mapper.TbUserMapper
import org.apache.juli.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.lang.Nullable
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *  Created by Liubin on 2019-04-03
 *  自定义拦截器 ，拦截token,并更新token的缓存时间
 */
class MyInterceptor : HandlerInterceptor {
    protected val logger = LogFactory.getLog(MyInterceptor::class.java)

    @Autowired
    private val redisTemplate: StringRedisTemplate? = null

    @Autowired
    private val userMapper: TbUserMapper? = null

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        println("处理器前方法")
        val username = request.getParameter("username")
        val token = request.getParameter("token")
        response.contentType = "application/json; charset=utf-8"
        return when {
            username.isBlank() means "用户名为空" -> false.write(response, 1001)
            token.isBlank() means "token为空" -> false.write(response, 1006)
            else -> {
                //拿用户信息
                val user = userMapper!!.selectUserByUsername(username)
                user?.run {
                    //拿缓存的token
                    val cacheToken = redisTemplate!!.opsForValue().get("token$id$username")
                    when {
                        cacheToken.isNullOrBlank() means "登录超时" -> false.write(response, 1007)
                        cacheToken != token means "token不匹配" -> false.write(response, 1008)
                        else -> {
                            //缓存起来
                            redisTemplate!!.opsForValue().set("token$id$username", token, 1800, TimeUnit.SECONDS)
                            true
                        }
                    }
                } ?: false.write(response, 1004) means "用户不存在"
            }
        }
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, @Nullable modelAndView: ModelAndView?) {
        println("处理器后方法")
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, @Nullable ex: Exception?) {
        println("处理器完成方法")
    }
}