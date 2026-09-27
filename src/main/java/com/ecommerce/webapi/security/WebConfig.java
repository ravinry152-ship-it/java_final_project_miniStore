package com.ecommerce.webapi.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // កំណត់ទីតាំងថតเก็บរូបភាពក្នុងเครื่อง (AbsolutePath ឬ RelativePath)
        Path uploadDir = Paths.get("./media/product/");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/media/product/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}
