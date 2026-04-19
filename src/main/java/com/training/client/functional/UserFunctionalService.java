package com.training.client.functional;

import java.util.function.*;

public class UserFunctionalService {
    
    private final Function<String, String> uppercase = String::toUpperCase;
    private final Function<String, String> trim = String::trim;
    private final Function<String, String> combined = trim.andThen(uppercase);
    
    public String processUsername(String username) {
        return combined.apply(username);
    }

    private final Predicate<String> isValidEmail = email -> email != null && email.contains("@");
    private final Predicate<String> isLongEnough = s -> s != null && s.length() >= 3;
    
    public boolean validateEmail(String email) {
        return isValidEmail.test(email);
    }

    public boolean validateUsername(String username) {
        return isLongEnough.test(username);
    }

    private final Supplier<String> defaultUsername = () -> "default_user";
    
    public String getDefaultUsername() {
        return defaultUsername.get();
    }

    private final Consumer<String> logConsumer = System.out::println;
    
    public void logMessage(String message) {
        logConsumer.accept(message);
    }

    private final BinaryOperator<Integer> maxAge = Integer::max;
    
    public int getMaxAge(int age1, int age2) {
        return maxAge.apply(age1, age2);
    }
}