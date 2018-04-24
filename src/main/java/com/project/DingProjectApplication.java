package com.project;

import com.project.util.ApplicationContextUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@MapperScan("com.project.dao")
@SpringBootApplication
@ServletComponentScan
@EnableCaching
@Configuration
public class DingProjectApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(DingProjectApplication.class, args);
		ApplicationContextUtils.setApplicationContext(app);
	}

	/**
	 * 文件上传配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//文件最大
		factory.setMaxFileSize("300MB"); //KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("500MB");
		return factory.createMultipartConfig();
	}
}


