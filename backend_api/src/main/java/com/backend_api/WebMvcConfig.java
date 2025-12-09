package com.backend_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${upload.dir:uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadsPath = "file:" + uploadDir + (uploadDir.endsWith(File.separator) ? "" : File.separator);
        registry.addResourceHandler("/uploads/*")
                .addResourceLocations(uploadsPath)
                .setCachePeriod(3600);
    }
}