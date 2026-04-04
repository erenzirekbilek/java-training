package Interface_Types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Interface_Types {

    public static void main(String[] args) {
        System.out.println("=== INTERFACE TYPES IN JAVA ===\n");
        
        normalInterface();
        System.out.println();
        functionalInterface();
        System.out.println();
        markerInterface();
        System.out.println();
        comparisonTable();
    }

    static void normalInterface() {
        System.out.println("--- NORMAL INTERFACE ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Traditional interface with abstract methods");
        System.out.println("   - May have default and static methods (Java 8+)");
        System.out.println("   - Implementation provides concrete behavior");
        
        System.out.println("\n2. EXAMPLE:");
        System.out.println("   interface Drawable {");
        System.out.println("       void draw();");
        System.out.println("       void resize(int scale);");
        System.out.println("   }");
        
        System.out.println("\n3. IMPLEMENTATION:");
        class Circle implements Drawable {
            public void draw() { System.out.println(\"Drawing Circle\"); }
            public void resize(int scale) { System.out.println(\"Resizing Circle by \" + scale); }
        }
        
        Drawable circle = new Circle();
        circle.draw();
        circle.resize(2);
        
        System.out.println("\n4. DEFAULT METHODS:");
        System.out.println("   - Optional: implementor can override");
        System.out.println("   - Enables interface evolution (backward compatibility)");
        
        System.out.println("   interface A {");
        System.out.println("       default void greet() { System.out.println(\"Hello\"); }");
        System.out.println("   }");
    }

    static void functionalInterface() {
        System.out.println("--- FUNCTIONAL INTERFACE ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Has exactly ONE abstract method (SAM - Single Abstract Method)");
        System.out.println("   - Can use lambda expressions");
        System.out.println("   - Annotated with @FunctionalInterface");
        
        System.out.println("\n2. EXAMPLES FROM JAVA API:");
        System.out.println("   Runnable -> void run()");
        System.out.println("   Callable<V> -> V call()");
        System.out.println("   Comparator<T> -> int compare(T o1, T o2)");
        System.out.println("   Predicate<T> -> boolean test(T t)");
        System.out.println("   Function<T,R> -> R apply(T t)");
        System.out.println("   Supplier<T> -> T get()");
        System.out.println("   Consumer<T> -> void accept(T t)");
        
        System.out.println("\n3. LAMBDA EXPRESSIONS:");
        
        @FunctionalInterface
        interface MathOperation {
            int operation(int a, int b);
        }
        
        MathOperation add = (a, b) -> a + b;
        MathOperation multiply = (a, b) -> a * b;
        
        System.out.println("   add(5, 3) = " + add.operation(5, 3));
        System.out.println("   multiply(5, 3) = " + multiply.operation(5, 3));
        
        System.out.println("\n4. METHOD REFERENCES:");
        List<String> names = new ArrayList<>();
        names.add(\"Alice\");
        names.add(\"Bob\");
        
        names.forEach(System.out::println);
        
        System.out.println("\n5. BUILT-IN FUNCTIONAL INTERFACES:");
        
        java.util.function.Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("   Predicate: isEven.test(4) = " + isEven.test(4));
        
        java.util.function.Function<String, Integer> length = String::length;
        System.out.println(\"   Function: length.apply(\\\"Hello\\\") = \" + length.apply(\"Hello\"));
        
        java.util.function.Supplier<Double> random = Math::random;
        System.out.println("   Supplier: random.get() = " + random.get());
        
        java.util.function.Consumer<String> printer = System.out::println;
        printer.accept(\"Consumer output\");
        
        java.util.function.UnaryOperator<Integer> square = n -> n * n;
        System.out.println("   UnaryOperator: square.apply(5) = " + square.apply(5));
        
        java.util.function.BinaryOperator<Integer> max = Integer::max;
        System.out.println("   BinaryOperator: max.apply(5, 10) = " + max.apply(5, 10));
    }

    static void markerInterface() {
        System.out.println("--- MARKER INTERFACE ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Has NO methods at all");
        System.out.println("   - Just marks/identifies a class for special treatment");
        System.out.println("   - Used for metadata and type checking");
        
        System.out.println("\n2. EXAMPLES FROM JAVA:");
        System.out.println("   Serializable -> marks class for serialization");
        System.out.println("   Cloneable -> marks class for Object.clone()");
        System.out.println("   Remote -> marks class for RMI");
        
        System.out.println("\n3. SERIALIZABLE EXAMPLE:");
        
        class Person implements Serializable {
            private static final long serialVersionUID = 1L;
            private String name;
            private int age;
            
            public Person(String name, int age) {
                this.name = name;
                this.age = age;
            }
            
            @Override
            public String toString() {
                return \"Person{name='\" + name + \"', age=\" + age + \"}\";
            }
        }
        
        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
            
            Person person = new Person(\"John\", 30);
            oos.writeObject(person);
            oos.close();
            
            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais);
            
            Person deserialized = (Person) ois.readObject();
            ois.close();
            
            System.out.println("   Original: " + person);
            System.out.println("   Deserialized: " + deserialized);
            
        } catch (Exception e) {
            System.out.println(\"   Exception: \" + e.getMessage());
        }
        
        System.out.println("\n4. CLONEABLE EXAMPLE:");
        
        class Animal implements Cloneable {
            String name;
            
            public Animal(String name) {
                this.name = name;
            }
            
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        }
        
        try {
            Animal original = new Animal(\"Dog\");
            Animal cloned = (Animal) original.clone();
            System.out.println(\"   Original: \" + original.name);
            System.out.println(\"   Cloned: \" + cloned.name);
            System.out.println(\"   Same object? \" + (original == cloned));
        } catch (CloneNotSupportedException e) {}
        
        System.out.println("\n5. MARKER vs ATTRIBUTE:");
        System.out.println("   Marker: No methods, just tag");
        System.out.println("   Attribute: Can have getters/setters");
        System.out.println("   Modern approach: Annotations (@Component, @Entity)");
    }

    static void comparisonTable() {
        System.out.println("--- COMPARISON TABLE ---");
        
        String[][] table = {
            {"Type", "Methods", "Lambda", "Purpose", "Example"},
            {"Normal", "Multiple abstract", "No", "Contract definition", "List, Set"},
            {"Functional", "One abstract (SAM)", "Yes", "Behavior passing", "Predicate, Function"},
            {"Marker", "Zero methods", "No", "Type marking", "Serializable, Cloneable"}
        };
        
        System.out.println();
        for (String[] row : table) {
            System.out.printf("%-12s | %-20s | %-6s | %-20s | %s%n", 
                row[0], row[1], row[2], row[3], row[4]);
        }
        
        System.out.println("\n--- KEY DIFFERENCES ---");
        System.out.println("1. Normal Interface: Multiple abstract methods, traditional OOP");
        System.out.println("2. Functional Interface: Exactly one abstract method, enables lambdas");
        System.out.println("3. Marker Interface: No methods, signals special capability");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: What makes an interface functional?");
        System.out.println("A: Exactly one abstract method (can have default/static)");
        
        System.out.println("\nQ: Can a functional interface have default methods?");
        System.out.println("A: Yes! Default methods don't count as abstract methods");
        
        System.out.println("\nQ: Why use marker interfaces?");
        System.out.println("A: Compile-time type checking, marks class for special treatment");
        
        System.out.println("\nQ: What's the modern alternative to marker interfaces?");
        System.out.println("A: Annotations (@Override, @Deprecated, etc.)");
    }
}