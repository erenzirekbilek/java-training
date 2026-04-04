package HashCode_Equals;

import java.util.*;

public class HashCode_Equals {

    public static void main(String[] args) {
        System.out.println("=== HASHCODE AND EQUALS ===\n");
        
        equalsMethod();
        System.out.println();
        hashCodeMethod();
        System.out.println();
        contract();
        System.out.println();
        personMapExample();
        System.out.println();
        commonMistakes();
        System.out.println();
        interviewSummary();
    }

    static void equalsMethod() {
        System.out.println("--- EQUALS METHOD ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - In Object class, default uses == (reference comparison)");
        System.out.println("   - Should be overridden for logical equality");
        System.out.println("   - Returns boolean");
        
        System.out.println("\n2. DEFAULT BEHAVIOR:");
        
        String s1 = new String("Hello");
        String s2 = new String("Hello");
        
        System.out.println("   s1 == s2: " + (s1 == s2));
        System.out.println("   s1.equals(s2): " + s1.equals(s2));
        
        System.out.println("\n3. CUSTOM EQUALS:");
        
        class Person {
            String name;
            int age;
            
            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }
            
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                
                Person other = (Person) obj;
                return age == other.age && 
                       (name != null ? name.equals(other.name) : other.name == null);
            }
        }
        
        Person p1 = new Person("John", 30);
        Person p2 = new Person("John", 30);
        Person p3 = new Person("Jane", 25);
        
        System.out.println("\n   p1.equals(p2): " + p1.equals(p2));
        System.out.println("   p1.equals(p3): " + p1.equals(p3));
        
        System.out.println("\n4. EQUALS CONTRACT:");
        System.out.println("   - Reflexive: x.equals(x) is true");
        System.out.println("   - Symmetric: x.equals(y) == y.equals(x)");
        System.out.println("   - Transitive: x.equals(y) && y.equals(z) => x.equals(z)");
        System.out.println("   - Consistent: Multiple calls return same result");
        System.out.println("   - Null check: x.equals(null) is false");
    }

    static void hashCodeMethod() {
        System.out.println("--- HASHCODE METHOD ---");
        
        System.out.println("\n1. DEFINITION:");
        System.out.println("   - Returns int hash value");
        System.out.println("   - Used by HashMap, HashSet, Hashtable");
        System.out.println("   - Should be consistent with equals()");
        
        System.out.println("\n2. DEFAULT BEHAVIOR:");
        
        String str = "Hello";
        System.out.println("   \"Hello\".hashCode(): " + str.hashCode());
        System.out.println("   \"Hello\".hashCode(): " + "Hello".hashCode());
        System.out.println("   \"World\".hashCode(): " + "World".hashCode());
        
        System.out.println("\n3. CUSTOM HASHCODE:");
        
        class Person {
            String name;
            int age;
            
            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }
            
            @Override
            public int hashCode() {
                int result = name != null ? name.hashCode() : 0;
                result = 31 * result + age;
                return result;
            }
        }
        
        Person p1 = new Person("John", 30);
        Person p2 = new Person("John", 30);
        
        System.out.println("\n   p1.hashCode(): " + p1.hashCode());
        System.out.println("   p2.hashCode(): " + p2.hashCode());
        System.out.println("   Same? " + (p1.hashCode() == p2.hashCode()));
        
        System.out.println("\n4. HASHCODE FORMULA (Common pattern):");
        System.out.println("   result = 31 * result + field.hashCode()");
        System.out.println("   31 is prime, produces better distribution");
    }

    static void contract() {
        System.out.println("--- EQUALS-HASHCODE CONTRACT ---");
        
        System.out.println("\n1. THE CONTRACT:");
        System.out.println("   If a.equals(b) is true, then a.hashCode() == b.hashCode()");
        System.out.println("   MUST be true!");
        
        System.out.println("\n2. WHY IT MATTERS:");
        
        PersonWithEquals person1 = new PersonWithEquals("John", 30);
        PersonWithEquals person2 = new PersonWithEquals("John", 30);
        
        Map<PersonWithEquals, String> map = new HashMap<>();
        map.put(person1, "Developer");
        
        System.out.println("\n   Using in HashMap:");
        System.out.println("   map.get(person2): " + map.get(person2));
        System.out.println("   equals: " + person1.equals(person2));
        System.out.println("   hashCode: " + (person1.hashCode() == person2.hashCode()));
        
        System.out.println("\n3. BREAKING THE CONTRACT:");
        
        System.out.println("   Wrong: Override equals but not hashCode:");
        
        class WrongPerson {
            String name;
            
            WrongPerson(String name) {
                this.name = name;
            }
            
            @Override
            public boolean equals(Object obj) {
                return name.equals(((WrongPerson) obj).name);
            }
            
            // hashCode NOT overridden - uses Object.hashCode()
        }
        
        WrongPerson wp1 = new WrongPerson("John");
        WrongPerson wp2 = new WrongPerson("John");
        
        Map<WrongPerson, String> wrongMap = new HashMap<>();
        wrongMap.put(wp1, "Developer");
        
        System.out.println("   wp1.equals(wp2): " + wp1.equals(wp2));
        System.out.println("   wp1.hashCode(): " + wp1.hashCode());
        System.out.println("   wp2.hashCode(): " + wp2.hashCode());
        System.out.println("   map.get(wp2): " + wrongMap.get(wp2));
        System.out.println("   PROBLEM: Returns null despite equals being true!");
        
        System.out.println("\n4. CORRECT IMPLEMENTATION:");
        
        class CorrectPerson {
            String name;
            int age;
            
            CorrectPerson(String name, int age) {
                this.name = name;
                this.age = age;
            }
            
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                CorrectPerson other = (CorrectPerson) obj;
                return age == other.age && 
                       (name != null ? name.equals(other.name) : other.name == null);
            }
            
            @Override
            public int hashCode() {
                int result = name != null ? name.hashCode() : 0;
                result = 31 * result + age;
                return result;
            }
        }
        
        CorrectPerson cp1 = new CorrectPerson("John", 30);
        CorrectPerson cp2 = new CorrectPerson("John", 30);
        
        Map<CorrectPerson, String> correctMap = new HashMap<>();
        correctMap.put(cp1, "Developer");
        
        System.out.println("   cp1.equals(cp2): " + cp1.equals(cp2));
        System.out.println("   cp1.hashCode() == cp2.hashCode(): " + (cp1.hashCode() == cp2.hashCode()));
        System.out.println("   map.get(cp2): " + correctMap.get(cp2));
        System.out.println("   CORRECT!");
    }

    static void personMapExample() {
        System.out.println("--- PERSON EQUALITY IN MAP ===");
        
        System.out.println("\n1. SCENARIO:");
        System.out.println("   Person(name=\"Rahul\") as Map key");
        System.out.println("   What happens with/without equals/hashCode?");
        
        System.out.println("\n2. WITHOUT EQUALS/HASHCODE:");
        
        class PersonWithoutEquals {
            String name;
            
            PersonWithoutEquals(String name) {
                this.name = name;
            }
            
            // No equals() or hashCode() overridden
        }
        
        Map<PersonWithoutEquals, String> map1 = new HashMap<>();
        
        PersonWithoutEquals p1 = new PersonWithoutEquals("Rahul");
        PersonWithoutEquals p2 = new PersonWithoutEquals("Rahul");
        PersonWithoutEquals p3 = new PersonWithoutEquals("Rahul");
        
        map1.put(p1, "First");
        map1.put(p2, "Second");
        map1.put(p3, "Third");
        
        System.out.println("   map1.size(): " + map1.size());
        System.out.println("   map1.get(p1): " + map1.get(p1));
        System.out.println("   Result: 3 different entries (reference equality)");
        
        System.out.println("\n3. WITH EQUALS/HASHCODE:");
        
        class PersonWithEquals {
            String name;
            
            PersonWithEquals(String name) {
                this.name = name;
            }
            
            @Override
            public boolean equals(Object obj) {
                return name.equals(((PersonWithEquals) obj).name);
            }
            
            @Override
            public int hashCode() {
                return name.hashCode();
            }
        }
        
        Map<PersonWithEquals, String> map2 = new HashMap<>();
        
        PersonWithEquals p4 = new PersonWithEquals("Rahul");
        PersonWithEquals p5 = new PersonWithEquals("Rahul");
        PersonWithEquals p6 = new PersonWithEquals("Rahul");
        
        map2.put(p4, "First");
        map2.put(p5, "Second");
        map2.put(p6, "Third");
        
        System.out.println("   map2.size(): " + map2.size());
        System.out.println("   map2.get(p4): " + map2.get(p4));
        System.out.println("   Result: 1 entry (value overwritten)");
        
        System.out.println("\n4. GETTING BACK SAME PERSON:");
        
        PersonWithEquals lookup = new PersonWithEquals("Rahul");
        System.out.println("   map2.get(new PersonWithEquals(\"Rahul\")): " + map2.get(lookup));
        
        System.out.println("\n5. PRACTICAL IMPLICATIONS:");
        
        Map<String, Integer> stringMap = new HashMap<>();
        
        String s1 = new String("Rahul");
        String s2 = new String("Rahul");
        
        stringMap.put(s1, 100);
        
        System.out.println("   stringMap.get(s2): " + stringMap.get(s2));
        System.out.println("   String works because it has proper equals/hashCode");
    }

    static void commonMistakes() {
        System.out.println("--- COMMON MISTAKES ---");
        
        System.out.println("\n1. ONLY OVERRIDING EQUALS:");
        System.out.println("   Problem: HashMap won't find the element");
        
        class Incomplete {
            String name;
            
            @Override
            public boolean equals(Object obj) {
                return name.equals(((Incomplete) obj).name);
            }
        }
        
        Map<Incomplete, String> m = new HashMap<>();
        Incomplete i1 = new Incomplete();
        i1.name = "Test";
        
        m.put(i1, "Value");
        
        Incomplete i2 = new Incomplete();
        i2.name = "Test";
        
        System.out.println("   equals: " + i1.equals(i2));
        System.out.println("   get result: " + m.get(i2));
        System.out.println("   PROBLEM: Returns null!");
        
        System.out.println("\n2. ONLY OVERRIDING HASHCODE:");
        System.out.println("   Problem: All objects have same hash, bad performance");
        
        class WrongHash {
            String name;
            
            @Override
            public int hashCode() {
                return 42;
            }
        }
        
        System.out.println("   All hashCodes return 42 - degenerates to list");
        
        System.out.println("\n3. INCONSISTENT FIELDS:");
        System.out.println("   Problem: HashCode changes while in collection");
        
        class Mutable {
            String name;
            
            public void setName(String name) {
                this.name = name;
            }
            
            @Override
            public int hashCode() {
                return name.hashCode();
            }
        }
        
        Map<Mutable, String> mutableMap = new HashMap<>();
        Mutable m1 = new Mutable();
        m1.name = "Original";
        
        mutableMap.put(m1, "Value");
        
        m1.name = "Changed";
        
        System.out.println("   After changing key:");
        System.out.println("   map.get(m1): " + mutableMap.get(m1));
        System.out.println("   PROBLEM: Cannot find element!");
        
        System.out.println("\n4. NOT USING ALL RELEVANT FIELDS:");
        System.out.println("   Problem: Different objects considered equal");
        
        class PartialEquals {
            int a;
            int b;
            
            PartialEquals(int a, int b) {
                this.a = a;
                this.b = b;
            }
            
            @Override
            public boolean equals(Object obj) {
                return a == ((PartialEquals) obj).a;
                // Only uses 'a', not 'b' - inconsistent!
            }
            
            @Override
            public int hashCode() {
                return Integer.hashCode(a);
            }
        }
        
        System.out.println("   equals(new(1,2), new(1,3)) = " + 
            new PartialEquals(1, 2).equals(new PartialEquals(1, 3)));
    }

    static void interviewSummary() {
        System.out.println("--- INTERVIEW SUMMARY ---");
        
        System.out.println("\n1. EQUALS vs ==:");
        System.out.println("   ==: Reference comparison (same object)");
        System.out.println("   equals: Logical comparison (should be overridden)");
        
        System.out.println("\n2. WHY OVERRIDE BOTH:");
        System.out.println("   - HashMap/HashSet use hashCode to find bucket");
        System.out.println("   - Then use equals to find exact element");
        System.out.println("   - Must be consistent!");
        
        System.out.println("\n3. BEST PRACTICES:");
        System.out.println("   - Use Objects.hash() for quick implementation");
        System.out.println("   - Use same fields for both equals and hashCode");
        System.out.println("   - Make keys immutable for HashMap");
        
        System.out.println("\n4. IDE GENERATED CODE:");
        System.out.println("   @Override");
        System.out.println("   public boolean equals(Object o) {");
        System.out.println("       if (this == o) return true;");
        System.out.println("       if (o == null || getClass() != o.getClass()) return false;");
        System.out.println("       Person person = (Person) o;");
        System.out.println("       return age == person.age &&");
        System.out.println("              Objects.equals(name, person.name);");
        System.out.println("   }");
        
        System.out.println("   @Override");
        System.out.println("   public int hashCode() {");
        System.out.println("       return Objects.hash(name, age);");
        System.out.println("   }");
        
        System.out.println("\n--- INTERVIEW QUESTIONS ---");
        System.out.println("Q: What happens if only equals is overridden?");
        System.out.println("A: HashMap can't find elements (hashCode mismatch)");
        
        System.out.println("\nQ: What happens if only hashCode is overridden?");
        System.out.println("A: Different objects may collide, slow performance");
        
        System.out.println("\nQ: Can hashCode be same for different objects?");
        System.out.println("A: Yes (hash collision), but degrades performance");
        
        System.out.println("\nQ: Should keys be immutable?");
        System.out.println("A: Yes, otherwise hashCode changes break HashMap");
    }
}

class PersonWithEquals {
    String name;
    
    PersonWithEquals(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
        return name.equals(((PersonWithEquals) obj).name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}