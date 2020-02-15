import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class GNode2{
    int index;
    Path2 bestExt;
    boolean chosen;

    ArrayList<Path2> outEdges = new ArrayList<>();

    GNode2(int index){
        this.index = index;
    }
}

class Path2 {
    int weight;
    int startNode;
    int endNode;

    Path2(int weight, int startNode, int endNode){
        this.weight = weight;
        this.startNode = startNode;
        this.endNode = endNode;
    }
}

public class Ex2 {
    static GNode2[] cities;
    static ArrayList<Path2> roads;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int m = input.nextInt();

        cities = new GNode2[n];
        for(int i = 0; i < n; i++){
            cities[i] = new GNode2(i + 1);
        }
        //create nodes

        roads = new ArrayList<>();
        for(int i = 0;i < m; i++){
            int u = input.nextInt();
            int v = input.nextInt();
            int w = input.nextInt();

            Path2 tmp1 = new Path2(w, u, v);
            Path2 tmp2 = new Path2(w, v, u);

            cities[u - 1].outEdges.add(tmp1);
            cities[v - 1].outEdges.add(tmp2);

            roads.add(tmp1);
        }
        //build graph

        int minimumCost = 0;
        if(n > 1){
            minimumCost = minimumSpanningTree();
        }

        System.out.println(minimumCost);
    }

    public static int minimumSpanningTree(){
        int result = 0;

        ArrayList<GNode2> nodesNotInTree =
                new ArrayList<>(Arrays.asList(cities));
        //copy of all nodes, nodes in it will be deleted when chosen

        Path2 smallest = roads.get(0);
        for(Path2 tmp : roads){
            if(tmp.weight < smallest.weight){
                smallest = tmp;
            }
        }
        GNode2 u = cities[smallest.startNode - 1];
        GNode2 v = cities[smallest.endNode - 1];
        u.chosen = true;
        v.chosen = true;
        nodesNotInTree.remove(u);
        nodesNotInTree.remove(v);

        result += smallest.weight;
        System.out.println(result);
        //chose first two nodes with edge of minimum weight

        for(GNode2 tmp : nodesNotInTree){
            Path2 inU = ifContain(tmp, u);
            Path2 inV = ifContain(tmp, v);

            if(inU != null && inV == null){
                tmp.bestExt = inU;
            }else if(inU == null && inV != null){
                tmp.bestExt = inV;
            }else if(inU != null){
                if(inU.weight > inV.weight){
                    tmp.bestExt = inV;
                }else{
                    tmp.bestExt = inU;
                }
            }
        }
        //create best-ext at first time

        while(nodesNotInTree.size() != 0){
            smallest = findSmallestWeight();
            GNode2 z = cities[smallest.endNode - 1];
            z.chosen = true;
            nodesNotInTree.remove(z);
            //chose edge with minimum best-ext edge

            result += smallest.weight;
            System.out.println(result);

            for(Path2 tmp : z.outEdges){
                GNode2 end = cities[tmp.endNode - 1];
                if(!end.chosen){
                    if(end.bestExt == null ||
                            end.bestExt.weight > tmp.weight){
                        end.bestExt = tmp;
                    }
                    //null because can not reach
                }
            }
            //change best-ext when choosing a new node

            for(GNode2 tmp : cities){
                if(tmp.chosen){
                    tmp.bestExt = null;
                }
            }
            //set best-ext nodes be chosen as null
        }
        //update best-ext

        return result;
    }

    public static Path2 ifContain(GNode2 toFind, GNode2 u){
        Path2 result = null;
        for(Path2 tmp : u.outEdges){
            if(tmp.endNode == toFind.index){
                result = tmp;
                break;
            }
        }
        return result;
    }

    public static Path2 findSmallestWeight(){
        int smallest = -1;

        for(int i = 0; i < cities.length; i++){
            if(cities[i].bestExt != null){
                smallest = i;
                break;
            }
        }

        for(int i = smallest + 1; i < cities.length; i++){
            if(cities[i].bestExt != null &&
                    cities[i].bestExt.weight < cities[smallest].bestExt.weight){
                smallest = i;
            }
        }

        return cities[smallest].bestExt;
    }
}
