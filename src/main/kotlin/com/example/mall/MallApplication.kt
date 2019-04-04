package com.example.mall

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.example.mall.**.mapper")
class MallApplication

fun main(args: Array<String>) {
    runApplication<MallApplication>(*args)
}
