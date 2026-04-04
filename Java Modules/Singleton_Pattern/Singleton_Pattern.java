package Singleton_Pattern;

public class Singleton_Pattern {

    public static void main(String[] args) {
        System.out.println("=== SINGLETON DESIGN PATTERN ===\n");
        
        singletonDefinition();
        System.out.println();
        eagerInitialization();
        System.out.println();
        lazyInitialization();
        System.out.println();
        doubleCheckedLocking();
        System.out.println();
        enumSingleton();
        System.out.println();
        interviewSummary();
    }

    static void singletonDefinition() {
        System.out.println("--- SINGLETON PATTERN ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Creational design pattern");
        System.out.println("   - Ensures only ONE instance exists");
        System.out.println("   - Provides global access point");
        
        System.out.println("\n2. USE CASES:");
        System.out.println("   - Database connections");
        System.out.println("   - Configuration managers");
        System.out.println("   - Logger instances");
        System.out.println("   - Thread pools");
        System.out.println("   - Cache managers");
        
        System.out.println("\n3. REQUIREMENTS:");
        System.out.println("   - Private constructor");
        System.out.println("   - Private static instance");
        System.out.println("   - Public static getInstance() method");
        
        System.out.println("\n4. BASIC STRUCTURE:");
        
        System.out.println("   class Singleton {");
        System.out.println("       private static Singleton instance;");
        System.out.println("       ");
        System.out.println("       private Singleton() { }");
        System.out.println("       ");
        System.out.println("       public static Singleton getInstance() {");
        System.out.println("           if (instance == null) {");
        System.out.println("               instance = new Singleton();");
        System.out.println("           }");
        System.out.println("           return instance;");
        System.out.println("       }");
        System.out.println("   }");
    }

    static void eagerInitialization() {
        System.out.println("--- EAGER INITIALIZATION ---");
        
        System.out.println("\n1. HOW IT WORKS:");
        System.out.println("   - Instance created at class loading time");
        System.out.println("   - Thread-safe by default");
        System.out.println("   - Simple implementation");
        
        System.out.println("\n2. IMPLEMENTATION:");
        
        class EagerSingleton {
            private static final EagerSingleton instance = new EagerSingleton();
            
            private EagerSingleton() {
                System.out.println("   EagerSingleton created");
            }
            
            public static EagerSingleton getInstance() {
                return instance;
            }
            
            public void showMessage() {
                System.out.println("   Eager Singleton method");
            }
        }
        
        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance();
        
        System.out.println("   Same instance? " + (eager1 == eager2));
        
        System.out.println("\n3. ADVANTAGES:");
        System.out.println("   + Simple to implement");
        System.out.println("   + Thread-safe automatically");
        System.out.println("   + No synchronization needed");
        
        System.out.println("\n4. DISADVANTAGES:");
        System.out.println("   - Created even if not used");
        System.out.println("   - Can't pass constructor arguments");
        System.out.println("   - May waste resources");
        
        System.out.println("\n5. VARIANT: STATIC BLOCK:");
        
        class StaticBlockSingleton {
            private static StaticBlockSingleton instance;
            
            static {
                instance = new StaticBlockSingleton();
            }
            
            private StaticBlockSingleton() {
                System.out.println("   StaticBlockSingleton created");
            }
            
            public static StaticBlockSingleton getInstance() {
                return instance;
            }
        }
        
        StaticBlockSingleton sbs = StaticBlockSingleton.getInstance();
        System.out.println("   Static block singleton: " + (sbs != null));
    }

    static void lazyInitialization() {
        System.out.println("--- LAZY INITIALIZATION ---");
        
        System.out.println("\n1. HOW IT WORKS:");
        System.out.println("   - Instance created only when needed");
        System.out.println("   - Saves resources");
        System.out.println("   - But NOT thread-safe!");
        
        System.out.println("\n2. UNSAFE IMPLEMENTATION:");
        
        class LazySingleton {
            private static LazySingleton instance;
            
            private LazySingleton() {
                System.out.println("   LazySingleton created");
            }
            
            public static LazySingleton getInstance() {
                if (instance == null) {
                    instance = new LazySingleton();
                }
                return instance;
            }
        }
        
        System.out.println("   This is NOT thread-safe!");
        System.out.println("   Race condition possible in multi-threaded environment");
        
        System.out.println("\n3. THREAD-SAFE (SYNCHRONIZED):");
        
        class ThreadSafeLazySingleton {
            private static volatile ThreadSafeLazySingleton instance;
            
            private ThreadSafeLazySingleton() {
                System.out.println("   ThreadSafeLazySingleton created");
            }
            
            public static synchronized ThreadSafeLazySingleton getInstance() {
                if (instance == null) {
                    instance = new ThreadSafeLazySingleton();
                }
                return instance;
            }
        }
        
        ThreadSafeLazySingleton t1 = ThreadSafeLazySingleton.getInstance();
        ThreadSafeLazySingleton t2 = ThreadSafeLazySingleton.getInstance();
        
        System.out.println("   Same instance? " + (t1 == t2));
        
        System.out.println("\n4. DISADVANTAGES OF SYNCHRONIZED METHOD:");
        System.out.println("   - Every call adds synchronization overhead");
        System.out.println("   - Performance bottleneck");
        System.out.println("   - Only first call needs protection");
    }

