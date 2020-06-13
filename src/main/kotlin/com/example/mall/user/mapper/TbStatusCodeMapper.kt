package com.example.mall.user.mapper

import com.example.mall.user.pojo.TbStatusCode
import org.springframework.stereotype.Repository

/**
 *  Created by Liubin on 2019-04-03
 */
@Repository
interface TbStatusCodeMapper {
    fun selectStatus(): MutableList<TbStatusCode>
}
