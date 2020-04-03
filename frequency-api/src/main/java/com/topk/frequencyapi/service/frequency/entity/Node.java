package com.topk.frequencyapi.service.frequency.entity;

public class Node {
    private CountNode countNode;
    private final String value;
    private Node prev;
    private Node next;

    public Node(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public CountNode getCountNode() {
        return this.countNode;
    }

    public void setCountNode(CountNode countNode) {
        this.countNode = countNode;
    }

    public Node getPrev() {
        return this.prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return this.next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void removeReferences() {
        if (prev != null) {
            prev.setNext(next);
        }
        if (next != null) {
            next.setPrev(prev);
        }
        prev = null;
        next = null;
    }

    @Override
    public String toString() {
        return String.format("Value: %s Prev: %s Next: %s CountNode: %s",
                this.value,
                this.prev != null ? this.prev.value : "",
                this.next != null ? this.next.value : "",
                this.countNode != null ? this.countNode.getCount() : "");
    }
}
