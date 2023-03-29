import java.util.*;


public class A3Q1 {
    public static int find_exit(int time, String[][] jail) {
        HashSet<Integer> visited = new HashSet();
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=0; i < jail.length; i++) {
            for(int j=jail[i].length-1; j>=0; j--) {
                if (jail[i][j].equals("S")){
                    int res=solution(jail,i,j,0,time, visited, map);
                    if (res <= time) 	return res;
                    else				return -1;
                }
            }
        }
        return -1;
    }

    public static int solution(String[][] grid,int row, int column, int time, int maxtime,HashSet<Integer> visited,HashMap<Integer, Integer> map) {
        if  (row == -1 ||
                column == -1 ||
                row == grid.length ||
                column == grid[row].length)
            return Integer.MAX_VALUE-1;

        if(visited.contains(hash(grid,row,column))) return Integer.MAX_VALUE-1;
        if(time > maxtime) return Integer.MAX_VALUE-1;

        if( row == 0 ||
            column == 0 ||
            row == grid.length-1 ||
            column == grid[row].length-1)
            return 0;

        if(map.containsKey(hash(grid,row,column))) return map.get(hash(grid,row,column));

        int val1=Integer.MAX_VALUE-1;
        int val2=Integer.MAX_VALUE-1;
        int val3=Integer.MAX_VALUE-1;
        int val4=Integer.MAX_VALUE-1;

        visited.add(hash(grid,row,column));

        if(new String("U0").contains(grid[row+1][column])) val1=solution(grid, row+1, column,time+1, maxtime,visited,map);
        if(new String("D0").contains(grid[row-1][column])) val2=solution(grid, row-1, column,time+1, maxtime,visited,map);
        if(new String("R0").contains(grid[row][column-1])) val3=solution(grid, row, column-1,time+1, maxtime,visited,map);
        if(new String("L0").contains(grid[row][column+1])) val4=solution(grid, row, column+1,time+1, maxtime,visited,map);

        visited.remove(hash(grid,row,column));

        map.put(hash(grid,row,column),1+Math.min(val1,Math.min(val2,Math.min(val3,val4))));

        return map.get(hash(grid,row,column));
    }
    public static int hash( String[][] grid,int row, int column) {
        return grid.length * row + column;
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

        String[][] custom_test =   {{"1","L","1","1","1","1","1"},
                                    {"1","0","1","0","0","0","1"},
                                    {"1","0","R","0","1","0","1"},
                                    {"1","S","1","0","1","0","1"},
                                    {"1","0","1","0","1","0","1"},
                                    {"1","0","1","0","1","0","1"},
                                    {"1","0","0","0","1","0","1"},
                                    {"1","0","1","0","1","0","1"},
                                    {"1","0","1","0","1","0","1"},
                                    {"1","1","1","1","1","1","1"}};


        String[][] custom_test1=   {{"1","1","1","1"},
                                    {"1","S","0","1"},
                                    {"1","0","1","1"},
                                    {"0","L","1","1"}};


        String[][] custom_test2=   {{"1","1","1","1","1","1","1","1","1","1","1"},
                                    {"1","S","0","0","0","0","0","0","0","0","1"},
                                    {"1","0","0","0","1","1","1","1","0","0","1"},
                                    {"1","0","0","0","L","0","0","1","0","0","1"},
                                    {"1","0","0","0","1","1","0","1","1","1","1"},
                                    {"1","1","1","1","1","1","0","1","1","1","1"}};

        System.out.println(find_exit(100,custom_test2));
    }

}