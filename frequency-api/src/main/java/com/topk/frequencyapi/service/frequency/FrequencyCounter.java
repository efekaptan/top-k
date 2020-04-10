package com.topk.frequencyapi.service.frequency;

import com.topk.frequencyapi.service.frequency.entity.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FrequencyCounter {
    void insert(String word);

    void remove(String word);

    List<Pair> getTopPairs(int count);

    List<String> getTopTerms(int count);

    int getTermCount();
}
