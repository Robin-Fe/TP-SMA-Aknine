package com.example.tp_sma_aknine;

import java.util.HashMap;
import java.util.LinkedList;

public class MailBox {
    private final HashMap<Agent, LinkedList<Message>> box = new HashMap<>();

    public MailBox() {}

    public void addMessage(Agent target, int freeX, int freeY) {
        this.box.computeIfAbsent(target, k -> new LinkedList<>());
        this.box.get(target).add(new Message(freeX, freeY));
    }

    public LinkedList<Message> deliverMessages(Agent checker) {
        LinkedList<Message> messages = this.box.get(checker);
        this.box.remove(checker);
        return messages;
    }
}
