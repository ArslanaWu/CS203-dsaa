import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Ex2_re {
    static int[][] monsters;
    static int[][] graph;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0; i < t; i++){
            int n = input.nextInt();
            int m = input.nextInt();
            int k = input.nextInt();

            graph = new int[k + 2][k + 2];
            monsters = new int[3][k];

            for(int j = 0; j < k; j++){
                monsters[0][j] = input.nextInt();//x
                monsters[1][j] = input.nextInt();//y
                monsters[2][j] = input.nextInt();//s
            }

            adjacencyMatrix(monsters,m,n);

            if(!canReachLastNode()){
                System.out.println("Yes");
            }else{
                System.out.println("No");
            }
        }
    }

    public static void adjacencyMatrix(int[][] monsters,int m,int n){
        int k = monsters[0].length;

        for(int i = 1; i < k + 1; i++){
            for(int j = 1; j < k + 1; j++){
                if(i == j){
                    continue;
                }
                if(haveEdge(i - 1,j - 1)){
                    graph[i][j] = 1;
                }
            }
        }
        //edge ij

        for(int i = 1; i < k + 1; i++){
            if(haveEdge(0,i - 1,m,n)){
                graph[0][i] = 1;
                graph[i][0] = 1;
            }
        }
        //edge 0i

        for(int i = 1; i < k + 1; i++){
            if(haveEdge(1,i - 1,m,n)){
                graph[k + 1][i] = 1;
                graph[i][k + 1] = 1;
            }
        }
        //edge (k+1)i
    }

    public static boolean haveEdge(int mon1,int mon2){
        int x1 = monsters[0][mon1];
        int y1 = monsters[1][mon1];
        int s1 = monsters[2][mon1];

        int x2 = monsters[0][mon2];
        int y2 = monsters[1][mon2];
        int s2 = monsters[2][mon2];

        double distance = Math.pow(x2 - x1,2) + Math.pow(y2 - y1,2);
        //distance = Math.sqrt(distance);

        return distance <= (s1 + s2) * (s1 + s2);
    }

    public static boolean haveEdge(int kind,int mon,int m,int n){
        int x = monsters[0][mon];
        int y = monsters[1][mon];
        int s = monsters[2][mon];

        if(kind == 0){
            //edge 0i
            if(s - x >= 0 || s + y >= m){
                return true;
            }else{
                return false;
            }
        }else{
            //edge (k+1)i
            if(s - y >= 0 || s + x >= n){
                return true;
            }else{
                return false;
            }
        }
    }

    public static boolean canReachLastNode(){
        //bfs
        int nodes = graph.length;
        int[] colors  = new int[nodes];
        int v;
        //0 white(not operated)
        //1 yellow(in the queue)
        //2 red(is visited)

        Queue<Integer> que = new LinkedList<>();
        que.offer(0);
        colors[0] = 1;

        while(!que.isEmpty()){
            v = que.poll();
            //index of first vertex in queue

            for(int i = 0; i < nodes; i++){
                if(graph[v][i] == 1 && colors[i] == 0){
                    que.offer(i);
                    colors[i] = 1;
                }
            }
            //find v's out-neighbor and add them into queue

            colors[v] = 2;
        }

        if(colors[nodes - 1] == 0){
            return false;
        }else{
            return true;
        }
    }
}
