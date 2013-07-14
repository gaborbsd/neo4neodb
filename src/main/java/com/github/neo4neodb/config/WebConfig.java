package com.github.neo4neodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.github,neo4neodb")
@Import({Neo4jConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Resource
    private org.springframework.core.env.Environment environment;

    @Bean
    public InternalResourceViewResolver configureInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jspx");
        return resolver;
    }

}
