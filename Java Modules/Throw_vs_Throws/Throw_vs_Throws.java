package Throw_vs_Throws;

import java.io.*;

public class Throw_vs_Throws {

    public static void main(String[] args) {
        System.out.println("=== THROW VS THROWS ===\n");
        
        throwKeyword();
        System.out.println();
        throwsKeyword();
        System.out.println();
        differences();
        System.out.println();
        practicalExamples();
        System.out.println();
        interviewSummary();
    }

    static void throwKeyword() {
        System.out.println("--- THROW KEYWORD ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Used to explicitly throw an exception");
        System.out.println("   - throw new ExceptionType(\"message\");");
        System.out.println("   - Transfer control to catch block");
        
        System.out.println("\n2. SYNTAX:");
        System.out.println("   throw new NullPointerException(\"Object is null\");");
        System.out.println("   throw new IOException(\"File not found\");");
        
        System.out.println("\n3. THROWING RUNTIME EXCEPTION:");
        
        try {
            int age = -5;
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("   Caught: " + e.getMessage());
        }
        
        System.out.println("\n4. THROWING CHECKED EXCEPTION:");
        
        try {
            throw new IOException("Checked exception");
        } catch (IOException e) {
            System.out.println("   Caught: " + e.getMessage());
        }
        
        System.out.println("\n5. RE-THROWING:");
        
        try {
            try {
                throw new RuntimeException("Original");
            } catch (RuntimeException e) {
                System.out.println("   Caught: " + e.getMessage());
                throw new RuntimeException("Re-thrown", e);
            }
        } catch (RuntimeException e) {
            System.out.println("   Re-caught: " + e.getMessage());
            System.out.println("   Original cause: " + e.getCause().getMessage());
        }
    }

    static void throwsKeyword() {
        System.out.println("--- THROWS KEYWORD ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Declares that method may throw exceptions");
        System.out.println("   - Used in method signature");
        System.out.println("   - Caller must handle or declare");
        
        System.out.println("\n2. SYNTAX:");
        System.out.println("   void readFile() throws IOException { }");
        System.out.println("   void process() throws IOException, SQLException { }");
        
        System.out.println("\n3. METHOD SIGNATURE EXAMPLES:");
        
        System.out.println("   public void readFile() throws FileNotFoundException {");
        System.out.println("       FileReader fr = new FileReader(\"file.txt\");");
        System.out.println("   }");
        
        System.out.println("\n4. CALLER RESPONSIBILITY:");
        
        try {
            methodThatThrows();
        } catch (IOException e) {
            System.out.println("   Handled by caller: " + e.getMessage());
        }
        
        System.out.println("\n5. UNCHECKED EXCEPTIONS (No need to declare):");
        
        System.out.println("   // No need to declare RuntimeException");
        System.out.println("   void divide(int a, int b) {");
        System.out.println("       if (b == 0) throw new ArithmeticException(\"div by 0\");");
        System.out.println("   }");
    }

    static void methodThatThrows() throws IOException {
        throw new IOException("Method throws this");
    }

    static void differences() {
        System.out.println("--- KEY DIFFERENCES ---");
        
        String[][] table = {
            {"Aspect", "throw", "throws"},
            {"Purpose", "Throw an exception", "Declare exceptions"},
            {"Location", "Inside method body", "Method signature"},
            {"Syntax", "throw new Exception()", "method() throws Exception"},
            {"Count", "Single exception", "Multiple exceptions"},
            {"Type", "Can be any Exception", "Checked exceptions mainly"},
            {"Use", "Explicitly raise error", "Delegate handling"},
        };
        
        System.out.println();
        for (String[] row : table) {
            System.out.printf("%-12s | %-30s | %s%n", row[0], row[1], row[2]);
        }
        
        System.out.println("\n--- VISUAL COMPARISON ---");
        
        System.out.println("\n   throw (USAGE):");
        System.out.println("   ┌─────────────────────────────┐");
        System.out.println("   │ void method() {            │");
        System.out.println("   │   if (error)               │");
        System.out.println("   │     throw new Exception(); │");
        System.out.println("   │ }                          │");
        System.out.println("   └─────────────────────────────┘");
        
        System.out.println("\n   throws (DECLARATION):");
        System.out.println("   ┌───────────────────────────────────┐");
        System.out.println("   │ void method() throws Exception { │");
        System.out.println("   │     // method body              │");
        System.out.println("   │ }                                │");
        System.out.println("   └───────────────────────────────────┘");
    }

