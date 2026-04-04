package Stream_API_Basics;

import java.util.*;
import java.util.stream.*;

public class Stream_API_Basics {

    public static void main(String[] args) {
        System.out.println("=== STREAM API BASICS ===\n");
        
        basicStreamOperations();
        System.out.println();
        oddSquaresExample();
        System.out.println();
        intermediateVsTerminal();
        System.out.println();
        commonMistakes();
    }

    static void basicStreamOperations() {
        System.out.println("--- BASIC STREAM OPERATIONS ---");
        
        System.out.println("\n1. STREAM CREATION:");
        Stream<Integer> fromArray = Stream.of(1, 2, 3, 4, 5);
        Stream<String> fromList = List.of("a", "b", "c").stream();
        IntStream range = IntStream.range(1, 10);
        
        System.out.println("   Stream.of(1,2,3,4,5) - from values");
        System.out.println("   List.of(...).stream() - from collection");
        System.out.println("   IntStream.range(1,10) - primitive range");
        
        System.out.println("\n2. INTERMEDIATE OPERATIONS (Lazy):");
        System.out.println("   - filter(Predicate) - keep matching elements");
        System.out.println("   - map(Function) - transform elements");
        System.out.println("   - flatMap(Function) - flatten nested streams");
        System.out.println("   - distinct() - remove duplicates");
        System.out.println("   - sorted() / sorted(Comparator) - sort");
        System.out.println("   - limit(n) / skip(n) - limit/skip elements");
        
        System.out.println("\n3. TERMINAL OPERATIONS (Eager):");
        System.out.println("   - forEach(Consumer) - iterate");
        System.out.println("   - collect(Collector) - to collection");
        System.out.println("   - reduce(BinaryOperator) - combine elements");
        System.out.println("   - count() / min() / max()");
        System.out.println("   - anyMatch / allMatch / noneMatch");
        System.out.println("   - toArray()");
        
        System.out.println("\n4. PIPELINE EXAMPLE:");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        List<Integer> result = numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * n)
            .limit(3)
            .collect(Collectors.toList());
        
        System.out.println("   Input: " + numbers);
        System.out.println("   filter(even) -> map(square) -> limit(3)");
        System.out.println("   Result: " + result);
    }

    static void oddSquaresExample() {
        System.out.println("--- FIND SQUARES OF ODD NUMBERS 1-10 ---");
        
        System.out.println("\n1. REQUIREMENT:");
        System.out.println("   Find squares of odd numbers from 1 to 10");
        System.out.println("   Odd numbers: 1, 3, 5, 7, 9");
        System.out.println("   Squares: 1, 9, 25, 49, 81");
        
        System.out.println("\n2. SOLUTION 1: IntStream");
        IntStream oddSquares = IntStream.rangeClosed(1, 10)
            .filter(n -> n % 2 == 1)
            .map(n -> n * n);
        
        System.out.println("   IntStream.rangeClosed(1,10)");
        System.out.println("       .filter(n -> n % 2 == 1)");
        System.out.println("       .map(n -> n * n)");
        System.out.print("   Result: ");
        oddSquares.forEach(n -> System.out.print(n + " "));
        System.out.println();
        
        System.out.println("\n3. SOLUTION 2: Stream<Integer>");
        List<Integer> oddSquaresList = Stream.iterate(1, n -> n + 1)
            .limit(10)
            .filter(n -> n % 2 == 1)
            .map(n -> n * n)
            .collect(Collectors.toList());
        
        System.out.println("   Stream.iterate(1, n -> n + 1)");
        System.out.println("       .limit(10)");
        System.out.println("       .filter(n -> n % 2 == 1)");
        System.out.println("       .map(n -> n * n)");
        System.out.println("   Result: " + oddSquaresList);
        
        System.out.println("\n4. SOLUTION 3: Using collect with joining");
        String output = IntStream.rangeClosed(1, 10)
            .filter(n -> n % 2 == 1)
            .map(n -> n * n)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(", "));
        
        System.out.println("   As joined string: " + output);
        
        System.out.println("\n5. SOLUTION 4: toSet() for unique values");
        Set<Integer> oddSquaresSet = IntStream.rangeClosed(1, 10)
            .filter(n -> n % 2 == 1)
            .map(n -> n * n)
            .boxed()
            .collect(Collectors.toSet());
        
        System.out.println("   As Set: " + oddSquaresSet);
    }

    static void intermediateVsTerminal() {
        System.out.println("--- INTERMEDIATE vs TERMINAL OPERATIONS ---");
        
        System.out.println("\n1. LAZY vs EAGER:");
        System.out.println("   Intermediate: Return new Stream (lazy)");
        System.out.println("   Terminal: Produce result (eager)");
        
        System.out.println("\n2. WITHOUT TERMINAL (Nothing happens):");
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = nums.stream()
            .filter(n -> {
                System.out.println("Filter: " + n);
                return n > 2;
            })
            .map(n -> {
                System.out.println("Map: " + n);
                return n * 2;
            });
        System.out.println("   Stream created but nothing executed yet");
        
        System.out.println("\n3. WITH TERMINAL (Executes pipeline):");
        nums.stream()
            .filter(n -> {
                System.out.println("Filter: " + n);
                return n > 2;
            })
            .map(n -> {
                System.out.println("Map: " + n);
                return n * 2;
            })
            .collect(Collectors.toList());
        
        System.out.println("\n4. SHORT-CIRCUIT OPERATIONS:");
        System.out.println("   - limit(), findFirst(), findAny() can stop early");
        
        Optional<Integer> first = nums.stream()
            .filter(n -> n > 3)
            .findFirst();
        System.out.println("   findFirst() > 3: " + first.orElse(-1));
        
        System.out.println("\n5. LAZY EVALUATION BENEFIT:");
        System.out.println("   - Memory efficient for large/infinite streams");
        System.out.println("   - Can process elements one-by-one");
    }

    static void commonMistakes() {
        System.out.println("--- COMMON MISTAKES ---");
        
        System.out.println("\n1. REUSING STREAM:");
        List<Integer> list = Arrays.asList(1, 2, 3);
        Stream<Integer> s = list.stream();
        
        System.out.println("   Mistake: Trying to use stream twice");
        try {
            s.count();
            s.filter(x -> x > 1);
        } catch (IllegalStateException e) {
            System.out.println("   Error: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\n2. MODIFYING SOURCE DURING STREAM:");
        System.out.println("   Mistake: Modifying collection while streaming");
        
        List<String> names = new ArrayList<>(Arrays.asList("A", "B", "C"));
        System.out.println("   Safe: Create new stream from copy");
        
        System.out.println("\n3. FORGOTTING TERMINAL:");
        System.out.println("   List<Integer> result = stream.filter(x -> x > 0);");
        System.out.println("   ERROR: Cannot assign Stream to List!");
        System.out.println("   FIX: .collect(Collectors.toList())");
        
        System.out.println("\n4. SIDE EFFECTS IN LAMBDA:");
        List<Integer> collected = new ArrayList<>();
        nums.stream()
            .filter(n -> n > 2)
            .forEach(collected::add);
        System.out.println("   Side effect: " + collected);
        System.out.println("   Better: Use collect()");
        
        System.out.println("\n5. NULL IN STREAM:");
        System.out.println("   Stream.of(1, null, 3) - null is allowed");
        System.out.println("   But null values cause issues in operations");
        System.out.println("   Fix: Use filter(Objects::nonNull)");
    }
}