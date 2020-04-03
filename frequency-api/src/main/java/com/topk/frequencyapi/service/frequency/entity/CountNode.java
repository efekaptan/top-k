package com.topk.frequencyapi.service.frequency.entity;

public class CountNode {
    private final int count;
    private Node head;
    private CountNode next;
    private CountNode prev;

    public CountNode(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public Node getHead() {
        return this.head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public CountNode getNext() {
        return next;
    }

    public void setNext(CountNode next) {
        this.next = next;
    }

    public CountNode getPrev() {
        return prev;
    }

    public void setPrev(CountNode prev) {
        this.prev = prev;
    }

    public CountNode increment() {
        int newCount = getCount() + 1;
        CountNode newCountNode;
        if (next != null && next.count == newCount) {
            newCountNode = next;
        } else {
            newCountNode = new CountNode(newCount);
            newCountNode.next = next;
            newCountNode.setPrev(this);
            next = newCountNode;
        }

        return newCountNode;
    }

    public CountNode decrement() {
        int newCount = Math.max(0, count - 1);
        CountNode newCountNode;
        if (prev != null && prev.count == newCount) {
            newCountNode = prev;
        } else {
            newCountNode = new CountNode(newCount);
            newCountNode.next = this;
            newCountNode.prev = prev;
            prev = newCountNode;
        }

        return newCountNode;
    }

    public void syncHead(Node node) {
        if (head == node) {
            head = head.getNext();
        }
    }

    @Override
    public String toString() {
        return String.format("Count: %s Prev: %s Next: %s Head: %s",
                count,
                prev != null ? prev.count : "",
                next != null ? next.count : "",
                head != null ? head.getValue() : "");
    }
}
