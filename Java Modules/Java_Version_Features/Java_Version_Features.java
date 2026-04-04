package Java_Version_Features;

public class Java_Version_Features {

    public static void main(String[] args) {
        System.out.println("=== JAVA 8 / 17 / 21 FEATURES ===\n");
        
        java8Features();
        System.out.println();
        java17Features();
        System.out.println();
        java21Features();
        System.out.println();
        sealedClassImitation();
    }

    static void java8Features() {
        System.out.println("--- JAVA 8 KEY FEATURES ---");
        
        System.out.println("\n1. LAMBDA EXPRESSIONS:");
        System.out.println("   - Anonymous functions without class overhead");
        System.out.println("   Syntax: (param) -> expression or (param) -> { statements }");
        
        java.util.function.Function<Integer, Integer> square = x -> x * x;
        System.out.println("   square.apply(5) = " + square.apply(5));
        
        java.util.function.Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("   isEven.test(4) = " + isEven.test(4));
        
        Runnable task = () -> System.out.println("   Lambda Runnable executed");
        task.run();
        
        System.out.println("\n2. STREAMS API:");
        System.out.println("   - Process collections in functional style");
        System.out.println("   - Operations: filter, map, reduce, collect");
        
        var numbers = java.util.Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream()
            .filter(n -> n % 2 == 1)
            .mapToInt(n -> n * n)
            .sum();
        System.out.println("   Sum of squares of odd numbers 1-5 = " + sum);
        
        System.out.println("\n3. INTERFACE DEFAULT METHODS:");
        System.out.println("   - Interfaces can have implemented methods");
        System.out.println("   - Enables backward compatibility");
        
        System.out.println("\n4. OPTIONAL CLASS:");
        System.out.println("   - Null-safe alternative");
        String result = java.util.Optional.of("Hello")
            .map(String::toUpperCase)
            .orElse("Default");
        System.out.println("   Optional result: " + result);
        
        System.out.println("\n5. NEW DATE/TIME API:");
        System.out.println("   - java.time package: LocalDate, LocalDateTime, etc.");
        java.time.LocalDate today = java.time.LocalDate.now();
        System.out.println("   Today: " + today);
        
        System.out.println("\n6. METHOD REFERENCES:");
        System.out.println("   - Shorthand for lambdas");
        java.util.List<String> names = java.util.Arrays.asList("Alice", "Bob", "Charlie");
        names.forEach(System.out::println);
    }

    static void java17Features() {
        System.out.println("--- JAVA 17 KEY FEATURES ---");
        
        System.out.println("\n1. SEALED CLASSES (Preview in 15/16, Final in 17):");
        System.out.println("   - Restrict which classes can extend/implement");
        System.out.println("   - Uses 'sealed', 'permits' keywords");
        
        System.out.println("\n2. PATTERN MATCHING FOR 'instanceof':");
        Object obj = "Hello";
        if (obj instanceof String s && s.length() > 3) {
            System.out.println("   Pattern matched: " + s.toUpperCase());
        }
        
        System.out.println("\n3. RECORDS (Preview in 14/15/16, Final in 16):");
        System.out.println("   - Immutable data carriers");
        
        System.out.println("\n4. TEXT BLOCKS (Preview in 13/14, Final in 15):");
        String json = """
            {
                "name": "John",
                "age": 30
            }
            """;
        System.out.println("   Text block: " + json.trim());
        
        System.out.println("\n5. SWITCH EXPRESSIONS (Standard in 14):");
        int day = 3;
        String dayType = switch (day) {
            case 1, 2, 3, 4, 5 -> "Weekday";
            case 6, 7 -> "Weekend";
            default -> "Invalid";
        };
        System.out.println("   Switch expression result: " + dayType);
        
        System.out.println("\n6. RECORDS (Already in 16, enhanced in 17):");
        System.out.println("   - Sealed records, better serialization");
        
        System.out.println("\n7. STRICTFP (Removed):");
        System.out.println("   - Floating-point consistency across platforms");
    }

