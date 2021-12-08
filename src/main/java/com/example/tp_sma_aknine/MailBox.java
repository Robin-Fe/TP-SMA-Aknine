package com.example.tp_sma_aknine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MailBox {
    private final HashMap<Agent, LinkedList<Message>> box = new HashMap<>();
    private boolean semaphore = true;

    public MailBox() {}

    public void addMessage(Agent target, Coordinate coordinate) {
        this.box.computeIfAbsent(target, k -> new LinkedList<>());
        this.box.get(target).add(new Message(coordinate));
    }

    public LinkedList<Message> deliverMessages(Agent checker) {
        for (Map.Entry mapEntry : box.entrySet()) {
            Agent agent = (Agent) mapEntry.getKey();
            if (checker.equals(agent)){
                LinkedList<Message> messages = (LinkedList<Message>) mapEntry.getValue();
                return messages;
            }
        }
        return new LinkedList<>();
    }

    public boolean getSemaphore() {
        return semaphore;
    }

    public boolean pickSemaphore() {
        // ToDo : increment score
        if (getSemaphore()) {
            semaphore = false;
            return true;
        }
        return false;
    }

    public void letSemaphore() {
        semaphore = true;
    }

    public void emptyBox(Agent checker){
        Agent removeAgent = null;
        for (Map.Entry mapEntry : box.entrySet()) {
            Agent agent = (Agent) mapEntry.getKey();
            if (checker.equals(agent)){
                removeAgent = agent;
            }
        }
        this.box.remove(removeAgent);
    }
}
