package com.training.client.collection;

import java.util.*;

public class UserCollectionService {
    
    public List<String> createArrayList() {
        List<String> users = new ArrayList<>();
        users.add("user1");
        users.add("user2");
        return users;
    }

    public List<String> createLinkedList() {
        List<String> users = new LinkedList<>();
        users.add("user1");
        users.add("user2");
        return users;
    }

    public Map<Long, String> createHashMap() {
        Map<Long, String> users = new HashMap<>();
        users.put(1L, "user1");
        users.put(2L, "user2");
        return users;
    }

    public Map<Long, String> createLinkedHashMap() {
        Map<Long, String> users = new LinkedHashMap<>();
        users.put(1L, "user1");
        users.put(2L, "user2");
        return users;
    }

    public Set<String> createHashSet() {
        Set<String> users = new HashSet<>();
        users.add("user1");
        users.add("user2");
        return users;
    }

    public Map<String, String> createTreeMap() {
        Map<String, String> users = new TreeMap<>();
        users.put("a", "user1");
        users.put("b", "user2");
        return users;
    }
}