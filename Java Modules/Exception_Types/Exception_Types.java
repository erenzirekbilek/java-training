package Exception_Types;

import java.io.*;

public class Exception_Types {

    public static void main(String[] args) {
        System.out.println("=== COMPILE TIME VS RUNTIME EXCEPTION ===\n");
        
        exceptionHierarchy();
        System.out.println();
        checkedVsUnchecked();
        System.out.println();
        examples();
        System.out.println();
        interviewSummary();
    }

    static void exceptionHierarchy() {
        System.out.println("--- EXCEPTION HIERARCHY ---");
        
        System.out.println("\n1. Throwable (Root):");
        System.out.println("   - Error (JVM errors, not your fault)");
        System.out.println("   - Exception (Application errors)");
        
        System.out.println("\n2. Exception Branch:");
        System.out.println("   - RuntimeException (Unchecked)");
        System.out.println("     * NullPointerException");
        System.out.println("     * ArrayIndexOutOfBoundsException");
        System.out.println("     * IllegalArgumentException");
        System.out.println("     * ArithmeticException");
        System.out.println("   - Other Exceptions (Checked)");
        System.out.println("     * IOException");
        System.out.println("     * SQLException");
        System.out.println("     * ClassNotFoundException");
        
        System.out.println("\n3. DIAGRAM:");
        System.out.println("           Throwable");
        System.out.println("          /         \\");
        System.out.println("        Error    Exception");
        System.out.println("                /         \\");
        System.out.println("        RuntimeException  Other (Checked)");
        System.out.println("        (Unchecked)      IOException");
    }

    static void checkedVsUnchecked() {
        System.out.println("--- CHECKED VS UNCHECKED ---");
        
        System.out.println("\n1. COMPILE-TIME (Checked) Exceptions:");
        System.out.println("   - Subclass of Exception but NOT RuntimeException");
        System.out.println("   - Compiler forces you to handle them");
        System.out.println("   - Declare with 'throws' or use try-catch");
        System.out.println("   - Usually external factors (file, network, DB)");
        
        System.out.println("\n2. RUNTIME (Unchecked) Exceptions:");
        System.out.println("   - Subclass of RuntimeException");
        System.out.println("   - Compiler does NOT force handling");
        System.out.println("   - Usually programming bugs");
        System.out.println("   - Can occur anywhere in code");
        
        System.out.println("\n3. KEY DIFFERENCES:");
        
        String[][] table = {
            {"Aspect", "Checked", "Unchecked"},
            {"Parent", "Exception (not Runtime)", "RuntimeException"},
            {"Compile Time", "Enforced", "Not enforced"},
            {"Handling", "Must handle/declare", "Optional"},
            {"Examples", "IOException, SQLException", "NPE, ArrayIndexOutOfBounds"},
            {"Cause", "External factors", "Programmer error"},
            {"Recovery", "Can recover", "Usually bug"}
        };
        
        System.out.println();
        for (String[] row : table) {
            System.out.printf("%-15s | %-20s | %s%n", row[0], row[1], row[2]);
        }
    }

    static void examples() {
        System.out.println("--- CODE EXAMPLES ---");
        
        System.out.println("\n1. UNCHECKED EXCEPTION (Runtime):");
        
        String str = null;
        try {
            System.out.println("   Length: " + str.length());
        } catch (NullPointerException e) {
            System.out.println("   NullPointerException caught!");
        }
        
        int[] arr = {1, 2, 3};
        try {
            int x = arr[10];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("   ArrayIndexOutOfBoundsException caught!");
        }
        
        try {
            int y = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("   ArithmeticException caught!");
        }
        
        System.out.println("\n2. CHECKED EXCEPTION (Compile Time):");
        
        System.out.println("   File reading (must handle):");
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("nonexistent.txt"));
            String line = reader.readLine();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("   FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("   IOException: " + e.getMessage());
        }
        
        System.out.println("\n3. THROWING CHECKED EXCEPTION:");
        
        try {
            throwCheckedException();
        } catch (IOException e) {
            System.out.println("   Caught: " + e.getMessage());
        }
        
        System.out.println("\n4. DECLARING THROWS:");
        
        System.out.println("   public void readFile() throws IOException { }");
        System.out.println("   public void process() throws SQLException, IOException { }");
        
        System.out.println("\n5. CUSTOM EXCEPTIONS:");
        
        try {
            validateAge(15);
        } catch (InvalidAgeException e) {
            System.out.println("   Custom exception: " + e.getMessage());
        }
    }

    static void throwCheckedException() throws IOException {
        throw new IOException("This is a checked exception");
    }
    
    static void validateAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Age must be 18 or older");
        }
        System.out.println("   Age is valid: " + age);
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        System.out.println("\n1. WHY SEPARATE?");
        System.out.println("   - Checked: External errors you CAN handle");
        System.out.println("   - Unchecked: Programmer bugs (should fix code)");
        
        System.out.println("\n2. WHEN TO USE CHECKED:");
        System.out.println("   - File operations (FileNotFoundException)");
        System.out.println("   - Network (SocketException)");
        System.out.println("   - Database (SQLException)");
        
        System.out.println("\n3. WHEN TO USE UNCHECKED:");
        System.out.println("   - Null pointer access");
        System.out.println("   - Invalid arguments");
        System.out.println("   - Logic errors");
        
        System.out.println("\n4. BEST PRACTICES:");
        System.out.println("   - Don't catch RuntimeException unless necessary");
        System.out.println("   - Always handle checked exceptions properly");
        System.out.println("   - Use specific exception types");
        System.out.println("   - Don't swallow exceptions without logging");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: Difference between throw and throws?");
        System.out.println("A: throw =抛出异常对象, throws =声明异常");
        
        System.out.println("\nQ: Can we throw RuntimeException?");
        System.out.println("A: Yes, but no need to declare in throws");
        
        System.out.println("\nQ: Error vs Exception?");
        System.out.println("A: Error = JVM issues, cannot recover; Exception = can handle");
    }
}

class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}