package com.example.mall.util;

import com.example.mall.entity.CommonResponse;
import com.example.mall.user.mapper.TbStatusCodeMapper;
import com.example.mall.user.pojo.TbStatusCode;
import com.google.gson.Gson;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Liubin on 2019-04-03
 */
@Component
public class StatusCodeUtil {
    protected static final Log logger = LogFactory.getLog(StatusCodeUtil.class);

    @Autowired
    private TbStatusCodeMapper statusCodeMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static TbStatusCodeMapper statusCodeDB;


    private static StringRedisTemplate redisDB;

    @PostConstruct
    private void init() {
        statusCodeDB = statusCodeMapper;
        redisDB = redisTemplate;
    }

    @PostConstruct
    private void initCacheStatusCode() {
        logger.info("initCacheStatusCode");
        List<TbStatusCode> codeList = statusCodeMapper.selectStatus();
        for (TbStatusCode code : codeList) {
            redisTemplate.opsForValue().set(code.getCacheStatus(), code.toJsonStr());
        }
    }

    //获取状态码信息
    public static CommonResponse getStatusCode(Integer statusCode) {
        try {
            String jsonStr = redisDB.opsForValue().get("status"+statusCode);
            if (jsonStr.isEmpty()) {
                return new CommonResponse();
            } else {
                TbStatusCode redisCode = new Gson().fromJson(jsonStr, TbStatusCode.class);
                return new CommonResponse(redisCode.getStatusCode(), redisCode.getMessage(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse(500, "未知异常", null);
        }
    }
}
