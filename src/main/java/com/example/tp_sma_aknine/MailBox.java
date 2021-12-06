package com.example.tp_sma_aknine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MailBox {
    private final HashMap<Agent, LinkedList<Message>> box = new HashMap<>();

    public MailBox() {}

    public void addMessage(Agent target, int freeX, int freeY) {
        this.box.computeIfAbsent(target, k -> new LinkedList<>());
        this.box.get(target).add(new Message(freeX, freeY));
        LinkedList<Message> messages = this.box.get(target);
        System.out.println(messages);
    }

    public LinkedList<Message> deliverMessages(Agent checker) {
        for (Map.Entry mapEntry : box.entrySet()) {
            Agent agent = (Agent) mapEntry.getKey();
            if (checker.equals(agent)){
                LinkedList<Message> messages = (LinkedList<Message>) mapEntry.getValue();
                box.remove(mapEntry);
                return messages;
            }
        }
    return new LinkedList<>();
    }
}
