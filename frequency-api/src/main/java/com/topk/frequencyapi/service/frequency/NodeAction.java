package com.topk.frequencyapi.service.frequency;

import com.topk.frequencyapi.service.frequency.entity.CountNode;

public interface NodeAction {
    CountNode apply(CountNode countNode);
}