    static void practicalExamples() {
        System.out.println("--- PRACTICAL EXAMPLES ---");
        
        System.out.println("\n1. VALIDATION EXAMPLE:");
        
        class UserValidator {
            public void validate(String username, int age) {
                if (username == null || username.isEmpty()) {
                    throw new IllegalArgumentException("Username required");
                }
                if (age < 0) {
                    throw new IllegalArgumentException("Age cannot be negative");
                }
                if (age < 18) {
                    throw new IllegalArgumentException("Must be 18+");
                }
                System.out.println("   Valid user: " + username + ", age " + age);
            }
        }
        
        UserValidator validator = new UserValidator();
        try {
            validator.validate("John", 25);
            validator.validate("", 20);
        } catch (IllegalArgumentException e) {
            System.out.println("   Validation failed: " + e.getMessage());
        }
        
        System.out.println("\n2. FILE PROCESSING:");
        
        class FileProcessor {
            public void processFile(String path) throws IOException {
                if (path == null) {
                    throw new IllegalArgumentException("Path cannot be null");
                }
                
                File file = new File(path);
                if (!file.exists()) {
                    throw new FileNotFoundException("File not found: " + path);
                }
                
                System.out.println("   Processing: " + path);
            }
        }
        
        FileProcessor fp = new FileProcessor();
        try {
            fp.processFile("nonexistent.txt");
        } catch (IOException e) {
            System.out.println("   File error: " + e.getMessage());
        }
        
        System.out.println("\n3. CHAINED EXCEPTIONS:");
        
        try {
            processData();
        } catch (Exception e) {
            System.out.println("   Caught: " + e.getMessage());
            System.out.println("   Cause: " + (e.getCause() != null ? e.getCause().getMessage() : "none"));
        }
        
        System.out.println("\n4. CUSTOM EXCEPTION WITH THROW:");
        
        class InsufficientBalanceException extends Exception {
            public InsufficientBalanceException(String msg) {
                super(msg);
            }
        }
        
        class BankAccount {
            private double balance = 100;
            
            public void withdraw(double amount) throws InsufficientBalanceException {
                if (amount > balance) {
                    throw new InsufficientBalanceException(
                        "Balance " + balance + " < withdrawal " + amount);
                }
                balance -= amount;
                System.out.println("   Withdrawn: " + amount + ", Balance: " + balance);
            }
        }
        
        BankAccount account = new BankAccount();
        try {
            account.withdraw(150);
        } catch (InsufficientBalanceException e) {
            System.out.println("   Error: " + e.getMessage());
        }
        
        try {
            account.withdraw(50);
        } catch (InsufficientBalanceException e) {
            System.out.println("   Error: " + e.getMessage());
        }
    }

    static void processData() throws Exception {
        try {
            throw new IOException("IO Error");
        } catch (IOException e) {
            throw new Exception("Processing failed", e);
        }
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        System.out.println("\n1. throw vs throws:");
        System.out.println("   throw - throws the exception object");
        System.out.println("   throws - declares what can be thrown");
        
        System.out.println("\n2. When to use throw:");
        System.out.println("   - Explicitly raise an error");
        System.out.println("   - After validation checks");
        System.out.println("   - Business logic errors");
        
        System.out.println("\n3. When to use throws:");
        System.out.println("   - Checked exceptions in method signature");
        System.out.println("   - Delegate handling to caller");
        System.out.println("   - Multiple exception types");
        
        System.out.println("\n4. Common mistakes:");
        System.out.println("   - Using throw for RuntimeException (no need for throws)");
        System.out.println("   - Forgetting to handle declared exceptions");
        System.out.println("   - Throwing exception without message");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: Can we throw RuntimeException?");
        System.out.println("A: Yes, but no need to declare in throws");
        
        System.out.println("\nQ: Can we declare RuntimeException in throws?");
        System.out.println("A: Yes, but not required (unchecked)");
        
        System.out.println("\nQ: What is difference between throw and throws?");
        System.out.println("A: throw throws an object, throws declares exception types");
        
        System.out.println("\nQ: Can we throw multiple exceptions?");
        System.out.println("A: throw一次一个, throws可以声明多个");
        
        System.out.println("\nQ: What happens if we don't handle declared exception?");
        System.out.println("A: Compile-time error for checked exceptions");
    }
}