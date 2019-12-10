package com.syfri.digitalplan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2配置文件
 * @author  li.xue  2017/12/4
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket createRestApi(){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.syfri.digitalplan.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("数字化预案总队平台 [API SERVER] 接口")
				.termsOfServiceUrl("FRAMEWORK ")
				.description("更多问题 请关注 ")
				.license("FRAMEWORK 1.0.RELEASE")
				.contact(new Contact("沈阳消防研究所","", "xxx@syfri.com"))
				.version("FRAMEWORK 1.0.RELEASE")
				.build();
	}

}
