package com.topk.frequencyapi.service.frequency.entity;

public class Pair {
    private final String term;
    private final int count;

    public Pair(String term, int count) {
        this.term = term;
        this.count = count;
    }

    public String getTerm() {
        return term;
    }

    public int getCount() {
        return count;
    }
}
