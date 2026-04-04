package Map_vs_Collections;

import java.util.*;

public class Map_vs_Collections {

    public static void main(String[] args) {
        System.out.println("=== WHY MAP IS SEPARATE FROM COLLECTIONS ===\n");
        
        mapVsCollection();
        System.out.println();
        mapHierarchy();
        System.out.println();
        structuralDifferences();
        System.out.println();
        whySeparate();
    }

    static void mapVsCollection() {
        System.out.println("--- MAP vs COLLECTION FRAMEWORK ---");
        
        System.out.println("\n1. CORE DIFFERENCE:");
        System.out.println("   COLLECTION: Stores individual elements");
        System.out.println("   MAP: Stores key-value pairs (associations)");
        
        System.out.println("\n2. COLLECTION (List, Set, Queue):");
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        
        System.out.println("   list.add(\"Apple\") -> stores element directly");
        System.out.println("   Iterating: for (String s : list)");
        System.out.println("   Elements: " + list);
        
        System.out.println("\n3. MAP:");
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 1);
        map.put("Banana", 2);
        map.put("Cherry", 3);
        
        System.out.println("   map.put(\"Apple\", 1) -> stores key-value pair");
        System.out.println("   Iterating: for (String key : map.keySet())");
        System.out.println("   Keys: " + map.keySet());
        System.out.println("   Values: " + map.values());
    }

    static void mapHierarchy() {
        System.out.println("--- JAVA COLLECTIONS HIERARCHY ---");
        
        System.out.println("\n1. COLLECTION INTERFACE:");
        System.out.println("   Collection<E>");
        System.out.println("   ├── List<E> (ordered, allows duplicates)");
        System.out.println("   │   ├── ArrayList, LinkedList, Vector");
        System.out.println("   ├── Set<E> (no duplicates)");
        System.out.println("   │   ├── HashSet, TreeSet, LinkedHashSet");
        System.out.println("   │   └── SortedSet<E>");
        System.out.println("   └── Queue<E> (FIFO)");
        System.out.println("       ├── PriorityQueue, LinkedList");
        
        System.out.println("\n2. MAP INTERFACE (Separate from Collection):");
        System.out.println("   Map<K,V>");
        System.out.println("   ├── HashMap (no order)");
        System.out.println("   ├── LinkedHashMap (insertion order)");
        System.out.println("   ├── TreeMap (sorted by keys)");
        System.out.println("   ├── Hashtable (synchronized)");
        System.out.println("   └── ConcurrentHashMap (thread-safe)");
        System.out.println("   └── SortedMap<K,V>");
    }

    static void structuralDifferences() {
        System.out.println("--- STRUCTURAL DIFFERENCES ---");
        
        String[][] diff = {
            {"Feature", "Collection", "Map"},
            {"Data Structure", "Single elements", "Key-Value pairs"},
            {"Interface", "extends Collection", "Standalone interface"},
            {"Iteration", "Iterator<E>", "keySet(), values(), entrySet()"},
            {"Add Method", "add(E element)", "put(K key, V value)"},
            {"Get Method", "get(index)", "get(K key)"},
            {"Null Support", "List/Set allow null", "HashMap allows null keys/values"},
            {"Duplicates", "Set removes duplicates", "Keys must be unique"}
        };
        
        System.out.println();
        for (String[] row : diff) {
            System.out.printf("%-15s | %-20s | %s%n", row[0], row[1], row[2]);
        }
        
        System.out.println("\n--- CODE EXAMPLES ---");
        
        System.out.println("\nCollection operations:");
        Collection<String> coll = new ArrayList<>();
        coll.add("A");
        coll.add("B");
        coll.add("C");
        System.out.println("   add(), remove(), contains()");
        
        System.out.println("\nMap operations:");
        Map<String, Integer> m = new HashMap<>();
        m.put("A", 1);
        m.put("B", 2);
        m.put("C", 3);
        System.out.println("   put(), get(), containsKey(), entrySet()");
    }

    static void whySeparate() {
        System.out.println("--- WHY MAP IS SEPARATE ---");
        
        System.out.println("\n1. FUNDAMENTAL DIFFERENCE:");
        System.out.println("   - Collection stores elements individually");
        System.out.println("   - Map stores associations (key -> value)");
        System.out.println("   - Different data model requires different interface");
        
        System.out.println("\n2. HISTORICAL REASON:");
        System.out.println("   - Map existed before Java 1.2 Collections");
        System.out.println("   - Dictionary class was the predecessor");
        System.out.println("   - Kept separate due to different semantics");
        
        System.out.println("\n3. DESIGN PRINCIPLE:");
        System.out.println("   - Different operations for different needs");
        System.out.println("   - Cannot force Map into Collection hierarchy");
        System.out.println("   - Would break Liskov Substitution Principle");
        
        System.out.println("\n4. WHY NOT EXTEND COLLECTION?");
        Map<String, Integer> map = new HashMap<>();
        
        System.out.println("   Cannot do: map.add(\"key\") - needs 2 parameters");
        System.out.println("   Cannot do: map.contains(\"value\") - need key");
        System.out.println("   Cannot iterate elements directly - have pairs");
        
        System.out.println("\n5. THE entrySet() COMPROMISE:");
        System.out.println("   MapEntry (Map.Entry) bridges the gap:");
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            String key = entry.getKey();
            Integer value = entry.getValue();
        }
        System.out.println("   Entry set IS a Collection (Set)");
    }
}
