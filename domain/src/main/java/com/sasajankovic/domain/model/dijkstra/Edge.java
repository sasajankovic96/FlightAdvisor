package com.sasajankovic.domain.model.dijkstra;

import com.sasajankovic.domain.entities.Money;
import com.sasajankovic.domain.entities.route.Route;
import lombok.Getter;

@Getter
public class Edge implements Comparable<Edge> {
    private final Route route;
    private final Vertex source;
    private final Vertex destination;
    private final Money cost;

    public Edge(Route route, Vertex source, Vertex destination) {
        this.route = route;
        this.source = source;
        this.destination = destination;
        this.cost = route.getPrice();
    }

    @Override
    public int compareTo(Edge edge) {
        return cost.compareTo(edge.getCost());
    }
}
