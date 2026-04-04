package Multithreading_Basics;

import java.util.concurrent.*;

public class Multithreading_Basics {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== MULTITHREADING BASICS ===\n");
        
        whyMultithreading();
        System.out.println();
        dataProcessingExample();
        System.out.println();
        benefitsVsChallenges();
        System.out.println();
        interviewSummary();
    }

    static void whyMultithreading() {
        System.out.println("--- WHY MULTITHREADING ---");
        
        System.out.println("\n1. PROBLEM: SINGLE THREADED LIMITATION");
        System.out.println("   - CPU sits idle while waiting for I/O");
        System.out.println("   - Only one task at a time");
        System.out.println("   - Underutilizes multi-core CPUs");
        
        System.out.println("\n2. SOLUTION: MULTITHREADING");
        System.out.println("   - Multiple tasks concurrently");
        System.out.println("   - Better CPU utilization");
        System.out.println("   - Responsive applications");
        
        System.out.println("\n3. REAL-WORLD USES:");
        System.out.println("   - Web servers (handle multiple requests)");
        System.out.println("   - GUI applications (keep UI responsive)");
        System.out.println("   - Batch processing (parallel data handling)");
        System.out.println("   - Games (physics, rendering, AI in parallel)");
        
        System.out.println("\n4. CPU VS CORES:");
        System.out.println("   - Single core: One thread at a time (time-sliced)");
        System.out.println("   - Multi-core: True parallelism");
        System.out.println("   - Java runs on both, abstracts the difference");
        
        System.out.println("\n5. EXAMPLE: SEQUENTIAL VS PARALLEL:");
        
        System.out.println("\n   Sequential:");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            simulateWork(100);
        }
        long seqTime = System.currentTimeMillis() - start;
        System.out.println("   Time: " + seqTime + "ms");
        
        System.out.println("\n   Parallel (3 threads):");
        start = System.currentTimeMillis();
        
        Thread t1 = new Thread(() -> simulateWork(100));
        Thread t2 = new Thread(() -> simulateWork(100));
        Thread t3 = new Thread(() -> simulateWork(100));
        
        t1.start();
        t2.start();
        t3.start();
        
        t1.join();
        t2.join();
        t3.join();
        
        long parTime = System.currentTimeMillis() - start;
        System.out.println("   Time: " + parTime + "ms");
        
        System.out.println("   Speedup: " + (double)seqTime/parTime + "x");
    }

    static void simulateWork(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {}
    }

    static void dataProcessingExample() throws InterruptedException {
        System.out.println("--- DATA PROCESSING SPEEDUP ---");
        
        System.out.println("\n1. LARGE DATA OPERATIONS:");
        System.out.println("   - Processing 1 million records");
        System.out.println("   - Sequential: ~100 seconds");
        System.out.println("   - With 10 threads: ~10 seconds");
        
        System.out.println("\n2. EXAMPLE: BATCH SAVE TO DATABASE:");
        
        class DatabaseService {
            public void saveRecord(int id) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {}
            }
            
            public void saveBatchSequential(int[] records) {
                long start = System.currentTimeMillis();
                for (int id : records) {
                    saveRecord(id);
                }
                System.out.println("   Sequential: " + (System.currentTimeMillis() - start) + "ms");
            }
            
            public void saveBatchParallel(int[] records, int threads) {
                long start = System.currentTimeMillis();
                
                ExecutorService executor = Executors.newFixedThreadPool(threads);
                for (int id : records) {
                    executor.submit(() -> saveRecord(id));
                }
                
                executor.shutdown();
                executor.awaitTermination(10, TimeUnit.SECONDS);
                
                System.out.println("   Parallel (" + threads + " threads): " 
                    + (System.currentTimeMillis() - start) + "ms");
            }
        }
        
        DatabaseService db = new DatabaseService();
        
        int[] data = new int[100];
        for (int i = 0; i < 100; i++) data[i] = i;
        
        System.out.println("   Saving 100 records:");
        db.saveBatchSequential(data);
        
        db.saveBatchParallel(data, 4);
        db.saveBatchParallel(data, 10);
        
        System.out.println("\n3. EXAMPLE: DELETING LARGE DATASET:");
        
        System.out.println("   Sequential delete: O(n)");
        System.out.println("   Parallel delete: O(n/threads)");
        System.out.println("   But be careful of deadlock, race conditions!");
        
        System.out.println("\n4. THREAD POOL SIZING:");
        
        System.out.println("   CPU-bound tasks: threads = CPU cores");
        System.out.println("   I/O-bound tasks: threads > CPU cores");
        System.out.println("   Formula: threads = cores * (1 + wait_time / compute_time)");
        
        System.out.println("\n   Common pool types:");
        System.out.println("   - FixedThreadPool: Fixed number");
        System.out.println("   - CachedThreadPool: Dynamic");
        System.out.println("   - SingleThreadExecutor: One thread");
        System.out.println("   - WorkStealingPool: Fork/Join");
    }

    static void benefitsVsChallenges() {
        System.out.println("--- BENEFITS VS CHALLENGES ---");
        
        System.out.println("\n1. BENEFITS:");
        System.out.println("   ✓ Better CPU utilization");
        System.out.println("   ✓ Faster task completion");
        System.out.println("   ✓ Responsive applications");
        System.out.println("   ✓ Better resource sharing");
        
        System.out.println("\n2. CHALLENGES:");
        System.out.println("   ✗ Race conditions");
        System.out.println("   ✗ Deadlocks");
        System.out.println("   ✗ Complexity in debugging");
        System.out.println("   ✗ Context switching overhead");
        
        System.out.println("\n3. COMMON ISSUES:");
        
        System.out.println("\n   Race Condition:");
        int counter = 0;
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter++;
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter++;
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        System.out.println("   Counter: " + counter + " (expected 2000)");
        System.out.println("   Lost updates due to race condition!");
        
        System.out.println("\n   Deadlock Example:");
        
        Object lock1 = new Object();
        Object lock2 = new Object();
        
        Thread t3 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("   T3: Acquired lock1");
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                synchronized (lock2) {
                    System.out.println("   T3: Acquired lock2");
                }
            }
        });
        
        Thread t4 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("   T4: Acquired lock2");
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                synchronized (lock1) {
                    System.out.println("   T4: Acquired lock1");
                }
            }
        });
        
        t3.start();
        t4.start();
        
        t3.join(500);
        t4.join(500);
        
        if (t3.isAlive() || t4.isAlive()) {
            t3.interrupt();
            t4.interrupt();
            System.out.println("   Deadlock detected!");
        }
        
        System.out.println("\n4. SOLUTIONS:");
        System.out.println("   - Use synchronization (synchronized, Lock)");
        System.out.println("   - Avoid nested locks");
        System.out.println("   - Use thread-safe collections");
        System.out.println("   - Follow consistent lock ordering");
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        System.out.println("\n1. WHY MULTITHREADING?");
        System.out.println("   - Better CPU utilization");
        System.out.println("   - Concurrent execution");
        System.out.println("   - Better application responsiveness");
        
        System.out.println("\n2. WHEN TO USE?");
        System.out.println("   - I/O operations (file, network, DB)");
        System.out.println("   - Background tasks");
        System.out.println("   - Batch processing");
        System.out.println("   - Multiple independent tasks");
        
        System.out.println("\n3. HOW TO SPEED UP?");
        System.out.println("   - Split data into chunks");
        System.out.println("   - Process in parallel");
        System.out.println("   - Use thread pools");
        System.out.println("   - Combine results");
        
        System.out.println("\n4. EXAMPLE: 1 MILLION RECORDS SAVE:");
        
        System.out.println("   // Divide into 10 chunks");
        System.out.println("   // Each thread saves 100,000 records");
        System.out.println("   // 10x faster!");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: How does multithreading speed up I/O operations?");
        System.out.println("A: While one thread waits, others can work");
        
        System.out.println("\nQ: What is the maximum speedup?");
        System.out.println("A: Number of CPU cores (theoretically)");
        
        System.out.println("\nQ: Can too many threads slow things down?");
        System.out.println("A: Yes, context switching overhead");
    }
}