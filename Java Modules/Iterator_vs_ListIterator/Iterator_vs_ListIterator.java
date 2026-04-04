package Iterator_vs_ListIterator;

import java.util.*;

public class Iterator_vs_ListIterator {

    public static void main(String[] args) {
        System.out.println("=== ITERATOR VS LISTITERATOR ===\n");
        
        iteratorBasics();
        System.out.println();
        listIteratorCapabilities();
        System.out.println();
        concurrentModificationException();
        System.out.println();
        interviewSummary();
    }

    static void iteratorBasics() {
        System.out.println("--- ITERATOR BASICS ---");
        
        System.out.println("\n1. ITERATOR INTERFACE:");
        System.out.println("   - Universal cursor for all Collections");
        System.out.println("   - Methods: hasNext(), next(), remove()");
        System.out.println("   - Forward-only traversal");
        
        List<String> list = Arrays.asList("A", "B", "C", "D");
        Iterator<String> iter = list.iterator();
        
        System.out.println("\n2. TRAVERSAL EXAMPLE:");
        System.out.print("   Elements: ");
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
        }
        System.out.println();
        
        System.out.println("\n3. ITERATOR FOR DIFFERENT COLLECTIONS:");
        Set<String> set = new HashSet<>(Arrays.asList("X", "Y", "Z"));
        Iterator<String> setIter = set.iterator();
        System.out.print("   Set elements: ");
        while (setIter.hasNext()) {
            System.out.print(setIter.next() + " ");
        }
        System.out.println();
        
        Map<String, Integer> map = Map.of("K1", 1, "K2", 2);
        Iterator<String> mapIter = map.keySet().iterator();
        System.out.print("   Map keys: ");
        while (mapIter.hasNext()) {
            System.out.print(mapIter.next() + " ");
        }
        System.out.println();
        
