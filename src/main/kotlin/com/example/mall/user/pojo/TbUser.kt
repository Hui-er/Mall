package com.example.mall.user.pojo

import org.apache.ibatis.type.Alias

/**
 *  Created by Liubin on 2019-04-02
 */
@Alias("user")
class TbUser(
        var id: Int = 0,
        var username: String? = null,
        var password: String? = null,
        var token: String? = null,
        var nickname: String? = null,
        var phone: String? = null,
        var headImg: String? = null
)