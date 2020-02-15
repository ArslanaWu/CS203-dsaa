import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class TreeNode2{
    int number;
    TreeNode2 leftChild;
    TreeNode2 rightChild;

    TreeNode2(int number){
        this.number = number;
    }
}

public class Ex2 {
    static int[] fathers;
    static TreeNode2[] treeNodes;
    static TreeNode2 root;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for(int i = 0;i < t;i++){
            int n = input.nextInt();

            fathers = new int[n + 1];
            treeNodes = new TreeNode2[n + 1];

            boolean isBinary = buildTree(input,n);
            boolean result = false;

            if(isBinary){
                boolean isComplete = checkComplete(n);
                if(isComplete){
                    boolean isMinHeap = checkMinHeap(root);
                    boolean isMaxHeap = checkMaxHeap(root);

                    if(isMaxHeap || isMinHeap){
                        result = true;
                    }
                }
            }
            if(result){
                System.out.printf("Case #%d: YES\n",i + 1);
            }else{
                System.out.printf("Case #%d: NO\n",i + 1);
            }
        }
    }

    public static boolean buildTree(Scanner input,int n){
        for(int j = 1;j < n + 1;j++){
            int a = input.nextInt();
            fathers[j] = -1;
            TreeNode2 tmp = new TreeNode2(a);
            treeNodes[j] = tmp;
        }

        for(int j = 1;j < n;j++){
            int x = input.nextInt();
            int y = input.nextInt();

            if(treeNodes[x].leftChild == null){
                treeNodes[x].leftChild = treeNodes[y];
                fathers[y] = x;
            }else if(treeNodes[x].rightChild == null){
                treeNodes[x].rightChild = treeNodes[y];
                fathers[y] = x;
            }else{
                return false;
            }
        }

        for(int j = 1;j < n + 1;j++){
            if(fathers[j] == -1){
                root = treeNodes[j];
                break;
            }
        }
        return true;
    }

    public static boolean checkComplete(int n){
        TreeNode2[] levelTraverse = new TreeNode2[1000000];
        Queue<TreeNode2> queue = new LinkedList<>();
        boolean isComplete = true;

        int cnt = 1;
        levelTraverse[1] = root;
        queue.offer(root);

        while(!queue.isEmpty()){
            TreeNode2 tmp = queue.poll();
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

    public static boolean checkMinHeap(TreeNode2 root){
        if(root.leftChild == null && root.rightChild == null){
            return true;
        }else{
            if(root.leftChild != null){
                if(root.number > root.leftChild.number){
                    return false;
                }else{
                    checkMinHeap(root.leftChild);
                }
            }
            if(root.rightChild != null){
                if(root.number > root.rightChild.number){
                    return false;
                }else{
                    checkMinHeap(root.rightChild);
                }
            }
            return true;
        }
    }

    public static boolean checkMaxHeap(TreeNode2 root){
        if(root.leftChild == null && root.rightChild == null){
            return true;
        }else{
            if(root.leftChild != null){
                if(root.number < root.leftChild.number){
                    return false;
                }else{
                    checkMinHeap(root.leftChild);
                }
            }
            if(root.rightChild != null){
                if(root.number < root.rightChild.number){
                    return false;
                }else{
                    checkMinHeap(root.rightChild);
                }
            }
            return true;
        }
    }


}
