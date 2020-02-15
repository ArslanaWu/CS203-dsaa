import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Stack;

class GNode5{
    int index;
    ArrayList<GNode5> outEdges = new ArrayList<>();

    GNode5(int index){
        this.index = index;
    }
}

public class Ex5 {
    static ArrayList<GNode5> soldiers;
    static Queue<Integer> que;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0; i < t; i++){
            int n = input.nextInt();
            int m = input.nextInt();

            soldiers = new ArrayList<>();
            que = new PriorityQueue<>();

            for(int j = 0; j < n; j++){
                GNode5 tmp = new GNode5(j + 1);
                soldiers.add(tmp);

                que.add(j + 1);
            }

            for(int j = 0; j < m; j++){
                int u = input.nextInt();
                int v = input.nextInt();

                soldiers.get(v - 1).outEdges.add(soldiers.get(u - 1));
            }
            //build reverse graph and queue

            //print();

            lineUp(n);
            System.out.println();
        }
    }

    public static void lineUp(int amount){
        int[] colors = new int[amount];
        Stack<GNode5> stack = new Stack<>();
        GNode5 v;

        v = soldiers.get(que.peek() - 1);
        stack.add(v);
        colors[v.index - 1] = 1;
        que.remove(v.index);

        while(!que.isEmpty() || !stack.isEmpty()){
            while(!stack.isEmpty()){
                v = stack.peek();

                GNode5 u = haveWhiteOutNeighbor(colors,v);
                if(u != null){
                    stack.push(u);
                    colors[u.index - 1] = 1;
                    que.remove(u.index);
                }else{
                    stack.pop();
                    colors[v.index - 1] = 2;
                    System.out.print(v.index);

                    if(!que.isEmpty() || !stack.isEmpty()){
                        System.out.print(" ");
                    }
                }
            }

            if(!que.isEmpty()){
                v = soldiers.get(que.peek() - 1);
                stack.add(v);
                colors[v.index - 1] = 1;
                que.remove(v.index);
            }
        }
        //dfs
    }

    public static GNode5 haveWhiteOutNeighbor(int[] colors, GNode5 v){
        GNode5 u = null;

        for(int i = 0; i < v.outEdges.size(); i++){
            GNode5 tmp = v.outEdges.get(i);
            if(colors[tmp.index - 1] == 0){
                u = tmp;
                break;
            }
        }
        //initialization

        if(u != null){
            for(int i = 0; i < v.outEdges.size(); i++){
                GNode5 tmp = v.outEdges.get(i);
                if(u.index > tmp.index && colors[tmp.index - 1] == 0){
                    u = tmp;
                }
            }
        }
        //find min

        return u;
    }

    public static void print(){
        for(int i = 0; i < soldiers.size(); i++){
            System.out.printf("%d: ",soldiers.get(i).index);
            for(int j = 0; j < soldiers.get(i).outEdges.size(); j++){
                System.out.print(soldiers.get(i).outEdges.get(j).index + " ");
            }
            System.out.println();
        }
    }
}
