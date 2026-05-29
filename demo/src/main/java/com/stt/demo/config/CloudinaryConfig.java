package com.stt.demo.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {

        String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");

        System.out.println("========== CLOUDINARY DEBUG ==========");
        System.out.println("Cloud Name: " + cloudName);
        System.out.println("=====================================");

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", System.getenv("CLOUDINARY_API_KEY"),
                "api_secret", System.getenv("CLOUDINARY_API_SECRET"),
                "secure", true
        ));
    }
}