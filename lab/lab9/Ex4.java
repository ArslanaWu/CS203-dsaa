import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Ex4 {
    static int[][] graph;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0; i < t; i++){
            int n = input.nextInt();//cities
            int m = input.nextInt();//roads

            graph = new int[n][n];

            for(int j = 0; j < m; j++){
                int u = input.nextInt();
                int v = input.nextInt();

                graph[u - 1][v - 1] = 1;
                graph[v - 1][u - 1] = 1;
            }
            //build graph

            //print();

            int[] chosen = choose();
            int amount = 0;

            for(int j = 0; j < n; j++){
                if(chosen[j] == 1){
                    amount++;
                }
            }
            System.out.println(amount);

            for(int j = 0; j < n; j++){
                if(chosen[j] == 1){
                    System.out.print(j + 1 + " ");
                }
            }
            System.out.println();
        }
    }

    public static int[] choose(){
        //bfs

        int cities = graph.length;
        int[] colors = new int[cities];
        //0 white(not operated)
        //1 yellow(in the queue)
        //2 red(is visited)
        int[] chosen = new int[cities];
        //0 not chosen
        //1 chosen
        int chosenAmount = 0;
        int v;

        Queue<Integer> que = new LinkedList<>();
        que.offer(0);
        colors[0] = 1;

        while(!que.isEmpty()){
            v = que.poll();
            //index of first vertex in queue

            for(int i = 0; i < cities; i++){
                if(graph[v][i] == 1 && colors[i] == 0){
                    que.offer(i);
                    colors[i] = 1;

                    if(chosen[v] == 0){
                        chosen[i] = 1;
                        chosenAmount++;
                    }
                }
            }
            //find v's out-neighbor and add them into queue

            colors[v] = 2;
        }

        if(chosenAmount * 2 > cities){
            for(int i = 0; i < cities; i++){
                if(chosen[i] == 0){
                    chosen[i] = 1;
                }else{
                    chosen[i] = 0;
                }
            }
        }

        return chosen;
    }

    public static void print(){
        int k = graph.length;

        for(int i = 0; i < k; i++){
            for(int j = 0; j < k; j++){
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
    }
}
