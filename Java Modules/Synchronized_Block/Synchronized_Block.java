package Synchronized_Block;

import java.util.concurrent.*;

public class Synchronized_Block {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== SYNCHRONIZED BLOCK ===\n");
        
        basicSynchronized();
        System.out.println();
        bankAccountExample();
        System.out.println();
        synchronizedVsUnsynchronized();
        System.out.println();
        interviewSummary();
    }

    static void basicSynchronized() {
        System.out.println("--- BASIC SYNCHRONIZED ---");
        
        System.out.println("\n1. SYNCHRONIZED METHOD:");
        System.out.println("   - Locks the entire method");
        System.out.println("   - Only one thread can execute at a time");
        
        class Counter {
            private int count = 0;
            
            public synchronized void increment() {
                count++;
            }
            
            public synchronized int getCount() {
                return count;
            }
        }
        
        Counter counter = new Counter();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        System.out.println("   Final count: " + counter.getCount() + " (expected: 2000)");
        
        System.out.println("\n2. SYNCHRONIZED BLOCK:");
        System.out.println("   - More granular locking");
        System.out.println("   - Lock specific object, not entire method");
        
        class BankAccount {
            private double balance;
            private final Object lock = new Object();
            
            public void deposit(double amount) {
                synchronized (lock) {
                    balance += amount;
                    System.out.println("   Deposited: " + amount + ", Balance: " + balance);
                }
            }
            
            public void withdraw(double amount) {
                synchronized (lock) {
                    if (balance >= amount) {
                        balance -= amount;
                        System.out.println("   Withdrawn: " + amount + ", Balance: " + balance);
                    } else {
                        System.out.println("   Insufficient funds");
                    }
                }
            }
            
            public double getBalance() {
                synchronized (lock) {
                    return balance;
                }
            }
        }
        
        BankAccount account = new BankAccount();
        account.deposit(100);
        account.withdraw(50);
        System.out.println("   Final balance: " + account.getBalance());
        
        System.out.println("\n3. THIS vs CLASS vs OBJECT LOCK:");
        
        class Example {
            synchronized void method1() {
                System.out.println("   Method 1 - locks 'this'");
            }
            
            void method2() {
                synchronized (this) {
                    System.out.println("   Method 2 - locks 'this'");
                }
            }
            
            static synchronized void staticMethod() {
                System.out.println("   Static method - locks Class");
            }
            
            void method3() {
                synchronized (Example.class) {
                    System.out.println("   Method 3 - locks Class");
                }
            }
        }
        
        Example obj = new Example();
        obj.method1();
        obj.method2();
        Example.staticMethod();
    }

    static void bankAccountExample() throws InterruptedException {
        System.out.println("--- BANK ACCOUNT EXAMPLE ---");
        
        System.out.println("\n1. UNSAFE ACCOUNT (Race Condition):");
        
        class UnsafeAccount {
            private double balance = 1000;
            
            public void withdraw(double amount) {
                if (balance >= amount) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {}
                    balance -= amount;
                }
            }
            
            public double getBalance() {
                return balance;
            }
        }
        
        UnsafeAccount unsafe = new UnsafeAccount();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                unsafe.withdraw(10);
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                unsafe.withdraw(10);
            }
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        System.out.println("   Expected: 0, Actual: " + unsafe.getBalance());
        System.out.println("   PROBLEM: Race condition!");
        
        System.out.println("\n2. SAFE ACCOUNT (Synchronized):");
        
        class SafeAccount {
            private double balance = 1000;
            
            public synchronized void withdraw(double amount) {
                if (balance >= amount) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {}
                    balance -= amount;
                }
            }
            
            public synchronized double getBalance() {
                return balance;
            }
        }
        
        SafeAccount safe = new SafeAccount();
        
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                safe.withdraw(10);
            }
        });
        
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                safe.withdraw(10);
            }
        });
        
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        
        System.out.println("   Expected: 0, Actual: " + safe.getBalance());
        System.out.println("   SUCCESS: Thread-safe with synchronized!");
        
        System.out.println("\n3. SYNCHRONIZED BLOCK VERSION:");
        
        class BlockSafeAccount {
            private double balance = 1000;
            private final Object lock = new Object();
            
            public void withdraw(double amount) {
                synchronized (lock) {
                    if (balance >= amount) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {}
                        balance -= amount;
                    }
                }
            }
            
            public double getBalance() {
                synchronized (lock) {
                    return balance;
                }
            }
        }
        
        BlockSafeAccount blockSafe = new BlockSafeAccount();
        
        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                blockSafe.withdraw(10);
            }
        });
        
        Thread t6 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                blockSafe.withdraw(10);
            }
        });
        
        t5.start();
        t6.start();
        t5.join();
        t6.join();
        
        System.out.println("   Expected: 0, Actual: " + blockSafe.getBalance());
        System.out.println("   SUCCESS: Using synchronized block!");
        
        System.out.println("\n4. TRANSFER BETWEEN ACCOUNTS:");
        
        class TransferAccount {
            private double balance;
            
            public TransferAccount(double balance) {
                this.balance = balance;
            }
            
            public synchronized void deposit(double amount) {
                balance += amount;
            }
            
            public synchronized boolean withdraw(double amount) {
                if (balance >= amount) {
                    balance -= amount;
                    return true;
                }
                return false;
            }
            
            public synchronized double getBalance() {
                return balance;
            }
        }
        
        TransferAccount acc1 = new TransferAccount(1000);
        TransferAccount acc2 = new TransferAccount(500);
        
        System.out.println("   Before transfer:");
        System.out.println("   Acc1: " + acc1.getBalance() + ", Acc2: " + acc2.getBalance());
        
        acc1.withdraw(300);
        acc2.deposit(300);
        
        System.out.println("   After transfer: Acc1: " + acc1.getBalance() + ", Acc2: " + acc2.getBalance());
    }

    static void synchronizedVsUnsynchronized() {
        System.out.println("--- SYNCHRONIZED VS UNSYNCHRONIZED ---");
        
        System.out.println("\n1. PERFORMANCE DIFFERENCE:");
        System.out.println("   - Unsynchronized: Faster, not thread-safe");
        System.out.println("   - Synchronized: Slower due to locking, but safe");
        
        System.out.println("\n2. WHEN TO USE SYNCHRONIZED:");
        System.out.println("   - Multiple threads accessing shared data");
        System.out.println("   - Read-modify-write operations");
        System.out.println("   - Compound actions");
        
        System.out.println("\n3. ALTERNATIVES:");
        System.out.println("   - ReentrantLock (more control)");
        System.out.println("   - ConcurrentHashMap (for collections)");
        System.out.println("   - Atomic classes (AtomicInteger, etc.)");
        
        System.out.println("\n4. REENTRANTLOCK EXAMPLE:");
        
        ReentrantLock reLock = new ReentrantLock();
        
        reLock.lock();
        try {
            System.out.println("   Critical section 1");
            reLock.lock();
            try {
                System.out.println("   Critical section 2 (reentrant)");
            } finally {
                reLock.unlock();
            }
        } finally {
            reLock.unlock();
        }
        
        System.out.println("\n5. ATOMIC CLASS EXAMPLE:");
        
        AtomicInteger atomicCount = new AtomicInteger(0);
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                atomicCount.incrementAndGet();
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                atomicCount.incrementAndGet();
            }
        });
        
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}
        
        System.out.println("   AtomicInteger: " + atomicCount.get());
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        System.out.println("\n1. SYNCHRONIZED METHOD vs BLOCK:");
        System.out.println("   Method: locks entire method");
        System.out.println("   Block: locks specific section, more efficient");
        
        System.out.println("\n2. INTRINSIC LOCK:");
        System.out.println("   - Every object has an intrinsic lock");
        System.out.println("   - synchronized(obj) uses that lock");
        System.out.println("   - Method uses 'this' lock");
        System.out.println("   - Static method uses class lock");
        
        System.out.println("\n3. REENTRANT:");
        System.out.println("   - Same thread can acquire lock multiple times");
        System.out.println("   - Nested synchronized calls work");
        
        System.out.println("\n4. WHAT ISN'T THREAD-SAFE:");
        System.out.println("   - Shared mutable state");
        System.out.println("   - Non-atomic operations");
        System.out.println("   - Race conditions");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: What does synchronized keyword do?");
        System.out.println("A: Acquires monitor lock, prevents concurrent access");
        
        System.out.println("\nQ: Can two threads call synchronized methods simultaneously?");
        System.out.println("A: No, if same object; Yes, if different objects");
        
        System.out.println("\nQ: Difference between synchronized method and block?");
        System.out.println("A: Block is more granular, better performance");
        
        System.out.println("\nQ: What is deadlock?");
        System.out.println("A: Two threads waiting for each other's locks");
    }
}