        System.out.println("\n4. REMOVING DURING ITERATION:");
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Iterator<Integer> numIter = numbers.iterator();
        while (numIter.hasNext()) {
            if (numIter.next() % 2 == 0) {
                numIter.remove();
            }
        }
        System.out.println("   After removing evens: " + numbers);
    }

    static void listIteratorCapabilities() {
        System.out.println("--- LISTITERATOR CAPABILITIES ---");
        
        System.out.println("\n1. LISTITERATOR EXTENDS ITERATOR:");
        System.out.println("   - Available ONLY for List implementations");
        System.out.println("   - Additional methods:");
        System.out.println("     * hasPrevious(), previous() - backward navigation");
        System.out.println("     * nextIndex(), previousIndex() - position tracking");
        System.out.println("     * add(E element) - insert at current position");
        System.out.println("     * set(E element) - replace last returned element");
        System.out.println("     * remove() - remove last returned element");
        
        LinkedList<String> linkedList = new LinkedList<>(Arrays.asList("A", "B", "C", "D"));
        ListIterator<String> listIter = linkedList.listIterator();
        
        System.out.println("\n2. BIDIRECTIONAL TRAVERSAL:");
        System.out.println("   Forward:");
        while (listIter.hasNext()) {
            System.out.println("     " + listIter.nextIndex() + ": " + listIter.next());
        }
        
        System.out.println("   Backward:");
        while (listIter.hasPrevious()) {
            System.out.println("     " + listIter.previousIndex() + ": " + listIter.previous());
        }
        
        System.out.println("\n3. ADD/SET OPERATIONS:");
        ListIterator<String> li = linkedList.listIterator();
        while (li.hasNext()) {
            if (li.next().equals("B")) {
                li.set("B_modified");
                li.add("NEW");
            }
        }
        System.out.println("   After set('B') and add('NEW'): " + linkedList);
        
        System.out.println("\n4. PRACTICAL USE CASE:");
        List<Integer> nums = new ArrayList<>(Arrays.asList(10, 20, 30, 40));
        ListIterator<Integer> numsLi = nums.listIterator();
        while (numsLi.hasNext()) {
            int idx = numsLi.nextIndex();
            int val = numsLi.next();
            if (val == 30) {
                numsLi.previous();
                numsLi.add(25);
                numsLi.next();
            }
        }
        System.out.println("   Inserted 25 before 30: " + nums);
    }

    static void concurrentModificationException() {
        System.out.println("--- CONCURRENTMODIFICATIONEXCEPTION ---");
        
        System.out.println("\n1. WHAT HAPPENS:");
        System.out.println("   - Thrown when collection modified during iteration");
        System.out.println("   - Except for Iterator.remove() method");
        System.out.println("   - Fail-fast behavior: detects concurrent modification");
        
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        
        System.out.println("\n2. PROBLEMATIC CODE (Throws Exception):");
        try {
            for (String s : list) {
                if (s.equals("B")) {
                    list.remove(s);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("   Exception: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\n3. SOLUTION 1: ITERATOR.REMOVE()");
        List<String> list1 = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        Iterator<String> iter1 = list1.iterator();
        while (iter1.hasNext()) {
            if (iter1.next().equals("B")) {
                iter1.remove();
            }
        }
        System.out.println("   After safe removal: " + list1);
        
        System.out.println("\n4. SOLUTION 2: COPY OF LIST");
        List<String> list2 = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        for (String s : new ArrayList<>(list2)) {
            if (s.equals("B")) {
                list2.remove(s);
            }
        }
        System.out.println("   Using copy: " + list2);
        
        System.out.println("\n5. SOLUTION 3: REMOVEIF (JAVA 8+)");
        List<String> list3 = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        list3.removeIf(s -> s.equals("B"));
        System.out.println("   Using removeIf: " + list3);
        
        System.out.println("\n6. SOLUTION 4: COPYONWRITEARRAYLIST");
        CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>(Arrays.asList("A", "B", "C", "D"));
        for (String s : cowList) {
            if (s.equals("B")) {
                cowList.remove(s);
            }
        }
        System.out.println("   Using CopyOnWriteArrayList: " + cowList);
        
        System.out.println("\n7. WHY IT HAPPENS (INTERNAL MECHANISM):");
        System.out.println("   - Each collection has modCount variable");
        System.out.println("   - Iterator stores expectedModCount at creation");
        System.out.println("   - After each next(), checks if modCount == expectedModCount");
        System.out.println("   - If different -> ConcurrentModificationException");
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        String[][] table = {
            {"Feature", "Iterator", "ListIterator"},
            {"Applicable To", "All Collections", "List Only"},
            {"Direction", "Forward only", "Bidirectional"},
            {"Add Element", "No", "Yes (add)"},
            {"Set Element", "No", "Yes (set)"},
            {"Remove Element", "Yes (remove)", "Yes (remove)"},
            {"Index Tracking", "No", "Yes (nextIndex/previousIndex)"},
            {"Throw CME", "Yes on structural change", "Yes on structural change"},
            {"Fail-Fast", "Yes", "Yes"}
        };
        
        System.out.println();
        for (String[] row : table) {
            System.out.printf("%-20s | %-20s | %s%n", row[0], row[1], row[2]);
        }
        
        System.out.println("\n--- KEY TAKEAWAYS ---");
        System.out.println("1. Use Iterator for general collections (Set, Queue)");
        System.out.println("2. Use ListIterator for List with modification needs");
        System.out.println("3. NEVER modify collection directly during enhanced for-loop");
        System.out.println("4. Use iterator.remove() or removeIf() for safe removal");
        System.out.println("5. CopyOnWriteArrayList is thread-safe alternative for iteration");
        
        System.out.println("\n--- INTERVIEW QUESTION ---");
        System.out.println("Q: How do you safely remove elements while iterating?");
        System.out.println("A: Use Iterator.remove() or List.removeIf() method");
    }
}