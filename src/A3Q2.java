import java.util.*;

public class A3Q2 {
    public static long[] num_pieces(long[] pieces, int[][] instructions) {
        HashMap<Integer,ArrayList<Integer[]>> edges = new HashMap<>();
        HashMap<Integer, int[]> map = new HashMap<>();
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
        if(map.containsKey(cur_node)) return;

        int[] arr = new int[res.length];
        map.put(cur_node, arr);
        for(int j=0; j < edges.get(cur_node).size(); j++) {
            map.get(cur_node)[edges.get(cur_node).get(j)[0]]+=edges.get(cur_node).get(j)[1];
            if(!edges.containsKey(edges.get(cur_node).get(j)[0])) map.get(cur_node)[edges.get(cur_node).get(j)[0]]=edges.get(cur_node).get(j)[1];
            else helper(edges.get(cur_node).get(j)[0], edges, res, map,pieces);
        }
        //gets the edges
        for(int j=0; j < edges.get(cur_node).size(); j++) {
            if(!edges.containsKey(edges.get(cur_node).get(j)[0])) continue;

            for (int i=0; i<map.get(edges.get(cur_node).get(j)[0]).length ;i++) {
                map.get(cur_node)[i]+=map.get(edges.get(cur_node).get(j)[0])[i]*edges.get(cur_node).get(j)[1];
            }
        }
    }
    public static void custome_testcase() {
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
        custome_testcase();
    }


}