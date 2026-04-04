package Final_Finally_Finalize;

import java.io.IOException;

public class Final_Finally_Finalize {

    public static void main(String[] args) {
        System.out.println("=== FINAL VS FINALLY VS FINALIZE ===\n");
        
        finalKeyword();
        System.out.println();
        finallyBlock();
        System.out.println();
        finalizeMethod();
        System.out.println();
        comparisonTable();
    }

    static void finalKeyword() {
        System.out.println("--- FINAL KEYWORD ---");
        
        System.out.println("\n1. FINAL VARIABLE:");
        System.out.println("   - Cannot be reassigned once assigned");
        System.out.println("   - Must be initialized either:");
        System.out.println("     * At declaration");
        System.out.println("     * In constructor (for instance variables)");
        System.out.println("     * In static initializer (for static variables)");
        
        final int CONSTANT = 100;
        System.out.println("   final int CONSTANT = " + CONSTANT);
        
        class Person {
            final String name;
            
            Person(String name) {
                this.name = name;
            }
        }
        
        Person p = new Person("John");
        System.out.println("   Person name: " + p.name);
        
        System.out.println("\n2. FINAL METHOD:");
        System.out.println("   - Cannot be overridden by subclasses");
        System.out.println("   - Used for security and performance");
        
        System.out.println("   class Parent {");
        System.out.println("       public final void show() { }");
        System.out.println("   }");
        
        System.out.println("\n3. FINAL CLASS:");
        System.out.println("   - Cannot be extended/inherited");
        System.out.println("   - String, Integer, Math are final classes");
        System.out.println("   - Prevents behavior modification for security");
        
        System.out.println("   public final class String { }");
        System.out.println("   public final class Integer { }");
        
        System.out.println("\n4. FINAL REFERENCE:");
        System.out.println("   - Reference itself is constant, not the object");
        
        final StringBuilder sb = new StringBuilder("Hello");
        sb.append(" World");
        System.out.println("   sb.append() works: " + sb.toString());
        
        System.out.println("   Cannot: sb = new StringBuilder() - COMPILE ERROR");
    }

    static void finallyBlock() {
        System.out.println("--- FINALLY BLOCK ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Always executes regardless of exception");
        System.out.println("   - Used for resource cleanup (file, connection)");
        System.out.println("   - Executes even if return statement in try/catch");
        
        System.out.println("\n2. BASIC EXAMPLE:");
        
        try {
            int result = 10 / 2;
            System.out.println("   Try: 10 / 2 = " + result);
        } catch (ArithmeticException e) {
            System.out.println("   Catch: Division by zero");
        } finally {
            System.out.println("   Finally: Always executes");
        }
        
        System.out.println("\n3. FINALLY WITH RETURN:");
        
        String test = testFinally();
        System.out.println("   Return value: " + test);
        
        System.out.println("\n4. TRY-WITH-RESOURCES (Java 7+):");
        
        class AutoCloseableResource implements AutoCloseable {
            public void doSomething() {
                System.out.println("   Resource doing work");
            }
            
            @Override
            public void close() {
                System.out.println("   Resource automatically closed");
            }
        }
        
        try (AutoCloseableResource res = new AutoCloseableResource()) {
            res.doSomething();
        } catch (Exception e) {
            System.out.println("   Exception: " + e.getMessage());
        }
        
        System.out.println("\n5. FINALLY NOT EXECUTED (JVM Crash):");
        System.out.println("   - System.exit()");
        System.out.println("   - Runtime.getRuntime().halt()");
        System.out.println("   - JVM crash (native code error)");
        
        try {
            System.exit(0);
        } catch (Exception e) {}
    }

    static String testFinally() {
        try {
            return "Return from try";
        } catch (Exception e) {
            return "Return from catch";
        } finally {
            System.out.println("   Finally executes before return");
        }
    }

    static void finalizeMethod() {
        System.out.println("--- FINALIZE METHOD ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Called by garbage collector before object removal");
        System.out.println("   - Protected void finalize() throws Throwable");
        System.out.println("   - Legacy cleanup mechanism (deprecated in Java 9+)");
        
        System.out.println("\n2. DEPRECATED REASONS:");
        System.out.println("   - Non-deterministic (when GC runs is unpredictable)");
        System.out.println("   - Can cause performance issues");
        System.out.println("   - Not guaranteed to execute");
        System.out.println("   - Can resurrect objects (bad practice)");
        
        System.out.println("\n3. EXAMPLE:");
        
        class Resource implements Comparable<Resource> {
            String name;
            
            Resource(String name) {
                this.name = name;
            }
            
            @Override
            protected void finalize() throws Throwable {
                System.out.println("   Finalize called for: " + name);
            }
            
            @Override
            public int compareTo(Resource o) {
                return name.compareTo(o.name);
            }
        }
        
        Resource r = new Resource("Test");
        r = null;
        
        System.out.println("   Created Resource and set to null");
        System.out.println("   Calling System.gc() to request GC");
        System.gc();
        
        System.out.println("\n4. MODERN ALTERNATIVES:");
        System.out.println("   - Try-with-resources (AutoCloseable)");
        System.out.println("   - Cleaner API (Java 9+)");
        System.out.println("   - Reference queues");
        
        System.out.println("\n5. CLEANER API EXAMPLE (Java 9+):");
        
        class CleanupTask implements Runnable {
            @Override
            public void run() {
                System.out.println("   Cleaner: Cleanup performed");
            }
        }
        
        Cleaner cleaner = Cleaner.create();
        Resource cleanRes = new Resource("Cleanable");
        cleaner.register(cleanRes, new CleanupTask());
        
        System.out.println("   Registered cleanup task");
        cleanRes = null;
        System.gc();
    }

    static void comparisonTable() {
        System.out.println("--- COMPARISON TABLE ---");
        
        String[][] table = {
            {"Feature", "final", "finally", "finalize"},
            {"Type", "Keyword", "Block", "Method"},
            {"Purpose", "Prevent modification", "Ensure cleanup", "Garbage collection"},
            {"When", "Compile time", "Exception handling", "Runtime (GC)"},
            {"Usage", "Variable/Method/Class", "try-catch-finally", "Object lifecycle"},
            {"Execution", "N/A", "Always (except System.exit)", "When GC runs"},
            {"Deprecated", "No", "No", "Yes (Java 9+)"}
        };
        
        System.out.println();
        for (String[] row : table) {
            System.out.printf("%-12s | %-20s | %-20s | %s%n", 
                row[0], row[1], row[2], row[3]);
        }
        
        System.out.println("\n--- CODE COMPARISON ---");
        
        System.out.println("\n1. FINAL:");
        final int x = 10;
        System.out.println("   final int x = " + x + " - cannot change");
        
        System.out.println("\n2. FINALLY:");
        try {
            System.out.println("   try block");
        } finally {
            System.out.println("   finally - cleanup always runs");
        }
        
        System.out.println("\n3. FINALIZE (DEPRECATED):");
        System.out.println("   @Override");
        System.out.println("   protected void finalize() {");
        System.out.println("       // cleanup code");
        System.out.println("   }");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: Does finally always execute?");
        System.out.println("A: Except for System.exit() or JVM crash");
        
        System.out.println("\nQ: Can finally block without catch?");
        System.out.println("A: Yes, try-finally is valid");
        
        System.out.println("\nQ: Why is finalize deprecated?");
        System.out.println("A: Non-deterministic, performance issues, not guaranteed");
        
        System.out.println("\nQ: Difference between final and finally?");
        System.out.println("A: final = prevent change, finally = always execute cleanup");
    }
}