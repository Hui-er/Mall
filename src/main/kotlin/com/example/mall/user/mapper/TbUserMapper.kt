package com.example.mall.user.mapper

import com.example.mall.user.pojo.TbUser
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

/**
 *  Created by Liubin on 2019-04-02
 *
 *  用户表操作
 */
@Repository
interface TbUserMapper {
    /**
     * 通过用户名查用户信息
     */
    fun selectUserByUsername(@Param("username") username: String): TbUser?

    /**
     * 新增一条用户
     */
    fun insertUser(user: TbUser): Int

    /**
     * 更新用户信息
     */
    fun updateUser(user: TbUser): Int

}