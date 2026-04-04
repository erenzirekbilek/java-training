Aşağıda Java ile ilgili konu başlıkları verilmiştir. Her bir konu için ayrı bir **eğitim modülü** hazırla.

Her modül şu bölümleri içermelidir:
1. **Kısa Teori** (2-3 paragraf)
2. **Kod Örneği** (çalıştırılabilir, açıklamalı)
3. **Sık Yapılan Hatalar** (madde madde)
4. **Mülakat Sorusu Tipinde Özet**

Konular:

- Internal working of Hashmap / ConcurrentHashMap, neden CHM null'a izin vermez?
- Map neden Collection arayüzünden ayrı olarak düşünülmüş?
- Iterator vs ListIterator çalışma prensibi – ConcurrentModificationException nasıl çözülür?
- Java 8 / 17 / 21 özellikleri – Java 8 ile sealed class nasıl taklit edilir?
- Stream API – 1'den 10'a kadar tek sayıların karesini bul (kod).
- Interface çeşitleri: functional vs marker vs normal (farkları ve örnekler)
- final vs finally vs finalize (karşılaştırmalı tablo + kod)
- Comparator vs Comparable – nasıl implement edilir? (önce doğal, sonra özel sıralama)
- Compile time vs runtime exception (checked/unchecked farkı + örnek)
- synchronized block – bir işlemi nasıl thread-safe yaparsın? (örnek: banka hesabı)
- Nedir thread? Thread lifecycle (durumları ve geçişleri)
- Multithreading neden ortaya çıktı? – Büyük miktarda veri kaydetme/silme işlemini nasıl hızlandırır?
- Throw vs Throws (farklar + metod imzasında kullanım)
- super vs this (nerede, ne zaman kullanılır)
- Process vs Thread (memory, maliyet, iletişim farkları)
- Synchronization hangi durumda faydalıdır? Read mi write mı? – ConcurrentHashMap örneğiyle.
- Singleton tasarım deseni (lazy, eager, double-checked locking, enum)
- hashCode() ve equals() nedir? Sadece birini implement edip Map'te key olarak kullanırsan ne olur? (örnek: Person(name="Rahul"))
- Person p ve p1 aynı "Rahul" ismine sahip. equals/hashcode uygulanırsa map'te ne olur? Uygulanmazsa ne olur?

Not: Her modülü bağımsız olarak anlaşılacak şekilde yaz. Java 8+ sözdizimi kullan. Gerekiyorsa UML benzeri diyagramları metin olarak çiz (örn: [*] --> Runnable).