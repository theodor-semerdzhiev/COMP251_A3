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
        for(Integer e: edges.keySet()) helper(e, edges, res, map,pieces);

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

        //BASE CASE: reached a leaf
        //System.out.println(map.toString());
        int[] arr = new int[res.length];
        map.put(cur_node, arr);

        boolean isEnd=false;
        for (Integer[] edge : edges.get(cur_node)) {
            map.get(cur_node)[edge[0]]+=edge[1];
            //res[edge[0]]+=edge[1];
            if(!edges.containsKey(edge[0])) {
                map.get(cur_node)[edge[0]]=edge[1];
                //res[edge[0]]=edge[1];
            } else {
                helper(edge[0], edges, res, map,pieces);
                isEnd=true;
            }
        }
        if(!isEnd) return;
        //gets the edges
        for (Integer[] edge : edges.get(cur_node)) {
            if(!edges.containsKey(edge[0])) {
                continue;
            }
            for (int i=0; i<map.get(edge[0]).length ;i++) {
                map.get(cur_node)[i]+=map.get(edge[0])[i]*edge[1];
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

        System.out.println(Arrays.toString(num_pieces(pieces_4,instructions_4)));
    }


}