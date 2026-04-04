package Sync_Read_Write;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class Sync_Read_Write {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== SYNCHRONIZATION: READ VS WRITE ===\n");
        
        synchronizationBenefit();
        System.out.println();
        concurrentHashMapExample();
        System.out.println();
        readWriteLock();
        System.out.println();
        interviewSummary();
    }

    static void synchronizationBenefit() {
        System.out.println("--- WHEN IS SYNCHRONIZATION USEFUL? ---");
        
        System.out.println("\n1. SYNCHRONIZATION NEEDED FOR:");
        System.out.println("   - Write operations (critical)");
        System.out.println("   - Read-modify-write operations");
        System.out.println("   - Compound actions");
        
        System.out.println("\n2. READ vs WRITE IMPORTANCE:");
        
        System.out.println("\n   WRITE operations need synchronization:");
        Counter unsafeCounter = new Counter();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                unsafeCounter.increment();
            }
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                unsafeCounter.increment();
            }
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        System.out.println("   Expected: 20000, Actual: " + unsafeCounter.get());
        System.out.println("   LOST UPDATES due to race condition!");
        
        System.out.println("\n   With synchronization (AtomicInteger):");
        
        AtomicInteger safeCounter = new AtomicInteger(0);
        
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                safeCounter.incrementAndGet();
            }
        });
        
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                safeCounter.incrementAndGet();
            }
        });
        
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        
        System.out.println("   Expected: 20000, Actual: " + safeCounter.get());
        System.out.println("   CORRECT with atomic operations!");
        
        System.out.println("\n3. READ operations (Often don't need sync):");
        
        System.out.println("   - Simple reads are usually safe");
        System.out.println("   - If data rarely changes, reads can be unsynchronized");
        System.out.println("   - Stale data acceptable in some cases");
        
        System.out.println("\n4. WHEN READ SYNC NEEDED:");
        System.out.println("   - Read while another thread writes");
        System.out.println("   - Complex data structures");
        System.out.println("   - Consistent snapshot needed");
    }

    static void concurrentHashMapExample() {
        System.out.println("--- CONCURRENTHASHMAP EXAMPLE ---");
        
        System.out.println("\n1. WHY CHM?");
        System.out.println("   - Thread-safe without locking entire map");
        System.out.println("   - Allows concurrent reads");
        System.out.println("   - Bucket-level locking (since Java 8)");
        
        ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<>();
        
        System.out.println("\n2. OPERATIONS:");
        
        System.out.println("   putIfAbsent: " + chm.putIfAbsent("key", 1));
        System.out.println("   After putIfAbsent: " + chm.get("key"));
        
        System.out.println("   compute: " + chm.compute("key", (k, v) -> v + 10));
        System.out.println("   After compute: " + chm.get("key"));
        
        System.out.println("\n3. CONCURRENT ACCESS:");
        
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        for (int i = 0; i < 100; i++) {
            final int id = i;
            executor.submit(() -> {
                chm.put("counter", id);
            });
        }
        
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                chm.get("counter");
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        
        System.out.println("   Final value: " + chm.get("counter"));
        System.out.println("   (Last writer wins in non-atomic operations)");
        
        System.out.println("\n4. ATOMIC OPERATIONS (Safe):");
        
        ConcurrentHashMap<String, AtomicInteger> atomicMap = new ConcurrentHashMap<>();
        atomicMap.put("counter", new AtomicInteger(0));
        
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                atomicMap.get("counter").incrementAndGet();
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        
        System.out.println("   Atomic counter: " + atomicMap.get("counter").get());
        System.out.println("   CORRECT with atomic wrapper!");
        
        System.out.println("\n5. WHEN TO USE CHM:");
        
        System.out.println("   ✓ Many readers");
        System.out.println("   ✓ Some writers");
        System.out.println("   ✓ Need thread-safe collection");
        
        System.out.println("\n   ✗ Need to lock entire map");
        System.out.println("   ✗ Need ordering (use Hashtable)");
    }

    static void readWriteLock() {
        System.out.println("--- READ-WRITE LOCK PATTERN ---");
        
        System.out.println("\n1. PROBLEM:");
        System.out.println("   - Only one thread can access at a time");
        System.out.println("   - Even multiple readers block each other");
        
        System.out.println("\n2. SOLUTION: ReadWriteLock:");
        System.out.println("   - Multiple readers can access simultaneously");
        System.out.println("   - Writers get exclusive access");
        System.out.println("   - Readers wait if writer active");
        System.out.println("   - Writers wait if any reader active");
        
        System.out.println("\n3. IMPLEMENTATION:");
        
        class ReadWriteCache {
            private final Map<String, String> cache = new HashMap<>();
            private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
            
            public String read(String key) {
                rwLock.readLock().lock();
                try {
                    System.out.println("   Reading: " + key + " = " + cache.get(key));
                    return cache.get(key);
                } finally {
                    rwLock.readLock().unlock();
                }
            }
            
            public void write(String key, String value) {
                rwLock.writeLock().lock();
                try {
                    System.out.println("   Writing: " + key + " = " + value);
                    cache.put(key, value);
                } finally {
                    rwLock.writeLock().unlock();
                }
            }
        }
        
        ReadWriteCache cache = new ReadWriteCache();
        
        cache.write("a", "value-a");
        cache.read("a");
        
        System.out.println("\n4. MULTIPLE READERS:");
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        for (int i = 0; i < 3; i++) {
            final int id = i;
            executor.submit(() -> cache.read("a"));
        }
        
        executor.submit(() -> cache.write("a", "new-value"));
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        
        System.out.println("\n5. READ-WRITE LOCK VS SYNCHRONIZED:");
        
        String[][] table = {
            {"Feature", "synchronized", "ReadWriteLock"},
            {"Readers", "1 at a time", "Multiple concurrent"},
            {"Writers", "1 at a time", "1 at a time (exclusive)"},
            {"Performance", "Simple but slow", "Better for read-heavy"},
            {"Use Case", "Balanced read/write", "Read-heavy workloads"}
        };
        
        System.out.println();
        for (String[] row : table) {
            System.out.printf("%-15s | %-25s | %s%n", row[0], row[1], row[2]);
        }
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        System.out.println("\n1. SYNCHRONIZATION BENEFITS:");
        System.out.println("   - Ensures data consistency");
        System.out.println("   - Prevents race conditions");
        System.out.println("   - Makes compound actions atomic");
        
        System.out.println("\n2. READ vs WRITE:");
        System.out.println("   - Write operations MUST be synchronized");
        System.out.println("   - Read-only operations often don't need sync");
        System.out.println("   - But be careful of partial writes!");
        
        System.out.println("\n3. CONCURRENTHASHMAP:");
        System.out.println("   - Bucket-level locking (not whole map)");
        System.out.println("   - Multiple readers allowed");
        System.out.println("   - Writers don't block readers");
        System.out.println("   - No null keys/values (thread-safety)");
        
        System.out.println("\n4. CHOOSING THE RIGHT TOOL:");
        
        System.out.println("   - HashMap (not thread-safe)");
        System.out.println("   - Hashtable (fully synchronized, slower)");
        System.out.println("   - ConcurrentHashMap (bucket-level lock, fast)");
        System.out.println("   - Collections.synchronizedMap (method-level lock)");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: When is synchronization needed?");
        System.out.println("A: When multiple threads access shared mutable data");
        
        System.out.println("\nQ: Why use ConcurrentHashMap over Hashtable?");
        System.out.println("A: Better performance - bucket-level locking vs table-level");
        
        System.out.println("\nQ: Can multiple threads read simultaneously?");
        System.out.println("A: Yes, with ReadWriteLock or ConcurrentHashMap");
        
        System.out.println("\nQ: What happens if read without synchronization?");
        System.out.println("A: Might see partial/torn reads, stale data");
    }
    
    private static class Counter {
        private int count = 0;
        public void increment() { count++; }
        public int get() { return count; }
    }
}

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;