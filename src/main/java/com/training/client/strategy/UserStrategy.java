package com.training.client.strategy;

public class UserStrategy {
    
    public interface UserValidationStrategy {
        boolean validate(String username, String email);
    }
    
    public static class EmailValidationStrategy implements UserValidationStrategy {
        @Override
        public boolean validate(String username, String email) {
            return email != null && email.contains("@");
        }
    }
    
    public static class UsernameValidationStrategy implements UserValidationStrategy {
        @Override
        public boolean validate(String username, String email) {
            return username != null && username.length() >= 3;
        }
    }
    
    public static class StrictValidationStrategy implements UserValidationStrategy {
        @Override
        public boolean validate(String username, String email) {
            return username != null && username.length() >= 3 && 
                   email != null && email.contains("@");
        }
    }

    public static class UserValidator {
        private UserValidationStrategy strategy;
        
        public UserValidator(UserValidationStrategy strategy) {
            this.strategy = strategy;
        }
        
        public boolean validate(String username, String email) {
            return strategy.validate(username, email);
        }
        
        public void setStrategy(UserValidationStrategy strategy) {
            this.strategy = strategy;
        }
    }
}