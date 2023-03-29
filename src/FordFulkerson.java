import java.util.*;
import java.io.File;

public class FordFulkerson {

    public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
        ArrayList<Integer> path = new ArrayList<Integer>();
        /* YOUR CODE GOES HERE*/
        HashSet<Integer> visited = new HashSet<>();
        if(DFS(source,destination,graph,path,visited) == false) return null;
        return path;
    }
    public static boolean DFS(Integer cur_node, Integer destination, WGraph graph, ArrayList<Integer> arr, HashSet<Integer> visited) {
        if(visited.contains(cur_node)) return false;
        if(cur_node == destination) {
            arr.add(destination);
            return true;
        }
        visited.add(cur_node);
        for(Edge e: graph.getEdges()) {
            if(e.nodes[0] == cur_node && graph.getEdge(e.nodes[0],e.nodes[1]).weight > 0) {
                arr.add(cur_node);
                if(DFS(e.nodes[1], destination, graph, arr,visited)) return true;
                arr.remove(arr.size()-1);
            }
        }
        visited.remove(cur_node);
        return false;
    }



    public static String fordfulkerson( WGraph graph){
        String answer="";
        int maxFlow = 0;

        /* YOUR CODE GOES HERE		*/

        //WGraph ResidualGraph = new WGraph(graph);
        ArrayList<Integer> path;
        WGraph ResidualGraph = new WGraph(graph);
        for(Edge e: ResidualGraph.getEdges()) {
            e.weight=0;
        }
        path=pathDFS(graph.getSource(), graph.getDestination(), graph);
        while(path != null) {
            int pathflow=Integer.MAX_VALUE;
            for(int i =0 ; i< path.size()-1; i++) {
                pathflow=Math.min(pathflow, graph.getEdge(path.get(i),path.get(i+1)).weight);
            }
            for(int i =0 ; i< path.size()-1; i++) {
                graph.getEdge(path.get(i),path.get(i+1)).weight -= pathflow;
                Edge reverse = graph.getEdge(path.get(i+1),path.get(i));
                if (reverse != null)  reverse.weight+=pathflow;
                ResidualGraph.getEdge(path.get(i),path.get(i+1)).weight += pathflow;
            }
            maxFlow+=pathflow;

            path=pathDFS(graph.getSource(), graph.getDestination(), graph);
        }

        answer += maxFlow + "\n" + ResidualGraph.toString();
        return answer;
    }


    public static void test1() {

        WGraph g = new WGraph();
        g.setSource(0);
        g.setDestination(5);
        Edge[] edges = new Edge[] {
                new Edge(0, 1, 10),
                new Edge(0, 2, 5),
                new Edge(2, 4, 5),
                new Edge(1, 3, 10),
                new Edge(1, 6, 5),
                new Edge(3, 5, 10),
                new Edge(2, 5, 5)
        };
        Arrays.stream(edges).forEach(e->g.addEdge(e));
        ArrayList<Integer> path = FordFulkerson.pathDFS(0, 6, g);
        System.out.println(path.toString());
    }

    public static void test2() {
        WGraph g = new WGraph();
        g.setSource(0);
        g.setDestination(9);
        Edge[] edges = new Edge[] {
                new Edge(0, 1, 10),
                new Edge(0, 2, 5),
                new Edge(2, 3, 5),
                new Edge(1, 3, 10),
                new Edge(3, 4, 5),
                new Edge(4, 5, 10),
                new Edge(4, 6, 5),
                new Edge(6, 7, 5),
                new Edge(6, 8, 10),
                new Edge(8, 9, 10),
        };
        Arrays.stream(edges).forEach(e->g.addEdge(e));
        String result = FordFulkerson.fordfulkerson(g);
        System.out.println(result);
    }


    public static void main(String[] args){
        String file = "C:\\Users\\Theodor Semerdzhiev\\intelliJ\\COMP251_A3\\src\\ff2.txt";
        //File f = new File();
        //WGraph g = new WGraph(args[0]);

        test2();
        //System.out.println(fordfulkerson(g));
    }
}

