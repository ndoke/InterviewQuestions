package com.algorithm.crackingthecodinginterview;

import com.algorithm.crackingthecodinginterview.chapter4.Graph;
import com.algorithm.crackingthecodinginterview.chapter4.Node;
import com.algorithm.crackingthecodinginterview.chapter4.RouteBetweenNodes;
import com.algorithm.crackingthecodinginterview.chapter4.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Chapter4Test {
    @Test
    public void hasPath() {
        Graph g = new Graph();
        Node n1 = new Node("n1", State.UNVISITED);
        Node n2 = new Node("n2", State.UNVISITED);
        Node n3 = new Node("n3", State.UNVISITED);
        Node n4 = new Node("n4", State.UNVISITED);
        Node n5 = new Node("n5", State.UNVISITED);
        Node n6 = new Node("n6", State.UNVISITED);
        Node n7 = new Node("n7", State.UNVISITED);
        n1.setAdjacent(List.of(n2, n3, n4));
        n2.setAdjacent(List.of(n3, n5));
        n3.setAdjacent(List.of(n6));
        g.setNodes(List.of(n1, n2, n3, n4, n5, n6, n7));
        RouteBetweenNodes routeBetweenNodes = new RouteBetweenNodes();
        Assertions.assertTrue(routeBetweenNodes.hasPath(g, n1, n6));
        Assertions.assertFalse(routeBetweenNodes.hasPath(g, n1, n7));
    }
}
