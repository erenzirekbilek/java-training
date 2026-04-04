package Super_vs_This;

public class Super_vs_This {

    public static void main(String[] args) {
        System.out.println("=== SUPER VS THIS ===\n");
        
        thisKeyword();
        System.out.println();
        superKeyword();
        System.out.println();
        differences();
        System.out.println();
        practicalExamples();
        System.out.println();
        interviewSummary();
    }

    static void thisKeyword() {
        System.out.println("--- THIS KEYWORD ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Reference to current object");
        System.out.println("   - Used to differentiate instance variables from parameters");
        System.out.println("   - Available in non-static methods");
        
        System.out.println("\n2. USES:");
        System.out.println("   - Reference current object");
        System.out.println("   - Pass current object as parameter");
        System.out.println("   - Constructor chaining (this())");
        
        System.out.println("\n3. EXAMPLES:");
        
        class Person {
            String name;
            int age;
            
            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }
            
            void display() {
                System.out.println("   Name: " + this.name + ", Age: " + this.age);
            }
            
            void compare(Person other) {
                System.out.println("   Comparing with: " + other.name);
            }
            
            Person getThis() {
                return this;
            }
        }
        
        Person p = new Person("John", 30);
        p.display();
        p.compare(p);
        System.out.println("   getThis() == p: " + (p.getThis() == p));
        
        System.out.println("\n4. CONSTRUCTOR CHAINING:");
        
        class Employee {
            String name;
            int salary;
            String dept;
            
            Employee(String name) {
                this(name, 0);
            }
            
            Employee(String name, int salary) {
                this(name, salary, "General");
            }
            
            Employee(String name, int salary, String dept) {
                this.name = name;
                this.salary = salary;
                this.dept = dept;
            }
            
            void show() {
                System.out.println("   " + name + ", " + salary + ", " + dept);
            }
        }
        
        Employee e1 = new Employee("Alice");
        Employee e2 = new Employee("Bob", 50000);
        Employee e3 = new Employee("Charlie", 60000, "IT");
        
