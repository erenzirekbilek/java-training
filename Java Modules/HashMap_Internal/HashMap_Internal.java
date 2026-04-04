package HashMap_Internal;

import java.util.*;
import java.util.concurrent.*;

public class HashMap_Internal {

    public static void main(String[] args) {
        System.out.println("=== HASHMAP & CONCURRENTHASHMAP INTERNALS ===\n");
        
        hashMapInternalWorking();
        System.out.println();
        concurrentHashMapInternal();
        System.out.println();
        whyNoNullInCHM();
    }

    static void hashMapInternalWorking() {
        System.out.println("--- HASHMAP INTERNAL WORKING ---");
        System.out.println("\n1. STRUCTURE:");
        System.out.println("   - Uses array of buckets (Node[] table)");
        System.out.println("   - Each bucket contains a linked list or red-black tree");
        System.out.println("   - Each Node stores: key, value, hash, next");
        
        System.out.println("\n2. PUT OPERATION:");
        System.out.println("   Step 1: Calculate hash of key using hashCode()");
        System.out.println("   Step 2: Calculate index = hash & (table.length - 1)");
        System.out.println("   Step 3: If bucket empty, create new Node");
        System.out.println("   Step 4: If bucket has nodes, traverse链表");
        System.out.println("           - If key exists, update value");
        System.out.println("           - If not, add new Node at end");
        System.out.println("   Step 5: If size > threshold, resize (double capacity)");
        
        System.out.println("\n3. GET OPERATION:");
        System.out.println("   - Calculate hash and index");
        System.out.println("   - Traverse bucket's linked list");
        System.out.println("   - Compare hash and key");
        System.out.println("   - Return value if found, null otherwise");
        
        System.out.println("\n4. RESIZE MECHANISM:");
        System.out.println("   - When size > capacity * loadFactor (default 0.75)");
        System.out.println("   - Create new array 2x size");
        System.out.println("   - Rehash all elements to new buckets");
        
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Apple", 1);
        map.put("Banana", 2);
        map.put("Cherry", 3);
        
        System.out.println("\n5. DEMO:");
        System.out.println("   map.put(\"Apple\", 1)");
        System.out.println("   map.put(\"Banana\", 2)");
        System.out.println("   map.get(\"Apple\") = " + map.get("Apple"));
        System.out.println("   map.get(\"Banana\") = " + map.get("Banana"));
    }

    static void concurrentHashMapInternal() {
        System.out.println("--- CONCURRENTHASHMAP INTERNAL WORKING ---");
        
        System.out.println("\n1. KEY DIFFERENCES FROM HASHMAP:");
        System.out.println("   - Thread-safe (synchronized)");
        System.out.println("   - No locking on entire map");
        System.out.println("   - Uses segment-based locking");
        System.out.println("   - Allows concurrent read/write");
        
        System.out.println("\n2. STRUCTURE (Java 8+):");
        System.out.println("   - Uses Node[] array (similar to HashMap)");
        System.out.println("   - Each bucket has synchronized blocks");
        System.out.println("   - Uses CAS (Compare-And-Swap) for updates");
        System.out.println("   - Tree bins for high-collision buckets");
        
        System.out.println("\n3. LOCKING MECHANISM:");
        System.out.println("   - Before Java 8: Segments (default 16)");
        System.out.println("   - Java 8+: Bucket-level locking");
        System.out.println("   - Uses synchronized on first node of bucket");
        
        System.out.println("\n4. CONCURRENT OPERATIONS:");
        System.out.println("   - compute(), computeIfAbsent(), merge()");
        System.out.println("   - These are atomic operations");
        
        ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<>();
        chm.put("One", 1);
        chm.put("Two", 2);
        chm.putIfAbsent("Three", 3);
        
        System.out.println("\n5. DEMO:");
        System.out.println("   chm.put(\"One\", 1)");
        System.out.println("   chm.putIfAbsent(\"Three\", 3) = " + chm.putIfAbsent("Three", 33));
        System.out.println("   chm.get(\"Three\") = " + chm.get("Three"));
        
        System.out.println("\n6. THREAD-SAFE OPERATIONS:");
        System.out.println("   - chm.compute(\"One\", (k,v) -> v + 10) = " + chm.compute("One", (k, v) -> v + 10));
        System.out.println("   - chm.get(\"One\") = " + chm.get("One"));
    }

    static void whyNoNullInCHM() {
        System.out.println("--- WHY CONCURRENTHASHMAP DOES NOT ALLOW NULL ---");
        
        System.out.println("\n1. DESIGN REASON:");
        System.out.println("   - Null values create ambiguity");
        System.out.println("   - Cannot distinguish between:");
        System.out.println("     a) Key not present");
        System.out.println("     b) Key present with null value");
        
        System.out.println("\n2. CONCURRENCY ISSUE:");
        System.out.println("   HashMap allows null because it's not thread-safe");
        System.out.println("   CHM needs to be thread-safe: get(key) returns null");
        System.out.println("   Could mean key doesn't exist OR value is null");
        
        System.out.println("\n3. ATOMIC OPERATIONS:");
        System.out.println("   - putIfAbsent(), compute() need clear semantics");
        System.out.println("   - Cannot handle null ambiguity in atomic ops");
        
        System.out.println("\n4. RACE CONDITION EXAMPLE:");
        System.out.println("   Thread A: map.get(key) -> null?");
        System.out.println("   Thread B: map.put(key, null)");
        System.out.println("   Result: Ambiguous - key missing or null value?");
        
        System.out.println("\n5. HASHMAP vs CHM:");
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("test", null);
        System.out.println("   HashMap: put(null, null) = " + hm.put(null, null));
        System.out.println("   HashMap: get(null) = " + hm.get(null));
        
        ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<>();
        try {
            chm.put(null, 1);
        } catch (NullPointerException e) {
            System.out.println("   CHM: put(null, 1) throws NullPointerException");
        }
        
        try {
            chm.put("key", null);
        } catch (NullPointerException e) {
            System.out.println("   CHM: put(\"key\", null) throws NullPointerException");
        }
        
        System.out.println("\n6. CONCLUSION:");
        System.out.println("   - CHM prioritizes thread-safety and clarity");
        System.out.println("   - No null keys or values allowed");
        System.out.println("   - Trade-off: less flexible but more predictable");
    }
}
