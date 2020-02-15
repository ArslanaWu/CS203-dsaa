import java.util.Scanner;

class Node3{
    int number;
    Node3 left = null;
    Node3 right = null;

    Node3(int number){
        this.number = number;
    }
}

public class Ex3 {
    static Node3[] location;
    static Node3 root;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n  = input.nextInt();

            location = new Node3[n + 1];

            int[] pre = new int[n];
            int[] in = new int[n];
            for(int j = 0;j < n;j++){
                pre[j] = input.nextInt();
            }
            for(int j = 0;j < n;j++){
                in[j] = input.nextInt();
            }

            createTree(pre,in);

            root = location[pre[0]];
            postorderTraversal(root,root.number);

            if(i < t - 1){
                System.out.println();
            }
        }
    }

    public static void createTree(int[] pre,int[] in){
        Node3 root;
        if(location[pre[0]] == null){
            root = new Node3(pre[0]);
            location[pre[0]] = root;
        }else{
            root = location[pre[0]];
        }

        int rootPosition = 0;
        for(int i = 0;i < in.length;i++){
            if(in[i] == pre[0]){
                rootPosition = i;
                break;
            }
        }

        int left = rootPosition;
        int right = pre.length - left - 1;

        int[] preLeft = new int[left];
        int[] inLeft = new int[left];
        int[] preRight = new int[right];
        int[] inRight = new int[right];
        for(int i = 0;i < left;i++){
            preLeft[i] = pre[i + 1];
            inLeft[i] = in[i];
        }
        for(int i = 0;i < right;i++){
            preRight[i] = pre[i + 1 + left];
            inRight[i] = in[i + 1 + left];
        }

        if(left > 0){
            Node3 tmp = new Node3(preLeft[0]);
            root.left = tmp;
            location[preLeft[0]] = tmp;
        }
        if(right > 0){
            Node3 tmp = new Node3(preRight[0]);
            root.right = tmp;
            location[preRight[0]] = tmp;
        }

        if(preLeft.length > 1){
            createTree(preLeft,inLeft);
        }
        if(preRight.length > 1){
            createTree(preRight,inRight);
        }

    }

    public static void postorderTraversal(Node3 root,int rootNumber){
        if(root.left != null){
            postorderTraversal(root.left,rootNumber);
        }
        if(root.right != null){
            postorderTraversal(root.right,rootNumber);
        }
        if(root.number != rootNumber){
            System.out.print(root.number + " ");
        }else{
            System.out.print(root.number);
        }
    }
}
