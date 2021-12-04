# Feedback-Vertex-Set

## Geometric graph : 
A geometric graph in a 2D plane is defined by a set of points in the plane called vertices, and a
threshold on the distance between the points : there is an edge between two vertices if and only if the Euclidean distance
between the two vertices is smaller than this threshold.


## Feedback Vertex Set : 
Given a graph G = (V, E), the minimum Feedback Vertex Set (MinFVS) problem consists
in computing a minimum sized subset of vertices F ⊆ V such that the subgraph G[V \ F] induced by V \ F in G is
cycleless, that is, G[V \ F] is a forest.

The overall goal is to propose a heuristics to MinFVS problem in a 2D geometric graph, without budget. In the GUI
canvas, V is given by the input file input.points and (after parsing,) the variable named points. The distance
threshold under which an edge exists is given in the variable named edgeThreshold.
