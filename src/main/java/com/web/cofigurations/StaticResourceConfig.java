package com.web.cofigurations;



import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${resume.upload.dir}")
    private String resumeUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve the resume files from the /uploads/resumes directory
        registry.addResourceHandler("/uploads/resumes/**")
                .addResourceLocations("file:" + resumeUploadDir + File.separator);
    }
}
