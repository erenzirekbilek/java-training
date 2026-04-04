package Comparator_vs_Comparable;

import java.util.*;

public class Comparator_vs_Comparable {

    public static void main(String[] args) {
        System.out.println("=== COMPARATOR VS COMPARABLE ===\n");
        
        comparableInterface();
        System.out.println();
        comparatorInterface();
        System.out.println();
        practicalExamples();
        System.out.println();
        interviewSummary();
    }

    static void comparableInterface() {
        System.out.println("--- COMPARABLE INTERFACE (NATURAL ORDER) ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - java.lang.Comparable<T>");
        System.out.println("   - Implemented by class itself");
        System.out.println("   - Defines natural ordering");
        System.out.println("   - Single method: int compareTo(T o)");
        
        System.out.println("\n2. IMPLEMENTATION:");
        
        class Person implements Comparable<Person> {
            String name;
            int age;
            
            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }
            
            @Override
            public int compareTo(Person other) {
                return this.age - other.age;
            }
            
            @Override
            public String toString() {
                return name + "(" + age + ")";
            }
        }
        
        List<Person> people = Arrays.asList(
            new Person("Alice", 30),
            new Person("Bob", 25),
            new Person("Charlie", 35)
        );
        
        Collections.sort(people);
        System.out.println("   Sorted by age: " + people);
        
        System.out.println("\n3. COMPARE TO LOGIC:");
        System.out.println("   this < other  -> negative");
        System.out.println("   this == other -> 0");
        System.out.println("   this > other  -> positive");
        
        System.out.println("\n4. STRING COMPARABLE:");
        System.out.println("   String implements Comparable<String>");
        System.out.println("   \"Apple\".compareTo(\"Banana\") = " + "Apple".compareTo("Banana"));
        System.out.println("   \"Banana\".compareTo(\"Apple\") = " + "Banana".compareTo("Apple"));
        System.out.println("   \"Apple\".compareTo(\"Apple\") = " + "Apple".compareTo("Apple"));
        
