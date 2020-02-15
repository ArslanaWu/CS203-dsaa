import java.util.*;

class GNode3{
    int index;
    ArrayList<GNode3> outEdges = new ArrayList<>();
    ArrayList<GNode3> reverseOutEdges = new ArrayList<>();

    GNode3(int index){
        this.index = index;
    }
}

public class Ex3 {
    static GNode3[] rooms;
    static ArrayList<Integer> scc;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int m = input.nextInt();

        rooms = new GNode3[n];
        for(int i = 0; i < n; i++){
            rooms[i] = new GNode3(i + 1);
        }
        scc = new ArrayList<>();

        for(int i = 0; i < m; i++){
            int u = input.nextInt();
            int v = input.nextInt();

            rooms[u - 1].outEdges.add(rooms[v - 1]);
            rooms[v - 1].reverseOutEdges.add(rooms[u - 1]);
        }
        //build graph

        findSCC();
        //find strongly connected components

        System.out.println(Arrays.toString(scc.toArray()));

        if(scc.size() == 1 && scc.get(0) == n){
            System.out.println("Bravo");
        }else{
            System.out.println("ovarB");
        }
    }

    public static void findSCC(){
        //dfs on reverse graph
        ArrayList<Integer> top = topologicalSort();

        //dfs on primary graph
        Collections.reverse(top);

        int amount = rooms.length;
        int[] colors = new int[amount];
        Stack<GNode3> stack = new Stack<>();
        GNode3 v;

        v = rooms[top.get(0) - 1];
        stack.add(v);
        colors[v.index - 1] = 1;
        top.remove( (Integer)v.index );

        int nodesInSCC = 0;
        while(!top.isEmpty() || !stack.isEmpty()){
            while(!stack.isEmpty()){
                v = stack.peek();

                GNode3 u = haveWhiteOutNeighbor(colors,v);
                if(u != null){
                    stack.push(u);
                    colors[u.index - 1] = 1;
                    top.remove( (Integer)u.index );
                }else{
                    stack.pop();
                    colors[v.index - 1] = 2;
                    nodesInSCC++;
                }
            }

            scc.add(nodesInSCC);
            nodesInSCC = 0;

            if(!top.isEmpty()){
                v = rooms[top.get(0) - 1];
                stack.add(v);
                colors[v.index - 1] = 1;
                top.remove( (Integer)v.index );
            }
        }
    }

    public static GNode3 haveWhiteOutNeighbor(int[] colors, GNode3 v){
        GNode3 u = null;

        for(int i = 0; i < v.outEdges.size(); i++){
            GNode3 tmp = v.outEdges.get(i);
            if(colors[tmp.index - 1] == 0){
                u = tmp;
                break;
            }
        }
        //initialization

        if(u != null){
            for(int i = 0; i < v.outEdges.size(); i++){
                GNode3 tmp = v.outEdges.get(i);
                if(u.index > tmp.index && colors[tmp.index - 1] == 0){
                    u = tmp;
                }
            }
        }
        //find min

        return u;
    }

    public static ArrayList<Integer> topologicalSort(){
        int amount = rooms.length;

        ArrayList<Integer> result = new ArrayList<>();

        ArrayList<Integer> copy = new ArrayList<>();
        for(GNode3 tmp : rooms){
            copy.add(tmp.index);
        }

        int[] colors = new int[amount];
        Stack<GNode3> stack = new Stack<>();
        GNode3 v;

        v = rooms[copy.get(0) - 1];
        stack.add(v);
        colors[v.index - 1] = 1;
        copy.remove( (Integer)v.index );

        while(!copy.isEmpty() || !stack.isEmpty()){
            while(!stack.isEmpty()){
                v = stack.peek();

                GNode3 u = haveWhiteOutNeighborReverse(colors,v);
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
                v = rooms[copy.get(0) - 1];
                stack.add(v);
                colors[v.index - 1] = 1;
                copy.remove( (Integer)v.index );
            }
        }

        return result;
    }

    public static GNode3 haveWhiteOutNeighborReverse(int[] colors, GNode3 v){
        GNode3 u = null;

        for(int i = 0; i < v.reverseOutEdges.size(); i++){
            GNode3 tmp = v.reverseOutEdges.get(i);
            if(colors[tmp.index - 1] == 0){
                u = tmp;
                break;
            }
        }
        //initialization

        if(u != null){
            for(int i = 0; i < v.reverseOutEdges.size(); i++){
                GNode3 tmp = v.reverseOutEdges.get(i);
                if(u.index > tmp.index && colors[tmp.index - 1] == 0){
                    u = tmp;
                }
            }
        }
        //find min

        return u;
    }
}
