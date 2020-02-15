import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class MinHeapNode2 {
    long number;
    MinHeapNode2 leftChild;
    MinHeapNode2 rightChild;

    MinHeapNode2(long number){
        this.number = number;
    }
}

public class Ex4 {
    static MinHeapNode2 root;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();
        int k = input.nextInt();
        int s = input.nextInt();

        long last_ans = s;
        long number;

        if(k <= t){
            //first k nodes
            long smallest;
            for(int i = 1;i <= k;i++){
                long n = i + last_ans;
                number = n;
                while(n != 0){
                    number += n % 10;
                    n = n / 10;
                }
                //generate number

                if(i == 1){
                    root = new MinHeapNode2(number);
                }else{
                    MinHeapNode2 tmp = new MinHeapNode2(number);
                    insert(tmp,i);
                }
                //build heap

                if(i % 100 == 0){
                    smallest = root.number;
                    System.out.print(smallest + " ");
                    last_ans = smallest;
                }
            }

            //next t-k nodes, 只需处理100的整数倍个
            long kthLargest;
            for(int i = k + 1;i <= t;i++){
                long n = i + last_ans;
                number = n;
                while(n != 0){
                    number += n % 10;
                    n = n / 10;
                }
                //generate number

                MinHeapNode2 tmp = new MinHeapNode2(number);
                insert(tmp,k + 1);
                deleteMin(k + 1);
                //collect numbers and keep heap a min-heap

                if(i % 100 == 0){
                    kthLargest = root.number;
                    System.out.print(kthLargest + " ");
                    last_ans = kthLargest;
                }
            }
        }else{
            //只需输出前t个的最小值
            long smallest;
            for(int i = 1;i <= t;i++){
                long n = i + last_ans;
                number = n;
                while(n != 0){
                    number += n % 10;
                    n = n / 10;
                }
                //generate number

                if(i == 1){
                    root = new MinHeapNode2(number);
                }else{
                    MinHeapNode2 tmp = new MinHeapNode2(number);
                    insert(tmp,i);
                }
                //build heap

                if(i % 100 == 0){
                    smallest = root.number;
                    System.out.print(smallest + " ");
                    last_ans = smallest;
                }
            }
        }
    }

    public static void insert(MinHeapNode2 toInsert, int amount){
        String binary = Integer.toBinaryString(amount);
        MinHeapNode2 ptr = root;
        int length = binary.length();
        MinHeapNode2[] fathers = new MinHeapNode2[length - 1];

        for(int i = 1;i < length;i++){
            fathers[length - 1 - i] = ptr;
            if(i == binary.length() - 1){
                if(binary.charAt(i) == '0'){
                    ptr.leftChild = toInsert;
                }else{
                    ptr.rightChild = toInsert;
                }
                ptr = toInsert;
                break;
            }

            if(binary.charAt(i) == '0'){
                ptr = ptr.leftChild;
            }else{
                ptr = ptr.rightChild;
            }
        }
        //find right place to insert and insert

        int i = 0;
        while(i < length - 1 && ptr.number < fathers[i].number){
            swap(fathers[i],ptr);
            ptr = fathers[i];
            i++;
        }
        //keep the heap a min-heap
    }
    //amount after insert
    public static long deleteMin(int amount){
        String binary = Integer.toBinaryString(amount);
        int length = binary.length();
        MinHeapNode2 ptr = root;
        long number = 0;

        for(int i = 1;i < length;i++){
            if(i == binary.length() - 1){
                if(binary.charAt(i) == '0'){
                    number = ptr.leftChild.number;
                    ptr.leftChild = null;
                }else{
                    number = ptr.rightChild.number;
                    ptr.rightChild = null;
                }
                break;
            }
            if(binary.charAt(i) == '0'){
                ptr = ptr.leftChild;
            }else{
                ptr = ptr.rightChild;
            }
        }

        long min = root.number;
        root.number = number;

        ptr = root;
        MinHeapNode2 smaller = findSmallerRoot(root);
        while(smaller != null && ptr.number > smaller.number){
            swap(ptr,smaller);
            ptr = smaller;
            smaller = findSmallerRoot(ptr);
        }
        return min;
    }
    //amount before delete
    public static MinHeapNode2 findSmallerRoot(MinHeapNode2 node){
        if(node.rightChild == null && node.leftChild == null){
            return null;
        }else if(node.rightChild != null && node.leftChild == null){
            return node.rightChild;
        }else if(node.rightChild == null){
            return node.leftChild;
        }else{
            if(node.leftChild.number > node.rightChild.number){
                return node.rightChild;
            }else{
                return node.leftChild;
            }
        }
    }

    public static void swap(MinHeapNode2 node1, MinHeapNode2 node2){
        long swap = node1.number;
        node1.number = node2.number;
        node2.number = swap;
    }


    public static MinHeapNode2 rightMostLeaf(int amount){
        String binary = Integer.toBinaryString(amount);
        int length = binary.length();
        MinHeapNode2 ptr = root;

        for(int i = 1;i < length;i++){
            if(binary.charAt(i) == '0'){
                ptr = ptr.leftChild;

            }else{
                ptr = ptr.rightChild;
            }
        }
        return ptr;
    }

    public static void print(int n) {
        MinHeapNode2[] levelTraverse = new MinHeapNode2[20];
        Queue<MinHeapNode2> queue = new LinkedList<>();

        int cnt = 1;
        levelTraverse[1] = root;
        queue.offer(root);

        while (!queue.isEmpty()) {
            MinHeapNode2 tmp = queue.poll();
            if (tmp.leftChild != null) {
                levelTraverse[cnt * 2] = tmp.leftChild;
                queue.offer(tmp.leftChild);
            }
            if (tmp.rightChild != null) {
                levelTraverse[cnt * 2 + 1] = tmp.rightChild;
                queue.offer(tmp.rightChild);
            }
            cnt++;
        }

        for (int i = 1; i < n + 1; i++) {
            System.out.print(levelTraverse[i].number + " ");

        }
        System.out.println();
    }
}