        System.out.println("\n5. INTEGER COMPARABLE:");
        System.out.println("   Integer implements Comparable<Integer>");
        System.out.println("   10.compareTo(20) = " + Integer.compare(10, 20));
        System.out.println("   20.compareTo(10) = " + Integer.compare(20, 10));
    }

    static void comparatorInterface() {
        System.out.println("--- COMPARATOR INTERFACE (CUSTOM ORDER) ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - java.util.Comparator<T>");
        System.out.println("   - Separate from class");
        System.out.println("   - Multiple ways to compare");
        System.out.println("   - Methods: compare(T o1, T o2), equals(Object)");
        
        System.out.println("\n2. IMPLEMENTING COMPARATOR:");
        
        class Person {
            String name;
            int age;
            
            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }
            
            @Override
            public String toString() {
                return name + "(" + age + ")";
            }
        }
        
        Comparator<Person> byName = (p1, p2) -> p1.name.compareTo(p2.name);
        Comparator<Person> byAge = (p1, p2) -> p1.age - p2.age;
        
        List<Person> persons = Arrays.asList(
            new Person("Charlie", 35),
            new Person("Alice", 30),
            new Person("Bob", 25)
        );
        
        System.out.println("\n3. SORTING WITH COMPARATOR:");
        
        List<Person> list1 = new ArrayList<>(persons);
        Collections.sort(list1, byName);
        System.out.println("   By name: " + list1);
        
        List<Person> list2 = new ArrayList<>(persons);
        Collections.sort(list2, byAge);
        System.out.println("   By age: " + list2);
        
        System.out.println("\n4. USING sort() WITH COMPARATOR:");
        list1.sort(byName);
        System.out.println("   list.sort(byName): " + list1);
        
        list2.sort(byAge.reversed());
        System.out.println("   list.sort(byAge.reversed()): " + list2);
        
        System.out.println("\n5. COMPARATOR COMBINING:");
        Comparator<Person> byNameThenAge = byName.thenComparing(byAge);
        System.out.println("   byName.thenComparing(byAge)");
    }

    static void practicalExamples() {
        System.out.println("--- PRACTICAL EXAMPLES ---");
        
        System.out.println("\n1. BUILT-IN COMPARATORS:");
        
        List<String> words = Arrays.asList("banana", "apple", "cherry");
        words.sort(Comparator.naturalOrder());
        System.out.println("   naturalOrder: " + words);
        
        words.sort(Comparator.reverseOrder());
        System.out.println("   reverseOrder: " + words);
        
        System.out.println("\n2. COMPARING.COMPARING (Java 8+):");
        
        class Employee {
            String name;
            int salary;
            
            Employee(String name, int salary) {
                this.name = name;
                this.salary = salary;
            }
            
            @Override
            public String toString() {
                return name + "($" + salary + ")";
            }
        }
        
        List<Employee> employees = Arrays.asList(
            new Employee("John", 50000),
            new Employee("Jane", 60000),
            new Employee("Bob", 45000)
        );
        
        employees.sort(Comparator.comparing(e -> e.name));
        System.out.println("   By name: " + employees);
        
        employees.sort(Comparator.comparingInt(e -> e.salary));
        System.out.println("   By salary: " + employees);
        
        employees.sort(Comparator.comparing(Employee::getName).reversed());
        System.out.println("   By name reversed: " + employees);
        
        System.out.println("\n3. NULL HANDLING:");
        
        List<String> withNulls = Arrays.asList("Apple", null, "Banana", "Cherry");
        withNulls.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        System.out.println("   nullsFirst: " + withNulls);
        
        withNulls.sort(Comparator.nullsLast(Comparator.reverseOrder()));
        System.out.println("   nullsLast: " + withNulls);
        
        System.out.println("\n4. MULTI-LEVEL SORTING:");
        
        class Student {
            String name;
            int grade;
            double gpa;
            
            Student(String name, int grade, double gpa) {
                this.name = name;
                this.grade = grade;
                this.gpa = gpa;
            }
            
            @Override
            public String toString() {
                return name + "[g" + grade + ", gpa" + gpa + "]";
            }
        }
        
        List<Student> students = Arrays.asList(
            new Student("Alice", 10, 3.5),
            new Student("Bob", 10, 3.8),
            new Student("Charlie", 11, 3.5),
            new Student("Diana", 11, 3.9)
        );
        
        students.sort(Comparator
            .comparingInt(s -> s.grade)
            .thenComparingDouble(s -> s.gpa)
            .reversed());
        
        System.out.println("   Sort by grade desc, then gpa desc: " + students);
        
        System.out.println("\n5. COLLECTORS (Sorting in Stream):");
        
        List<String> names = Arrays.asList("C", "A", "B");
        
        List<String> sorted = names.stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("   Stream sorted: " + sorted);
        
        List<String> reversed = names.stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
        System.out.println("   Stream reversed: " + reversed);
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        String[][] table = {
            {"Aspect", "Comparable", "Comparator"},
            {"Package", "java.lang", "java.util"},
            {"Interface", "compareTo(T o)", "compare(T o1, T o2)"},
            {"Implementation", "In the class itself", "Separate class/lambda"},
            {"Sorting", "Collections.sort(list)", "Collections.sort(list, comp)"},
            {"Multiple", "One natural order", "Multiple custom orders"},
            {"Purpose", "Default/natural order", "Custom/flexible ordering"},
            {"Method", "compareTo()", "compare()"}
        };
        
        System.out.println();
        for (String[] row : table) {
            System.out.printf("%-15s | %-30s | %s%n", row[0], row[1], row[2]);
        }
        
        System.out.println("\n--- CODE EXAMPLE: BOTH ---");
        
        class Product implements Comparable<Product> {
            String name;
            double price;
            
            Product(String name, double price) {
                this.name = name;
                this.price = price;
            }
            
            @Override
            public int compareTo(Product other) {
                return Double.compare(this.price, other.price);
            }
            
            @Override
            public String toString() {
                return name + "($" + price + ")";
            }
        }
        
        Comparator<Product> byNameDesc = (p1, p2) -> p2.name.compareTo(p1.name);
        
        List<Product> products = Arrays.asList(
            new Product("Laptop", 999.99),
            new Product("Phone", 699.99),
            new Product("Tablet", 499.99)
        );
        
        System.out.println("\n   Natural order (price):");
        Collections.sort(products);
        System.out.println("   " + products);
        
        System.out.println("\n   Custom order (name desc):");
        products.sort(byNameDesc);
        System.out.println("   " + products);
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: When to use Comparable vs Comparator?");
        System.out.println("A: Comparable for natural order, Comparator for multiple/custom orders");
        
        System.out.println("\nQ: Can a class implement both?");
        System.out.println("A: Yes, implement Comparable for natural, use Comparator for others");
        
        System.out.println("\nQ: What is compareTo returning 0?");
        System.out.println("A: Elements are equal for sorting purposes");
    }
    
    public String getName() { return null; }
}

import java.util.stream.Collectors;