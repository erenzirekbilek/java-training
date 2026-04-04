package Set_vs_ArrayList;

import java.util.*;

public class Set_vs_ArrayList {

    public static void main(String[] args) {
        System.out.println("=== SET vs ARRAYLIST ===\n");
        
        arrayListDemo();
        System.out.println();
        setDemo();
        System.out.println();
        differences();
    }

    static void arrayListDemo() {
        System.out.println("--- ARRAYLIST ---");
        System.out.println("Ordered collection that allows duplicates\n");
        
        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Apple");
        fruits.add("Mango");
        fruits.add("Banana");

        System.out.println("Added: Apple, Banana, Apple, Mango, Banana");
        System.out.println("Contains duplicates: YES");
        System.out.println("Order: Maintained (insertion order)");
        System.out.println("Size: " + fruits.size());
        System.out.println("Get by index: " + fruits.get(2));
        System.out.println("Contains Apple: " + fruits.contains("Apple"));
        
        System.out.println("\nAll elements:");
        for (String fruit : fruits) {
            System.out.println("  - " + fruit);
        }
    }

    static void setDemo() {
        System.out.println("--- SET ---");
        System.out.println("Collection that does NOT allow duplicates\n");
        
        Set<String> fruitSet = new HashSet<>();
        fruitSet.add("Apple");
        fruitSet.add("Banana");
        fruitSet.add("Apple");
        fruitSet.add("Mango");
        fruitSet.add("Banana");

        System.out.println("Added: Apple, Banana, Apple, Mango, Banana");
        System.out.println("Contains duplicates: NO (duplicates ignored)");
        System.out.println("Order: NOT guaranteed (HashSet)");
        System.out.println("Size: " + fruitSet.size());
        System.out.println("Contains Apple: " + fruitSet.contains("Apple"));
        
        System.out.println("\nAll elements:");
        for (String fruit : fruitSet) {
            System.out.println("  - " + fruit);
        }

        System.out.println("\n--- LINKEDHASHSET (Preserves Order) ---");
        Set<String> linkedSet = new LinkedHashSet<>();
        linkedSet.add("First");
        linkedSet.add("Second");
        linkedSet.add("First");
        linkedSet.add("Third");
        
        System.out.println("Added: First, Second, First, Third");
        System.out.println("Order: Maintained (insertion order)");
        for (String s : linkedSet) {
            System.out.println("  - " + s);
        }

        System.out.println("\n--- TREESET (Sorted Order) ---");
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Banana");
        treeSet.add("Apple");
        treeSet.add("Mango");
        treeSet.add("Cherry");
        
        System.out.println("Added: Banana, Apple, Mango, Cherry");
        System.out.println("Order: Sorted (alphabetical)");
        for (String s : treeSet) {
            System.out.println("  - " + s);
        }
    }

    static void differences() {
        System.out.println("--- KEY DIFFERENCES ---");
        
        String[][] diff = {
            {"Feature", "ArrayList", "Set"},
            {"Duplicates", "Allowed", "Not Allowed"},
            {"Order", "Insertion order", "Not guaranteed (except LinkedHashSet, TreeSet)"},
            {"Get by index", "Yes (get(index))", "No"},
            {"Performance", "Fast for indexing", "HashSet: Fast, TreeSet: O(log n)"},
            {"Use case", "Sequential access, duplicates needed", "Unique items needed"}
        };
        
        System.out.println();
        for (String[] row : diff) {
            System.out.printf("%-15s | %-20s | %s%n", row[0], row[1], row[2]);
        }
        
        System.out.println("\n--- WHEN TO USE WHAT ---");
        System.out.println("Use ArrayList when:");
        System.out.println("  - You need to access elements by index");
        System.out.println("  - Duplicates are allowed");
        System.out.println("  - Order matters");
        
        System.out.println("\nUse Set when:");
        System.out.println("  - You need unique elements only");
        System.out.println("  - Fast lookup is important");
        System.out.println("  - Order doesn't matter (or use LinkedHashSet/TreeSet)");
    }
}
