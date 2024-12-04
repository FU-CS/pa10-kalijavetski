package pa10;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;


public class GraphDirect{
    private List<Edge> graph;

    public GraphDirect(){
        this.graph = new ArrayList<>();
    }

    public void addEdge(int v, int w){
        Edge edge = new Edge(v,w);
        this.graph.add(edge);
    }

    public String topologicalSort(){
        String sorted = "";
        Set<Integer> visited = new HashSet<>();
        Set<Integer> vertices = new HashSet<>();
        for (Edge edge:this.graph){
            vertices.add(edge.source);
            vertices.add(edge.destination);
        }
        for (int vertex:vertices){
            if (!visited.contains(vertex)){
                sorted = DFS(vertex,sorted,visited);
            }
        }
        return sorted;
    }

    private String DFS(int vertex, String sorted, Set<Integer> visited){
        visited.add(vertex);
        sorted = sorted + vertex + " ";
        for (Edge edge:this.graph){
            if (edge.source == vertex){
                int neighbor = edge.destination;
                if (!visited.contains(neighbor)){
                    sorted = DFS(neighbor,sorted,visited);
                }
            }
        }
        return sorted;
    }


    public String kahn(){
        String sorted = "";
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> no_incoming = new LinkedList<>();
        Set<Integer> vertices = new HashSet<>();
        for (Edge edge:this.graph){
            vertices.add(edge.source);
            vertices.add(edge.destination);
        }
        int[] counts = new int[vertices.size()];
        for (Edge edge:this.graph){
            counts[edge.destination] += 1;
        }
        for (int vertex:vertices){
            if (counts[vertex]==0){
                no_incoming.add(vertex);
            }
        }
        while (!no_incoming.isEmpty()){
            int curr = no_incoming.poll();
            if (!visited.contains(curr)){
                sorted = sorted + curr + " ";
                visited.add(curr);
            }
            for (Edge edge:this.graph){
                if (edge.source == curr){
                    counts[edge.destination] = counts[edge.destination]-1;
                    if (counts[edge.destination]==0){
                        no_incoming.add(edge.destination);
                    }
                }
            }
        }
        return sorted;
    }

    private static class Edge{
        int source;
        int destination;
    
        public Edge(int source, int destination) {
            this.source = source;
            this.destination = destination;
        }
    }
}
