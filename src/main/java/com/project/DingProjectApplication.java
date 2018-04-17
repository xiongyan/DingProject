package com.project;

import com.project.util.ApplicationContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@MapperScan("com.project.dao")
@SpringBootApplication
@ServletComponentScan
@EnableCaching
public class DingProjectApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(DingProjectApplication.class, args);
		ApplicationContextUtils.setApplicationContext(app);
	}
}