        e1.show();
        e2.show();
        e3.show();
    }

    static void superKeyword() {
        System.out.println("--- SUPER KEYWORD ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Reference to parent class");
        System.out.println("   - Used to access parent constructor, methods, variables");
        System.out.println("   - Available in subclass");
        
        System.out.println("\n2. USES:");
        System.out.println("   - Call parent constructor (super())");
        System.out.println("   - Access parent method (super.method())");
        System.out.println("   - Access parent variable (super.variable)");
        
        System.out.println("\n3. EXAMPLES:");
        
        class Animal {
            String name = "Animal";
            
            Animal() {
                System.out.println("   Animal constructor");
            }
            
            void speak() {
                System.out.println("   Animal speaks");
            }
            
            void eat() {
                System.out.println("   Animal eats");
            }
        }
        
        class Dog extends Animal {
            String name = "Dog";
            
            Dog() {
                super();
                System.out.println("   Dog constructor");
            }
            
            @Override
            void speak() {
                System.out.println("   Dog barks");
            }
            
            void show() {
                System.out.println("   this.name: " + this.name);
                System.out.println("   super.name: " + super.name);
                
                this.speak();
                super.speak();
            }
        }
        
        Dog dog = new Dog();
        dog.show();
        
        System.out.println("\n4. CONSTRUCTOR CHAINING:");
        
        class Vehicle {
            String type;
            
            Vehicle() {
                System.out.println("   Vehicle()");
            }
            
            Vehicle(String type) {
                this.type = type;
                System.out.println("   Vehicle(type)");
            }
        }
        
        class Car extends Vehicle {
            String model;
            
            Car() {
                super("Sedan");
                System.out.println("   Car()");
            }
            
            Car(String model) {
                this();
                this.model = model;
                System.out.println("   Car(model)");
            }
        }
        
        Car c = new Car("Tesla");
    }

    static void differences() {
        System.out.println("--- KEY DIFFERENCES ---");
        
        String[][] table = {
            {"Aspect", "this", "super"},
            {"Reference", "Current object", "Parent object"},
            {"Purpose", "Refer to own members", "Access parent members"},
            {"Usage", "In any non-static method", "In subclass only"},
            {"Constructor", "this() calls own constructor", "super() calls parent constructor"},
            {"Variable", "this.x refers to instance variable", "super.x refers to parent variable"},
            {"Method", "this.method() calls own method", "super.method() calls parent method"},
        };
        
        System.out.println();
        for (String[] row : table) {
            System.out.printf("%-12s | %-20s | %s%n", row[0], row[1], row[2]);
        }
        
        System.out.println("\n--- FLOW DIAGRAM ---");
        
        System.out.println("\n   super() - calls parent constructor");
        System.out.println("       │");
        System.out.println("       v");
        System.out.println("   parent class initialized");
        System.out.println("       │");
        System.out.println("       v");
        System.out.println("   this() - calls own constructor");
        System.out.println("       │");
        System.out.println("       v");
        System.out.println("   subclass initialized");
    }

    static void practicalExamples() {
        System.out.println("--- PRACTICAL EXAMPLES ---");
        
        System.out.println("\n1. PARAMETER HIDING:");
        
        class Parent {
            int value = 10;
        }
        
        class Child extends Parent {
            int value = 20;
            
            void display() {
                System.out.println("   this.value: " + this.value);
                System.out.println("   super.value: " + super.value);
            }
        }
        
        Child child = new Child();
        child.display();
        
        System.out.println("\n2. METHOD OVERRIDING:");
        
        class Shape {
            void draw() {
                System.out.println("   Drawing shape");
            }
            
            void calculate() {
                System.out.println("   Calculating area");
            }
        }
        
        class Circle extends Shape {
            @Override
            void draw() {
                System.out.println("   Drawing circle");
            }
            
            void callParent() {
                super.draw();
                super.calculate();
            }
        }
        
        Circle circle = new Circle();
        circle.draw();
        circle.callParent();
        
        System.out.println("\n3. CONSTRUCTOR WITH SUPER vs THIS:");
        
        class Base {
            Base(int x) {
                System.out.println("   Base constructor: " + x);
            }
        }
        
        class Derived extends Base {
            Derived() {
                super(100);
                System.out.println("   Derived constructor");
            }
            
            Derived(int x) {
                super(x * 2);
                System.out.println("   Derived constructor: " + x);
            }
        }
        
        new Derived();
        new Derived(50);
        
        System.out.println("\n4. CHAINING ORDER:");
        
        class A {
            A() {
                System.out.println("   A constructor");
            }
        }
        
        class B extends A {
            B() {
                System.out.println("   B constructor");
            }
        }
        
        class C extends B {
            C() {
                System.out.println("   C constructor");
            }
        }
        
        new C();
        
        System.out.println("\n   Order: A -> B -> C");
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        System.out.println("\n1. KEY POINTS:");
        System.out.println("   this: refers to current object");
        System.out.println("   super: refers to parent class");
        System.out.println("   Both must be first statement in constructor");
        
        System.out.println("\n2. WHEN TO USE this:");
        System.out.println("   - Same name instance variable and parameter");
        System.out.println("   - Pass current object to another method");
        System.out.println("   - Constructor chaining");
        
        System.out.println("\n3. WHEN TO USE super:");
        System.out.println("   - Access parent constructor");
        System.out.println("   - Access overridden parent method");
        System.out.println("   - Access parent instance variable");
        
        System.out.println("\n4. DEFAULT BEHAVIOR:");
        System.out.println("   - If super() not called, super() default is called");
        System.out.println("   - If this() not called, default constructor used");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: Can we use super and this in static method?");
        System.out.println("A: No, only in non-static context");
        
        System.out.println("\nQ: Can super() and this() be in same constructor?");
        System.out.println("A: Yes, but only one of them per constructor");
        
        System.out.println("\nQ: What is the order of constructor call?");
        System.out.println("A: Parent constructor first, then child");
        
        System.out.println("\nQ: this vs this()?");
        System.out.println("A: this is reference, this() is constructor call");
    }
}