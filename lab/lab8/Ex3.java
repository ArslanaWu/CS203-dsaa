import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class MinHeapNode1{
    long number;
    MinHeapNode1 leftChild;
    MinHeapNode1 rightChild;

    MinHeapNode1(long number){
        this.number = number;
    }
}

public class Ex3 {
    static int[] lengths;
    static MinHeapNode1 root;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n = input.nextInt();

            lengths = new int[n];
            long result = 0;

            for(int j = 0;j < n;j++){
                lengths[j] = input.nextInt();
            }

            buildHeap();

            int amount = n;
            while(amount > 1){
                long length1 = deleteMin(amount);
                amount--;
                //print(amount);
                long length2 = deleteMin(amount);
                //print(amount - 1);
                result = result + length1 + length2;

                MinHeapNode1 tmp = new MinHeapNode1(length1 + length2);
                if(amount == 1){
                    root = tmp;
                }else{
                    insert(tmp,amount);
                }

                //print(amount);
            }
            System.out.println(result);
        }
    }

    public static void buildHeap(){
        root = new MinHeapNode1(lengths[0]);
        int nodeAmount = 1;

        for(int i = 1;i < lengths.length;i++){
            MinHeapNode1 tmp = new MinHeapNode1(lengths[i]);
            nodeAmount++;
            insert(tmp,nodeAmount);
            //print(nodeAmount);
        }
    }

    public static void insert(MinHeapNode1 toInsert, int amount){
        String binary = Integer.toBinaryString(amount);
        MinHeapNode1 ptr = root;
        int length = binary.length();
        MinHeapNode1[] fathers = new MinHeapNode1[length - 1];

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

        int i = 0;
        while(i < length - 1 && ptr.number < fathers[i].number){
            swap(fathers[i],ptr);
            ptr = fathers[i];
            i++;
        }
    }
    //insert for build min heap

    public static long deleteMin(int amount){
        String binary = Integer.toBinaryString(amount);
        int length = binary.length();
        MinHeapNode1 ptr = root;
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
        MinHeapNode1 smaller = findSmaller(root);
        while(smaller != null && ptr.number > smaller.number){
            swap(ptr,smaller);
            ptr = smaller;
            smaller = findSmaller(ptr);
        }
        return min;
    }
    //delete for build min heap

    public static void swap(MinHeapNode1 node1, MinHeapNode1 node2){
        long swap = node1.number;
        node1.number = node2.number;
        node2.number = swap;
    }

    public static MinHeapNode1 findSmaller(MinHeapNode1 node){
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

    public static void print(int n) {
        MinHeapNode1[] levelTraverse = new MinHeapNode1[20];
        Queue<MinHeapNode1> queue = new LinkedList<>();

        int cnt = 1;
        levelTraverse[1] = root;
        queue.offer(root);

        while (!queue.isEmpty()) {
            MinHeapNode1 tmp = queue.poll();
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
