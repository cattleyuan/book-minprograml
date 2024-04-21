package com.ab.config;


import com.ab.interpretor.AdminInterpretor;
import com.ab.interpretor.UserInterpretor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private AdminInterpretor adminInterpretor;
    @Autowired
    private UserInterpretor userInterpretor;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterpretor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/admin/login");

        registry.addInterceptor(userInterpretor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/wxlogin","/user/puzzle/show","/user/book/**","/user/product/**");
    }

    @Bean
    public Docket docket1() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("book思益项目接口文档")
                .version("2.0")
                .description("book思益项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ab.puzzle.controller.user"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
    @Bean
    public Docket docket2() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("book思益项目接口文档")
                .version("2.0")
                .description("book思益项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ab.puzzle.controller.admin"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

    }


}
