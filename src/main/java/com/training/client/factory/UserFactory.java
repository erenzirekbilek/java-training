package com.training.client.factory;

public class UserFactory {
    
    public static class AdminUserFactory {
        public static User createAdmin() {
            User user = new User();
            user.setUsername("admin");
            user.setRole("ADMIN");
            user.setActive(true);
            return user;
        }
    }
    
    public static class GuestUserFactory {
        public static User createGuest() {
            User user = new User();
            user.setUsername("guest");
            user.setRole("GUEST");
            user.setActive(true);
            return user;
        }
    }

    public static class User {
        private String username;
        private String role;
        private Boolean active;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public Boolean getActive() { return active; }
        public void setActive(Boolean active) { this.active = active; }
    }
}