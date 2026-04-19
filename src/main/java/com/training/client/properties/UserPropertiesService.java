package com.training.client.properties;

import java.util.Properties;
import java.io.*;

public class UserPropertiesService {
    
    public Properties loadProperties(String fileName) throws IOException {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(fileName)) {
            props.load(input);
        }
        return props;
    }

    public void saveProperties(Properties props, String fileName) throws IOException {
        try (OutputStream output = new FileOutputStream(fileName)) {
            props.store(output, "User Properties");
        }
    }

    public String getProperty(Properties props, String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public void setProperty(Properties props, String key, String value) {
        props.setProperty(key, value);
    }
}