package com.example.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.example.mapper.BlogMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Tu
 * @Package com.example.config
 * @date 2021/1/29-17:23
 * PaginationInterceptor是一个分页插件
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.example.mapper")
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        BlogMapper.selectPage(paginationInterceptor,null);
        return paginationInterceptor;
    }
}
