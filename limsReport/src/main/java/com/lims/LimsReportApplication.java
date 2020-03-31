package com.lims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
public class LimsReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(LimsReportApplication.class, args);
        System.out.println("lims报告外挂管理系统启动成功");
    }
}
