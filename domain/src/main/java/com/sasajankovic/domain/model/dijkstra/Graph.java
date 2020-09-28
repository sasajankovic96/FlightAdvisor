package com.sasajankovic.domain.model.dijkstra;

import com.sasajankovic.domain.entities.Money;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private final List<Vertex> vertices;
    private final List<Edge> edges;

    private Set<Vertex> settledNodes;
    private Set<Vertex> unsettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Money> distance;

    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = new ArrayList<>(vertices);
        this.edges = new ArrayList<>(edges);
    }

    public List<Edge> findShortestPath(Vertex source, Vertex destination) {
        settledNodes = new HashSet<>();
        unsettledNodes = new HashSet<>();
        predecessors = new HashMap<>();
        distance = new HashMap<>();

        distance.put(source, Money.ZERO);
        unsettledNodes.add(source);
        while (unsettledNodes.size() > 0) {
            Vertex currentNode = getMinUnsettledNode();
            settledNodes.add(currentNode);
            unsettledNodes.remove(currentNode);
            findMinimumCost(currentNode);
        }

        LinkedList<Vertex> path = getPath(destination);

        return getEdgesFromPath(path);
    }

    private Vertex getMinUnsettledNode() {
        return unsettledNodes.stream()
                .sorted(Comparator.comparing(this::getMinCost))
                .findFirst()
                .get();
    }

    private Money getMinCost(Vertex destination) {
        return distance.getOrDefault(
                destination, Money.create(BigDecimal.valueOf(Integer.MAX_VALUE)));
    }

    private void findMinimumCost(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbours(node);
        for (Vertex target : adjacentNodes) {
            if (getMinCost(target).compareTo(getMinCost(node).add(getCost(node, target))) > 0) {
                distance.put(target, getMinCost(node).add(getCost(node, target)));
                predecessors.put(target, node);
                unsettledNodes.add(target);
            }
        }
    }

    private List<Vertex> getNeighbours(Vertex node) {
        return edges.stream()
                .filter(edge -> edge.getSource().equals(node) && !isSettled(edge.getDestination()))
                .map(edge -> edge.getDestination())
                .collect(Collectors.toList());
    }

    private boolean isSettled(Vertex node) {
        return settledNodes.contains(node);
    }

    private Money getCost(Vertex source, Vertex destination) {
        return edges.stream()
                .filter(
                        edge ->
                                edge.getSource().equals(source)
                                        && edge.getDestination().equals(destination))
                .map(Edge::getCost)
                .findFirst()
                .get();
    }

    private LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex step = target;

        if (predecessors.get(step) == null) {
            return new LinkedList<>();
        }

        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }

    private List<Edge> getEdgesFromPath(List<Vertex> path) {
        List<Edge> edgesShortestPath = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            int index = i;
            Edge edge =
                    edges.stream()
                            .filter(
                                    e ->
                                            e.getSource().equals(path.get(index))
                                                    && e.getDestination()
                                                            .equals(path.get(index + 1)))
                            .findFirst()
                            .get();
            edgesShortestPath.add(edge);
        }

        return edgesShortestPath;
    }
}
