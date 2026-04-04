package Try_Catch_Finally;

public class Try_Catch_Finally {

    public static void main(String[] args) {
        System.out.println("=== EXCEPTION HANDLING: TRY-CATCH-FINALLY ===\n");
        
        basicTryCatch();
        multipleCatch();
        tryCatchFinally();
        tryWithResources();
        throwingExceptions();
    }

    static void basicTryCatch() {
        System.out.println("--- BASIC TRY-CATCH ---");
        
        try {
            int[] numbers = {1, 2, 3, 4, 5};
            System.out.println("Attempting to access index 10...");
            int result = numbers[10];
            System.out.println("Value: " + result);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exception caught: " + e.getMessage());
            System.out.println("Index does not exist in the array!");
        }
        System.out.println("Program continues after catch block\n");
    }

    static void multipleCatch() {
        System.out.println("--- MULTIPLE CATCH BLOCKS ---");
        
        try {
            String input = "hello";
            System.out.println("Trying to parse string as integer: " + input);
            int num = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: Cannot parse to integer");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: Object is null");
        } catch (Exception e) {
            System.out.println("General Exception: " + e.getMessage());
        }
        System.out.println();
    }

    static void tryCatchFinally() {
        System.out.println("--- TRY-CATCH-FINALLY ---");
        
        try {
            int a = 10, b = 0;
            System.out.println("Attempting to divide " + a + " by " + b);
            int result = a / b;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException: Cannot divide by zero");
        } finally {
            System.out.println("Finally block: This ALWAYS executes");
            System.out.println("Used for cleanup - closing files, connections, etc.");
        }
        System.out.println();
    }

    static void tryWithResources() {
        System.out.println("--- TRY-WITH-RESOURCES ---");
        System.out.println("Resources are automatically closed after use\n");
        
        try (FileReaderMock reader = new FileReaderMock("data.txt")) {
            String content = reader.read();
            System.out.println("File content: " + content);
        } catch (FileException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println("Resource automatically closed!\n");
    }

    static void throwingExceptions() {
        System.out.println("--- THROWING EXCEPTIONS ---");
        
        try {
            validateAge(-5);
            validateAge(25);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        
        try {
            divideWithThrow(10, 0);
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }

    static void validateAge(int age) throws IllegalArgumentException {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        System.out.println("Age is valid: " + age);
    }

    static void divideWithThrow(int a, int b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        System.out.println("Result: " + (a / b));
    }

    static class FileReaderMock implements AutoCloseable {
        private String filename;
        private boolean closed = false;

        public FileReaderMock(String filename) throws FileException {
            this.filename = filename;
            if (filename == null || filename.isEmpty()) {
                throw new FileException("Filename cannot be null or empty");
            }
            System.out.println("Opened file: " + filename);
        }

        public String read() {
            return "Sample file content";
        }

        @Override
        public void close() {
            closed = true;
            System.out.println("File closed: " + filename);
        }
    }

    static class FileException extends Exception {
        public FileException(String message) {
            super(message);
        }
    }
}