    static void doubleCheckedLocking() {
        System.out.println("--- DOUBLE-CHECKED LOCKING ---");
        
        System.out.println("\n1. HOW IT WORKS:");
        System.out.println("   - Check instance twice with synchronization");
        System.out.println("   - First check without lock (fast path)");
        System.out.println("   - Second check inside synchronized block");
        System.out.println("   - Uses 'volatile' keyword");
        
        System.out.println("\n2. IMPLEMENTATION:");
        
        class DoubleCheckedSingleton {
            private static volatile DoubleCheckedSingleton instance;
            
            private DoubleCheckedSingleton() {
                System.out.println("   DoubleCheckedSingleton created");
            }
            
            public static DoubleCheckedSingleton getInstance() {
                if (instance == null) {
                    synchronized (DoubleCheckedSingleton.class) {
                        if (instance == null) {
                            instance = new DoubleCheckedSingleton();
                        }
                    }
                }
                return instance;
            }
        }
        
        DoubleCheckedSingleton d1 = DoubleCheckedSingleton.getInstance();
        DoubleCheckedSingleton d2 = DoubleCheckedSingleton.getInstance();
        
        System.out.println("   Same instance? " + (d1 == d2));
        
        System.out.println("\n3. WHY VOLATILE?");
        System.out.println("   - Prevents instruction reordering");
        System.out.println("   - Without volatile:");
        System.out.println("     1. Allocate memory");
        System.out.println("     2. Assign reference");
        System.out.println("     3. Call constructor");
        System.out.println("   - Thread might see non-fully-constructed object");
        
        System.out.println("\n4. ADVANTAGES:");
        System.out.println("   + Thread-safe without full synchronization");
        System.out.println("   + Lazy initialization");
        System.out.println("   + High performance");
        
        System.out.println("\n5. DISADVANTAGES:");
        System.out.println("   - Complex to understand");
        System.out.println("   - Requires Java 5+ for correct behavior");
        System.out.println("   - Some consider it anti-pattern");
    }

    static void enumSingleton() {
        System.out.println("--- ENUM SINGLETON (BEST PRACTICE) ---");
        
        System.out.println("\n1. HOW IT WORKS:");
        System.out.println("   - Java enum values are instantiated once");
        System.out.println("   - Thread-safe by JVM guarantee");
        System.out.println("   - Serialization-safe automatically");
        
        System.out.println("\n2. IMPLEMENTATION:");
        
        enum EnumSingleton {
            INSTANCE;
            
            private EnumSingleton() {
                System.out.println("   EnumSingleton created");
            }
            
            public void showMessage() {
                System.out.println("   EnumSingleton method");
            }
            
            public static EnumSingleton getInstance() {
                return INSTANCE;
            }
        }
        
        EnumSingleton e1 = EnumSingleton.INSTANCE;
        EnumSingleton e2 = EnumSingleton.getInstance();
        
        System.out.println("   Same instance? " + (e1 == e2));
        
        System.out.println("\n3. ADVANTAGES:");
        System.out.println("   + Thread-safe (JVM guarantee)");
        System.out.println("   + Serialization handled automatically");
        System.out.println("   + Reflection-proof (cannot create new instance)");
        System.out.println("   + Simple to implement");
        System.out.println("   + Cannot be extended (but also cannot extend others)");
        
        System.out.println("\n4. DISADVANTAGES:");
        System.out.println("   - Cannot extend other classes");
        System.out.println("   - Cannot use lazy initialization");
        System.out.println("   - May feel like abuse of enum");
        
        System.out.println("\n5. COMPARISON:");
        
        String[][] table = {
            {"Method", "Thread-Safe", "Lazy Init", "Reflection-Safe", "Simple"},
            {"Eager", "Yes", "No", "No", "Yes"},
            {"Lazy (sync)", "Yes", "Yes", "No", "Yes"},
            {"Double-Checked", "Yes", "Yes", "No", "No"},
            {"Enum", "Yes", "No", "Yes", "Yes"}
        };
        
        System.out.println();
        for (String[] row : table) {
            System.out.printf("%-20s | %-12s | %-10s | %-12s | %s%n", 
                row[0], row[1], row[2], row[3], row[4]);
        }
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        System.out.println("\n1. SINGLETON USE CASES:");
        System.out.println("   - Configuration managers");
        System.out.println("   - Database connection pools");
        System.out.println("   - Logger instances");
        System.out.println("   - Caches");
        
        System.out.println("\n2. IMPLEMENTATION CHOICES:");
        System.out.println("   - Eager: Simple, always created");
        System.out.println("   - Lazy (synchronized): Thread-safe but slow");
        System.out.println("   - Double-checked: Efficient, uses volatile");
        System.out.println("   - Enum: Best practice, most secure");
        
        System.out.println("\n3. BREAKING SINGLETON:");
        
        System.out.println("   - Reflection:");
        System.out.println("     Constructor constructor = cls.getDeclaredConstructor();");
        System.out.println("     constructor.setAccessible(true);");
        System.out.println("     Object obj = constructor.newInstance();");
        
        System.out.println("\n   - Serialization:");
        System.out.println("     Add readResolve() method to return instance");
        
        System.out.println("\n4. BEST IMPLEMENTATION:");
        System.out.println("   enum Singleton { INSTANCE }");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: Why use singleton pattern?");
        System.out.println("A: Global access, single instance, resource management");
        
        System.out.println("\nQ: Difference between eager and lazy?");
        System.out.println("A: Eager creates at load time, lazy on first request");
        
        System.out.println("\nQ: Why double-checked locking uses volatile?");
        System.out.println("A: Prevents instruction reordering, ensures proper construction");
        
        System.out.println("\nQ: Best singleton implementation?");
        System.out.println("A: Enum singleton (thread-safe, serialization-safe, reflection-safe)");
    }
}