package Java_Modules;

public class Interfaces_Abstract {

    public static void main(String[] args) {
        System.out.println("=== INTERFACES AND ABSTRACT CLASSES ===\n");
        
        demonstrateAbstractClasses();
        System.out.println();
        demonstrateInterfaces();
        System.out.println();
        demonstrateDifferences();
    }

    static void demonstrateAbstractClasses() {
        System.out.println("--- ABSTRACT CLASSES ---");
        System.out.println("An abstract class is a class that cannot be instantiated");
        System.out.println("It can have both abstract methods (without body) and concrete methods\n");

        Animal dog = new Dog("Buddy", 3);
        dog.eat();
        dog.sleep();
        dog.makeSound();

        Animal cat = new Cat("Whiskers", 2);
        cat.eat();
        cat.sleep();
        cat.makeSound();
    }

    static void demonstrateInterfaces() {
        System.out.println("--- INTERFACES ---");
        System.out.println("An interface is a contract that defines what methods a class must implement");
        System.out.println("A class can implement multiple interfaces (unlike inheritance)\n");

        Drawable circle = new Circle(5);
        circle.draw();
        ((Shape)circle).calculateArea();

        Drawable rectangle = new Rectangle(4, 6);
        rectangle.draw();
        ((Shape)rectangle).calculateArea();
    }

    static void demonstrateDifferences() {
        System.out.println("--- KEY DIFFERENCES ---");
        System.out.println("1. Abstract class can have constructors; interface cannot");
        System.out.println("2. Abstract class can have instance variables; interface variables are final");
        System.out.println("3. Abstract class can extend one class; interface can extend multiple interfaces");
        System.out.println("4. Abstract class methods can be public, protected, private; interface methods are public by default");
    }

    abstract static class Animal {
        protected String name;
        protected int age;

        public Animal(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public abstract void makeSound();

        public void eat() {
            System.out.println(name + " is eating");
        }

        public void sleep() {
            System.out.println(name + " is sleeping");
        }
    }

    static class Dog extends Animal {
        public Dog(String name, int age) {
            super(name, age);
        }

        @Override
        public void makeSound() {
            System.out.println(name + " says: Woof! Woof!");
        }
    }

    static class Cat extends Animal {
        public Cat(String name, int age) {
            super(name, age);
        }

        @Override
        public void makeSound() {
            System.out.println(name + " says: Meow! Meow!");
        }
    }

    interface Drawable {
        void draw();
    }

    interface Shape {
        void calculateArea();
    }

    static class Circle implements Drawable, Shape {
        private double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        @Override
        public void draw() {
            System.out.println("Drawing a circle with radius " + radius);
        }

        @Override
        public void calculateArea() {
            double area = Math.PI * radius * radius;
            System.out.println("Circle area: " + area);
        }
    }

    static class Rectangle implements Drawable, Shape {
        private double width;
        private double height;

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public void draw() {
            System.out.println("Drawing a rectangle " + width + "x" + height);
        }

        @Override
        public void calculateArea() {
            double area = width * height;
            System.out.println("Rectangle area: " + area);
        }
    }
}
