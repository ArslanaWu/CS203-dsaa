import java.util.*;

class GNode1{
    int index;
    int dist = Integer.MAX_VALUE;

    int added = 0;//what index of ArrayList have been used
    ArrayList<Path1> outEdges = new ArrayList<>();

    GNode1(int index){
        this.index = index;
    }
}

class Path1 {
    int weight;
    GNode1 startNode;
    GNode1 endNode;

    Path1(int weight, GNode1 startNode, GNode1 endNode){
        this.weight = weight;
        this.startNode = startNode;
        this.endNode = endNode;
    }
}

public class Ex1 {
    static GNode1[] sights;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int m = input.nextInt();

        sights = new GNode1[n];
        for(int i = 0; i < n; i++){
            sights[i] = new GNode1(i + 1);
        }

        for(int i = 0;i < m; i++){
            int u = input.nextInt();
            int v = input.nextInt();
            int w = input.nextInt();

            Path1 tmp1 = new Path1(w, sights[u - 1], sights[v - 1]);
            Path1 tmp2 = new Path1(w, sights[v - 1], sights[u - 1]);

            sights[u - 1].outEdges.add(tmp1);
            sights[v - 1].outEdges.add(tmp2);
        }
        //build graph

        int s = input.nextInt();
        int t = input.nextInt();

        int shortest = dijkstra(sights[s - 1],sights[t - 1]);
        System.out.println(shortest);
    }

    public static int dijkstra(GNode1 origin, GNode1 destination){
        origin.dist = 0;

        ArrayList<GNode1> copy = new ArrayList<>(Arrays.asList(sights));

        while(copy.size() != 0){
            int s = shortestDist(copy);
            GNode1 u = copy.get(s);
            copy.remove(s);

            for(int i = 0; i < u.outEdges.size(); i++){
                Path1 tmp = u.outEdges.get(i);
                GNode1 v = tmp.endNode;

                if(v.dist > u.dist + tmp.weight){
                    v.dist = u.dist + tmp.weight;
                }
            }
        }

        if(destination.dist == Integer.MAX_VALUE){
            return -1;
        }else{
            return destination.dist;
        }

    }

    public static int shortestDist(ArrayList<GNode1> copy){
        int s = 0;

        for(int i = 1; i < copy.size(); i++){
            if(copy.get(s).dist > copy.get(i).dist){
                s = i;
            }
        }

        return s;
    }
}
