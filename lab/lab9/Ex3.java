import java.util.*;

class GNode3{
    int index;
    ArrayList<GNode3> outEdges = new ArrayList<>();

    GNode3(int index){
        this.index = index;
    }
}

public class Ex3 {
    static ArrayList<GNode3> cities = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int m = input.nextInt();

        for(int i = 1; i < n + 1; i++){
            GNode3 tmp = new GNode3(i);
            cities.add(tmp);
        }

        for(int i = 0; i < m; i++){
            int u = input.nextInt();
            int v = input.nextInt();
            int w = input.nextInt();

            addEdge(u,v,w);
        }

        //print();

        System.out.print(calculateTime(n));
    }

    public static void addEdge(int u, int v, int time){
        GNode3 city1 = cities.get(u - 1);
        GNode3 city2 = cities.get(v - 1);

        if(time == 1){
            city1.outEdges.add(city2);
            //city2.connections.add(city1);
        }else{
            GNode3 tmp = new GNode3(cities.size() + 1);
            cities.add(tmp);

            city1.outEdges.add(tmp);
            tmp.outEdges.add(city2);
        }
    }

    public static int calculateTime(int n){
        //bfs
        //return height of bfs(minimum time)
        //return -1 when cannot reach

        int amount = cities.size();
        int[] heights = new int[amount + 1];
        int[] colors = new int[amount + 1];
        GNode3 v;
        //0 white(not operated)
        //1 yellow(in the queue)
        //2 red(is visited)

        Queue<GNode3> que = new LinkedList<>();
        que.offer(cities.get(0));
        colors[1] = 1;

        while(!que.isEmpty()){
            v = que.poll();

            for(int i = 0; i < v.outEdges.size(); i++){
                GNode3 u = v.outEdges.get(i);

                if(colors[u.index] == 0){
                    que.offer(u);
                    colors[u.index] = 1;
                    heights[u.index] = heights[v.index] + 1;
                }
            }
            colors[v.index] = 2;
        }

        if(colors[n] == 0){
            return -1;
        }else{
            return heights[n];
        }
    }

    public static void print(){
        for(int i = 0; i < cities.size(); i++){
            System.out.printf("%d: ",cities.get(i).index);
            for(int j = 0; j < cities.get(i).outEdges.size(); j++){
                System.out.print(cities.get(i).outEdges.get(j).index + " ");
            }
            System.out.println();
        }
    }
}
