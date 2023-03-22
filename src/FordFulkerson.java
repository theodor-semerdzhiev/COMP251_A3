import java.util.*;
import java.io.File;

public class FordFulkerson {

    public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
        ArrayList<Integer> path = new ArrayList<Integer>();
        /* YOUR CODE GOES HERE*/
        HashSet<Integer> visited = new HashSet<>();
        DFS(source,destination,graph,path,visited);
        return path;
    }
    public static boolean DFS(Integer cur_node, Integer destination, WGraph graph, ArrayList<Integer> arr, HashSet<Integer> visited) {
        if(cur_node == destination) {
            arr.add(destination);
            return true;
        }

        visited.add(cur_node);

        for(Edge e: graph.getEdges()) {
            if(e.nodes[0] == cur_node) {
                arr.add(cur_node);
                if(DFS(e.nodes[1], destination, graph, arr,visited)) {

                    return true;
                }
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

        WGraph ResidualGraph = new WGraph(graph);


        answer += maxFlow + "\n" + graph.toString();
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
        ArrayList<Integer> path = FordFulkerson.pathDFS(0, 5, g);
        System.out.println(path.toString());


    }


    public static void main(String[] args){
        //String file = args[0];
//        String file = "ff2";
//        File f = new File(file);
//        WGraph g = new WGraph(file);

        test1();
//        System.out.println(fordfulkerson(g));
    }
}

