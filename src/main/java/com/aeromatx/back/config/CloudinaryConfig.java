// CloudinaryConfig.java
package com.aeromatx.back.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "Root",
            "api_key", "965812618559566",
            "api_secret", "nQQRPep1c7YH8BHNZ2eKs_W73Pw"
        ));
    }
}
