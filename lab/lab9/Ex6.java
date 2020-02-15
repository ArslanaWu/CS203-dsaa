import java.util.*;

class GNode6{
    int len;
    int wid;
    int hei;
    int index;
    ArrayList<GNode6> outEdges = new ArrayList<>();

    int dist = 0;
    int inDegree = 0;

    GNode6(int len, int wid, int hei, int index){
        this.len = len;
        this.wid = wid;
        this.hei = hei;
        this.index = index;
    }
}

public class Ex6{
    static GNode6[] materials;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();
//        int t = 1;

//        java.util.Random r = new java.util.Random();

        for(int i = 0; i < t; i++){
            int n = input.nextInt();
//            int n = r.nextInt(10) + 1;
//            System.out.println(n);

            materials = new GNode6[n];

            for(int j = 0; j < n; j++){
                int a = input.nextInt(); //length
                int b = input.nextInt(); //width
                int c = input.nextInt(); //height

//                int a = r.nextInt(20) + 1;
//                int b = r.nextInt(20) + 1;
//                int c = r.nextInt(20) + 1;
//                System.out.println(a +" "+ b + " " + c);

                GNode6 tmp = new GNode6(a,b,c,j + 1);
                materials[j] = tmp;
            }

            addEdge(n);

            //print();

            int max = calculateDistance(n);

            System.out.println(max);
        }
    }

    public static void addEdge(int n){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){

                if(i == j){
                    continue;
                }

                GNode6 m1 = materials[i];
                GNode6 m2 = materials[j];
                if((m1.len < m2.len && m1.wid < m2.wid) ||
                        (m1.len < m2.wid && m1.wid < m2.len)){
                    m1.outEdges.add(m2);
                    m2.inDegree++;
                }
            }
        }
    }

    public static int calculateDistance(int n){
        //top sort on bfs
        //add into queue when in-level = 0

        GNode6 v;

        Queue<GNode6> que = new LinkedList<>();

        for(int i = 0; i < n; i++){
            if(materials[i].inDegree == 0){
                que.offer(materials[i]);
                materials[i].dist = materials[i].hei;
            }
        }

        while(!que.isEmpty()){
            v = que.poll();

            for(int i = 0; i < v.outEdges.size(); i++){
                GNode6 u = v.outEdges.get(i);
                u.inDegree--;

                if(u.inDegree == 0){
                    que.offer(u);
                }

                if(u.dist < v.dist + u.hei){
                    u.dist = v.dist + u.hei;
                }
            }
        }

        int max = materials[0].dist;
        for(int i = 1; i < n; i++){
            if(max < materials[i].dist){
                max = materials[i].dist;
            }
        }
        return max;
    }






    public static void dijkstra(int n){
        GNode6 source = null;

        //Queue<GNode6> copy = new PriorityQueue<>(cmp);
        ArrayList<GNode6> copy = new ArrayList<>();
        for(int i = 0; i < n; i++){
            copy.add(materials[i]);
        }
        //create copy of materials[] using arrayList

        for(int i = 0; i < n; i++){
            if(materials[i].inDegree == 0){
                source = materials[i];
                source.dist = source.hei;
                break;
            }
        }
        //find source

        while(copy.size() != 0){
            int biggest = biggestDist(copy);
            GNode6 u = copy.get(biggest);
            copy.remove(biggest);

            for(int i = 0; i < u.outEdges.size(); i++){
                GNode6 v = u.outEdges.get(i);

                if(v.dist < u.dist + v.hei){
                    v.dist = u.dist + v.hei;
                }
            }
        }
    }

    public static int biggestDist(ArrayList<GNode6> copy){
        int biggest = 0;

        for(int i = 1; i < copy.size(); i++){
            if(copy.get(biggest).dist < copy.get(i).dist){
                biggest = i;
            }
        }

        return biggest;
    }

    public static Comparator<GNode6> cmp = new Comparator<GNode6>() {
        @Override
        public int compare(GNode6 o1, GNode6 o2) {

            if(o1.dist > o2.dist){
                return 1;
            }else{
                return -1;
            }
        }
    };

    public static void print(){
        for(int i = 0; i < materials.length; i++){
//            System.out.printf("%d %d %d %b: ",materials[i].len,
//                    materials[i].wid, materials[i].hei,
//                    materials[i].haveInDegree);
            System.out.printf("%d: ",materials[i].index);

            for(int j = 0; j < materials[i].outEdges.size(); j++){
//                System.out.printf("%d %d %d    ",materials[i].outEdges.get(j).len,
//                        materials[i].outEdges.get(j).wid,
//                        materials[i].outEdges.get(j).hei);
                System.out.printf("%d ",materials[i].outEdges.get(j).index);
            }
            System.out.println();
        }
    }
}