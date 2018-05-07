package com.orioles.helper_model;

import com.orioles.districtgeneration.Coordinate;
import com.orioles.districtgeneration.Edge;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Polygon {
	// edges.get(0) = bounding region; subsequent elements are holes
	private List<List<Edge>> edges;

	public Polygon(List<List<Edge>> edges) {
		this.edges = edges;
	}

	public List<List<Edge>> getEdges() {
		return edges;
	}

	public List<Edge> getAllEdges() {
		return edges.stream().flatMap(Collection::stream).collect(Collectors.toList());
	}

	public List<Coordinate> getCoordinates() {
		return edges.stream().flatMap(Collection::stream).map(Edge::getP1).collect(Collectors.toList());
	}
}
