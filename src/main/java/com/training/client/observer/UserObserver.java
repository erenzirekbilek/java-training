package com.training.client.observer;

import java.util.ArrayList;
import java.util.List;

public class UserObserver implements Observer {
    private final List<String> events = new ArrayList<>();

    @Override
    public void update(String event) {
        events.add(event);
    }

    public List<String> getEvents() {
        return events;
    }

    public interface Observer {
        void update(String event);
    }

    public static class UserSubject {
        private final List<Observer> observers = new ArrayList<>();

        public void attach(Observer observer) {
            observers.add(observer);
        }

        public void detach(Observer observer) {
            observers.remove(observer);
        }

        public void notifyObservers(String event) {
            observers.forEach(o -> o.update(event));
        }
    }
}