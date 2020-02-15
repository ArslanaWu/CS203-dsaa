import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;

class TreeNode1{
    int number;
    TreeNode1 leftChild;
    TreeNode1 rightChild;

    TreeNode1(int number){
        this.number = number;
    }
}

public class Ex1 {
    static int[] fathers;
    static TreeNode1[] treeNodes;
    static TreeNode1 root;


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n = input.nextInt();

            fathers = new int[n + 1];
            treeNodes = new TreeNode1[n + 1];

            buildTree(input,n);

            boolean isComplete = checkComplete(n);

            if(isComplete){
                System.out.println("Yes");
            }else{
                System.out.println("No");
            }
        }
    }

    public static void buildTree(Scanner input,int n){
        for(int j = 1;j < n + 1;j++){
            fathers[j] = -1;
            TreeNode1 tmp = new TreeNode1(j);
            treeNodes[j] = tmp;
        }

        for(int j = 1;j < n + 1;j++){
            int x = input.nextInt();
            int y = input.nextInt();

            if(x != 0){
                treeNodes[j].leftChild = treeNodes[x];
                fathers[x] = j;
            }
            if(y != 0){
                treeNodes[j].rightChild = treeNodes[y];
                fathers[y] = j;
            }
        }

        for(int j = 1;j < n + 1;j++){
            if(fathers[j] == -1){
                root = treeNodes[j];
                break;
            }
        }
    }

    public static boolean checkComplete(int n){
        TreeNode1[] levelTraverse = new TreeNode1[262150];
        Queue<TreeNode1> queue = new LinkedList<>();
        boolean isComplete = true;

        int cnt = 1;
        levelTraverse[1] = root;
        queue.offer(root);

        while(!queue.isEmpty()){
            TreeNode1 tmp = queue.poll();
            if(tmp.leftChild != null){
                levelTraverse[cnt * 2] = tmp.leftChild;
                queue.offer(tmp.leftChild);
            }
            if(tmp.rightChild != null){
                levelTraverse[cnt * 2 + 1] = tmp.rightChild;
                queue.offer(tmp.rightChild);
            }
            cnt++;
        }

        for(int i = 1;i < n + 1;i++){
            if(levelTraverse[i] == null){
                isComplete =  false;
                break;
            }
        }
        return  isComplete;
    }
}
