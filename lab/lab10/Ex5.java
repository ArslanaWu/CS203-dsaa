import java.util.*;

class GNode5{
    int index;
    int actived;
    long dist = Long.MAX_VALUE;
    ArrayList<Path5> outEdges = new ArrayList<>();

    GNode5(int index, int actived){
        this.index = index;
        this.actived = actived;
    }
}

class Path5 {
    int weight;
    GNode5 startNode;
    GNode5 endNode;

    boolean active = false;

    Path5(int weight, GNode5 startNode, GNode5 endNode){
        this.weight = weight;
        this.startNode = startNode;
        this.endNode = endNode;
    }
}

public class Ex5 {
    static GNode5[][] cities;
    static long[][] dist;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int m = input.nextInt();
        int p = input.nextInt();
        int k = input.nextInt();

        cities = new GNode5[k + 1][n];
        for(int i = 0; i < k + 1; i++){
            for(int j = 0; j < n; j++){
                cities[i][j] = new GNode5(j + 1, i);
            }
        }

        for(int i = 0; i < m; i++){
            int u = input.nextInt();
            int v = input.nextInt();
            int w = input.nextInt();

            for(int j = 0; j < k + 1; j++){
                Path5 tmp = new Path5(w, cities[j][u - 1], cities[j][v - 1]);
                cities[j][u - 1].outEdges.add(tmp);
            }
        }
        //build normal roads

        for(int i = 0; i < p; i++){
            int u = input.nextInt();
            int v = input.nextInt();

            for(int j = 0; j < k + 1; j++){
                Path5 tmp = new Path5(0, cities[j][u - 1], cities[j][v - 1]);
                cities[j][u - 1].outEdges.add(tmp);
            }
        }
        //build portals

        int s = input.nextInt();//origin
        int t = input.nextInt();//destination

        dist = new long[k + 1][n];
        for(int i = 0; i < k + 1; i++){
            for(int j = 0; j < n; j++){
                dist[i][j] = Long.MAX_VALUE;
            }
        }
        for(int i = 0; i < k + 1; i++){
            cities[i][s - 1].dist = 0;
            dist[i][s - 1] = 0;
        }
        //initialization

        findMin(s, k);

        long min = dist[0][t - 1];
        for(int i = 1; i < k + 1; i++){
            min = Math.min(min, dist[i][t - 1]);
        }

        System.out.println(min);
    }

    public static void findMin(int start, int active){
        Queue<GNode5> minHeap = new PriorityQueue<>(cmp);

        minHeap.add(cities[0][start - 1]);

        while(!minHeap.isEmpty()){
            GNode5 u = minHeap.poll();

            for(int i = 0; i < u.outEdges.size(); i++){
                Path5 tmp = u.outEdges.get(i);

                if(tmp.weight == 0){
                    if(u.actived < active){
                        int n = u.actived + 1;
                        int m = tmp.endNode.index - 1;
                        GNode5 v = cities[n][m];

                        if(v.dist > u.dist){ //? dist[u.actived][u.index - 1]
                            v.dist = u.dist;
                            dist[n][m] = u.dist;

                            minHeap.add(v);
                        }
                    }
                }
                else{
                    GNode5 v = tmp.endNode;

                    if(v.dist > u.dist + tmp.weight){
                        v.dist = u.dist + tmp.weight;
                        dist[v.actived][v.index - 1] = u.dist + tmp.weight;

                        minHeap.add(v);
                    }
                }
            }
        }
    }

    public static Comparator<GNode5> cmp = new Comparator<GNode5>() {
        @Override
        public int compare(GNode5 o1, GNode5 o2) {
            if(o1.dist > o2.dist){
                return 1;
            }else{
                return -1;
            }
        }
    };
}
