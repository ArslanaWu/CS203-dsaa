import java.util.*;

class GNode6 {
    int index;
    int x;
    int y;
    int r;
    int t;
    ArrayList<GNode6> outEdges = new ArrayList<>();
    ArrayList<GNode6> reverseOutEdges = new ArrayList<>();

    int inWhichSCC = -1;
    int inDegree = 0;

    int indexNew;

    GNode6(int index, int x, int y, int r, int t){
        this.index = index;
        this.x = x;
        this.y = y;
        this.r = r;
        this.t = t;
    }

    GNode6(int indexNew, GNode6 node){
        index = indexNew;
        x = node.x;
        y = node.y;
        r = node.r;
        t = node.t;
    }
}

public class Ex6 {
    static GNode6[] bombs;
    static ArrayList<GNode6[]> scc;
    static GNode6[] newBombs;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();

        bombs = new GNode6[n];
        for(int i = 0; i < n; i++){
            int x = input.nextInt();
            int y = input.nextInt();
            int r = input.nextInt();
            int t = input.nextInt();

            bombs[i] = new GNode6(i + 1, x, y, r, t);
        }
        scc = new ArrayList<>();

        buildGraph();

        findSCC();

        refreshGraph();

        int cost = calculateCost();

        System.out.println(cost);
    }

    public static void buildGraph(){
        int n = bombs.length;

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(i == j){
                    continue;
                }

                long xi = bombs[i].x;
                long xj = bombs[j].x;
                long yi = bombs[i].y;
                long yj = bombs[j].y;
                long ri = bombs[i].r;

                long distance = (xi - xj) * (xi - xj) +
                        (yi - yj) * (yi - yj);

                if(distance <= ri * ri){
                    //have edge
                    bombs[i].outEdges.add(bombs[j]);
                    bombs[j].reverseOutEdges.add(bombs[i]);
                }
            }
        }
    }

    public static void findSCC(){
        //dfs on reverse graph
        ArrayList<Integer> top = topologicalSort();

        //dfs on primary graph
        Collections.reverse(top);

        int amount = bombs.length;
        int[] colors = new int[amount];
        Stack<GNode6> stack = new Stack<>();
        GNode6 v;

        v = bombs[top.get(0) - 1];
        stack.add(v);
        colors[v.index - 1] = 1;
        top.remove( (Integer)v.index );

        int amountSCC = 0;
        GNode6[] nodesInSCC = new GNode6[bombs.length];
        while(!top.isEmpty() || !stack.isEmpty()){
            while(!stack.isEmpty()){
                v = stack.peek();

                GNode6 u = haveWhiteOutNeighbor(colors,v);
                if(u != null){
                    stack.push(u);
                    colors[u.index - 1] = 1;
                    top.remove( (Integer)u.index );
                }else{
                    stack.pop();
                    colors[v.index - 1] = 2;
                    amountSCC++;
                    nodesInSCC[amountSCC - 1] = v;
                }
            }

            scc.add(nodesInSCC);
            nodesInSCC = new GNode6[bombs.length];
            amountSCC = 0;

            if(!top.isEmpty()){
                v = bombs[top.get(0) - 1];
                stack.add(v);
                colors[v.index - 1] = 1;
                top.remove( (Integer)v.index );
            }
        }
    }

    public static GNode6 haveWhiteOutNeighbor(int[] colors, GNode6 v){
        GNode6 u = null;

        for(int i = 0; i < v.outEdges.size(); i++){
            GNode6 tmp = v.outEdges.get(i);
            if(colors[tmp.index - 1] == 0){
                u = tmp;
                break;
            }
        }
        //initialization

        if(u != null){
            for(int i = 0; i < v.outEdges.size(); i++){
                GNode6 tmp = v.outEdges.get(i);
                if(u.index > tmp.index && colors[tmp.index - 1] == 0){
                    u = tmp;
                }
            }
        }
        //find min

        return u;
    }

    public static ArrayList<Integer> topologicalSort(){
        int amount = bombs.length;

        ArrayList<Integer> result = new ArrayList<>();

        ArrayList<Integer> copy = new ArrayList<>();
        for(GNode6 tmp : bombs){
            copy.add(tmp.index);
        }

        int[] colors = new int[amount];
        Stack<GNode6> stack = new Stack<>();
        GNode6 v;

        v = bombs[copy.get(0) - 1];
        stack.add(v);
        colors[v.index - 1] = 1;
        copy.remove( (Integer)v.index );

        while(!copy.isEmpty() || !stack.isEmpty()){
            while(!stack.isEmpty()){
                v = stack.peek();

                GNode6 u = haveWhiteOutNeighborReverse(colors,v);
                if(u != null){
                    stack.push(u);
                    colors[u.index - 1] = 1;
                    copy.remove( (Integer)u.index );
                }else{
                    stack.pop();
                    colors[v.index - 1] = 2;
                    result.add(v.index);
                }
            }

            if(!copy.isEmpty()){
                v = bombs[copy.get(0) - 1];
                stack.add(v);
                colors[v.index - 1] = 1;
                copy.remove( (Integer)v.index );
            }
        }

        return result;
    }

    public static GNode6 haveWhiteOutNeighborReverse(int[] colors, GNode6 v){
        GNode6 u = null;

        for(int i = 0; i < v.reverseOutEdges.size(); i++){
            GNode6 tmp = v.reverseOutEdges.get(i);
            if(colors[tmp.index - 1] == 0){
                u = tmp;
                break;
            }
        }
        //initialization

        if(u != null){
            for(int i = 0; i < v.reverseOutEdges.size(); i++){
                GNode6 tmp = v.reverseOutEdges.get(i);
                if(u.index > tmp.index && colors[tmp.index - 1] == 0){
                    u = tmp;
                }
            }
        }
        //find min

        return u;
    }

    public static void refreshGraph(){
        //find smallest index in scc
        ArrayList<GNode6> smallest = new ArrayList<>(scc.size());
        for(int i = 0; i < scc.size(); i++){
            GNode6[] nodesInSCC = scc.get(i);

            for(GNode6 tmp : nodesInSCC){
                if(tmp != null){
                    tmp.inWhichSCC = i;
                }
            }

            int s = 0;
            for(int j = 1; j < bombs.length; j++){
                if(nodesInSCC[j] == null){
                    break;
                }else if(nodesInSCC[j].t < nodesInSCC[s].t){
                    s = j;
                }
            }
            smallest.add(nodesInSCC[s]);
        }

        //replace one SCC to its smallest node "缩点", replace index only
        //先找到缩完图里会有哪些点，用这些点建一个新的空图
        int nodes = smallest.size();
        newBombs = new GNode6[nodes];
        for(int i = 0; i < nodes; i++){
            newBombs[i] = new GNode6(i + 1, smallest.get(i));
        }

        //对于每一个scc遍历所有点的出边，
        //如果有个边从这个scc出发到达scc之外 或者 从外边出发到达这个scc内就加边
        for(int i = 0; i < nodes; i++){
            GNode6[] nodesInSCC = scc.get(smallest.get(i).inWhichSCC);
            List<GNode6> list = Arrays.asList(nodesInSCC);

            for(int j = 0; j < bombs.length; j++){
                for(GNode6 tmp : bombs[j].outEdges){
                    if(list.contains(bombs[j]) && !list.contains(tmp)){
                        GNode6 son = newBombs[tmp.inWhichSCC];
                        newBombs[i].outEdges.add(son);
                        son.inDegree++;
                    }
                    else if(!list.contains(bombs[j]) && list.contains(tmp)){
                        GNode6 s = newBombs[bombs[j].inWhichSCC];
                        s.outEdges.add(newBombs[i]);
                        newBombs[i].inDegree++;
                    }
                }
            }
        }
    }

    public static int calculateCost(){
        int cost = 0;

        for(GNode6 tmp : newBombs){
            if(tmp.inDegree == 0){
                cost += tmp.t;
            }
        }
        return cost;
    }
}
