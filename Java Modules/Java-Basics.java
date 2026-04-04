package Java_Modules;

public class Java_Basics {
    
    public static void main(String[] args) {
        System.out.println("Java Basics");
    }

    static class DataTypes {
        byte b = 127;
        short s = 32767;
        int i = 2147483647;
        long l = 9223372036854775807L;
        float f = 3.14f;
        double d = 3.14159;
        char c = 'A';
        boolean bool = true;
        String str = "Hello";
    }

    static class Operators {
        void operators() {
            int a = 10, b = 5;
            int sum = a + b;
            int diff = a - b;
            int prod = a * b;
            int quot = a / b;
            int rem = a % b;
            boolean greater = a > b;
            boolean equals = a == b;
            boolean and = true && false;
            boolean or = true || false;
            int inc = ++a;
            int dec = --b;
        }
    }

    static class ControlFlow {
        void conditionals(int num) {
            if (num > 0) {
                System.out.println("Positive");
            } else if (num < 0) {
                System.out.println("Negative");
            } else {
                System.out.println("Zero");
            }

            switch(num) {
                case 1: System.out.println("One"); break;
                case 2: System.out.println("Two"); break;
                default: System.out.println("Other");
            }
        }

        void loops(int n) {
            for(int i = 0; i < n; i++) {
                System.out.println(i);
            }

            int i = 0;
            while(i < n) {
                System.out.println(i++);
            }

            i = 0;
            do {
                System.out.println(i++);
            } while(i < n);
        }
    }

    static class Arrays {
        void arrays() {
            int[] arr = new int[5];
            int[] arr2 = {1, 2, 3, 4, 5};
            
            for(int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }

            for(int num : arr2) {
                System.out.println(num);
            }
        }
    }

    static class Methods {
        int add(int a, int b) {
            return a + b;
        }

        void print(String msg) {
            System.out.println(msg);
        }

        int[] reverse(int[] arr) {
            int n = arr.length;
            int[] result = new int[n];
            for(int i = 0; i < n; i++) {
                result[i] = arr[n - 1 - i];
            }
            return result;
        }
    }

    static class OOP {
        static class Person {
            String name;
            int age;

            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }

            void display() {
                System.out.println("Name: " + name + ", Age: " + age);
            }
        }

        static class Student extends Person {
            int grade;

            Student(String name, int age, int grade) {
                super(name, age);
                this.grade = grade;
            }

            @Override
            void display() {
                super.display();
                System.out.println("Grade: " + grade);
            }
        }
    }

    static class Encapsulation {
        static class Account {
            private double balance;

            public double getBalance() {
                return balance;
            }

            public void deposit(double amount) {
                if(amount > 0) {
                    balance += amount;
                }
            }

            public void withdraw(double amount) {
                if(amount > 0 && amount <= balance) {
                    balance -= amount;
                }
            }
        }
    }

    static class InheritanceDemo {
        static class Animal {
            void eat() {
                System.out.println("Eating");
            }
        }

        static class Dog extends Animal {
            void bark() {
                System.out.println("Barking");
            }
        }
    }

    static class Polymorphism {
        static class Shape {
            void draw() {
                System.out.println("Drawing shape");
            }
        }

        static class Circle extends Shape {
            @Override
            void draw() {
                System.out.println("Drawing circle");
            }
        }

        static class Rectangle extends Shape {
            @Override
            void draw() {
                System.out.println("Drawing rectangle");
            }
        }
    }

    static class Abstraction {
        static abstract class Vehicle {
            abstract void start();
            
            void stop() {
                System.out.println("Stopped");
            }
        }

        static class Car extends Vehicle {
            @Override
            void start() {
                System.out.println("Car started");
            }
        }
    }

    static class InterfaceDemo {
        interface Drawable {
            void draw();
        }

        static class Line implements Drawable {
            @Override
            public void draw() {
                System.out.println("Drawing line");
            }
        }
    }

    static class ExceptionHandling {
        void handle() {
            try {
                int[] arr = new int[5];
                arr[10] = 1;
            } catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Array index out of bounds");
            } finally {
                System.out.println("Finally block");
            }
        }

        void throwDemo(int age) throws IllegalArgumentException {
            if(age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
        }
    }

    static class Collections {
        import java.util.*;
        
        void lists() {
            ArrayList<String> list = new ArrayList<>();
            list.add("Apple");
            list.add("Banana");
            list.add("Cherry");
            
            for(String s : list) {
                System.out.println(s);
            }
        }

        void maps() {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("One", 1);
            map.put("Two", 2);
            
            for(String key : map.keySet()) {
                System.out.println(key + ": " + map.get(key));
            }
        }

        void sets() {
            HashSet<Integer> set = new HashSet<>();
            set.add(1);
            set.add(2);
            set.add(3);
            
            for(Integer i : set) {
                System.out.println(i);
            }
        }
    }

    static class FileIO {
        void fileOperations() {
            try {
                java.io.FileWriter writer = new java.io.FileWriter("output.txt");
                writer.write("Hello World");
                writer.close();
                
                java.io.FileReader reader = new java.io.FileReader("output.txt");
                int ch;
                while((ch = reader.read()) != -1) {
                    System.out.print((char)ch);
                }
                reader.close();
            } catch(java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Generics {
        static class Box<T> {
            private T content;
            
            public void set(T content) {
                this.content = content;
            }
            
            public T get() {
                return content;
            }
        }

        static <T> void printArray(T[] arr) {
            for(T item : arr) {
                System.out.println(item);
            }
        }
    }

    static class Lambda {
        interface MathOperation {
            int operation(int a, int b);
        }

        void lambda() {
            MathOperation add = (a, b) -> a + b;
            MathOperation multiply = (a, b) -> a * b;
            
            System.out.println(add.operation(5, 3));
            System.out.println(multiply.operation(5, 3));
        }
    }

    static class Threads {
        void threadDemo() {
            Thread t = new Thread(() -> {
                for(int i = 0; i < 5; i++) {
                    System.out.println("Running");
                }
            });
            t.start();
        }

        class MyThread extends Thread {
            public void run() {
                System.out.println("Thread running");
            }
        }
    }
}
