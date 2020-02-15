import java.util.*;

class GNode4 {
    int n;
    int m;
    int bestExt = -100;
    boolean chosen = false;

    ArrayList<Path4> outEdges = new ArrayList<>();

    GNode4(int n, int m){
        this.n = n;
        this.m = m;
    }

    GNode4(GNode4 copy){
        n = copy.n;
        m = copy.m;
        chosen = copy.chosen;
        bestExt = copy.bestExt;
        outEdges = copy.outEdges;
    }

    GNode4 f;
    GNode4 l;
    GNode4 r;
}

class Path4 {
    int weight;
    GNode4 startNode;
    GNode4 endNode;

    Path4(int weight, GNode4 startNode, GNode4 endNode){
        this.weight = weight;
        this.startNode = startNode;
        this.endNode = endNode;
    }
}

public class Ex4 {
    static int[][] cij;
    static GNode4[][] cells;
    static ArrayList<Path4> paths;
    static Queue<GNode4> maxHeap;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int m = input.nextInt();

        cells = new GNode4[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                cells[i][j] = new GNode4(i, j);
            }
        }

        cij = new int[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                cij[i][j] = input.nextInt();
            }
        }
        paths = new ArrayList<>(n * m);

        buildGraph();

        long max = maximumSpanningTree(n, m);

        System.out.println(max);
    }

    public static void buildGraph(){
        int n = cells.length;
        int m = cells[0].length;

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m - 1; j++){
                int weight = cij[i][j] * cij[i][j + 1];

                Path4 tmp1 = new Path4(weight, cells[i][j], cells[i][j + 1]);
                Path4 tmp2 = new Path4(weight, cells[i][j + 1], cells[i][j]);

                cells[i][j].outEdges.add(tmp1);
                cells[i][j + 1].outEdges.add(tmp2);

                paths.add(tmp1);
            }
        }

        for(int i = 0; i < n - 1; i++){
            for(int j = 0; j < m; j++){
                int weight = cij[i][j] * cij[i + 1][j];

                Path4 tmp1 = new Path4(weight, cells[i][j], cells[i + 1][j]);
                Path4 tmp2 = new Path4(weight, cells[i + 1][j], cells[i][j]);

                cells[i][j].outEdges.add(tmp1);
                cells[i + 1][j].outEdges.add(tmp2);

                paths.add(tmp1);
            }
        }
    }

    public static long maximumSpanningTree(int n, int m){
        long max = 0;

        int[][] bestExt = new int[n][m];
        boolean[][] chosen = new boolean[n][m];
        long notInTree = (long)m * (long)n;

        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                bestExt[i][j] = -100;
                chosen[i][j] = false;
            }
        }

        Path4 s = paths.get(0);
        for(Path4 tmp : paths){
            if(tmp.weight > s.weight){
                s = tmp;
            }
        }
        GNode4 u = s.startNode;
        GNode4 v = s.endNode;
        chosen[u.n][u.m] = true;
        chosen[v.n][v.m] = true;
        notInTree = notInTree - 2;
        //chose first two nodes with edge of maximum weight

        max += s.weight;

        maxHeap = new PriorityQueue<>(cmp);
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(chosen[i][j]){
                    continue;
                }

                GNode4 tmp = cells[i][j];
                Path4 inU = ifContain(tmp, u);
                Path4 inV = ifContain(tmp, v);

                if(inU != null && inV == null){
                    tmp.bestExt = inU.weight;
                    bestExt[i][j] = inU.weight;

                    maxHeap.add(tmp);
                }else if(inU == null && inV != null){
                    tmp.bestExt = inV.weight;
                    bestExt[i][j] = inV.weight;

                    maxHeap.add(tmp);
                }else if(inU != null){
                    if(inU.weight < inV.weight){
                        tmp.bestExt = inV.weight;
                        bestExt[i][j] = inV.weight;

                        maxHeap.add(tmp);
                    }else{
                        tmp.bestExt = inU.weight;
                        bestExt[i][j] = inU.weight;

                        maxHeap.add(tmp);
                    }
                }
            }
        }

        while(notInTree != 0){
            GNode4 z = maxHeap.poll();
            while(chosen[z.n][z.m]){
                z = maxHeap.poll();
            }
            chosen[z.n][z.m] = true;
            notInTree--;

            max += z.bestExt;
            //chose node with maximum best-ext

            for(Path4 tmp : z.outEdges){
                GNode4 end = tmp.endNode;

                if(!chosen[end.n][end.m]){
                    if(bestExt[end.n][end.m] < tmp.weight){
                        GNode4 a = new GNode4(end);
                        a.bestExt = tmp.weight;
                        maxHeap.add(a);

                        bestExt[end.n][end.m] = tmp.weight;
                    }
                }
            }
        }
        //update best-ext

        return max;
    }

    public static Path4 ifContain(GNode4 toFind, GNode4 u){
        Path4 result = null;
        for(Path4 tmp : u.outEdges){
            if(tmp.endNode == toFind){
                result = tmp;
                break;
            }
        }
        return result;
    }

    public static Comparator<GNode4> cmp = new Comparator<GNode4>() {
        @Override
        public int compare(GNode4 o1, GNode4 o2) {
            if(o1.bestExt < o2.bestExt){
                return 1;
            }else{
                return -1;
            }
        }
    };
}
