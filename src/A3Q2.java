
import java.util.*;

public class A3Q2 {
    public static long[] num_pieces(long[] pieces, int[][] instructions) {
        HashMap<Integer,ArrayList<Integer[]>> edges = new HashMap<>();
        HashMap<Integer, int[]> map = new HashMap<>();

        //creates a hashmap for out edges for a fast lookup
        for(int i=0 ; i< instructions.length; i++) {
            if(edges.containsKey(instructions[i][1])){
                Integer[] arr = {instructions[i][0], instructions[i][2]};
                edges.get(instructions[i][1]).add(arr);
            } else {
                ArrayList<Integer[]> arr = new ArrayList<>();
                Integer[] key_pair = {instructions[i][0], instructions[i][2]};
                arr.add(key_pair);
                edges.put(instructions[i][1], arr);
            }
        }
        //creates result (return) array
        long[] res = new long[pieces.length];
        //for(Integer e: edges.keySet()) helper(e, edges, res, map,pieces);
        //runs helper if corresponding node i as any edges going out of it (hence if its in the edges hashmap)
        //if not, its a fundamental element
        for(int i=0; i<pieces.length; i++) if(edges.containsKey(i)) helper(i, edges, res, map,pieces);

        //for debugging purposes
        for(Integer e : map.keySet()) System.out.println(e+" :"+Arrays.toString(map.get(e)));

        //we compute are final result array
        for(int i=0; i<res.length; i++) {
            if(pieces[i] != 0) {
                //if fundamental element
                if (!map.containsKey(i)) {
                    res[i] += pieces[i];
                    continue;
                }
                //else we iterate through required nodes to create node i
                for (int j = 0; j < map.get(i).length; j++) {
                    if(i==j) {
                        res[j]+=pieces[j];
                    }
                    res[j] += map.get(i)[j] * pieces[i];
                }
            }
        }


        return res;
    }

    public static void helper(int cur_node,HashMap<Integer,ArrayList<Integer[]>> edges, long[] res, HashMap<Integer, int[]> map, long[] pieces) {
        //if we already computed the instructions (nodes needed to create that node) for cur_node, then we return
        //this also mean all of its children have already been computed
        if(map.containsKey(cur_node)) return;

        //insert cur_node into the map HashMap
        int[] arr = new int[res.length];
        map.put(cur_node, arr);
        //iterate through every out going edge of cur_node
        for(int j=0; j < edges.get(cur_node).size(); j++) {
            //next node is not a leaf (i.e its in the edges set (therefor it has outgoing edges), we have a recursive call
            if(edges.containsKey(edges.get(cur_node).get(j)[0])) {
                map.get(cur_node)[edges.get(cur_node).get(j)[0]] += edges.get(cur_node).get(j)[1];
                helper(edges.get(cur_node).get(j)[0], edges, res, map, pieces);
            }
        }

        //iterates through cur_nodes out going edge nodes
        for(int j=0; j < edges.get(cur_node).size(); j++) {
            //if a connected node is not in the edges set, then it's a leaf, therefor we add the weight of that edge into
            //the instructions array for cur_node stored in map (hashmap)
            if(!edges.containsKey(edges.get(cur_node).get(j)[0])) {
                map.get(cur_node)[edges.get(cur_node).get(j)[0]]=edges.get(cur_node).get(j)[1];
                continue;
            }
            //if its not a leaf then we have already computed the material needed to create that node
            //we iterate through that node's corresponding instructions array, we add those values to the cur_nodes' instructions array multiplied
            //by the weight of that edge (how many of those nodes children we need to create that node)
            for (int i=0; i<map.get(edges.get(cur_node).get(j)[0]).length ;i++) {
                map.get(cur_node)[i]+=map.get(edges.get(cur_node).get(j)[0])[i]*edges.get(cur_node).get(j)[1];
            }
        }
    }

