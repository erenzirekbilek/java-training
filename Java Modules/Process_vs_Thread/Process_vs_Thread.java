package Process_vs_Thread;

import java.io.*;

public class Process_vs_Thread {

    public static void main(String[] args) throws Exception {
        System.out.println("=== PROCESS VS THREAD ===\n");
        
        processDefinition();
        System.out.println();
        threadDefinition();
        System.out.println();
        comparison();
        System.out.println();
        memoryAndCost();
        System.out.println();
        interviewSummary();
    }

    static void processDefinition() {
        System.out.println("--- PROCESS ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Independent execution unit");
        System.out.println("   - Has its own memory space");
        System.out.println("   - OS-level isolation");
        System.out.println("   - Multiple processes can run concurrently");
        
        System.out.println("\n2. PROCESS CHARACTERISTICS:");
        System.out.println("   - Each process has: PID, Memory, Resources");
        System.out.println("   - Communication via IPC (pipes, sockets, messages)");
        System.out.println("   - Heavyweight (expensive to create)");
        System.out.println("   - Isolated - one process can't access another's memory");
        
        System.out.println("\n3. PROCESS TYPES:");
        System.out.println("   - Parent Process: Creates child processes");
        System.out.println("   - Child Process: Created by parent");
        System.out.println("   - Daemon Process: Background service");
        
        System.out.println("\n4. JAVA PROCESS EXAMPLE:");
        
        System.out.println("   Current process PID: " + ProcessHandle.current().pid());
        System.out.println("   Process info: " + ProcessHandle.current().info());
        
        System.out.println("\n   Starting new process:");
        
        ProcessBuilder pb = new ProcessBuilder("echo", "Hello from new process");
        Process p = pb.start();
        
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(p.getInputStream()));
        
        String output = reader.readLine();
        System.out.println("   Process output: " + output);
        
        int exitCode = p.waitFor();
        System.out.println("   Exit code: " + exitCode);
        