    static void java21Features() {
        System.out.println("--- JAVA 21 KEY FEATURES ---");
        
        System.out.println("\n1. VIRTUAL THREADS (Preview in 19/20, Final in 21):");
        System.out.println("   - Lightweight threads, millions per application");
        System.out.println("   - Simple example:");
        
        Thread virtualThread = Thread.ofVirtual().start(() -> {
            System.out.println("   Virtual thread running");
        });
        try {
            virtualThread.join();
        } catch (InterruptedException e) {}
        
        System.out.println("\n2. SEALED CLASSES (Final):");
        System.out.println("   - Now fully standardized");
        
        System.out.println("\n3. PATTERN MATCHING FOR SWITCH:");
        System.out.println("   - Match types, null, guards");
        
        Object o = "Test";
        String result = switch (o) {
            case String s -> "String: " + s;
            case Integer i -> "Integer: " + i;
            case null -> "Null value";
            default -> "Unknown";
        };
        System.out.println("   Pattern match result: " + result);
        
        System.out.println("\n4. SEQUENCED COLLECTIONS:");
        System.out.println("   - New interface for ordered collections");
        System.out.println("   - First/Last element access, reversed view");
        
        java.util.SequencedCollection<Integer> sc = new java.util.ArrayList<>(java.util.Arrays.asList(1, 2, 3));
        System.out.println("   First: " + sc.getFirst() + ", Last: " + sc.getLast());
        
        System.out.println("\n5. RECORD PATTERNS:");
        System.out.println("   - Decompose records in instanceof and switch");
        
        System.out.println("\n6. STRING TEMPLATES (Preview):");
        System.out.println("   - Embedded expressions in strings (JEP 430)");
    }

    static void sealedClassImitation() {
        System.out.println("--- SEALED CLASS IMITATION IN JAVA 8 ---");
        
        System.out.println("\n1. PROBLEM:");
        System.out.println("   Sealed classes restrict inheritance hierarchy");
        System.out.println("   Java 8 doesn't have 'sealed' keyword");
        
        System.out.println("\n2. SOLUTION 1: FINAL CLASS + ABSTRACT FACTORY");
        System.out.println("   - Make base class final to prevent extension");
        System.out.println("   - Use abstract factory for controlled instantiation");
        
        System.out.println("\n   Base Class (Final):");
        System.out.println("   public final class Animal { }");
        System.out.println("   Cannot be extended!");
        
        System.out.println("\n3. SOLUTION 2: PRIVATE CONSTRUCTOR + STATIC FACTORY");
        System.out.println("   - Private constructor prevents external extension");
        System.out.println("   - Static methods return limited subtypes");
        
        System.out.println("\n   Example:");
        System.out.println("   abstract class Shape {");
        System.out.println("       private Shape() { }");
        System.out.println("       public static Shape circle() { return new Circle(); }");
        System.out.println("       public static Shape rectangle() { return new Rectangle(); }");
        System.out.println("   }");
        
        System.out.println("\n4. SOLUTION 3: ENUM WITH BEHAVIOR (Best for limited types)");
        System.out.println("   - Enum naturally limits possible values");
        
        enum Operation {
            PLUS {
                public int apply(int a, int b) { return a + b; }
            },
            MINUS {
                public int apply(int a, int b) { return a - b; }
            };
            
            public abstract int apply(int a, int b);
        }
        
        System.out.println("   PLUS.apply(5, 3) = " + Operation.PLUS.apply(5, 3));
        System.out.println("   MINUS.apply(5, 3) = " + Operation.MINUS.apply(5, 3));
        
        System.out.println("\n5. SOLUTION 4: COMPILE-TIME CHECKS (Interface + Implementation)");
        System.out.println("   - Use interface with known implementations");
        System.out.println("   - Compile fails if new class doesn't implement interface");
        
        System.out.println("\n   interface Payment { void pay(double amount); }");
        System.out.println("   class CreditCard implements Payment { ... }");
        System.out.println("   class PayPal implements Payment { ... }");
        System.out.println("   // Only these two can implement Payment");
        
        System.out.println("\n6. SOLUTION 5: CLASSLOADER CHECK (Advanced)");
        System.out.println("   - Throw exception in constructor if unauthorized");
        
        System.out.println("\n7. COMPARISON TABLE:");
        System.out.println("   +--------------------+------------------+--------+");
        System.out.println("   | Method             | Flexibility      | Safety |");
        System.out.println("   +--------------------+------------------+--------+");
        System.out.println("   | Final Class        | Low              | High   |");
        System.out.println("   | Private Constructor| Medium           | High   |");
        System.out.println("   | Enum               | Low (fixed)      | Highest|");
        System.out.println("   | Sealed (Java 17+)  | High             | High   |");
        System.out.println("   +--------------------+------------------+--------+");
        
        System.out.println("\n8. JAVA 17+ SEALED CLASS (Real Solution):");
        System.out.println("   sealed class Animal permits Dog, Cat { }");
        System.out.println("   final class Dog extends Animal { }");
        System.out.println("   sealed class Cat extends Animal permits Persian { }");
    }
}