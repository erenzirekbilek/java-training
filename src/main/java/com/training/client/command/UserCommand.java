package com.training.client.command;

public class UserCommand {
    
    public interface Command {
        void execute();
    }
    
    public static class CreateUserCommand implements Command {
        private String username;
        
        public CreateUserCommand(String username) {
            this.username = username;
        }
        
        public void execute() {
            System.out.println("Creating user: " + username);
        }
    }
    
    public static class DeleteUserCommand implements Command {
        private Long userId;
        
        public DeleteUserCommand(Long userId) {
            this.userId = userId;
        }
        
        public void execute() {
            System.out.println("Deleting user: " + userId);
        }
    }
    
    public static class CommandInvoker {
        public void executeCommand(Command command) {
            command.execute();
        }
    }
}