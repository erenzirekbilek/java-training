package com.training.client.soap;

import com.training.client.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Component
public class UserSoapClient {
    private static final Logger logger = LoggerFactory.getLogger(UserSoapClient.class);
    
    private final WebServiceTemplate webServiceTemplate;
    private final String soapUrl;

    public UserSoapClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
        this.soapUrl = "http://localhost:8080/ws/users";
    }

    public UserDto getUserById(Long id) {
        logger.info("SOAP request for user id: {}", id);
        try {
            Object response = webServiceTemplate.sendSourceAndReceive(soapUrl, 
                "<getUserRequest><id>" + id + "</id></getUserRequest>",
                source -> {});
            return new UserDto(id, "soap_user", "soap@example.com", "SOAP User", 25, true);
        } catch (Exception e) {
            logger.error("SOAP error", e);
            return null;
        }
    }

    public UserDto createUser(UserDto user) {
        logger.info("SOAP create user: {}", user.getUsername());
        try {
            return user;
        } catch (Exception e) {
            logger.error("SOAP create error", e);
            return null;
        }
    }
}