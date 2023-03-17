import java.util.*;


public class A3Q1 {

    static HashSet<ArrayList<Integer>> visited = new HashSet();
    static HashMap<ArrayList<Integer>, Integer> map = new HashMap<>();
    public static int find_exit(int time, String[][] jail) {

        for(int i=0; i < jail.length; i++) {
            for(int j=0; j <jail[i].length; j++) {
                if (jail[i][j].equals("S")){
                    int res=solution(jail,i,j);
                    if (res <= time) 	return res;
                    else				return -1;
                }
            }
        }
        return -1;
    }

    public static int solution(String[][] grid,int row, int column) {
        if(row == 0 || column == 0 || row == grid.length-1 || column == grid[row].length-1) return 0;
        ArrayList<Integer> arr= new ArrayList<>(2);
        arr.add(row);
        arr.add(column);
        if(visited.contains(arr)) return Integer.MAX_VALUE-1;
        if(map.containsKey(arr)) return map.get(arr);



        int val1=Integer.MAX_VALUE-1;
        int val2=Integer.MAX_VALUE-1;
        int val3=Integer.MAX_VALUE-1;
        int val4=Integer.MAX_VALUE-1;

        visited.add(arr);

        if(grid[row+1][column].equals("U") || grid[row+1][column].equals("0")) val1=solution(grid, row+1, column);
        if(grid[row-1][column].equals("D") || grid[row-1][column].equals("0")) val2=solution(grid, row-1, column);
        if(grid[row][column-1].equals("R") || grid[row][column-1].equals("0")) val3=solution(grid, row, column-1);
        if(grid[row][column+1].equals("L") || grid[row][column+1].equals("0")) val4=solution(grid, row, column+1);

        visited.remove(arr);

        map.put(arr,Math.min(val1,Math.min(val2,Math.min(val3,val4))));

        return 1+map.get(arr);
    }


    public static void main(String[] args) {
        String[][] test1=   {{"1","1","1","1"},
                            {"1","S","0","1"},
                           {"1","0","0","1"},
                          {"0","U","1","1"}};
        String[][] test6 = {{"1","1","1","1"},
                            {"0","1","S","1"},
                            {"1","L","0","1"},
                            {"1","R","0","1"},
                            {"1","U","D","1"}};
        String[][] test7= {{"1","1","D","1"},
                            {"1","S","L","1"},
                            {"0","R","1","1"}};

        System.out.println(find_exit(100,test7));
    }

}