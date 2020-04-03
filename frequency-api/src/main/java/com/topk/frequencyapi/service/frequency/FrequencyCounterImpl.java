package com.topk.frequencyapi.service.frequency;

import com.topk.frequencyapi.service.frequency.entity.CountNode;
import com.topk.frequencyapi.service.frequency.entity.Node;
import com.topk.frequencyapi.service.frequency.entity.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class FrequencyCounterImpl implements FrequencyCounter {
    private CountNode biggest = null;
    private CountNode smallest = null;
    private final Map<String, Node> nodeMap;

    public FrequencyCounterImpl() {
        nodeMap = new ConcurrentHashMap<>();
    }

    public synchronized void insert(String word) {
        Node node = nodeMap.computeIfAbsent(word, c -> new Node(word));
        CountNode newCountNode = applyNodeAction(node, countNode -> countNode.increment());
        setBiggestCountNode(newCountNode);
    }

    public synchronized void remove(String word) {
        Node node = nodeMap.get(word);
        if (node != null) {
            CountNode oldCountNode = node.getCountNode();
            applyNodeAction(node, countNode -> countNode.decrement());
            syncBiggestCountNode(oldCountNode);
        }
    }

    public List<Pair> gettopkPairs(int count) {
        List<Pair> result = new ArrayList<>();
        CountNode current = biggest;
        while (count > 0 && current != null) {
            Node node = current.getHead();
            while (count > 0 && node != null) {
                result.add(new Pair(node.getValue(), current.getCount()));
                node = node.getNext();
                count--;
            }
            current = current.getPrev();
        }
        return result;
    }

    public List<String> gettopkTerms(int count) {
        List<Pair> pairs = gettopkPairs(count);
        return pairs.stream().map(c -> c.getTerm()).collect(Collectors.toList());
    }

    public int getTermCount() {
        return nodeMap.size();
    }

    private CountNode applyNodeAction(Node node, NodeAction nodeAction) {
        CountNode oldCountNode = getCountNode(node);
        CountNode newCountNode = nodeAction.apply(oldCountNode);
        oldCountNode.syncHead(node);
        node.removeReferences();
        addNodeToCountNode(node, newCountNode);
        return newCountNode;
    }

    private void syncBiggestCountNode(CountNode oldCountNode) {
        if (biggest == oldCountNode) {
            while (biggest != null && biggest.getHead() == null && biggest.getPrev() != null) {
                biggest = biggest.getPrev();
                biggest.setNext(null);
            }
        }
    }

    private void setBiggestCountNode(CountNode newCountNode) {
        if (biggest == null) {
            biggest = newCountNode;
        } else {
            if (newCountNode.getCount() > biggest.getCount()) {
                biggest = newCountNode;
            }
        }
    }

    private void addNodeToCountNode(Node node, CountNode countNode) {
        if (countNode == getCountNodeZero()) {
            nodeMap.remove(node.getValue());
            return;
        }

        if (countNode.getHead() == null) {
            countNode.setHead(node);
        } else {
            node.setNext(countNode.getHead());
            node.setPrev(null);
            countNode.getHead().setPrev(node);
            countNode.setHead(node);
        }
        node.setCountNode(countNode);
    }

    private CountNode getCountNode(Node node) {
        if (node.getCountNode() != null) {
            return node.getCountNode();
        }

        return getCountNodeZero();
    }

    private CountNode getCountNodeZero() {
        if (smallest == null || smallest.getCount() != 0) {
            smallest = new CountNode(0);
        }
        return smallest;
    }
}

