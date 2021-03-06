package com.mzydz.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author kongling
 * @package com
 * @date 2019-05-09  11:39
 * @project suda_cloud
 */
@SpringBootApplication(scanBasePackages = {"config.advice"}, exclude = {})
@MapperScan(basePackages = {"com.mzydz.platform.modules.*.mapper"})
//配置druid必须加的注解，如果不加，访问页面打不开，filter和servlet、listener之类的需要单独进行注册才能使用
// spring boot里面提供了该注解起到注册作用 能够扫描到我们自己编写的servlet和filter。
@ServletComponentScan(value = {"config"})
//扫描公共模块下的包     注入注解
@ComponentScan(basePackages = {"com.util", "com.mzydz.platform", "config"})
@EnableAsync
@EnableCaching
@EnableScheduling
public class ProjectPlatformItemApplication {
    private static final Logger logger = LoggerFactory.getLogger(ProjectPlatformItemApplication.class);


    public static void main(String[] args) {
        logger.info("Springboot启动中....");
        SpringApplication.run(ProjectPlatformItemApplication.class, args);
        logger.info("启动成功！");
    }
}
