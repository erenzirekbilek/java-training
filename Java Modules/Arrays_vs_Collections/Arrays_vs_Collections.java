package Arrays_vs_Collections;

import java.util.*;

public class Arrays_vs_Collections {

    public static void main(String[] args) {
        System.out.println("=== ARRAYS vs COLLECTIONS ===\n");
        
        arrayDemo();
        System.out.println();
        collectionDemo();
        System.out.println();
        differences();
    }

    static void arrayDemo() {
        System.out.println("--- ARRAYS ---");
        System.out.println("Fixed-size, primitive data structure\n");
        
        String[] array = new String[5];
        array[0] = "Apple";
        array[1] = "Banana";
        array[2] = "Cherry";
        array[3] = "Date";
        array[4] = "Elderberry";

        System.out.println("Created array of size 5");
        System.out.println("Access by index: " + array[2]);
        System.out.println("Length: " + array.length);
        
        System.out.println("\nTraverse with for loop:");
        for (int i = 0; i < array.length; i++) {
            System.out.println("  [" + i + "] " + array[i]);
        }

        System.out.println("\nLimitations:");
        System.out.println("  - Fixed size (cannot resize)");
        System.out.println("  - No built-in methods");
        System.out.println("  - Only stores primitives or objects");
        System.out.println("  - No dynamic adding/removing");
    }

    static void collectionDemo() {
        System.out.println("--- COLLECTIONS ---");
        System.out.println("Dynamic, flexible data structures\n");
        
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        list.add("Date");
        list.add("Elderberry");

        System.out.println("Created ArrayList (dynamic size)");
        System.out.println("Access by index: " + list.get(2));
        System.out.println("Size: " + list.size());
        
        System.out.println("\nDynamic operations:");
        list.add("Fig");
        System.out.println("After adding 'Fig': " + list.size() + " elements");
        
        list.remove("Banana");
        System.out.println("After removing 'Banana': " + list.size() + " elements");
        
        list.add(1, "Blueberry");
        System.out.println("After inserting at index 1:");
        for (String s : list) {
            System.out.println("  - " + s);
        }

        System.out.println("\nBuilt-in methods:");
        System.out.println("  - contains(): " + list.contains("Apple"));
        System.out.println("  - isEmpty(): " + list.isEmpty());
        System.out.println("  - indexOf(): " + list.indexOf("Cherry"));
        System.out.println("  - clear(): removes all elements");
    }

    static void differences() {
        System.out.println("--- KEY DIFFERENCES ---");
        
        String[][] diff = {
            {"Feature", "Array", "Collections"},
            {"Size", "Fixed", "Dynamic (resizable)"},
            {"Performance", "Faster (no overhead)", "Slightly slower"},
            {"Type", "Primitives & Objects", "Objects only"},
            {"Methods", "length property", "Built-in methods"},
            {"Memory", "Contiguous block", "Heap storage"},
            {"Generics", "Limited", "Full generic support"}
        };
        
        System.out.println();
        for (String[] row : diff) {
            System.out.printf("%-15s | %-20s | %s%n", row[0], row[1], row[2]);
        }
        
        System.out.println("\n--- WHEN TO USE WHAT ---");
        System.out.println("Use Arrays when:");
        System.out.println("  - Size is known and fixed");
        System.out.println("  - Performance is critical");
        System.out.println("  - Working with primitives");
        
        System.out.println("\nUse Collections when:");
        System.out.println("  - Size may change");
        System.out.println("  - Need built-in methods");
        System.out.println("  - Need type safety (generics)");
    }
}
