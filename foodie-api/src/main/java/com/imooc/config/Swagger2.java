package com.imooc.config;

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

@Configuration
@EnableSwagger2
public class Swagger2 {
    //  http://localhost:8080/swagger-ui.html   原路径
    //  http://localhost:8080/doc.html   原路径
    //配置Swagger2核心配置 docket
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)        //指定api类型为swagger2
                    .apiInfo(apiInfo())                       //用于定义API文档汇总信息
                    .select().apis(RequestHandlerSelectors.basePackage("com.imooc.controller"))  //指定Controller包
                    .paths(PathSelectors.any())               //所有Controller
                    .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                    .title("电商平台接口API")                  //文档页标题
                    .contact(new Contact("Deer","https://www.imooc.com"
                            ,"919114921@qq.com"))       //联系人信息
                    .description("专为电商平台提供的api文档")  //详细信息
                    .version("1.0.1")                         //文档版本号
                    .termsOfServiceUrl("https://www.imooc.com") //网址地址
                    .build();
    }
}
