package com.topk.frequencyapi.controller;

import com.topk.frequencyapi.service.frequency.FrequencyCounter;
import com.topk.frequencyapi.service.frequency.entity.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "frequency")
public class FrequencyController {
    @Autowired
    private FrequencyCounter frequencyCounter;

    @GetMapping("/terms")
    public List<String> terms(@RequestParam(value = "count", defaultValue = "100") int count) {
        return frequencyCounter.getTopTerms(count);
    }

    @GetMapping("/terms/count")
    public int termsCount() {
        return frequencyCounter.getTermCount();
    }

    @GetMapping("/pairs")
    public List<Pair> pairs(@RequestParam(value = "count", defaultValue = "100") int count) {
        return frequencyCounter.getTopPairs(count);
    }
}