        System.out.println("\n5. PROCESS COMMUNICATION:");
        System.out.println("   - Sockets (network)");
        System.out.println("   - Pipes (anonymous/named)");
        System.out.println("   - Message queues");
        System.out.println("   - Shared memory (via files)");
    }

    static void threadDefinition() {
        System.out.println("--- THREAD ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Lightweight execution unit within a process");
        System.out.println("   - Shares process resources (memory, files)");
        System.out.println("   - Multiple threads in one process");
        System.out.println("   - Java: java.lang.Thread");
        
        System.out.println("\n2. THREAD CHARACTERISTICS:");
        System.out.println("   - Each thread has: Stack, Registers, Program Counter");
        System.out.println("   - Shared: Heap, Code, Data, Global variables");
        System.out.println("   - Lightweight (cheap to create)");
        System.out.println("   - Communication: Direct (shared memory)");
        
        System.out.println("\n3. JAVA THREAD EXAMPLE:");
        
        Thread thread1 = new Thread(() -> {
            System.out.println("   Thread 1 running");
        }, "MyThread-1");
        
        Thread thread2 = new Thread(() -> {
            System.out.println("   Thread 2 running");
        }, "MyThread-2");
        
        thread1.start();
        thread2.start();
        
        System.out.println("   Thread 1 name: " + thread1.getName());
        System.out.println("   Thread 2 name: " + thread2.getName());
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {}
        
        System.out.println("\n4. THREAD TYPES:");
        System.out.println("   - User Thread: Regular application threads");
        System.out.println("   - Daemon Thread: Background (GC, finalizer)");
        System.out.println("   - Main Thread: First thread in application");
        
        System.out.println("\n5. THREAD COMMUNICATION:");
        System.out.println("   - Shared variables");
        System.out.println("   - wait(), notify(), notifyAll()");
        System.out.println("   - Locks and synchronized");
    }

    static void comparison() {
        System.out.println("--- COMPARISON ---");
        
        String[][] table = {
            {"Aspect", "Process", "Thread"},
            {"Definition", "Execution unit", "Lightweight sub-unit"},
            {"Independence", "Independent", "Part of process"},
            {"Memory", "Separate memory space", "Shares process memory"},
            {"Communication", "IPC (sockets, pipes)", "Direct (shared memory)"},
            {"Creation Cost", "High (heavyweight)", "Low (lightweight)"),
            {"Isolation", "Strong (can't access others)", "Weak (shares memory)"},
            {"Overhead", "More context switching", "Less context switching"},
            {"Failure Impact", "Process crash only", "Entire process affected"},
            {"Java Class", "ProcessBuilder", "Thread, Runnable"},
        };
        
        System.out.println();
        for (String[] row : table) {
            System.out.printf("%-20s | %-20s | %s%n", row[0], row[1], row[2]);
        }
        
        System.out.println("\n--- DIAGRAM ---");
        
        System.out.println("\n   PROCESSES (OS Level):");
        System.out.println("   ┌─────────┐     ┌─────────┐     ┌─────────┐");
        System.out.println("   │Process 1│     │Process 2│     │Process 3│");
        System.out.println("   │ ┌─────┐ │     │ ┌─────┐ │     │ ┌─────┐ │");
        System.out.println("   │ │Thread│ │     │ │Thread│ │     │ │Thread│ │");
        System.out.println("   │ └─────┘ │     │ └─────┘ │     │ └─────┘ │");
        System.out.println("   └─────────┘     └─────────┘     └─────────┘");
        
        System.out.println("\n   THREADS (Within Process):");
        System.out.println("   ┌──────────────────────────────────┐");
        System.out.println("   │         PROCESS                  │");
        System.out.println("   │  ┌──────┐ ┌──────┐ ┌──────┐      │");
        System.out.println("   │  │Thread│ │Thread│ │Thread│      │");
        System.out.println("   │  │  1   │ │  2   │ │  3   │      │");
        System.out.println("   │  └──────┘ └──────┘ └──────┘      │");
        System.out.println("   │  Shared Heap & Resources        │");
        System.out.println("   └──────────────────────────────────┘");
    }

    static void memoryAndCost() throws InterruptedException {
        System.out.println("--- MEMORY AND COST DIFFERENCES ---");
        
        System.out.println("\n1. MEMORY SEPARATION:");
        
        System.out.println("\n   Process:");
        System.out.println("   - Separate heap");
        System.out.println("   - Separate stack");
        System.out.println("   - Separate code segment");
        System.out.println("   - Separate global data");
        System.out.println("   - Total: ~MBs per process");
        
        System.out.println("\n   Thread:");
        System.out.println("   - Only own stack");
        System.out.println("   - Shares: heap, code, data, files");
        System.out.println("   - Total: ~KB per thread");
        
        System.out.println("\n2. CREATION TIME COMPARISON:");
        
        long start = System.nanoTime();
        
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {}).start();
        }
        
        long threadTime = System.nanoTime() - start;
        System.out.println("   Creating 1000 threads: " + threadTime / 1_000_000 + "ms");
        
        Thread.sleep(100);
        
        start = System.nanoTime();
        
        for (int i = 0; i < 10; i++) {
            ProcessBuilder pb = new ProcessBuilder("echo", "test");
            pb.start();
        }
        
        long processTime = System.nanoTime() - start;
        System.out.println("   Creating 10 processes: " + processTime / 1_000_000 + "ms");
        
        System.out.println("   Threads are " + (processTime / 10) / (threadTime / 1000) + "x faster");
        
        System.out.println("\n3. RESOURCE USAGE:");
        
        System.out.println("   Thread: ~1MB stack + minimal overhead");
        System.out.println("   Process: ~10MB+ memory + file descriptors");
        
        System.out.println("\n4. CONTEXT SWITCHING:");
        
        System.out.println("   Process switch:");
        System.out.println("   - Save/restore full CPU state");
        System.out.println("   - Switch memory page tables");
        System.out.println("   - Update memory management structures");
        
        System.out.println("\n   Thread switch:");
        System.out.println("   - Save/restore CPU registers");
        System.out.println("   - Switch stack pointer");
        System.out.println("   - Much faster!");
        
        System.out.println("\n5. COMMUNICATION COST:");
        
        System.out.println("   Process: Serialization + IPC overhead");
        System.out.println("   Thread: Direct memory access");
        
        System.out.println("\n   Example: Shared counter (threads vs processes)");
        
        int[] sharedCounter = {0};
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) sharedCounter[0]++;
        });
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) sharedCounter[0]++;
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        System.out.println("   Shared counter (threads): " + sharedCounter[0]);
        
        System.out.println("\n   For processes, you'd need shared memory or IPC");
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        System.out.println("\n1. KEY DIFFERENCES:");
        System.out.println("   Process: Heavyweight, isolated memory, IPC needed");
        System.out.println("   Thread: Lightweight, shared memory, direct communication");
        
        System.out.println("\n2. WHEN TO USE PROCESS:");
        System.out.println("   - Need isolation (security)");
        System.out.println("   - Different privileges");
        System.out.println("   - Fault tolerance (one crash doesn't affect others)");
        
        System.out.println("\n3. WHEN TO USE THREAD:");
        System.out.println("   - Share data easily");
        System.out.println("   - Communication needed");
        System.out.println("   - Lightweight operations");
        
        System.out.println("\n4. REAL-WORLD:");
        System.out.println("   - Browser: Process per tab (isolation)");
        System.out.println("   - Web server: Threads per request (speed)");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: Can threads in same process share memory?");
        System.out.println("A: Yes, share heap and static variables");
        
        System.out.println("\nQ: Can processes share memory?");
        System.out.println("A: Yes, via shared memory segments");
        
        System.out.println("\nQ: Which is more expensive to create?");
        System.out.println("A: Process is much more expensive");
        
        System.out.println("\nQ: If one thread crashes, what happens?");
        System.out.println("A: Entire process crashes (all threads die)");
        
        System.out.println("\nQ: If one process crashes, what happens?");
        System.out.println("A: Only that process dies, others continue");
    }
}