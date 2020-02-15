import java.util.*;

class GNode2r{
    int index;
    int bestExt = 1000001;
    boolean chosen = false;

    ArrayList<Path2> outEdges = new ArrayList<>();

    GNode2r(int index){
        this.index = index;
    }

    GNode2r(GNode2r copy){
        index = copy.index;
        chosen = copy.chosen;
        bestExt = copy.bestExt;
        outEdges = copy.outEdges;
    }
}

public class Ex2_re {
    static GNode2r[] cities;
    static ArrayList<Path2> roads;
    static Queue<GNode2r> minHeap;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int m = input.nextInt();

        cities = new GNode2r[n];
        for(int i = 0; i < n; i++){
            cities[i] = new GNode2r(i + 1);
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

        ArrayList<GNode2r> nodesNotInTree =
                new ArrayList<>(Arrays.asList(cities));
        //copy of all nodes, nodes in it will be deleted when chosen

        Path2 smallest = roads.get(0);
        for(Path2 tmp : roads){
            if(tmp.weight < smallest.weight){
                smallest = tmp;
            }
        }
        GNode2r u = cities[smallest.startNode - 1];
        GNode2r v = cities[smallest.endNode - 1];
        u.chosen = true;
        v.chosen = true;
        nodesNotInTree.remove(u);
        nodesNotInTree.remove(v);

        result += smallest.weight;
        System.out.println(result);
        //chose first two nodes with edge of minimum weight

        minHeap = new PriorityQueue<>(cmp);
        for(GNode2r tmp : nodesNotInTree){
            Path2 inU = ifContain(tmp, u);
            Path2 inV = ifContain(tmp, v);

            if(inU != null && inV == null){
                tmp.bestExt = inU.weight;
                minHeap.add(tmp);
            }else if(inU == null && inV != null){
                tmp.bestExt = inV.weight;
                minHeap.add(tmp);
            }else if(inU != null){
                if(inU.weight > inV.weight){
                    tmp.bestExt = inV.weight;
                    minHeap.add(tmp);
                }else{
                    tmp.bestExt = inU.weight;
                    minHeap.add(tmp);
                }
            }
        }
        //create best-ext at first time

        while(nodesNotInTree.size() != 0){
            GNode2r z = cities[minHeap.poll().index - 1];
            while(z.chosen){
                z = cities[minHeap.poll().index - 1];
            }
            z.chosen = true;
            nodesNotInTree.remove(z);
            //chose edge with minimum best-ext edge

            result += z.bestExt;
            System.out.println(result);
            if(result == 26){
                System.out.println("!");
            }

            for(Path2 tmp : z.outEdges){
                GNode2r end = cities[tmp.endNode - 1];
                if(!end.chosen){
                    if(end.bestExt > tmp.weight){
                        GNode2r a = new GNode2r(end);
                        a.bestExt = tmp.weight;
                        minHeap.add(a);

                        nodesNotInTree.remove(end);
                        nodesNotInTree.add(a);
                        cities[end.index - 1] = a;
                    }
                    //null because can not reach
                }
            }
            //change best-ext when choosing a new node
        }
        //update best-ext

        return result;
    }

    public static Path2 ifContain(GNode2r toFind, GNode2r u){
        Path2 result = null;
        for(Path2 tmp : u.outEdges){
            if(tmp.endNode == toFind.index){
                result = tmp;
                break;
            }
        }
        return result;
    }

    public static Comparator<GNode2r> cmp = new Comparator<GNode2r>() {
        @Override
        public int compare(GNode2r o1, GNode2r o2) {
            if(o1.bestExt > o2.bestExt){
                return 1;
            }else{
                return -1;
            }
        }
    };
}
