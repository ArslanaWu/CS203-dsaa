import java.util.*;

class GNode7{
    int index;

    int added = 0;//what index of ArrayList have been used
    ArrayList<Path> outEdges = new ArrayList<>();

    GNode7(int index){
        this.index = index;
    }
}

class Path{
    long weight;
    GNode7 startNode;
    GNode7 endNode;

    GNode7 penultNode;
    long penultWeight;

    Path(long weight, GNode7 startNode, GNode7 endNode){
        this.weight = weight;
        this.startNode = startNode;
        this.endNode = endNode;
    }
}

public class Ex7 {
    static GNode7[] camps;
    static ArrayList<Long> kths;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        java.util.Random r = new java.util.Random(5);

        int t = input.nextInt();

//        int t = 1;
//        System.out.println(t);

        for(int i = 0; i < t; i++){
            int n = input.nextInt(); //nodes
            int m = input.nextInt(); //paths
            int q = input.nextInt(); //queries

//            int n = r.nextInt(499) + 1;
//            int m = r.nextInt(499) + 1;
//            int q = r.nextInt(499) + 1;
//            System.out.println(n +" "+ m + " " + q);

            camps = new GNode7[n];
            for(int j = 0; j < n; j++){
                camps[j] = new GNode7(j + 1);
            }
            //initialization nodes

            for(int j = 0; j < m; j++){
                int u = input.nextInt(); //start
                int v = input.nextInt(); //end
                long w = input.nextLong(); //weight

//                int u = r.nextInt(n) + 1;
//                int v = r.nextInt(n) + 1;
//                long w = r.nextInt(1000000000 - 1) + 1;
//                System.out.println(u +" "+ v + " " + w);

                Path tmp = new Path(w, camps[u - 1], camps[v - 1]);
                camps[u - 1].outEdges.add(tmp);
            }
            //build graph

            for(int j = 0; j < n; j++){
                camps[j].outEdges.sort(cmp);
            }
            //ascending sort outEdges ArrayList

            int[] queries = new int[q];
            for(int j = 0; j < q; j++){
                int k = input.nextInt();

//                int k = r.nextInt(3000) + 1;
//                System.out.println(k);

                queries[j] = k;
            }
            //read queries

            int max = queries[0];
            for(int j = 0; j < q; j++){
                if(max < queries[j]){
                    max = queries[j];
                }
            }
            //find max kth want to find

            kths = new ArrayList<>();
            findKthShortest(max,n);

            for(int j = 0; j < q; j++){
                System.out.println(kths.get(queries[j] - 1));
            }
        }
    }

    public static void findKthShortest(int kth, int n){
        Queue<Path> minHeap = new PriorityQueue<>(cmp);

        for(int i = 0; i < n;i++){
            ArrayList<Path> tmp = camps[i].outEdges;

            if(tmp.size() != 0){
                minHeap.add(tmp.get(0));
                camps[i].added++;
            }
        }
        //initialization

        while(kths.size() < kth){
            if(minHeap.size() == 0){
                kths.add(kths.get(kths.size() - 1));
                continue;
            }

            Path kthEdge = minHeap.poll();
            kths.add(kthEdge.weight);

            GNode7 start = kthEdge.startNode;
            GNode7 end = kthEdge.endNode;
            long wei = kthEdge.weight;

            //extend
            ArrayList<Path> outEdges = end.outEdges;
            if(outEdges.size() != 0){
                Path tmp;
                if(end.added < outEdges.size()){
                    tmp = outEdges.get(end.added - 1);
                    end.added++;
                }else{
                    tmp = outEdges.get(outEdges.size() - 1);
                }

                Path newPath = new Path(wei + tmp.weight,
                        start, tmp.endNode);
                newPath.penultNode = end;
                newPath.penultWeight = tmp.weight;

                minHeap.add(newPath);
            }

            //penult
            GNode7 penult = kthEdge.penultNode;
            if(penult == null){
                penult = kthEdge.startNode;
                wei = 0;
            }else{
                wei -= kthEdge.penultWeight;
            }

            outEdges = penult.outEdges;
            if(penult.added < outEdges.size()){
                Path tmp = penult.outEdges.get(penult.added - 1);

                Path newPath = new Path(wei + tmp.weight,
                        start, tmp.endNode);
                if(wei != 0){
                    newPath.penultNode = penult;
                    newPath.penultWeight = tmp.weight;
                }

                minHeap.add(tmp);
                penult.added++;
            }
        }
    }

    public static Comparator<Path> cmp = new Comparator<Path>() {
        @Override
        public int compare(Path o1, Path o2) {
            if(o1.weight > o2.weight){
                return 1;
            }else{
                return -1;
            }
        }
    };
}
