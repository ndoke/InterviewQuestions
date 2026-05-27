package com.algorithm.crackingthecodinginterview.chapter4;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String name;
    private State state;
    private List<Node> adjacent;

    public Node(String name, State state) {
        this.name = name;
        this.state = state;
        this.adjacent = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Node> getAdjacent() {
        return adjacent;
    }

    public void setAdjacent(List<Node> adjacent) {
        this.adjacent = adjacent;
    }
}
