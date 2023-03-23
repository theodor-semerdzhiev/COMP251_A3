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

        long[] res = new long[pieces.length];
        //for(Integer e: edges.keySet()) helper(e, edges, res, map,pieces);
        for(int i=0; i<pieces.length; i++) if(edges.containsKey(i)) helper(i, edges, res, map,pieces);

        for(Integer e : map.keySet()) System.out.println(e+" :"+Arrays.toString(map.get(e)));

        //we compute are final result array
        for(int i=0; i<res.length; i++) {
            if(pieces[i] != 0) {
                if (!map.containsKey(i)) {
                    res[i] += pieces[i];
                    continue;
                }
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
        //if we already computed the instructions for cur_node, then we return
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
            //by the weight of that edge
            for (int i=0; i<map.get(edges.get(cur_node).get(j)[0]).length ;i++) {
                map.get(cur_node)[i]+=map.get(edges.get(cur_node).get(j)[0])[i]*edges.get(cur_node).get(j)[1];
            }
        }
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

        System.out.println(Arrays.toString(num_pieces(pieces_3,instructions_3)));
    }


}