    public static long[] num_pieces1(long[] pieces, int[][] instructions) {
        long[] res = new long[pieces.length];

        HashMap<Integer, ArrayList<int[]>> edges = new HashMap<>();
        HashSet<Integer> visited = new HashSet<Integer>();


        for(int i=0; i< instructions.length; i++) {
            if(!edges.containsKey(instructions[i][1])) {
                edges.put(instructions[i][1], new ArrayList<int[]>());
                edges.get(instructions[i][1]).add(new int[2]);
                edges.get(instructions[i][1]).get(edges.get(instructions[i][1]).size()-1)[0]=instructions[i][0];
                edges.get(instructions[i][1]).get(edges.get(instructions[i][1]).size()-1)[1]=instructions[i][2];
            } else {
                edges.get(instructions[i][1]).add(new int[2]);
                edges.get(instructions[i][1]).get(edges.get(instructions[i][1]).size()-1)[0]=instructions[i][0];
                edges.get(instructions[i][1]).get(edges.get(instructions[i][1]).size()-1)[1]=instructions[i][2];
            }
        }

        ArrayList<Integer> list = new ArrayList<>();
        for(int i =0 ;i<res.length; i++) topo_sort(list,edges,visited, i);

        //int[][] reqs = new int[pieces.length][pieces.length];
        HashMap<Integer,int[]> maps = new HashMap<>();

        for(int k = 0; k < list.size(); k++) {
            if(!edges.containsKey(list.get(k))) {
                res[list.get(k)]+=pieces[list.get(k)];
                continue;
            }
            maps.put(list.get(k),new int[pieces.length]);

            for(int n=0; n < edges.get(list.get(k)).size(); n++) {
            //for(int[] neighbor: edges.get(list.get(k))) {
                if(!edges.containsKey(edges.get(list.get(k)).get(n)[0])) {
                    maps.get(list.get(k))[edges.get(list.get(k)).get(n)[0]]=edges.get(list.get(k)).get(n)[1];
                    continue;
                }
                for(int i=0 ; i< maps.get(list.get(k)).length; i++) {
                    maps.get(list.get(k))[i]+=maps.get(edges.get(list.get(k)).get(n)[0])[i]*edges.get(list.get(k)).get(n)[1];

                }
                maps.get(list.get(k))[edges.get(list.get(k)).get(n)[0]]+=edges.get(list.get(k)).get(n)[1];
            }
            if(pieces[list.get(k)] == 0) continue;
            for(int i=0 ; i < pieces.length; i++) {

                if(i == list.get(k)) {
                    res[list.get(k)]+=pieces[list.get(k)];
                }
                res[i]+=maps.get(list.get(k))[i]*pieces[list.get(k)];
            }
        }


        for(Integer e: maps.keySet()) System.out.println("for " + e + " :"+ Arrays.toString(maps.get(e)));

        return res;
    }

    public static void topo_sort(ArrayList<Integer> list, HashMap<Integer,ArrayList<int[]>> edges, HashSet<Integer> visited, Integer cur_node) {
        if(visited.contains(cur_node)) return;
        visited.add(cur_node);
        if(!edges.containsKey(cur_node)) {
            list.add(cur_node);
            return;
        }
        for(int i=0; i< edges.get(cur_node).size(); i++) {
            if(edges.get(cur_node).size() == 0) continue;
            topo_sort(list,edges, visited,edges.get(cur_node).get(i)[0]);
        }

        list.add(cur_node);
    }
    public static void custom_testcase() {
        long[] pieces4 = {53,1,0,3,0,4,0,1,1,0,3,0,1,0};
        int[][] instructions4 ={
                {6,2,2},
                {2,13,2},
                {13,12,2},
                {13,7,1},
                {13,11,3},
                {7,3,1},
                {7,4,2},
                {3,1,3},
                {4,1,1},
                {11,5,2},
                {12,9,1},
                {12,10,3},
                {9,5,1},
                {10,5,2},
                {5,1,3},
                {5,8,2},
                {5,4,1}
        };
        long[] pieces = pieces4;
        int[][] inst = instructions4;
        System.out.println(Arrays.toString(num_pieces(pieces, inst)));
        System.out.println(Arrays.toString(num_pieces1(pieces, inst)));
    }
    public static void main(String[] args) {
        long[] pieces_1= {0,0,0,0,3};
        int[][] instructions_1 = {{0,1,3},{1,4,1},{2,4,1},{3,4,2}};

        long[] pieces_2= {0,0,0,0,0,3};
        int[][] instructions_2 = {{0,3,3},{1,4,3},{2,5,1},{3,5,1},{4,5,1}};

        long[] pieces_3= {0,0,2,2,3,1};
        int[][] instructions_3 = {{0,3,1},{1,3,2},{2,4,1},{3,4,3},{3,5,1}};

        long[] pieces_4= {0,0,2,2,3,1,2,3,1,4};
        int[][] instructions_4 = {{0,3,1},{1,3,2},{2,4,1},{3,4,3},{3,5,1},{3,8,3}};

        System.out.println(Arrays.toString(num_pieces1(pieces_4,instructions_4)));
        System.out.println(Arrays.toString(num_pieces(pieces_4,instructions_4)));
        custom_testcase();
    }


}