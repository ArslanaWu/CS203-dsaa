import java.util.ArrayList;
import java.util.Scanner;

//估计是错的
public class Ex4_re {
    static int[][] cij;
    static GNode4[][] cells;
    static ArrayList<Path4> paths;
    static GNode4 maxHeap = null;

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

        int max;
        if(n == 1 && m == 1){
            max = cij[0][0];
        }else{
            max = maximumSpanningTree();
        }

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

    public static int maximumSpanningTree(){
        int max = 0;

        ArrayList<GNode4> nodesNotInTree = new ArrayList<>();
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                nodesNotInTree.add(cells[i][j]);
            }
        }
        //copy of all nodes, nodes in it will be deleted when chosen

        Path4 s = paths.get(0);
        for(Path4 tmp : paths){
            if(tmp.weight > s.weight){
                s = tmp;
            }
        }
        GNode4 u = cells[s.startNode.n][s.startNode.m];
        GNode4 v = cells[s.endNode.n][s.endNode.m];
        u.chosen = true;
        v.chosen = true;
        nodesNotInTree.remove(u);
        nodesNotInTree.remove(v);

        max += s.weight;
        //chose first two nodes with edge of maximum weight

        int cnt = 0;
        for(GNode4 tmp : nodesNotInTree){
            Path4 inU = ifContain(tmp, u);
            Path4 inV = ifContain(tmp, v);

            if(inU != null || inV != null){
                if(inU != null && inV == null){
                    tmp.bestExt = inU.weight;
                }else if(inU == null && inV != null){
                    tmp.bestExt = inV.weight;
                }else{
                    if(inU.weight < inV.weight){
                        tmp.bestExt = inV.weight;
                    }else{
                        tmp.bestExt = inU.weight;
                    }
                }

                if(cnt == 0){
                    maxHeap = tmp;
                    cnt = 1;
                }else{
                    cnt++;
                    insert(tmp, cnt);
                }
            }
        }
        //create best-ext at first time

        while(nodesNotInTree.size() != 0){
            GNode4 z = cells[maxHeap.n][maxHeap.m];
            z.chosen = true;

            if(cnt == 1){
                maxHeap = null;
                cnt = 0;
            }else{
                deleteMax(cnt);
                cnt--;
            }

            nodesNotInTree.remove(z);
            //chose node with maximum best-ext

            max += z.bestExt;
            System.out.println(max);

            for(Path4 tmp : z.outEdges){
                GNode4 end = cells[tmp.endNode.n][tmp.endNode.m];

                if(!end.chosen){
                    if(end.bestExt < tmp.weight){
                        if(end.bestExt == -100){
                            end.bestExt = tmp.weight;
                            cnt++;
                            insert(end, cnt);
                        }else{
                            end.bestExt = tmp.weight;
                            maintain(end);
                        }
                    }
                }
            }
        }
        //update best-ext

        return max;
    }

    private static void maintain(GNode4 end) {
        GNode4 ptr = end;

        while(ptr.f != null && ptr.bestExt > ptr.f.bestExt){
            swap(ptr.f, ptr);
            ptr = ptr.f;
        }
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

    public static void insert(GNode4 toInsert, int amount){
        String binary = Integer.toBinaryString(amount);
        GNode4 ptr = maxHeap;
        int length = binary.length();
        GNode4[] fathers = new GNode4[length - 1];

        for(int i = 1;i < length;i++){
            fathers[length - 1 - i] = ptr;
            if(i == binary.length() - 1){
                if(binary.charAt(i) == '0'){
                    ptr.l = toInsert;
                }else{
                    ptr.r = toInsert;
                }
                ptr = toInsert;
                break;
            }

            if(binary.charAt(i) == '0'){
                ptr = ptr.l;
            }else{
                ptr = ptr.r;
            }
        }

        int i = 0;
        while(i < length - 1 && ptr.bestExt > fathers[i].bestExt){
            ptr.f = fathers[i];
            swap(fathers[i],ptr);
            ptr = fathers[i];
            i++;
        }
    }

    public static void swap(GNode4 node1, GNode4 node2){
        int swap = node1.bestExt;
        node1.bestExt = node2.bestExt;
        node2.bestExt = swap;

        swap = node1.n;
        node1.n = node2.n;
        node2.n = swap;

        swap = node1.m;
        node1.m = node2.m;
        node2.m = swap;
    }

    public static void deleteMax(int amount){
        String binary = Integer.toBinaryString(amount);
        int length = binary.length();
        GNode4 ptr = maxHeap;
        int bestExt = 0;
        int n = 0;
        int m = 0;

        for(int i = 1;i < length;i++){
            if(i == binary.length() - 1){
                if(binary.charAt(i) == '0'){
                    bestExt = ptr.l.bestExt;
                    n = ptr.l.n;
                    m = ptr.l.m;
                    ptr.l = null;
                }else{
                    bestExt = ptr.r.bestExt;
                    n = ptr.r.n;
                    m = ptr.r.m;
                    ptr.r = null;
                }
                break;
            }
            if(binary.charAt(i) == '0'){
                ptr = ptr.l;
            }else{
                ptr = ptr.r;
            }
        }

        //int min = maxHeap.bestExt;
        maxHeap.bestExt = bestExt;
        maxHeap.n = n;
        maxHeap.m = m;

        ptr = maxHeap;
        GNode4 b = findBigger(maxHeap);
        while(b != null && ptr.bestExt < b.bestExt){
            swap(ptr,b);
            ptr = b;
            b = findBigger(ptr);
        }
        //return min;
    }

    public static GNode4 findBigger(GNode4 node){
        if(node.r == null && node.l == null){
            return null;
        }else if(node.r != null && node.l == null){
            return node.r;
        }else if(node.r == null){
            return node.l;
        }else{
            if(node.l.bestExt < node.r.bestExt){
                return node.r;
            }else{
                return node.l;
            }
        }
    }
}
