package com.training.client.decorator;

public class UserDecorator {
    
    public interface UserService {
        String getUser();
    }
    
    public static class BasicUserService implements UserService {
        public String getUser() { return "Basic User"; }
    }
    
    public static class UserServiceDecorator implements UserService {
        protected UserService userService;
        
        public UserServiceDecorator(UserService userService) {
            this.userService = userService;
        }
        
        public String getUser() { return userService.getUser(); }
    }
    
    public static class LoggingDecorator extends UserServiceDecorator {
        public LoggingDecorator(UserService userService) { super(userService); }
        
        public String getUser() {
            System.out.println("Logging: Getting user");
            String result = super.getUser();
            System.out.println("Logging: Got user - " + result);
            return result;
        }
    }
    
    public static class ValidationDecorator extends UserServiceDecorator {
        public ValidationDecorator(UserService userService) { super(userService); }
        
        public String getUser() {
            System.out.println("Validation: Checking access");
            return super.getUser();
        }
    }
}