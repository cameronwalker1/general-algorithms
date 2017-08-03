import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;

/**
 * Your implementation of 4 different graph algorithms.
 *
 * @author Nick Liccini
 * @version 1.0
 */
public class GraphAlgs {

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex. You will be
     * modifying the empty list passed in to contain the vertices in
     * visited order. The start vertex should be at the beginning of the list
     * and the last vertex visited should be at the end.  (You may assume the
     * list is empty in the beginning).
     *
     * This method should utilize the adjacency matrix represented graph.
     *
     * When deciding which neighbors to visit next from a vertex, visit starting
     * with the vertex at index 0 to the vertex at index |V| - 1. Failure to do
     * so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.Map},
     * {@code java.util.List}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph in an adjacency matrix format to search
     * @param dfsList the list of visited vertices in order. This list will be
     * empty initially. You will be adding to this list as you perform dfs.
     * @return true if the graph is connected (you were able to reach every
     * vertex and edge from {@code start}), false otherwise
     */
    public static <T> boolean depthFirstSearch(Vertex<T> start,
                                            GraphAdjMatrix<T> graph,
                                            List<Vertex<T>> dfsList) {
        if (start == null || graph == null || dfsList == null
                || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("One or more of your inputs"
                    + " is invalid. Please check if any are null and if"
                    + " the vertex " + start + " is in the graph");
        }
        Integer[][] matrix = graph.getAdjMatrix();
        List<Vertex<T>> vertices = graph.getVertices();
        Set<Vertex<T>> visited = new HashSet<>();
        dfsHelper(start, visited, dfsList, matrix, vertices);
        return dfsList.size() == vertices.size();
    }

    /**
     * Private recursive helper method to perform depth first search (dfs)
     * using an adjacency matrix
     *
     * @param v the vertex to begin dfs on
     * @param visited the helper set of visited vertices in order
     * @param dfsList the list of visited vertices in order
     * @param adjMatrix the adjacency matrix that represents the graph
     * @param vertices the list of all vertices in the graph
     * @param <T> the generic typing of the data
     */
    private static <T> void dfsHelper(Vertex<T> v, Set<Vertex<T>> visited,
                                      List<Vertex<T>> dfsList,
                                      Integer[][] adjMatrix,
                                         List<Vertex<T>> vertices) {
        visited.add(v);
        dfsList.add(v);
        for (Vertex<T> u : vertices) {
            if (adjMatrix[v.getId()][u.getId()] != null
                    && !visited.contains(u)) {
                dfsHelper(u, visited, dfsList, adjMatrix, vertices);
            }
        }
    }

    /**
     * Find the single source shortest distance between the start vertex and
     * all vertices given a weighted graph using Dijkstra's shortest path
     * algorithm.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists. You may assume that going from a vertex to itself
     * has a shortest distance of 0.
     *
     * This method should utilize the adjacency list represented graph.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces.
     *
     * You should implement CLASSIC Dijkstra's, which is the version of the
     * algorithm that terminates once you've "visited" all of the nodes.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if start doesn't exist in the graph.
     * @throws IllegalStateException if any of the edges are negative
     * @param <T> the generic typing of the data
     * @param start index representing which vertex to start at (source)
     * @param graph the Graph we are searching using an adjacency List
     * @return a map of the shortest distances from start to every other node
     *         in the graph
     */
    public static <T> Map<Vertex<T>, Integer> shortPathDijk(Vertex<T> start,
                                                      GraphAdjList<T> graph) {
        if (start == null || graph == null
                || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("One or more of your inputs"
                    + " is invalid. Please check if any are null and if"
                    + " the vertex " + start + " is in the graph");
        }
        for (Edge<T> e : graph.getEdges()) {
            if (e.getWeight() < 0) {
                throw new IllegalStateException(e + " has an invalid weight.");
            }
        }
        Set<Vertex<T>> vertices = graph.getVertices();
        Map<Vertex<T>, List<Edge<T>>> adjList = graph.getAdjList();
        PriorityQueue<Edge<T>> edges = new PriorityQueue<>(adjList.get(start));
        Set<Vertex<T>> unvisited = new HashSet<>(vertices);
        Map<Vertex<T>, Integer> paths = new HashMap<>();
        for (Vertex<T> v : vertices) {
            paths.put(v, Integer.MAX_VALUE);
        }
        paths.put(start, 0);
        unvisited.remove(start);
        while (!unvisited.isEmpty() && !edges.isEmpty()) {
            Edge<T> e = edges.poll();
            Vertex<T> u = e.getU();
            Vertex<T> v = e.getV();
            while (v != null && !unvisited.contains(v)) {
                e = edges.poll();
                u = e.getU();
                v = e.getV();
            }
            if (u != null) {
                Integer dist = paths.get(u) + e.getWeight();
                if (dist < paths.get(v)) {
                    paths.put(v, dist);
                }
                unvisited.remove(v);
                for (Edge<T> edge : adjList.get(v)) {
                    dist = paths.get(u) + edge.getWeight();
                    edges.add(new Edge<>(v, edge.getV(), dist));
                }
            }
        }
        return paths;
    }

    /**
     * Run Prim's algorithm on the given graph and return the MST/MSF
     * in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return a minimal spanning forest (MSF).
     *
     * This method should utilize the adjacency list represented graph.
     *
     * A minimal spanning forest (MSF) is just a generalized version of the MST
     * for disconnected graphs. After the MST algorithm finishes, just check to
     * see if there are still some vertices that are not connected to the
     * MST/MSF. If all vertices have been visited, you are done. If not, run
     * the algorithm again on an unvisited vertex.
     *
     * You may assume that all of the edge weights are unique (THIS MEANS THAT
     * THE MST/MSF IS UNIQUE FOR THE GRAPH, REGARDLESS OF STARTING VERTEX!!)
     * Although, if your algorithm works correctly, it should work even if the
     * MST/MSF is not unique, this is just for testing purposes.
     *
     * You should not allow for any self-loops in the MST/MSF. Additionally,
     * you may assume that the graph is undirected.
     *
     * You may import/use {@code java.util.PriorityQueue} and
     * {@code java.util.Set} and any class that
     * implements the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the Graph we are searching using an adjacency list
     * @return the MST/MSF of the graph
     */
    public static <T> Set<Edge<T>> mstPrim(GraphAdjList<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Your input graph is null.");
        }
        Set<Vertex<T>> vertices = graph.getVertices();
        Set<Vertex<T>> unvisited = new HashSet<>(vertices);
        Map<Vertex<T>, List<Edge<T>>> adjList = graph.getAdjList();
        Set<Edge<T>> msf = new HashSet<>();
        if (vertices.iterator().hasNext()) {
            Vertex<T> start = vertices.iterator().next();
            PriorityQueue<Edge<T>> edges =
                    new PriorityQueue<>(adjList.get(start));
            while (!unvisited.isEmpty() && !edges.isEmpty()) {
                Edge<T> e = edges.poll();
                Vertex<T> v = e.getV();
                if (!v.equals(e.getU())) {
                    while (v != null && !unvisited.contains(v)) {
                        e = edges.poll();
                        v = e.getV();
                    }
                }
                msf.add(e);
                // Add edge conjugate since this is an undirected graph
                msf.add(new Edge<>(e.getV(), e.getU(), e.getWeight()));
                edges.addAll(adjList.get(v));
                unvisited.remove(v);
            }
        }
        return msf;
    }

    /**
     * Run Kruskal's algorithm on the given graph and return the MST/MSF
     * in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return a minimal spanning forest (MSF).
     *
     * This method should utilize the adjacency list represented graph.
     *
     * A minimal spanning forest (MSF) is just a generalized version of the MST
     * for disconnected graphs. Unlike Prim's algorithm, Kruskal's algorithm
     * will naturally return a MSF if the graph is disconnected.
     *
     * You may assume that all of the edge weights are unique (THIS MEANS THAT
     * THE MST/MSF IS UNIQUE FOR THE GRAPH.) Although, if your algorithm works
     * correctly, it should work even if the MST/MSF is not unique, this is
     * just for testing purposes.
     *
     * You should not allow for any self-loops in the MST/MSF. Additionally,
     * you may assume that the graph is undirected.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you.  A Disjoint Set will keep track of which vertices are
     * connected to each other by the edges you've chosen for your MST/MSF.
     * Without a Disjoint Set, it is possible for Kruskal's to omit edges that
     * should be in the final MST/MSF.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the Graph we are searching using an adjacency list
     * @return the MST/MSF of the graph
     */
    public static <T> Set<Edge<T>> mstKruskal(GraphAdjList<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Your input graph is null.");
        }
        PriorityQueue<Edge<T>> edges = new PriorityQueue<>(graph.getEdges());
        DisjointSet<Vertex<T>> set = new DisjointSet<>(graph.getVertices());
        Set<Edge<T>> msf = new HashSet<>();
        while (!edges.isEmpty()) {
            Edge<T> e = edges.poll();
            Vertex<T> u = e.getU();
            Vertex<T> v = e.getV();
            if (!set.find(u).equals(set.find(v))) {
                set.union(u, v);
                msf.add(e);
                // Since the graph is undirected, add the conjugate edge
                msf.add(new Edge<>(v, u, e.getWeight()));
            }
        }
        return msf;
    }
}