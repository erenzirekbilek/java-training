package com.training.client.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    
    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Value("${app.environment:unknown}")
    private String environment;

    public String getActiveProfile() {
        return activeProfile;
    }

    public String getEnvironment() {
        return environment;
    }

    public boolean isProduction() {
        return "prod".equals(activeProfile);
    }

    public boolean isDevelopment() {
        return "dev".equals(activeProfile);
    }
}