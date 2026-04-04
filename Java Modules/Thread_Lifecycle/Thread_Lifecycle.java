package Thread_Lifecycle;

public class Thread_Lifecycle {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== THREAD LIFECYCLE ===\n");
        
        threadStates();
        System.out.println();
        stateTransitions();
        System.out.println();
        practicalExample();
        System.out.println();
        interviewSummary();
    }

    static void threadStates() {
        System.out.println("--- THREAD STATES (NEW, RUNNABLE, BLOCKED, WAITING, TERMINATED) ---");
        
        System.out.println("\n1. STATE DIAGRAM:");
        System.out.println("                    NEW");
        System.out.println("                      |");
        System.out.println("                      v");
        System.out.println("   +---------> RUNNABLE <------------+");
        System.out.println("   |              |                  |");
        System.out.println("   |              v                  |");
        System.out.println("   |         BLOCKED/WAITING         |");
        System.out.println("   |              |                  |");
        System.out.println("   |              v                  |");
        System.out.println("   +--------- TERMINATED <-----------+");
        
        System.out.println("\n2. ALL 6 STATES (java.lang.Thread.State):");
        System.out.println("   NEW - Thread created, not started");
        System.out.println("   RUNNABLE - Running or ready to run");
        System.out.println("   BLOCKED - Waiting for monitor lock");
        System.out.println("   WAITING - Waiting indefinitely (wait(), join(), park())");
        System.out.println("   TIMED_WAITING - Waiting with timeout (sleep, wait with timeout)");
        System.out.println("   TERMINATED - Finished execution");
        
        System.out.println("\n3. GETTING STATE:");
        
        Thread thread = new Thread(() -> {
            System.out.println("   Running...");
        });
        
        System.out.println("   NEW state: " + thread.getState());
        
        thread.start();
        
        Thread.sleep(50);
        System.out.println("   RUNNABLE state: " + thread.getState());
        
        thread.join();
        System.out.println("   TERMINATED state: " + thread.getState());
    }

    static void stateTransitions() {
        System.out.println("--- STATE TRANSITIONS ---");
        
        System.out.println("\n1. NEW -> RUNNABLE:");
        System.out.println("   Thread thread = new Thread(task);");
        System.out.println("   thread.start();");
        
        System.out.println("\n2. RUNNABLE -> BLOCKED:");
        System.out.println("   - Trying to enter synchronized block/method");
        System.out.println("   - Lock held by another thread");
        
        System.out.println("\n3. RUNNABLE -> WAITING:");
        System.out.println("   - thread.wait()");
        System.out.println("   - thread.join() (no timeout)");
        System.out.println("   - LockSupport.park()");
        
        System.out.println("\n4. RUNNABLE -> TIMED_WAITING:");
        System.out.println("   - thread.sleep(millis)");
        System.out.println("   - thread.wait(millis)");
        System.out.println("   - thread.join(millis)");
        System.out.println("   - LockSupport.parkNanos()");
        
        System.out.println("\n5. WAITING/TIMED_WAITING -> RUNNABLE:");
        System.out.println("   - notify() called");
        System.out.println("   - notifyAll() called");
        System.out.println("   - timeout expires");
        System.out.println("   - Lock unparked");
        
        System.out.println("\n6. RUNNABLE -> TERMINATED:");
        System.out.println("   - run() method completes normally");
        System.out.println("   - run() method throws uncaught exception");
        
        System.out.println("\n7. EXAMPLES:");
        
        System.out.println("   Sleep (TIMED_WAITING):");
        Thread sleepThread = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        });
        sleepThread.start();
        Thread.sleep(10);
        System.out.println("   State during sleep: " + sleepThread.getState());
        sleepThread.join();
        
        System.out.println("\n   Join (WAITING):");
        Thread mainThread = Thread.currentThread();
        Thread joinThread = new Thread(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        });
        joinThread.start();
        joinThread.join();
        System.out.println("   Main thread waited for joinThread");
        
        System.out.println("\n   Synchronized (BLOCKED):");
        Object lock = new Object();
        
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("   T1 holding lock");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
        });
        
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("   T2 holding lock");
            }
        });
        
        t1.start();
        Thread.sleep(10);
        t2.start();
        Thread.sleep(10);
        System.out.println("   T2 blocked waiting: " + t2.getState());
        
        t1.join();
        t2.join();
    }

    static void practicalExample() throws InterruptedException {
        System.out.println("--- PRACTICAL EXAMPLE ---");
        
        System.out.println("\n1. CREATING THREADS:");
        
        System.out.println("\n   Extending Thread:");
        class MyThread extends Thread {
            @Override
            public void run() {
                System.out.println("   MyThread running");
            }
        }
        
        Thread t1 = new MyThread();
        t1.start();
        
        System.out.println("\n   Implementing Runnable:");
        Runnable task = () -> System.out.println("   Runnable task running");
        Thread t2 = new Thread(task);
        t2.start();
        
        System.out.println("\n   Using ExecutorService:");
        java.util.concurrent.ExecutorService executor = 
            java.util.concurrent.Executors.newFixedThreadPool(2);
        
        executor.submit(() -> System.out.println("   Task 1"));
        executor.submit(() -> System.out.println("   Task 2"));
        
        executor.shutdown();
        executor.awaitTermination(1, java.util.concurrent.TimeUnit.SECONDS);
        
        System.out.println("\n2. CONTROLLING THREADS:");
        
        Thread.sleep(10);
        
        System.out.println("\n   isAlive():");
        Thread aliveThread = new Thread(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        });
        aliveThread.start();
        System.out.println("   Before join - isAlive: " + aliveThread.isAlive());
        aliveThread.join();
        System.out.println("   After join - isAlive: " + aliveThread.isAlive());
        
        System.out.println("\n   Thread priorities:");
        Thread minPriority = new Thread(() -> {});
        Thread maxPriority = new Thread(() -> {});
        minPriority.setPriority(Thread.MIN_PRIORITY);
        maxPriority.setPriority(Thread.MAX_PRIORITY);
        System.out.println("   Min priority: " + minPriority.getPriority());
        System.out.println("   Max priority: " + maxPriority.getPriority());
        
        System.out.println("\n   Daemon thread:");
        Thread daemon = new Thread(() -> {
            while (true) {}
        });
        daemon.setDaemon(true);
        daemon.start();
        System.out.println("   Daemon thread started");
        
        System.out.println("\n3. INTERRUPTING THREADS:");
        
        Thread interruptThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("   Interrupted!");
                    break;
                }
            }
        });
        
        interruptThread.start();
        Thread.sleep(100);
        interruptThread.interrupt();
        interruptThread.join();
        
        System.out.println("   Thread interrupted successfully");
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        String[][] table = {
            {"State", "Description", "Transition To"},
            {"NEW", "Created, not started", "RUNNABLE"},
            {"RUNNABLE", "Running or ready", "BLOCKED, WAITING, TERMINATED"},
            {"BLOCKED", "Waiting for lock", "RUNNABLE"},
            {"WAITING", "Waiting indefinitely", "RUNNABLE"},
            {"TIMED_WAITING", "Waiting with timeout", "RUNNABLE"},
            {"TERMINATED", "Completed", "N/A (final)"}
        };
        
        System.out.println();
        for (String[] row : table) {
            System.out.printf("%-15s | %-25s | %s%n", row[0], row[1], row[2]);
        }
        
        System.out.println("\n--- KEY METHODS AND STATES ---");
        System.out.println("   sleep()      -> TIMED_WAITING");
        System.out.println("   wait()       -> WAITING");
        System.out.println("   join()       -> WAITING (or TIMED_WAITING)");
        System.out.println("   synchronized -> BLOCKED");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: Can a dead thread be restarted?");
        System.out.println("A: No, throws IllegalThreadStateException");
        
        System.out.println("\nQ: Difference between wait() and sleep()?");
        System.out.println("A: wait() releases lock, sleep() doesn't");
        
        System.out.println("\nQ: What happens when you call thread.start() twice?");
        System.out.println("A: IllegalThreadStateException");
        
        System.out.println("\nQ: Is thread.start() synchronous?");
        System.out.println("A: No, start() returns immediately, run() executes asynchronously");
    }
}