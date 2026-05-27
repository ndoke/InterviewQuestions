package com.algorithm.crackingthecodinginterview.chapter4;

import java.util.LinkedList;

public class RouteBetweenNodes {
    public boolean hasPath(Graph g, Node start, Node end) {
        if (start == end) {
            return true;
        }

        for (Node n : g.getNodes()) {
            n.setState(State.UNVISITED);
        }

        LinkedList<Node> q = new LinkedList<>();
        start.setState(State.VISITING);
        q.add(start);
        Node u;

        while (!q.isEmpty()) {
            u = q.removeFirst();
            if (u != null) {
                for (Node adj : u.getAdjacent()) {
                    if (adj.getState() == State.UNVISITED) {
                        if (adj == end) {
                            return true;
                        } else {
                            adj.setState(State.VISITING);
                            q.add(adj);
                        }
                    }
                }
                u.setState(State.VISITED);
            }
        }

        return false;
    }
}
