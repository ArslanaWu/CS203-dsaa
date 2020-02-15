//确定哪些是点哪些是边
//有边代表过不去
//大于欧几里得距离：si + sj > mi mj间的距离，则两怪兽间有边
//定义两个边界节点1 10 10 1，判断怪兽的节点能不能覆盖边（根据坐标判断），能覆盖则有边
//如果两边界节点之间有边，则一定不能拿到剑
//根据图建立BFS tree，如果两边界节点可达，则不能拿到剑
//详细图解见相册

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;

class GraphNode1{
    int order;
    int color = 0;//white 0  yellow 1  red 2
    ArrayList<GraphNode1> sons = new ArrayList<>();

    GraphNode1(int order){
        this.order = order;
    }

}

public class Ex2 {
    //static GraphNode1[] graph;
    static int[][] graph;
    static int[][] monsters;

    static GraphNode1 rootBFS;
    static GraphNode1 rootQueue;

    static boolean reach;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0; i < t; i++){
            rootBFS = null;
            rootQueue = null;
            reach = false;

            int n = input.nextInt();
            int m = input.nextInt();
            int k = input.nextInt();

            //graph = new GraphNode1[k + 2];
            graph = new int[k + 2][k + 2];
            monsters = new int[3][k];

            for(int j = 0; j < k; j++){
                monsters[0][j] = input.nextInt();//x
                monsters[1][j] = input.nextInt();//y
                monsters[2][j] = input.nextInt();//s
            }

            //adjacencyList(monsters,m,n);
            adjacencyMatrix(monsters,m,n);

            //buildBFS(m,n);

            print();

            //canReach(rootBFS,k + 1);

            if(!reach){
                System.out.println("Yes");
            }else{
                System.out.println("No");
            }

        }
    }

//    public static void adjacencyList(int[][] monsters,int m,int n){
//        int k = monsters[0].length;
//
//        for(int i = 0; i < k + 2; i++){
//            graph[i] = new GraphNode1(i);
//        }
//        //create nodes
//
//        for(int i = 1; i < k + 1; i++){
//            if(haveEdge(i - 1,m,n)){
//                graph[0].sons.add(graph[i]);
//                graph[i].sons.add(graph[0]);
//            }
//        }
//        //edge 0i
//
//        for(int i = 1; i < k + 1; i++){
//            if(haveEdge(i - 1,m,n)){
//                graph[k + 1].sons.add(graph[i]);
//                graph[i].sons.add(graph[k + 1]);
//            }
//        }
//        //edge k+1i
//
//        for(int i = 1; i < k + 1; i++){
//            for(int j = 1; j < k + 1; j++){
//                if(i == j){
//                    continue;
//                }
//                if(haveEdge(i - 1,j - 1)){
//                    graph[i].sons.add(graph[j]);
//                }
//            }
//        }
//        //edge ij
//    }

    public static boolean haveEdge(int mon1,int mon2){
        int x1 = monsters[0][mon1];
        int y1 = monsters[1][mon1];
        int s1 = monsters[2][mon1];

        int x2 = monsters[0][mon2];
        int y2 = monsters[1][mon2];
        int s2 = monsters[2][mon2];

        double distance = Math.pow(x2 - x1,2) + Math.pow(y2 - y1,2);
        //distance = Math.sqrt(distance);

        return distance <= (s1 + s2)*(s1 + s2);
    }

    public static boolean haveEdge(int mon,int m,int n){
        int x = monsters[0][mon];
        int y = monsters[1][mon];
        int s = monsters[2][mon];


        return s - x >= 0 || s + x >= n || s - y >= 0 || s + y >= m;
    }

//    public static void buildBFS(int m,int n){
//        //color: white 0  yellow 1  red 2
//        GraphNode1 sourceVertex = new GraphNode1(0);
//
//        Queue<GraphNode1> queue = new LinkedList<>();
//        queue.offer(sourceVertex);
//        sourceVertex.color = 1;
//
//        rootBFS = sourceVertex;
//
//        while(!queue.isEmpty()){
//            GraphNode1 emptyV = queue.poll();//空点
//            GraphNode1 v = graph[emptyV.order];//对应的已储存信息的点
//
//            for(int i = 0; i < v.sons.size(); i++){
//                GraphNode1 tmp = v.sons.get(i);//对应的已储存信息的点
//                GraphNode1 emptyEmp = new GraphNode1(tmp.order);//空点
//
//                if(tmp.color == 0){
//                    queue.offer(emptyEmp);
//                    tmp.color = 1;
//
//                    emptyV.sons.add(emptyEmp);
//                }
//            }
//            v.color = 2;
//        }
//    }

    public static void canReach(GraphNode1 root,int order){
        if(root.order == order){
            reach =  true;
        }else{
            if(root.sons != null){
                for(int i = 0; i < root.sons.size(); i++){
                    canReach(root.sons.get(i),order);
                }
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
            if(haveEdge(i - 1,m,n)){
                graph[0][i] = 1;
                graph[i][0] = 1;
            }
        }
        //edge 0i

        for(int i = 1; i < k + 1; i++){
            if(haveEdge(i - 1,m,n)){
                graph[k + 1][i] = 1;
                graph[i][k + 1] = 1;
            }
        }
        //edge 1i
    }

//    public static void print(){
//        int k = monsters[0].length;
//
//        for(int i = 0; i < k + 2; i++){
//            System.out.printf("v%d: ",graph[i].order);
//            for(int j = 0; j < graph[i].sons.size(); j++){
//                System.out.printf("v%d ",graph[i].sons.get(j).order);
//            }
//            System.out.println();
//        }
//
//    }

    public static void print(){
        int k = monsters[0].length;

        for(int i = 0; i < k + 2; i++){
            for(int j = 0; j < k + 2; j++){
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }

    }
}
